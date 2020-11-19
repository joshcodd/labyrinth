package application;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MainLogic {
private Pane view;


	public Pane jumpPlayer(String fileName) {
		
		try {
			URL fileURL = Mainrun.class.getResource("/mainrun/" + fileName + ".fxml");
			if (fileURL == null) {
				throw new java.io.FileNotFoundException("NO SUCH FILE");
			}

			view = new FXMLLoader().load(fileURL);

		} catch (Exception e) {
			System.out.println("Error again. PLease check");
		}
		return view;
	}

}
