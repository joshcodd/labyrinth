package controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import models.AudioPlayer;
import models.Game;
import models.MessageOfTheDay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import views.scenes.GameScene;
import views.scenes.LevelSelectionScene;
import views.scenes.SelectPlayerScene;

import java.applet.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;

public class MenuController  {
    public static final String FILE_NOT_FOUND_MESSAGE = "One or more of the required game files could not be loaded. Please verify the integrity of the game files and try again.";

    public Button newGame;
    public Button loadGame;
    @FXML
    private Label message;
    @FXML
    private MediaView backgroundMusic;

    private Stage primaryStage;

    @FXML
    public void initialize(){
        message.setText(String.valueOf(new MessageOfTheDay()));
    }

    public void handleButtonNewGame(ActionEvent actionEvent) throws FileNotFoundException {
        new AudioPlayer().clickPlay();
        newGame.setText("opening");
        LevelSelectionScene levelSelectionScene = new LevelSelectionScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    public void handleButtonLoadGame(ActionEvent actionEvent) {
        new AudioPlayer().clickPlay();
        try {
            loadGame.setText("opening");
            Game game = new Game("src/gamefiles/levels/game", new String[]{"Josh", "Neil", "Andreas"});
            GameScene gameScene = new GameScene(primaryStage, game, backgroundMusic.getMediaPlayer());
        } catch (FileNotFoundException e) {
            loadGame.setText("Load Game");
            loadGame.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR, FILE_NOT_FOUND_MESSAGE, ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }
}
