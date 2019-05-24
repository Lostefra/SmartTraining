package model;
import java.time.LocalDate;

public class Cliente extends Utente {
	
	private TesseraSocio tes;
	
	public Cliente(String nome, String cognome,
			String email, String codiceFiscale, LocalDate dataDiNascita,
			String luogoNascita, String indirizzoResidenza,
			String numTelefono, TesseraSocio tesSoc) {
		
		super(nome, cognome, email, codiceFiscale, dataDiNascita, luogoNascita, indirizzoResidenza, numTelefono);
		this.tes = tesSoc;
	}

	public Cliente(String nome, String cognome,
			String email, String codiceFiscale, LocalDate dataDiNascita,
			String luogoNascita, String indirizzoResidenza,
			String numTelefono, TesseraSocio tesSoc, String id) {
		
		super(nome, cognome, email, codiceFiscale, dataDiNascita, luogoNascita, indirizzoResidenza, numTelefono, id);
		this.tes = tesSoc;
	}
	
	public TesseraSocio getTes() {
		return tes;
	}

	public void setTes(TesseraSocio tes) {
		this.tes = tes;
	}
	
}
