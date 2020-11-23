package views.scenes;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

                Scene scene = new Scene(root, 600, 600);
                primaryStage.setScene(scene);
                primaryStage.show();

            } catch (Exception e){
                e.getStackTrace();

            }

        }
    }


