package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * Player Selection Scene Controller
 * @author James Charnock
 * @StudentID 1909700
 */

public class PlayerSelectionController {

    @FXML
    public HBox hbox = new HBox();

    ObservableList players = FXCollections.observableArrayList();
    @FXML
    public ChoiceBox<String> cbox1= new ChoiceBox<>();

    @FXML
    public ChoiceBox<String> cbox2= new ChoiceBox<>();

    @FXML
    public ChoiceBox<String> cbox3= new ChoiceBox<>();

    @FXML
    public ChoiceBox<String> cbox4= new ChoiceBox<>();

    @FXML
    public VBox vbox1 = new VBox();

    @FXML
    public VBox vbox2 = new VBox();

    @FXML
    public VBox vbox3 = new VBox();

    @FXML
    public VBox vbox4 = new VBox();

    @FXML
    public Label label1 = new Label();

    @FXML
    public Label label2 = new Label();

    @FXML
    public Label label3 = new Label();

    @FXML
    public Label label4 = new Label();

    @FXML
    public Label title;

    @FXML
    public ButtonBar buttBar;

    @FXML
    public Button newPlayerButton;

    @FXML
    public Button beginGameButton;

    @FXML
    public ChoiceBox<String> getChoiceBox(){
        return cbox1;
    }

    @FXML
    public HBox getHBox(){
        return hbox;
    }

    @FXML
    public void initialize() {

        cbox1.setItems(players);
        //System.out.println();
        //playersList.getItems().add(new Button("Button 1"));
    }
}