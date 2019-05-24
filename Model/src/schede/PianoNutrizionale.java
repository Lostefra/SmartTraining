package schede;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Attori.Cliente;
import Attori.PersonalTrainer;

public class PianoNutrizionale extends Scheda {

	private List<Pasto> pasti;
	
	public PianoNutrizionale(String id, Cliente cliente, PersonalTrainer personalTrainer, LocalDateTime dataOra,
			LocalDate dateInizio, int durataSettimane, String note) {
		super(id, cliente, personalTrainer, dataOra, dateInizio, durataSettimane, note);
		
		pasti = new ArrayList<Pasto>(); 	//si fa add per riempire, remove per rimuovere
	}

	public List<Pasto> getPasti() {
		return pasti;
	}

	//per aggiornare una pasto con un nuovo alimento
	public Pasto getRemovePasto(Pasto p) {
		Pasto res = null;
		for(Pasto tmp : pasti) {
			if(tmp.equals(p)) {
				res = tmp;
				pasti.remove(tmp);
				return res;
			}
		}
			
		return res;
	}

	
}
