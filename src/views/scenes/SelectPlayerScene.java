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
import java.io.InputStream;
import java.util.ArrayList;

public class SelectPlayerScene {
    private Stage primaryStage;
    private ArrayList<String> players;
    private PlayerSelectionController myController;
    private double paneWidth = 900;
    private double paneHeight = 600;


    public SelectPlayerScene (Stage stage) throws FileNotFoundException {
        this.primaryStage = stage;
        this.players = FileHandler.getAllNames();

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("views/layouts/PlayerSelectionView.fxml").openStream());
            PlayerSelectionController controller = loader.getController();
            controller.setCbox1(players);
            Scene scene = new Scene(root, 850, 650);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e){
            e.printStackTrace();

        }
    }
}
