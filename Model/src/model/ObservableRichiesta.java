package model;

import java.time.LocalDateTime;

import util.Utilities;

public class ObservableRichiesta {

	private String id;
	private String nome;
	private String cognome;
	private String tipologia;
	private LocalDateTime dataOra;
	private String dataOraStringa;
	
	
	public ObservableRichiesta(String id, String nome, String cognome, Richiesta richiesta, LocalDateTime dataOra, String dataOraStringa) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataOraStringa = dataOraStringa;
		if (richiesta instanceof RichiestaPianoNutrizionale)
			this.tipologia = "Piano Nutrizionale";
		else
			this.tipologia = "Scheda di Allenamento";
		this.dataOra = dataOra;
		
	}


	public String getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}


	public String getCognome() {
		return cognome;
	}


	public String getTipologia() {
		return tipologia;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}
	
	public String getDataOraStringa() {
		return dataOraStringa;
	}
	
	
}
