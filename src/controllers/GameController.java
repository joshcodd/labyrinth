package controllers;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import views.scenes.MenuScene;
import java.net.URL;
import java.util.*;

/**
 * Controller class for GameScene. Runs through a game and then renders changes throughout the game to
 * the view.
 * @author Josh Codd, Neil Woodhouse
 */
public class GameController implements Initializable {
    @FXML
    public GridPane bottomButtons;
    @FXML
    public GridPane topButtons;
    @FXML
    public GridPane leftButtons;
    @FXML
    public GridPane rightButtons;
    @FXML
    public Label playerLabel;
    @FXML
    public HBox boardArea;
    @FXML
    public javafx.scene.image.ImageView selectedTile;
    @FXML
    public Button actionButton;
    @FXML
    public Button continueButton;
    @FXML
    public ImageView incrementOrientation;
    @FXML
    public ImageView decrementOrientation;
    @FXML
    public Button drawTile;
    @FXML
    private GridPane gameBoardPane;
    @FXML
    private VBox playerConsole;
    @FXML
    private VBox menu;
    @FXML
    private Button menuButton;
    @FXML
    private Button muteButton;
    @FXML
    private Button saveButton;
    @FXML
    private MediaView backgroundMusic;
    @FXML
    private GridPane actionTilePane;

    private GameBoard gameBoard;
    private SimpleDoubleProperty tileSize = new SimpleDoubleProperty(0);
    private Game game;
    private Stage primaryStage;
    private MediaPlayer mediaPlayer;

    /**
     * Constructs a GameController.
     * @param game The game to be played.
     * @param stage The stage that this application is being displayed on.
     */
    public GameController(Game game, Stage stage, MediaPlayer backgroundMusic) {
        this.gameBoard = game.getGameBoard();
        this.game = game;
        this.primaryStage = stage;
        this.mediaPlayer = backgroundMusic;
    }

    /**
     * Initializes the Game and the Game GUI so that it's ready to be played.
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topButtons.setTranslateX(50);
        bottomButtons.setTranslateX(50);
        tileSize.bind(boardArea.heightProperty().divide(gameBoard.getHeight() + 2));
        setPlayerLabel("Player " + (game.getCurrentPlayerNum() + 1) + "'s turn!");
        backgroundMusic.setMediaPlayer(mediaPlayer);
        initializeEventHandlers();

    }

    /**
     * Updates the current game board GUI being displayed in the game scene.
     */
    public void updateGameBoard() {
        gameBoardPane.getChildren().clear();
        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                gameBoardPane.add(new StackPane(getTileImage(gameBoard.getTileAt(new Coord(i, j)))), j, i);
            }
        }
    }

    public void updateActionTileHand() {
        actionTilePane.getChildren().clear();
        ArrayList<ActionTile> currentActionTiles = game.getCurrentPlayer().getActionTiles();
        for (int i = 0; i < currentActionTiles.size(); i++) {

            String testText = "Tile" + i;
            String imagePath = "/resources/" + currentActionTiles.get(i).getClass().getName().substring(7) + ".png";
            actionTilePane.add(new ImageView(new Image(imagePath, 40, 40, false, false)), i, 0);
        }
    }

    /**
     * Updates tiles so that they are selectable and can be used for a player to move on to that tile. Tiles are
     * only updated if that tile is a possible and legal move.
     * @param moves The moves that are available to the current player.
     */
    public void updateMoves(ArrayList<Coord> moves) {
        for (Node node : gameBoardPane.getChildren()) {
            Coord curr = new Coord(GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
            if (moves.contains(curr)){
                StackPane tile = (StackPane)node;
                ImageView x = new ImageView("/resources/X.png");
                x.setFitWidth(40);
                x.setFitHeight(40);
                tile.getChildren().add(x);

                tile.getStyleClass().add("tile-selection");
                tile.setOnMouseClicked(event -> {
                    game.getCurrentPlayer().movePlayer(curr);
                    tile.getChildren().remove(x);
                    updateGameBoard();
                    nextRound();
                    drawPlayers();
                    new AudioPlayer().clickPlay();
                });
            }
        }
    }

    /**
     * Set a message to displayed in the player label.
     * @param message The message to be displayed.
     */
    public void setPlayerLabel(String message){
        playerLabel.setText(message);
    }

    /**
     * Draws buttons for inserting tiles at the ends of every row or columns where the rows are moveable. That is, there
     * are no fixed tiles in that row or column.
     * @param visible If the arrows should be visible or hidden.
     */
    public void updateArrows(boolean visible) {
        clearArrows();
        ColumnConstraints gridWidth = new ColumnConstraints();
        gridWidth.minWidthProperty().bind(tileSize);

        RowConstraints gridHeight = new RowConstraints();
        gridHeight.minHeightProperty().bind(tileSize);

        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {

                if (j == 0) {
                    leftButtons.getRowConstraints().add(gridHeight);
                    ImageView arrow = getArrowImage("LEFT", i, 2);
                    arrow.setVisible(visible && !gameBoard.isRowFixed(i));
                    leftButtons.add(new StackPane(arrow), j, i);
                } else if (j == gameBoard.getWidth() - 1) {
                    rightButtons.getRowConstraints().add(gridHeight);
                    ImageView arrow = getArrowImage("RIGHT", i,0);
                    arrow.setVisible(visible && !gameBoard.isRowFixed(i));
                    rightButtons.add(new StackPane(arrow), j, i);
                }

                if (i == 0) {
                    topButtons.getColumnConstraints().add(gridWidth);
                    ImageView arrow = getArrowImage("DOWN", j,3);
                    arrow.setVisible(visible && !gameBoard.isColumnFixed(j));
                    topButtons.add(new StackPane(arrow), j, i);
                } else if (i == gameBoard.getHeight() - 1) {
                    bottomButtons.getColumnConstraints().add(gridWidth);
                    ImageView arrow = getArrowImage("UP", j, 1);
                    arrow.setVisible(visible && !gameBoard.isColumnFixed(j));
                    bottomButtons.add(new StackPane(arrow), j, i);
                }
            }
        }
    }

    /**
     * Draws the players currently in the game to the scene at their current positions.
     */
    public void drawPlayers() {
        for (Player player : game.getPlayers()) {
            for (Node node : gameBoardPane.getChildren()) {
                if (GridPane.getColumnIndex(node) == player.getCurrentPosition().getY()
                        && GridPane.getRowIndex(node) == player.getCurrentPosition().getX()) {
                    int playerNumber = player.getPlayerNumber() + 1;
                    ImageView tank = new ImageView("resources/" + playerNumber + ".png");
                    tank.setFitHeight(30);
                    tank.setFitWidth(30);
                    StackPane cell = (StackPane) node;
                    cell.getChildren().add(tank);
                }
            }
        }
    }

    private void clearArrows() {
        leftButtons.getChildren().clear();
        leftButtons.getRowConstraints().clear();
        rightButtons.getChildren().clear();
        rightButtons.getRowConstraints().clear();
        topButtons.getChildren().clear();
        topButtons.getColumnConstraints().clear();
        bottomButtons.getColumnConstraints().clear();
        bottomButtons.getChildren().clear();
    }

    private ImageView getTileImage(FloorTile tile) {
        ImageView tileImage = new ImageView("/resources/" + tile.isFixed()
                + tile.getShape() + ".png");
        tileImage.fitHeightProperty().bind(tileSize);
        tileImage.fitWidthProperty().bind(tileSize);
        tileImage.setRotate(getRotationValue(tile.getOrientation()));
        return tileImage;
    }

    private ImageView getArrowImage(String direction, int index, int orientation) {
        ImageView arrow = new ImageView("/resources/left.png");
        arrow.setFitWidth(50);
        arrow.setFitHeight(50);
        arrow.setRotate(getRotationValue(orientation));
        arrow.setOnMouseClicked(event -> {
            Tile fallenOff = gameBoard.insertTile((FloorTile) game.getCurrentTile(), direction, index);
            game.updatePlayerPositions(direction, index);
            updateGameBoard();
            updateArrows(false);
            drawPlayers();
            game.getTileBag().addTile(fallenOff);
            continueButton.setDisable(false);
            selectedTile.setImage(null);
            new AudioPlayer().clickPlay();
        });
        return arrow;
    }

    private void nextRound() {
        this.setPlayerLabel("No available moves:(");
        if (game.checkWin(game.getCurrentPlayer())) {
            setPlayerLabel("Player " + (game.getCurrentPlayerNum()) + " Wins!");
            game.setOver(true);
            //TODO Exit game with win screen + related audio
        }
        game.nextPlayer();
        selectedTile.setImage(null);
        updateActionTileHand();
        this.setPlayerLabel("Player " + (game.getCurrentPlayerNum() + 1) + "'s turn!");
        drawTile.setDisable(false);
        this.updateArrows(false);
        continueButton.setDisable(true);
    }

    private double getRotationValue(int orientation) {
        switch (orientation) {
            case 1 :
                return 90;
            case 2 :
                return 180;
            case 3 :
                return 270;
            default:
                return 0;
        }
    }

    private void initializeEventHandlers(){
        menuButton.setOnMouseClicked((event) -> {
            menu.setVisible(!menu.isVisible());
            playerConsole.setVisible(!menu.isVisible());
            new AudioPlayer().clickPlay();
        });

        drawTile.setOnAction((event) -> {
            if (game.getCurrentTile() == null) {
                game.setCurrentTile(game.getTileBag().drawTile());
                if (game.getCurrentTile() instanceof FloorTile) {
                    updateArrows(true);
                    selectedTile.setImage((getTileImage((FloorTile) game.getCurrentTile())).getImage());
                    selectedTile.setRotate(getRotationValue(((FloorTile) game.getCurrentTile()).getOrientation()));
                } else {
                    selectedTile.setImage(new Image("/resources/" + game.getCurrentTile()
                            .getClass().getName().substring(7) + ".png"));
                    selectedTile.setRotate(0);
                }
                drawTile.setDisable(true);
                if (!(game.getCurrentTile() instanceof FloorTile)) {
                    continueButton.setDisable(false);
                }
            } else {
                System.out.println(game.getCurrentTile());
            }
            new AudioPlayer().clickPlay();
        });

        decrementOrientation.setOnMouseClicked(event -> {
            if (game.getCurrentTile() != null && game.getCurrentTile() instanceof FloorTile){
                ((FloorTile) game.getCurrentTile()).decrementOrientation();
                selectedTile.setRotate(getRotationValue(((FloorTile) game.getCurrentTile()).getOrientation()));
            }
            new AudioPlayer().clickPlay();
        });

        incrementOrientation.setOnMouseClicked(event -> {
            if (game.getCurrentTile() != null && game.getCurrentTile() instanceof FloorTile){
                ((FloorTile) game.getCurrentTile()).incrementOrientation();
                selectedTile.setRotate(getRotationValue(((FloorTile) game.getCurrentTile()).getOrientation()));
            }
            new AudioPlayer().clickPlay();
        });

        continueButton.setOnAction((event) -> {
            if (drawTile.isDisabled()) {
                if (ActionTile.class.isAssignableFrom(game.getCurrentTile().getClass())) {
                    game.getCurrentPlayer().addActionTile((ActionTile)game.getCurrentTile());
                }
                if ((game.getCurrentPlayer().getActionTiles()).size() == 0) {
                    actionButton.setDisable(true);
                }
                ArrayList<Coord> validMoves = gameBoard.getValidMoves(game.getCurrentPlayer());
                if (validMoves.size() == 0) {
                    nextRound();
                } else {
                    this.continueButton.setDisable(true);
                    updateMoves(validMoves);
                }
            }
            new AudioPlayer().clickPlay();
        });

        continueButton.setDisable(true);
        if (game.getCurrentPlayer().getActionTiles().size() == 0) {
            actionButton.setDisable(true);
        }

        saveButton.setOnMouseClicked(event -> {
            MenuScene menu = new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
        });

        muteButton.setOnMouseClicked(event -> {
            if (muteButton.getText().equals("Mute")){
                mediaPlayer.pause();
                muteButton.setText("Unmute");
            } else {
                mediaPlayer.play();
                muteButton.setText("Mute");
            }
        });
    }
}