package views.scenes;

import controllers.MenuController;
import controllers.PlayerSelectionController;

import game.MessageOfTheDay;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Label;


/**
 * dasdas
 */
public class MenuScene {
        private Stage primaryStage;

        public MenuScene (Stage stage){
            this.primaryStage = stage;
            stage.setTitle("Labyrinth by Ravensburger");
            try {
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getClassLoader().getResource("views/layouts/MenuView.fxml").openStream());
                MenuController controller = loader.getController();

                controller.setPrimaryStage(stage);
                Scene scene = new Scene(root, 600, 600);
                primaryStage.setScene(scene);
                primaryStage.show();

            } catch (Exception e){
                e.getStackTrace();

            }

        }
    }


