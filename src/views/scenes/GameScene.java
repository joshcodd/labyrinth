package views.scenes;
import controllers.GameController;
import game.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

/**
 * Class that represents the main game scene and handles player turn sequence
 * @author Josh Codd, Neil Woodhouse
 */
public class GameScene {
    private GameController controller;
    private GameBoard gameBoard;
    private Stage primaryStage;
    private Player[] players;
    private int numPlayers;
    private TileBag tileBag = new TileBag();
    private boolean gameOver = false;
    private int currentPlayer = 0;
    private SimpleDoubleProperty tileSize = new SimpleDoubleProperty(0);

    /**
     * Method to construct and initialize a game scene.
     * @param stage the stage to display this scene.
     * @param gameFilename the filepath to the game file to be loaded
     * @param playerNames an array of names for registered player profiles
     */
    public GameScene (Stage stage,   String gameFilename, String[] playerNames){
        this.numPlayers = playerNames.length;
        this.players = new Player[numPlayers];
        this.primaryStage = stage;
        for (int i = 0; i < playerNames.length; i++) {
            try {
                PlayerProfile currentProfile = FileHandler.loadProfile(playerNames[i]);
                players[i] = new Player(i, currentProfile);
            } catch (FileNotFoundException e) {
                System.out.println("Error: Player profile file not found. Please check the filepath of the game save files.");
                //TODO Exit to level select screen
            }
        }

        try {
            gameBoard = FileHandler.loadNewGame(gameFilename, players, tileBag);
        } catch(FileNotFoundException e) {
            System.out.println("Error: The specified game file could not be found. Please check that you're providing a filepath to a valid game file location.");
            //TODO Exit to level select scene
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = fxmlLoader.load(getClass().getResource("../layouts/gameView.fxml").openStream());
            this.controller = (GameController) fxmlLoader.getController();
            Scene scene = new Scene(root, 1000, 650);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        drawGameBoard();
        updateButtons();
        drawPlayers();
        tileSize.bind(controller.getBoardArea().heightProperty().divide(gameBoard.getHeight() + 2));
    }


    /**
     * Method to draw the game board to screen.
     */
    public void drawGameBoard () {
        GridPane board = new GridPane();
        GridPane buttonsLeft = new GridPane();
        GridPane buttonsRight = new GridPane();
        GridPane buttonsTop = new GridPane();
        GridPane buttonsBottom = new GridPane();

        controller.getPlayerLabel().textProperty().set("Player " + currentPlayer + "'s turn");

        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                int finalI = i;
                int finalJ = j;

                ImageView arrowHorizontal = new ImageView("/resources/rightArrow.png");
                arrowHorizontal.setFitWidth(50);
                arrowHorizontal.setFitHeight(50);

                ImageView arrowVertical = new ImageView("/resources/rightArrow.png");
                arrowVertical.setFitWidth(50);
                arrowVertical.setFitHeight(50);

                ImageView tile = new ImageView("/resources/" + gameBoard.getTileAt(i, j).isFixed()
                        + gameBoard.getTileAt(i, j).getShape() + ".png");
                tile.fitHeightProperty().bind(tileSize);
                tile.fitWidthProperty().bind(tileSize);

                ColumnConstraints gridWidth = new ColumnConstraints();
                gridWidth.minWidthProperty().bind(tileSize);

                RowConstraints gridHeight = new RowConstraints();
                gridHeight.minHeightProperty().bind(tileSize);

                if (j == 0) {
                    arrowHorizontal.setRotate(180);
                    arrowHorizontal.setOnMouseClicked(event -> {
                        gameBoard.insertTile(new FloorTile(0, false, ShapeOfTile.CROSSROADS), "LEFT", finalI);
                        clearBoard();
                        drawGameBoard();
                        drawPlayers();
                        updateButtons();
                    });
                    buttonsLeft.getRowConstraints().add(gridHeight);
                    buttonsLeft.add(new StackPane(arrowHorizontal), j, i);

                } else if (j == gameBoard.getWidth() - 1) {
                    arrowHorizontal.setOnMouseClicked(event -> {
                        gameBoard.insertTile(new FloorTile(0, false, ShapeOfTile.CROSSROADS), "RIGHT", finalI);
                        clearBoard();
                        drawGameBoard();
                        drawPlayers();
                        updateButtons();
                    });
                    buttonsRight.getRowConstraints().add(gridHeight);
                    buttonsRight.add(new StackPane(arrowHorizontal), j, i);
                }

                if (i == 0) {
                    arrowVertical.setRotate(-90);
                    arrowVertical.setOnMouseClicked(event -> {
                        gameBoard.insertTile(new FloorTile(0, false, ShapeOfTile.CROSSROADS), "DOWN", finalJ);
                        clearBoard();
                        drawGameBoard();
                        drawPlayers();
                        updateButtons();
                    });
                    buttonsTop.getColumnConstraints().add(gridWidth);
                    buttonsTop.add(new StackPane(arrowVertical), j, i);
                } else if (i == gameBoard.getHeight() - 1) {
                    arrowVertical.setRotate(90);
                    arrowVertical.setOnMouseClicked(event -> {
                        gameBoard.insertTile(new FloorTile(0, false, ShapeOfTile.CROSSROADS), "UP", finalJ);
                        clearBoard();
                        drawGameBoard();
                        drawPlayers();
                        updateButtons();
                    });
                    buttonsBottom.getColumnConstraints().add(gridWidth);
                    buttonsBottom.add(new StackPane(arrowVertical), j, i);
                }

                board.add(new StackPane(tile), j, i);
            }
        }

        buttonsTop.setTranslateX(50);
        buttonsBottom.setTranslateX(50);
        controller.getTopButtons().getChildren().add(buttonsTop);
        controller.getGameBoardPane().getChildren().addAll(buttonsLeft, board, buttonsRight);
        controller.getBottomButtons().getChildren().add(buttonsBottom);
    }


    /**
     * Method to draw buttons for inserting tiles at the ends of rows/columns where there are no
     * fixed tiles.
     */
    public void updateButtons() {
        GridPane leftButtons = (GridPane) controller.getGameBoardPane().getChildren().get(0);
        GridPane rightButtons = (GridPane) controller.getGameBoardPane().getChildren().get(2);
        GridPane topButtons = (GridPane) controller.getTopButtons().getChildren().get(0);
        GridPane bottomButtons = (GridPane) controller.getBottomButtons().getChildren().get(0);

        for (int i = 0; i < gameBoard.getWidth(); i++) {
            for (Node node : leftButtons.getChildren()) {
                if (GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == i) {
                    node.setVisible(!gameBoard.isRowFixed(i));
                }
            }

            for (Node node : rightButtons.getChildren()) {
                if (GridPane.getColumnIndex(node) == gameBoard.getWidth() - 1 && GridPane.getRowIndex(node) == i) {
                    node.setVisible(!gameBoard.isRowFixed(i));
                }
            }
        }

        //MAY NEED TO SWAP HEIGHT/WIDTH
        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (Node node : topButtons.getChildren()) {
                if (GridPane.getColumnIndex(node) == i && GridPane.getRowIndex(node) == 0) {
                    node.setVisible(!gameBoard.isColumnFixed(i));
                }
            }

            for (Node node : bottomButtons.getChildren()) {
                if (GridPane.getColumnIndex(node) == i && GridPane.getRowIndex(node) == gameBoard.getHeight() - 1) {
                    node.setVisible(!gameBoard.isColumnFixed(i));
                }
            }
        }
    }

    /**
     * Draws to players to the screen at their current positions.
     */
    public void drawPlayers() {
        GridPane board = (GridPane) controller.getGameBoardPane().getChildren().get(1);
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
     * Removed the game board from the scene graph.
     */
    public void clearBoard() {
        controller.getTopButtons().getChildren().clear();
        controller.getGameBoardPane().getChildren().clear();
        controller.getBottomButtons().getChildren().clear();
    }

    /**
     * Starts the gameplay loop. Stops when a player wins the game.
     */
    public void startGame() {

        while (!gameOver) {
            playerTurn(currentPlayer);
            currentPlayer = (currentPlayer + 1) % numPlayers;
        }
    }

    private void playerTurn(int playerNum) {

    }
}
