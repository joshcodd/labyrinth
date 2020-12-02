package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import models.PlayerProfile;
import views.scenes.EditPlayersScene;
import views.scenes.GameScene;
import views.scenes.LevelSelectionScene;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
    public Button confButt1;

    @FXML
    public Button confButt2;

    @FXML
    public Button confButt3;

    @FXML
    public Button confButt4;

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
    public Label colourLabel1;

    @FXML
    public Label colourLabel2;

    @FXML
    public Label colourLabel3;

    @FXML
    public Label colourLabel4;

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

    public Label[] colourLabels;

    public Label[] profileLabels;

    public Label[] startLabels;

    public ChoiceBox<String>[] profileBoxes;

    public Button[] confButtons;

    public ChoiceBox<String>[] colourBoxes;

    public String defaultColour = "Auto-Assign";

    public CheckBox[] startFirstChecks;
    
    public TextField playerName;

    public ImageView[] tankViews;

    public String gameName;

    public Stage primaryStage;

    public Player[] players;

    public int numPlayers = 4;

    public int[][] playerFormData;

    /**
     *
     */
    @FXML
    public void setColourBoxes(){
        for(int i = 0; i<4; i++){
            colourBoxes[i].setItems(observableArrayList("Green","Red", "Blue", "Desert Camo","Auto-Assign"));
            colourBoxes[i].setValue(defaultColour);
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
            if(players[i].isStartingFirst()){
                startLabels[i].setVisible(true);
            } else {
                startLabels[i].setVisible(false);
            }
        }
    }

    /**
     * @param index
     * @param player
     */
    public void updateTankView(int index, Player player){
        String imageName;
        switch(player.getColour()){
            case "Green":
                imageName="1";
                break;
            case "Red":
                imageName="2";
                break;
            case "Desert Camo":
                imageName="3";
                break;
            case "Blue":
                imageName="4";
                break;
            default:
                imageName="5";
        }
        tankViews[index].setImage(new Image("resources/"+imageName+".png"));
    }

    /**
     * @param index
     * @param player
     */
    public void updateColourLabel(int index, Player player) {
        colourLabels[index].setText(player.getColour());
    }

    /**
     * @param id
     * @param field
     * @param value
     * @return
     */


    /**
     * @param index
     */
    public void selectProfile(int index, Player player){
        ChoiceBox<String> profileBox = profileBoxes[index];
        Label profileLabel = profileLabels[index];

        String value = profileBox.getValue();
            if(value != null){
                if (!checkTaken(index, "name", value)) {
                    if(player.hasProfileSet()) {
                        player.getProfile().setplayerName(value);
                        profileLabel.setText(player.getProfileName());
                    } else {
                        try {
                            player.setProfile(FileHandler.loadProfile(value));
                            profileLabel.setText(player.getProfileName());
                            System.out.println("Success");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    profileBox.setValue(null);
                    profileLabel.setText("Profile taken.");
                }

            } else {
                profileLabel.setText("No profile selected");
            }
        profileLabel.setVisible(true);
    }

    /**
     * @param index
     */
    public void selectColour(int index, Player player){
        ChoiceBox<String> colourBox = colourBoxes[index];
        Label colourLabel = colourLabels[index];

        String colour = colourBox.getValue();

        if(!colour.equals("Auto-Assign")) {

            if (!checkTaken(index, "colour", colour)) {
                player.setColour(colour);
                colourLabel.setText(player.getColour());

            } else {
                colourLabel.setText("Colour taken. ");
            }
        } else {
            colourLabel.setText("Auto-assign colour");
            player.setColour(colour);
        }
        updateTankView(index, player);
        colourLabel.setVisible(true);
    }

    public void setConfLabels(int index, boolean value){
        if(value) {
            profileLabels[index].setVisible(true);
            colourLabels[index].setVisible(true);
            playerFormData[index][1] = 1;
        } else {
            profileLabels[index].setVisible(false);
            colourLabels[index].setVisible(false);
            playerFormData[index][1] = 0;
        }
    }

    public boolean checkTaken(int id, String field, String value){
        int count = 0;
        boolean taken = true;
        for(int i =0; i < numPlayers;i++){
            if(i==id){
                continue;
            }
            if(field.equals("name")){
                if(players[i].hasProfileSet()) {
                    if (players[i].getProfileName().equals(value)) {
                        count += 1;
                    }
                }
            } else if (field.equals("colour")){
                if(players[i].getColour().equals(value)){
                    count+=1;
                }
            } else {System.out.println("checkTaken: invalid field");}
        }
        if(count<1){
            taken = false;
        }
        return taken;
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
     * @param increase
     * @param oldValue
     */
    public void updatePlayerForms(boolean increase, int oldValue){
        if(increase) {
            for(int i = oldValue; i<numPlayers; i++) {
                playerForms[i].setVisible(true);
                playerForms[i].setManaged(true);

                if (playerFormData[i][1] == 1) {
                    if (checkTaken(i, "name", players[i].getProfileName())) {

                        profileBoxes[i].setValue(null);
                        selectProfile(i, players[i]);

                    }
                    if (checkTaken(i, "colour", players[i].getColour())) {
                        colourBoxes[i].setValue(defaultColour);
                        selectColour(i, players[i]);
                    }
                } else {
                    setConfLabels(i, false);
                }
            }
        } else {
            for (int i = numPlayers; i < oldValue; i++) {
                playerForms[i].setVisible(false);
                playerForms[i].setManaged(false);
            }
        }
    }

    /**
     * @param actionEvent
     */
    public void handleMute(ActionEvent actionEvent) {
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
        this.colourLabels = new Label[]{colourLabel1, colourLabel2, colourLabel3, colourLabel4};
        this.startLabels = new Label[]{startLabel1, startLabel2, startLabel3, startLabel4};
        this.confButtons = new Button[]{confButt1, confButt2, confButt3, confButt4};
        this.startFirstChecks = new CheckBox[]{startFirst1, startFirst2, startFirst3, startFirst4};
        this.tankViews = new ImageView[]{tankView1, tankView2, tankView3, tankView4};
        // key {playerNum, showing confirmations, ready}
        this.playerFormData = new int[][]{{1,0,0},{2,0,0},{3,0,0},{4,0,0}};

        players = new Player[]{
                new Player(1, null), new Player(2, null),
                new Player(3, null), new Player(4, null)};


        try {
            setProfileBoxes(FileHandler.getAllNames());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




        // set each player form
        for (int i = 0; i < 4; i++) {

            int index = i;

            confButtons[index].setOnAction(new EventHandler<ActionEvent>() {
                /**
                 * @param event
                 */
                @Override
                public void handle(ActionEvent event) {
                    Player player = players[index];
                    playerFormData[index][1] = 1;
                    selectProfile(index, player);
                    selectColour(index, player);
                    if(player.hasProfileSet())
                        playerFormData[index][2] = 1;
                    System.out.println("id: "+playerFormData[index][0]+ "ready:" +playerFormData[index][2]);
                }
            });

            startFirstChecks[index].setOnAction(new EventHandler<ActionEvent>() {
                /**
                 * @param event
                 */
                @Override
                public void handle(ActionEvent event) {
                    if (startFirstChecks[index].isSelected()) {
                        setStartingPlayer(index);
                    } else {
                        players[index].setFirst(false);
                    }
                    updateStartLabels(players);
                }
            });
        }

        numPlayersSlider.getValue();

        numPlayersSlider.valueProperty().addListener(new ChangeListener<Number>() {
            /**
             * @param observable
             * @param oldValue
             * @param newValue
             */
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                numPlayers = newValue.intValue();
                updatePlayerForms(oldValue.intValue() < numPlayers, oldValue.intValue());

                numPlayersLabel.setText("Number of Players: " + numPlayers);
            }
        });


        backButt.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * @param event Go back
             */

            @Override
            public void handle(ActionEvent event) {
                new LevelSelectionScene(primaryStage, backgroundMusic.getMediaPlayer());
            }
        });


            beginButt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    System.out.println(Arrays.toString(players));
                    try {
                        new GameScene(primaryStage, new Game(gameName, players), backgroundMusic.getMediaPlayer());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        );


    }
}