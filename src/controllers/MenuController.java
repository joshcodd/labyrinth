package controllers;

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
import views.scenes.SelectPlayerScene;

import java.applet.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;

public class MenuController  {
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
        SelectPlayerScene menu = new SelectPlayerScene(primaryStage);
    }

    public void handleButtonLoadGame(ActionEvent actionEvent) {
        new AudioPlayer().clickPlay();
        loadGame.setText("opening");
        Game game = new Game("src/levels/game", new String[]{"Josh", "Neil", "Andreas"});
        GameScene gameScene = new GameScene(primaryStage, game, backgroundMusic.getMediaPlayer());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }
}
