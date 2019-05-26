package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//questo controller e' visto interamento dal fxml (deve essere interno al progetto)
public class Controller {

	@FXML
    public void loginAddettoSicurezza(ActionEvent event)
    {
		alert("OK","", "stampa");
    }
	
	@FXML
    public void registrazioneAddettoSicurezza(ActionEvent event)
    {
		alert("Errore","", "Non è possibile registrarsi da questo client");
    }
	
	private static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}


	
}
