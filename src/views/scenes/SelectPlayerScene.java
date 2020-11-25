package views.scenes;
/**
 * Player Selection scene class
 * @author James Charnock
 * @StudentID 1909700
 */
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import models.FileHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.PlayerSelectionController;

import javafx.scene.media.MediaPlayer;
import models.NewPlayer;


import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SelectPlayerScene {
    private Stage primaryStage;
    private ArrayList<String> profiles;
    private NewPlayer[] players = new NewPlayer[]{new NewPlayer(),new NewPlayer(), new NewPlayer(), new NewPlayer()};
    private PlayerSelectionController controller;
    private int numPlayers = 4;

    public boolean checkNotTaken(int id, String field, String value){
        int count = 0;
        boolean taken = true;
        for(int i =0; i<numPlayers;i++){
            if(i==id){
                continue;
            }
            if(field.equals("name")){
                if(players[i].profileName.equals(value)){
                    count+=1;
                }
            } else if (field.equals("colour")){
                if(players[i].colour.equals(value)){
                    count+=1;
                }
            } else {System.out.println("checkTaken: invalid field");}
        }
        if(count<1){
            taken = false;
        }
        return !taken;
    }

    public void selectPlayer(int index){
        String value = controller.profileBoxes[index].getValue();
        if(value != null){
            if(checkNotTaken(index, "name", value)){
                players[index].profileName = value;
            } else {
                controller.confLabels[index].setText("Profile taken.");
            }
        }
    }
    public void selectColour(int index){
        String colour = controller.colourBoxes[index].getValue();
        if(colour!= null) {
            if (checkNotTaken(index, "colour", colour)) {
                players[index].colour = colour;
            } else {
                controller.confLabels[index].setText("Colour taken. ");
            }
        }
    }

    public void setStartingPlayer(int index){
        for(int i = 0; i<4; i++){
            if(i!=index) {
                if (players[i].startFirst) {
                    players[i].startFirst = false;
                }
                controller.startFirstChecks[i].setSelected(false);
            }
        }
        players[index].startFirst = true;
    }


    public SelectPlayerScene (Stage stage, String level, MediaPlayer backgroundMusic) {
        this.primaryStage = stage;
        try {
            profiles = FileHandler.getAllNames();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("views/layouts/PlayerSelectionView.fxml").openStream());
            this.controller = loader.getController();
            controller.setProfileBoxes(profiles);
            controller.setColourBoxes();
            Scene scene = new Scene(root, 1200, 650);
            scene.getStylesheets().add("styles.css");
            primaryStage.setScene(scene);
            primaryStage.show();

            numPlayers = (int)controller.numPlayersSlider.getValue();
            // set groups
            for(int i = 0; i<numPlayers; i++){


                int index = i;

                controller.profileButtons[index].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        selectPlayer(index);
                        controller.updateConfLabel(index, players[index]);
                    }
                });
                controller.colourButtons[index].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        selectColour(index);
                        controller.updateConfLabel(index, players[index]);
                    }
                });
                controller.startFirstChecks[index].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(controller.startFirstChecks[index].isSelected()) {
                            setStartingPlayer(index);
                        }else{
                            players[index].startFirst=false;
                        }
                        controller.updateConfLabels(players);
                    }
                });
            }



            controller.numPlayersSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    numPlayers = newValue.intValue();
                    controller.numPlayersLabel.setText("Number of Players: " + numPlayers);
                }
            });




        } catch (Exception e){
            e.printStackTrace();

        }
    }
}
