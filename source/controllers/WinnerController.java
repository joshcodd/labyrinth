package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.AudioPlayer;
import models.Player;
import views.scenes.MenuScene;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for WinnerScene. Displays the winner and tank
 * also displays the button functionality
 * @author Andreas Eleftheriades
 * @version 1.0
 */
public class WinnerController implements Initializable {
    private final int TANK_SIZE = 60;
    @FXML
    private ImageView tankView;
    @FXML
    private Button muteButton;
    @FXML
    private Label winLabel;
    @FXML
    private Button menuButton;
    @FXML
    private MediaView backgroundMusic;
    private Player winner;
    private Stage primaryStage;
    private MediaPlayer mediaPlayer;

    /**
     * Constructs a WinnerController.
     * @param winner The winner of the game.
     * @param stage The stage that this application is being displayed on.
     * @param backgroundMusic The audio to play in the background.
     */
    public WinnerController(Player winner, Stage stage,
                            MediaPlayer backgroundMusic) {
        this.winner = winner;
        this.primaryStage = stage;
        this.mediaPlayer = backgroundMusic;
    }

    /**
     * Initializes the winner of the game with tank
     * @param location The location used to resolve relative paths for the
     *                 root object, or null if the location is not known.
     * @param resources The resources used to localize the root object,
     *                  or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        muteButton.setOnMouseClicked((event) -> handleMute());
        menuButton.setOnMouseClicked((event) -> handleMenu());
        this.winLabel.setText(winner.getProfileName() + " is the winner!");
        updateTankView();
    }

    /**
     * Sets player tank images to the correct tank and colour.
     */
    private void updateTankView() {
        tankView.setImage(new Image("resources/"
                + winner.getColour() + ".png"));
        tankView.setFitHeight(TANK_SIZE);
        tankView.setFitWidth(TANK_SIZE);
    }

    /**
     * Mutes the background audio if its currently un-muted, and un-mutes
     * the background audio if it is currently muted.
     */
    private void handleMute() {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic
                .getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic
                .getMediaPlayer().isMute()));
    }

    /**
     * Returns back to the main menu.
     */
    private void handleMenu() {
        new AudioPlayer().clickPlay();
        new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * Sets the media (audio) to be played in the background of the menu.
     * @param backgroundMusic The audio to be played.
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-"
                + backgroundMusic.getMediaPlayer().isMute()));
    }
}
