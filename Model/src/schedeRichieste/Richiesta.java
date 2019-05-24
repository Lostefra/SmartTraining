package schedeRichieste;

import java.time.LocalDate;
import java.time.LocalDateTime;

import Attori.Cliente;
import Attori.PersonalTrainer;

public abstract class Richiesta {
	private String id;
	private Cliente cliente;
	private PersonalTrainer personalTrainer;
	private LocalDateTime dataOra;
	private LocalDate dateInizio;
	private int durataSettimane;
	private String note;
	
	public Richiesta(String id, Cliente idCliente, PersonalTrainer idPersonalTrainer, LocalDateTime dataOra, LocalDate dateInizio,
			int durataSettimane, String note) {
		super();
		this.id = id;
		this.cliente = idCliente;
		this.personalTrainer = idPersonalTrainer;
		this.dataOra = dataOra;
		this.dateInizio = dateInizio;
		this.durataSettimane = durataSettimane;
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public Cliente getIdCliente() {
		return cliente;
	}

	public PersonalTrainer getIdPersonalTrainer() {
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
