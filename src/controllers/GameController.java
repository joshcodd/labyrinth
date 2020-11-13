package controllers;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

public class GameController {

    @FXML
    public HBox bottomButtons;

    @FXML
    public HBox topButtons;

    @FXML
    private HBox gameBoardPane;

    @FXML
    public void initialize(){
    }

    @FXML
    public HBox getGameBoardPane(){
        return gameBoardPane;
    }

    @FXML
    public Pane getBottomButtons(){
        return bottomButtons;
    }

    @FXML
    public Pane getTopButtons(){
        return topButtons;
    }


}
