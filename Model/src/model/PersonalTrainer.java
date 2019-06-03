package model;
import java.time.LocalDate;

public class PersonalTrainer extends Utente implements Comparable<PersonalTrainer>{
	
	public PersonalTrainer(String nome, String cognome,
			String email, String codiceFiscale,
			LocalDate dataDiNascita, String luogoNascita,
			String indirizzoResidenza, String numTelefono, String id) {
		
		super(nome, cognome, email, codiceFiscale,
				dataDiNascita, luogoNascita, indirizzoResidenza, numTelefono, id);
	}

	@Override
	public int compareTo(PersonalTrainer that) {
		if(that.getCognome().equalsIgnoreCase(this.cognome))
			return this.getNome().compareToIgnoreCase(that.getNome());
		return this.getCognome().compareToIgnoreCase(that.getCognome());
	}
	
	

}
