package controllers;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import views.scenes.LevelSelectionScene;
import javafx.scene.control.ChoiceBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import static javafx.collections.FXCollections.observableArrayList;
import java.io.File;
import java.util.Objects;
import javax.swing.*;


public class LevelSelectionController {

    @FXML
    public Button backButton;

    @FXML
    public Button confirmButton;

    @FXML
    public ChoiceBox<String> dropdown;

    public void setDropdown(){
        dropdown.setItems(getLevels());
    }

    public ObservableList<String> getLevels(){
        File folder = new File("src/levels");
        ObservableList<String> listOfFiles = observableArrayList();
        System.out.println(folder.exists());
        System.out.println(folder.isDirectory());


        for (File i : Objects.requireNonNull(folder.listFiles())) {
            System.out.println(i.getName());
            listOfFiles.add(i.getName());
        }
        return listOfFiles;
    }

    public void handleConfirm(ActionEvent actionEvent) {
        confirmButton.setText("doing nothing");
    }

    public void handleBack(ActionEvent actionEvent) {
        backButton.setText("doing nothing");
    }
}

