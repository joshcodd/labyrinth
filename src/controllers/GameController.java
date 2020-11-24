package controllers;
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
import views.scenes.GameScene;
import views.scenes.MenuScene;

import javax.swing.*;
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


    private GameBoard gameBoard;
    private Player[] players;
    private SimpleDoubleProperty tileSize = new SimpleDoubleProperty(0);
    private Game game;
    private Stage primaryStage;


    public GameController(Game game, Stage stage) {
        this.players = game.getPlayers();
        this.gameBoard = game.getGameBoard();
        this.game = game;
        this.primaryStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topButtons.setTranslateX(50);
        bottomButtons.setTranslateX(50);
        tileSize.bind(boardArea.heightProperty().divide(gameBoard.getHeight() + 2));
        setPlayerLabel("Player " + (game.getCurrentPlayer() + 1) + "'s turn!");
        gameBoard.addObserver((o, arg) -> {
            updateGameBoard();
            updateArrows(false);
            drawPlayers();
        });

        menuButton.setOnMouseClicked((event) -> {
            menu.setVisible(!menu.isVisible());
            playerConsole.setVisible(!menu.isVisible());
        });

        drawTile.setOnAction((event) -> {
            if (game.getCurrentTile() == null) {
                game.setCurrentTile(game.getTileBag().drawTile());
                if (game.getCurrentTile() instanceof FloorTile) {
                    updateArrows(true);
                    selectedTile.setImage(new Image("/resources/" + "false"
                            + ((FloorTile) game.getCurrentTile()).getShape() + ".png"));
                } else {
                    selectedTile.setImage(new Image("/resources/" + game.getCurrentTile()
                            .getClass().getName().substring(7) + ".png"));
                }
                drawTile.setDisable(true);
                if (!(game.getCurrentTile() instanceof FloorTile)) {
                    continueButton.setDisable(false);
                }
            } else {
                System.out.println(game.getCurrentTile());
            }
        });

        decrementOrientation.setOnMouseClicked(event -> {
            if (game.getCurrentTile() != null && game.getCurrentTile() instanceof FloorTile){
                ((FloorTile) game.getCurrentTile()).decrementOrientation();
                getRotationValue();
            }
        });

        incrementOrientation.setOnMouseClicked(event -> {
            if (game.getCurrentTile() != null && game.getCurrentTile() instanceof FloorTile){
                ((FloorTile) game.getCurrentTile()).incrementOrientation();
                getRotationValue();
            }
        });

        continueButton.setOnAction((event) -> {
            if (drawTile.isDisabled()) {
                if (ActionTile.class.isAssignableFrom(game.getCurrentTile().getClass())) {
                    players[game.getCurrentPlayer()].addActionTile((ActionTile)game.getCurrentTile());
                }

                if ((players[game.getCurrentPlayer()].getActionTiles()).size() == 0) {
                    actionButton.setDisable(true);
                }

                ArrayList<Coord> validMoves = gameBoard.getValidMoves(game.getPlayers()[game.getCurrentPlayer()]);

                if (validMoves.size() == 0) {
                    nextRound();
                } else {
                    this.continueButton.setDisable(true);
                    updateMoves(validMoves);
                }

            }
        });

        continueButton.setDisable(true);
        if (players[game.getCurrentPlayer()].getActionTiles().size() == 0) {
            actionButton.setDisable(true);
        }

        saveButton.setOnMouseClicked(event -> {
            MenuScene menu = new MenuScene(primaryStage);
        });
    }

    /**
     * Updates the current game board GUI being displayed in the game scene.
     */
    public void updateGameBoard() {
        gameBoardPane.getChildren().clear();
        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                gameBoardPane.add(new StackPane(getTileImage(i,j)), j, i);
            }
        }
        drawPlayers();
    }

    public void updateMoves(ArrayList<Coord> moves) {
        for (Node node : gameBoardPane.getChildren()) {
            Coord curr = new Coord(GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
            if (moves.contains(curr)){
                StackPane tile = (StackPane)node;
                ImageView x = new ImageView("/resources/X.png");
                x.setFitWidth(40);
                x.setFitHeight(40);
                tile.getChildren().add(x);

                tile.setOnMouseClicked(event -> {
                    Player player = game.getPlayers()[game.getCurrentPlayer()];
                    player.movePlayer(curr);
                    tile.getChildren().remove(x);
                    updateGameBoard();
                    nextRound();
                });

                tile.setOnMouseEntered(event -> {
                    tile.setOpacity(0.5);
                });

                tile.setOnMouseExited(event -> {
                    tile.setOpacity(1);
                });
            }
        }
    }
    
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
                    ImageView arrow = getArrowImage("LEFT", i);
                    if (visible) {
                        arrow.setVisible(!gameBoard.isRowFixed(i));
                    } else {
                        arrow.setVisible(false);
                    }
                    leftButtons.add(new StackPane(arrow), j, i);
                } else if (j == gameBoard.getWidth() - 1) {
                    rightButtons.getRowConstraints().add(gridHeight);
                    ImageView arrow = getArrowImage("RIGHT", i);
                    if (visible) {
                        arrow.setVisible(!gameBoard.isRowFixed(i));
                    } else {
                        arrow.setVisible(false);
                    }
                    rightButtons.add(new StackPane(arrow), j, i);
                }

                if (i == 0) {
                    topButtons.getColumnConstraints().add(gridWidth);
                    ImageView arrow = getArrowImage("DOWN", j);
                    if (visible) {
                        arrow.setVisible(!gameBoard.isColumnFixed(j));
                    } else {
                        arrow.setVisible(false);
                    }
                    topButtons.add(new StackPane(arrow), j, i);
                } else if (i == gameBoard.getHeight() - 1) {
                    bottomButtons.getColumnConstraints().add(gridWidth);
                    ImageView arrow = getArrowImage("UP", j);
                    if (visible) {
                        arrow.setVisible(!gameBoard.isColumnFixed(j));
                    } else {
                        arrow.setVisible(false);
                    }
                    bottomButtons.add(new StackPane(arrow), j, i);
                }
            }
        }
    }

    /**
     * Draws the players currently in the game to the scene at their current positions.
     */
    public void drawPlayers() {
        for (Player player : players) {
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

    /**
     * Removes the arrow buttons that surround the game board.
     */
    public void clearArrows() {
        leftButtons.getChildren().clear();
        leftButtons.getRowConstraints().clear();
        rightButtons.getChildren().clear();
        rightButtons.getRowConstraints().clear();
        topButtons.getChildren().clear();
        topButtons.getColumnConstraints().clear();
        bottomButtons.getColumnConstraints().clear();
        bottomButtons.getChildren().clear();
    }

    private ImageView getTileImage(int i, int j) {
        ImageView tile = new ImageView("/resources/" + gameBoard.getTileAt(i, j).isFixed()
                + gameBoard.getTileAt(i, j).getShape() + ".png");
        tile.fitHeightProperty().bind(tileSize);
        tile.fitWidthProperty().bind(tileSize);

        switch (gameBoard.getTileAt(i, j).getOrientation()) {
            case 0 :
                tile.setRotate(0);
                break;
            case 1 :
                tile.setRotate(90);
                break;
            case 2 :
                tile.setRotate(180);
                break;
            case 3 :
                tile.setRotate(270);
                break;
        }
        return tile;
    }

    private ImageView getArrowImage(String direction, int index) {
        ImageView arrow = new ImageView("/resources/left.png");
        arrow.setFitWidth(50);
        arrow.setFitHeight(50);

        switch (direction) {
            case "LEFT" :
                arrow.setRotate(180);
                break;
            case "RIGHT" :
                arrow.setRotate(0);
                break;
            case "UP" :
                arrow.setRotate(90);
                break;
            case "DOWN" :
                arrow.setRotate(-90);
                break;
        }
        arrow.setOnMouseClicked(event -> {
            gameBoard.insertTile((FloorTile) game.getCurrentTile(), direction, index);
            continueButton.setDisable(false);
            selectedTile.setImage(null);
        });
        return arrow;
    }

    private void nextRound() {
        this.setPlayerLabel("No available moves:(");
        Player currentPlayer = players[game.getCurrentPlayer()];
        if (game.checkWin(currentPlayer)) {
            setPlayerLabel("Player " + (currentPlayer.getPlayerNumber()) + " Wins!");
            game.setOver(true);
            //TODO Exit game with win screen + related audio
        }
        game.nextPlayer();
        selectedTile.setImage(null);
        this.setPlayerLabel("Player " + (game.getCurrentPlayer() + 1) + "'s turn!");
        drawTile.setDisable(false);
        this.updateArrows(false);
        continueButton.setDisable(true);
    }

    private void getRotationValue() {
        switch (((FloorTile) game.getCurrentTile()).getOrientation()) {
            case 0 :
                selectedTile.setRotate(0);
                break;
            case 1 :
                selectedTile.setRotate(90);
                break;
            case 2 :
                selectedTile.setRotate(180);
                break;
            case 3 :
                selectedTile.setRotate(270);
                break;
        }
    }
}