package model;

public class ProdottoSelezionato implements Comparable<ProdottoSelezionato> {
	private int codice;
	private String nome;
	private float prezzo;
	private int quantita;
	
	public ProdottoSelezionato() {}
	
	public ProdottoSelezionato(int codice, String nome, float prezzo, int quantita) {
		this.codice = codice;
		this.nome = nome;
		this.prezzo = prezzo;
		this.quantita = quantita;
	}


	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	public float getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}


	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	
	@Override
	public int compareTo(ProdottoSelezionato that) {
		return this.getCodice() - that.getCodice();
	}
}
