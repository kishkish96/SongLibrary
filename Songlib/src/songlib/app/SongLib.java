/*
 * Group #: 79
 * 
 * Kishan Patel (kp644)
 * Neal D Patel (ndp73)
 * 
 */

package songlib.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import songlib.view.SongLibController;


public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/SongLib.fxml"));
		GridPane grid = (GridPane)loader.load();
		
		SongLibController slc = loader.getController();
		slc.start(primaryStage);
		
		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
