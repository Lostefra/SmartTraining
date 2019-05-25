package log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Entry;
import model.EntryMessaggio;
import model.EntryOperazione;
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
				campi = line.split("|");
				EntryOperazione o;
				EntryMessaggio m;

				//se entry messaggio
				if(campi[2].equals("null")) {
					m = new EntryMessaggio(LocalDateTime.parse(campi[0], formatterDataOra), campi[1]);
					entries.add(m);
				}
				//se entry operazione
				else {
					o = new EntryOperazione(LocalDateTime.parse(campi[0], formatterDataOra), campi[1], campi[2]);
					entries.add(o);
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
			boolean idOK = false;
			if(e instanceof EntryOperazione) {
				EntryOperazione eo = (EntryOperazione) e;
				idOK = idUtente.equals(eo.getIdUtente());
			}
			if(	(dataInizio == null || !e.getDataOra().isBefore(dataInizio)) &&
					(dataFine == null || !e.getDataOra().isAfter(dataFine)) && 
					(idUtente == null || idOK)) {
				res.add(e);
			}			
		}
		return res;
	}
	
	public boolean scriviMessaggio(EntryMessaggio m) {
		PrintWriter pw = Utilities.apriFileAppend("log.txt");
		pw.write(m.getDataOra() +"|"+m.getMessaggio()+'\n');
		pw.close();
		return true;
	}
	
	public boolean scriviMessaggio(LocalDateTime dataOra, String messaggio) {
		PrintWriter pw = Utilities.apriFileAppend("log.txt");
		pw.write(dataOra.format(formatterDataOra) +"|"+messaggio +'\n');
		pw.close();
		return true;
	}
	
	public boolean scriviOperazione(EntryOperazione o) {
		PrintWriter pw = Utilities.apriFileAppend("log.txt");
		pw.write(o.getDataOra() +"|"+o.getAttivita()+"|"+o.getIdUtente()+'\n');
		pw.close();
		return true;
	}
	
	public boolean scriviOperazione(LocalDateTime dataOra, String attivita, String idUtente) {
		PrintWriter pw = Utilities.apriFileAppend("log.txt");
		pw.write(dataOra.format(formatterDataOra) +"|"+attivita+"|"+idUtente +'\n');
		pw.close();
		return true;
	}
	
}




















