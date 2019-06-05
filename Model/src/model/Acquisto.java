package model;


public class Acquisto {
	private int codice;
	private String dataOra;
	private int puntiGuadagnati;
	
	public Acquisto(int codice, String dataOra, int puntiGuadagnati) {
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
	
	public String getDataOra() {
		return dataOra;
	}
	public void setDataOra(String dataOra) {
		this.dataOra = dataOra;
	}
	
	public int getPuntiGuadagnati() {
		return puntiGuadagnati;
	}
	public void setPuntiGuadagnati(int puntiGuadagnati) {
		this.puntiGuadagnati = puntiGuadagnati;
	}
	
	@Override
	public String toString() {
		return "Codice Acquisto: " + codice + "\nData e Ora: " + dataOra +
				"\nHai guadagnato " + puntiGuadagnati + " punti";
	}
	
	public String toStringPalestra() {
		return "Codice Acquisto: " + codice + "\nData e Ora: " + dataOra;
	}
}
