package views.scenes;


import controllers.LevelSelectionController;
import controllers.MenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LevelSelectionScene {
    private Stage primaryStage;
    private LevelSelectionController controller;


    public LevelSelectionScene(Stage stage){
        this.primaryStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("views/layouts/LevelSelectionView.fxml").openStream());
            Scene scene = new Scene(root, 600, 600);
            LevelSelectionController controller = loader.getController();
            controller.setDropdown();

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e){
            e.printStackTrace();

        }
    }
}
