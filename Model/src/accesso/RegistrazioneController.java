package accesso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

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
		
		File inputFile = new File("C:/SmartTrainingFiles/utenti.txt");
		
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		
		String currentLine;
		String lineToRemove = null;
		
		String[] utente = new String[300];
		boolean pt = false;
		
		/* Controllo esistenza credenziali */
		while((currentLine = reader.readLine()) != null) {
			utente = currentLine.split("\\|");
/*			System.out.println("1"+utente[0]);
			System.out.println(utente[1]);
			System.out.println(utente[2]);
			System.out.println(utente[3]);
			System.out.println(utente[4]);
			System.out.println(utente[5]);
			System.out.println(utente[6]);
			System.out.println(utente[7]);
			System.out.println(utente[8]);
			System.out.println(utente[9]);
*/			
			if(username.equals(utente[0]))
				return "Errore: username già presente nel sistema";
			else if (email.equals(utente[6]))
				return "Errore: email già presente nel sistema";
			else if (codiceFiscale.equals(utente[7]) && !(utente[0].equals("null")))
				return "Errore: codice fiscale già presente nel sistema";
		}
		reader.close();
		
		/* Controllo codiceID */
		if (!(codiceID.equals("null"))) {
			boolean found = false;
			pt = true;
			reader = new BufferedReader(new FileReader(inputFile));
			while((currentLine = reader.readLine()) != null && !found) {
				utente = currentLine.split(Pattern.quote("|"));
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
		
		/* Aggiunta PersonalTrainer nel DB */
		if(pt) {
			File tempFile = new File("C:/SmartTrainingFiles/temp.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			reader = new BufferedReader(new FileReader(inputFile));
			
			while ((currentLine = reader.readLine()) != null) {
				if (!(currentLine.equals(lineToRemove))) 
					writer.write(currentLine+"\n");
			}			
			reader.close();
			
			reader = new BufferedReader(new FileReader(inputFile));
			
			writer.write(username+"|"+password+"|P|"+generateID(reader, "P")+"|"+nome+"|"+cognome+"|"+email+"|"
					+codiceFiscale+"|"+dataNascita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"|"+luogoNascita
					+"|"+indirizzoResidenza+"|"+numeroTelefono+"|null|null|null|"+codiceID+"\n");
			reader.close();
			writer.close();
			if(!inputFile.delete())
				return "Errore: errore scrittura nel database";
			
			boolean t = tempFile.renameTo(inputFile);			
			if (t)
				return "T";
			else {
				return "Errore: errore scrittura nel database";
			}
		}
		
		/* Creazione tessera socio */
		int codice;
		boolean codiceEsiste = false;
		reader = Utilities.apriFile("utenti.txt");
		do {
			codice = Utilities.generaIntero();
			while((currentLine = reader.readLine()) != null && !codiceEsiste) {
				String[] campi = new String[100];
				campi = currentLine.split(Pattern.quote("|"));
				if(campi[2].equals("C") && Integer.parseInt(campi[12]) == (codice))
					codiceEsiste = true;
			}
		}while(codiceEsiste == true);
		reader.close();
		
		/* Aggiunta Cliente al DB */
		reader = new BufferedReader(new FileReader(inputFile));
		
		
		PrintWriter writer = Utilities.apriFileAppend("utenti.txt");
		String idCliente =generateID(reader, "C");
		if(username == null) {
			System.out.println("Username");
		}
		if(password == null) {
			System.out.println("password");
		}
		if(idCliente == null) {
			System.out.println("idCliente");
		}
		if(nome == null) {
			System.out.println("nome");
		}
		if(cognome == null) {
			System.out.println("cognome");
		}
		if(email == null) {
			System.out.println("email");
		}
		if(codiceFiscale == null) {
			System.out.println("codiceFiscale");
		}
		if(dataNascita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) == null) {
			System.out.println("dataNascita.format(DateTimeFormatter.ofPattern(\"dd/MM/yyyy\"))");
		}
		if(luogoNascita == null) {
			System.out.println("luogoNascita");
		}
		if(indirizzoResidenza == null) {
			System.out.println("indirizzoResidenza");
		}
		if(numeroTelefono == null) {
			System.out.println("numeroTelefono");
		}

		writer.write(username+"|"+password+"|C|"+idCliente+"|"+nome+"|"+cognome+"|"+email+"|"
				+codiceFiscale+"|"+dataNascita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"|"+luogoNascita
				+"|"+indirizzoResidenza+"|"+numeroTelefono+"|"+codice+"|0|" + 
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))+ "|null\n");
		
		reader.close();
		writer.close();
		return "T";
	}
	
	//dd
	private String generateID (BufferedReader bf, String type) throws IOException{
		String currentLine;
		String[] user;
		String id;
		boolean ok = true;
		do {
			id = Utilities.generaID(type, 5);
			while ((currentLine=bf.readLine()) != null) {
				user = currentLine.split(Pattern.quote("|"));
				if (id.equals(user[2]))
					ok = false;
			}
		} while (!ok);
		return id;
	}
}
