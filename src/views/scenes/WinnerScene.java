package views.scenes;

import controllers.EditPlayersController;
import controllers.GameController;
import controllers.MenuController;
import controllers.WinnerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.Constants;
import models.Player;

public class WinnerScene {
    private Stage primaryStage;
    private WinnerController controller;
    /**
     * Constructs and initializes a game scene. Then display it on the stage.
     * @param stage The stage to display this scene on.
     * @param winner The winning player.
     * @param backgroundMusic The audio to play in the background.
     */
    public WinnerScene(Stage stage, MediaPlayer backgroundMusic, Player winner) {
        this.primaryStage = stage;
        try {

            FXMLLoader loader = new FXMLLoader();
            this.controller = new WinnerController(winner, primaryStage, backgroundMusic);
            //this.controller = loader.getController();
            Parent root = loader.load(getClass().getClassLoader()
                    .getResource("views/layouts/WinnerView.fxml")
                    .openStream());


            //controller.setPrimaryStage(primaryStage);
           // controller.setBackgroundMusic(new MediaView(backgroundMusic));


            Scene scene = new Scene(root, Constants.SCENE_WIDTH,
                    Constants.SCENE_HEIGHT);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println(e);
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "An error was encountered while attempting winner scene.",
                    ButtonType.OK);
            //error.showAndWait();
           // new MenuScene(stage, backgroundMusic);
        }
    }
}
