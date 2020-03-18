package essoh.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainTP6 extends Application {
	@Override
	public void start(Stage primaryStage) {
		try
		{
	        primaryStage.setTitle("TP6 ESSOH");
			FXMLLoader loader = new FXMLLoader() ;
            loader.setLocation(MainTP6.class.getResource("../../essoh/Vue/VueTP6.fxml"));
            Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}