package model;

public class Sconto {
	private float valore;
	private int puntiRichiesti;
	private float spesaMinima;

	public Sconto(float valore, int puntiRichiesti, float spesaMinima) {
		this.valore = valore;
		this.puntiRichiesti = puntiRichiesti;
		this.spesaMinima = spesaMinima;
	}

	public boolean isAvailable(int puntiSullaTessera, float sommaSpesa) {
		return (sommaSpesa > spesaMinima) && (puntiSullaTessera > puntiRichiesti);
	}
	
	public float getValore() {
		return valore;
	}
	
	public int getPuntiRichiesti() {
		return puntiRichiesti;
	}

	public float getSpesaMinima() {
		return spesaMinima;
	}
}
