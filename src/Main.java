import game.*;
import javafx.application.Application;
import javafx.stage.Stage;
import views.scenes.GameScene;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) {
        GameScene game = new GameScene(primaryStage, "src/levels/game", new String[]{"Josh", "Neil", "Andreas"});
        //gametest.startGame();

    }

    public static void main(String[] args) {
        launch(args);
    }
}