package schede;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class Sessione {
	private DayOfWeek giorno;
	private List<Esercizio> esercizi;
	
	public Sessione(DayOfWeek giorno) {
		super();
		this.giorno = giorno;
		this.esercizi = new ArrayList<Esercizio>();	//si fa add per riempire, remove per rimuovere
	}

	public DayOfWeek getGiorno() {
		return giorno;
	}

	public List<Esercizio> getEsercizi() {
		return esercizi;
	}
	
	public boolean equals(Sessione sessione) {
		return sessione.getGiorno().equals(giorno);
	}
	
	
}
