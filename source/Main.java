import javafx.application.Application;
import javafx.stage.Stage;
import models.AudioPlayer;
import views.scenes.MenuScene;

/**
 * Runs the game.
 * @author Josh Codd
 */
public class Main extends Application {

    /**
     * Starts the application and displays on screen.
     * @param primaryStage The stage the display the application on.
     */
    @Override
    public void start(Stage primaryStage) {
        new MenuScene(primaryStage, new AudioPlayer().backgroundPlay());
    }

    /**
     * Runs the game.
     * @param args Options.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
