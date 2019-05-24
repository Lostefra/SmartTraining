package schede;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Attori.Cliente;
import Attori.PersonalTrainer;
import util.Utilities;

public class SchedeController {
	
	private DateTimeFormatter formatterDataOra = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("mm:ss");
	
	
	public List<Scheda> visualizzaStoricoPT(){
		List<Scheda> schede = new ArrayList<Scheda>();
		BufferedReader bf_schede = Utilities.apriFile("schede.txt");
		String line;
		try {
			//idScheda|idCliente|idPersonalTrainer|dataOraInserimento|dataInizio|durataSettimane|note|'A' / 'P'
			while((line = bf_schede.readLine()) != null) {
				String[] campi = new String[100];
				campi = line.split("|");
				SchedaAllenamento sa;
				PianoNutrizionale pn;
				
				BufferedReader bf_inner = Utilities.apriFile("eserciziAlimenti.txt");
				String temp;
				
				Cliente c = Utilities.leggiCliente(campi[1]);
				PersonalTrainer p = Utilities.leggiPersonalTrainer(campi[2]);
				
				//se scheda allenamento
				if(campi[7].equals("A")) {
					Sessione sessione;
					sa = new SchedaAllenamento(campi[0], c, p, LocalDateTime.parse(campi[3], formatterDataOra), 
							LocalDate.parse(campi[4],  formatterData), Integer.parseInt(campi[5]), campi[6]);
					//idScheda|giorno|ora|nome|peso|numeroSerie|numeroRipetizioni|tempoRecupero
					while((temp = bf_inner.readLine()) != null) {
						String[] esercizio = new String[100];
						esercizio = temp.split("|");					
						if(sa.getId().equals(esercizio[0])) { //se esercizio associato alla scheda in questione
							sessione = new Sessione(DayOfWeek.of(Integer.parseInt(esercizio[1])));
							//se sessione già presente, rimuovo da lista la salvo nella variabile locale sessione
							if(sa.getSessioni().contains(sessione)) { 
								sessione = sa.getRemoveSessione(sessione);
							}
							sessione.getEsercizi().add(new Esercizio(campi[3], Integer.parseInt(campi[5]), 
									Integer.parseInt(campi[6]), LocalTime.parse(campi[7], formatterOra)));
							sa.getSessioni().add(sessione);
						}
				
					}
					schede.add(sa);
				}
				//se piano nutrizionale
				else {
					Pasto pasto;
					pn = new PianoNutrizionale(campi[0], c, p, LocalDateTime.parse(campi[3], formatterDataOra), 
							LocalDate.parse(campi[4],  formatterData), Integer.parseInt(campi[5]), campi[6]);
					//idScheda|giorno|ora|nome|peso|numeroSerie|numeroRipetizioni|tempoRecupero
					while((temp = bf_inner.readLine()) != null) {
						String[] alimento = new String[100];
						alimento = temp.split("|");					
						if(pn.getId().equals(alimento[0])) { //se alimento associato alla scheda in questione
							pasto = new Pasto(DayOfWeek.of(Integer.parseInt(alimento[1])), LocalTime.parse(alimento[2], formatterOra));
							//se pasto già presente, rimuovo da lista la salvo nella variabile locale pasto
							if(pn.getPasti().contains(pasto)) { 
								pasto = pn.getRemovePasto(pasto);
							}
							pasto.getAlimenti().add(new Alimento(campi[3], Integer.parseInt(campi[4])));
							pn.getPasti().add(pasto);
						}
				
					}
					schede.add(pn);
				}
				
				bf_inner.close();
			}
			bf_schede.close();
		} catch (IOException e) {
		
		}
		return schede;
	}
	
	public List<Scheda> visualizzaStoricoCliente(Cliente c){
		List<Scheda> schede = new ArrayList<Scheda>();
		BufferedReader bf_schede = Utilities.apriFile("schede.txt");
		String line;
		try {
			//idScheda|idCliente|idPersonalTrainer|dataOraInserimento|dataInizio|durataSettimane|note|'A' / 'P'
			while((line = bf_schede.readLine()) != null) {
				String[] campi = new String[100];
				campi = line.split("|");
				SchedaAllenamento sa;
				PianoNutrizionale pn;
				
				BufferedReader bf_inner = Utilities.apriFile("eserciziAlimenti.txt");
				String temp;
				
				PersonalTrainer p = Utilities.leggiPersonalTrainer(campi[2]);
				
				if(campi[1].equals(c.getId())) { //se scheda è del cliente in questione
					//se scheda allenamento
					if(campi[7].equals("A")) {
						Sessione sessione;
						sa = new SchedaAllenamento(campi[0], c, p, LocalDateTime.parse(campi[3], formatterDataOra), 
								LocalDate.parse(campi[4],  formatterData), Integer.parseInt(campi[5]), campi[6]);
						//idScheda|giorno|ora|nome|peso|numeroSerie|numeroRipetizioni|tempoRecupero
						while((temp = bf_inner.readLine()) != null) {
							String[] esercizio = new String[100];
							esercizio = temp.split("|");					
							if(sa.getId().equals(esercizio[0])) { //se esercizio associato alla scheda in questione
								sessione = new Sessione(DayOfWeek.of(Integer.parseInt(esercizio[1])));
								//se sessione già presente, rimuovo da lista la salvo nella variabile locale sessione
								if(sa.getSessioni().contains(sessione)) { 
									sessione = sa.getRemoveSessione(sessione);
								}
								sessione.getEsercizi().add(new Esercizio(campi[3], Integer.parseInt(campi[5]), 
										Integer.parseInt(campi[6]), LocalTime.parse(campi[7], formatterOra)));
								sa.getSessioni().add(sessione);
							}
					
						}
						schede.add(sa);
					}
					//se piano nutrizionale
					else {
						Pasto pasto;
						pn = new PianoNutrizionale(campi[0], c, p, LocalDateTime.parse(campi[3], formatterDataOra), 
								LocalDate.parse(campi[4],  formatterData), Integer.parseInt(campi[5]), campi[6]);
						//idScheda|giorno|ora|nome|peso|numeroSerie|numeroRipetizioni|tempoRecupero
						while((temp = bf_inner.readLine()) != null) {
							String[] alimento = new String[100];
							alimento = temp.split("|");					
							if(pn.getId().equals(alimento[0])) { //se alimento associato alla scheda in questione
								pasto = new Pasto(DayOfWeek.of(Integer.parseInt(alimento[1])), LocalTime.parse(alimento[2], formatterOra));
								//se pasto già presente, rimuovo da lista la salvo nella variabile locale pasto
								if(pn.getPasti().contains(pasto)) { 
									pasto = pn.getRemovePasto(pasto);
								}
								pasto.getAlimenti().add(new Alimento(campi[3], Integer.parseInt(campi[4])));
								pn.getPasti().add(pasto);
							}
					
						}
						schede.add(pn);
					}
				}
				bf_inner.close();
			}
			bf_schede.close();
		} catch (IOException e) {
		
		}
		return schede;
	}
	
	public List<Scheda> visualizzaAttuali(Cliente c){
		List<Scheda> schede = new ArrayList<Scheda>();
		BufferedReader bf_schede = Utilities.apriFile("schede.txt");
		String line;
		try {
			//idScheda|idCliente|idPersonalTrainer|dataOraInserimento|dataInizio|durataSettimane|note|'A' / 'P'
			while((line = bf_schede.readLine()) != null) {
				String[] campi = new String[100];
				campi = line.split("|");
				SchedaAllenamento sa;
				PianoNutrizionale pn;
				
				BufferedReader bf_inner = Utilities.apriFile("eserciziAlimenti.txt");
				String temp;
				
				LocalDate dataFineValidita = LocalDate.parse(campi[5], formatterData).plusWeeks(Integer.parseInt(campi[6]));
				
				//facendo così le schede che terminano la validita' oggi compaiono tra le attuali
				boolean dataNONok = dataFineValidita.isBefore(LocalDate.now());
				
				PersonalTrainer p = Utilities.leggiPersonalTrainer(campi[2]);
				
				if(campi[1].equals(c.getId()) && !dataNONok) { //se scheda è del cliente in questione ed e' attuale
					//se scheda allenamento
					if(campi[7].equals("A")) {
						Sessione sessione;
						sa = new SchedaAllenamento(campi[0], c, p, LocalDateTime.parse(campi[3], formatterDataOra), 
								LocalDate.parse(campi[4],  formatterData), Integer.parseInt(campi[5]), campi[6]);
						//idScheda|giorno|ora|nome|peso|numeroSerie|numeroRipetizioni|tempoRecupero
						while((temp = bf_inner.readLine()) != null) {
							String[] esercizio = new String[100];
							esercizio = temp.split("|");					
							if(sa.getId().equals(esercizio[0])) { //se esercizio associato alla scheda in questione
								sessione = new Sessione(DayOfWeek.of(Integer.parseInt(esercizio[1])));
								//se sessione già presente, rimuovo da lista la salvo nella variabile locale sessione
								if(sa.getSessioni().contains(sessione)) { 
									sessione = sa.getRemoveSessione(sessione);
								}
								sessione.getEsercizi().add(new Esercizio(campi[3], Integer.parseInt(campi[5]), 
										Integer.parseInt(campi[6]), LocalTime.parse(campi[7], formatterOra)));
								sa.getSessioni().add(sessione);
							}
					
						}
						schede.add(sa);
					}
					//se piano nutrizionale
					else {
						Pasto pasto;
						pn = new PianoNutrizionale(campi[0], c, p, LocalDateTime.parse(campi[3], formatterDataOra), 
								LocalDate.parse(campi[4],  formatterData), Integer.parseInt(campi[5]), campi[6]);
						//idScheda|giorno|ora|nome|peso|numeroSerie|numeroRipetizioni|tempoRecupero
						while((temp = bf_inner.readLine()) != null) {
							String[] alimento = new String[100];
							alimento = temp.split("|");					
							if(pn.getId().equals(alimento[0])) { //se alimento associato alla scheda in questione
								pasto = new Pasto(DayOfWeek.of(Integer.parseInt(alimento[1])), LocalTime.parse(alimento[2], formatterOra));
								//se pasto già presente, rimuovo da lista la salvo nella variabile locale pasto
								if(pn.getPasti().contains(pasto)) { 
									pasto = pn.getRemovePasto(pasto);
								}
								pasto.getAlimenti().add(new Alimento(campi[3], Integer.parseInt(campi[4])));
								pn.getPasti().add(pasto);
							}
					
						}
						schede.add(pn);
					}
				}
				bf_inner.close();
			}
			bf_schede.close();
		} catch (IOException e) {
		
		}
		return schede;
	}
	
	//i parametri in input messi a null => non si filtra per quel paramentro, TIPOLOGIA: 'A' / 'P'
	public List<Scheda> applicaFiltro(List<Scheda> schede, String nomeCliente, String cognomeCliente, String nomePersonalTrainer,
			String cognomePersonalTrainer, LocalDate dataInizio, LocalDate dataFine, String tipologia){
		List<Scheda> res = new ArrayList<Scheda>();
		for(Scheda s : schede) {
			boolean tipoOK = false;
			if(tipologia != null) {
				if(tipologia.equals("A")) {
					tipoOK = s instanceof SchedaAllenamento;
				}
				else if(tipologia.equals("P")){
					tipoOK = s instanceof PianoNutrizionale;
				}
			}
			if((nomeCliente == null || s.getCliente().getNome().contains(nomeCliente)) &&
					(cognomeCliente == null || s.getCliente().getCognome().contains(cognomeCliente)) &&
					(nomePersonalTrainer == null || s.getPersonalTrainer().getNome().contains(nomePersonalTrainer)) &&
					(cognomePersonalTrainer == null || s.getPersonalTrainer().getCognome().contains(cognomePersonalTrainer)) &&
					(dataInizio == null || !s.getDateInizio().plusWeeks(s.getDurataSettimane()).isBefore(dataInizio)) &&
					(dataFine == null || !s.getDateInizio().isAfter(dataFine)) && 
					(tipologia == null || tipoOK)) {
				schede.add(s);
			}			
		}
		return res;
	}
	
	//idScheda|idCliente|idPersonalTrainer|dataOraInserimento|dataInizio|durataSettimane|note|'A' / 'P'
	//idScheda|giorno|ora|nome|peso|numeroSerie|numeroRipetizioni|tempoRecupero
	public boolean inserisciSchedaAllenamento(Cliente c, PersonalTrainer p, LocalDate dataInizio, int durataSettimane, 
			String note, List<EsercizioAlimento> esercizi) {
		boolean res = false, codiceEsiste = false; 
		String codice, line;
		BufferedReader bf_schede = Utilities.apriFile("schede.txt");
		try {
			do {
				codice = Utilities.generaID("SCHEDA", 3);
				while((line = bf_schede.readLine()) != null && !codiceEsiste) {
					String[] campi = new String[100];
					campi = line.split("|");
					if(campi[0].equals(codice))
						codiceEsiste = true;
				}
			}while(codiceEsiste == true);
			//codice buono
			bf_schede.close();
			PrintWriter pw_schede = Utilities.apriFileAppend("schede.txt");
			String scheda; 
			if(note != null)
				scheda = codice+"|"+c.getId()+"|"+p.getId()+"|"+ LocalDateTime.now().format(formatterDataOra) + "|"+
						dataInizio.format(formatterData) +"|"+ durataSettimane +"|"+ note + "|P";
			else
				scheda = codice+"|"+c.getId()+"|"+p.getId()+"|"+ LocalDateTime.now().format(formatterDataOra) + "|"+
						dataInizio.format(formatterData) +"|"+ durataSettimane+"|null|P";
		
			pw_schede.write(scheda);
			pw_schede.close();
			PrintWriter pw_esercizi = Utilities.apriFileAppend("eserciziAlimenti.txt");
			for(EsercizioAlimento e : esercizi) {
				//idScheda|giorno|ora|nome|peso|numeroSerie|numeroRipetizioni|tempoRecupero
				String esercizio = codice+"|"+ e.getGiorno().getValue() +"|null|"+ e.getEsercizio().getNome()+"|null|"+
						e.getEsercizio().getNumeroSerie()+"|"+e.getEsercizio().getNumeroRipetizioni()+"|"+
						e.getEsercizio().getTempoRecupero().format(formatterOra);
				pw_esercizi.write(esercizio);
			}
			pw_esercizi.close();
			
		} catch(Exception e) {
			
		}
		return res;
	}
	
	//idScheda|idCliente|idPersonalTrainer|dataOraInserimento|dataInizio|durataSettimane|note|'A' / 'P'
		//idScheda|giorno|ora|nome|peso|numeroSerie|numeroRipetizioni|tempoRecupero
		public boolean inserisciPianoNutrizionale(Cliente c, PersonalTrainer p, LocalDate dataInizio, int durataSettimane, 
				String note, List<EsercizioAlimento> alimenti) {
			boolean res = false, codiceEsiste = false; 
			String codice, line;
			BufferedReader bf_schede = Utilities.apriFile("schede.txt");
			try {
				do {
					codice = Utilities.generaID("SCHEDA", 3);
					while((line = bf_schede.readLine()) != null && !codiceEsiste) {
						String[] campi = new String[100];
						campi = line.split("|");
						if(campi[0].equals(codice))
							codiceEsiste = true;
					}
				}while(codiceEsiste == true);
				//codice buono
				bf_schede.close();
				PrintWriter pw_schede = Utilities.apriFileAppend("schede.txt");
				String scheda;
				if(note != null)
					scheda = codice+"|"+c.getId()+"|"+p.getId()+"|"+ LocalDateTime.now().format(formatterDataOra) + "|"+
							dataInizio.format(formatterData) +"|"+ durataSettimane +"|"+ note + "|A";
				else
					scheda = codice+"|"+c.getId()+"|"+p.getId()+"|"+ LocalDateTime.now().format(formatterDataOra) + "|"+
							dataInizio.format(formatterData) +"|"+ durataSettimane+"|null|A";
			
				pw_schede.write(scheda);
				pw_schede.close();
				PrintWriter pw_alimenti = Utilities.apriFileAppend("eserciziAlimenti.txt");
				for(EsercizioAlimento a : alimenti) {
					//idScheda|giorno|ora|nome|peso|numeroSerie|numeroRipetizioni|tempoRecupero
					String esercizio = codice+"|"+ a.getGiorno().getValue() +"|"+a.getOra().format(formatterOra)+"|"+ 
							a.getAlimento().getNome()+"|"+ a.getAlimento().getPeso()+"|null|null|null";
					pw_alimenti.write(esercizio);
				}
				pw_alimenti.close();
				
			} catch(Exception e) {
				
			}
			return res;
		}
}

















