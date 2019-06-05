package creazioneID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import log.LogController;
import util.Utilities;

public class CreazioneIDController {
	
	public String generaCodiceID(String codiceFiscale) {
		String id = null;
		if(codiceFiscale.length() == 16) {
			BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
			String line;
			String[] utente = new String[300];
			
			//verifico che il codice fiscale non sia gia associato ad un utente
			try {
				while((line = bf_utenti.readLine()) != null) {
					utente = line.split(Pattern.quote("|"));
					if(codiceFiscale.equals(utente[7]))
						return id;
				}
				bf_utenti.close();
				//codice fiscale non esistente in utenti.txt, OK
				
				//generazione codice id
				boolean codiceEsiste;
				do {
					id = Utilities.generaID("", 6);
					codiceEsiste = false;
					bf_utenti = Utilities.apriFile("utenti.txt");
					while((line = bf_utenti.readLine()) != null && !codiceEsiste) {
						String[] campi = new String[100];
						campi = line.split(Pattern.quote("|"));
						if(campi[15].equals(id))
							codiceEsiste = true;
					}
				}while(codiceEsiste == true);
				//id generato correttamente, segue inserimento nel file utenti .txt
				bf_utenti.close();
				PrintWriter pw_utenti = Utilities.apriFileAppend("utenti.txt");
				pw_utenti.write("null|null|P|null|null|null|null|"+ codiceFiscale +"|null|null|null|null|null|null|null|" + id + '\n');
				pw_utenti.close();
				//codice inserito, lo restituisco al client
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		LogController lc = new LogController();
		lc.scriviMessaggio(LocalDateTime.now() ,"Generato correttamente l'id: "+ id);
		return id;
	}
	
}
