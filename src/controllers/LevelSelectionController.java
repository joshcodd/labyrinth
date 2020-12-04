package controllers;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.AudioPlayer;
import views.scenes.MenuScene;
import views.scenes.SelectPlayerScene;

import static javafx.collections.FXCollections.observableArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * Controller class for the level selection scene.
 * Finds and displays level files for players to choose from.
 * @author George Boumphrey
 */
public class LevelSelectionController {

    @FXML
    public Button backButton;

    @FXML
    public Button confirmButton;

    @FXML
    public ChoiceBox<String> dropdown;
    @FXML
    public MediaView backgroundMusic;
    public Button muteButton;

    Stage primaryStage;

    /**
     * Method to set choices for which level to select.
     */
    public void setDropdown(){
        dropdown.setItems(getLevels());
    }

    /**
     * Create a list of level names from the directory containing level files.
     * @return A list of level file names.
     */
    public ObservableList<String> getLevels(){
        File folder = new File("src/gamefiles/levels");
        ObservableList<String> listOfFiles = observableArrayList();
        for (File i : Objects.requireNonNull(folder.listFiles())) {
            listOfFiles.add(i.getName().substring(0, i.getName().length() - 4));
        }
        return listOfFiles;
    }

    /**
     * Progress to next scene on button press.
     * @param actionEvent
     * @throws FileNotFoundException
     */
    public void handleConfirm(ActionEvent actionEvent) throws FileNotFoundException {
        new AudioPlayer().clickPlay();
        String gameName = dropdown.getValue();
        if (gameName != null){
            new SelectPlayerScene(primaryStage, dropdown.getValue(), backgroundMusic.getMediaPlayer());
        }
    }

    /**
     * Return to main menu on button press.
     * @param actionEvent
     */
    public void handleBack(ActionEvent actionEvent) {
        new AudioPlayer().clickPlay();
        MenuScene menuScene = new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * Mute background music on button press.
     * @param actionEvent
     */
    public void handleMute(ActionEvent actionEvent) {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic.getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * Set background music for this scene.
     * @param backgroundMusic The background audio to use.
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * Set the stage for this scene.
     * @param primaryStage The stage to display.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}

