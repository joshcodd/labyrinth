package views.scenes;
import controllers.LoadSaveController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.Constants;

/**
 * Class that represents a load save scene, and displays this to screen.
 * The load save scene is the GUI that allows a user to pick a previously saved game
 * to play.
 * @author Josh Codd
 */
public class LoadSaveScene {
    private Stage primaryStage;
    private LoadSaveController controller;

    /**
     * Constructs and initializes a load save scene. Then display it on the stage.
     * @param stage The stage to display this scene on.
     * @param backgroundMusic The audio to play in the background.
     */
    public LoadSaveScene(Stage stage, MediaPlayer backgroundMusic){
        this.primaryStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("views/layouts/LoadSaveView.fxml").openStream());
            LoadSaveController controller = loader.getController();
            controller.setBackgroundMusic(new MediaView(backgroundMusic));
            controller.setPrimaryStage(stage);
            controller.setDropdown();
            Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "An error was encountered while attempting load saves.",
                    ButtonType.OK);
            error.showAndWait();
            new MenuScene(stage, backgroundMusic);
        }
    }
}
