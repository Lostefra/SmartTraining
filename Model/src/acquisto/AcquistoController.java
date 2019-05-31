package acquisto;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.regex.Pattern;

import mail.Main;
import model.Acquisto;
import model.Alimento;
import model.Cliente;
import model.Esercizio;
import model.Pasto;
import model.PersonalTrainer;
import model.PianoNutrizionale;
import model.ProdottiPalestra;
import model.Prodotto;
import model.SchedaAllenamento;
import model.ScontiPalestra;
import model.Sconto;
import model.Sessione;
import model.TesseraSocio;
import util.Utilities;

public class AcquistoController {
	
	private Cliente cli;
	private List<Prodotto> selezionati;
	
	public AcquistoController(Cliente c) {
		this.cli = c;
		this.selezionati = new ArrayList<Prodotto>();
	}
	
	/**
	 * 
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
				campi = line.split("|");
				
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
		
		}
		return prodotti;
	}
	
	/**
	 *
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
	 * I parametri in input messi a null => non si filtra per quel paramentro 
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
	 *
	 * @param prodotto
	 * @return prodottiSelezionati
	 */
	public List<Prodotto> aggiungiProdotto(Prodotto p) {
		List<Prodotto> prodottiSelezionati = new ArrayList<Prodotto>();
		diminuisciDisponibilita(p);
		prodottiSelezionati.add(p);
		return prodottiSelezionati;
	}
	
	/**
	 *
	 * @param prodotto
	 */
	private void diminuisciDisponibilita(Prodotto p) {
		try {
	        // input the (modified) file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\Davide\\git\\SmartTraining\\SmartTrainingFiles\\prodotti.txt"));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;
	        int disp = -1;

	        while ((line = file.readLine()) != null) {
	            String[] res = new String[100];
	            res = line.split("\\|");
	        	
	        	if(Integer.parseInt(res[0]) == p.getCodice()) {
	        		disp = Integer.parseInt(res[4]) - 1;
	        		line = res[0] + "|" + res[1] + "|" + res[2] + "|" + 
	        				res[3] + "|" + disp; // replace the line here
	        		inputBuffer.append(line);
	        	    inputBuffer.append('\n');
	        	}
	        	
	        	else {   // replace the line here
	    	        		inputBuffer.append(line);
	    	        	    inputBuffer.append('\n');
	        	}
	        }
	        file.close();

	        // write the new string with the replaced line OVER the same file
	        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Davide\\git\\SmartTraining\\SmartTrainingFiles\\prodotti.txt");
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
	}
	
	/**
	 *
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
	 *
	 * @param prodotto
	 */
	private void aumentaDisponibilita(Prodotto p) {
		try {
	        // input the (modified) file content to the StringBuffer "input"
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
	        	
	        	else {   // replace the line here
	    	        		inputBuffer.append(line);
	    	        	    inputBuffer.append('\n');
	        	}
	        }
	        file.close();

	        // write the new string with the replaced line OVER the same file
	        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Davide\\git\\SmartTraining\\SmartTrainingFiles\\prodotti.txt");
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
		
	}
	
	/**
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
	
	public float applicaSconto(Sconto s, float sommaSpesa) {
		return sommaSpesa - s.getValore();
	}
	
	public boolean conferma(Acquisto a, Sconto s) { //DA RIVEDERE BENE, non so se la view fa i calcoli
		if(effettuaPagamento() == false) return false;
		
		else {
			aggiornaSaldoPunti(s);
			mandaMail(a);
			
			return true;
		}
	}
	
	private void mandaMail(Acquisto a) {
		StringBuilder sb = new StringBuilder();
		int numeroCoseAcquistate = 1; /*Provvisorio: dobbiamo prendere la lista dei selezionati e
										vedere quante volte compare ogni prodotto
									  */
		for (Prodotto prodotto : selezionati) {
			/*Formato: 2 Integratori, 30 euro
						1 Integratore, 15 euro
						3 SteroidiSuperPower 150 euro
			*/
			sb.append("NumeroCoseAcquistate " + prodotto.getNome() + ", " + prodotto.getPrezzo()*numeroCoseAcquistate + "\n");
		}
		mail.Main.mandaMail("aaabbbccc@gmail.com", a.toString(), sb.toString());
							//Indirizzo, header (Codice, DataOra, PuntiGuadagnati), listaArticoliAcquistati
	}
	
	private boolean effettuaPagamento() {
		if(Utilities.generaIntero(100) == 50) //Se l'intero generato è 50, fallisce (una possibilità su 100)
			return false;
		return true;
	}
	
	public void annulla() {
		//?????
	}
	
		
	public int getSaldoPunti() {
		return cli.getTes().getSaldoPunti();
	}
	
	public int aggiornaSaldoPunti(Sconto s) {
		int nuovoSaldoPunti;
		nuovoSaldoPunti = getSaldoPunti() - s.getPuntiRichiesti();
		return nuovoSaldoPunti;
	}
}
