package controllers;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

public class GameController {
    @FXML
    public Pane gameBoardLeft;

    @FXML
    public Pane gameBoardRight;

    @FXML
    public Pane gameBoardTop;

    @FXML
    public Pane gameBoardBottom;

    @FXML
    private StackPane gameBoardPane;

    @FXML
    public void initialize(){
    }

    @FXML
    public StackPane getGameBoardPane(){
        return gameBoardPane;
    }

    @FXML
    public Pane getButtonLeft(){
        return gameBoardLeft;
    }

    @FXML
    public Pane getButtonRight(){
        return gameBoardRight;
    }

    @FXML
    public Pane getButtonTop(){
        return gameBoardTop;
    }

    @FXML
    public Pane getButtonBottom(){
        return gameBoardBottom;
    }
}
