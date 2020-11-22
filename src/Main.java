import game.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import views.scenes.GameScene;
import views.scenes.MenuScene;
import views.scenes.SelectPlayerScene;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        //Game game = new Game("src/levels/game", new String[]{"Josh", "Neil", "Andreas"});
        //GameScene gameScene = new GameScene(primaryStage, game);

        MenuScene menu = new MenuScene(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}