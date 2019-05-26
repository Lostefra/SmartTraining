package richieste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.PersonalTrainer;
import model.Richiesta;
import model.RichiestaPianoNutrizionale;
import model.RichiestaSchedaAllenamento;
import util.Utilities;

public class RichiesteController {
	private DateTimeFormatter formatterDataOra = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	/**
	 * 
	 * @param p personal trainer del quale si vogliono vedere le richieste
	 * @return richieste
	 */
	public List<Richiesta> visualizzaRichieste(PersonalTrainer p) {
		BufferedReader bf_richieste = Utilities.apriFile("richieste.txt");
		String line;
		List<Richiesta> richieste = new ArrayList<Richiesta>();
		try {
			
			while((line = bf_richieste.readLine()) != null) {
				
				String[] richiesta = new String[100];
				richiesta = line.split("|");
				if(richiesta[2].equals(p.getId())) {	//idPersonalTrainer uguale in richiesta e campo passato
					if(richiesta[7].contentEquals("A")) {
						RichiestaSchedaAllenamento r = new RichiestaSchedaAllenamento(richiesta[0],
								Utilities.leggiCliente(richiesta[1]), Utilities.leggiPersonalTrainer(richiesta[2]),
										LocalDateTime.parse(richiesta[3], formatterDataOra),
										LocalDate.parse(richiesta[4], formatterData), Integer.parseInt(richiesta[5]), 
										richiesta[6], Integer.parseInt(richiesta[8]));
						
						richieste.add(r);
								
					}
					else {
						RichiestaPianoNutrizionale r = new RichiestaPianoNutrizionale(richiesta[0],
								Utilities.leggiCliente(richiesta[1]), Utilities.leggiPersonalTrainer(richiesta[2]),
										LocalDateTime.parse(richiesta[3], formatterDataOra),
										LocalDate.parse(richiesta[4], formatterData), Integer.parseInt(richiesta[5]), 
										richiesta[6], Integer.parseInt(richiesta[9]), Integer.parseInt(richiesta[10]), 
										richiesta[11]);
						
						richieste.add(r);
					}
				}		
			}
		} catch (IOException e) {
		
		}
		return richieste;

	}
	
	/**
	 * 
	 * @param c cliente
	 * @param p personal trainer
	 * @param dataInizio data inizio validita
	 * @param durataSettimane
	 * @param note
	 * @param numeroAllenamentiSettimanali
	 * @return true se inserimento riuscito, false altrimenti
	 */
	public boolean inserisciRichiestaSchedaAllenamento(Cliente c, PersonalTrainer p, LocalDate dataInizio, int durataSettimane, 
				String note, int numeroAllenamentiSettimanali) {
		boolean res = false, codiceEsiste = false; 
		String codice, line;
		BufferedReader bf_schede = Utilities.apriFile("richieste.txt");
		try {
			do {
				codice = Utilities.generaID("RICH", 3);
				while((line = bf_schede.readLine()) != null && !codiceEsiste) {
					String[] campi = new String[100];
					campi = line.split("|");
					if(campi[0].equals(codice))
						codiceEsiste = true;
				}
			}while(codiceEsiste == true);
			bf_schede.close();
			//CODICE OK
			PrintWriter pw_richieste = Utilities.apriFileAppend("richieste.txt");
			StringBuilder richiesta = new StringBuilder(codice+ "|"+ c.getId()+ "|"+p.getId()+ "|");
			richiesta.append(LocalDateTime.now().format(formatterDataOra)+ "|"+ dataInizio.format(formatterData)+ "|"+durataSettimane);
			if(note== null)
				richiesta.append("|null|A|" + numeroAllenamentiSettimanali +"|null|null|null\n");
			else
				richiesta.append("|"+ note+ "|A|" + numeroAllenamentiSettimanali+"|null|null|null\n");
			pw_richieste.write(richiesta.toString());
			pw_richieste.close();
			res = true;
		
		} catch(Exception e) {
			
		}
		return res;
	}
	
	/**
	 * 
	 * @param c cliente
	 * @param p personal trainer
	 * @param dataInizio data inizio validita
	 * @param durataSettimane
	 * @param note
	 * @param altezza
	 * @param peso
	 * @param elencoAllergeni
	 * @return true se inserimento riuscito, false altrimenti
	 */
	public boolean inserisciRichiestaPianoNutrizionale(Cliente c, PersonalTrainer p, LocalDate dataInizio, int durataSettimane, 
			String note, int altezza, int peso, String elencoAllergeni) {
		boolean res = false, codiceEsiste = false; 
		String codice, line;
		BufferedReader bf_schede = Utilities.apriFile("richieste.txt");
		try {
			do {
				codice = Utilities.generaID("RICH", 3);
				while((line = bf_schede.readLine()) != null && !codiceEsiste) {
					String[] campi = new String[100];
					campi = line.split("|");
					if(campi[0].equals(codice))
						codiceEsiste = true;
				}
			}while(codiceEsiste == true);
			bf_schede.close();
			//CODICE OK
			PrintWriter pw_richieste = Utilities.apriFileAppend("richieste.txt");
			StringBuilder richiesta = new StringBuilder(codice+ "|"+ c.getId()+ "|"+p.getId()+ "|");
			richiesta.append(LocalDateTime.now().format(formatterDataOra)+ "|"+ dataInizio.format(formatterData)+ "|"+durataSettimane);
			if(note == null)
				richiesta.append("|null|P|null|" + altezza +"|"+ peso+"|");
			else
				richiesta.append("|"+ note+ "|P|null|" + altezza +"|"+ peso+"|");
			if(elencoAllergeni == null)
				richiesta.append("null\n");
			else
				richiesta.append(elencoAllergeni + '\n');
			
			pw_richieste.write(richiesta.toString());
			pw_richieste.close();
			res = true;
		
		} catch(Exception e) {
			
		}
		return res;
	}
	
	/**
	 * 
	 * @param idRichiesta
	 * @return esito rimozione
	 */
	public boolean eliminaRichiesta(String id) {
		boolean res = false;
		
		BufferedReader bf_richieste = Utilities.apriFile("richieste.txt");
		String line;
		int index = 0;
		try {
			
			while((line = bf_richieste.readLine()) != null) {
				
				String[] richiesta = new String[100];
				richiesta = line.split("|");
				if(richiesta[0].equals(id)) {	//idRichiesta individuato
					bf_richieste.close();
					Utilities.riscriviTranneRiga("richieste.txt", index);
					res = true;
					break;
				}
				index++;
					
			}
			bf_richieste.close();
			
		} catch (IOException e) {
		
		}		
		return res;
	}
	
	/**
	 * 
	 * @param r richiesta
	 * @return esito rimozione
	 */
	public boolean eliminaRichiesta(Richiesta r) {
		boolean res = false;
		
		BufferedReader bf_richieste = Utilities.apriFile("richieste.txt");
		String line;
		int index = 0;
		try {
			
			while((line = bf_richieste.readLine()) != null) {
				
				String[] richiesta = new String[100];
				richiesta = line.split("|");
				if(richiesta[0].equals(r.getId())) {	//idRichiesta individuato
					bf_richieste.close();
					Utilities.riscriviTranneRiga("richieste.txt", index);
					res = true;
					break;
				}
				index++;
					
			}
			bf_richieste.close();
			
		} catch (IOException e) {
		
		}		
		return res;
	}
	
	/**
	 * 
	 * @return lista personal trainer
	 */
	public List<PersonalTrainer> getElencoPersonalTrainer(){
		List<PersonalTrainer> lista = new ArrayList<PersonalTrainer>();	
		BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
		String line;
		try {	
			while((line = bf_utenti.readLine()) != null) {		
				String[] richiesta = new String[100];
				richiesta = line.split("|");
				if(richiesta[2].equals("P")) {	//utente e' PersonalTrainer 
					PersonalTrainer p = Utilities.leggiPersonalTrainer(richiesta[3]);
					lista.add(p);
				}		
				
			}
			bf_utenti.close();
		} catch (IOException e) {
		
		}
		return lista;
	}

}

















