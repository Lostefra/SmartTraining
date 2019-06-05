package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Cliente;
import model.Prodotto;
import model.ProdottoSelezionato;
import model.Sconto;
import util.Utilities;

//questo controller e' visto interamento dal fxml (deve essere interno al progetto)
public class ControllerAcquisto {

	@FXML private TextField codiceProdotto;
	@FXML private TextField nomeProdotto;
	@FXML private TextField prezzoMin;
	@FXML private TextField prezzoMax;	
	
	@FXML private TableView<Prodotto> tabellaProdotti;
    @FXML private TableColumn<Prodotto, Integer> codiceCol;
    @FXML private TableColumn<Prodotto, String> prodottoCol;
    @FXML private TableColumn<Prodotto, String> descCol;
    @FXML private TableColumn<Prodotto, Float> prezzoCol;
    @FXML private TableColumn<Prodotto, Integer> disponibilitaCol;
    List<Prodotto> prodotti = new ArrayList<Prodotto>();
    
    @FXML private TableView<Sconto> tabellaSconti;
    @FXML private TableColumn<Sconto, Integer> valoreScontiCol;
    @FXML private TableColumn<Sconto, String> puntiRichiestiCol;
    @FXML private TableColumn<Sconto, String> spesaMinimaCol;
    @FXML private TableColumn<Sconto, Integer> disponibilitaScontoCol;
    List<Sconto> sconti = new ArrayList<Sconto>();
    
    @FXML private ComboBox<Integer> quantita;
    @FXML private Button aggiungi;
    @FXML private Button elimina;
    @FXML private Button conferma;
    @FXML private Button confermaCarrello;
    
    @FXML private TableView<ProdottoSelezionato> tabellaSelezionati;
    @FXML private TableColumn<ProdottoSelezionato, Integer> codiceSelezionatiCol;
    @FXML private TableColumn<ProdottoSelezionato, String> prodottoSelezionatiCol;
    @FXML private TableColumn<ProdottoSelezionato, Integer> quantitaSelezionatiCol;
    @FXML private TableColumn<ProdottoSelezionato, Float> prezzoSelezionatiCol;
    List<ProdottoSelezionato> prodottiSelezionati = new ArrayList<ProdottoSelezionato>();
    
    @FXML private TextField punti;
    @FXML private TextField totale;
    @FXML private Label toChange;
    
    AnchorPane root;
    Cliente c;
    acquisto.AcquistoController ac;
    
    //questa funzione e' invocata automaticamente dal controller ad ogni caricamento di file fxml
  	@FXML private void initialize() {
  		c = Utilities.leggiCliente(Main.idCliente);
  		ac = new acquisto.AcquistoController(c);
  		getProdotti();
  		getSconti();
  		initSelezionati();
  		punti.setText("" + ac.getSaldoPunti());
  		punti.setEditable(false);
  		totale.setEditable(false);
  		conferma.setDisable(true);
  	}
  	
  	/**
	 * Legge tutti i prodotti dal database e popola la tabella
	 */
	public void getProdotti() {
		prodotti = ac.getProdotti();

		//la tabella sara' non nulla quando sara' caricato il file VisualizzaLog.fxml
		if(tabellaProdotti != null) {
			codiceCol.setCellValueFactory(new PropertyValueFactory<Prodotto, Integer>("codiceInteger"));
	        prodottoCol.setCellValueFactory(new PropertyValueFactory<Prodotto, String>("nome"));
	        descCol.setCellValueFactory(new PropertyValueFactory<Prodotto, String>("descrizione"));
	        prezzoCol.setCellValueFactory(new PropertyValueFactory<Prodotto, Float>("prezzoFloat"));
	        disponibilitaCol.setCellValueFactory(new PropertyValueFactory<Prodotto, Integer>("disponibilitaInteger"));
	        
	        Collections.sort(prodotti);
	        tabellaProdotti.getItems().setAll(prodotti);	        
	        tabellaProdotti.setOnMouseClicked(e -> {
	        	if(tabellaProdotti.getSelectionModel().getSelectedItem() != null)
	        		aggiornaQuantita(tabellaProdotti.getSelectionModel().getSelectedItem().getDisponibilita());
	        });
		}
	}
	
	/**
	 * Legge gli sconti dal file e popola la tabella
	 */
	public void getSconti() {
		sconti = ac.getScontiDisponibili();
		//la tabella sara' non nulla quando sara' caricato il file VisualizzaLog.fxml
		if(tabellaSconti!= null) {
			valoreScontiCol.setCellValueFactory(new PropertyValueFactory<Sconto, Integer>("valore"));
	        puntiRichiestiCol.setCellValueFactory(new PropertyValueFactory<Sconto, String>("puntiRichiesti"));
	        spesaMinimaCol.setCellValueFactory(new PropertyValueFactory<Sconto, String>("spesaMinima"));
	        
	        Collections.sort(sconti);
	        tabellaSconti.getItems().setAll(sconti);
	        
	        tabellaSconti.setOnMouseClicked(e -> {
	        	if(aggiungi.isDisabled() && elimina.isDisabled()) 
	        		totale.setText("" + ac.applicaSconto(tabellaSconti.getSelectionModel().getSelectedItem()));
	        });
	    }
	}
	
	
	/**
	 * Inizializza la tabella dei prodotti selezionati
	 */
	@FXML
	public void initSelezionati() {
		totale.setText("0.0");
		prodottiSelezionati = ac.getSelezionati(); //Dovrei prendere la lista dei selezionati 
										//ma se non ho aggiunto nulla è vuota
		if(tabellaSelezionati!= null) {
			codiceSelezionatiCol.setCellValueFactory(new PropertyValueFactory<ProdottoSelezionato, Integer>("codice"));
	        prodottoSelezionatiCol.setCellValueFactory(new PropertyValueFactory<ProdottoSelezionato, String>("nome"));
	        quantitaSelezionatiCol.setCellValueFactory(new PropertyValueFactory<ProdottoSelezionato, Integer>("quantita"));
	        prezzoSelezionatiCol.setCellValueFactory(new PropertyValueFactory<ProdottoSelezionato, Float>("prezzo"));
		}
	}
  	
	/**
	 * Applica il filtro specificato dall'utente tramite i textField della view
	 * @param evento dato dal click del mouse sul bottone
	 */
	@FXML
	public void applicaFiltro(ActionEvent event) {
		prodotti = ac.getProdotti();
		
		int codice = -1; 
		float pMin = Float.MIN_VALUE;
		float pMax = Float.MAX_VALUE;
		try{
			codice = Integer.parseInt(codiceProdotto.getText());
		}
		catch(Exception e) {
			if(!codiceProdotto.getText().equals(""))
				alert("Errore di conversione","Attenzione!","Il codice prodotto inserito non risulta essere un valore numerico valido");
			codiceProdotto.setText("");
		}
		try{
			pMin = Float.parseFloat(prezzoMin.getText());
		}
		catch(Exception e) {
			if(!prezzoMin.getText().equals(""))
				alert("Errore di conversione","Attenzione!","Il prezzo minimo inserito non risulta essere un valore numerico valido");
			prezzoMin.setText("");
		}
		try{
			 pMax = Float.parseFloat(prezzoMax.getText());
		}
		catch(Exception e) {
			if(!prezzoMax.getText().equals(""))
				alert("Errore di conversione","Attenzione!","Il prezzo massimo inserito non risulta essere un valore numerico valido");
			prezzoMax.setText("");
		}
		String prod = nomeProdotto.getText();	
		
		prodotti = ac.applicaFiltro(prodotti, OptionalInt.of(codice), prod,
									Optional.of(pMin), Optional.of(pMax));
		
		codiceCol.setCellValueFactory(new PropertyValueFactory<Prodotto, Integer>("codice"));
        prodottoCol.setCellValueFactory(new PropertyValueFactory<Prodotto, String>("nome"));
        descCol.setCellValueFactory(new PropertyValueFactory<Prodotto, String>("descrizione"));
        prezzoCol.setCellValueFactory(new PropertyValueFactory<Prodotto, Float>("prezzo"));
        disponibilitaCol.setCellValueFactory(new PropertyValueFactory<Prodotto, Integer>("disponibilita"));
        
        Collections.sort(prodotti);
        tabellaProdotti.getItems().setAll(prodotti);
}
  	
  	
	/**
	 * Funzione usata per aggiornare la comboBox con la disponibilità di ogni prodotto
	 * 
	 * @param massima disponibilità del singolo prodotto (se > 10, allora è 10)
	 */
	private void aggiornaQuantita(int max) {
		ObservableList<Integer> integers = FXCollections.observableArrayList();
		quantita.setItems(integers);
		if(max > 10) max = 10;
		
		for (int i = 0; i < max; i++)
			quantita.getItems().add(1+i);
	}
	
	@FXML
	public void aggiungi(ActionEvent event) {
		Prodotto prodotto = tabellaProdotti.getSelectionModel().getSelectedItem();
		
		if(quantita.getValue() != null && prodotto != null) {
			ProdottoSelezionato pSelected = new ProdottoSelezionato();
			pSelected.setCodice(prodotto.getCodice());
			pSelected.setNome(prodotto.getNome());
			pSelected.setQuantita(quantita.getValue());		
			pSelected.setPrezzo(quantita.getValue()*prodotto.getPrezzo());
			
			if(tabellaSelezionati.getItems().contains(pSelected)) {
				System.out.print(pSelected.getNome()+" sostitutito\n");
				int index = tabellaSelezionati.getItems().lastIndexOf(pSelected);
				ProdottoSelezionato old = tabellaSelezionati.getItems().get(index);
				tabellaSelezionati.getItems().remove(index);
				index = ac.getSelezionati().lastIndexOf(pSelected);
				ac.getSelezionati().remove(index);
				pSelected.setQuantita(quantita.getValue()+ old.getQuantita());
				pSelected.setPrezzo(quantita.getValue()*prodotto.getPrezzo() + old.getPrezzo());
				tabellaSelezionati.getItems().add(pSelected);
				ac.aggiungiProdotto(pSelected, quantita.getValue());
			}
			else {
				tabellaSelezionati.getItems().add(pSelected);
				ac.aggiungiProdotto(pSelected, quantita.getValue());
			}
	        totale.setText("" + ac.calcolaSommaSpesa());
	        prodotti = ac.getProdotti();
	        Collections.sort(prodotti);
	        tabellaProdotti.getItems().setAll(prodotti);	
		}
	}

	@FXML
	public void confermaCarrello(ActionEvent event) {
		if(!tabellaSelezionati.getItems().isEmpty()) {
			aggiungi.setDisable(true);
			elimina.setDisable(true);
			tabellaSconti.getItems().setAll(ac.confermaCarrello());
			toChange.setText("Questi sono gli sconti disponibili per te:");
			conferma.setDisable(false);	  	
			
		}
	}
	
	
	
	@FXML
	public void elimina(ActionEvent event) {
		 ProdottoSelezionato prodottoSel = tabellaSelezionati.getSelectionModel().getSelectedItem();
		 if(prodottoSel != null) {
			 ac.eliminaProdotto(prodottoSel, prodottoSel.getQuantita());		 
			 tabellaSelezionati.getItems().remove(prodottoSel);
			 totale.setText("" + ac.calcolaSommaSpesa());
			 prodotti = ac.getProdotti();
		     Collections.sort(prodotti);
		     tabellaProdotti.getItems().setAll(prodotti);
		 }
	}
	
	@FXML
	public void conferma(ActionEvent event) {
		Sconto s = tabellaSconti.getSelectionModel().getSelectedItem(); 
		if(ac.conferma(s, Main.idCliente)) {
			inform("Esito acquisto", "Complimenti, il tuo acquisto è avvenuto con sucesso", "Riceverai una mail di conferma");
			aggiungi.setDisable(false);
			elimina.setDisable(false);
			tabellaSelezionati.getItems().setAll();
			ac.getSelezionati().clear();
			tabellaSconti.getItems().setAll(ac.getScontiDisponibili());
			totale.setText("0.0");
			conferma.setDisable(true);	
			punti.setText("" + ac.getSaldoPunti());
		}
		else {
			alert("Esito acquisto", "Attenzione, si è verificato un errore nella procedura", "Puoi effettuare un nuovo tentativo o tornare nella schermata principale");
		}
	}

	@FXML
	public void indietro(ActionEvent e) {
		root = null;
		try {
			if(tabellaSelezionati != null) {
				for (int i = 0; i < tabellaSelezionati.getItems().size(); i++) {
					ProdottoSelezionato current = tabellaSelezionati.getItems().get(i);
					ac.aumentaDisponibilita(current, current.getQuantita());
				}
			}
			
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomeCliente.fxml"));
		} catch (IOException e1) { e1.printStackTrace(); }
		
		Scene scene = new Scene(root,900,600);
		Main.stage.setScene(scene);		
	}
	
	private static void inform(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	private static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
}
