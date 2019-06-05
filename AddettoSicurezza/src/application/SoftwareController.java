package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SoftwareController {

	@FXML private TextField ultimoAggiornamento;
	private AnchorPane root;
	
	@FXML
	public void indietro(ActionEvent event) throws IOException {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
	}
	
	@FXML
	public void verifica(ActionEvent event) throws IOException {
		ultimoAggiornamento.setText("SmartTraining-1.0");
		inform("Aggiornamento", "Sistema aggiornato", "Ultimo aggiornamento: SmartTraining-1.0");
	} 
	
	private static void inform(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
}
