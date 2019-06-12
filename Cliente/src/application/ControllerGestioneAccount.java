package application;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

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
    private String vecchiaEmail, vecchioNumero, vecchioIndirizzo;
    
  //questa funzione e' invocata automaticamente dal controller ad ogni caricamenti di file fxml
  	@FXML private void initialize() {
  		getDatiUtente();
  	}

	private void getDatiUtente() {
		GestioneAccountController gac = new GestioneAccountController();
		Cliente c = gac.getDatiCliente(Main.idCliente);
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
		
		tipologia.setEditable(false);
		nome.setEditable(false);
		cognome.setEditable(false);
		tessera.setEditable(false);
		numero.setEditable(false);
		indirizzo.setEditable(false);
		luogoNascita.setEditable(false);
		dataNascita.setEditable(false);
		email.setEditable(false);
		codiceFiscale.setEditable(false);
		
		elimina.setDisable(false);
		indietro.setDisable(false);
		modifica.setDisable(false);
		conferma.setDisable(true);
		annulla.setDisable(true);
	}
	
	@FXML
	public void eliminaAccount(ActionEvent event)
    {
		GestioneAccountController gac = new GestioneAccountController();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminazione Account");
		alert.setHeaderText("Attenzione, si sta per eliminare il proprio account");
		alert.setContentText("Sicuro di voler procedere?");
		
		elimina.setDisable(true);
		indietro.setDisable(true);
		modifica.setDisable(true);
		conferma.setDisable(true);
		annulla.setDisable(true);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){

			if(gac.eliminaAccount(Main.idCliente)) {
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
		elimina.setDisable(false);
		indietro.setDisable(false);
		modifica.setDisable(false);
		conferma.setDisable(true);
		annulla.setDisable(true);
			
    } 
	
	@FXML
	public void modifica(ActionEvent event)
    {
		vecchiaEmail = email.getText();
		vecchioNumero = numero.getText();
		vecchioIndirizzo = indirizzo.getText();
		numero.setEditable(true);
		indirizzo.setEditable(true);
		email.setEditable(true);
		
		tipologia.setDisable(true);
		nome.setDisable(true);
		cognome.setDisable(true);
		tessera.setDisable(true);
		luogoNascita.setDisable(true);
		dataNascita.setDisable(true);
		codiceFiscale.setDisable(true);
		
		elimina.setDisable(true);
		conferma.setDisable(false);
		annulla.setDisable(false);
		modifica.setDisable(true);
			
    } 
	
	@FXML
	public void conferma(ActionEvent event)
    {
		GestioneAccountController gac = new GestioneAccountController();
		String numeroParametro = null, indirizzoParametro =  null, emailParametro = null;
		if(numero.getText().equals("")) {
			alert("Modifica dati", "", "Il numero inserito risulta scorretto");
			return;		
		}
		if(email.getText().equals("") || !emailRegex(email.getText()) || !email.getText().contains("@") || !email.getText().contains(".")) {
			alert("Modifica dati", "", "L'email inserita risulta scorretta");
			return;
		}
		if(indirizzo.getText().equals("")) {
			alert("Modifica dati", "", "L'indirizzo inserito risulta scorretto");
			return;
		}
		emailParametro = email.getText();
		numeroParametro = numero.getText();
		indirizzoParametro = indirizzo.getText();
		if(gac.modificaDati(Main.idCliente, emailParametro, indirizzoParametro, numeroParametro)) {
			inform("Modifica dati", "", "Modifica effettuata con successo");
			numero.setEditable(false);
			indirizzo.setEditable(false);
			email.setEditable(false);
			
			tipologia.setDisable(false);
			nome.setDisable(false);
			cognome.setDisable(false);
			tessera.setDisable(false);
			luogoNascita.setDisable(false);
			dataNascita.setDisable(false);
			codiceFiscale.setDisable(false);
			
			modifica.setDisable(false);
			elimina.setDisable(false);
			conferma.setDisable(true);
			annulla.setDisable(true);
		}
		else {
			alert("Modifica dati", "", "Modifica fallita. Riprovare");
		}
			
    } 
	
	private boolean emailRegex(String mail) {
		return Pattern.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", mail);
	}

	@FXML
	public void annulla(ActionEvent event)
    {
		numero.setEditable(false);
		indirizzo.setEditable(false);
		email.setEditable(false);
		numero.setText(vecchioNumero);
		indirizzo.setText(vecchioIndirizzo);
		email.setText(vecchiaEmail);
		
		tipologia.setDisable(false);
		nome.setDisable(false);
		cognome.setDisable(false);
		tessera.setDisable(false);
		luogoNascita.setDisable(false);
		dataNascita.setDisable(false);
		codiceFiscale.setDisable(false);
		
		modifica.setDisable(false);
		elimina.setDisable(false);
		conferma.setDisable(true);
		annulla.setDisable(true);
			
    }
	
	@FXML
	public void indietro(ActionEvent event) throws IOException {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomeCliente.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
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
