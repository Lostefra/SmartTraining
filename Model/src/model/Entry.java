package model;

import java.time.LocalDateTime;

import util.Utilities;

public class Entry {
	private LocalDateTime dataOra;
	private String idUtente;
	private String descrizione;
	private String stringDataOra;

	public Entry(LocalDateTime dataOra, String idUtente,  String descrizione) {
		super();
		this.dataOra = dataOra;
		this.idUtente = idUtente;
		this.descrizione = descrizione;
		stringDataOra = dataOra.format(Utilities.formatterDataOra);
	}

	public String getStringDataOra() {
		return stringDataOra;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public String getIdUtente() {
		return idUtente;
	}
	
}
