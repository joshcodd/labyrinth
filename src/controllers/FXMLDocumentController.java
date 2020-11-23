import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLDocumentController implements Initializable {
	
    
    @FXML
    private Label lbl1,lbl2;
    
    @FXML
    private Button twoPlayers,threePlayers,fourPlayers,backMenu, submit;
    
    @FXML
    private TextField playerinfo1 , playerinfo2 , playerinfo3,playerinfo4;
    
    @FXML
    private Label disIlayerinfo1 , disPlayerinfo2 ;
    
    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
       
        if(event.getSource()==twoPlayers){
            stage = (Stage) twoPlayers.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("TwoPlayer.fxml"));
        }
        
        else if(event.getSource()==threePlayers){
            stage = (Stage) twoPlayers.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ThreePlayer.fxml"));
        }
        
        else if(event.getSource()==fourPlayers){
            stage = (Stage) fourPlayers.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FourPlayer.fxml"));
        }
        
        else{
            stage = (Stage) backMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ChoosePlayer.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void submitPlayer(ActionEvent event) throws Exception{
    	Stage stage2;
    	Parent root2;
    	

    
    	
    	if(event.getSource()==submit){
    		submit.setOnAction(a -> System.out.println(playerinfo1.getText()+ playerinfo2.getText()+ playerinfo3.getText()+ playerinfo4.getText()));
    		stage2 = (Stage)submit.getScene().getWindow();
    		root2 = FXMLLoader.load(getClass().getResource("Submit.fxml"));
    		
        }
    	
        else{
            stage2 = (Stage) backMenu.getScene().getWindow();
            root2 = FXMLLoader.load(getClass().getResource("ChoosePlayer.fxml"));
        }
    	Scene scene2 = new Scene(root2);
        stage2.setScene(scene2);
        stage2.show();
    	
    	
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }       
}