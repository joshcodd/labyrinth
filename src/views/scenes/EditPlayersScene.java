package views.scenes;

import controllers.EditPlayersController;
import controllers.LeaderboardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class EditPlayersScene {
    private Stage primaryStage;

    public EditPlayersScene(Stage stage, MediaPlayer backgroundMusic){
        this.primaryStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("views/layouts/EditPlayersView.fxml").openStream());
            EditPlayersController controller = loader.getController();
            controller.setBackgroundMusic(new MediaView(backgroundMusic));
            controller.setPrimaryStage(stage);
            Scene scene = new Scene(root, 1200, 650);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
