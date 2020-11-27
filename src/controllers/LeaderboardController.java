package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.AudioPlayer;
import models.FileHandler;
import models.Player;
import models.PlayerProfile;
import views.scenes.MenuScene;
import views.scenes.SelectPlayerScene;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardController {

    @FXML
    public Button backButton;
    @FXML
    public ChoiceBox<String> dropdown;
    @FXML
    public MediaView backgroundMusic;
    @FXML
    public ListView leaderboard;
    public Button muteButton;

    ImageView gold = new ImageView(new Image("/resources/gold.png"));
    ImageView silver = new ImageView(new Image("/resources/silver.png"));
    ImageView bronze = new ImageView(new Image("/resources/bronze.png"));


    Stage primaryStage;

    public void setDropdown(){
        dropdown.setItems(getLevels());
    }

    public ObservableList<String> getLevels(){
        File folder = new File("src/gamefiles/levels");
        ObservableList<String> listOfFiles = observableArrayList();
        for (File i : Objects.requireNonNull(folder.listFiles())) {
            listOfFiles.add(i.getName().substring(0, i.getName().length() - 4));
        }
        return listOfFiles;
    }

    public void handleChange(ActionEvent actionEvent) throws FileNotFoundException {
        new AudioPlayer().clickPlay();
        String gameName = dropdown.getValue();
        leaderboard.getItems().clear();
        if (gameName != null){
            ArrayList<PlayerProfile> profiles = new ArrayList<>();
            for (String player : FileHandler.loadLeaderboard(gameName)){
                profiles.add(FileHandler.loadProfile(player));
            }

            profiles.sort(new Comparator<PlayerProfile>() {
                public int compare(PlayerProfile p1, PlayerProfile p2) {
                    return p2.getNumberofWins() - p1.getNumberofWins();
                }
            });

            for (int i = 0; i < profiles.size(); i++){
                PlayerProfile currentProfile = profiles.get(i);
                String playerString = currentProfile.getPlayerName() + " "
                        + currentProfile.getNumberofWins() + " wins.";
                Text playerText = new Text(playerString);

                switch (i){
                    case 0 :
                        gold.setFitHeight(30);
                        gold.setFitWidth(30);
                        playerText.getStyleClass().add("lb-text-win");
                        leaderboard.getItems().add(new HBox(gold, playerText));
                        break;

                    case 1 :
                        silver.setFitHeight(30);
                        silver.setFitWidth(30);
                        playerText.getStyleClass().add("lb-text-win");
                        leaderboard.getItems().add(new HBox(silver, playerText));
                        break;

                    case 2 :
                        bronze.setFitHeight(30);
                        bronze.setFitWidth(30);
                        playerText.getStyleClass().add("lb-text-win");
                        leaderboard.getItems().add(new HBox(bronze, playerText));
                        break;
                    default:
                        playerText.getStyleClass().add("lb-text");
                        HBox cell = new HBox(playerText);
                        cell.setPadding(new Insets(5,0,5,30));
                        leaderboard.getItems().add(cell);
                }
            }
        }
    }

    public void handleBack(ActionEvent actionEvent) {
        new AudioPlayer().clickPlay();
        MenuScene menuScene = new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    public void handleMute(ActionEvent actionEvent) {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic.getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}

