package views.scenes;
/**
 * Player Selection scene class
 * @author James Charnock
 * @StudentID 1909700
 */


import javafx.scene.media.MediaView;
import models.Constants;
import models.FileHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.PlayerSelectionController;
import javafx.scene.media.MediaPlayer;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 */
public class SelectPlayerScene {

    private Stage primaryStage;
    private ArrayList<String> profiles;
    private PlayerSelectionController controller;


    /**
     * @param stage
     * @param level
     * @param backgroundMusic
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
            e.printStackTrace();

        }
    }
}
