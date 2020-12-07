package views.scenes;

import controllers.EditPlayersController;
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
 * Class that represents an edit players scene.
 * Generates the GUI for viewing, adding and deleting players.
 * @author Josh Codd
 */
public class EditPlayersScene {
    private Stage primaryStage;

    /**
     * Constructs and initializes a edit players scene. Then display it on
     * the stage.
     * @param stage The stage to display this scene on.
     * @param backgroundMusic The audio to play in the background.
     */
    public EditPlayersScene(Stage stage, MediaPlayer backgroundMusic) {
        this.primaryStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader()
                    .getResource("views/layouts/EditPlayersView.fxml")
                    .openStream());
            EditPlayersController controller = loader.getController();
            controller.setBackgroundMusic(new MediaView(backgroundMusic));
            controller.setPrimaryStage(stage);
            Scene scene = new Scene(root, Constants.SCENE_WIDTH,
                    Constants.SCENE_HEIGHT);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "An error was encountered while attempting load players.",
                    ButtonType.OK);
            error.showAndWait();
            new MenuScene(stage, backgroundMusic);
        }
    }
}
