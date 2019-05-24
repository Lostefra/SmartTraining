package schedeRichieste;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Attori.Cliente;
import Attori.PersonalTrainer;

public class SchedaAllenamento extends Scheda {

	private List<Sessione> sessioni;
	
	public SchedaAllenamento(String id, Cliente cliente, PersonalTrainer personalTrainer, LocalDateTime dataOra,
			LocalDate dateInizio, int durataSettimane, String note) {
		super(id, cliente, personalTrainer, dataOra, dateInizio, durataSettimane, note);
		
		sessioni = new ArrayList<Sessione>(); 	//si fa add per riempire, remove per rimuovere
	}

	public List<Sessione> getSessioni() {
		return sessioni;
	}
	
	
	//per aggiornare una sessione con un nuovo esericizio
	public Sessione getRemoveSessione(Sessione s) {
		Sessione res = null;
		for(Sessione tmp : sessioni) {
			if(tmp.equals(s)) {
				res = tmp;
				sessioni.remove(tmp);
				return res;
			}
		}
			
		return res;
	}
	
}
