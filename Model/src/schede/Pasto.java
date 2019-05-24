package schede;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Pasto {
	private DayOfWeek giorno;
	private LocalTime ora;
	private List<Alimento> alimenti;
	
	public Pasto(DayOfWeek giorno, LocalTime ora) {
		super();
		this.giorno = giorno;
		this.ora = ora;
		alimenti = new ArrayList<Alimento>(); 	//si fa add per riempire, remove per rimuovere
	}

	public DayOfWeek getGiorno() {
		return giorno;
	}

	public LocalTime getOra() {
		return ora;
	}

	public List<Alimento> getAlimenti() {
		return alimenti;
	}
	
	public boolean equals(Pasto pasto) {
		return pasto.getGiorno().equals(giorno) && pasto.getOra().equals(ora);
	}
	
	
	
}
