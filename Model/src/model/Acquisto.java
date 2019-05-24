package model;

import java.time.LocalDateTime;

public class Acquisto {
	private int codice;
	private LocalDateTime dataOra;
	private int puntiGuadagnati;
	
	public Acquisto(int codice, LocalDateTime dataOra, int puntiGuadagnati) {
		this.codice = codice;
		this.dataOra = dataOra;
		this.puntiGuadagnati = puntiGuadagnati;
	}
	
	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}
	
	public LocalDateTime getDataOra() {
		return dataOra;
	}
	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}
	
	public int getPuntiGuadagnati() {
		return puntiGuadagnati;
	}
	public void setPuntiGuadagnati(int puntiGuadagnati) {
		this.puntiGuadagnati = puntiGuadagnati;
	}
	
	
	
}
