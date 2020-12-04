package views.scenes;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaView;
import models.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.PlayerSelectionController;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;

/**
 * Class that represents a player selection scene, and displays this to screen.
 * This scene is the GUI that allows a user to select players and their colours
 * and then continue to play.
 * @author James Charnock
 */
public class SelectPlayerScene {
    private Stage primaryStage;
    private ArrayList<String> profiles;
    private PlayerSelectionController controller;

    /**
     * Construct and initialize a game scene. Then display it on the stage.
     * @param stage The stage to display this scene on.
     * @param level The level selected to play the game on.
     * @param backgroundMusic The audio to play in the background.
     */
    public SelectPlayerScene (Stage stage, String level, MediaPlayer backgroundMusic) {
        this.primaryStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("views/layouts/PlayerSelectionView.fxml").openStream());
            this.controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setBackgroundMusic(new MediaView(backgroundMusic));
            controller.setColourBoxes();
            controller.setGameName(level);
            Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "An error was encountered while attempting load players.",
                    ButtonType.OK);
            error.showAndWait();
            new MenuScene(stage, backgroundMusic);
        }
    }
}
