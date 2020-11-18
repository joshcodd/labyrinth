package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javax.swing.text.html.ImageView;

public class GameController {

    @FXML
    public HBox bottomButtons;

    @FXML
    public HBox topButtons;


    @FXML
    public Label playerLabel;

    @FXML
    public VBox boardArea;



    @FXML
    public javafx.scene.image.ImageView selectedTile;

    @FXML
    public Button actionButton;

    @FXML
    public Button drawButton;

    @FXML
    private HBox gameBoardPane;


    @FXML
    public void initialize(){
    }

    public Button getActionButton() {
        return actionButton;
    }

    public Button getDrawButton() {
        return drawButton;
    }

    @FXML
    public VBox getBoardArea() {
        return boardArea;
    }

    @FXML
    public javafx.scene.image.ImageView getSelectedTile() {
        return selectedTile;
    }


    @FXML
    public Label getPlayerLabel() {
        return playerLabel;
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
