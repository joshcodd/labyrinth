package controllers;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import views.scenes.MenuScene;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
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
    private HBox actionTilePane;
    @FXML
    private Button quitButton;

    private GameBoard gameBoard;
    private SimpleDoubleProperty tileSize = new SimpleDoubleProperty(0);
    private Game game;
    private Stage primaryStage;
    private MediaPlayer mediaPlayer;
    private ActionTile selectedActionTile;

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
        //setPlayerLabel("Player " + (game.getCurrentPlayerNum() + 1) + "'s turn!");
        setPlayerLabel(game.getCurrentPlayerName() +"'s turn'");
        backgroundMusic.setMediaPlayer(mediaPlayer);
        muteButton.setText(backgroundMusic.getMediaPlayer().isMute() ? "Un-mute":"Mute");
        initializeEventHandlers();
        updateActionTileHand();
        drawActions();




        if (game.getCurrentTile() != null && game.getCurrentTile() instanceof ActionTile){
            selectedActionTile = (ActionTile) game.getCurrentTile();
            selectedTile.setImage(new Image("/resources/" + selectedActionTile.getClass().getName()
                    .substring(7) + ".png"));
            continueButton.setDisable(false);
            actionButton.setDisable(false);
            drawTile.setDisable(true);
        } else if (game.getCurrentTile() != null && game.getCurrentTile() instanceof FloorTile) {
            FloorTile tile = (FloorTile) game.getCurrentTile();
            selectedTile.setImage(getTileImage(tile).getImage());
            drawTile.setDisable(true);
        }
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
        drawActions();
    }

    /**
     *
     */
    public void updateActionTileHand() {
        actionTilePane.getChildren().clear();
        ArrayList<ActionTile> currentActionTiles = game.getCurrentPlayer().getActionTiles();
        for (int i = 0; i < currentActionTiles.size(); i++) {
            int actionTileNum = i;
            String imagePath = "/resources/" + currentActionTiles.get(i).getClass().getName().substring(7) + ".png";
            ImageView actionTile = new ImageView(new Image(imagePath));
            actionTile.setFitWidth(100);
            actionTile.setFitHeight(100);
            actionTile.getStyleClass().add("hover-effect");
            actionTile.setOnMouseClicked(event -> {
                if (!actionButton.isDisable()) {
                    selectedActionTile = currentActionTiles.get(actionTileNum);
                    selectedTile.setImage(new Image("/resources/" + currentActionTiles.get(actionTileNum)
                            .getClass().getName().substring(7) + ".png"));
                    selectedTile.setRotate(0);
                    game.setCurrentTile(selectedActionTile);
                }
            });
            actionTilePane.getChildren().add(actionTile);

        }
    }

    private ArrayList<Coord> availableMoves (ArrayList<Coord> moves){
        for (Player player : game.getPlayers()) {
            moves.remove(player.getCurrentPosition());
        }
        return moves;
    }


    /**
     * Updates tiles so that they are selectable and can be used for a player to move on to that tile. Tiles are
     * only updated if that tile is a possible and legal move.
     * @param moves The moves that are available to the current player.
     */
    public void updateMoves(ArrayList<Coord> moves) {
        availableMoves(moves);
        for (Node node : gameBoardPane.getChildren()) {
            Coord curr = new Coord(GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
            if (moves.contains(curr) && !(gameBoard.getAction(curr) instanceof FireTile)){
                StackPane tile = (StackPane)node;
                ImageView x = new ImageView("/resources/X.png");
                x.setFitWidth(40);
                x.setFitHeight(40);
                tile.getChildren().add(x);
                tile.getStyleClass().add("tile-selection");
                tile.setOnMouseClicked(event -> {
                    tile.getChildren().remove(x);
                    animateMove(curr);
                    game.getCurrentPlayer().movePlayer(curr);
                    new AudioPlayer().clickPlay();
                });
            }
        }
    }

    /**
     *
     */
    public ImageView getPlayer(StackPane tile) {
        ImageView playerPiece = null;
        playerPiece = (ImageView) tile.getChildren().get(tile.getChildren().size() - 1);
        return playerPiece;
    }

    /**
     *
     */
    public StackPane getTilePane(int x, int y) {
        StackPane tile = null;
        for (Node node : gameBoardPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == y
                    && GridPane.getRowIndex(node) == x) {
                tile = (StackPane) node;
            }
        }
        return tile;
    }

    private void animateMove(Coord destination) {
        Coord currPosition = game.getCurrentPlayer().getCurrentPosition();
        StackPane currentPane = getTilePane(currPosition.getX(), currPosition.getY());
        StackPane destPane = getTilePane(destination.getX(), destination.getY());
        StackPane finalPane = getTilePane(gameBoard.getWidth() - 1, gameBoard.getHeight() - 1);
        ImageView tank = getPlayer(currentPane);
        Point2D tankReal = currentPane.localToScene(currentPane.getWidth()/2,currentPane.getWidth()/2);
        Point2D tankDest = destPane.localToScene(destPane.getWidth()/2,destPane.getHeight()/2);
        Point2D tankFake = finalPane.localToScene(finalPane.getWidth()/2,finalPane.getWidth()/2);
        double fakeX = tankReal.getX() - tankFake.getX();
        double fakeY = tankReal.getY() - tankFake.getY();
        double transX = tankDest.getX() - tankFake.getX();
        double transY = tankDest.getY() - tankFake.getY();
        currentPane.getChildren().remove(tank);
        finalPane.getChildren().add(tank);
        tank.setTranslateX(fakeX);
        tank.setTranslateY(fakeY);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(1));
        translateTransition.setToX(transX);
        translateTransition.setToY(transY);
        translateTransition.setNode(tank);
        translateTransition.play();

        translateTransition.setOnFinished(e->{
            if (continueButton.isDisabled() && drawTile.isDisabled()) {
                nextRound();
            }
            updateGameBoard();
            drawPlayers();
        });
    }

    /**
     * Set a message to displayed in the player label.
     * @param message The message to be displayed.
     */
    public void setPlayerLabel(String message){
        playerLabel.setText(message);
    }

    public void playAction(ActionTile action) {
        for (Node node : gameBoardPane.getChildren()) {
            Coord position = new Coord(GridPane.getRowIndex(node), GridPane.getColumnIndex(node));

            //Selects surrounding tiles for a 3x3
            int xPos = position.getX();
            int yPos = position.getY();
            ArrayList<Coord> positions = new ArrayList<>();
            positions.add(new Coord(xPos - 1, yPos + 1));
            positions.add(new Coord(xPos, yPos + 1));
            positions.add(new Coord(xPos + 1, yPos +1));
            positions.add(new Coord(xPos - 1, yPos));
            positions.add(position);
            positions.add(new Coord(xPos + 1, yPos));
            positions.add(new Coord(xPos - 1, yPos - 1));
            positions.add(new Coord(xPos, yPos - 1));
            positions.add(new Coord(xPos + 1, yPos - 1));


            boolean validPlacement = true;
            if (action instanceof FireTile) {
                for (Player player : game.getPlayers()) {
                    for (Coord surroundingTile : positions) {
                        if (player.getCurrentPosition().equals(surroundingTile)) {
                            validPlacement = false;
                        } else if(surroundingTile.isEmpty()) {
                            positions.remove(surroundingTile);
                        }
                    }
                }
            }
            if ((gameBoard.getAction(position) == null) && (validPlacement)) {
                StackPane tile = (StackPane) node;
                tile.getStyleClass().add("tile-selection");
                tile.setOnMouseClicked(event -> {
                    for (Coord pos: positions) {
                        if (action instanceof FireTile) {
                            gameBoard.addAction(new FireTile(), pos);
                        }
                        else if (action instanceof IceTile) {
                            gameBoard.addAction(new IceTile(), pos);
                        }
                    }
                    updateGameBoard();
                    drawPlayers();
                    continueButton.setDisable(false);
                });
            }
        }
    }

    /**
     *
     */
    public void drawActions() {
        for (Node node : gameBoardPane.getChildren()) {
            Coord position = new Coord(GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
            ActionTile currentAction = gameBoard.getAction(position);

            if (currentAction instanceof FireTile) {
                StackPane action = (StackPane)node;
                ImageView fire = new ImageView("/resources/FireTile.png");
                fire.setFitWidth(60);
                fire.setFitHeight(60);
                fire.setRotate(new Random().nextInt(360));
                action.getChildren().add(fire);
            }
            else if (currentAction instanceof IceTile) {
                StackPane action = (StackPane)node;
                ImageView ice = new ImageView("/resources/IceTile.png");
                ice.setFitWidth(40);
                ice.setFitHeight(40);
                action.getChildren().add(ice);
            }
        }
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
                    ImageView tank = new ImageView("resources/" + player.getColour() + ".png");
                    tank.setFitHeight(30);
                    tank.setFitWidth(30);
                    StackPane cell = (StackPane) node;
                    cell.getChildren().add(tank);
                }
            }
        }
    }

    /**
     *
     */
    private void selectBacktrack() {
        for (Player player : game.getPlayers()) {
            for (Node node : gameBoardPane.getChildren()) {
                if (GridPane.getColumnIndex(node) == player.getCurrentPosition().getY()
                        && GridPane.getRowIndex(node) == player.getCurrentPosition().getX()) {
                    ImageView tank = new ImageView("resources/" + player.getColour() + ".png");
                    tank.setOnMouseClicked(event -> backtrackPlayer(player));
                    tank.setFitHeight(30);
                    tank.setFitWidth(30);
                    StackPane cell = (StackPane) node;
                    cell.getChildren().add(tank);
                }
            }
        }
    }

    /**
     * @param player the player to be backtracked to an earlier position
     */
    private void backtrackPlayer(Player player) {
        Coord pastPosition = player.getPrevPosition(1);
        ActionTile targetTile = gameBoard.getAction(pastPosition);
        if (!pastPosition.isEmpty() && !(targetTile instanceof FireTile)) {
            player.movePlayer(pastPosition);
        }
        else {
            pastPosition = player.getPrevPosition(0);
            targetTile = gameBoard.getAction(pastPosition);
            if (pastPosition.isEmpty() && !(targetTile instanceof FireTile)) {
                player.movePlayer(pastPosition);
            }
        }
        updateGameBoard();
        drawPlayers();
    }

    /**
     *
     */
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

    /**
     * @param tile the FloorTile object to fetch a sprite for
     * @return an ImageView object with the correct sprite rendering for the given tile
     */
    private ImageView getTileImage(FloorTile tile) {
        ImageView tileImage = new ImageView("/resources/" + tile.isFixed()
                + tile.getShape() + ".png");
        tileImage.fitHeightProperty().bind(tileSize);
        tileImage.fitWidthProperty().bind(tileSize);
        tileImage.setRotate(getRotationValue(tile.getOrientation()));
        return tileImage;
    }

    /**
     * @param direction the direction that affected players should be moved, if the arrow is clicked
     * @param index the position index of the arrow on the grid
     * @param orientation the direction that the arrow should be facing in
     * @return an ImageView object of the tile image, with correct on click behaviour
     */
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
            if ((game.getCurrentPlayer().getActionTiles()).size() > 0) {
                actionButton.setDisable(false);
            }
        });
        return arrow;
    }

    private void handleWinSaves() {
        for (Player player : game.getPlayers()){

            try {
                if (player.getProfileName().equals(game.getCurrentPlayerName())) {
                    player.getProfile().incrementWins();
                } else {
                    player.getProfile().incrementLosses();
                }
                player.getProfile().incrementGamesPlayed();
                player.getProfile().save();
                FileHandler.saveLeaderboard(game.getLevelName(), player.getProfileName());
            } catch (IOException e){
                e.printStackTrace();
                Alert leaderboardError = new Alert(Alert.AlertType.ERROR,
                        "An error was encountered while attempting to update the leaderboards. " +
                                "Any updates to the leaderboard status have not been saved.",
                        ButtonType.OK);
                leaderboardError.showAndWait();
            }
        }
    }

    /**
     * This method checks if the current player has won, and otherwise sets up the UI for the next player's turn.
     */
    private void nextRound() {
        gameBoard.refreshActionBoard(game.getNumPlayers());
        if (game.checkWin(game.getCurrentPlayer())) {
            setPlayerLabel(game.getCurrentPlayerName() + " Wins!");
            game.setOver(true);
            handleWinSaves();
        } else {
            game.nextPlayer();
            selectedTile.setImage(null);
            updateActionTileHand();
            this.setPlayerLabel(game.getCurrentPlayerName() + "'s turn!");
            drawTile.setDisable(false);
            this.updateArrows(false);
            continueButton.setDisable(true);
            updateGameBoard();
            drawPlayers();
        }
    }


    /**
     * @param orientation the integer value representing the orientation of a tile
     * @return the rotation value of the tile (in degrees) (possible values: 0, 90, 180, 270)
     */
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

    /**
     *
     */
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
                    if ((game.getCurrentPlayer().getActionTiles()).size() > 0) {
                        actionButton.setDisable(false);
                    }
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
                ArrayList<Coord> validMoves = gameBoard.getValidMoves(game.getCurrentPlayer());
                if (availableMoves(validMoves).size() == 0) {
                    nextRound();
                } else {
                    this.continueButton.setDisable(true);
                    updateMoves(validMoves);
                }
            }
            new AudioPlayer().clickPlay();
            actionButton.setDisable(true);
            selectedTile.setImage(null);
        });

        continueButton.setDisable(true);
        actionButton.setDisable(true);

        saveButton.setOnMouseClicked(event -> {
            if (!game.isOver()){
                TextInputDialog input = new TextInputDialog(Instant.now().toString());
                input.showAndWait();
                try {
                    String result = input.getResult().trim();
                    if (result.equals("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot be empty", ButtonType.CLOSE);
                        alert.showAndWait();
                    } else {
                        try {
                            FileHandler.saveGameFile(input.getResult(), game);
                            MenuScene menu = new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
                        } catch (IOException exception) {
                            exception.printStackTrace();
                            Alert saveError = new Alert(Alert.AlertType.ERROR,
                                    "An error was encountered while attempting to save the game. " +
                                            "The game state has not been saved.",
                                    ButtonType.OK, ButtonType.CANCEL);
                            saveError.showAndWait();
                            if (saveError.getResult() == ButtonType.OK) {
                                MenuScene menu = new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
                            }
                        }

                    }
                } catch (NullPointerException ignored){
                    ignored.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Game is over.", ButtonType.CLOSE);
                alert.showAndWait();
            }
        });

        quitButton.setOnMouseClicked(event -> {
            MenuScene menu = new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
        });

        muteButton.setOnMouseClicked(event -> {
            backgroundMusic.getMediaPlayer().setMute(!backgroundMusic.getMediaPlayer().isMute());
            muteButton.setText(backgroundMusic.getMediaPlayer().isMute() ? "UnMute":"Mute");
        });

        actionButton.setOnMouseClicked(e -> {
                if (selectedActionTile instanceof DoubleMoveTile) {
                    updateMoves(gameBoard.getValidMoves(game.getCurrentPlayer()));
                    actionButton.setDisable(true);
                    selectedTile.setImage(null);
                    selectedActionTile = null;
                    game.getCurrentPlayer().removeActionTile(new DoubleMoveTile());
                    updateActionTileHand();
                }
                else if (selectedActionTile instanceof BackTrackTile) {
                    selectBacktrack();
                    actionButton.setDisable(true);
                    selectedTile.setImage(null);
                    selectedActionTile = null;
                    game.getCurrentPlayer().removeActionTile(new BackTrackTile());
                    updateActionTileHand();
                }
                else if (selectedActionTile instanceof FireTile) {
                    playAction(new FireTile());
                    actionButton.setDisable(true);
                    continueButton.setDisable(true);
                    selectedTile.setImage(null);
                    selectedActionTile = null;
                    game.getCurrentPlayer().removeActionTile(new FireTile());
                    updateActionTileHand();
                }
                else if (selectedActionTile instanceof IceTile) {
                    playAction(new IceTile());
                    actionButton.setDisable(true);
                    continueButton.setDisable(true);
                    selectedTile.setImage(null);
                    selectedActionTile = null;
                    game.getCurrentPlayer().removeActionTile(new IceTile());
                    updateActionTileHand();
                }
        });
    }
}