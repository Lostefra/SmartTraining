package model;

import java.time.LocalDateTime;

public class OrarioIngressoUscita {
	private LocalDateTime ingresso;
	private LocalDateTime uscita;
	
	
	public OrarioIngressoUscita(LocalDateTime ingresso) {
		this.ingresso = ingresso;
		this.uscita = null;
	}

	public OrarioIngressoUscita(LocalDateTime ingresso, LocalDateTime uscita) {
		this.ingresso = ingresso;
		this.uscita = uscita;
	}

	public LocalDateTime getIngresso() {
		return ingresso;
	}

	public void setIngresso(LocalDateTime ingresso) {
		this.ingresso = ingresso;
	}

	public LocalDateTime getUscita() {
		return uscita;
	}

	public void setUscita(LocalDateTime uscita) {
		this.uscita = uscita;
	}
	
	
	

}
