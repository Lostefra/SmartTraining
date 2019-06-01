package accesso;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import model.Cliente;
import model.OrarioIngressoUscita;
import model.UserType;
import util.Utilities;

public class LoginController {

	private int aumentoPunti;
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return UserType
	 * @throws IOException
	 */
	public UserType verificaCredenziali(String username, String password) throws IOException {
		
		if (username.equals("amministratore") && password.equals("password")) {
			return UserType.Amministratore;
		}
		
		BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
		String line;
		boolean found = false;
		UserType result = null;
		String[] utente = new String[300];
		
		while((line = bf_utenti.readLine()) != null && !found) {
			
			utente = line.split(Pattern.quote("|"));
			
			if(username.equals(utente[0]) && password.equals(utente[1])) {
				found = true;
				if(utente[2].equals("C")) {
					result = UserType.Cliente;
					this.aumentoPunti = 0;
					this.calcolaAumentoPunti(getOrariIngressoUscita(Utilities.getCliente(username)));
				}
				else 
					result = UserType.PersonalTrainer;
			}
		}
		bf_utenti.close();
		return result;
	}
	
	/**
	 * 
	 * Restituisce la lista degli orari di ingresso e uscita dalla palestra
	 * dall'ultimo aggiornamento dei punti della tessera socio del cliente
	 * 
	 * @param cliente
	 * @return List OrarioIngressoUscita
	 * @throws IOException
	 */
	private List<OrarioIngressoUscita> getOrariIngressoUscita(Cliente cliente) throws IOException {
		String userID = cliente.getId();
		BufferedReader reader = Utilities.apriFile("orariIngressoUscita.txt");
		
		String currentLine;
		String[] user = new String[200];
		
		List<OrarioIngressoUscita> result = new ArrayList<>();
		
		while ((currentLine = reader.readLine()) != null) {
			user = currentLine.split(Pattern.quote("|"));
			
			if (user[0].equals(userID) && 
					cliente.getTes().getUltimoAggiornamento().isAfter(LocalDateTime.parse(user[1], Utilities.formatterDataOra)))
				if (!user[2].equals("null"))
					result.add(new OrarioIngressoUscita(LocalDateTime.parse(user[1], Utilities.formatterDataOra), 
							LocalDateTime.parse(user[2], Utilities.formatterDataOra)));
		}
		
		reader.close();
		
		return result;
	}
//prova
	private void calcolaAumentoPunti(List<OrarioIngressoUscita> orari) {
		aumentoPunti = 0;
		long min;
		
		for (OrarioIngressoUscita orario : orari) {
			min = Duration.between(orario.getIngresso(), orario.getUscita()).toMinutes();
			aumentoPunti += (min % 30)*5; 
		}
	}
	
	public int getAumentoPunti() {
		return this.aumentoPunti;
	}
}
