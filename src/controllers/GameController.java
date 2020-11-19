package controllers;
import com.sun.org.apache.xpath.internal.operations.Bool;
import game.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Controller class for GameScene. Runs through a game and then renders changes throughout the game to
 * the view.
 * @author Josh Codd, Neil Woodhouse
 */
public class GameController implements Initializable {

    @FXML
    public HBox bottomButtons;

    @FXML
    public HBox topButtons;

    @FXML
    public Label playerLabel;

    @FXML
    public HBox boardArea;

    @FXML
    public javafx.scene.image.ImageView selectedTile;

    @FXML
    public Button actionButton;

    @FXML
    public Button drawButton;

    @FXML
    private HBox gameBoardPane;

    private GameBoard gameBoard;
    private Player[] players;
    private SimpleDoubleProperty tileSize = new SimpleDoubleProperty(0);
    private Game game;
    GridPane buttonsLeft = new GridPane();
    GridPane buttonsRight = new GridPane();
    GridPane buttonsTop = new GridPane();
    GridPane buttonsBottom = new GridPane();
    GridPane board = new GridPane();
    
    public GameController(Game game) {
        this.players = game.getPlayers();
        this.gameBoard = game.getGameBoard();
        this.game = game;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonsTop.setTranslateX(50);
        buttonsBottom.setTranslateX(50);
        topButtons.getChildren().add(buttonsTop);
        gameBoardPane.getChildren().addAll(buttonsLeft,board,buttonsRight);
        bottomButtons.getChildren().add(buttonsBottom);
        tileSize.bind(boardArea.heightProperty().divide(gameBoard.getHeight() + 2));
        gameBoard.addObserver((o, arg) -> {
            updateGameBoard();
            updateArrows();
            drawPlayers();
        });
    }

    /**
     * Updates the current game board GUI being displayed in the game scene.
     */
    public void updateGameBoard() {
        board.getChildren().clear();
        playerLabel.textProperty().set("Player " + "currentPlayer" + "'s turn");
        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                board.add(new StackPane(getTileImage(i,j)), j, i);
            }
        }
    }

    /**
     * Draws buttons for inserting tiles at the ends of every row or columns where the rows are moveable. That is, there
     * are no fixed tiles in that row or column.
     */
    public void updateArrows() {
        clearArrows();
        ColumnConstraints gridWidth = new ColumnConstraints();
        gridWidth.minWidthProperty().bind(tileSize);

        RowConstraints gridHeight = new RowConstraints();
        gridHeight.minHeightProperty().bind(tileSize);

        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {

                if (j == 0) {
                    buttonsLeft.getRowConstraints().add(gridHeight);
                    ImageView arrow = getArrowImage("LEFT", i);
                    arrow.setVisible(!gameBoard.isRowFixed(i));
                    buttonsLeft.add(new StackPane(arrow), j, i);
                } else if (j == gameBoard.getWidth() - 1) {
                    buttonsRight.getRowConstraints().add(gridHeight);
                    ImageView arrow = getArrowImage("RIGHT", i);
                    arrow.setVisible(!gameBoard.isRowFixed(i));
                    buttonsRight.add(new StackPane(arrow), j, i);
                }

                if (i == 0) {
                    buttonsTop.getColumnConstraints().add(gridWidth);
                    ImageView arrow = getArrowImage("DOWN", j);
                    arrow.setVisible(!gameBoard.isColumnFixed(j));
                    buttonsTop.add(new StackPane(arrow), j, i);
                } else if (i == gameBoard.getHeight() - 1) {
                    buttonsBottom.getColumnConstraints().add(gridWidth);
                    ImageView arrow = getArrowImage("UP", j);
                    arrow.setVisible(!gameBoard.isColumnFixed(j));
                    buttonsBottom.add(new StackPane(arrow), j, i);
                }
            }
        }
    }

    /**
     * Draws the players currently in the game to the scene at their current positions.
     */
    public void drawPlayers() {
        for (Player player : players) {
            for (Node node : board.getChildren()) {
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
        buttonsLeft.getChildren().clear();
        buttonsLeft.getRowConstraints().clear();
        buttonsRight.getChildren().clear();
        buttonsRight.getRowConstraints().clear();
        buttonsTop.getChildren().clear();
        buttonsTop.getColumnConstraints().clear();
        buttonsBottom.getColumnConstraints().clear();
        buttonsBottom.getChildren().clear();
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
            gameBoard.insertTile(new FloorTile(0, false, ShapeOfTile.CROSSROADS), direction, index);
        });
        return arrow;
    }
}