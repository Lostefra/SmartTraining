package application;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.PersonalTrainer;
import richieste.RichiesteController;
import util.Utilities;

public class ControllerRichiesta {
	
	@FXML private TextArea note;
	@FXML private TextArea allergeni;
	@FXML private TableView<PersonalTrainer> tablePT;
	@FXML private TableColumn<PersonalTrainer, String> colNome;
	@FXML private TableColumn<PersonalTrainer, String> colCognome;
	@FXML private TextField nomeText;
	@FXML private TextField cognomeText;
	@FXML private ComboBox<String> tipologia;
	@FXML private DatePicker dataInizio;
	@FXML private TextField durataSettimane;
	@FXML private TextField peso;
	@FXML private TextField altezza;
	@FXML private TextField numeroAllenamenti;
	@FXML private Label labelOpzionale;
	AnchorPane root;
	
	@FXML
	public void initialize() {
		RichiesteController rc = new RichiesteController();
		colNome.setCellValueFactory(new PropertyValueFactory<PersonalTrainer, String>("nome"));
		colCognome.setCellValueFactory(new PropertyValueFactory<PersonalTrainer, String>("cognome"));
		List<PersonalTrainer> list = rc.getElencoPersonalTrainer();
		Collections.sort(list);
		tablePT.getItems().setAll(list);
		labelOpzionale.setStyle("-fx-font-style: Italic;");
		
	}
	
	
	@FXML
	public void cerca(ActionEvent event) throws IOException {
		RichiesteController rc = new RichiesteController();
		List<PersonalTrainer> list = rc.getElencoPersonalTrainerFiltro(nomeText.getText(), cognomeText.getText());
		Collections.sort(list);
		tablePT.getItems().setAll(list);
	}
	
	@FXML
	public void selezioneTipologia(ActionEvent event) throws IOException {
		//se la tipologia non cambia
		if(tipologia.getSelectionModel().getSelectedItem().equals("Allenamento") && !numeroAllenamenti.isDisabled() ||
				tipologia.getSelectionModel().getSelectedItem().equals("Nutrizionale") && numeroAllenamenti.isDisabled())
			return;
		//se invece cambia
		if(tipologia.getSelectionModel().getSelectedItem().equals("Allenamento")) {
			numeroAllenamenti.setDisable(false);
			allergeni.setDisable(true);
			peso.setDisable(true);
			altezza.setDisable(true);
			allergeni.setText("");
			peso.setText("");
			altezza.setText("");
		}
		else if(tipologia.getSelectionModel().getSelectedItem().equals("Nutrizionale")){
			numeroAllenamenti.setDisable(true);
			allergeni.setDisable(false);
			peso.setDisable(false);
			altezza.setDisable(false);
			numeroAllenamenti.setText("");
		}
		else
			System.out.println("Errore, tipologia sconosciuta");
	}
	
	@FXML
	public void inserisci(ActionEvent event) throws IOException {
		PersonalTrainer p = tablePT.getSelectionModel().getSelectedItem();
		if(p == null) {
			alert("Impossibile inoltrare la richiesta","Attenzione!", "Deve essere selezionato un Personal Trainer dall'elenco");
			return;
		}
		if(tipologia.getSelectionModel().getSelectedItem().equals("Allenamento")) {
			int numeroAllenamentiSettimanali, durata = -1;
			try {
				numeroAllenamentiSettimanali = Integer.parseInt(numeroAllenamenti.getText());
			}catch(Exception e) {
				alert("Impossibile inoltrare la richiesta","Attenzione!", "Il numero di allenamenti settimanali deve essere espresso come numero intero");
				return;
			}
			try {
				durata = Integer.parseInt(durataSettimane.getText());
			}catch(Exception e) {
				if(!durataSettimane.getText().equals("")) {
					alert("Impossibile inoltrare la richiesta","Attenzione!", "La durata in settimane deve essere espressa come numero intero");
					return;
				}
				durata = -1;
			}
			RichiesteController rc = new RichiesteController();
			if(rc.inserisciRichiestaSchedaAllenamento(Utilities.leggiCliente(Main.idCliente), p, 
					dataInizio.getValue(), durata, note.getText(), numeroAllenamentiSettimanali)) {
				inform("Richiesta Scheda Allenamento", "Operazione completata con successo", "La tua richiesta sarà presa a carico dal Personal Trainer,"
						+ " potrai trovare la scheda che ti assegnerà nella tua area personale.");
				pulisci();
			}
			else
				alert("Richiesta Scheda Allenamento", "Operazione fallita", "Attenzione, il sistema non è riuscito nell'inserimento, ti preghiamo di ripetere l'operazione."
						+ " Ci scusiamo per il disagio.");
		}
		else if(tipologia.getSelectionModel().getSelectedItem().equals("Nutrizionale")) {
			int pesoInt, altezzaInt, durata = -1;
			try {
				pesoInt = Integer.parseInt(peso.getText());
			}catch(Exception e) {
				alert("Impossibile inoltrare la richiesta","Attenzione!", "Il peso deve essere espresso come numero intero");
				return;
			}
			try {
				altezzaInt = Integer.parseInt(altezza.getText());
			}catch(Exception e) {
				alert("Impossibile inoltrare la richiesta","Attenzione!", "L'altezza deve essere espressa come numero intero");
				return;
			}
			try {
				durata = Integer.parseInt(durataSettimane.getText());
			}catch(Exception e) {
				if(!durataSettimane.getText().equals("")) {
					alert("Impossibile inoltrare la richiesta","Attenzione!", "La durata in settimane deve essere espressa come numero intero");
					return;
				}
				durata = -1;
			}
			RichiesteController rc = new RichiesteController();
			if(rc.inserisciRichiestaPianoNutrizionale(Utilities.leggiCliente(Main.idCliente), p, 
					dataInizio.getValue(), durata, note.getText(), altezzaInt, pesoInt, allergeni.getText())) {
				inform("Richiesta Piano Nutrionale", "Operazione completata con successo", "La tua richiesta sarà presa a carico dal Personal Trainer,"
						+ " potrai trovare la scheda che ti assegnerà nella tua area personale.");
				pulisci();
			}
			else
				alert("Richiesta Piano Nutrionale", "Operazione fallita", "Attenzione, il sistema non è riuscito nell'inserimento, ti preghiamo di ripetere l'operazione."
						+ " Ci scusiamo per il disagio.");
		}
		else
			System.out.println("Errore, tipologia sconosciuta");
	}
	
	private void pulisci() {
		 note.setText("");;
		 allergeni.setText("");
		 dataInizio.setValue(null);;
		 durataSettimane.setText("");
		 peso.setText("");
		 altezza.setText("");
		 numeroAllenamenti.setText("");
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
