package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import models.Game;
import models.NewPlayer;
import models.PlayerProfile;
import views.scenes.GameScene;

import java.io.FileNotFoundException;
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
    public Button confButt1;

    @FXML
    public Button confButt2;

    @FXML
    public Button confButt3;

    @FXML
    public Button confButt4;
    
    @FXML
    public Button createPlayer;

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
    public ButtonBar buttBar;

    @FXML
    public Button backButt;

    @FXML
    public Button newPlayerButt;

    @FXML
    public Button beginButt;

    public Label[] colourLabels;

    public Label[] profileLabels;

    public Label[] startLabels;

    public ChoiceBox<String>[] profileBoxes;

    public Button[] confButtons;

    public ChoiceBox<String>[] colourBoxes;

    public CheckBox[] startFirstChecks;
    
    public TextField playerName;

    public ImageView[] tankViews;

    public String gameName;
    public Stage primaryStage;


    @FXML
    public void setColourBoxes(){
        for(int i = 0; i<4; i++){
            colourBoxes[i].setItems(observableArrayList("Green","Red", "Blue", "Desert Camo","Auto-Assign"));
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

    public void updateStartLabels(NewPlayer[] players){
        for(int i=0;i<players.length;i++) {
            if(players[i].startFirst){
                startLabels[i].setText("Starting First");
            } else {
                startLabels[i].setText("");
            }
        }
    }

    public void updateTankView(int index, NewPlayer player){
        String imageName;
        switch(player.colour){
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

    public void updateProfileLabel(int index, NewPlayer player) {
        profileLabels[index].setText(player.profileName);
    }

    public void updateColourLabel(int index, NewPlayer player) {
        colourLabels[index].setText(player.colour);
    }
    
    public void createPlayer(ActionEvent event) throws Exception{
    	Stage stage2;
    	Parent root2;
    	if(event.getSource()==newPlayerButt){
    		stage2 = (Stage)newPlayerButt.getScene().getWindow();
    		root2 = FXMLLoader.load(getClass().getResource("CreatePlayer.fxml"));
    		
        }
        else{
            stage2 = (Stage) backButt.getScene().getWindow();
            root2 = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
        }
    	Scene scene2 = new Scene(root2);
        stage2.setScene(scene2);
        stage2.show();
    }
    
    public void submitPlayer(ActionEvent event) throws Exception{
    	Stage stage;
    	Parent root;
    	
    	if(event.getSource()==createPlayer){
    		createPlayer.setOnAction(a -> playerName.getText());
    		new PlayerProfile(playerName.getText(),0,0,0);
    		stage = (Stage)createPlayer.getScene().getWindow();
    		root = FXMLLoader.load(getClass().getResource("CPlayer.fxml"));
        }
    	
        else{
        	stage = (Stage) backButt.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
        }
    	Scene scene2 = new Scene(root);
    	stage.setScene(scene2);
    	stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.profileBoxes = new ChoiceBox[]{profileBox1, profileBox2, profileBox3, profileBox4};
        this.colourBoxes = new ChoiceBox[]{colourBox1,colourBox2, colourBox3, colourBox4};
        this.profileLabels = new Label[]{profileLabel1,profileLabel2,profileLabel3,profileLabel4};
        this.colourLabels = new Label[]{colourLabel1,colourLabel2,colourLabel3,colourLabel4};
        this.startLabels = new Label[]{startLabel1,startLabel2,startLabel3, startLabel4};
        this.confButtons = new Button[]{confButt1,confButt2, confButt3, confButt4};
        this.startFirstChecks = new CheckBox[]{startFirst1,startFirst2,startFirst3,startFirst4};
        this.tankViews=new ImageView[]{tankView1,tankView2,tankView3,tankView4};

    }
}