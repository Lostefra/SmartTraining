package application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import log.LogController;
import model.Alimento;
import model.Esercizio;
import model.ObservableScheda;
import model.ObservableSchedaContenuto;
import model.Pasto;
import model.PianoNutrizionale;
import model.Scheda;
import model.SchedaAllenamento;
import model.Sessione;
import schede.SchedeController;

public class ControllerStorico {

	AnchorPane root;
	@FXML private TableView<ObservableScheda> listaSchede;
	@FXML private TableColumn<ObservableScheda, String> cliente;
	@FXML private TableColumn<ObservableScheda, String> pt;
	@FXML private TableColumn<ObservableScheda, String> dataInizio;
	@FXML private TableColumn<ObservableScheda, String> dataFine;
	@FXML private TableColumn<ObservableScheda, String> tipologia;
	
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
	
	@FXML private TextArea note1;
	@FXML private TextArea note2;
	@FXML private TextArea note3;
	@FXML private TextArea note4;
	@FXML private TextArea note5;
	@FXML private TextArea note6;
	@FXML private TextArea note7;
	
	@FXML private DatePicker dataInizioFiltro;
	@FXML private DatePicker dataFineFiltro;
	@FXML private TextField nomePTFiltro;
	@FXML private TextField cognomePTFiltro;
	@FXML private TextField nomeClienteFiltro;
	@FXML private TextField cognomeClienteFiltro;
	@FXML private ComboBox<String> tipologiaFiltro;
	
	
	@FXML private void initialize()  {
		ObservableList<String> items = FXCollections.observableArrayList();
		items.add("Piano Nutrizionale");
		items.add("Scheda di Allenamento");
		items.add("Tutte");
		tipologiaFiltro.setItems(items);
		SchedeController sc = new SchedeController();
		List<Scheda> schede = sc.visualizzaStoricoPT();
		List<ObservableScheda> obsList = new ArrayList<>();
		String tipologiaString;
		for(Scheda s: schede) {
			if(s instanceof SchedaAllenamento)
				tipologiaString = "Scheda di Allenamento";
			else
				tipologiaString = "Piano Nutrizionale";
			obsList.add(new ObservableScheda(s.getId(), s.getCliente().getNome(), s.getCliente().getCognome(),
					s.getPersonalTrainer().getNome(), s.getPersonalTrainer().getCognome(), s.getDateInizio(),
					s.getDateInizio().plusWeeks(s.getDurataSettimane()), tipologiaString));
		}
		cliente.setCellValueFactory(new PropertyValueFactory<ObservableScheda, String>("cliente"));
        pt.setCellValueFactory(new PropertyValueFactory<ObservableScheda, String>("pt"));
        dataInizio.setCellValueFactory(new PropertyValueFactory<ObservableScheda, String>("dataInizio"));
        dataFine.setCellValueFactory(new PropertyValueFactory<ObservableScheda, String>("dataFine"));
        tipologia.setCellValueFactory(new PropertyValueFactory<ObservableScheda, String>("tipologia"));  

        listaSchede.getItems().setAll(obsList);      

        listaSchede.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) { 
            	if(listaSchede.getSelectionModel().getSelectedItem() != null) {
	            	String id = listaSchede.getSelectionModel().getSelectedItem().getId();
	            	Scheda toInit = sc.applicaFiltro(schede, null, null, null, null, null, null, null, id).get(0);
	            	initTab(toInit);  
            	}
            }

        });    
        note1.setWrapText(true);
        note2.setWrapText(true);
        note3.setWrapText(true);
        note4.setWrapText(true);
        note5.setWrapText(true);
        note6.setWrapText(true);
        note7.setWrapText(true);
	}
	
	private void initTab(Scheda scheda) {
		if(scheda instanceof SchedaAllenamento) {
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
			
			List<ObservableSchedaContenuto> listEsercizi = new ArrayList<>();		
			for(Sessione s: ((SchedaAllenamento) scheda).getSessioni()) {
				if(s.getGiorno().getValue() == 1) {
					for(Esercizio e : s.getEsercizi()) {
						listEsercizi.add(new ObservableSchedaContenuto(e.getNome(), e.getNumeroSerie(), e.getNumeroRipetizioni(), e.getTempoRecupero()));
					}
				}
			}
			colAlun.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
			colBlun.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
			colClun.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
	        lunTable.getItems().setAll(listEsercizi);
	        
	        listEsercizi = new ArrayList<>();		
			for(Sessione s: ((SchedaAllenamento) scheda).getSessioni()) {
				if(s.getGiorno().getValue() == 2) {
					for(Esercizio e : s.getEsercizi()) {
						listEsercizi.add(new ObservableSchedaContenuto(e.getNome(), e.getNumeroSerie(), e.getNumeroRipetizioni(), e.getTempoRecupero()));
					}
				}
			}
			colAmar.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
			colBmar.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
			colCmar.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
	        marTable.getItems().setAll(listEsercizi);
	        
	        listEsercizi = new ArrayList<>();		
			for(Sessione s: ((SchedaAllenamento) scheda).getSessioni()) {
				if(s.getGiorno().getValue() == 3) {
					for(Esercizio e : s.getEsercizi()) {
						listEsercizi.add(new ObservableSchedaContenuto(e.getNome(), e.getNumeroSerie(), e.getNumeroRipetizioni(), e.getTempoRecupero()));
					}
				}
			}
			colAmer.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
			colBmer.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
			colCmer.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
	        merTable.getItems().setAll(listEsercizi);
	        
	        listEsercizi = new ArrayList<>();		
			for(Sessione s: ((SchedaAllenamento) scheda).getSessioni()) {
				if(s.getGiorno().getValue() == 4) {
					for(Esercizio e : s.getEsercizi()) {
						listEsercizi.add(new ObservableSchedaContenuto(e.getNome(), e.getNumeroSerie(), e.getNumeroRipetizioni(), e.getTempoRecupero()));
					}
				}
			}
			colAgio.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
			colBgio.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
			colCgio.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
	        gioTable.getItems().setAll(listEsercizi);
	        
	        listEsercizi = new ArrayList<>();		
			for(Sessione s: ((SchedaAllenamento) scheda).getSessioni()) {
				if(s.getGiorno().getValue() == 5) {
					for(Esercizio e : s.getEsercizi()) {
						listEsercizi.add(new ObservableSchedaContenuto(e.getNome(), e.getNumeroSerie(), e.getNumeroRipetizioni(), e.getTempoRecupero()));
					}
				}
			}
			colAven.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
			colBven.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
			colCven.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
	        venTable.getItems().setAll(listEsercizi);
	        
	        listEsercizi = new ArrayList<>();		
			for(Sessione s: ((SchedaAllenamento) scheda).getSessioni()) {
				if(s.getGiorno().getValue() == 6) {
					for(Esercizio e : s.getEsercizi()) {
						listEsercizi.add(new ObservableSchedaContenuto(e.getNome(), e.getNumeroSerie(), e.getNumeroRipetizioni(), e.getTempoRecupero()));
					}
				}
			}
			colAsab.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
			colBsab.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
			colCsab.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
	        sabTable.getItems().setAll(listEsercizi);
	        
	        listEsercizi = new ArrayList<>();		
			for(Sessione s: ((SchedaAllenamento) scheda).getSessioni()) {
				if(s.getGiorno().getValue() == 7) {
					for(Esercizio e : s.getEsercizi()) {
						listEsercizi.add(new ObservableSchedaContenuto(e.getNome(), e.getNumeroSerie(), e.getNumeroRipetizioni(), e.getTempoRecupero()));
					}
				}
			}
			colAdom.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("esercizio"));
			colBdom.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("ripetizioni"));
			colCdom.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("recupero"));
	        domTable.getItems().setAll(listEsercizi);
			
	        if(scheda.getNote() != null && !scheda.getNote().equals("null")) {
	        	note1.setText(scheda.getNote());
	        	note2.setText(scheda.getNote());
	        	note3.setText(scheda.getNote());
	        	note4.setText(scheda.getNote());
	        	note5.setText(scheda.getNote());
	        	note6.setText(scheda.getNote());
	        	note7.setText(scheda.getNote());
	        }
	        else {
	        	note1.setText("");
	        	note2.setText("");
	        	note3.setText("");
	        	note4.setText("");
	        	note5.setText("");
	        	note6.setText("");
	        	note7.setText("");
	        }
		}
		else if(scheda instanceof PianoNutrizionale) {
			colAlun.setText("Orario");
			colBlun.setText("Alimento");
			colClun.setText("Peso (gr.)");
			colAmar.setText("Orario");
			colBmar.setText("Alimento");
			colCmar.setText("Peso (gr.)");
			colAmer.setText("Orario");
			colBmer.setText("Alimento");
			colCmer.setText("Peso (gr.)");
			colAgio.setText("Orario");
			colBgio.setText("Alimento");
			colCgio.setText("Peso (gr.)");
			colAven.setText("Orario");
			colBven.setText("Alimento");
			colCven.setText("Peso (gr.)");
			colAsab.setText("Orario");
			colBsab.setText("Alimento");
			colCsab.setText("Peso (gr.)");
			colAdom.setText("Orario");
			colBdom.setText("Alimento");
			colCdom.setText("Peso (gr.)");
			
			colAlun.setPrefWidth(58.0);
			colBlun.setPrefWidth(186.0);
			colClun.setPrefWidth(83.0);
			colAmar.setPrefWidth(58.0);
			colBmar.setPrefWidth(186.0);
			colCmar.setPrefWidth(83.0);
			colAmer.setPrefWidth(58.0);
			colBmer.setPrefWidth(186.0);
			colCmer.setPrefWidth(83.0);
			colAgio.setPrefWidth(58.0);
			colBgio.setPrefWidth(186.0);
			colCgio.setPrefWidth(83.0);
			colAven.setPrefWidth(58.0);
			colBven.setPrefWidth(186.0);
			colCven.setPrefWidth(83.0);
			colAsab.setPrefWidth(58.0);
			colBsab.setPrefWidth(186.0);
			colCsab.setPrefWidth(83.0);
			colAdom.setPrefWidth(58.0);
			colBdom.setPrefWidth(186.0);
			colCdom.setPrefWidth(83.0);
			
			List<ObservableSchedaContenuto> listaAlimenti = new ArrayList<>();		
			for(Pasto p: ((PianoNutrizionale) scheda).getPasti()) {
				if(p.getGiorno().getValue() == 1) {
					for(Alimento a : p.getAlimenti()) {
						listaAlimenti.add(new ObservableSchedaContenuto(p.getOra(), a.getNome(), a.getPeso()));
					}
				}
			}
			colAlun.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("orario"));
			colBlun.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("alimento"));
			colClun.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("peso"));
	        lunTable.getItems().setAll(listaAlimenti);
	        
	        listaAlimenti = new ArrayList<>();			
	    			for(Pasto p: ((PianoNutrizionale) scheda).getPasti()) {
	    				if(p.getGiorno().getValue() == 2) {
	    					for(Alimento a : p.getAlimenti()) {
	    						listaAlimenti.add(new ObservableSchedaContenuto(p.getOra(), a.getNome(), a.getPeso()));
	    					}
	    				}
	    			}
			colAmar.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("orario"));
			colBmar.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("alimento"));
			colCmar.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("peso"));
	        marTable.getItems().setAll(listaAlimenti);
	        
	        listaAlimenti = new ArrayList<>();			
			for(Pasto p: ((PianoNutrizionale) scheda).getPasti()) {
				if(p.getGiorno().getValue() == 3) {
					for(Alimento a : p.getAlimenti()) {
						listaAlimenti.add(new ObservableSchedaContenuto(p.getOra(), a.getNome(), a.getPeso()));
					}
				}
			}
			colAmer.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("orario"));
			colBmer.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("alimento"));
			colCmer.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("peso"));
		    merTable.getItems().setAll(listaAlimenti);
	        
	        listaAlimenti = new ArrayList<>();			
			for(Pasto p: ((PianoNutrizionale) scheda).getPasti()) {
				if(p.getGiorno().getValue() == 4) {
					for(Alimento a : p.getAlimenti()) {
						listaAlimenti.add(new ObservableSchedaContenuto(p.getOra(), a.getNome(), a.getPeso()));
					}
				}
			}
			colAgio.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("orario"));
			colBgio.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("alimento"));
			colCgio.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("peso"));
	        gioTable.getItems().setAll(listaAlimenti);
	        
	        listaAlimenti = new ArrayList<>();			
			for(Pasto p: ((PianoNutrizionale) scheda).getPasti()) {
				if(p.getGiorno().getValue() == 5) {
					for(Alimento a : p.getAlimenti()) {
						listaAlimenti.add(new ObservableSchedaContenuto(p.getOra(), a.getNome(), a.getPeso()));
					}
				}
			}
			colAven.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("orario"));
			colBven.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("alimento"));
			colCven.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("peso"));
	        venTable.getItems().setAll(listaAlimenti);
	        
	        listaAlimenti = new ArrayList<>();			
			for(Pasto p: ((PianoNutrizionale) scheda).getPasti()) {
				if(p.getGiorno().getValue() == 6) {
					for(Alimento a : p.getAlimenti()) {
						listaAlimenti.add(new ObservableSchedaContenuto(p.getOra(), a.getNome(), a.getPeso()));
					}
				}
			}
			colAsab.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("orario"));
			colBsab.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("alimento"));
			colCsab.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("peso"));
	        sabTable.getItems().setAll(listaAlimenti);
			
	        listaAlimenti = new ArrayList<>();			
			for(Pasto p: ((PianoNutrizionale) scheda).getPasti()) {
				if(p.getGiorno().getValue() == 7) {
					for(Alimento a : p.getAlimenti()) {
						listaAlimenti.add(new ObservableSchedaContenuto(p.getOra(), a.getNome(), a.getPeso()));
					}
				}
			}
			colAdom.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("orario"));
			colBdom.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("alimento"));
			colCdom.setCellValueFactory(new PropertyValueFactory<ObservableSchedaContenuto, String>("peso"));
	        domTable.getItems().setAll(listaAlimenti);
	        
	        if(scheda.getNote() != null && !scheda.getNote().equals("null")) {
	        	note1.setText(scheda.getNote());
	        	note2.setText(scheda.getNote());
	        	note3.setText(scheda.getNote());
	        	note4.setText(scheda.getNote());
	        	note5.setText(scheda.getNote());
	        	note6.setText(scheda.getNote());
	        	note7.setText(scheda.getNote());
	        }
	        else {
	        	note1.setText("");
	        	note2.setText("");
	        	note3.setText("");
	        	note4.setText("");
	        	note5.setText("");
	        	note6.setText("");
	        	note7.setText("");
	        }
		}
		else {
			
			System.out.println("tipologia scheda non trovata");
		}
	}
	
	@FXML
	public void indietro(ActionEvent event) throws IOException {
		root = null;	
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomePersonalTrainer.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
	}
	
	@FXML
	public void applica(ActionEvent event) throws IOException {
		if(dataInizioFiltro.getValue() != null && dataFineFiltro.getValue() != null && 
				dataFineFiltro.getValue().compareTo(dataInizioFiltro.getValue()) < 0 ) {
			alert("Errore ricerca","Filtri per data errati", "La data di inizio deve anticipare la data di fine validita");
			return;
		}
		SchedeController sc = new SchedeController();
		List<Scheda> schede = sc.visualizzaStoricoPT();
		String tip = "tutte";
		if (tipologiaFiltro.getValue() != null) {
			if (tipologiaFiltro.getValue().toString().equals("Piano Nutrizionale"))
				tip = "Nutrizionale";
			else
				if (tipologiaFiltro.getValue().toString().equals("Scheda di Allenamento"))
					tip = "Allenamento";
		}
		schede = sc.applicaFiltro(schede, nomeClienteFiltro.getText(), cognomeClienteFiltro.getText(), 
				nomePTFiltro.getText(), cognomePTFiltro.getText(), 
				dataInizioFiltro.getValue(), dataFineFiltro.getValue(), tip, null);
		
		List<ObservableScheda> obsList = new ArrayList<>();
		String tipologiaString;
		for(Scheda s: schede) {
			if(s instanceof SchedaAllenamento)
				tipologiaString = "Scheda di Allenamento";
			else
				tipologiaString = "Piano Nutrizionale";
			obsList.add(new ObservableScheda(s.getId(), s.getCliente().getNome(), s.getCliente().getCognome(),
					s.getPersonalTrainer().getNome(), s.getPersonalTrainer().getCognome(), s.getDateInizio(),
					s.getDateInizio().plusWeeks(s.getDurataSettimane()), tipologiaString));
		}

        listaSchede.getItems().setAll(obsList);      

    	LogController log = new LogController();
        log.scriviOperazione(LocalDateTime.now(), "Richiesta applicazione di filtro di ricerca nello storico delle schede", Main.idPT);

	}
	
	private static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	
}
