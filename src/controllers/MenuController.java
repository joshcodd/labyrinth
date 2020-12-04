package controllers;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import models.AudioPlayer;
import models.MessageOfTheDay;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import views.scenes.*;
import java.io.FileNotFoundException;


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
     * Method that initializes game title image and message of the day
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
     * Method that deals with new game button actions
     * @throws FileNotFoundException
     */
    public void handleButtonNewGame() throws FileNotFoundException {
        new AudioPlayer().clickPlay();
        newGame.setText("opening");
        new LevelSelectionScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * Method that deals with load game button
     */
    public void handleButtonLoadGame() {
        new AudioPlayer().clickPlay();
        new LoadSaveScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     *  Method that deals with leaderboard button
     */
    public void handleButtonLeaderboard() {
        new AudioPlayer().clickPlay();
            new LeaderboardScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * Method that deals with edit players button
     */
    public void handleButtonEditPlayers() {
        new AudioPlayer().clickPlay();
        new EditPlayersScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * Method to set a primary stage
     * @param primaryStage Stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * plays the audio in the background
     * @param backgroundMusic MediaView
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
