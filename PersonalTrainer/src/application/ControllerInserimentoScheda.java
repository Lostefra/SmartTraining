package application;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.EsercizioAlimento;
import model.ObservableSchedaContenuto;
import richieste.RichiesteController;
import schede.SchedeController;
import util.Utilities;

public class ControllerInserimentoScheda {
@FXML private TabPane tabPane;
	
	@FXML private Tab lun;
	@FXML private TableView<ObservableSchedaContenuto> lunTable;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colAlun;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colBlun;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colClun;
	@FXML private Tab mar;
	@FXML private TableView<ObservableSchedaContenuto> marTable;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colAmar;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colBmar;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colCmar;
	@FXML private Tab mer;
	@FXML private TableView<ObservableSchedaContenuto> merTable;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colAmer;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colBmer;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colCmer;
	@FXML private Tab gio;
	@FXML private TableView<ObservableSchedaContenuto> gioTable;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colAgio;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colBgio;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colCgio;
	@FXML private Tab ven;
	@FXML private TableView<ObservableSchedaContenuto> venTable;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colAven;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colBven;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colCven;
	@FXML private Tab sab;
	@FXML private TableView<ObservableSchedaContenuto> sabTable;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colAsab;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colBsab;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colCsab;
	@FXML private Tab dom;
	@FXML private TableView<ObservableSchedaContenuto> domTable;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colAdom;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colBdom;
	@FXML private TableColumn<ObservableSchedaContenuto, String> colCdom;
	
	@FXML private TextField esercizio;
	@FXML private TextField ripetizioni;
	@FXML private TextField serie;
	@FXML ComboBox<String> min;
	@FXML ComboBox<String> sec;
	@FXML ComboBox<String> giornoSettimana;
	@FXML DatePicker dataInizio;
	@FXML TextField durata;
	@FXML TextArea note;

	
	private List<EsercizioAlimento> esercizi;	
	private AnchorPane root;
	
	
	@FXML 
	private void initialize()  {
		initTab();
		
		esercizi = new ArrayList<>();
		note.setWrapText(true);
		
		ObservableList<String> itemsSettimana = FXCollections.observableArrayList();
		itemsSettimana.add("Lunedì"); itemsSettimana.add("Martedì"); itemsSettimana.add("Mercoledì");
		itemsSettimana.add("Giovedì"); itemsSettimana.add("Venerdì"); itemsSettimana.add("Sabato"); 
		itemsSettimana.add("Domenica");
		giornoSettimana.setItems(itemsSettimana);
		
		ObservableList<String> itemsMin =  FXCollections.observableArrayList();
		itemsMin.addAll("00","01","02","03","04","05","06", "07","08","09","10","11","12","13","14","15","16","17","18","19","20");
		min.setItems(itemsMin);
		
		ObservableList<String> itemsSec =  FXCollections.observableArrayList();
		itemsSec.addAll("00","01","02","03","04","05","06", "07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59");
		sec.setItems(itemsSec);
		
	}
	
	private void initTab() {

		colAlun.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
		colBlun.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
		colClun.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
		
		colAmar.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
		colBmar.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
		colCmar.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
		
		colAmer.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
		colBmer.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
		colCmer.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
		
		colAgio.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
		colBgio.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
		colCgio.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
		
		colAven.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
		colBven.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
		colCven.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
		
		colAsab.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
		colBsab.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
		colCsab.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
		
		colAdom.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
		colBdom.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
		colCdom.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
	}
	
	@FXML
	public void aggiungi(ActionEvent event) {
		try {
			Integer.parseInt(serie.getText());
		} catch (Exception e) {
			alert("Errore", "Errore numero di serie", "Il valore inserito non è valido");
			return;
		}
		try {
			Integer.parseInt(ripetizioni.getText());
		} catch (Exception e) {
			alert("Errore", "Errore numero di ripetizioni", "Il valore inserito non è valido");
			return;
		}

		if (giornoSettimana.getValue() == null) {
			alert("Errore", "Errore giorno della settimana", "Selezionare un valore");
			return;
		}
		
		if (esercizio.getText() == null || esercizio.getText().equals("")) {
			alert("Errore", "Errore nome esercizio", "Scrivere il nome dell'esercizio");
			return;
		}
		
		if (min.getValue() == null) {
			alert("Errore", "Errore recupero", "Selezionare il tempo do recupero");
			return;
		}
		if (sec.getValue() == null) {
			alert("Errore", "Errore recupero", "Selezionare il tempo do recupero");
			return;
		}
		
		//int s = 0;
		if(giornoSettimana.getValue().equals("Lunedì")) {
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, Integer.parseInt(min.getValue()), Integer.parseInt(sec.getValue())),
					DayOfWeek.MONDAY));
			tabPane.getSelectionModel().select(lun);
		}
		else if(giornoSettimana.getValue().equals("Martedì")) {
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, Integer.parseInt(min.getValue()), Integer.parseInt(sec.getValue())),
					DayOfWeek.TUESDAY));
			tabPane.getSelectionModel().select(mar);
		}
		else 
		if(giornoSettimana.getValue().equals("Mercoledì")) {
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, Integer.parseInt(min.getValue()), Integer.parseInt(sec.getValue())),
					DayOfWeek.WEDNESDAY));
			tabPane.getSelectionModel().select(mer);
		}
		else 
		if(giornoSettimana.getValue().equals("Giovedì")) {
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, Integer.parseInt(min.getValue()), Integer.parseInt(sec.getValue())),
					DayOfWeek.THURSDAY));
			tabPane.getSelectionModel().select(gio);
		}
		else 
		if(giornoSettimana.getValue().equals("Venerdì")) {
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, Integer.parseInt(min.getValue()), Integer.parseInt(sec.getValue())),
					DayOfWeek.FRIDAY));
			tabPane.getSelectionModel().select(ven);
		}
		else 	
		if(giornoSettimana.getValue().equals("Sabato")) {
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, Integer.parseInt(min.getValue()), Integer.parseInt(sec.getValue())),
					DayOfWeek.SATURDAY));
			tabPane.getSelectionModel().select(sab);
		}
		else 
		if(giornoSettimana.getValue().equals("Domenica")) {
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, Integer.parseInt(min.getValue()), Integer.parseInt(sec.getValue())),
					DayOfWeek.SUNDAY));
			tabPane.getSelectionModel().select(dom);
		}
		
		fillTable();
		
	}
	
	private void fillTable() {
		List<ObservableSchedaContenuto> listEsercizi = new ArrayList<>();		
		for(EsercizioAlimento e: esercizi) {
			if(e.getGiorno().compareTo(DayOfWeek.MONDAY) == 0) {
				listEsercizi.add(new ObservableSchedaContenuto(e.getEsercizio().getNome(), e.getEsercizio().getNumeroSerie(), 
						e.getEsercizio().getNumeroRipetizioni(), e.getEsercizio().getTempoRecupero()));
			}
		}
		lunTable.getItems().setAll(listEsercizi);
		
		listEsercizi = new ArrayList<>();		
		for(EsercizioAlimento e: esercizi) {
			if(e.getGiorno().compareTo(DayOfWeek.TUESDAY) == 0) {
				listEsercizi.add(new ObservableSchedaContenuto(e.getEsercizio().getNome(), e.getEsercizio().getNumeroSerie(), 
						e.getEsercizio().getNumeroRipetizioni(), e.getEsercizio().getTempoRecupero()));
			}
		}
		marTable.getItems().setAll(listEsercizi);
		listEsercizi = new ArrayList<>();		
		for(EsercizioAlimento e: esercizi) {
			if(e.getGiorno().compareTo(DayOfWeek.WEDNESDAY) == 0) {
				listEsercizi.add(new ObservableSchedaContenuto(e.getEsercizio().getNome(), e.getEsercizio().getNumeroSerie(), 
						e.getEsercizio().getNumeroRipetizioni(), e.getEsercizio().getTempoRecupero()));
			}
		}
		merTable.getItems().setAll(listEsercizi);
		listEsercizi = new ArrayList<>();		
		for(EsercizioAlimento e: esercizi) {
			if(e.getGiorno().compareTo(DayOfWeek.THURSDAY) == 0) {
				listEsercizi.add(new ObservableSchedaContenuto(e.getEsercizio().getNome(), e.getEsercizio().getNumeroSerie(), 
						e.getEsercizio().getNumeroRipetizioni(), e.getEsercizio().getTempoRecupero()));
			}
		}
		gioTable.getItems().setAll(listEsercizi);
		listEsercizi = new ArrayList<>();		
		for(EsercizioAlimento e: esercizi) {
			if(e.getGiorno().compareTo(DayOfWeek.FRIDAY) == 0) {
				listEsercizi.add(new ObservableSchedaContenuto(e.getEsercizio().getNome(), e.getEsercizio().getNumeroSerie(), 
						e.getEsercizio().getNumeroRipetizioni(), e.getEsercizio().getTempoRecupero()));
			}
		}
		venTable.getItems().setAll(listEsercizi);
		listEsercizi = new ArrayList<>();		
		for(EsercizioAlimento e: esercizi) {
			if(e.getGiorno().compareTo(DayOfWeek.SATURDAY) == 0) {
				listEsercizi.add(new ObservableSchedaContenuto(e.getEsercizio().getNome(), e.getEsercizio().getNumeroSerie(), 
						e.getEsercizio().getNumeroRipetizioni(), e.getEsercizio().getTempoRecupero()));
			}
		}
		sabTable.getItems().setAll(listEsercizi);
		listEsercizi = new ArrayList<>();		
		for(EsercizioAlimento e: esercizi) {
			if(e.getGiorno().compareTo(DayOfWeek.SUNDAY) == 0) {
				listEsercizi.add(new ObservableSchedaContenuto(e.getEsercizio().getNome(), e.getEsercizio().getNumeroSerie(), 
						e.getEsercizio().getNumeroRipetizioni(), e.getEsercizio().getTempoRecupero()));
			}
		}
		domTable.getItems().setAll(listEsercizi);
			
	}
	
	@FXML
	public void indietro(ActionEvent event) throws IOException {
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomePersonalTrainer.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
	}
	
	public void conferma(ActionEvent event) throws NumberFormatException, IOException {
		SchedeController controller = new SchedeController();
		
		if (dataInizio.getValue() == null || dataInizio.getValue().isBefore(LocalDate.now())) {
			alert ("Errore", "Errore data inizio", "Inserire una data valida");
			return;
		}
		
		try {
			Integer.parseInt(durata.getText());
		} catch (Exception e) {
			alert ("Errore", "Errore durata", "Inserire una durata in settimane valida");
			return;
		}
		if(esercizi.isEmpty()) {
			alert ("Errore", "Errore esercizi", "Non sono presenti esercizi nella scheda");
			return;
		}
		
		if(controller.inserisciSchedaAllenamento(Utilities.leggiCliente(Main.idC), Utilities.leggiPersonalTrainer(Main.idPT),
				dataInizio.getValue(), Integer.parseInt(durata.getText()), note.getText(), esercizi)) {
			inform("Inserimento scheda", "Operazione completata con successo", "La scheda e' stata correttamente inserita nel profilo del cliente");
			RichiesteController rc = new RichiesteController();
			if(!rc.eliminaRichiesta(Main.idRichiesta))
				alert("Inserimento scheda", "Operazione fallita", "Siamo spiacenti, la scheda e' stata inserita ma si e' verificato un errore nella rimozione della richiesta");
			
		}
		else {
			alert("Inserimento scheda", "Operazione fallita", "Siamo spiacenti, l'inserimento della scheda non e' andato a buon fine");
		}
		
		root = null;
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomePersonalTrainer.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
		
	}
	
	@FXML
	public void elimina (ActionEvent event) {
		String t = tabPane.getSelectionModel().getSelectedItem().getText();
		EsercizioAlimento es = null;
		if(t.equals("Lun")){
			ObservableSchedaContenuto c = lunTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (c != null && e.getGiorno().compareTo(DayOfWeek.MONDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Mar")){
			ObservableSchedaContenuto c = marTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (c != null && e.getGiorno().compareTo(DayOfWeek.TUESDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Mer")){
			ObservableSchedaContenuto c = merTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (c != null && e.getGiorno().compareTo(DayOfWeek.WEDNESDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Gio")){
			ObservableSchedaContenuto c = gioTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (c != null && e.getGiorno().compareTo(DayOfWeek.THURSDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Ven")){
			ObservableSchedaContenuto c = venTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (c != null && e.getGiorno().compareTo(DayOfWeek.FRIDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Sab")){
			ObservableSchedaContenuto c = sabTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (c != null && e.getGiorno().compareTo(DayOfWeek.SATURDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Dom")){
			ObservableSchedaContenuto c = domTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (c != null && e.getGiorno().compareTo(DayOfWeek.SUNDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(es != null)
			esercizi.remove(es);
		
		fillTable();
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
