package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import models.PlayerProfile;
import views.scenes.MenuScene;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.scene.paint.Color.TRANSPARENT;

/**
 * Leader board controller.
 * Allows the user to view the leader boards for each level.
 * @author Josh Codd
 */
public class LeaderboardController {
    private final int DOT_TXT = 4;
    private final int FORMAT_PADDING = 10;
    //Medal images for 1st, 2nd and 3rd place.
    private final ImageView GOLD_MEDAL
            = new ImageView(new Image("/resources/gold.png"));
    private final ImageView SILVER_MEDAL
            = new ImageView(new Image("/resources/silver.png"));
    private final ImageView BRONZE_MEDAL
            = new ImageView(new Image("/resources/bronze.png"));
    private final int MEDAL_SIZE = 30;

    @FXML
    private ChoiceBox<String> dropdown;
    @FXML
    private MediaView backgroundMusic;
    @FXML
    private ListView<HBox> leaderboard;
    @FXML
    private Button muteButton;
    private Stage primaryStage;

    /**
     * Sets the dropdown box to include all game file names.
     */
    public void setDropdown() {
        dropdown.setItems(getLevels());
    }

    /**
     * Handles a change in the levels dropdown box. When changed, the
     * leaderboard also changes to whatever file name is selected.
     * @throws FileNotFoundException If leaderboard file does not exist.
     */
    public void handleChange() throws FileNotFoundException {
        new AudioPlayer().clickPlay();
        String gameName = dropdown.getValue();
        leaderboard.getItems().clear();
        if (gameName != null) { // If a name is selected
            ArrayList<PlayerProfile> profiles = new ArrayList<>();
            for (String player : FileHandler.loadLeaderboard(gameName)) {
                profiles.add(FileHandler.loadProfile(player));
            }
            //Sort profiles by wins
            profiles.sort((p1, p2) ->
                    p2.getNumberOfWins() - p1.getNumberOfWins());

            for (int i = 0; i < profiles.size(); i++) {
                PlayerProfile currentProfile = profiles.get(i);
                addLeaderboardRow(i, currentProfile);
            }
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
        muteButton.getStyleClass()
                .set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
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
     * Gets the names of all game level files.
     * @return All game file names.
     */
    private ObservableList<String> getLevels() {
        File folder = new File("src/gamefiles/levels");
        ObservableList<String> listOfFiles = observableArrayList();
        for (File i : Objects.requireNonNull(folder.listFiles())) {
            listOfFiles.add(i.getName().substring(0, i
                    .getName().length() - DOT_TXT));
        }
        return listOfFiles;
    }

    /**
     * Adds a profile to the leaderboard. If the profile is top three then
     * their respective medal is displayed.
     * @param index The players position in the leaderboard.
     * @param currentProfile The profile to add to leaderboard.
     */
    private void addLeaderboardRow(int index, PlayerProfile currentProfile) {
        String name = currentProfile.getPlayerName();
        String space = new String(new char[FORMAT_PADDING - name.length()])
                .replace("\0", "0");
        int wins = currentProfile.getNumberOfWins();
        int losses = currentProfile.getNumberOfLosses();
        int games = currentProfile.getNumberOfGamesPlayed();

        // Formats the players statistics
        Text playerText = new Text(name);
        Text whitespace = new Text(space);
        whitespace.setFill(TRANSPARENT);
        Text stats = new Text("| " + wins + " wins | " + losses
                + " losses | " + games + " games played");

        switch (index) {
            case 0 : // 1st = gold medal
                GOLD_MEDAL.setFitHeight(MEDAL_SIZE);
                GOLD_MEDAL.setFitWidth(MEDAL_SIZE);
                playerText.getStyleClass().add("lb-text-win");
                whitespace.getStyleClass().add("lb-text-win");
                stats.getStyleClass().add("lb-text-win");
                leaderboard.getItems().add(new HBox(GOLD_MEDAL,
                        playerText, whitespace, stats));
                break;

            case 1 : // 2nd = silver medal
                SILVER_MEDAL.setFitHeight(MEDAL_SIZE);
                SILVER_MEDAL.setFitWidth(MEDAL_SIZE);
                whitespace.getStyleClass().add("lb-text-win");
                stats.getStyleClass().add("lb-text-win");
                playerText.getStyleClass().add("lb-text-win");
                leaderboard.getItems().add(new HBox(SILVER_MEDAL,
                        playerText, whitespace, stats));
                break;

            case 2 : // 3rd = bronze medal
                BRONZE_MEDAL.setFitHeight(MEDAL_SIZE);
                BRONZE_MEDAL.setFitWidth(MEDAL_SIZE);
                whitespace.getStyleClass().add("lb-text-win");
                stats.getStyleClass().add("lb-text-win");
                playerText.getStyleClass().add("lb-text-win");
                leaderboard.getItems().add(new HBox(BRONZE_MEDAL,
                        playerText, whitespace, stats));
                break;

            default: // Non top three row.
                whitespace.getStyleClass().add("lb-text");
                stats.getStyleClass().add("lb-text");
                playerText.getStyleClass().add("lb-text");
                HBox cell = new HBox(playerText, whitespace, stats);
                cell.getStyleClass().add("leaderboard-cell");
                leaderboard.getItems().add(cell);
        }
    }
}
