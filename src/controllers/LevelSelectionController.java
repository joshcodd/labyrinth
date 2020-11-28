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
 *
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
     *
     */
    public void setDropdown(){
        dropdown.setItems(getLevels());
    }

    /**
     * @return
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
     * @param actionEvent
     */
    public void handleBack(ActionEvent actionEvent) {
        new AudioPlayer().clickPlay();
        MenuScene menuScene = new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * @param actionEvent
     */
    public void handleMute(ActionEvent actionEvent) {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic.getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * @param backgroundMusic
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}

