package model;

import java.time.LocalTime;

import util.Utilities;

public class ObservableSchedaContenuto {
	
	private String esercizio;
	private String ripetizioni;
	private String recupero;
	private String alimento;
	private String orario;
	private String peso;
	
	public ObservableSchedaContenuto(LocalTime ora, String nome, int peso2) {
		orario = ora.format(Utilities.formatterOra);
		alimento = nome;
		peso = peso2 + "";
	}
	
	public ObservableSchedaContenuto(String nome, int serie, int ripetizioni, LocalTime recupero) {
		this.recupero = recupero.format(Utilities.formatterRecupero);
		esercizio = nome;
		this.ripetizioni = serie + "x"+ ripetizioni;
	}

	public String getEsercizio() {
		return esercizio;
	}

	public String getRipetizioni() {
		return ripetizioni;
	}

	public String getRecupero() {
		return recupero;
	}

	public String getAlimento() {
		return alimento;
	}

	public String getOrario() {
		return orario;
	}

	public String getPeso() {
		return peso;
	}
	
	
}
