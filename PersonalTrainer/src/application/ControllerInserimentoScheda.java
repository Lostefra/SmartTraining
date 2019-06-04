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
import model.SchedaAllenamento;
import model.Sessione;
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
	@FXML ComboBox<Integer> min;
	@FXML ComboBox<Integer> sec;
	@FXML ComboBox<String> giornoSettimana;
	@FXML DatePicker dataInizio;
	@FXML TextField durata;
	@FXML TextArea note;
	
	private Sessione mon;
	private Sessione tue;
	private Sessione wed;
	private Sessione thu;
	private Sessione fri;
	private Sessione sat;
	private Sessione sun;
	
	private List<EsercizioAlimento> esercizi;
	private List<ObservableSchedaContenuto> observableSchedaContenuto;
	private SchedaAllenamento scheda;
	
	private AnchorPane root;
	
	
	@FXML 
	private void initialize()  {
		initTab();
		observableSchedaContenuto = new ArrayList<>();
		esercizi = new ArrayList<>();
		note.setWrapText(true);
		
		ObservableList<String> itemsSettimana = FXCollections.observableArrayList();
		itemsSettimana.add("Lunedì"); itemsSettimana.add("Martedì"); itemsSettimana.add("Mercoledì");
		itemsSettimana.add("Giovedì"); itemsSettimana.add("Venerdì"); itemsSettimana.add("Sabato"); 
		itemsSettimana.add("Domenica");
		giornoSettimana.setItems(itemsSettimana);
		
		ObservableList<Integer> itemsMin =  FXCollections.observableArrayList();
		itemsMin.addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
		min.setItems(itemsMin);
		
		ObservableList<Integer> itemsSec =  FXCollections.observableArrayList();
		itemsSec.addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
		sec.setItems(itemsSec);
		
		mon = new Sessione(DayOfWeek.MONDAY);
		tue = new Sessione(DayOfWeek.TUESDAY);
		wed = new Sessione(DayOfWeek.WEDNESDAY);
		thu = new Sessione(DayOfWeek.THURSDAY);
		fri = new Sessione(DayOfWeek.FRIDAY);
		sat = new Sessione(DayOfWeek.SATURDAY);
		sun = new Sessione(DayOfWeek.SUNDAY);

	}
	
	private void initTab() {
		colAlun.setText("Esercizio");
		colBlun.setText("Ripetizioni");
		colClun.setText("Recupero");
		colAmar.setText("Esercizio");
		colBmar.setText("Ripetizioni");
		colCmar.setText("Recupero");
		colAmer.setText("Esercizio");
		colBmer.setText("Ripetizioni");
		colCmer.setText("Recupero");
		colAgio.setText("Esercizio");
		colBgio.setText("Ripetizioni");
		colCgio.setText("Recupero");
		colAven.setText("Esercizio");
		colBven.setText("Ripetizioni");
		colCven.setText("Recupero");
		colAsab.setText("Esercizio");
		colBsab.setText("Ripetizioni");
		colCsab.setText("Recupero");
		colAdom.setText("Esercizio");
		colBdom.setText("Ripetizioni");
		colCdom.setText("Recupero");
		
		colAlun.setPrefWidth(178.0);
		colBlun.setPrefWidth(79.0);
		colClun.setPrefWidth(70.0);
		colAmar.setPrefWidth(178.0);
		colBmar.setPrefWidth(79.0);
		colCmar.setPrefWidth(70.0);
		colAmer.setPrefWidth(178.0);
		colBmer.setPrefWidth(79.0);
		colCmer.setPrefWidth(70.0);
		colAgio.setPrefWidth(178.0);
		colBgio.setPrefWidth(79.0);
		colCgio.setPrefWidth(70.0);
		colAven.setPrefWidth(178.0);
		colBven.setPrefWidth(79.0);
		colCven.setPrefWidth(70.0);
		colAsab.setPrefWidth(178.0);
		colBsab.setPrefWidth(79.0);
		colCsab.setPrefWidth(70.0);
		colAdom.setPrefWidth(178.0);
		colBdom.setPrefWidth(79.0);
		colCdom.setPrefWidth(70.0);
		
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
			int t = Integer.parseInt(serie.getText());
		} catch (Exception e) {
			alert("Errore", "Errore numero di serie", "Il valore inserito non è valido");
			return;
		}
		try {
			int t = Integer.parseInt(ripetizioni.getText());
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
		if(giornoSettimana.getValue().equals("Lunedì"))
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, min.getValue(), sec.getValue()),
					DayOfWeek.MONDAY));
		else if(giornoSettimana.getValue().equals("Martedì"))
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, min.getValue(), sec.getValue()),
					DayOfWeek.TUESDAY));
		else 
		if(giornoSettimana.getValue().equals("Mercoledì"))
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, min.getValue(), sec.getValue()),
					DayOfWeek.WEDNESDAY));
		else 
		if(giornoSettimana.getValue().equals("Giovedì"))
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, min.getValue(), sec.getValue()),
					DayOfWeek.THURSDAY));
		else 
		if(giornoSettimana.getValue().equals("Venerdì"))
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, min.getValue(), sec.getValue()),
					DayOfWeek.FRIDAY));
		else 	
		if(giornoSettimana.getValue().equals("Sabato"))
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, min.getValue(), sec.getValue()),
					DayOfWeek.SATURDAY));
		else 
		if(giornoSettimana.getValue().equals("Domenica"))
			esercizi.add(new EsercizioAlimento(esercizio.getText(), Integer.parseInt(serie.getText()), 
					Integer.parseInt(ripetizioni.getText()), LocalTime.of(0, min.getValue(), sec.getValue()),
					DayOfWeek.SUNDAY));
		
		
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
		
		if (dataInizio.getValue().isBefore(LocalDate.now()) || dataInizio.getValue() == null) {
			alert ("Errore", "Errore data inizio", "Inserire una data valida");
			return;
		}
		
		try {
			int t = Integer.parseInt(durata.getText());
		} catch (Exception e) {
			alert ("Errore", "Errore durata", "Inserire una durata in settimane valida");
		}
		
		controller.inserisciSchedaAllenamento(Utilities.getCliente(Main.usernameC), Utilities.getPersonalTrainer(Main.usernamePT),
				dataInizio.getValue(), Integer.parseInt(durata.getText()), note.getText(), esercizi);
		
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
				if (e.getGiorno().compareTo(DayOfWeek.MONDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Mar")){
			ObservableSchedaContenuto c = marTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (e.getGiorno().compareTo(DayOfWeek.TUESDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Mer")){
			ObservableSchedaContenuto c = merTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (e.getGiorno().compareTo(DayOfWeek.WEDNESDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Gio")){
			ObservableSchedaContenuto c = gioTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (e.getGiorno().compareTo(DayOfWeek.THURSDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Ven")){
			ObservableSchedaContenuto c = venTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (e.getGiorno().compareTo(DayOfWeek.FRIDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Sab")){
			ObservableSchedaContenuto c = sabTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (e.getGiorno().compareTo(DayOfWeek.SATURDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
		if(t.equals("Dom")){
			ObservableSchedaContenuto c = domTable.getSelectionModel().getSelectedItem();
			for (EsercizioAlimento e : esercizi) {
				if (e.getGiorno().compareTo(DayOfWeek.SUNDAY)==0 && e.getEsercizio().getNome().equals(c.getEsercizio())) {
					es = e;
				}
			}
		}
		
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
