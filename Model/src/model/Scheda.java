package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Scheda {
	String id;
	String idCliente;
	String idPersonalTrainer;
	LocalDateTime dataOra;
	LocalDate dateInizio;
	int durataSettimane;
	String note;
	
	public Scheda(String id, String idCliente, String idPersonalTrainer, LocalDateTime dataOra, LocalDate dateInizio,
			int durataSettimane, String note) {
		super();
		this.id = id;
		this.idCliente = idCliente;
		this.idPersonalTrainer = idPersonalTrainer;
		this.dataOra = dataOra;
		this.dateInizio = dateInizio;
		this.durataSettimane = durataSettimane;
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public String getIdPersonalTrainer() {
		return idPersonalTrainer;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public LocalDate getDateInizio() {
		return dateInizio;
	}

	public int getDurataSettimane() {
		return durataSettimane;
	}

	public String getNote() {
		return note;
	}
	
	
}
