package model;

import java.time.LocalTime;

public class Esercizio {
	private String nome;
	private int numeroSerie;
	private int numeroRipetizioni;
	private LocalTime tempoRecupero;
	
	public Esercizio(String nome, int numeroSerie, int numeroRipetizioni, LocalTime tempoRecupero) {
		super();
		this.nome = nome;
		this.numeroSerie = numeroSerie;
		this.numeroRipetizioni = numeroRipetizioni;
		this.tempoRecupero = tempoRecupero;
	}

	public String getNome() {
		return nome;
	}

	public int getNumeroSerie() {
		return numeroSerie;
	}

	public int getNumeroRipetizioni() {
		return numeroRipetizioni;
	}

	public LocalTime getTempoRecupero() {
		return tempoRecupero;
	}

	
}
