package controllers;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

public class GameController {
    @FXML
    private StackPane gameBoardPane;

    @FXML
    public void initialize(){
    }

    @FXML
    public StackPane getGameBoardPane(){
        return gameBoardPane;
    }
}
