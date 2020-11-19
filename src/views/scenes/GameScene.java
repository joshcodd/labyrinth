package views.scenes;
import controllers.GameController;
import game.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Class that represents the main game scene.
 * @author Josh Codd, Neil Woodhouse
 */
public class GameScene {
    private GameController controller;
    private Stage primaryStage;

    /**
     * Method to construct and initialize a game scene.
     * @param stage the stage to display this scene.
     * @param game the game that this scene will play.
     */
    public GameScene (Stage stage, Game game){
        this.primaryStage = stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            this.controller = new GameController(game);
            fxmlLoader.setController(controller);
            Pane root = fxmlLoader.load(getClass().getResource("../layouts/gameView.fxml").openStream());
            Scene scene = new Scene(root, 1000, 650);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        controller.updateGameBoard();
        controller.drawPlayers();
        controller.updateArrows(false);
    }
}