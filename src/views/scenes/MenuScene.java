package views.scenes;

import controllers.MenuController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


/**
 * Class that represents the menu game scene.
 * @author Andreas Eleftheriades
 */
public class MenuScene {
        private Stage primaryStage;

    /**
     * Method to construct and initialize a menu scene.
     * @param stage the stage to display this scene.
     * @param backgroundMusic teh games background music will play.
     */
        public MenuScene (Stage stage, MediaPlayer backgroundMusic) {
            this.primaryStage = stage;
            stage.setTitle("Labyrinth by Space Invaders");
            try {
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getClassLoader().getResource("views/layouts/MenuView.fxml").openStream());
                MenuController controller = loader.getController();
                controller.setPrimaryStage(stage);
                controller.setBackgroundMusic(new MediaView(backgroundMusic));
                Scene scene = new Scene(root, 1200, 650);
                scene.getStylesheets().add("styles.css");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (Exception e){
                e.getStackTrace();
            }

        }
    }
