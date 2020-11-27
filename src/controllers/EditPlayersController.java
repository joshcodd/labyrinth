package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    Stage primaryStage;

    ArrayList<PlayerProfile> playerProfiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generatePlayers();
    }
    public static final String FILE_NOT_FOUND_MESSAGE = "One or more of the required game files could not be loaded. Please verify the integrity of the game files and try again.";

    public void handleAddPlayerClick(ActionEvent actionEvent) throws IOException {
        new AudioPlayer().clickPlay();
        String name = playerName.getText();

        if (name == null || name.trim().isEmpty()) {
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

    public void handleBack(ActionEvent actionEvent) {
        new AudioPlayer().clickPlay();
        MenuScene menuScene = new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

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

    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
