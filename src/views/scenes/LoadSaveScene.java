package views.scenes;
import controllers.LevelSelectionController;
import controllers.LoadSaveController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.Constants;

/**
 * Class that represents the main game scene.
 * @author
 * @version 1.0
 */
public class LoadSaveScene {
    private Stage primaryStage;
    private LoadSaveController controller;


    /**
     * Method to construct and initialize a load and save scene.
     * @param stage the stage to display this scene.
     * @param backgroundMusic the games background music will play.
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
            e.printStackTrace();

        }
    }
}
