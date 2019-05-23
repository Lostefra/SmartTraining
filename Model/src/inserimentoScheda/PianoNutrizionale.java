package inserimentoScheda;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PianoNutrizionale extends Scheda {
	int altezza;
	int peso;
	String ElencoAllergeni;
	
	public PianoNutrizionale(String id, String idCliente, String idPersonalTrainer, LocalDateTime dataOra,
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
