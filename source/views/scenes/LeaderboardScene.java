package views.scenes;
import controllers.LeaderboardController;
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
 * Class that represents a leaderboard scene, and displays this to screen.
 * The leaderboard scene is the scene allows the user to select a level name
 * and view the leaderboard for that level.
 * @author Josh Codd
 */
public class LeaderboardScene {
    private Stage primaryStage;

    /**
     * Constructs and initializes a leaderboard scene. Then display it on the
     * stage.
     * @param stage The stage to display this scene on.
     * @param backgroundMusic The audio to play in the background.
     */
    public LeaderboardScene(Stage stage, MediaPlayer backgroundMusic) {
        this.primaryStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader()
                    .getResource("views/layouts/LeaderboardView.fxml")
                    .openStream());
            LeaderboardController controller = loader.getController();
            controller.setBackgroundMusic(new MediaView(backgroundMusic));
            controller.setPrimaryStage(stage);
            controller.setDropdown();
            Scene scene = new Scene(root, Constants.SCENE_WIDTH,
                    Constants.SCENE_HEIGHT);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "An error was encountered while attempting to load "
                            + "leaderboard.",
                    ButtonType.OK);
            error.showAndWait();
            new MenuScene(stage, backgroundMusic);
        }
    }
}
