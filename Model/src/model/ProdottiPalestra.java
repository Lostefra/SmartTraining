package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ProdottiPalestra {
	private List<Prodotto> listaProdotti;

	public ProdottiPalestra() {
		try {
			this.listaProdotti = leggiProdotti();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<Prodotto> leggiProdotti() throws IOException {
		List<Prodotto> listaTemp = new ArrayList<Prodotto>();
		String filepath = "path\\to\\fileProdotti";
		FileWriter fw = new FileWriter(filepath,true); //Apertura in append con flag true
		PrintWriter pw = new PrintWriter(new BufferedWriter(fw)); //Wrapper vari per fare la println, più comoda
		
		BufferedReader rd = new BufferedReader(new FileReader(filepath));
		String riga;
		while((riga = rd.readLine()) != null) {
			StringTokenizer stk = new StringTokenizer(riga);
			
			int codice = Integer.parseInt(stk.nextToken());
			String nome = stk.nextToken();
			float prezzo = Float.parseFloat(stk.nextToken());
			String descrizione = stk.nextToken();
			int quantita = Integer.parseInt(stk.nextToken());
			
			
			listaTemp.add(new Prodotto(codice, nome, prezzo,
										descrizione, quantita));
		}
		
		rd.close();
		pw.close();
		
		/*	STAMPA DI PROVA
		for (Sconto sconto : listaTemp) {
			System.out.println("Sconto dal valore di " + sconto.getValore() + " euro" +
					"\nServono " + sconto.getPuntiRichiesti() + " punti" +
					"\nE una spesa minima di " + sconto.getSpesaMinima() + " euro\n\n");
		}
		*/
		
		return listaTemp;
	}

	public List<Prodotto> getListaProdotti() {
		return listaProdotti;
	}
	
}
