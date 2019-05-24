package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RichiestaPianoNutrizionale extends Richiesta {
	private int altezza;
	private int peso;
	private String ElencoAllergeni;
	
	public RichiestaPianoNutrizionale(String id, Cliente idCliente, PersonalTrainer idPersonalTrainer, LocalDateTime dataOra,
			LocalDate dateInizio, int durataSettimane, String note, int altezza, int peso, String elencoAllergeni) {
		super(id, idCliente, idPersonalTrainer, dataOra, dateInizio, durataSettimane, note);
		this.altezza = altezza;
		this.peso = peso;
		ElencoAllergeni = elencoAllergeni;
	}

	public int getAltezza() {
		return altezza;
	}

	public int getPeso() {
		return peso;
	}

	public String getElencoAllergeni() {
		return ElencoAllergeni;
	}
	
	
}
