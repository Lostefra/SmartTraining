package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Scheda {

	private String id;
	private Cliente cliente;
	private PersonalTrainer personalTrainer;
	private LocalDateTime dataOra;
	private LocalDate dateInizio;
	private int durataSettimane;
	private String note;
	
	public Scheda(String id, Cliente cliente, PersonalTrainer personalTrainer, LocalDateTime dataOra,
			LocalDate dateInizio, int durataSettimane, String note) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.personalTrainer = personalTrainer;
		this.dataOra = dataOra;
		this.dateInizio = dateInizio;
		this.durataSettimane = durataSettimane;
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public PersonalTrainer getPersonalTrainer() {
		return personalTrainer;
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
