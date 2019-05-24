package model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class EsercizioAlimento {
	private Alimento alimento;
	private Esercizio esercizio;
	private DayOfWeek giorno;
	private LocalTime ora;
	
	public EsercizioAlimento(String nome, int peso, DayOfWeek giorno, LocalTime ora) {
		alimento = new Alimento(nome, peso);
		this.giorno = giorno;
		this.ora = ora;
	}
	
	public EsercizioAlimento(String nome, int numeroSerie, int numeroRipetizioni, 
			LocalTime tempoRecupero, DayOfWeek giorno, LocalTime ora) {
		esercizio = new Esercizio(nome, numeroSerie, numeroRipetizioni, tempoRecupero);
		this.giorno = giorno;
	}

	public Alimento getAlimento() {
		return alimento;
	}

	public Esercizio getEsercizio() {
		return esercizio;
	}

	public DayOfWeek getGiorno() {
		return giorno;
	}

	public LocalTime getOra() {
		return ora;
	}
	
}
