package model;

import java.time.LocalDate;

import util.Utilities;

public class ObservableScheda {
	private String nomeCliente;
	private String cognomeCliente;
	private String nomePT;
	private String cognomePT;
	private String dataInizio;
	private String dataFine;
	private String tipologia;
	
	public ObservableScheda(String nomeCliente, String cognomeCliente, String nomePT, String cognomePT,
			LocalDate dataInizio, LocalDate dataFine, String tipologia) {
		super();
		this.nomeCliente = nomeCliente;
		this.cognomeCliente = cognomeCliente;
		this.nomePT = nomePT;
		this.cognomePT = cognomePT;
		this.dataInizio = dataInizio.format(Utilities.formatterData);
		this.dataFine = dataFine.format(Utilities.formatterData);
		this.tipologia = tipologia;
	}

	public String getNomeCliente() {
		return nomeCliente;
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
	
	
	
}
