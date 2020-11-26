package views.scenes;

import controllers.LeaderboardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class LeaderboardScene {
    private Stage primaryStage;

    public LeaderboardScene(Stage stage, MediaPlayer backgroundMusic){
        this.primaryStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("views/layouts/LeaderboardView.fxml").openStream());
            LeaderboardController controller = loader.getController();
            controller.setBackgroundMusic(new MediaView(backgroundMusic));
            controller.setPrimaryStage(stage);
            controller.setDropdown();
            Scene scene = new Scene(root, 1200, 650);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
