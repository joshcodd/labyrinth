package controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
 * Player Selection Scene Controller
 *
 * @author James Charnock
 * @StudentID 1909700
 */
public class PlayerSelectionController implements Initializable {
    /**
     * The Mute button.
     */
    @FXML
    public Button muteButton;
    /**
     * The Background music.
     */
    @FXML
    public MediaView backgroundMusic;
    /**
     * The Player form 1.
     */
    @FXML
    public VBox playerForm1;
    /**
     * The Player form 2.
     */
    @FXML
    public VBox playerForm2;
    /**
     * The Player form 3.
     */
    @FXML
    public VBox playerForm3;
    /**
     * The Player form 4.
     */
    @FXML
    public VBox playerForm4;
    /**
     * The Player label 1.
     */
    @FXML
    public Label playerLabel1;
    /**
     * The Player label 2.
     */
    @FXML
    public Label playerLabel2;
    /**
     * The Player label 3.
     */
    @FXML
    public Label playerLabel3;
    /**
     * The Player label 4.
     */
    @FXML
    public Label playerLabel4;
    /**
     * The Profile box 1.
     */
    @FXML
    public ChoiceBox<String> profileBox1;
    /**
     * The Profile box 2.
     */
    @FXML
    public ChoiceBox<String> profileBox2;
    /**
     * The Profile box 3.
     */
    @FXML
    public ChoiceBox<String> profileBox3;
    /**
     * The Profile box 4.
     */
    @FXML
    public ChoiceBox<String> profileBox4;
    /**
     * The Colour box 1.
     */
    @FXML
    public ChoiceBox<String> colourBox1;
    /**
     * The Colour box 2.
     */
    @FXML
    public ChoiceBox<String> colourBox2;
    /**
     * The Colour box 3.
     */
    @FXML
    public ChoiceBox<String> colourBox3;
    /**
     * The Colour box 4.
     */
    @FXML
    public ChoiceBox<String> colourBox4;
    /**
     * The Start first 1.
     */
    @FXML
    public CheckBox startFirst1;
    /**
     * The Start first 2.
     */
    @FXML
    public CheckBox startFirst2;
    /**
     * The Start first 3.
     */
    @FXML
    public CheckBox startFirst3;
    /**
     * The Start first 4.
     */
    @FXML
    public CheckBox startFirst4;
    /**
     * The Profile label 1.
     */
    @FXML
    public Label profileLabel1;
    /**
     * The Profile label 2.
     */
    @FXML
    public Label profileLabel2;
    /**
     * The Profile label 3.
     */
    @FXML
    public Label profileLabel3;
    /**
     * The Profile label 4.
     */
    @FXML
    public Label profileLabel4;
    /**
     * The Tank view 1.
     */
    @FXML
    public ImageView tankView1;
    /**
     * The Tank view 2.
     */
    @FXML
    public ImageView tankView2;
    /**
     * The Tank view 3.
     */
    @FXML
    public ImageView tankView3;
    /**
     * The Tank view 4.
     */
    @FXML
    public ImageView tankView4;
    /**
     * The Start label 1.
     */
    @FXML
    public Label startLabel1;
    /**
     * The Start label 2.
     */
    @FXML
    public Label startLabel2;
    /**
     * The Start label 3.
     */
    @FXML
    public Label startLabel3;
    /**
     * The Start label 4.
     */
    @FXML
    public Label startLabel4;
    /**
     * The Num players label.
     */
    @FXML
    public Label numPlayersLabel;
    /**
     * The Num players slider.
     */
    @FXML
    public Slider numPlayersSlider;
    /**
     * The Butt bar.
     */
    @FXML
    public HBox buttBar;
    /**
     * The Back butt.
     */
    @FXML
    public Button backButt;
    /**
     * The New player butt.
     */
    @FXML
    public Button newPlayerButt;
    /**
     * The Begin butt.
     */
    @FXML
    public Button beginButt;

    private VBox[] playerForms;
    private Label[] profileLabels;
    private Label[] startLabels;
    private ChoiceBox<String>[] profileBoxes;
    private ChoiceBox<String>[] colourBoxes;
    private String defaultColour = "Auto-Assign";
    private CheckBox[] startFirstChecks;
    private ImageView[] tankViews;
    private String gameName;
    private Stage primaryStage;
    private Player[] players;
    private int numPlayers = 4;

    /**
     * Set colour boxes.
     */
    @FXML
    public void setColourBoxes(){
        for(int i = 0; i<4; i++){
            colourBoxes[i].setItems(observableArrayList("Green","Red", "Blue", "Desert-Camo", defaultColour));
            colourBoxes[i].setValue(defaultColour);
            colourBoxes[i].setDisable(true);
        }
    }

    /**
     * Set profile boxes.
     *
     * @param names ArrayList of profile names
     */
    @FXML
    public void setProfileBoxes(ArrayList<String> names){
        for(int i = 0; i<4; i++) {
            profileBoxes[i].setItems(observableArrayList(names));
        }
    }

    /**
     * Sets background music.
     *
     * @param backgroundMusic Background music
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * Sets game name.
     *
     * @param gameName the game name
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Sets primary stage.
     *
     * @param primaryStage primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Update start labels.
     *
     * @param players the players
     */
    public void updateStartLabels(Player[] players){
        for(int i=0;i<players.length;i++) {
            startLabels[i].setVisible(players[i].isStartingFirst());
        }
    }

    /**
     * Update tank view.
     *
     * @param index  tankView identifier
     * @param colour tank colour
     */
    public void updateTankView(int index, String colour){
        tankViews[index].setImage(new Image("resources/"+colour+".png"));
        tankViews[index].setFitHeight(60);
        tankViews[index].setFitWidth(60);


    }

    /**
     * Set starting player.
     *
     * @param index player identifier
     */
    public void setStartingPlayer(int index){
        for(int i = 0; i<4; i++){
            if(i!=index) {
                if (players[i].isStartingFirst()) {
                    players[i].setFirst(false);
                }
                startFirstChecks[i].setSelected(false);
            }
        }
        players[index].setFirst(true);
    }

    /**
     * Update player forms.
     *
     * @param decrease true if numPlayers less than oldValue
     * @param oldValue numPlayers before value change
     */
    public void updatePlayerForms(boolean decrease, int oldValue){
        if(decrease) {
            for (int i = numPlayers; i < oldValue; i++) {
                playerForms[i].setVisible(false);
                playerForms[i].setManaged(false);
                profileBoxes[i].getSelectionModel().clearSelection();
                colourBoxes[i].setValue(defaultColour);
                colourBoxes[i].setDisable(true);
            }
        }
        else{
            for(int i = oldValue; i<numPlayers; i++) {
                playerForms[i].setVisible(true);
                playerForms[i].setManaged(true);
                colourBoxes[i].setDisable(true);
            }
        }
    }


    /**
     * Automatically assigns the remaining colours to players who did not select one.
     *
     * @param players the players
     * @return the player [ ]
     */
    public Player[] autoAssignColour(Player[] players){
        ArrayList<String> availableColours = new ArrayList<String>();
        availableColours.add("Green");
        availableColours.add("Red");
        availableColours.add("Blue");
        availableColours.add("Desert-Camo");
        for (Player player : players) {
            if (!player.getColour().equals(defaultColour)) {
                availableColours.remove(player.getColour());
            }
        }

        for (Player player : players) {
            if (player.getColour().equals(defaultColour)) {
                player.setColour(availableColours.remove(0));
            }
        }
        return players;
    }

    /**
     * Handle mute event.
     */
    public void handleMute() {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic.getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * Initializer. Establishes necessary variables and, sets action events and listeners.
     *
     * @param location URL
     * @param resources Resource
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.playerForms = new VBox[]{playerForm1, playerForm2, playerForm3, playerForm4};
        this.profileBoxes = new ChoiceBox[]{profileBox1, profileBox2, profileBox3, profileBox4};
        this.colourBoxes = new ChoiceBox[]{colourBox1, colourBox2, colourBox3, colourBox4};
        this.profileLabels = new Label[]{profileLabel1, profileLabel2, profileLabel3, profileLabel4};
        this.startLabels = new Label[]{startLabel1, startLabel2, startLabel3, startLabel4};
        this.startFirstChecks = new CheckBox[]{startFirst1, startFirst2, startFirst3, startFirst4};
        this.tankViews = new ImageView[]{tankView1, tankView2, tankView3, tankView4};
        // key {playerNum, showing confirmations, ready}
        String[] prevNameValues = {null,null,null,null};
        String[] prevColourValues = {defaultColour,defaultColour,defaultColour,defaultColour};

        players = new Player[]{
                new Player(1, null), new Player(2, null),
                new Player(3, null), new Player(4, null)
        };

        // tries to load profile names and set them in profile boxes
        try {
            setProfileBoxes(FileHandler.getAllNames());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // set each player form
        for (int i = 0; i < 4; i++) {
            int index = i;
            updateTankView(index, defaultColour);
            startFirstChecks[index].setOnAction( event -> {
                if (startFirstChecks[index].isSelected()) {
                    setStartingPlayer(index);
                } else {
                    players[index].setFirst(false);
                }
                updateStartLabels(players);
            });

            // when profile selected: tries to load profile and allows user access to colour choice box,
            //                       then removes selected profile from other choice boxes

            profileBoxes[index].setOnAction(event -> {
                ChoiceBox<String> currProfileBox = profileBoxes[index];
                if (currProfileBox.getValue() != null) {
                    String playerName = currProfileBox.getValue().toString();
                    try {
                        Player player = new Player(index, FileHandler.loadProfile(playerName));
                        players[index] = player;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    colourBoxes[index].setDisable(false);
                    String selectedItem = profileBoxes[index].getSelectionModel().getSelectedItem();

                    for (int j = 0; j < 4; j++){
                        if (j != index) {
                            profileBoxes[j].getItems().remove(selectedItem);
                            if (prevNameValues[index] != null){
                                profileBoxes[j].getItems().add(prevNameValues[index]);
                            }
                        }
                    }
                    prevNameValues[index] = selectedItem;
                } else {
                    profileBoxes[index].getItems().remove(prevNameValues[index]);
                    for (int j = 0; j < 4; j++){
                        profileBoxes[j].getItems().add(prevNameValues[index]);
                    }
                }
            });

            // When colour selected: set as player's colour and remove it as an option in other colour boxes

            colourBoxes[index].setOnAction(event -> {
                players[index].setColour(colourBoxes[index].getValue());
                updateTankView(index, players[index].getColour());
                String selectedItem = colourBoxes[index].getValue();
                for (int j = 0; j < 4; j++) {
                    if (j != index && !selectedItem.equals(defaultColour)) {
                        colourBoxes[j].getItems().remove(selectedItem);
                        if (!prevColourValues[index].equals(defaultColour)) {
                            colourBoxes[j].getItems().add(prevColourValues[index]);
                        }
                    } else if (j != index && !prevColourValues[index].equals(defaultColour) &&
                            !prevColourValues[index].equals(selectedItem)){
                        colourBoxes[j].getItems().add(prevColourValues[index]);
                    }
                }
                prevColourValues[index] = selectedItem;
            });
        }

        // set numPlayers to new value and update the number of player forms

        numPlayersSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            numPlayers = newValue.intValue();
            updatePlayerForms(oldValue.intValue() > numPlayers, oldValue.intValue());
            numPlayersLabel.setText("Number of Players: " + numPlayers);
        });

        backButt.setOnAction(event -> {
            new LevelSelectionScene(primaryStage, backgroundMusic.getMediaPlayer());
        });

        newPlayerButt.setOnAction(event -> {
            new EditPlayersScene(primaryStage, backgroundMusic.getMediaPlayer());
        });

        // begin game, if all necessary fields are filled:
        //      enters players into the game with correct starting order

        beginButt.setOnAction (event -> {
            boolean isFilled = true;
            for (int i = 0; i < numPlayers; i++){
                if (profileBoxes[i].getValue() == null){
                    isFilled = false;
                }
            }
            // check if necessary details are filled
            if (isFilled){
                int startingPlayerIndex = -1;
                boolean isSelected = false;

                for (int i = 0; i < numPlayers; i++){
                    if (startFirstChecks[i].isSelected()){
                        isSelected = true;
                        startingPlayerIndex = i;
                    }
                }

                Player[] playerFinal = new Player[numPlayers];
                if (numPlayers >= 0) System.arraycopy(players, 0, playerFinal, 0, numPlayers);
                playerFinal = autoAssignColour(playerFinal);

                // if a player is selected to start first swap them with player in first pos
                if (isSelected) {
                    Player temp = playerFinal[0];
                    playerFinal[0] = playerFinal[startingPlayerIndex];
                    playerFinal[startingPlayerIndex] = temp;
                }
                try {
                    new GameScene(primaryStage, new Game(gameName, playerFinal), backgroundMusic.getMediaPlayer());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Empty players.", ButtonType.CLOSE);
                alert.showAndWait();
            }
        });
    }
}