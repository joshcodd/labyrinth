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

            Scene scene = new Scene(root, 850, 650);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawGameBoard (){
        Group gameBoardUI = new Group();
        for (int i = 0; i < (gameBoard.getHeight()); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                TileTest tile = new TileTest(gameBoard.getTileAt(j,i), i * gridWidth, j * gridHeight, gridWidth, gridHeight);
                gameBoardUI.getChildren().add(tile);
            }
        }
        controller.getGameBoardPane().getChildren().add(gameBoardUI);
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
