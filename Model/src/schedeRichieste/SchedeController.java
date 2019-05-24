package schedeRichieste;

import java.io.BufferedReader;
import java.io.IOException;
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
	
	DateTimeFormatter formatterDataOra = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("mm:ss");
	
	
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
							//se sessione gi� presente, rimuovo da lista la salvo nella variabile locale sessione
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
							//se pasto gi� presente, rimuovo da lista la salvo nella variabile locale pasto
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
				
				if(campi[1].equals(c.getId())) { //se scheda � del cliente in questione
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
								//se sessione gi� presente, rimuovo da lista la salvo nella variabile locale sessione
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
								//se pasto gi� presente, rimuovo da lista la salvo nella variabile locale pasto
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
				
			}
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
				
				//facendo cos� le schede che terminano la validita' oggi compaiono tra le attuali
				boolean dataNONok = dataFineValidita.isBefore(LocalDate.now());
				
				PersonalTrainer p = Utilities.leggiPersonalTrainer(campi[2]);
				
				if(campi[1].equals(c.getId()) && !dataNONok) { //se scheda � del cliente in questione ed e' attuale
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
								//se sessione gi� presente, rimuovo da lista la salvo nella variabile locale sessione
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
								//se pasto gi� presente, rimuovo da lista la salvo nella variabile locale pasto
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
				
			}
		} catch (IOException e) {
		
		}
		return schede;
	}
	
	//i parametri in input messi a null => non si filtra per quel paramentro
	public List<Scheda> applicaFiltro(List<Scheda> schede, String nomeCliente, String cognomeCliente, String nomePersonalTrainer,
			String cognomePersonalTrainer, LocalDate dataInizio, LocalDate dataFine, String tipologia){
		List<Scheda> res = new ArrayList<Scheda>();
		for(Scheda s : schede) {
			//TODO
		}
		return res;
	}
}

















