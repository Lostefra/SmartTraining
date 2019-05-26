package application;

import java.io.IOException;

import accesso.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.UserType;

public class Controller {

	@FXML
	PasswordField password;
	@FXML
	TextField username;
	
	@FXML
    public void loginPersonalTrainer(ActionEvent event)
    {
		if(username == null || username.getText().length() == 0 || password == null || password.getText().length() == 0)
			return;
		LoginController lc = new LoginController();
		UserType result;
		try {
			result = lc.verificaCredenziali(username.getText(), password.getText());
			if (result == null || !result.equals(UserType.PersonalTrainer)) {
				alert("Errore","", "Le credenziali inserite non sono valide");
				return;
			}
			
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomePersonalTrainer.fxml"));
			Scene scene = new Scene(root,900,600);
			Main.stage.setTitle("Smart Training - PersonalTrainer");
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.stage.setScene(scene);		
			Main.stage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//inform("OK","", "benvenuto " + username.getText());
    }
	
	@FXML
    public void registrazionePersonalTrainer(ActionEvent event)
    {
		inform("Segue la registrazione","", "Al posto del prompt vanno mostrata la view");
    }
	
	private static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	
	private static void inform(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	
}
