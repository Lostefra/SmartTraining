package accesso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import util.Utilities;

public class RegistrazioneController {

	/**
	 * Possibili return:
	 * "Errore: username già presente nel sistema"
	 * "Errore: email già presente nel sistema"
	 * "Errore: codice fiscale già presente nel sistema"
	 * "Errore: codiceID errato"
	 * "Errore: codiceFiscale non trovato"
	 * "Errore: errore scrittura del PersonalTrainer nel database"
	 * "T"
	 * 
	 * @param username
	 * @param password
	 * @param nome
	 * @param cognome
	 * @param email
	 * @param codiceFiscale
	 * @param dataNascita
	 * @param luogoNascita
	 * @param indirizzoResidenza
	 * @param numeroTelefono
	 * @param codiceID
	 * @return String result
	 * @throws IOException
	 */
	
	public String registrazione(String username, String password, String nome, 
		String cognome, String email, String codiceFiscale, LocalDate dataNascita,
		String luogoNascita, String indirizzoResidenza, String numeroTelefono, String codiceID) throws IOException {
		
		File inputFile = new File("C:/SmartTraining/utenti.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		
		String currentLine;
		String lineToRemove = null;
		
		String[] utente = new String[300];
		boolean pt = false;
		
		/* Controllo esistenza credenziali */
		while((currentLine = reader.readLine()) != null) {
			utente = currentLine.split("|");
			if(username.equals(utente[0]))
				return "Errore: username già presente nel sistema";
			else if (email.equals(utente[6]))
				return "Errore: email già presente nel sistema";
			else if (codiceFiscale.equals(utente[7]) && !(username.equals("null")))
				return "Errore: codice fiscale già presente nel sistema";
		}
		reader.close();
		
		/* Controllo codiceID */
		if (!(codiceID.equals("null"))) {
			boolean found = false;
			pt = true;
			reader = new BufferedReader(new FileReader(inputFile));
			while((currentLine = reader.readLine()) != null && !found) {
				utente = currentLine.split("|");
				if(codiceFiscale.equals(utente[7])) {
					found = true;
					if (!(codiceID.equals(utente[15])))
						return "Errore: codiceID errato";
				}
				lineToRemove = currentLine;
			}	
			if (!found)
				return "Errore: codiceFiscale non trovato";
			reader.close();
		}
		
		/* Aggiugnta PersonalTrainer nel DB */
		if(pt) {
			File tempFile = new File("C:/SmartTraining/temp.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			reader = new BufferedReader(new FileReader(inputFile));
			
			while ((currentLine = reader.readLine()) != null) {
				if (!(currentLine.equals(lineToRemove)))
					writer.write(currentLine+"\n");
			}
			writer.write(username+"|"+password+"|P|"+Utilities.generaID("P", 5)+"|"+nome+"|"+cognome+"|"+email+"|"
					+codiceFiscale+"|"+dataNascita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"|"+luogoNascita
					+"|"+indirizzoResidenza+"|"+numeroTelefono+"|"+codiceID+"\n");
			boolean t = tempFile.renameTo(inputFile);
			if (t)
				return "T";
			else
				return "Errore: errore scrittura nel database";
		}
		
		/* Aggiunta Cliente al DB */
		PrintWriter writer = Utilities.apriFileAppend("utenti.txt");
		writer.write(username+"|"+password+"|C|"+Utilities.generaID("C", 5)+nome+"|"+cognome+"|"+email+"|"
				+codiceFiscale+"|"+dataNascita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"|"+luogoNascita
				+"|"+indirizzoResidenza+"|"+numeroTelefono+"|null/n");
		
		return "T";
	}
}
