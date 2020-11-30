import controllers.LoadSaveController;
import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import models.AudioPlayer;
import views.scenes.LevelSelectionScene;
import views.scenes.LoadSaveScene;
import views.scenes.MenuScene;
import views.scenes.SelectPlayerScene;

import java.io.FileNotFoundException;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) {
        //LoadSaveScene test = new LoadSaveScene(primaryStage, new AudioPlayer().backgroundPlay() );
        MenuScene menu = new MenuScene(primaryStage, new AudioPlayer().backgroundPlay());
    }

    public static void main(String[] args) {
        launch(args);
    }
}