package controllers;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import views.scenes.*;

import java.applet.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;

/**
 *  Controller class for MenuScene. Deals with the buttons music and display message of the day
 *  Class that represents the menu game scene.
 *  @author Luka Zec and Andreas Eleftheriades
 *  @studentID 987856 and 1906277
 */
public class MenuController  {
    public static final String FILE_NOT_FOUND_MESSAGE = "One or more of the required game files could not be loaded. Please verify the integrity of the game files and try again.";

    public Button newGame;
    public Button loadGame;

    @FXML
    public Button leaderboard;
    public Button muteButton;
    public ImageView title;
    @FXML
    private Label message;
    @FXML
    private MediaView backgroundMusic;

    private Stage primaryStage;

    /**
     *
     */
    @FXML
    public void initialize(){
        message.setText(String.valueOf(new MessageOfTheDay()));
        Image titleImage = new Image("/resources/title.png");
        title.setImage(titleImage);
        title.setFitWidth(400);
        title.setFitHeight(300);


    }

    /**
     *
     * @throws FileNotFoundException
     */
    public void handleButtonNewGame() throws FileNotFoundException {
        new AudioPlayer().clickPlay();
        newGame.setText("opening");
        new LevelSelectionScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     *
     */
    public void handleButtonLoadGame() {
        new AudioPlayer().clickPlay();
        new LoadSaveScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     *
     */
    public void handleButtonLeaderboard() {
        new AudioPlayer().clickPlay();
            new LeaderboardScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     *
     */
    public void handleButtonEditPlayers() {
        new AudioPlayer().clickPlay();
        new EditPlayersScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * plays the audio in the background
     * @param backgroundMusic
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * silences the audio in the background
     */
    public void handleMute() {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic.getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }
}
