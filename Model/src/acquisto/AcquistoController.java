package acquisto;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.regex.Pattern;

import log.LogController;
import model.Acquisto;
import model.Cliente;
import model.Prodotto;
import model.ProdottoSelezionato;
import model.Sconto;
import util.Utilities;

public class AcquistoController {
	
	private Cliente cli;
	private List<ProdottoSelezionato> prodottiSelezionati;
	private LogController lc = new LogController();
	
	public AcquistoController(Cliente c) {
		this.cli = c;
		this.prodottiSelezionati = new ArrayList<ProdottoSelezionato>();
	}
	
	/** 
	 * Legge dal file tutti i prodotti presenti, serve per riempire la tabella che permette la selezione
	 * @return List<Prodotto> con i prodotti letti
	 */
	public List<Prodotto> getProdotti() {
		List<Prodotto> prodotti = new ArrayList<Prodotto>();
		BufferedReader bf_prodotti = Utilities.apriFile("prodotti.txt");
		String line;
		
		try {
			//codice|nome|prezzo|descrizione|quantita
			while((line = bf_prodotti.readLine()) != null) {
				String[] campi = new String[100];
				campi = line.split("\\|");
				
				int codice = Integer.parseInt(campi[0]);
				String nome = campi[1];
				float prezzo = Float.parseFloat(campi[2]);
				String descrizione = campi[3];
				int quantita = Integer.parseInt(campi[4]);
				
				Prodotto p = new Prodotto(codice, nome, prezzo, descrizione, quantita);
				prodotti.add(p);
			}
			
			bf_prodotti.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lc.scriviMessaggio(LocalDateTime.now(),"Download informazioni sui prodotti acquistabili completato con successo");
		return prodotti;
	}
	
	/**
	 * Legge dal file tutti gli sconti presenti, serve per riempire la tabella che permette la selezione dello sconto
	 * @return tuttiGliSconti
	 */
	public List<Sconto> getScontiDisponibili() {
		List<Sconto> scontiDisp = new ArrayList<Sconto>();
		BufferedReader bf_sconti = Utilities.apriFile("sconti.txt");
		
		String line;
		try {
			//valore in euro|punti richiesti|spesa minima
			while((line = bf_sconti.readLine()) != null) {
				String[] campi = new String[100];
				campi = line.split(Pattern.quote("|"));
				
				float prezzo = Float.parseFloat(campi[0]);
				int puntiRichiesti = Integer.parseInt(campi[1]);
				float spesaMinima = Float.parseFloat(campi[2]);
				
				Sconto s = new Sconto(prezzo, puntiRichiesti, spesaMinima);
				scontiDisp.add(s);
			}
			
			bf_sconti.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lc.scriviMessaggio(LocalDateTime.now(),"Download informazioni sugli sconti disponibili compleato con successo");
		return scontiDisp;
	}
	
	public List<ProdottoSelezionato> getSelezionati() {
		return prodottiSelezionati;
	}
	
	/**
	 * I parametri in input messi a null (Optional)  => non si filtra per quel paramentro 
	 *
	 * @param prodotti
	 * @param codice
	 * @param nomeProdotto
	 * @param prezzoMin
	 * @param prezzoMax
	 *
	 * @return prodottiFiltrati
	 */
	public List<Prodotto> applicaFiltro(List<Prodotto> prodotti, OptionalInt codice, String nomeProdotto, 
			Optional<Float> prezzoMin, Optional<Float> prezzoMax) {
			List<Prodotto> prodottiFiltrati = new ArrayList<Prodotto>();
			for (Prodotto prodotto : prodotti)
				if((!codice.isPresent() || codice.getAsInt() ==-1 || prodotto.getCodice() == codice.getAsInt()) &&
					(nomeProdotto == null|| nomeProdotto.contentEquals("")|| prodotto.getNome().toLowerCase().contains(nomeProdotto.toLowerCase())) &&
					(!prezzoMin.isPresent() || prodotto.getPrezzo() >= prezzoMin.get()) &&
					(!prezzoMax.isPresent() || prodotto.getPrezzo() <= prezzoMax.get()))
						prodottiFiltrati.add(prodotto);	
			lc.scriviMessaggio(LocalDateTime.now(),"Filtro sui prodotti applicato con successo");
			return prodottiFiltrati;

	}
	
	/**
	 * Si seleziona un prodotto dalla tabella iniziale e si aggiunge al carrello, diminuendo la sua disponibilità
	 * 
	 * @param prodotto
	 * @param numero articoli selezionati
	 * 
	 * @return prodottiSelezionati
	 */
	public List<ProdottoSelezionato> aggiungiProdotto(ProdottoSelezionato psel, int qty) {
		diminuisciDisponibilita(psel, qty);
		prodottiSelezionati.add(psel);
		return prodottiSelezionati;
	}
	
	/**
	 * Aggiorna il file contenente i prodotti, diminuendo la disponibilità di quello scelto
	 * @param prodotto
	 * @param numero articoli selezionati
	 *  
	 * @return disponibilitàDiQuelProdotto
	 */
	private void diminuisciDisponibilita(ProdottoSelezionato psel, int qty) {
		try {
	        // input the (modified) file content to the StringBuffer "input"
			BufferedReader bf_prodotti = Utilities.apriFile("prodotti.txt");
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;
	        int disp = -1;

	        while ((line = bf_prodotti.readLine()) != null) {
	            String[] res = new String[100];
	            res = line.split("\\|");
	        	
	        	if(Integer.parseInt(res[0]) == psel.getCodice()) { //Se ho trovato il codice giusto
	        		disp = Integer.parseInt(res[4]) - qty; //Diminuisco subito la disponibilità
	        		line = res[0] + "|" + res[1] + "|" + res[2] + "|" + 
	        				res[3] + "|" + disp; // Riscrivo la riga già con la disponibilità aggiornata
	        		inputBuffer.append(line);
	        	    inputBuffer.append('\n');
	        	}
	        	
	        	else { //Non era il codice giusto, salvo la riga appena letta nel buffer e provo con quella dopo
	    	        inputBuffer.append(line);
	    	        inputBuffer.append('\n');
	        	}
	        	
	        	//Non è trattato il caso in cui non si trovi il codice giusto perché la scelta è forzata dalla view
	        	//Non essendoci parametri che l'utente può inserire, sono per forza giusti
	        }
	        bf_prodotti.close();

	        // Scrivo tutto il buffer in overwrite sullo stesso file
	        FileOutputStream fileOut = new FileOutputStream("C:\\SmartTrainingFiles\\prodotti.txt");
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();
	        lc.scriviMessaggio(LocalDateTime.now(),"Disponibilita' del prodotto con codice "+ psel.getCodice()+ " decrementata correttamente");
	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Si toglie un prodotto dal carrello, viene aumentata la sua disponibilità.
	 * @param prodotto
	 * 
	 * @return List<ProdottoSelezionato> aggiornata
	 */
	public List<ProdottoSelezionato> eliminaProdotto(ProdottoSelezionato p, int qty) {
		
		aumentaDisponibilita(p, qty);
		prodottiSelezionati.remove(p);
		return prodottiSelezionati;
	}

	/**
	 * Aggiorna il file contenente i prodotti, aumentando la disponibilità di quello eliminato dalla scelta
	 * @param prodotto
	 */
	public void aumentaDisponibilita(ProdottoSelezionato prodotto, int qty) {
		try {
	        // Duale di quello sopra, leggere commenti in diminuisciDisponibilità
			//Questa volta è void perché non c'è una disponibilità massima da controllare
			
	        //BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\Davide\\git\\SmartTraining\\SmartTrainingFiles\\prodotti.txt"));
			BufferedReader bf_prodotti = Utilities.apriFile("prodotti.txt");
			StringBuffer inputBuffer = new StringBuffer();
	        String line;
	        int disp;

	        while ((line = bf_prodotti.readLine()) != null) {
	            String[] res = new String[100];
	            res = line.split("\\|");
	        	
	        	if(Integer.parseInt(res[0]) == prodotto.getCodice()) {
	        		disp = Integer.parseInt(res[4]) + qty;
	        		line = res[0] + "|" + res[1] + "|" + res[2] + "|" + 
	        				res[3] + "|" + disp; // replace the line here
	        		inputBuffer.append(line);
	        	    inputBuffer.append('\n');
	        	}
	        	
	        	else {
	        		inputBuffer.append(line);
	    	        inputBuffer.append('\n');
	        	}
	        }
	        bf_prodotti.close();

	        FileOutputStream fileOut = new FileOutputStream("C:\\SmartTrainingFiles\\prodotti.txt");
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();
	        lc.scriviMessaggio(LocalDateTime.now(),"Disponibilita' del prodotto con codice "+ prodotto.getCodice()+ " ripristinata correttamente");
	  	  
	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
		
	}
	
	/**
	 * Viene applicato lo sconto selezionato dal cliente (solo tra i disponibili), l'importo totale viene diminuito
	 * in base al valore dello sconto
	 * 
	 * @param puntiSullaTessera
	 * @param sommaSpesa
	 * 
	 * @return scontiDisponibili
	 */
	public float applicaSconto(Sconto s, float sommaSpesa) {
		return sommaSpesa - s.getValore();
	}
	
	/**
	 * Viene confermato l'acquisto. Il sistema esterno procede a validare il pagamento, in caso di esito positivo viene aggiornato
	 * il saldo punti e viene mandata la ricevuta via email.
	 * @param idCliente 
	 * 
	 * @param Sconto selezionato
	 * @return Esito operazione
	 */
	public boolean conferma(Sconto s, String idCliente) {
		if(effettuaPagamento() == false) return false;
		//Non c'è un while perché la conferma è manuale. Se il pagamento va male, si può anche decidere
		//di tornare indietro
		
		else {
			Acquisto a = creaAcquisto();
			aggiornaSaldoPunti(s);
			aggiungiPunti(a);
			mandaMail(a, idCliente);
			lc.scriviOperazione(LocalDateTime.now() ,"Acquisto con codice "+ a.getCodice()+ " effettuato correttamente",idCliente);			  
			return true;
		}
	}
	
	/**
	 * Genera l'acquisto 
	 * @return acquisto
	 */
	private Acquisto creaAcquisto() {
		int codice = Utilities.generaIntero(99999);
		int puntiGuadagnati = (int) Math.floor(calcolaSommaSpesa() / 5);
		return new Acquisto(codice, LocalDateTime.now().format(Utilities.formatterDataOra), puntiGuadagnati);
	}
	
	/**
	 * Manda via mail la ricevuta dell'acquisto appena effettuato
	 * @param idCliente 
	 * @param acquisto
	 */
	private void mandaMail(Acquisto a, String idCliente) {
		/*Formato: Quantità acquistata nome, prezzo euro (volendo si possono aggiungere anche codice e prezzo per singolo prodotto)
		 *  2 Integratori, 30 euro
		 *  1 Integratore, 15 euro
		 *  3 SteroidiSuperPower 150 euro
		*/
		StringBuilder sa = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		sa.append("Ciao gentilissimo " + Utilities.leggiCliente(idCliente).getNome() +" "+
				Utilities.leggiCliente(idCliente).getCognome()+", ecco il resoconto del tuo acquisto che ne attesta la validità.\n"
						+ "Grazie per aver scelto Smart Training!\n\n");
		sa.append(a.toString());
		for (ProdottoSelezionato prodottoSel : prodottiSelezionati)
			sb.append(prodottoSel.getQuantita() + " " + prodottoSel.getNome() + ", " +
						prodottoSel.getPrezzo() + 
						" € (prezzo singolo articolo: " + prodottoSel.getPrezzo()/(prodottoSel.getQuantita()*1.0) +" )\n");
		
		mail.Main.mandaMail(Utilities.leggiCliente(idCliente).getEmail(), sa.toString(), sb.toString());
		//Indirizzo, header (Codice, DataOra, PuntiGuadagnati), listaArticoliAcquistati
		
		//si manda email a indirizzo della palestra
		sa = new StringBuilder();
		sa.append("Salve, la informiamo che il cliente della sua palestra, "+ Utilities.leggiCliente(idCliente).getNome() +" "+
				Utilities.leggiCliente(idCliente).getCognome()+", ha completato con successo un acquisto tramite l'applicazione"
				+ " Smart Training! Ecco i dettagli dell'operazione:\n\n");
		sa.append(a.toStringPalestra());
		sb = new StringBuilder();
		for (ProdottoSelezionato prodottoSel : prodottiSelezionati)
			sb.append(prodottoSel.getQuantita() + " " + prodottoSel.getNome() + ", " +
						prodottoSel.getPrezzo() + 
						" € (prezzo singolo articolo: " + prodottoSel.getPrezzo()/(prodottoSel.getQuantita()*1.0) +" )\n");
		
		
		
		mail.Main.mandaMail("lorenzomario.amorosa@gmail.com", sa.toString(), sb.toString());
		//Indirizzo, header (Codice, DataOra, PuntiGuadagnati), listaArticoliAcquistati
		
	}
	
	private boolean effettuaPagamento() {
		return true;
	}
	
	/**
	 * Aggiorno la disponibilità di tutte le cose messe nel carrello
	 */
	public void annulla() {
		//Fa tutto il controller della view per semplicità
	}
	
	/**
	 * 
	 * @return saldoPunti
	 */
	public int getSaldoPunti() {
		return cli.getTes().getSaldoPunti();
	}
	
	/**
	 * Vengono scalati i punti se è stato selezionato uno sconto
	 * 
	 * @param sconto
	 */
	public void aggiornaSaldoPunti(Sconto s) {
		if(s != null) {
			cli.getTes().setSaldoPunti(getSaldoPunti() - s.getPuntiRichiesti());
			boolean found = false;
			StringBuilder sb = new StringBuilder();
			BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
			String line;
			String[] utente = new String[100];
			int i = 0;
			try {
				while((line = bf_utenti.readLine()) != null && !found) {
					
					utente = line.split(Pattern.quote("|"));
					if(cli.getId().equals(utente[3])) 
						found = true;
					i++;	
				}
				if(!found)
					return;
				//se sei qui hai trovato l'utente di cui aggiornare i dati,
				//in String[] utente ci sono i suoi vecchi dati
				bf_utenti.close();
				if(!Utilities.riscriviTranneRiga("utenti.txt", i))
					return;
				//ora bisogna aggiungere nuovamente nel file utenti.txt l'utente con dati nuovi
				
				PrintWriter pw = Utilities.apriFileAppend("utenti.txt");
				
				sb.append(utente[0]+"|"+utente[1]+"|"+utente[2]+"|"+utente[3]+"|"+utente[4]+"|"+
						utente[5]+"|");
				sb.append(utente[6]+"|");		
				sb.append(utente[7]+"|"+utente[8]+"|"+utente[9]+"|");
				sb.append(utente[10]+"|");
				sb.append(utente[11]+"|");
				sb.append(utente[12]+"|"+cli.getTes().getSaldoPunti()+"|"+utente[14]+"|"+utente[15]+"\n");
				
				pw.write(sb.toString());
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	
	/**
	 * Vengono aumentati i punti a seguito dell'acquisto
	 * 
	 * @param acquisto
	 */
	public void aggiungiPunti(Acquisto a) {
		cli.getTes().setSaldoPunti(getSaldoPunti() + a.getPuntiGuadagnati());
		boolean found = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
		String line;
		String[] utente = new String[100];
		int i = 0;
		try {
			while((line = bf_utenti.readLine()) != null && !found) {
				
				utente = line.split(Pattern.quote("|"));
				if(cli.getId().equals(utente[3])) 
					found = true;
				i++;	
			}
			if(!found)
				return;
			//se sei qui hai trovato l'utente di cui aggiornare i dati,
			//in String[] utente ci sono i suoi vecchi dati
			bf_utenti.close();
			if(!Utilities.riscriviTranneRiga("utenti.txt", i))
				return;
			//ora bisogna aggiungere nuovamente nel file utenti.txt l'utente con dati nuovi
			
			PrintWriter pw = Utilities.apriFileAppend("utenti.txt");
			
			sb.append(utente[0]+"|"+utente[1]+"|"+utente[2]+"|"+utente[3]+"|"+utente[4]+"|"+
					utente[5]+"|");
			sb.append(utente[6]+"|");		
			sb.append(utente[7]+"|"+utente[8]+"|"+utente[9]+"|");
			sb.append(utente[10]+"|");
			sb.append(utente[11]+"|");
			sb.append(utente[12]+"|"+cli.getTes().getSaldoPunti()+"|"+utente[14]+"|"+utente[15]+"\n");
			
			pw.write(sb.toString());
			pw.close();
			lc.scriviMessaggio(LocalDateTime.now() ,"Aggiornati correttamente i punti del cliente con id "+ utente[3]+ " dopo l'acquisto con codice "+ a.getCodice());			  
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * 
	 * @return somma totale da pagare
	 */
	public float calcolaSommaSpesa() {
		float sommaSpesa = (float) 0.0;
		for (ProdottoSelezionato prodotto : prodottiSelezionati) {			
			sommaSpesa += (prodotto.getPrezzo());
		}		
		return sommaSpesa;
	}
	
	/**
	 * Gli articoli presenti nel carrello sono confermati, si aggiorna la tabella con gli sconti disponibili
	 * in base al saldo punti e ai soldi spesi
	 * 
	 * @return scontiDispInBaseAPrezzoEPunti
	 */
	public List<Sconto> confermaCarrello() {
		float sommaSpesa = calcolaSommaSpesa();
				
		ArrayList<Sconto> scontiDispInBaseAPrezzoEPunti = new ArrayList<Sconto>();
		for (Sconto sconto : getScontiDisponibili()) {
			if(sconto.isAvailable(getSaldoPunti(), sommaSpesa))
				scontiDispInBaseAPrezzoEPunti.add(sconto);
		}
		return scontiDispInBaseAPrezzoEPunti;
	}
	
	/**
	 * Viene applicato lo sconto selezionato dal cliente (solo tra i disponibili), l'importo totale viene diminuito
	 * in base al valore dello sconto
	 * 
	 * @param Sconto selezionato	 * 
	 * @return Prezzo aggiornato
	 */
	public float applicaSconto(Sconto s) {
		return calcolaSommaSpesa() - s.getValore();
	}
	
}
