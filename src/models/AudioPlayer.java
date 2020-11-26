package models;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class AudioPlayer {

    MediaPlayer click;
    MediaPlayer backgroundMusic;

    public AudioPlayer() {
        String clickURL = "src/resources/audio/click.mp3";
        Media click = new Media(new File(clickURL).toURI().toString());
        String backgroundURL = "src/resources/audio/background.mp3";
        Media background = new Media(new File(backgroundURL).toURI().toString());
        this.click = new MediaPlayer(click);
        this.backgroundMusic = new MediaPlayer(background);
    }

    public void clickPlay(){
        click.play();
    }

    public MediaPlayer backgroundPlay(){
        backgroundMusic.setOnEndOfMedia(() -> backgroundMusic.seek(Duration.ZERO));
        backgroundMusic.setAutoPlay(true);
        backgroundMusic.setVolume(0.05);
        return backgroundMusic;
    }
}
