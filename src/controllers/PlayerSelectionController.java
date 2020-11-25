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
    public ChoiceBox<String> cbox1;

    @FXML
    public ChoiceBox<String> cbox2;

    @FXML
    public ChoiceBox<String> cbox3;

    @FXML
    public ChoiceBox<String> cbox4;

    @FXML
    public Label label1;

    @FXML
    public Label label2;

    @FXML
    public Label label3;

    @FXML
    public Label label4;

    @FXML
    public Label title;

    @FXML
    public ButtonBar buttBar;

    @FXML
    public Button newPlayerButton;

    @FXML
    public Button beginGameButton;

    @FXML
    public ChoiceBox<String> getCbox1(){
        return cbox1;
    }
    @FXML
    public ChoiceBox<String> getCbox2(){
        return cbox2;
    }
    @FXML
    public ChoiceBox<String> getCbox3(){
        return cbox3;
    }
    @FXML
    public ChoiceBox<String> getCbox4(){
        return cbox4;
    }

    @FXML
    public Label getLabel1() {
        return label1;
    }
    @FXML
    public Label getLabel2() {
        return label2;
    }
    @FXML
    public Label getLabel3() {
        return label3;
    }
    @FXML
    public Label getLabel4() {
        return label4;
    }
    @FXML
    public Label getTitle() {
        return title;
    }
    @FXML
    public ButtonBar getButtBar() {
        return buttBar;
    }
    @FXML
    public Button getNewPlayerButton() {
        return newPlayerButton;
    }
    @FXML
    public Button getBeginGameButton() {
        return beginGameButton;
    }

    @FXML
    public ChoiceBox<String> getChoiceBox(){
        return cbox1;
    }

    public String gameName;
    public Stage primaryStage;
    // To do: Create method that doesn't allow you to pick an already chosen player



    public void handleBeginClick(){
        ArrayList<String> players = new ArrayList<>();
        if (cbox1.getValue() != null) {
            players.add(cbox1.getValue());
        }
        if (cbox2.getValue() != null) {
            players.add(cbox2.getValue());
        }
        if (cbox3.getValue() != null) {
            players.add(cbox3.getValue());
        }
        if (cbox4.getValue() != null) {
            players.add(cbox4.getValue());
        }

        String[] players2 = players.toArray(new String[0]);

        if (players2.length > 1) {
            GameScene gameScene = new GameScene(primaryStage, new Game(gameName, players2), backgroundMusic.getMediaPlayer());
        }
    }

    @FXML
    public void setCbox1(ArrayList<String> players){
        cbox1.setItems(observableArrayList(players));
        cbox2.setItems(observableArrayList(players));
        cbox3.setItems(observableArrayList(players));
        cbox4.setItems(observableArrayList(players));
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
