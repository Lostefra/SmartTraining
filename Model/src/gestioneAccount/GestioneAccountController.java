package gestioneAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import model.Cliente;
import model.PersonalTrainer;
import util.Utilities;

public class GestioneAccountController {

	/**
	 * 
	 * @param id cliente
	 * @return cliente
	 */
	public Cliente getDatiCliente(String id) {
		return Utilities.leggiCliente(id);
	}
	
	/**
	 * 
	 * @param id personal trainer
	 * @return personal trainer
	 */
	public PersonalTrainer getDatiPersonalTrainer(String id) {
		return Utilities.leggiPersonalTrainer(id);
	}
	
	/**
	 * 
	 * @param id
	 * @param email
	 * @param indirizzoResidenza
	 * @param numeroTelefono
	 * @return modifica avvenuta
	 */
	public boolean modificaDati(String id, String email, String indirizzoResidenza, String numeroTelefono) {
		boolean res = false, found = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
		String line;
		String[] utente = new String[100];
		int i = 0;
		try {
			while((line = bf_utenti.readLine()) != null && !found) {
				
				utente = line.split(Pattern.quote("|"));
				if(id.equals(utente[3])) 
					found = true;
				i++;	
			}
			if(!found)
				return res;
			//se sei qui hai trovato l'utente di cui aggiornare i dati,
			//in String[] utente ci sono i suoi vecchi dati
			bf_utenti.close();
			if(!Utilities.riscriviTranneRiga("utenti.txt", i))
				return res;
			//ora bisogna aggiungere nuovamente nel file utenti.txt l'utente con dati nuovi
			
			PrintWriter pw = Utilities.apriFileAppend("utenti.txt");
			
			sb.append(utente[0]+"|"+utente[1]+"|"+utente[2]+"|"+utente[3]+"|"+utente[4]+"|"+
					utente[5]+"|");
			if(email == null)
				sb.append(utente[6]+"|");
			else
				sb.append(email +"|");
			sb.append(utente[7]+"|"+utente[8]+"|"+utente[9]+"|");
			if(indirizzoResidenza == null)
				sb.append(utente[10]+"|");
			else
				sb.append(indirizzoResidenza +"|");
			if(numeroTelefono == null)
				sb.append(utente[11]+"|");
			else
				sb.append(numeroTelefono +"|");
			sb.append(utente[12]+"|"+utente[13]+"|"+utente[14]+"|"+utente[15]+"\n");
			
			pw.write(sb.toString());
			pw.close();
			res = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 
	 * @param id utente
	 * @return risultato eliminazione
	 */
	public boolean EliminaAccount(String id) {
		boolean found = false;
		BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
		String line;
		int i = 0;
		try {
			while((line = bf_utenti.readLine()) != null && !found) {
				String[] utente = new String[100];
				utente = line.split(Pattern.quote("|"));
				if(id.equals(utente[3])) 
					found = true;
				i++;	
			}
			if(!found)
				return false;
			//se sei qui hai trovato l'utente da eliminare,
			bf_utenti.close();
			//eliminazione
			return Utilities.eliminazione(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
}

























