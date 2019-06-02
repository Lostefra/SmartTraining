package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Cliente;
import model.Prodotto;
import model.ProdottoSelezionato;
import model.Sconto;
import util.Utilities;

//questo controller e' visto interamento dal fxml (deve essere interno al progetto)
public class ControllerAcquisto {

	@FXML private TextArea codiceProdotto;
	@FXML private TextArea nomeProdotto;
	@FXML private TextArea prezzoMin;
	@FXML private TextArea prezzoMax;	
	
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
  	}
  	
  	/**
	 * Legge tutti i prodotti dal database e popola la tabella
	 */
	public void getProdotti() {
		prodotti = ac.getProdotti();
		//la tabella sara' non nulla quando sara' caricato il file VisualizzaLog.fxml
		if(tabellaProdotti != null) {
			codiceCol.setCellValueFactory(new PropertyValueFactory<Prodotto, Integer>("codice"));
	        prodottoCol.setCellValueFactory(new PropertyValueFactory<Prodotto, String>("nome"));
	        descCol.setCellValueFactory(new PropertyValueFactory<Prodotto, String>("descrizione"));
	        prezzoCol.setCellValueFactory(new PropertyValueFactory<Prodotto, Float>("prezzo"));
	        disponibilitaCol.setCellValueFactory(new PropertyValueFactory<Prodotto, Integer>("disponibilita"));
	        
	        Collections.sort(prodotti);
	        tabellaProdotti.getItems().setAll(prodotti);
	        
	        tabellaProdotti.setOnMouseClicked(e -> {
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
	        	if(e.getClickCount() == 2) //Doppio click per selezionare lo sconto
	        		totale.setText("" + ac.applicaSconto(tabellaSconti.getSelectionModel().getSelectedItem()));
	        });
	    }
	}
	
	
	/**
	 * Inizializza la tabella dei prodotti selezionati
	 */
	@FXML
	public void initSelezionati() {
		selezionati = ac.getSelezionati; //Dovrei prendere la lista dei selezionati 
		ma se non ho aggiunto nulla è vuota
		
		codiceSelezionatiCol.setCellValueFactory(new PropertyValueFactory<ProdottoSelezionato, Integer>("codice"));
        prodottoSelezionatiCol.setCellValueFactory(new PropertyValueFactory<ProdottoSelezionato, String>("nome"));
        quantitaSelezionatiCol.setCellValueFactory(new PropertyValueFactory<ProdottoSelezionato, Integer>("quantita"));
        prezzoSelezionatiCol.setCellValueFactory(new PropertyValueFactory<ProdottoSelezionato, Float>("prezzo"));
	}
  	
	/**
	 * Applica il filtro specificato dall'utente tramite i textField della view
	 * @param evento dato dal click del mouse sul bottone
	 */
  	@FXML
	public void applicaFiltro(ActionEvent event) {
		prodotti = ac.getProdotti();
		
		int codice = Integer.parseInt(codiceProdotto.getText());
		String prod = nomeProdotto.getText();
		float pMin = Float.parseFloat(prezzoMin.getText());
		float pMax = Float.parseFloat(prezzoMax.getText());
		
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
		quantita.getSelectionModel().clearSelection();
		
		if(max > 10) max = 10;
		
		for (int i = 0; i < max; i++)
			quantita.getItems().add(i);
	}
	
	@FXML
	public void aggiungi(ActionEvent event) {
		Prodotto prodotto = tabellaProdotti.getSelectionModel().getSelectedItem();
		
		ProdottoSelezionato pSelected = new ProdottoSelezionato();
		pSelected.setCodice(prodotto.getCodice());
		pSelected.setNome(prodotto.getNome());
		pSelected.setQuantita(quantita.getValue());
		pSelected.setPrezzo(prodotto.getPrezzo());
		
		ac.aggiungiProdotto(pSelected, quantita.getValue());
        tabellaSelezionati.getItems().setAll(pSelected);
        totale.setText("" + ac.calcolaSommaSpesa());
	}

	@FXML
	public void confermaCarrello(ActionEvent event) {
		aggiungi.setDisable(true);
		tabellaSconti.getItems().setAll(ac.confermaCarrello());
		toChange.setText("Questi sono gli sconti disponibili per te:");
	}
	
	
	
	@FXML
	public void elimina(ActionEvent event) {
		 ProdottoSelezionato prodottoSel = tabellaSelezionati.getSelectionModel().getSelectedItem();
		 ac.eliminaProdotto(prodottoSel, prodottoSel.getQuantita());
		 
		 tabellaSelezionati.getItems().remove(prodottoSel);
		 totale.setText("" + ac.calcolaSommaSpesa());
	}
	
	@FXML
	public void conferma(ActionEvent event) {
		Sconto s = tabellaSconti.getSelectionModel().getSelectedItem(); 
		ac.conferma(s);
	}

	@FXML
	public void indietro(ActionEvent e) {
		root = null;
		try {
			for (int i = 0; i < tabellaSelezionati.getItems().size(); i++) {
				ProdottoSelezionato current = tabellaSelezionati.getItems().get(i);
				ac.aumentaDisponibilita(current, current.getQuantita());
			}
			
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomeCliente.fxml"));
		} catch (IOException e1) { e1.printStackTrace(); }
		
		Scene scene = new Scene(root,900,600);
		Main.stage.setScene(scene);		
	}
}
