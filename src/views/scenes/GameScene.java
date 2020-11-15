package views.scenes;
import controllers.GameController;
import game.FloorTile;
import game.GameBoard;
import game.ShapeOfTile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * Class that represents the main game scene.
 * @author Josh Codd
 */
public class GameScene {
    private GameController controller;
    private GameBoard gameBoard;
    private Stage primaryStage;


    /**
     * Method to construct and initialize a game scene.
     * @param stage the stage to display this scene.
     * @param board GOING TO CHANGE WHEN GAME LOGIC IS COMPLETE.
     */
    public GameScene (Stage stage, GameBoard board){
        this.gameBoard = board;
        this.primaryStage = stage;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = fxmlLoader.load(getClass().getResource("../layouts/gameView.fxml").openStream());
            this.controller = (GameController) fxmlLoader.getController();
            drawGameBoard();
            updateButtons();
            Scene scene = new Scene(root, 1000, 650);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        //board.setPrefSize(0, 0);

        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                int finalI = i;
                int finalJ = j;

                ImageView arrowHorizontal = new ImageView("/resources/arrow.png");
                arrowHorizontal.setFitHeight(50);
                arrowHorizontal.setFitWidth(50);

                ImageView arrowVertical = new ImageView("/resources/arrow.png");
                arrowVertical.setFitHeight(50);
                arrowVertical.setFitWidth(50);

                ImageView tile = new ImageView("/resources/" + gameBoard.getTileAt(i, j).isFixed()
                        + gameBoard.getTileAt(i, j).getShape() + ".png");
                tile.setFitHeight(50);
                tile.setFitWidth(50);

                if (j == 0) {
                    arrowHorizontal.setRotate(180);
                    arrowHorizontal.setOnMouseClicked(event -> {
                        gameBoard.insertTile(new FloorTile(0, false, ShapeOfTile.CROSSROADS), "LEFT", finalI);
                        clearBoard();
                        drawGameBoard();
                        updateButtons();
                    });
                    buttonsLeft.add(new StackPane(arrowHorizontal), j, i);

                } else if (j == gameBoard.getWidth() - 1) {
                    arrowHorizontal.setOnMouseClicked(event -> {
                        gameBoard.insertTile(new FloorTile(0, false, ShapeOfTile.CROSSROADS), "RIGHT", finalI);
                        clearBoard();
                        drawGameBoard();
                        updateButtons();
                    });
                    buttonsRight.add(new StackPane(arrowHorizontal), j, i);
                }


                if (i == 0) {
                    arrowVertical.setRotate(-90);
                    arrowVertical.setOnMouseClicked(event -> {
                        gameBoard.insertTile(new FloorTile(0, false, ShapeOfTile.CROSSROADS), "DOWN", finalJ);
                        clearBoard();
                        drawGameBoard();
                        updateButtons();
                    });
                    buttonsTop.add(new StackPane(arrowVertical), j, i);
                } else if (i == gameBoard.getHeight() - 1) {
                    arrowVertical.setRotate(90);
                    arrowVertical.setOnMouseClicked(event -> {
                        gameBoard.insertTile(new FloorTile(0, false, ShapeOfTile.CROSSROADS), "UP", finalJ);
                        clearBoard();
                        drawGameBoard();
                        updateButtons();
                    });
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
     * Removed the game board from the scene graph.
     */
    public void clearBoard() {
        controller.getTopButtons().getChildren().clear();
        controller.getGameBoardPane().getChildren().clear();
        controller.getBottomButtons().getChildren().clear();
    }
}
