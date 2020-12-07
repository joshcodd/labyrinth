package controllers;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.AudioPlayer;
import views.scenes.MenuScene;
import views.scenes.SelectPlayerScene;
import static javafx.collections.FXCollections.observableArrayList;
import java.io.File;
import java.util.Objects;

/**
 * Controller class for the level selection scene.
 * Finds and displays level files for players to choose from.
 * @author George Boumphrey
 */
public class LevelSelectionController {
    private final int DOT_TXT = 4;
    @FXML
    private ChoiceBox<String> dropdown;
    @FXML
    private MediaView backgroundMusic;
    @FXML
    private Button muteButton;
    private Stage primaryStage;

    /**
     * Sets choices for which level to select.
     */
    public void setDropdown() {
        dropdown.setItems(getLevels());
    }

    /**
     * Progress to next scene on button press.
     */
    public void handleConfirm() {
        new AudioPlayer().clickPlay();
        String gameName = dropdown.getValue();
        if (gameName != null) {
            new SelectPlayerScene(primaryStage, dropdown.getValue(),
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
        backgroundMusic.getMediaPlayer()
                .setMute(!backgroundMusic.getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-"
                + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * Set background music for this scene.
     * @param backgroundMusic The background audio to use.
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-"
                + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * Set the stage for this scene.
     * @param primaryStage The stage to display.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Create a list of level names from the directory containing level files.
     * @return All level file names.
     */
    private ObservableList<String> getLevels() {
        File folder = new File("src/gamefiles/levels");
        ObservableList<String> listOfFiles = observableArrayList();
        for (File i : Objects.requireNonNull(folder.listFiles())) {
            listOfFiles.add(i.getName().substring(0, i.getName()
                    .length() - DOT_TXT));
        }
        return listOfFiles;
    }
}
