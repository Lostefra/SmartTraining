package accesso;

import java.io.BufferedReader;
import java.io.IOException;

import util.*;

public class LoginController {

	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return "A" = Amministratore, "C" = Cliente, "P" = PersoanlTrainer, "null" = username not found
	 * @throws IOException
	 */
	
	public String verificaCredenziali(String username, String password) throws IOException {
		
		if (username.equals("amministratore") && password.equals("password")) {
			return "A";
		}
		
		BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
		String line;
		boolean found = false;
		String result = null;
		String[] utente = new String[300];
		
		while((line = bf_utenti.readLine()) != null && !found) {
			
			utente = line.split("|");
			
			if(username.equals(utente[0]) && password.equals(utente[1])) {
				found = true;
				if(utente[2].equals("C"))
					result = "C";
				else 
					result = "P";
			}
		}
		
		return result;
	}
}
