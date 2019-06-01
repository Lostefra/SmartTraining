package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Scheda;
import model.SchedaAllenamento;
import schede.SchedeController;
import util.Utilities;

public class ControllerAttuali {

	AnchorPane root;
	@FXML private TableView<ObservableScheda> listaSchede;
	@FXML private TableColumn<ObservableScheda, String> nome;
	@FXML private TableColumn<ObservableScheda, String> cognome;
	@FXML private TableColumn<ObservableScheda, String> dataInizio;
	@FXML private TableColumn<ObservableScheda, String> dataFine;
	@FXML private TableColumn<ObservableScheda, String> pt;
	@FXML private TableColumn<ObservableScheda, String> tipologia;
	
	@FXML private TabPane tabPane;
	
	@FXML private Tab lun;
	@FXML private TableView lunTable;
	@FXML private TableColumn colAlun;
	@FXML private TableColumn colBlun;
	@FXML private TableColumn colClun;
	@FXML private Tab mar;
	@FXML private TableView marTable;
	@FXML private TableColumn colAmar;
	@FXML private TableColumn colBmar;
	@FXML private TableColumn colCmar;
	@FXML private Tab mer;
	@FXML private TableView merTable;
	@FXML private TableColumn colAmer;
	@FXML private TableColumn colBmer;
	@FXML private TableColumn colCmer;
	@FXML private Tab gio;
	@FXML private TableView gioTable;
	@FXML private TableColumn colAgio;
	@FXML private TableColumn colBgio;
	@FXML private TableColumn colCgio;
	@FXML private Tab ven;
	@FXML private TableView venTable;
	@FXML private TableColumn colAven;
	@FXML private TableColumn colBven;
	@FXML private TableColumn colCven;
	@FXML private Tab sab;
	@FXML private TableView sabTable;
	@FXML private TableColumn colAsab;
	@FXML private TableColumn colBsab;
	@FXML private TableColumn colCsab;
	@FXML private Tab dom;
	@FXML private TableView domTable;
	@FXML private TableColumn colAdom;
	@FXML private TableColumn colBdom;
	@FXML private TableColumn colCdom;
	
	
	 //vedere initialize confronto log controller carica log fxml
	@FXML private void initialize()  {
		SchedeController sc = new SchedeController();
		List<Scheda> schede = sc.visualizzaAttuali(Utilities.leggiCliente(Main.idCliente));
		List<ObservableScheda> obsList = new ArrayList<>();
		String tipologiaString;
		for(Scheda s: schede) {
			if(s instanceof SchedaAllenamento)
				tipologiaString = "Scheda di Allenamento";
			else
				tipologiaString = "Piano Nutrizionale";
			obsList.add(new ObservableScheda(s.getCliente().getNome(), s.getCliente().getCognome(),
					s.getPersonalTrainer().getNome(), s.getPersonalTrainer().getCognome(), s.getDateInizio(),
					s.getDateInizio().plusWeeks(s.getDurataSettimane()), tipologiaString));
		}
		nome.setCellValueFactory(new PropertyValueFactory<ObservableScheda, String>("nomePT"));
        cognome.setCellValueFactory(new PropertyValueFactory<ObservableScheda, String>("cognomePT"));
        dataInizio.setCellValueFactory(new PropertyValueFactory<ObservableScheda, String>("dataInizio"));
        dataFine.setCellValueFactory(new PropertyValueFactory<ObservableScheda, String>("dataFine"));
        tipologia.setCellValueFactory(new PropertyValueFactory<ObservableScheda, String>("tipologia"));    
        listaSchede.getItems().setAll(obsList);
        


        listaSchede.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
	        @Override
	        public void changed(ObservableValue<?> observableValue, Object oldValue, Object newValue) {
	            //Check whether item is selected and set value of selected item to Label
	            if(listaSchede.getSelectionModel().getSelectedItem() != null) 
	            {    
	               TableViewSelectionModel<ObservableScheda> selectionModel = listaSchede.getSelectionModel();
	               ObservableList<?> selectedCells = selectionModel.getSelectedCells();
	               ObservableScheda o = (ObservableScheda) selectedCells.get(0);
	               //TODO
	             }
             }
         });
        
	}
	
	@FXML
	public void indietro(ActionEvent event) throws IOException {
		root = null;
		
		root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomeCliente.fxml"));
		Main.stage.setScene(new Scene(root,900,600));
	}
	
}
