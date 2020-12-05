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

/**
 *  Controller class for the menu. Deals with the buttons, music and
 *  displays message of the day.
 *  Class that represents the menu game scene.
 *  @author Luka Zec and Andreas Eleftheriades
 */
public class MenuController  {
    private final int TITLE_WIDTH = 400;
    private final int TITLE_HEIGHT = 300;
    @FXML
    private Button newGame;
    @FXML
    private Button muteButton;
    @FXML
    private ImageView title;
    @FXML
    private Label message;
    @FXML
    private MediaView backgroundMusic;
    private Stage primaryStage;

    /**
     * Method that initializes game title image and message of the day.
     */
    @FXML
    public void initialize() {
        message.setText(String.valueOf(new MessageOfTheDay()));
        Image titleImage = new Image("/resources/title.png");
        title.setImage(titleImage);
        title.setFitWidth(TITLE_WIDTH);
        title.setFitHeight(TITLE_HEIGHT);
    }

    /**
     * Directs players to an area in which they can start a new game.
     */
    public void handleButtonNewGame() {
        new AudioPlayer().clickPlay();
        newGame.setText("opening");
        new LevelSelectionScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * Directs players to an area in which they can load a game from save file.
     */
    public void handleButtonLoadGame() {
        new AudioPlayer().clickPlay();
        new LoadSaveScene(primaryStage, backgroundMusic
                .getMediaPlayer());
    }

    /**
     * Directs players to the leaderboard.
     */
    public void handleButtonLeaderboard() {
        new AudioPlayer().clickPlay();
            new LeaderboardScene(primaryStage, backgroundMusic
                    .getMediaPlayer());
    }

    /**
     * Directs players to the edit players GUI.
     */
    public void handleButtonEditPlayers() {
        new AudioPlayer().clickPlay();
        new EditPlayersScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * Sets the stage in which the application is being displayed on.
     * @param primaryStage The stage being displayed on.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Sets the media (audio) to be played in the background of the menu.
     * @param backgroundMusic The audio to be played.
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic
                .getMediaPlayer().isMute()));
    }

    /**
     * Mutes the background audio if its currently un-muted, and un-mutes
     * the background audio if it is currently muted.
     */
    public void handleMute() {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic
                .getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic
                .getMediaPlayer().isMute()));
    }
}
