package views.scenes;

import controllers.WinnerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.Constants;
import models.Player;

/**
 * Class that represents the winner of the game scene to be displayed on
 * screen. the game scene is the scene in which the actual game is played.
 * @author Andreas Eleftheriades and James Charnock
 * @version 1.0
 */
public class WinnerScene {
    private Stage primaryStage;
    private WinnerController controller;

    /**
     * Constructs and initializes a winner scene. Then display it on the stage.
     * @param stage The stage to display this scene on.
     * @param winner The winning player.
     * @param backgroundMusic The audio to play in the background.
     */
    public WinnerScene(Stage stage, MediaPlayer backgroundMusic,
                       Player winner) {
        this.primaryStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            this.controller = new WinnerController(winner,
                    primaryStage, backgroundMusic);
            loader.setController(controller);
            Parent root = loader.load(getClass()
                    .getResource("/views/layouts/WinnerView.fxml")
                    .openStream());
            controller.setBackgroundMusic(new MediaView(backgroundMusic));
            Scene scene = new Scene(root, Constants.SCENE_WIDTH,
                    Constants.SCENE_HEIGHT);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "An error was encountered while attempting winner scene.",
                    ButtonType.OK);
            error.showAndWait();
            new MenuScene(stage, backgroundMusic);
        }
    }
}
