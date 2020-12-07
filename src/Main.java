import javafx.application.Application;
import javafx.stage.Stage;
import models.AudioPlayer;
import views.scenes.MenuScene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        new MenuScene(primaryStage, new AudioPlayer().backgroundPlay());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
