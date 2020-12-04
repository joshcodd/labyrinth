package views.scenes;
import controllers.GameController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import models.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Class that represents the main game scene to be displayed on screen.
 * The game scene is the scene in which the actual game is played.
 * @author Josh Codd, Neil Woodhouse
 */
public class GameScene {
    private GameController controller;
    private Stage primaryStage;

    /**
     * Constructs and initializes a game scene. Then display it on the stage.
     * @param stage The stage to display this scene on.
     * @param game The game to play.
     * @param backgroundMusic The audio to play in the background.
     */
    public GameScene (Stage stage, Game game, MediaPlayer backgroundMusic){
        this.primaryStage = stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            this.controller = new GameController(game, stage, backgroundMusic);
            fxmlLoader.setController(controller);
            Pane root = fxmlLoader.load(getClass().getResource("../layouts/gameView.fxml").openStream());
            Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            new MenuScene(stage, backgroundMusic);
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "An error was encountered while attempting load game.",
                    ButtonType.OK);
            error.showAndWait();
        }

        controller.updateGameBoard();
        controller.drawPlayers();
        boolean isMidTurn = (game.getCurrentTile() != null && game.getCurrentTile() instanceof FloorTile);
        controller.updateArrows(isMidTurn);
    }
}