package model;

import java.time.LocalDateTime;

public class EntryOperazione extends Entry {
	private String attivita;
	private String idUtente;
	
	public EntryOperazione(LocalDateTime dataOra, String attivita, String idUtente) {
		super(dataOra);
		this.attivita = attivita;
		this.idUtente = idUtente;
	}

	public String getAttivita() {
		return attivita;
	}

	public String getIdUtente() {
		return idUtente;
	}
	
}
