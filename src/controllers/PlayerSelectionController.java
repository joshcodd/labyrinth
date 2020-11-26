package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.Game;
import models.NewPlayer;
import views.scenes.GameScene;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Player Selection Scene Controller
 * @author James Charnock
 * @StudentID 1909700
 */

public class PlayerSelectionController implements Initializable {

    @FXML
    public HBox hbox;

    @FXML
    public MediaView backgroundMusic;

    @FXML
    ArrayList<String> players = new ArrayList<>();

    @FXML
    public Label title;

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
    public Button selectProf1;

    @FXML
    public Button selectProf2;

    @FXML
    public Button selectProf3;

    @FXML
    public Button selectProf4;

    @FXML
    public ChoiceBox<String> colourBox1;

    @FXML
    public ChoiceBox<String> colourBox2;

    @FXML
    public ChoiceBox<String> colourBox3;

    @FXML
    public ChoiceBox<String> colourBox4;

    @FXML
    public Button selectCol1;

    @FXML
    public Button selectCol2;

    @FXML
    public Button selectCol3;

    @FXML
    public Button selectCol4;

    @FXML
    public CheckBox startFirst1;

    @FXML
    public CheckBox startFirst2;

    @FXML
    public CheckBox startFirst3;

    @FXML
    public CheckBox startFirst4;

    @FXML
    public Label confLabel1;

    @FXML
    public Label confLabel2;

    @FXML
    public Label confLabel3;

    @FXML
    public Label confLabel4;

    @FXML
    public Label numPlayersLabel;

    @FXML
    public Slider numPlayersSlider;

    @FXML
    public ButtonBar buttBar;

    @FXML
    public Button backButt;

    @FXML
    public Button newPlayerButt;

    @FXML
    public Button beginButt;

    public Label[] confLabels;

    public ChoiceBox<String>[] profileBoxes;

    public Button[] profileButtons;

    public ChoiceBox<String>[] colourBoxes;

    public Button[] colourButtons;

    public CheckBox[] startFirstChecks;

    @FXML
    public ChoiceBox<String> getChoiceBox(){
        return profileBox1;
    }

    public String gameName;
    public Stage primaryStage;



//GameScene gameScene = new GameScene(primaryStage, new Game(gameName, players2), backgroundMusic.getMediaPlayer());

    @FXML
    public void setColourBoxes(){
        for(int i = 0; i<4; i++){
            colourBoxes[i].setItems(observableArrayList("Green","Red", "Blue", "Desert Camo"));
        }
    }

    @FXML
    public void setProfileBoxes(ArrayList<String> names){
        for(int i = 0; i<4; i++) {
            profileBoxes[i].setItems(observableArrayList(names));
        }
    }

    public void setBackgroundMusic(MediaView backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    public void setGameName(String gameName) {
        this.gameName = "src/gamefiles/levels/" + gameName;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void updateConfLabels(NewPlayer[] players){
        for(int i=0;i<players.length;i++) {
            confLabels[i].setText(players[i].toString());
        }
    }

    public void updateConfLabel(int index, NewPlayer player) {
        confLabels[index].setText(player.toString());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.confLabels= new Label[]{confLabel1, confLabel2, confLabel3, confLabel4};
        this.profileBoxes = new ChoiceBox[]{profileBox1, profileBox2, profileBox3, profileBox4};
        this.colourBoxes = new ChoiceBox[]{colourBox1,colourBox2, colourBox3, colourBox4};
        this.profileButtons = new Button[]{selectProf1,selectProf2, selectProf3, selectProf4};
        this.colourButtons = new Button[]{selectCol1,selectCol2,selectCol3,selectCol4};
        this.startFirstChecks = new CheckBox[]{startFirst1,startFirst2,startFirst3,startFirst4};

    }
}
