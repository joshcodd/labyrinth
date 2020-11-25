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

public class PlayerSelectionController{

    @FXML
    public HBox hbox;

    @FXML
    public MediaView backgroundMusic;

    @FXML
    ObservableList<String> players = observableArrayList();

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
    public ButtonBar buttBar;

    @FXML
    public Button backButt;

    @FXML
    public Button newPlayerButt;

    @FXML
    public Button beginButt;
    

    @FXML
    public ChoiceBox<String> getChoiceBox(){
        return profileBox1;
    }

    public String gameName;
    public Stage primaryStage;
    // To do: Create method that doesn't allow you to pick an already chosen player
    public void setprofileBox1(ArrayList<String> names){
        profileBox1.setItems(observableArrayList(names));
    }

    public void setProfileBox2(ArrayList<String> names){
        profileBox2.setItems(observableArrayList(names));
    }
    public void setProfileBox3(ArrayList<String> names){
        profileBox3.setItems(observableArrayList(names));
    }
    public void setProfileBox4(ArrayList<String> names){
        profileBox4.setItems(observableArrayList(names));
    }



    public void handleBeginClick(){
        ArrayList<String> players = new ArrayList<>();
        if (profileBox1.getValue() != null) {
            players.add(profileBox1.getValue());
        }
        if (profileBox2.getValue() != null) {
            players.add(profileBox2.getValue());
        }
        if (profileBox3.getValue() != null) {
            players.add(profileBox3.getValue());
        }
        if (profileBox4.getValue() != null) {
            players.add(profileBox4.getValue());
        }

        String[] players2 = players.toArray(new String[0]);

        if (players2.length > 1) {
            GameScene gameScene = new GameScene(primaryStage, new Game(gameName, players2), backgroundMusic.getMediaPlayer());
        }
    }

    @FXML
    public void setProfileBox(ArrayList<String> names){
        profileBox1.setItems(observableArrayList(names));
        profileBox2.setItems(observableArrayList(names));
        profileBox3.setItems(observableArrayList(names));
        profileBox4.setItems(observableArrayList(names));
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
}
