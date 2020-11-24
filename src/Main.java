import javafx.application.Application;
import javafx.stage.Stage;
import views.scenes.MenuScene;

import java.io.FileNotFoundException;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        MenuScene menu = new MenuScene(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}