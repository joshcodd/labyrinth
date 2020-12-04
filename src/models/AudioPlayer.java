package models;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

/**
 * Class created to allow a theme background audio to improve aesthetics.
 * @author
 * @version 1.0
 */
public class AudioPlayer {

    MediaPlayer click; // starts the music
    MediaPlayer backgroundMusic; // music to play

    /**
     * initialises the audio to play in the background
     */
    public AudioPlayer() {
        String clickURL = "src/resources/audio/click.mp3";
        Media click = new Media(new File(clickURL).toURI().toString());
        String backgroundURL = "src/resources/audio/background.mp3";
        Media background = new Media(new File(backgroundURL).toURI().toString());
        this.click = new MediaPlayer(click);
        this.backgroundMusic = new MediaPlayer(background);
    }

    /**
     *  Activates the background music
     */
    public void clickPlay(){
        click.play();
    }

    /**
     * Allows the audion to play continually
     * @return audio for the user to hear
     */
    public MediaPlayer backgroundPlay(){
        backgroundMusic.setOnEndOfMedia(() -> backgroundMusic.seek(Duration.ZERO));
        backgroundMusic.setAutoPlay(true);
        backgroundMusic.setVolume(0.05);
        return backgroundMusic;
    }
}
