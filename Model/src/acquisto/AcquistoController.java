package acquisto;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import model.Acquisto;
import model.Cliente;
import model.Prodotto;
import model.ProdottoSelezionato;
import model.Sconto;
import util.Utilities;

public class AcquistoController {
	
	private Cliente cli;
	private List<ProdottoSelezionato> prodottiSelezionati;
	
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
			e.printStackTrace();
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
	 * Si seleziona un prodotto dalla tabella iniziale e si aggiunge al carrello, diminuendo la sua disponibilit�
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
	 * Aggiorna il file contenente i prodotti, diminuendo la disponibilit� di quello scelto
	 * @param prodotto
	 * @param numero articoli selezionati
	 *  
	 * @return disponibilit�DiQuelProdotto
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
	        		disp = Integer.parseInt(res[4]) - qty; //Diminuisco subito la disponibilit�
	        		line = res[0] + "|" + res[1] + "|" + res[2] + "|" + 
	        				res[3] + "|" + disp; // Riscrivo la riga gi� con la disponibilit� aggiornata
	        		inputBuffer.append(line);
	        	    inputBuffer.append('\n');
	        	}
	        	
	        	else { //Non era il codice giusto, salvo la riga appena letta nel buffer e provo con quella dopo
	    	        inputBuffer.append(line);
	    	        inputBuffer.append('\n');
	        	}
	        	
	        	//Non � trattato il caso in cui non si trovi il codice giusto perch� la scelta � forzata dalla view
	        	//Non essendoci parametri che l'utente pu� inserire, sono per forza giusti
	        }
	        bf_prodotti.close();

	        // Scrivo tutto il buffer in overwrite sullo stesso file
	        FileOutputStream fileOut = new FileOutputStream("C:\\SmartTrainingFiles\\prodotti.txt");
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();
	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
	}
	
	/**
	 * Si toglie un prodotto dal carrello, viene aumentata la sua disponibilit�.
	 * @param prodotto
	 * 
	 * @return List<ProdottoSelezionato> aggiornata
	 */
	public List<ProdottoSelezionato> eliminaProdotto(ProdottoSelezionato p, int qty) {
		List<ProdottoSelezionato> prodottiSelezionati = new ArrayList<ProdottoSelezionato>();
		aumentaDisponibilita(p, qty);
		prodottiSelezionati.remove(p);
		return prodottiSelezionati;
	}

	/**
	 * Aggiorna il file contenente i prodotti, aumentando la disponibilit� di quello eliminato dalla scelta
	 * @param prodotto
	 */
	public void aumentaDisponibilita(ProdottoSelezionato prodotto, int qty) {
		try {
	        // Duale di quello sopra, leggere commenti in diminuisciDisponibilit�
			//Questa volta � void perch� non c'� una disponibilit� massima da controllare
			
	        //BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\Davide\\git\\SmartTraining\\SmartTrainingFiles\\prodotti.txt"));
			BufferedReader bf_prodotti = Utilities.apriFile("prodotti.txt");
			StringBuffer inputBuffer = new StringBuffer();
	        String line;
	        int disp = -1;

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
	 * 
	 * @param Sconto selezionato
	 * @return Esito operazione
	 */
	public boolean conferma(Sconto s) {
		if(effettuaPagamento() == false) return false;
		//Non c'� un while perch� la conferma � manuale. Se il pagamento va male, si pu� anche decidere
		//di tornare indietro
		
		else {
			Acquisto a = creaAcquisto();
			aggiornaSaldoPunti(s);
			mandaMail(a);
			return true;
		}
	}
	
	/**
	 * Genera l'acquisto 
	 * @return acquisto
	 */
	private Acquisto creaAcquisto() {
		int codice = Utilities.generaIntero(99999);
		int puntiGuadagnati = (int) Math.floor(calcolaSommaSpesa() / 10);
		return new Acquisto(codice, LocalDateTime.now().format(Utilities.formatterDataOra), puntiGuadagnati);
	}
	
	/**
	 * Manda via mail la ricevuta dell'acquisto appena effettuato
	 * @param acquisto
	 */
	private void mandaMail(Acquisto a) {
		/*Formato: Quantit� acquistata nome, prezzo euro (volendo si possono aggiungere anche codice e prezzo per singolo prodotto)
		 *  2 Integratori, 30 euro
		 *  1 Integratore, 15 euro
		 *  3 SteroidiSuperPower 150 euro
		*/
		
		StringBuilder sb = new StringBuilder();
		
		for (ProdottoSelezionato prodottoSel : prodottiSelezionati)
			sb.append(prodottoSel.getQuantita() + " " + prodottoSel.getNome() + ", " +
						prodottoSel.getPrezzo()*prodottoSel.getQuantita() + 
						" � (prezzo singolo articolo: " + prodottoSel.getPrezzo() +" )\n");
		
		mail.Main.mandaMail("aaabbbccc@gmail.com", a.toString(), sb.toString());
		//Indirizzo, header (Codice, DataOra, PuntiGuadagnati), listaArticoliAcquistati
	}
	
	private boolean effettuaPagamento() {
		if(Utilities.generaIntero(100) == 50) //Se l'intero generato � 50, fallisce (una possibilit� su 100 di fallimento)
			return false;
		return true;
	}
	
	/**
	 * Aggiorno la disponibilit� di tutte le cose messe nel carrello
	 */
	public void annulla() {
		//Fa tutto il controller della view per semplicit�
	}
	
	/**
	 * 
	 * @return saldoPunti
	 */
	public int getSaldoPunti() {
		return cli.getTes().getSaldoPunti();
	}
	
	/**
	 * Vengono scalati i se � stato selezionato uno sconto
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
	
	public float calcolaSommaSpesa() {
		float sommaSpesa = (float) 1.0;
		for (ProdottoSelezionato prodotto : prodottiSelezionati) {
			sommaSpesa += (prodotto.getPrezzo() * prodotto.getQuantita());
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
