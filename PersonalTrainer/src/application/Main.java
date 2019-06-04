package application;
	
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.EsercizioAlimento;


public class Main extends Application {
	
	static public Stage stage;
	static public String usernamePT;
	static public String idPT;
	static public String usernameC;
	static public String tipologiaScheda;
	static public List<EsercizioAlimento> esercizi;

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root,900,600);
			primaryStage.setTitle("Smart Training - Personal Trainer");
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			System.out.println("launched");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
