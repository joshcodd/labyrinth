import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import models.AudioPlayer;
import views.scenes.LevelSelectionScene;
import views.scenes.MenuScene;

import java.io.FileNotFoundException;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        //MenuScene menu = new MenuScene(primaryStage, new AudioPlayer().backgroundPlay());
        LevelSelectionScene hello = new LevelSelectionScene(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}