package controllers;

import com.sun.deploy.util.StringUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.AudioPlayer;
import models.FileHandler;
import models.PlayerProfile;
import views.scenes.MenuScene;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.scene.paint.Color.TRANSPARENT;
import static javafx.scene.paint.Color.WHITE;

/**
 * Leaderboard controller.
 * @author 
 * @studentID 
 */
public class LeaderboardController {

    @FXML
    public Button backButton;
    @FXML
    public ChoiceBox<String> dropdown;
    @FXML
    public MediaView backgroundMusic;
    @FXML
    public ListView<HBox> leaderboard;
    public Button muteButton;

    ImageView gold = new ImageView(new Image("/resources/gold.png"));
    ImageView silver = new ImageView(new Image("/resources/silver.png"));
    ImageView bronze = new ImageView(new Image("/resources/bronze.png"));

    Stage primaryStage;

    /**
     * The dropdown menu to display the level.
     */
    public void setDropdown(){
        dropdown.setItems(getLevels());
    }

    /**
     * To show the information by level.
     * @return listFoFiles
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
     * To get who is the gold silver and bronze by players, and also display the number of wins for others .
     * @param actionEvent
     * @throws FileNotFoundException
     */
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
                    return p2.getNumberOfWins() - p1.getNumberOfWins();
                }
            });

            for (int i = 0; i < profiles.size(); i++){
                PlayerProfile currentProfile = profiles.get(i);


                String name = currentProfile.getPlayerName();
                String space = new String(new char[10 - name.length()]).replace("\0", "0");
                int wins = currentProfile.getNumberOfWins();
                int losses = currentProfile.getNumberOfLosses();
                int games = currentProfile.getNumberOfGamesPlayed();

                Text playerText = new Text(name);
                Text whitespace = new Text(space);
                whitespace.setFill(TRANSPARENT);
                Text stats = new Text("| " + wins + " wins | " + losses + " losses | " + games + " games played");

                switch (i){
                    case 0 :
                        gold.setFitHeight(30);
                        gold.setFitWidth(30);
                        playerText.getStyleClass().add("lb-text-win");
                        whitespace.getStyleClass().add("lb-text-win");
                        stats.getStyleClass().add("lb-text-win");
                        leaderboard.getItems().add(new HBox(gold, playerText, whitespace,stats));
                        break;

                    case 1 :
                        silver.setFitHeight(30);
                        silver.setFitWidth(30);
                        whitespace.getStyleClass().add("lb-text-win");
                        stats.getStyleClass().add("lb-text-win");
                        playerText.getStyleClass().add("lb-text-win");
                        leaderboard.getItems().add(new HBox(silver, playerText, whitespace,stats));
                        break;

                    case 2 :
                        bronze.setFitHeight(30);
                        bronze.setFitWidth(30);
                        whitespace.getStyleClass().add("lb-text-win");
                        stats.getStyleClass().add("lb-text-win");
                        playerText.getStyleClass().add("lb-text-win");
                        leaderboard.getItems().add(new HBox(bronze, playerText, whitespace,stats));
                        break;
                    default:
                        whitespace.getStyleClass().add("lb-text");
                        stats.getStyleClass().add("lb-text");
                        playerText.getStyleClass().add("lb-text");
                        HBox cell = new HBox(playerText, whitespace,stats);
                        cell.setPadding(new Insets(5,0,5,30));
                        leaderboard.getItems().add(cell);
                }
            }
        }
    }

    /**
     * Back to the main menu scene.
     * @param actionEvent
     */
    public void handleBack(ActionEvent actionEvent) {
        new AudioPlayer().clickPlay();
        MenuScene menuScene = new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
    }

    /**
     * Mute the music.
     * @param actionEvent
     */
    public void handleMute(ActionEvent actionEvent) {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic.getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * Set the background music keeps playing.
     * @param backgroundMusic
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * Set the primary stage.
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}

