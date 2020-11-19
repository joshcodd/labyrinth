package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;

public class MoveScreneController {
	@FXML
	private Label welcome;
	
	@FXML
	private Button tol;
	
	
	@FXML
	public void jumpToTwoPlayerScene(ActionEvent event) {
		MainLogic object = new MainLogic();
		Pane view = object.jumpPlayer("ThreePlayer");
	}
	

	
	 
	public void jumpToThreePlayerScene(ActionEvent event) {
		MainLogic object = new MainLogic();
		Pane view = object.jumpPlayer("ThreePlayer");
		
	}
	
	@FXML
	public void jumpToFourPlayerScene(ActionEvent event) {
		MainLogic object = new MainLogic();
		Pane view = object.jumpPlayer("FourPlayer");
	
	}
		
}