package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.AudioPlayer;
import models.FileHandler;
import models.PlayerProfile;
import views.scenes.MenuScene;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Edit players controller.
 * Allows the user to add, view as well as delete player profiles.
 * @author Josh Codd, Chi Hang Tse
 */
public class EditPlayersController implements Initializable {
    @FXML
    private MediaView backgroundMusic;
    @FXML
    private ListView<String> profiles;
    @FXML
    private TextField playerName;
    @FXML
    private Button muteButton;
    @FXML
    private Stage primaryStage;

    /**
     * Initializes the player editing GUI so that it's ready to be displayed.
     * @param location The location used to resolve relative paths for the
     *                 root object, or null if the location is not known.
     * @param resources The resources used to localize the root object,
     *                  or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generatePlayers();
    }
    
    /**
     * Handles add player button click by creating and saving a new player, with
     * the name specified.
     */
    public void handleAddPlayerClick() {
        new AudioPlayer().clickPlay();
        String name = playerName.getText().toLowerCase();
        if (name.trim().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "No name entered.", ButtonType.CLOSE);
            error.showAndWait();
        } else if (profiles.getItems().contains(name)) {
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "Profile already exists.", ButtonType.CLOSE);
            error.showAndWait();
        } else {
            PlayerProfile newProfile = new PlayerProfile(name, 0, 0, 0);
            try {
                newProfile.save();
            } catch (IOException e) {
                Alert error = new Alert(Alert.AlertType.ERROR,
                        "Cannot save player.", ButtonType.CLOSE);
                error.showAndWait();
            }
            generatePlayers();
            playerName.clear();
        }
    }

    /**
     * Deletes a selected player from file.
     */
    public void handleDeleteClick() {
        try {
            FileHandler.deleteProfile(profiles.getSelectionModel()
                    .getSelectedItem());
        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "Cannot delete player.", ButtonType.CLOSE);
            error.showAndWait();
        }
        generatePlayers();
    }

    /**
     * Returns back to the menu.
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
     * Sets the media (audio) to be played in the background of the menu.
     * @param backgroundMusic The audio to be played.
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    /**
     * Sets the stage in which the application is being displayed on.
     * @param primaryStage The stage being displayed on.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        muteButton.getStyleClass().set(0,
                ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * Gets all saved players.
     */
    private void generatePlayers() {
        profiles.getItems().clear();
        try {
            for (String player : FileHandler.getAllNames()) {
                profiles.getItems().add(player);
            }
        } catch (FileNotFoundException e) {
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "Cannot load players.", ButtonType.CLOSE);
            error.showAndWait();
        }
    }

}
