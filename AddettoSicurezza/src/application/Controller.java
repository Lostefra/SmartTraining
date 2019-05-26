package application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
		Main.stage.onShowingProperty();
	/*	Main.stage.setOnShowing(new EventHandler<WindowEvent>() {
			//qui si caricano i log da mostrare all'inizio
		    @Override
		    public void handle(WindowEvent event) {
		    	System.out.print("sono nel handle\n");
		        getLogFile();
		    }

		});*/
		Main.stage.setOnShowing(event -> getLogFile());
		Main.stage.show();
	}
	
	private void getLogFile() {
		LogController lc = new LogController();
		entries = lc.getLog();
		dataOraCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("stringDataOra"));
        idCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("idUtente"));
        descCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("descrizione"));
        tabella.getItems().setAll(entries);
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
	
	@FXML
	public void applicaFiltro(ActionEvent event) {
		LogController lc = new LogController();
		entries = lc.getLog();
		LocalDateTime inizio = null, fine = null;
		if(dataInizio.getValue() != null && oraInizio.getValue() != null && minInizio.getValue() != null &&
				!oraInizio.getValue().equals("") && !minInizio.getValue().equals(""))
			inizio = LocalDateTime.parse(dataInizio.getValue().format(Utilities.formatterData)+ " " +
					oraInizio.getValue()+":"+minInizio.getValue(), Utilities.formatterDataOra);
		if(dataFine.getValue() != null && oraFine.getValue() != null && minFine.getValue() != null &&
				!oraFine.getValue().equals("") && !minFine.getValue().equals(""))
			fine = LocalDateTime.parse(dataFine.getValue().format(Utilities.formatterData)+ " " +
					oraFine.getValue()+":"+minFine.getValue(), Utilities.formatterDataOra);
		entries = lc.applicaFiltro(entries, inizio, fine, filtroID.getText());
		dataOraCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("stringDataOra"));
        idCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("idUtente"));
        descCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("descrizione"));
        tabella.getItems().setAll(entries);
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
