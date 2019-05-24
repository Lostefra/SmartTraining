package Attori;
import java.time.LocalDate;
import java.util.Random;


public abstract class Utente {
	
	protected final String nome;
	protected final String cognome;
	protected String email;
	protected final String codiceFiscale;
	protected final LocalDate dataDiNascita;
	protected final String luogoNascita;
	protected final String indirizzoResidenza;
	protected String numTelefono;
	protected final String id;
	
	private String alphaNumericCharacters = "abcdefghijklmnopqrstuvwxyz"
								            + "ABCDEFGHIJLMNOPQRSTUVWXYZ"
								            + "1234567890";
	
	public Utente(String nome, String cognome, 
			String email, String codiceFiscale, LocalDate dataDiNascita,
			String luogoNascita, String indirizzoResidenza, String numTelefono) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.codiceFiscale = codiceFiscale;
		this.dataDiNascita = dataDiNascita;
		this.luogoNascita = luogoNascita;
		this.indirizzoResidenza = indirizzoResidenza;
		this.numTelefono = numTelefono;
		this.id = generaID();
	}
	

	public Utente(String nome, String cognome, 
			String email, String codiceFiscale, LocalDate dataDiNascita,
			String luogoNascita, String indirizzoResidenza, String numTelefono, String id) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.codiceFiscale = codiceFiscale;
		this.dataDiNascita = dataDiNascita;
		this.luogoNascita = luogoNascita;
		this.indirizzoResidenza = indirizzoResidenza;
		this.numTelefono = numTelefono;
		this.id = id;
	}
	
	
	private String generaID() {
		Random r = new Random();
		StringBuilder result = new StringBuilder();
		
		for (int i =0; i< 6 ; i++) {
			result.append(
	        alphaNumericCharacters.charAt(r.nextInt(alphaNumericCharacters.length())));
	      }
		
		return result.toString();
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNumTelefono() {
		return numTelefono;
	}


	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}


	public String getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}


	public String getCognome() {
		return cognome;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}


	public String getLuogoNascita() {
		return luogoNascita;
	}


	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}
	
}
