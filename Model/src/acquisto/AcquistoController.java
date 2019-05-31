package acquisto;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import model.Acquisto;
import model.Cliente;
import model.Prodotto;
import model.Sconto;
import util.Utilities;

public class AcquistoController {
	
	private Cliente cli;
	private List<Prodotto> prodottiSelezionati;
	
	public AcquistoController(Cliente c) {
		this.cli = c;
		this.prodottiSelezionati = new ArrayList<Prodotto>();
	}
	
	/**
	 * Legge dal file tutti i prodotti presenti, serve per riempire la tabella che permette la selezione
	 * @return prodotti
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
				campi = line.split("|");
				
				float prezzo = Float.parseFloat(campi[0]);
				int puntiRichiesti = Integer.parseInt(campi[1]);
				float spesaMinima = Float.parseFloat(campi[2]);
				
				Sconto s = new Sconto(prezzo, puntiRichiesti, spesaMinima);
				scontiDisp.add(s);
			}
			
			bf_sconti.close();
		} catch (IOException e) {
		
		}
		
		return scontiDisp;
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
			if((!codice.isPresent() || prodotto.getCodice() == codice.getAsInt()) &&
					(nomeProdotto == null || prodotto.getNome().contains(nomeProdotto)) &&
					(!prezzoMin.isPresent() || prodotto.getPrezzo() > prezzoMin.get()) &&
					(!prezzoMax.isPresent() || prodotto.getPrezzo() < prezzoMax.get()))
				prodottiFiltrati.add(prodotto);
		
		return prodottiFiltrati;
	}
	
	/**
	 * Si seleziona un prodotto dalla tabella iniziale e si aggiunge al carrello, diminuendo la sua disponibilità
	 * 
	 * @param prodotto
	 * @return prodottiSelezionati
	 */
	public List<Prodotto> aggiungiProdotto(Prodotto p) {
		if(diminuisciDisponibilita(p) < 1)
			//Fare alert impossibile selezionare;
		prodottiSelezionati.add(p);
		return prodottiSelezionati;
	}
	
	/**
	 * Aggiorna il file contenente i prodotti, diminuendo la disponibilità di quello scelto
	 * @param prodotto
	 * @return disponibilitàDiQuelProdotto
	 */
	private int diminuisciDisponibilita(Prodotto p) {
		int disp = -1;
		
		try {
	        // input the (modified) file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\Davide\\git\\SmartTraining\\SmartTrainingFiles\\prodotti.txt"));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;

	        while ((line = file.readLine()) != null) {
	            String[] res = new String[100];
	            res = line.split("\\|");
	        	
	        	if(Integer.parseInt(res[0]) == p.getCodice()) { //Se ho trovato il codice giusto
	        		disp = Integer.parseInt(res[4]) - 1; //Diminuisco subito la disponibilità
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
	        file.close();

	        // Scrivo tutto il buffer in overwrite sullo stesso file
	        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Davide\\git\\SmartTraining\\SmartTrainingFiles\\prodotti.txt");
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();
	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
		
		return disp;
	}
	
	/**
	 * Si toglie un prodotto dal carrello, viene aumentata la sua disponibilità
	 * @param prodotto
	 * @return prodottiSelezionati
	 */
	public List<Prodotto> eliminaProdotto(Prodotto p) {
		List<Prodotto> prodottiSelezionati = new ArrayList<Prodotto>();
		aumentaDisponibilita(p);
		prodottiSelezionati.remove(p);
		return prodottiSelezionati;
	}

	/**
	 * Aggiorna il file contenente i prodotti, aumentando la disponibilità di quello eliminato dalla scelta
	 * @param prodotto
	 */
	private void aumentaDisponibilita(Prodotto p) {
		try {
	        // Duale di quello sopra, leggere commenti in diminuisciDisponibilità
			//Questa volta è void perché non c'è una disponibilità massima da controllare
			
	        BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\Davide\\git\\SmartTraining\\SmartTrainingFiles\\prodotti.txt"));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;
	        int disp = -1;

	        while ((line = file.readLine()) != null) {
	            String[] res = new String[100];
	            res = line.split("\\|");
	        	
	        	if(Integer.parseInt(res[0]) == p.getCodice()) {
	        		disp = Integer.parseInt(res[4]) + 1;
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
	        file.close();

	        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Davide\\git\\SmartTraining\\SmartTrainingFiles\\prodotti.txt");
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
		
	}
	
	/**
	 * Gli articoli presenti nel carrello sono confermati, si aggiorna la tabella con gli sconti disponibili
	 * in base al saldo punti e ai soldi spesi
	 * 
	 * @param puntiSullaTessera
	 * @param sommaSpesa
	 * 
	 * @return scontiDisponibili
	 */
	public List<Sconto> confermaCarrello(int puntiSullaTessera, float sommaSpesa) {
		ArrayList<Sconto> disp = new ArrayList<Sconto>();
		for (Sconto sconto : getScontiDisponibili()) {
			if(sconto.isAvailable(puntiSullaTessera, sommaSpesa))
				disp.add(sconto);
		}
		return disp;
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
	
	public boolean conferma(Sconto s) { //DA RIVEDERE BENE.. quando si crea l'acquisto? (in confermaCarrello???)
		if(effettuaPagamento() == false) return false;
		//Non c'è un while perché la conferma è manuale. Se il pagamento va male, si può anche decidere
		//di tornare indietro
		
		else {
			Acquisto a = creaAcquisto();
			aggiornaSaldoPunti(s);
			mandaMail(a);
			return true;
		}
	}
	
	private Acquisto creaAcquisto() {
		int codice = Utilities.generaIntero(99999);
		int puntiGuadagnati = (int) Math.floor(calcolaSommaSpesa() / 10);
		return new Acquisto(codice, LocalDateTime.now(), puntiGuadagnati);
	}

	private void mandaMail(Acquisto a) {
		Map<String, Integer> prod = new HashMap<String, Integer>();
		StringBuilder sb = new StringBuilder();
		
		for (Prodotto prodotto : prodottiSelezionati) {
			if(prod.containsKey(prodotto.getNome())) {
				int value = prod.get(prodotto.getNome());
				prod.put(prodotto.getNome(), ++value);
			}
			else
				prod.put(prodotto.getNome(), 1);
		}
		
		/*Formato: Quantità acquistata nome, prezzo euro (volendo si possono aggiungere anche codice e prezzo per singolo prodotto)
		 *  2 Integratori, 30 euro
		 *  1 Integratore, 15 euro
		 *  3 SteroidiSuperPower 150 euro
		*/
		
		for(String name : prod.keySet()) {
			float price = (float) -1;
			int qty = prod.get(name);
			for (Prodotto prodotto : prodottiSelezionati) {
				if(prodotto.getNome().equals(name))
					price = prodotto.getPrezzo();
			}
			sb.append(qty + " " + name + ", " + price*qty + " euro\n");
		}
			
		mail.Main.mandaMail("aaabbbccc@gmail.com", a.toString(), sb.toString());
							//Indirizzo, header (Codice, DataOra, PuntiGuadagnati), listaArticoliAcquistati
	}
	
	private boolean effettuaPagamento() {
		if(Utilities.generaIntero(100) == 50) //Se l'intero generato è 50, fallisce (una possibilità su 100 di fallimento)
			return false;
		return true;
	}
	
	/**
	 * Aggiorno la disponibilità di tutte le cose messe nel carrello
	 */
	public void annulla() {
		for (Prodotto prodotto : prodottiSelezionati) {
			aumentaDisponibilita(prodotto);
		}
	}
	
	/**
	 * 
	 * @return saldoPunti
	 */
	public int getSaldoPunti() {
		return cli.getTes().getSaldoPunti();
	}
	
	/**
	 * Vengono scalati i se è stato selezionato uno sconto
	 * 
	 * @param sconto
	 */
	public void aggiornaSaldoPunti(Sconto s) {
		cli.getTes().setSaldoPunti(getSaldoPunti() - s.getPuntiRichiesti()); 
	}
	
	/**
	 * Vengono aumentati i punti a seguito dell'acquisto
	 * 
	 * @param acquisto
	 */
	public void aggiungiPunti(Acquisto a) {
		cli.getTes().setSaldoPunti(getSaldoPunti() + a.getPuntiGuadagnati()); 
	}
	
	
	//DA QUI IN POI CI SONO I METODI DA USARE SE LA VIEW NON FA I CALCOLI
	
	private float calcolaSommaSpesa() {
		float sommaSpesa = (float) 1.0;
		for (Prodotto prodotto : prodottiSelezionati) {
			sommaSpesa += prodotto.getPrezzo();
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
	 * @param puntiSullaTessera
	 * @param sommaSpesa
	 * 
	 * @return scontiDisponibili
	 */
	public float applicaSconto(Sconto s) {
		return calcolaSommaSpesa() - s.getValore();
	}
}
