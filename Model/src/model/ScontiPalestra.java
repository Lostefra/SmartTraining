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

public class ScontiPalestra {
	private List<Sconto> listaSconti;

	public ScontiPalestra() {
		try {
			this.listaSconti = getScontiPalestra();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<Sconto> getScontiPalestra() throws IOException {
		List<Sconto> listaTemp = new ArrayList<Sconto>();
		String filepath = "path\\to\\fileSconti";
		FileWriter fw = new FileWriter(filepath,true); //Apertura in append con flag true
		PrintWriter pw = new PrintWriter(new BufferedWriter(fw)); //Wrapper vari per fare la println, più comoda
		
		BufferedReader rd = new BufferedReader(new FileReader(filepath));
		String riga;
		while((riga = rd.readLine()) != null) {
			StringTokenizer stk = new StringTokenizer(riga);
			
			float valore = Float.parseFloat(stk.nextToken());
			int puntiRichiesti = Integer.parseInt(stk.nextToken());
			float spesaMinima = Float.parseFloat(stk.nextToken());
			
			
			listaTemp.add(new Sconto(valore, puntiRichiesti, spesaMinima));
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

	public List<Sconto> getListaSconti() {
		return listaSconti;
	}
	
}
