package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import accesso.LoginController;
import accesso.RegistrazioneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.ObservableRichiesta;
import model.PersonalTrainer;
import model.Richiesta;
import model.RichiestaPianoNutrizionale;
import model.RichiestaSchedaAllenamento;
import model.UserType;
import richieste.RichiesteController;
import util.Utilities;

public class Controller {

	@FXML private PasswordField password;
	@FXML private TextField username;
	@FXML private TextField regNome;
	@FXML private TextField regCognome;
	@FXML private TextField regEmail;
	@FXML private TextField regCodFisc;
	@FXML private TextField regLuogoNascita;
	@FXML private TextField regTel;
	@FXML private TextField regUsername;
	@FXML private PasswordField regPassword;
	@FXML private PasswordField regConfermaPassword;
	@FXML private DatePicker regDataNascita;
	@FXML private TextField regCodiceID;
	@FXML private TextField regResidenza;
	@FXML private TextField nomeRichiesta;
	@FXML private TextField cognomeRichiesta;
	@FXML private TextField tipologiaRichiesta;
	@FXML private TextField dataInizioRichiesta;
	@FXML private TextField durataRichiesta;
	@FXML private TextField allenamentiRichiesta;
	@FXML private TextField pesoRichiesta;
	@FXML private TextField altezzaRichiesta;
	@FXML private TextArea allergeniRichiesta;
	@FXML private TextArea noteRichiesta;
	@FXML private TableView<ObservableRichiesta> homeTab;
	@FXML private TableColumn<ObservableRichiesta, String> idNome;
	@FXML private TableColumn<ObservableRichiesta, String> idCognome;
	@FXML private TableColumn<ObservableRichiesta, String> idTipologia;
	@FXML private TableColumn<ObservableRichiesta, String> idDataOra;
	
	public PersonalTrainer pt;
	
	private AnchorPane root;
	
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
				alert("Errore","", "Le credenziali inserite non sono valide o l'utente inserito non ha può accedere da questo Client");
				return;
			}
			
			
			Main.usernamePT = username.getText();
			
			Main.stage.setTitle("Smart Training - PersonalTrainer");
			viewHomePersonalTrainer(event);
			Main.stage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@FXML
	public void viewHomePersonalTrainer(ActionEvent event) throws IOException {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomePersonalTrainer.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
		
	}
	
	@FXML
	public void viewStoricoSchede(ActionEvent event) throws IOException {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/StoricoSchede PersonalTrainer.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
	}
	
	@FXML
	public void viewGestioneAccount (ActionEvent event) throws IOException {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Gestione Account.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
	}
	
	@FXML 
	private void initialize() throws NumberFormatException, IOException {
		getRichieste();
	}
	
	private void getRichieste() throws NumberFormatException, IOException {
		RichiesteController rc = new RichiesteController();
		
		if(homeTab != null) {
			pt = Utilities.getPersonalTrainer(Main.usernamePT);

			List<Richiesta> richieste = new ArrayList<>(rc.visualizzaRichieste(pt));
			List<ObservableRichiesta> observableRichieste = new ArrayList<>();
			for(Richiesta r : richieste)
				observableRichieste.add(new ObservableRichiesta(r.getId(), r.getCliente().getNome(), r.getCliente().getCognome(), r, r.getDataOra(), r.getDataOra().format(Utilities.formatterDataOra)));
			
			idNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			idCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
			idTipologia.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
			idDataOra.setCellValueFactory(new PropertyValueFactory<>("dataOraStringa"));
			homeTab.getItems().setAll(observableRichieste);
			
			homeTab.setOnMouseClicked(e -> {
						try {
							fill(homeTab.getSelectionModel().getSelectedItem());
						} catch (NumberFormatException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
			});

		}
	}
	
	@FXML
    public void viewRegistrazionePersonalTrainer(ActionEvent event) throws IOException
    {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Registrazione.fxml"));
		
		Main.stage.setScene(new Scene(root,900,600));
		
		/*regDataNascita.setDayCellFactory(d -> new DateCell() {
            @Override 
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isAfter(LocalDate.now().minusYears(14)) || item.isBefore(LocalDate.now().minusYears(90)));
            }});
		*/
    }
	 
	@FXML
	public void viewLogin (ActionEvent event) throws IOException {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
	}
	
	@FXML
	public void confermaRegistrazione (ActionEvent event) throws IOException {
		root = null;
		
		if (!checkValuesRegistrazione()) {
			return;
		}
		
		RegistrazioneController controller = new RegistrazioneController();
		String result = controller.registrazione(regUsername.getText(), regPassword.getText(), regNome.getText(), regCognome.getText(),
				regEmail.getText(), regCodFisc.getText(), regDataNascita.getValue(), regLuogoNascita.getText(),
				regResidenza.getText(), regTel.getText(), regCodiceID.getText());
		if (!result.equals("T")) {
			alert("Errore", "Errore registrazione", result);
			return;
		}
		
		viewLogin(event);
		inform("Smart Training", "Registrazione avvenuta con successo!", "Benvenuto in Smart Training");
	}

	
	private void fill (ObservableRichiesta observableRichiesta) throws NumberFormatException, IOException {
		
		RichiesteController rc = new RichiesteController();
		Richiesta richiesta = null;
		
		List<Richiesta> richieste = new ArrayList<>();
		
		richieste = rc.visualizzaRichieste(Utilities.getPersonalTrainer(Main.usernamePT));
		
		boolean found = false;
		
		for (int i = 0; i<richieste.size() && !found; i++) {
/*			if(richieste.get(i) == null) {
				System.out.println("richieste.get(i) " +i );
			}
			if(observableRichiesta == null) {
				System.out.println("observableRichiesta");
			}
*/			if (richieste.get(i).getId().equals(observableRichiesta.getId())) {
				richiesta = richieste.get(i);
				found = true;
			}
		}
		
		allergeniRichiesta.clear();
		pesoRichiesta.clear();
		altezzaRichiesta.clear();
		allenamentiRichiesta.clear();
		nomeRichiesta.clear();
		cognomeRichiesta.clear();
		tipologiaRichiesta.clear();
		dataInizioRichiesta.clear();
		durataRichiesta.clear();
		allenamentiRichiesta.clear();
		noteRichiesta.clear();
		allergeniRichiesta.setWrapText(true);
		noteRichiesta.setWrapText(true);
		
		if(richiesta instanceof RichiestaSchedaAllenamento) {
			allergeniRichiesta.setDisable(true);
			pesoRichiesta.setDisable(true);
			altezzaRichiesta.setDisable(true);
			allenamentiRichiesta.setDisable(false);
			
			nomeRichiesta.setText(richiesta.getCliente().getNome());
			cognomeRichiesta.setText(richiesta.getCliente().getCognome());
			tipologiaRichiesta.setText("Scheda di Allenamento");
			if(richiesta.getDateInizio()!=null) dataInizioRichiesta.setText(richiesta.getDateInizio().format(Utilities.formatterData));
			if (richiesta.getDurataSettimane()!=null) durataRichiesta.setText(Integer.toString(richiesta.getDurataSettimane()));
			allenamentiRichiesta.setText(Integer.toString(((RichiestaSchedaAllenamento)richiesta).getNumeroAllenamentiSettimanali()));
			if (richiesta.getNote()!=null) noteRichiesta.setText(richiesta.getNote());
		}
		else {
			allergeniRichiesta.setDisable(false);
			pesoRichiesta.setDisable(false);
			altezzaRichiesta.setDisable(false);
			allenamentiRichiesta.setDisable(true);
			
			nomeRichiesta.setText(richiesta.getCliente().getNome());
			cognomeRichiesta.setText(richiesta.getCliente().getCognome());
			tipologiaRichiesta.setText("Piano Nutrizionale");
			if(richiesta.getDateInizio()!=null) dataInizioRichiesta.setText(richiesta.getDateInizio().format(Utilities.formatterData));
			if (richiesta.getDurataSettimane()!=null) durataRichiesta.setText(Integer.toString(richiesta.getDurataSettimane()));
			if (((RichiestaPianoNutrizionale)richiesta).getElencoAllergeni() !=null) allergeniRichiesta.setText(((RichiestaPianoNutrizionale)richiesta).getElencoAllergeni());
			altezzaRichiesta.setText(Integer.toString(((RichiestaPianoNutrizionale)richiesta).getAltezza()));
			pesoRichiesta.setText(Integer.toString(((RichiestaPianoNutrizionale)richiesta).getPeso()));
			if (richiesta.getNote()!=null) noteRichiesta.setText(richiesta.getNote());
		}
		
		
	}

	private boolean checkValuesRegistrazione() {
		if (regUsername == null || regUsername.getText().length() < 5 || !isAlphaNumeric(regUsername.getText())) {
			alert("Errore", "Errore username", "Lo username deve avere minimo 5 caratteri ed essere composto solo da lettere e numeri.");
			return false;
		}
		if (regPassword == null || regPassword.getText().length() < 8  || !containsUpperCase() || !containsLowerCase() || !containsNumber() || !isAlphaNumeric(regPassword.getText())) {
			alert("Errore", "Errore password", "La password deve avere un minimo di 8 caratteri e contenere almeno una maiuscola, una minuscola e un numero. Deve essere composto solo da lettere e numeri.");
			return false;
		}
		if ( regNome == null ||  regNome.getText().length() < 1 || !isAlphabetic(regNome.getText())) {
			alert("Errore", "Errore nome", "Inserire il proprio nome");
			return false;
		}

		if (regCognome == null || regCognome.getText().length() < 1 || !isAlphabetic(regCognome.getText())) {
			alert("Errore", "Errore cognome", "Inserire il proprio cognome");
			return false;
		}
		if ( regEmail == null || regEmail.getText().length() < 1 || !isEmail()) {
			alert("Errore", "Errore email", "Inserire un email valido");
			return false;
		}
		if (regCodFisc == null || regCodFisc.getText().length() != 16 || !isAlphaNumeric(regCodFisc.getText())) {
			alert("Errore", "Errore codice fiscale", "Inserire un codice fiscale valido");
			return false;
		}
		if (regLuogoNascita == null || regLuogoNascita.getText().length() < 1 || containsSplit(regLuogoNascita.getText())) {
			alert("Errore", "Errore luogo di nascita", "Inserire un luogo di nascita valido. Non deve contenere il carattere '|' ");
			return false;
		}
		if (regTel == null || regTel.getText().length() < 1 || containsSplit(regTel.getText())) {
			alert("Errore", "Errore numero di telefono", "Inserire un numero di telefono valido. Non deve contenere il carattere '|' ");
			return false;
		}
		if(!regConfermaPassword.getText().equals(regPassword.getText())) {
			alert("Errore", "Errore conferma password", "Inserire la password di conferma non è uguale alla password inserita");
			return false;
		}
		if (regDataNascita == null || regDataNascita.getValue() == null) {
			alert("Errore", "Errore data di nascita", "Inserire data di nascita");
			return false;
		}
		if (regCodiceID == null || regCodiceID.getText().length() < 1) {
			alert("Errore", "Errore CodiceID", "Inserire il CodiceID");
			return false;
		}
		if (regResidenza == null || regTel.getText().length() < 1 || containsSplit(regResidenza.getText())) {
			alert("Errore", "Errore indirizzo di residenza", "Inserire l'indirizzo di residenza. Non deve contenere il carattere '|' ");
			return false;
		}
		return true;
	}
	
	private boolean isAlphaNumeric(String string) {
		char[] seq = string.toCharArray();
		
		for (char c : seq)
			if (!Character.isAlphabetic(c) && !Character.isDigit(c))
				return false;
		
		return true;
	}
	
	private boolean containsSplit(String string) {
		char[] seq = string.toCharArray();
		
		for (char c : seq)
			if (c=='|')
				return true;
		
		return false;
	}
	
	private boolean isAlphabetic(String string) {
		char[] seq = string.toCharArray();
		
		for (char c : seq)
			if (!Character.isAlphabetic(c) && c!=' ')
				return false;
		
		return true;
	}
	
	private boolean isEmail() {
		char[] seq = regEmail.getText().toCharArray();
		
		for (int i = 0; i<seq.length; i++)
			if (seq[i]=='@' && i>0 && i<seq.length-2)
				return true;
		
		return false;
	}
	
	private boolean containsUpperCase() {
		char[] seq = regPassword.getText().toCharArray();
		
		for (char c : seq)
			if (Character.isUpperCase(c))
				return true;
		
		return false;
	}
	
	private boolean containsLowerCase() {
		char[] seq = regPassword.getText().toCharArray();
		
		for (char c : seq)
			if (Character.isLowerCase(c))
				return true;
		
		return false;
	}
	
	private boolean containsNumber() {
		char[] seq = regPassword.getText().toCharArray();
		
		for (char c : seq)
			if (Character.isDigit(c))
				return true;
		
		return false;
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
