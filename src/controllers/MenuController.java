package controllers;

import game.MessageOfTheDay;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class MenuController  {
    public Button newGame;
    public Button loadGame;
    @FXML
    private Label message;

    @FXML
    public void initialize(){
        message.setText(String.valueOf(new MessageOfTheDay()));
    }

    public void handleButtonNewGame(ActionEvent actionEvent) {
        newGame.setText("opening");
    }

    public void handleButtonLoadGame(ActionEvent actionEvent) {
        loadGame.setText("opening");
    }
}
