package richieste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Attori.Cliente;
import Attori.PersonalTrainer;
import util.Utilities;

public class RichiesteController {
	private DateTimeFormatter formatterDataOra = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public List<Richiesta> visualizzaRichieste(PersonalTrainer p) {
		BufferedReader bf_richieste = Utilities.apriFile("richieste.txt");
		String line;
		List<Richiesta> richieste = new ArrayList<Richiesta>();
		try {
			
			//idRichiesta|idCliente|idPersonalTrainer|dataOraInserimento|dataInizio|durataSettimane|note|'A' / 'P'|numeroAllenamentiSettimanali|altezza|peso|elencoAllergeni
			while((line = bf_richieste.readLine()) != null) {
				
				String[] richiesta = new String[100];
				richiesta = line.split("|");
				if(richiesta[2].equals(p.getId())) {	//idPersonalTrainer uguale in richiesta e campo passato
					if(richiesta[7].contentEquals("A")) {
						//String id, Cliente idCliente, PersonalTrainer idPersonalTrainer, LocalDateTime dataOra,
						//LocalDate dateInizio, int durataSettimane, String note, int numeroAllenamentiSettimanali
						RichiestaSchedaAllenamento r = new RichiestaSchedaAllenamento(richiesta[0],
								Utilities.leggiCliente(richiesta[1]), Utilities.leggiPersonalTrainer(richiesta[2]),
										LocalDateTime.parse(richiesta[3], formatterDataOra),
										LocalDate.parse(richiesta[4], formatterData), Integer.parseInt(richiesta[5]), 
										richiesta[6], Integer.parseInt(richiesta[8]));
						
						richieste.add(r);
								
					}
					else {
						//String id, Cliente idCliente, PersonalTrainer idPersonalTrainer, LocalDateTime dataOra,
						//LocalDate dateInizio, int durataSettimane, String note, int altezza, int peso, String elencoAllergeni
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
			//idRichiesta|idCliente|idPersonalTrainer|dataOraInserimento|dataInizio|durataSettimane|note|'A' / 'P'|numeroAllenamentiSettimanali|altezza|peso|elencoAllergeni
			PrintWriter pw_richieste = Utilities.apriFileAppend("richieste.txt");
			StringBuilder richiesta = new StringBuilder(codice+ "|"+ c.getId()+ "|"+p.getId()+ "|");
			richiesta.append(LocalDateTime.now().format(formatterDataOra)+ "|"+ dataInizio.format(formatterData)+ "|"+durataSettimane);
			if(note== null)
				richiesta.append("|null|A|" + numeroAllenamentiSettimanali +"|null|null|null");
			else
				richiesta.append("|"+ note+ "|A|" + numeroAllenamentiSettimanali+"|null|null|null");
			pw_richieste.write(richiesta.toString());
			pw_richieste.close();
		
		} catch(Exception e) {
			
		}
		return res;
	}
	
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
		//idRichiesta|idCliente|idPersonalTrainer|dataOraInserimento|dataInizio|durataSettimane|note|'A' / 'P'|numeroAllenamentiSettimanali|altezza|peso|elencoAllergeni
		PrintWriter pw_richieste = Utilities.apriFileAppend("richieste.txt");
		StringBuilder richiesta = new StringBuilder(codice+ "|"+ c.getId()+ "|"+p.getId()+ "|");
		richiesta.append(LocalDateTime.now().format(formatterDataOra)+ "|"+ dataInizio.format(formatterData)+ "|"+durataSettimane);
		if(note == null)
			richiesta.append("|null|P|null|" + altezza +"|"+ peso+"|");
		else
			richiesta.append("|"+ note+ "|P|null|" + altezza +"|"+ peso+"|");
		if(elencoAllergeni == null)
			richiesta.append("null");
		else
			richiesta.append(elencoAllergeni);
		
		pw_richieste.write(richiesta.toString());
		pw_richieste.close();
	
	} catch(Exception e) {
		
	}
	return res;
}
}
















