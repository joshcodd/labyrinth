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
import java.util.Arrays;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Player Selection Scene Controller
 * @author James Charnock
 * @StudentID 1909700
 */
public class PlayerSelectionController implements Initializable {
    @FXML
    public Button muteButton;
    @FXML
    public MediaView backgroundMusic;
    @FXML
    public VBox playerForm1;
    @FXML
    public VBox playerForm2;
    @FXML
    public VBox playerForm3;
    @FXML
    public VBox playerForm4;
    @FXML
    public Label playerLabel1;
    @FXML
    public Label playerLabel2;
    @FXML
    public Label playerLabel3;
    @FXML
    public Label playerLabel4;
    @FXML
    public ChoiceBox<String> profileBox1;
    @FXML
    public ChoiceBox<String> profileBox2;
    @FXML
    public ChoiceBox<String> profileBox3;
    @FXML
    public ChoiceBox<String> profileBox4;
    @FXML
    public ChoiceBox<String> colourBox1;
    @FXML
    public ChoiceBox<String> colourBox2;
    @FXML
    public ChoiceBox<String> colourBox3;
    @FXML
    public ChoiceBox<String> colourBox4;
    @FXML
    public CheckBox startFirst1;
    @FXML
    public CheckBox startFirst2;
    @FXML
    public CheckBox startFirst3;
    @FXML
    public CheckBox startFirst4;
    @FXML
    public Label profileLabel1;
    @FXML
    public Label profileLabel2;
    @FXML
    public Label profileLabel3;
    @FXML
    public Label profileLabel4;
    @FXML
    public ImageView tankView1;
    @FXML
    public ImageView tankView2;
    @FXML
    public ImageView tankView3;
    @FXML
    public ImageView tankView4;
    @FXML
    public Label startLabel1;
    @FXML
    public Label startLabel2;
    @FXML
    public Label startLabel3;
    @FXML
    public Label startLabel4;
    @FXML
    public Label numPlayersLabel;
    @FXML
    public Slider numPlayersSlider;
    @FXML
    public HBox buttBar;
    @FXML
    public Button backButt;
    @FXML
    public Button newPlayerButt;
    @FXML
    public Button beginButt;
    public VBox[] playerForms;
    public Label[] profileLabels;
    public Label[] startLabels;
    public ChoiceBox<String>[] profileBoxes;
    public ChoiceBox<String>[] colourBoxes;
    public String defaultColour = "Auto-Assign";
    public CheckBox[] startFirstChecks;
    public ImageView[] tankViews;
    public String gameName;
    public Stage primaryStage;
    public Player[] players;
    public int numPlayers = 4;

    /**
     *
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
     * @param names
     */
    @FXML
    public void setProfileBoxes(ArrayList<String> names){
        for(int i = 0; i<4; i++) {
            profileBoxes[i].setItems(observableArrayList(names));
        }
    }

    /**
     * @param backgroundMusic
     */
    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void updateStartLabels(Player[] players){
        for(int i=0;i<players.length;i++) {
            startLabels[i].setVisible(players[i].isStartingFirst());
        }
    }

    /**
     * @param index
     * @param colour
     */
    public void updateTankView(int index, String colour){
        tankViews[index].setImage(new Image("resources/"+colour+".png"));
    }

    /**
     * @param index
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
     * @param decrease
     * @param oldValue
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
                System.out.print("changing" + player.getProfileName() + player.getColour());
                player.setColour(availableColours.remove(0));
            }
        }
        return players;
    }

    /**
     */
    public void handleMute() {
        backgroundMusic.getMediaPlayer().setMute(!backgroundMusic.getMediaPlayer().isMute());
        muteButton.getStyleClass().set(0, ("mute-" + backgroundMusic.getMediaPlayer().isMute()));
    }

    /**
     * @param location
     * @param resources
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

            profileBoxes[index].setOnAction(event -> {
                ChoiceBox currProfileBox = (ChoiceBox) event.getSource();
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

        beginButt.setOnAction (event -> {
            boolean isFilled = true;
            for (int i = 0; i < numPlayers; i++){
                if (profileBoxes[i].getValue() == null){
                    isFilled = false;
                }
            }

            if (isFilled){
                int startingPlayerIndex = -1;
                boolean isSelected = false;

                for (int i = 0; i < numPlayers; i++){
                    if (startFirstChecks[i].isSelected()){
                        isSelected = true;
                        startingPlayerIndex = i;
                    }
                }

                if (isSelected){
                    Player[] playerFinal = new Player[numPlayers];
                    if (numPlayers >= 0) System.arraycopy(players, 0, playerFinal, 0, numPlayers);
                    playerFinal = autoAssignColour(playerFinal);
                    Player temp = playerFinal[0];
                    playerFinal[0] = playerFinal[startingPlayerIndex];
                    playerFinal[startingPlayerIndex] = temp;
                    System.out.print(Arrays.toString(playerFinal));
                    try {
                        new GameScene(primaryStage, new Game(gameName, playerFinal), backgroundMusic.getMediaPlayer());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Pick starting player.", ButtonType.CLOSE);
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Empty players.", ButtonType.CLOSE);
                alert.showAndWait();
            }
        });
    }
}