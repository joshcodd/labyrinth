package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.AudioPlayer;
import models.FileHandler;
import models.Game;
import views.scenes.GameScene;
import views.scenes.MenuScene;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Load save controller.
 * Allows the user to select a saved game and then proceed to play it.
 * @author Josh Codd
 */
public class LoadSaveController {
    private final int DOT_TXT = 4;
    @FXML
    private ChoiceBox<String> dropdown;
    @FXML
    private MediaView backgroundMusic;
    @FXML
    private Button muteButton;
    private Stage primaryStage;

    /**
     * Sets the dropdown box to include all saved game file names.
     */
    public void setDropdown() {
        dropdown.setItems(getSaves());
    }

    /**
     * Creates a game from the selected filename and proceeds to play.
     * @throws FileNotFoundException If the game file does not exist.
     */
    public void handleConfirm() throws FileNotFoundException {
        new AudioPlayer().clickPlay();
        String saveName = dropdown.getValue();
        if (saveName != null) {
            Game loadedGame = FileHandler.continueGame(saveName);
            new GameScene(primaryStage, loadedGame,
                    backgroundMusic.getMediaPlayer());
        }
    }

    /**
     * Returns back to the main menu.
     */
    public void handleBack() {
        new AudioPlayer().clickPlay();
        new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * Mutes the background audio if its currently un-muted, and un-mutes
     * the background audio if it is currently muted.
     */
    public void handleMute() {
        new AudioPlayer().clickPlay();
        backgroundMusic.getMediaPlayer()
                .setMute(!backgroundMusic.getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic
                .getMediaPlayer().isMute()));
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

    /**
     * Sets the stage in which the application is being displayed on.
     * @param primaryStage The stage being displayed on.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Gets the names of all saved game files.
     * @return All saved game file names.
     */
    private ObservableList<String> getSaves() {
        File folder = new File("source/gamefiles/saves");
        ObservableList<String> listOfFiles = observableArrayList();
        for (File i : Objects.requireNonNull(folder.listFiles())) {
            listOfFiles.add(i.getName().substring(0, i.getName()
                    .length() - DOT_TXT));
        }
        return listOfFiles;
    }
}
