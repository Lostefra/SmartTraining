package application;

import java.io.IOException;

import accesso.LoginController;
import accesso.RegistrazioneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.UserType;

//questo controller e' visto interamento dal fxml (deve essere interno al progetto)
public class Controller {

	AnchorPane root;
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
	@FXML private TextField regResidenza;
	
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
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomeCliente.fxml"));
			Scene scene = new Scene(root,900,600);
			Main.stage.setScene(scene);		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//inform("OK","", "benvenuto " + username.getText());
    }
	
	@FXML
	public void viewHomeCliente(ActionEvent event) throws IOException {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomeCliente.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
		
	}
	
	@FXML
	public void viewLogin (ActionEvent event) throws IOException {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
	}
	
	@FXML
    public void registrazioneCliente(ActionEvent event)
    {
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Registrazione.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		Main.stage.setScene(new Scene(root,900,600));
    }
	
	@FXML
	public void confermaRegistrazione(ActionEvent event) throws IOException {
		root = null;
		System.out.println("uno");
		if (!checkValuesRegistrazione()) {
			return;
		}
		System.out.println("due");
		RegistrazioneController controller = new RegistrazioneController();
		String result = controller.registrazione(regUsername.getText(), regPassword.getText(), regNome.getText(), regCognome.getText(),
				regEmail.getText(), regCodFisc.getText(), regDataNascita.getValue(), regLuogoNascita.getText(),
				regResidenza.getText(), regTel.getText(), "null");
		if (!result.equals("T")) {
			alert("Errore", "Errore registrazione", result);
			return;
		}
		System.out.println("tre");
		viewLogin(event);
		inform("Smart Training", "Registrazione avvenuta con successo!", "Benvenuto in Smart Training");
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
		
		for (char c : seq) {
	//		System.out.println(c);
			if (!Character.isAlphabetic(c) && c!=' ')
				return false;
		}
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
