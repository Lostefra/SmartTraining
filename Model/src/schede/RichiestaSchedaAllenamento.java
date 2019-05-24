package schedeRichieste;

import java.time.LocalDate;
import java.time.LocalDateTime;

import Attori.Cliente;
import Attori.PersonalTrainer;

public class RichiestaSchedaAllenamento extends Richiesta {
	private int numeroAllenamentiSettimanali;

	public RichiestaSchedaAllenamento(String id, Cliente idCliente, PersonalTrainer idPersonalTrainer, LocalDateTime dataOra,
			LocalDate dateInizio, int durataSettimane, String note, int numeroAllenamentiSettimanali) {
		super(id, idCliente, idPersonalTrainer, dataOra, dateInizio, durataSettimane, note);
		this.numeroAllenamentiSettimanali = numeroAllenamentiSettimanali;
	}

	public int getNumeroAllenamentiSettimanali() {
		return numeroAllenamentiSettimanali;
	}
		
}
