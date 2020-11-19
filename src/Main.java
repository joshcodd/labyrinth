import game.*;
import javafx.application.Application;
import javafx.stage.Stage;
import views.scenes.GameScene;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game("src/levels/game", new String[]{"Josh", "Neil", "Andreas"});
        GameScene gameScene = new GameScene(primaryStage, game);
    }

    public static void main(String[] args) {
        launch(args);
    }
}