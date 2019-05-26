package application;

import java.io.IOException;

import accesso.LoginController;
import creazioneID.CreazioneIDController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.UserType;

//questo controller e' visto interamento dal fxml (deve essere interno al progetto)
public class Controller {

	@FXML
	PasswordField password;
	@FXML
	TextField username;
	@FXML
	TextField codiceFiscale;
	@FXML
	Text codiceID;
	
	@FXML
    public void loginAddettoSicurezza(ActionEvent event)
    {
		if(username == null || username.getText().length() == 0 || password == null || password.getText().length() == 0)
			return;
		LoginController lc = new LoginController();
		UserType result;
		try {
			result = lc.verificaCredenziali(username.getText(), password.getText());
			if (result == null || !result.equals(UserType.Amministratore)) {
				alert("Errore","", "Le credenziali inserite non sono valide");
				return;
			}
			//se sei qui l'addetto alla sicurezza è autenticato
			// da qui bisogna caricare fxml della home
			
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomeAddettoSicurezza.fxml"));
			Scene scene = new Scene(root,900,600);
			Main.stage.setTitle("Smart Training - Addetto alla Sicurezza");
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.stage.setScene(scene);		
			Main.stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//inform("OK","", "benvenuto " + username.getText());
    }
	
	private static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@FXML
	public void logout(ActionEvent e) {
		AnchorPane root = null;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root,900,600);
		Main.stage.setTitle("Smart Training - Addetto alla Sicurezza");
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.stage.setScene(scene);		
		Main.stage.show();
	}
	
	@FXML
	public void visualizzaLog(ActionEvent e) {
		AnchorPane root = null;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VisualizzaLog.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root,900,600);
		Main.stage.setTitle("Smart Training - Addetto alla Sicurezza");
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.stage.setScene(scene);		
		Main.stage.show();
	}
	
	@FXML
	public void creaID(ActionEvent e) {
		AnchorPane root = null;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CreaID.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root,900,600);
		Main.stage.setTitle("Smart Training - Addetto alla Sicurezza");
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.stage.setScene(scene);		
		Main.stage.show();
	}
	
	@FXML
	public void goToHome(ActionEvent event) {
		AnchorPane root = null;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomeAddettoSicurezza.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root,900,600);
		Main.stage.setTitle("Smart Training - Addetto alla Sicurezza");
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.stage.setScene(scene);		
		Main.stage.show();
	}
	
	@FXML
	public void generaID(ActionEvent event) {
		CreazioneIDController idc = new CreazioneIDController();
		if(codiceFiscale == null || codiceFiscale.getText().length() == 0)
			return;
		String id = idc.generaCodiceID(codiceFiscale.getText());
		if(id == null) {
			alert("Errore","", "Impossibile generare il codice ID. Codice fiscale non valido");
			return;
		}
		codiceID.setText(id);	
	}

	@SuppressWarnings("unused")
	private static void inform(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	
}
