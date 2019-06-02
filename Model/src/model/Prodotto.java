package model;

public class Prodotto implements Comparable<Prodotto> {
	private int codice;
	private String nome;
	private float prezzo;
	private String descrizione;
	private int disponibilita;
	private Integer codiceInteger;
	private Float prezzoFloat;
	private Integer disponibilitaInteger;
	
	
	public Prodotto(int codice, String nome, float prezzo,
					String descrizione, int disponibilita) {
		this.codice = codice;
		this.nome = nome;
		this.prezzo = prezzo;
		this.descrizione = descrizione;
		this.disponibilita = disponibilita;
		codiceInteger = new Integer(codice);
		disponibilitaInteger = new Integer(disponibilita);
		prezzoFloat = new Float(prezzo);

	}


	public Integer getCodiceInteger() {
		return codiceInteger;
	}


	public Float getPrezzoFloat() {
		return prezzoFloat;
	}


	public Integer getDisponibilitaInteger() {
		return disponibilitaInteger;
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


	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public int getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}
	
	@Override
	public int compareTo(Prodotto that) {
		return this.getCodice() - that.getCodice();
	}
}
