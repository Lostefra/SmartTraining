package schedeRichieste;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Scheda {

	private String id;
	private String idCliente;
	private String idPersonalTrainer;
	private LocalDateTime dataOra;
	private LocalDate dateInizio;
	private int durataSettimane;
	private String note;
	
	public Scheda(String id, String cliente, String personalTrainer, LocalDateTime dataOra,
			LocalDate dateInizio, int durataSettimane, String note) {
		super();
		this.id = id;
		this.idCliente = cliente;
		this.idPersonalTrainer = personalTrainer;
		this.dataOra = dataOra;
		this.dateInizio = dateInizio;
		this.durataSettimane = durataSettimane;
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public String getCliente() {
		return idCliente;
	}

	public String getPersonalTrainer() {
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
