package views.scenes;
/**
 * Player Selection scene class
 * @author James Charnock
 * @StudentID 1909700
 */
import game.FileHandler;
import game.Player;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import controllers.PlayerSelectionController;


import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SelectPlayerScene {
    private Stage primaryStage;
    private ArrayList<String> players;
    private PlayerSelectionController controller;
    private double paneWidth = 900;
    private double paneHeight = 600;


    public SelectPlayerScene (Stage stage, ArrayList<String> players){
        this.primaryStage = stage;
        this.players = players;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/layouts/PlayerSelectionView.fxml"));
            Parent root = loader.load();
            controller = loader.getController();

            controller.cbox1.getItems().addAll(FXCollections.observableArrayList(players));
            controller.cbox2.setItems(FXCollections.observableArrayList(players));
            controller.cbox3.setItems(FXCollections.observableArrayList(players));
            controller.cbox4.setItems(FXCollections.observableArrayList(players));

            controller.getChoiceBox().getItems().addAll(FXCollections.observableArrayList(players));

            Button newButt = new Button();
            controller.getHBox().getChildren().add(newButt);



            Scene scene = new Scene(root, 850, 650);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e){
            e.printStackTrace();

        }
    }
}
