package views.scenes;
import controllers.GameController;
import game.GameBoard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GameScene {
    private GameController controller;
    private GameBoard gameBoard;
    private Stage primaryStage;

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

                Rectangle tile = new Rectangle(50, 50);
                tile.setFill(Color.BURLYWOOD);
                tile.setStroke(Color.BLACK);

                Rectangle button = new Rectangle(50, 50);
                button.setStroke(Color.BLACK);
                button.setFill(Color.WHITE);

                if (j == 0) {
                    button.setOnMouseClicked(event -> {
                        gameBoard.insertTile("ins", "LEFT", finalI);
                        clearBoard();
                        drawGameBoard();
                        updateButtons();
                    });

                    buttonsLeft.add(new StackPane(button, new Text("butL")), j, i);
                } else if (j == gameBoard.getWidth() - 1) {
                    button.setOnMouseClicked(event -> {
                        gameBoard.insertTile("ins", "RIGHT", finalI);
                        clearBoard();
                        drawGameBoard();
                        updateButtons();
                    });

                    buttonsRight.add(new StackPane(button, new Text("butR")), j, i);
                }

                Rectangle button1 = new Rectangle(50, 50);
                button1.setStroke(Color.BLACK);
                button1.setFill(Color.WHITE);
                if (i == 0) {
                    button1.setOnMouseClicked(event -> {
                        gameBoard.insertTile("ins", "DOWN", finalJ);
                        clearBoard();
                        drawGameBoard();
                        updateButtons();
                    });

                    buttonsTop.add(new StackPane(button1, new Text("butT")), j, i);
                } else if (i == gameBoard.getHeight() - 1) {
                    button1.setOnMouseClicked(event -> {
                        gameBoard.insertTile("ins", "UP", finalJ);
                        clearBoard();
                        drawGameBoard();
                        updateButtons();
                    });

                    buttonsBottom.add(new StackPane(button1, new Text("butB")), j, i);
                }

                board.add(new StackPane(tile, new Text(gameBoard.getTileAt(i, j))), j, i);
            }
        }
        buttonsTop.setTranslateX(51);
        buttonsBottom.setTranslateX(51);
        controller.getTopButtons().getChildren().add(buttonsTop);
        controller.getGameBoardPane().getChildren().addAll(buttonsLeft, board, buttonsRight);
        controller.getBottomButtons().getChildren().add(buttonsBottom);
    }

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

    public void clearBoard() {
        controller.getTopButtons().getChildren().clear();
        controller.getGameBoardPane().getChildren().clear();
        controller.getBottomButtons().getChildren().clear();
    }
}
