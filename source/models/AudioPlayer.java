package models;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;

/**
 * Represents the audio that is used throughout the game.
 * Consists of background music, as well as button click sounds.
 * @author Josh Codd
 * @version 1.0
 */
public class AudioPlayer {
    private final double BACKGROUND_MUSIC_VOLUME = 0.05;
    private MediaPlayer click; // Button click
    private MediaPlayer backgroundMusic;

    /**
     * Creates and initializes the audio to ensure it is ready for use.
     */
    public AudioPlayer() {
        String clickURL = "source/resources/audio/click.mp3";
        Media click = new Media(getClass()
                .getResource(clickURL).toExternalForm());
        String backgroundURL = "source/resources/audio/background.mp3";
        Media background = new Media(getClass()
                .getResource(backgroundURL).toExternalForm());
        this.click = new MediaPlayer(click);
        this.backgroundMusic = new MediaPlayer(background);
    }

    /**
     *  Activates the background music.
     */
    public void clickPlay() {
        click.play();
    }

    /**
     * Initialises the background music to loop, and returns it.
     * @return The background music to play in scenes.
     */
    public MediaPlayer backgroundPlay() {
        // On end of track, restart.
        backgroundMusic.setOnEndOfMedia(() -> backgroundMusic
                .seek(Duration.ZERO));
        backgroundMusic.setAutoPlay(true);
        backgroundMusic.setVolume(BACKGROUND_MUSIC_VOLUME);
        return backgroundMusic;
    }
}
