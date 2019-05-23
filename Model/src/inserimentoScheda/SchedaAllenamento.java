package inserimentoScheda;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SchedaAllenamento extends Scheda {
	int numeroAllenamentiSettimanali;

	public SchedaAllenamento(String id, String idCliente, String idPersonalTrainer, LocalDateTime dataOra,
			LocalDate dateInizio, int durataSettimane, String note, int numeroAllenamentiSettimanali) {
		super(id, idCliente, idPersonalTrainer, dataOra, dateInizio, durataSettimane, note);
		this.numeroAllenamentiSettimanali = numeroAllenamentiSettimanali;
	}

	public int getNumeroAllenamentiSettimanali() {
		return numeroAllenamentiSettimanali;
	}
		
}
