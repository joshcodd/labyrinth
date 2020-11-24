package views.scenes;

import controllers.MenuController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


/**
 * dasdas
 */
public class MenuScene {
        private Stage primaryStage;
        private MediaPlayer mediaPlayer;

        public MenuScene (Stage stage) {
            music();
            this.primaryStage = stage;
            stage.setTitle("Labyrinth by Ravensburger");
            try {
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getClassLoader().getResource("views/layouts/MenuView.fxml").openStream());
                MenuController controller = loader.getController();

                controller.setPrimaryStage(stage);
                Scene scene = new Scene(root, 1200, 650);
                primaryStage.setScene(scene);
                primaryStage.show();

            } catch (Exception e){
                e.getStackTrace();

            }

        }
        public void music(){
            String s = "src/resources/audio/computerNoise1.mp3";
            Media h = new Media(new File(s).toURI().toString());
            mediaPlayer = new MediaPlayer(h);
            mediaPlayer.play();
        }
    }


