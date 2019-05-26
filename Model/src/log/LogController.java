package log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import model.Entry;
import util.Utilities;

public class LogController {
	
	private DateTimeFormatter formatterDataOra = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	/**
	 * 
	 * @return lista di entry componenti il log
	 */
	public List<Entry> getLog(){
		List<Entry> entries = new ArrayList<Entry>();
		BufferedReader bf_log = Utilities.apriFile("log.txt");
		String line;
		try {
			while((line = bf_log.readLine()) != null) {
				String[] campi = new String[10];
				campi = line.split(Pattern.quote("|"));
				Entry entry;

				//se entry messaggio
				if(campi[2].equals("null")) {
					entry = new Entry(LocalDateTime.parse(campi[0], formatterDataOra), "",campi[1]);
					entries.add(entry);
				}
				//se entry operazione
				else {
					entry = new Entry(LocalDateTime.parse(campi[0], formatterDataOra), campi[2], campi[1]);
					entries.add(entry);
				}			
			}
			bf_log.close();
		} catch (IOException e) {
		
		}		
		return entries;
	}
	
	/**
	 * //i parametri in input messi a null => non si filtra per quel paramentro
	 * 
	 * @param entries
	 * @param inizio
	 * @param fine
	 * @param idUtente
	 * @return lista filtrata di entry
	 */
	public List<Entry> applicaFiltro(List<Entry> entries, LocalDateTime dataInizio, LocalDateTime dataFine, String idUtente){
		List<Entry> res = new ArrayList<Entry>();
		for(Entry e : entries) {
			if(	(dataInizio == null || !e.getDataOra().isBefore(dataInizio)) &&
					(dataFine == null || !e.getDataOra().isAfter(dataFine)) && 
					(idUtente.equals("") || e.getIdUtente().contains(idUtente))) {
				res.add(e);
			}			
		}
		return res;
	}
	
	/**
	 * 
	 * @param m entryMessaggio
	 * @return scrittura avvenuta
	 */
	public boolean scriviMessaggio(Entry m) {
		PrintWriter pw = Utilities.apriFileAppend("log.txt");
		pw.write(m.getDataOra() +"|"+m.getDescrizione()+"|null\n");
		pw.close();
		return true;
	}
	
	/**
	 * 
	 * @param dataOra
	 * @param messaggio
	 * 
	 * @return scrittura avvenuta
	 */
	public boolean scriviMessaggio(LocalDateTime dataOra, String messaggio) {
		PrintWriter pw = Utilities.apriFileAppend("log.txt");
		pw.write(dataOra.format(formatterDataOra) +"|"+messaggio +"|null\n");
		pw.close();
		return true;
	}
	
	/**
	 * 
	 * @param o entryOperazione
	 * @return scrittura avvenuta
	 */
	public boolean scriviOperazione(Entry o) {
		PrintWriter pw = Utilities.apriFileAppend("log.txt");
		pw.write(o.getDataOra() +"|"+o.getDescrizione()+"|"+o.getIdUtente()+'\n');
		pw.close();
		return true;
	}
	
	/**
	 * 
	 * @param dataOra
	 * @param attivita
	 * @param idUtente
	 * @return scrittura avvenuta
	 */
	public boolean scriviOperazione(LocalDateTime dataOra, String attivita, String idUtente) {
		PrintWriter pw = Utilities.apriFileAppend("log.txt");
		pw.write(dataOra.format(formatterDataOra) +"|"+attivita+"|"+idUtente +'\n');
		pw.close();
		return true;
	}
	
}




















