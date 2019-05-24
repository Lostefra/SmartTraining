package Attori;
import java.time.LocalDate;

public class PersonalTrainer extends Utente {

	public PersonalTrainer(String nome, String cognome,
			String email, String codiceFiscale,
			LocalDate dataDiNascita, String luogoNascita,
			String indirizzoResidenza, String numTelefono) {
		
		super(nome, cognome, email, codiceFiscale,
				dataDiNascita, luogoNascita, indirizzoResidenza, numTelefono);
	}
	
	public PersonalTrainer(String nome, String cognome,
			String email, String codiceFiscale,
			LocalDate dataDiNascita, String luogoNascita,
			String indirizzoResidenza, String numTelefono, String id) {
		
		super(nome, cognome, email, codiceFiscale,
				dataDiNascita, luogoNascita, indirizzoResidenza, numTelefono, id);
	}

}
