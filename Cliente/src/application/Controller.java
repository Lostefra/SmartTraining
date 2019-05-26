package application;

import java.io.IOException;

import accesso.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.UserType;

//questo controller e' visto interamento dal fxml (deve essere interno al progetto)
public class Controller {

	@FXML
	PasswordField password;
	@FXML
	TextField username;
	
	@FXML
    public void loginCliente(ActionEvent event)
    {
		if(username == null || username.getText().length() == 0 || password == null || password.getText().length() == 0)
			return;
		LoginController lc = new LoginController();
		UserType result;
		try {
			result = lc.verificaCredenziali(username.getText(), password.getText());
			//System.out.println(username.getText() + " " + password.getText() + " " + result);
			if (result == null || !result.equals(UserType.Cliente)) {
				alert("Errore","", "Le credenziali inserite non sono valide");
				return;
			}
			//se sei qui il cliente è autenticato
			// da qui bisogna caricare fxml della home
		} catch (IOException e) {
			e.printStackTrace();
		}
		//inform("OK","", "benvenuto " + username.getText());
    }
	
	@FXML
    public void registrazioneCliente(ActionEvent event)
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
