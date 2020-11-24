package controllers;

import models.Game;
import models.MessageOfTheDay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import views.scenes.GameScene;
import views.scenes.SelectPlayerScene;

import java.io.FileNotFoundException;

public class MenuController  {
    public Button newGame;
    public Button loadGame;
    @FXML
    private Label message;
    

    private Stage primaryStage;

    @FXML
    public void initialize(){
        message.setText(String.valueOf(new MessageOfTheDay()));
    }

    public void handleButtonNewGame(ActionEvent actionEvent) throws FileNotFoundException {
        newGame.setText("opening");
        SelectPlayerScene menu = new SelectPlayerScene(primaryStage);
    }

    public void handleButtonLoadGame(ActionEvent actionEvent) {
        loadGame.setText("opening");
        Game game = new Game("src/levels/game", new String[]{"Josh", "Neil", "Andreas"});
        GameScene gameScene = new GameScene(primaryStage, game);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
