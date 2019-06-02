package model;

import java.time.LocalDate;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import util.Utilities;

public class ObservableScheda implements ObservableValue<ObservableScheda>{
	private String nomeCliente;
	private String cognomeCliente;
	private String nomePT;
	private String cognomePT;
	private String dataInizio;
	private String dataFine;
	private String tipologia;
	private String id;
	
	public ObservableScheda(String id,String nomeCliente, String cognomeCliente, String nomePT, String cognomePT,
			LocalDate dataInizio, LocalDate dataFine, String tipologia) {
		super();
		this.nomeCliente = nomeCliente;
		this.cognomeCliente = cognomeCliente;
		this.nomePT = nomePT;
		this.cognomePT = cognomePT;
		this.dataInizio = dataInizio.format(Utilities.formatterData);
		this.dataFine = dataFine.format(Utilities.formatterData);
		this.tipologia = tipologia;
		this.id = id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public String getId() {
		return id;
	}

	
	public String getCognomeCliente() {
		return cognomeCliente;
	}

	public String getNomePT() {
		return nomePT;
	}

	public String getCognomePT() {
		return cognomePT;
	}

	public String getDataInizio() {
		return dataInizio;
	}

	public String getDataFine() {
		return dataFine;
	}

	public String getTipologia() {
		return tipologia;
	}

	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(ChangeListener<? super ObservableScheda> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ObservableScheda getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeListener(ChangeListener<? super ObservableScheda> listener) {
		// TODO Auto-generated method stub
		
	}

	
	
}
