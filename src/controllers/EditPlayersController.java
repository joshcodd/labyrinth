package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.AudioPlayer;
import models.FileHandler;
import models.PlayerProfile;
import views.scenes.MenuScene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Edit the information of the player.
 * @author ???
 */
public class EditPlayersController implements Initializable {

    @FXML
    public Button backButton;

    @FXML
    public MediaView backgroundMusic;
    @FXML
    public ListView profiles;
    @FXML
    public Button addPlayer;
    @FXML
    public TextField playerName;
    public Button muteButton;
    Stage primaryStage;

    ArrayList<PlayerProfile> playerProfiles;
    public static final String FILE_NOT_FOUND_MESSAGE = "One or more of the required game files could not be loaded. Please verify the integrity of the game files and try again.";

    /**
     * To initialize the player by calling generatePlayer method.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generatePlayers();
    }
    
    /**
     * Method to add the player to the PlayerProfile.
     * @param actionEvent
     * @throws IOException
     */
    public void handleAddPlayerClick(ActionEvent actionEvent) throws IOException {
        new AudioPlayer().clickPlay();
        String name = playerName.getText().toLowerCase();

        if (name.trim().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No name entered.", ButtonType.CLOSE);
            error.showAndWait();
        }else if (profiles.getItems().contains(name)){
            Alert error = new Alert(Alert.AlertType.ERROR, "Profile already exists.", ButtonType.CLOSE);
            error.showAndWait();
        } else {
            PlayerProfile newProfile = new PlayerProfile(name, 0, 0, 0);
            newProfile.save();
            generatePlayers();
            playerName.clear();
        }
    }

    /**
     * To delete player's profile from file.
     * @param actionEvent
     * @throws IOException
     */
    public void handleDeleteClick(ActionEvent actionEvent) throws IOException {
        FileHandler.deleteProfile(profiles.getSelectionModel().getSelectedItem().toString());
        generatePlayers();
    }

    /**
     * * Back to the main menu function.
     * @param actionEvent
     */
    public void handleBack(ActionEvent actionEvent) {
        new AudioPlayer().clickPlay();
        MenuScene menuScene = new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     *To get all the player's name .
     */
    public void generatePlayers() {
        profiles.getItems().clear();
        try {
            for (String player : FileHandler.getAllNames()){
                profiles.getItems().add(player);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mute the background music.
     * @param actionEvent
     */
    public void handleMute(ActionEvent actionEvent) {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic.getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     *  Carries on playing the music from the previous scene
     * @param backgroundMusic the background music to be played.
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    /**
     *  Set the primary stage to have music keep playing.
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

}
