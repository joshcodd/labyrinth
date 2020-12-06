import javafx.application.Application;
import javafx.stage.Stage;
import models.AudioPlayer;
import models.Player;
import models.PlayerProfile;
import views.scenes.MenuScene;
import views.scenes.WinnerScene;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) {
        //MenuScene menu = new MenuScene(primaryStage, new AudioPlayer().backgroundPlay());
        Player player = new Player(2, new PlayerProfile("james", 0, 0,
       0));
        player.setColour("Blue");
       WinnerScene winn = new WinnerScene(primaryStage, new AudioPlayer().backgroundPlay(),player);
    }

    public static void main(String[] args) {
        launch(args);
    }
}