package application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import accesso.LoginController;
import creazioneID.CreazioneIDController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import log.LogController;
import model.Entry;
import model.UserType;
import util.Utilities;

//questo controller e' visto interamento dal fxml (deve essere interno al progetto)
public class Controller {

	@FXML
	private PasswordField password;
	@FXML
	private TextField username;
	@FXML
	private TextField codiceFiscale;
	@FXML
	private TextArea codiceID;
	@FXML private TableView<Entry> tabella;
    @FXML private TableColumn<Entry, String> idCol;
    @FXML private TableColumn<Entry, String> dataOraCol;
    @FXML private TableColumn<Entry, String> descCol;
    @FXML private TextField filtroID;
    @FXML private ComboBox<String> oraInizio;
    @FXML private ComboBox<String> oraFine;
    @FXML private ComboBox<String> minInizio;
    @FXML private ComboBox<String> minFine;
    @FXML private DatePicker dataInizio;
    @FXML private DatePicker dataFine;
	AnchorPane root;
	LogController log = new LogController();
	
	List<Entry> entries = new ArrayList<Entry>();
	
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
				log.scriviMessaggio(LocalDateTime.now(), "Tentativo di login fallito (Addetto alla Sicurezza)");
				return;
			}
			//se sei qui l'addetto alla sicurezza è autenticato
			// da qui bisogna caricare fxml della home
			log.scriviMessaggio(LocalDateTime.now(), "Autenticazione effettuata con successo (Addetto alla Sicurezza)");
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomeAddettoSicurezza.fxml"));
			Scene scene = new Scene(root,900,600);
			Main.stage.setScene(scene);		

		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
	
	private static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@FXML
	public void sistema(ActionEvent event) throws IOException {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/GestioneSoftware.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
	}
	
	
	@FXML
	public void logout(ActionEvent e) {
		root = null;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		log.scriviMessaggio(LocalDateTime.now(), "Logout effettuato con successo (Addetto alla Sicurezza)");
		Scene scene = new Scene(root,900,600);
		Main.stage.setScene(scene);		
	}
	
	@FXML
	public void visualizzaLog(ActionEvent e) {
		root = null;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VisualizzaLog.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root,900,600);
		Main.stage.setScene(scene);

	}
	
	//questa funzione e' invocata automaticamente dal controller ad ogni caricamenti di file fxml
	@FXML private void initialize() {
		getLogFile();
	}
	
	private void getLogFile() {
		LogController lc = new LogController();
		
		//la tabella sara' non nulla quando sara' caricato il file VisualizzaLog.fxml
		//di conseguenza anche le combobox
		if(tabella != null) {
		    oraInizio.setValue("00");
		    oraFine.setValue("23");
		    minInizio.setValue("00");
		    minFine.setValue("59");
			lc.scriviMessaggio(LocalDateTime.now() ,"Richiesta la visualizzazione dei log di sistema");
			entries = lc.getLog();
			dataOraCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("stringDataOra"));
	        idCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("idUtente"));
	        descCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("descrizione"));
	        Collections.sort(entries);
	        tabella.getItems().setAll(entries);
			
		}
	}
	
	@FXML
	public void creaID(ActionEvent e) {
		root = null;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CreaID.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root,900,600);
		Main.stage.setScene(scene);		
	}
	
	@FXML
	public void goToHome(ActionEvent event) {
		root = null;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomeAddettoSicurezza.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root,900,600);
		Main.stage.setScene(scene);		
	}
	
	@FXML
	public void generaID(ActionEvent event) {
		CreazioneIDController idc = new CreazioneIDController();
		if(codiceFiscale == null || codiceFiscale.getText().length() == 0)
			return;
		if(!isCodiceFiscale(codiceFiscale.getText())) {
			alert("Attenzione","Errore: codice fiscale non valido", "Inserire un codice fiscale valido");
			return;
		}
		String id = idc.generaCodiceID(codiceFiscale.getText());
		if(id == null) {
			alert("Errore","", "Impossibile generare il codice ID. Codice fiscale gia inserito nel sistema");
			return;
		}
		codiceID.setText(id);	
	}
	
	private boolean isCodiceFiscale(String cf) {
		return Pattern.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$", cf);
	}
	
	@FXML
	public void applicaFiltro(ActionEvent event) {
		if(dataInizio.getValue() != null && dataFine.getValue() != null && 
				dataFine.getValue().compareTo(dataInizio.getValue()) < 0 ) {
			alert("Errore ricerca","Filtri per data errati", "La data di inizio deve anticipare la data di fine validita");
			return;
		}
		LogController lc = new LogController();
		entries = lc.getLog();
		LocalDateTime inizio = null, fine = null;
		
		if(dataInizio.getValue() != null &&
				oraInizio.getValue() != null && 
				minInizio.getValue() != null &&
				!oraInizio.getValue().equals("") &&
				!minInizio.getValue().equals("")	)
			inizio = LocalDateTime.parse(dataInizio.getValue().format(Utilities.formatterData)+ " " +
											oraInizio.getValue()+":"+minInizio.getValue(), Utilities.formatterDataOra);
		
		if(dataFine.getValue() != null &&
				oraFine.getValue() != null &&
				minFine.getValue() != null &&
				!oraFine.getValue().equals("") &&
				!minFine.getValue().equals("")	 )
			fine = LocalDateTime.parse(dataFine.getValue().format(Utilities.formatterData)+ " " +
										oraFine.getValue()+":"+minFine.getValue(), Utilities.formatterDataOra);
		
		entries = lc.applicaFiltro(entries, inizio, fine, filtroID.getText());
		
		dataOraCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("stringDataOra"));
        idCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("idUtente"));
        descCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("descrizione"));
        
        Collections.sort(entries);
        tabella.getItems().setAll(entries);
	}
	

}
