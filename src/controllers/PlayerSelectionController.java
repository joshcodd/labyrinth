package controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.FileHandler;
import models.Game;
import models.Player;
import views.scenes.EditPlayersScene;
import views.scenes.GameScene;
import views.scenes.LevelSelectionScene;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Player Selection Scene Controller.
 * Links the player and player profile models to the GUI.
 * @author James Charnock
 */
public class PlayerSelectionController implements Initializable {
    private final String DEFAULT_COLOUR = "Auto-Assign";
    private final int FIRST_PLAYER = 1;
    private final int SECOND_PLAYER = 2;
    private final int THIRD_PLAYER = 3;
    private final int MAX_PLAYERS = 4;
    private final int TANK_SIZE = 60;
    @FXML
    private Button muteButton;
    @FXML
    private MediaView backgroundMusic;
    @FXML
    private VBox playerForm1;
    @FXML
    private VBox playerForm2;
    @FXML
    private VBox playerForm3;
    @FXML
    private VBox playerForm4;
    @FXML
    private ChoiceBox<String> profileBox1;
    @FXML
    private ChoiceBox<String> profileBox2;
    @FXML
    private ChoiceBox<String> profileBox3;
    @FXML
    private ChoiceBox<String> profileBox4;
    @FXML
    private ChoiceBox<String> colourBox1;
    @FXML
    private ChoiceBox<String> colourBox2;
    @FXML
    private ChoiceBox<String> colourBox3;
    @FXML
    private ChoiceBox<String> colourBox4;
    @FXML
    private CheckBox startFirst1;
    @FXML
    private CheckBox startFirst2;
    @FXML
    private CheckBox startFirst3;
    @FXML
    private CheckBox startFirst4;
    @FXML
    private ImageView tankView1;
    @FXML
    private ImageView tankView2;
    @FXML
    private ImageView tankView3;
    @FXML
    private ImageView tankView4;
    @FXML
    private Label startLabel1;
    @FXML
    private Label startLabel2;
    @FXML
    private Label startLabel3;
    @FXML
    private Label startLabel4;
    @FXML
    private Label numPlayersLabel;
    @FXML
    private Slider numPlayersSlider;
    @FXML
    private Button backButt;
    @FXML
    private Button newPlayerButt;
    @FXML
    private Button beginButt;

    private VBox[] playerForms;
    private Label[] startLabels;
    private ChoiceBox<String>[] profileBoxes;
    private ChoiceBox<String>[] colourBoxes;
    private CheckBox[] startFirstChecks;
    private ImageView[] tankViews;
    private String gameName;
    private Stage primaryStage;
    private Player[] players;
    private int numPlayers = MAX_PLAYERS;

    /**
     * Initializes the player selection GUI so that it's ready to be displayed.
     * @param location The location used to resolve relative paths for the
     *                 root object, or null if the location is not known.
     * @param resources The resources used to localize the root object,
     *                  or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.playerForms = new VBox[]{playerForm1, playerForm2,
                playerForm3, playerForm4};
        this.profileBoxes = new ChoiceBox[]{profileBox1, profileBox2,
                profileBox3, profileBox4};
        this.colourBoxes = new ChoiceBox[]{colourBox1, colourBox2,
                colourBox3, colourBox4};
        this.startLabels = new Label[]{startLabel1, startLabel2,
                startLabel3, startLabel4};
        this.startFirstChecks = new CheckBox[]{startFirst1, startFirst2,
                startFirst3, startFirst4};
        this.tankViews = new ImageView[]{tankView1, tankView2, tankView3,
                tankView4};
        String[] prevNameValues = {null, null, null, null};
        String[] prevColourValues = {DEFAULT_COLOUR, DEFAULT_COLOUR,
                DEFAULT_COLOUR, DEFAULT_COLOUR};
        players = new Player[]{
                new Player(FIRST_PLAYER, null), new Player(SECOND_PLAYER, null),
                new Player(THIRD_PLAYER, null), new Player(MAX_PLAYERS, null)
        };
        setProfileBoxes();
        // set each player form
        for (int i = 0; i < MAX_PLAYERS; i++) {
            int index = i;
            updateTankView(index, DEFAULT_COLOUR);
            startFirstChecks[index].setOnAction(event ->
                    handleStartingCheckBox(index));

            profileBoxes[index].setOnAction(event ->
                    handleProfileChange(index, prevNameValues));

            colourBoxes[index].setOnAction(event ->
                    handleColourChange(index, prevColourValues));
        }

        numPlayersSlider.valueProperty()
                .addListener((observable, oldValue, newValue) -> {
            numPlayers = newValue.intValue();
            updatePlayerForms(oldValue.intValue() > numPlayers,
                    oldValue.intValue());
            numPlayersLabel.setText("Number of Players: " + numPlayers);
        });

        backButt.setOnAction(event ->
                new LevelSelectionScene(primaryStage, backgroundMusic.
                        getMediaPlayer()));

        newPlayerButt.setOnAction(event ->
                new EditPlayersScene(primaryStage, backgroundMusic
                        .getMediaPlayer()));

        beginButt.setOnAction(event -> handleBeginClick());
    }

    /**
     * Adds all colour options to the select colour drop down boxes.
     */
    @FXML
    public void setColourBoxes() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            colourBoxes[i].setItems(observableArrayList("Green", "Red",
                    "Blue", "Desert-Camo", DEFAULT_COLOUR));
            colourBoxes[i].setValue(DEFAULT_COLOUR);
            colourBoxes[i].setDisable(true);
        }
    }

    /**
     * Sets the background music for the scene.
     * @param backgroundMusic The background music.
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic
                .getMediaPlayer().isMute()));
    }

    /**
     * Sets the level file name to be used in the game, when started.
     * @param gameName The level name.
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Sets stage that the application is being displayed on.
     * @param primaryStage The stage application is being displayed on.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Mutes the background audio if its currently un-muted, and un-mutes
     * the background audio if it is currently muted.
     */
    public void handleMute() {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic
                .getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic
                .getMediaPlayer().isMute()));
    }

    /**
     * Sets the label for the player that is starting the game.
     * @param players The players playing the game.
     */
    private void updateStartLabels(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            startLabels[i].setVisible(players[i].isStartingFirst());
        }
    }

    /**
     * Sets player tank images to the correct tank and colour.
     * @param index Player number/index.
     * @param colour The colour to set the tank image to.
     */
    private void updateTankView(int index, String colour) {
        tankViews[index].setImage(new Image("resources/" + colour + ".png"));
        tankViews[index].setFitHeight(TANK_SIZE);
        tankViews[index].setFitWidth(TANK_SIZE);
    }

    /**
     * Sets the player that is to start the game.
     * @param index The player identifier.
     */
    private void setStartingPlayer(int index) {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (i != index) {
                if (players[i].isStartingFirst()) {
                    players[i].setFirst(false);
                }
                startFirstChecks[i].setSelected(false);
            }
        }
        players[index].setFirst(true);
    }

    /**
     * Removes and adds player forms depending on how the number
     * of players selected to play.
     * @param decrease If the number of player forms is being decreased or not.
     * @param oldValue Number of player forms currently.
     */
    private void updatePlayerForms(boolean decrease, int oldValue) {
        if (decrease) {
            for (int i = numPlayers; i < oldValue; i++) {
                playerForms[i].setVisible(false);
                playerForms[i].setManaged(false);
                profileBoxes[i].getSelectionModel().clearSelection();
                colourBoxes[i].setValue(DEFAULT_COLOUR);
                colourBoxes[i].setDisable(true);
            }
        } else {
            for (int i = oldValue; i < numPlayers; i++) {
                playerForms[i].setVisible(true);
                playerForms[i].setManaged(true);
                colourBoxes[i].setDisable(true);
            }
        }
    }

    /**
     * If a players colour is set to auto assign, then a colour that is not
     * selected by any player is assigned.
     * @param players The players selected.
     * @return The players selected with their new, unique colours now
     * assigned.
     */
    private Player[] autoAssignColour(Player[] players) {
        ArrayList<String> availableColours = new ArrayList<>();
        availableColours.add("Green");
        availableColours.add("Red");
        availableColours.add("Blue");
        availableColours.add("Desert-Camo");
        for (Player player : players) {
            if (!player.getColour().equals(DEFAULT_COLOUR)) {
                availableColours.remove(player.getColour());
            }
        }

        for (Player player : players) {
            if (player.getColour().equals(DEFAULT_COLOUR)) {
                player.setColour(availableColours.remove(0));
            }
        }
        return players;
    }

    /**
     * Handles a change of a profile selection box. That is, removes a selected
     * player from all other profile selection boxes.
     * @param index The index of the player form.
     * @param prevNameValues The previous values of all profile
     *                       selection boxes.
     */
    private void handleProfileChange(int index, String[] prevNameValues) {
        ChoiceBox<String> currProfileBox = profileBoxes[index];
        if (currProfileBox.getValue() != null) {
            String playerName = currProfileBox.getValue();
            try {
                Player player = new Player(index, FileHandler
                        .loadProfile(playerName));
                players[index] = player;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            colourBoxes[index].setDisable(false);
            String selectedItem = profileBoxes[index].getSelectionModel()
                    .getSelectedItem();

            for (int j = 0; j < MAX_PLAYERS; j++) {
                if (j != index) {
                    profileBoxes[j].getItems().remove(selectedItem);
                    if (prevNameValues[index] != null) {
                        profileBoxes[j].getItems().add(prevNameValues[index]);
                    }
                }
            }
            prevNameValues[index] = selectedItem;
        } else {
            profileBoxes[index].getItems().remove(prevNameValues[index]);
            for (int j = 0; j < MAX_PLAYERS; j++) {
                profileBoxes[j].getItems().add(prevNameValues[index]);
            }
        }
    }

    /**
     * Handles a change of a colour selection box. Removes a selected
     * colour if it is not auto-assign, from all other colour selection boxes.
     * @param index The index of the player form.
     * @param prevColourValues The previous values of all profile
     *                       selection boxes.
     */
    private void handleColourChange(int index, String[] prevColourValues) {
        players[index].setColour(colourBoxes[index].getValue());
        updateTankView(index, players[index].getColour());
        String selectedItem = colourBoxes[index].getValue();
        for (int j = 0; j < MAX_PLAYERS; j++) {
            if (j != index && !selectedItem.equals(DEFAULT_COLOUR)) {
                colourBoxes[j].getItems().remove(selectedItem);
                if (!prevColourValues[index].equals(DEFAULT_COLOUR)) {
                    colourBoxes[j].getItems().add(prevColourValues[index]);
                }
            } else if (j != index && !prevColourValues[index]
                    .equals(DEFAULT_COLOUR) && !prevColourValues[index]
                    .equals(selectedItem)) {
                colourBoxes[j].getItems().add(prevColourValues[index]);
            }
        }
        prevColourValues[index] = selectedItem;
    }

    /**
     * Sets the selected checkbox player as the starting player.
     * @param index The index of player/checkbox.
     */
    private void handleStartingCheckBox(int index) {
        if (startFirstChecks[index].isSelected()) {
            setStartingPlayer(index);
        } else {
            players[index].setFirst(false);
        }
        updateStartLabels(players);
    }

    /**
     * Handles the begin button click, starts the game from the
     * selected player colours and profiles.
     */
    private void handleBeginClick() {
        boolean isFilled = true;
        for (int i = 0; i < numPlayers; i++) {
            if (profileBoxes[i].getValue() == null) {
                isFilled = false;
            }
        }
        // check if necessary details are filled
        if (isFilled) {
            int startingPlayerIndex = -1;
            boolean isSelected = false;
            for (int i = 0; i < numPlayers; i++) {
                if (startFirstChecks[i].isSelected()) {
                    isSelected = true;
                    startingPlayerIndex = i;
                }
            }

            // Removes any null players.
            Player[] playerFinal = new Player[numPlayers];
            if (numPlayers >= 0) {
                System.arraycopy(players, 0, playerFinal, 0, numPlayers);
            }
            playerFinal = autoAssignColour(playerFinal);

            /*if a player is selected to start first swap them with player
            in first position*/
            if (isSelected) {
                Player temp = playerFinal[0];
                playerFinal[0] = playerFinal[startingPlayerIndex];
                playerFinal[startingPlayerIndex] = temp;
            }
            try {
                new GameScene(primaryStage, new Game(gameName, playerFinal),
                        backgroundMusic.getMediaPlayer());
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Error starting game.", ButtonType.CLOSE);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty players.",
                    ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    /**
     * Adds all player profile options to the select profile drop down boxes.
     */
    @FXML
    public void setProfileBoxes() {
        try {
            for (int i = 0; i < MAX_PLAYERS; i++) {
                profileBoxes[i].setItems(observableArrayList(FileHandler
                        .getAllNames()));
            }
        } catch (FileNotFoundException e) {
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "An error was encountered while attempting to load players",
                    ButtonType.OK);
            error.showAndWait();
        }
    }
}
