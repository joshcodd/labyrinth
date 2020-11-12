package views.scenes;
import controllers.GameController;
import game.GameBoard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class GameScene {
    private double paneWidth = 500;
    private double paneHeight = 500;
    private double gridWidth;
    private double gridHeight;
    private GameController controller;
    private GameBoard gameBoard;
    private Stage primaryStage;

    public GameScene (Stage stage, GameBoard board){
        this.gameBoard = board;
        this.gridWidth = (paneWidth) / gameBoard.getWidth();
        this.gridHeight = (paneHeight) / gameBoard.getHeight();
        this.primaryStage = stage;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = fxmlLoader.load(getClass().getResource("../layouts/gameView.fxml").openStream());
            this.controller = (GameController) fxmlLoader.getController();
            drawGameBoard();

            Scene scene = new Scene(root, 850, 850);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawGameBoard (){
        Group gameBoardUI = new Group();
        Group leftButtons = new Group();
        Group rightButtons = new Group();
        Group topButtons = new Group();
        Group bottomButtons = new Group();
        for (int i = 0; i < (gameBoard.getHeight()); i++) {
            boolean moveableRow = true;
            for (int j = 0; j < gameBoard.getWidth(); j++) {

                TileTest tile = new TileTest(gameBoard.getTileAt(i,j), j * gridWidth, i * gridHeight, gridWidth, gridHeight);
                gameBoardUI.getChildren().add(tile);


                if (gameBoard.getTileAt(i, j).equals("FTile")) {
                    moveableRow = false;
//                    System.out.println(gameBoard.getTileAt(i, j));
//                    System.out.println(i + " " + j);
                }

                if (j == gameBoard.getWidth() - 1) {
//                    System.out.println(i + " " + j);
                    TileTest right = new TileTest("buttonRight" + i, 0,i  * gridWidth, gridWidth, gridHeight);
                    TileTest left = new TileTest("buttonLeft" + i, 0,i  * gridWidth, gridWidth, gridHeight);
                    int finalI = i;
                    left.setOnMouseClicked(event -> {
                        gameBoard.insertTile("ins","LEFT", finalI);
                        drawGameBoard();
                    });

                    right.setOnMouseClicked(event -> {
                        gameBoard.insertTile("ins","RIGHT", finalI);
                        drawGameBoard();
                    });
                    left.setVisible(moveableRow);
                    right.setVisible(moveableRow);
                    rightButtons.getChildren().add(right);
                    leftButtons.getChildren().add(left);
                }
            }
        }

        for (int i = 0; i < (gameBoard.getWidth()); i++) {
            boolean moveableColumn = true;
            int j;
            for (j = 0; j < gameBoard.getHeight(); j++) {
                System.out.println(j + " " + i);

                if (gameBoard.getTileAt(j, i).equals("FTile")) {
                    moveableColumn = false;
                }
            }

            TileTest top = new TileTest("buttonTop" + i, i * gridWidth, 0, gridWidth, gridHeight);
            TileTest bottom = new TileTest("buttonBottom" + i, i * gridWidth, 0, gridWidth, gridHeight);
            int finalI = i;
            top.setOnMouseClicked(event -> {
                gameBoard.insertTile("ins","DOWN", finalI);
                drawGameBoard();
            });
            top.setVisible(moveableColumn);
            bottom.setVisible(moveableColumn);
            bottom.setOnMouseClicked(event -> {
                gameBoard.insertTile("ins","UP", finalI);
                drawGameBoard();
            });
            topButtons.getChildren().add(top);
            bottomButtons.getChildren().add(bottom);
        }
        controller.getGameBoardPane().getChildren().add(gameBoardUI);
        controller.getButtonLeft().getChildren().add(leftButtons);
        controller.getButtonRight().getChildren().add(rightButtons);
        controller.getButtonBottom().getChildren().add(bottomButtons);
        controller.getButtonTop().getChildren().add(topButtons);
    }

    public static class TileTest extends StackPane {
        public TileTest ( String name, double x, double y, double width, double height) {
            Rectangle tile = new Rectangle( width, height);
            tile.setStroke(Color.BLACK);
            tile.setFill(Color.LIGHTBLUE);
            Label label = new Label( name);
            setTranslateX( x);
            setTranslateY( y);
            getChildren().addAll( tile, label);
        }
    }
}
