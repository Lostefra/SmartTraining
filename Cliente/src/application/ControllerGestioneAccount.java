package application;

import java.io.IOException;
import java.util.Optional;

import gestioneAccount.GestioneAccountController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Cliente;
import util.Utilities;

public class ControllerGestioneAccount {

	@FXML private TextField tipologia;
    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField numero;
    @FXML private TextField indirizzo;
    @FXML private TextField luogoNascita;
    @FXML private TextField dataNascita;
    @FXML private TextField codiceFiscale;
    @FXML private TextField tessera;
    @FXML private TextField email;
    @FXML private Button annulla;
    @FXML private Button conferma;
    @FXML private Button modifica;
    @FXML private Button indietro;
    @FXML private Button elimina;
    private AnchorPane root;
    
  //questa funzione e' invocata automaticamente dal controller ad ogni caricamenti di file fxml
  	@FXML private void initialize() {
  		getDatiUtente();
  	}

	private void getDatiUtente() {
		GestioneAccountController gac = new GestioneAccountController();
		
		Cliente c = gac.getDatiCliente("insanelosteID");
 		tipologia.setText("Cliente");
		nome.setText(c.getNome());
		cognome.setText(c.getCognome());
		tessera.setText(c.getTes().getNumero() + "");
		numero.setText(c.getNumTelefono());
		indirizzo.setText(c.getIndirizzoResidenza());
		luogoNascita.setText(c.getLuogoNascita());
		dataNascita.setText(c.getDataDiNascita().format(Utilities.formatterData));
		email.setText(c.getEmail());
		codiceFiscale.setText(c.getCodiceFiscale());
	}
	
	public void eliminaAccount(ActionEvent event)
    {
		GestioneAccountController gac = new GestioneAccountController();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminazione Account");
		alert.setHeaderText("Attenzione, si sta per eliminare il proprio account");
		alert.setContentText("Sicuro di voler procedere?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			if(gac.EliminaAccount("idREMOVE")) {
				inform("Eliminazione Account", "", "Eliminazione account effettuata con successo");
				//se sei qui l'account e' stato eliminato correttamente
				// da qui bisogna caricare fxml del
				try {
					root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				Scene scene = new Scene(root,900,600);
				Main.stage.setScene(scene);		
			}
			else {
				inform("Eliminazione Account", "", "Eliminazione account fallita!");
			}
			
		} else {
			inform("Eliminazione Account", "", "Operazione annullata");
		}
			
    } 
	
	
	private static void inform(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
}
