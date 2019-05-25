package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import model.Cliente;
import model.PersonalTrainer;
import model.TesseraSocio;


public class Utilities {		

	private static DateTimeFormatter formatterDataOra = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private static DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("mm:ss");
	private static String alphaNumericCharacters = "abcdefghijklmnopqrstuvwxyz"
												+ "ABCDEFGHIJLMNOPQRSTUVWXYZ"
												+ "1234567890";

	
	/**
	 * 
	 *  Il file deve essere inserito nella cartella C:/SmartTrainingFiles/ per essere letto,
	 *  la funzione ritorna un BufferedReader che punta al file che deve essere letto
	 * 
	 * @param filename
	 * @return bufferedReader
	 */
	public static BufferedReader apriFile(String filename) {
		return new BufferedReader(new InputStreamReader(Utilities.class.getResourceAsStream("C:/SmartTrainingFiles/" + filename)));
	}
	
	/**
	 * 
	 * Il file deve essere inserito nella cartella C:/SmartTrainingFiles/ per essere scritto in append,
	 *  la funzione ritorna un PrintWriter che punta al file che deve essere scritto in append
	 * 
	 * @param filename
	 * @return printWriter
	 */
	public static PrintWriter apriFileAppend(String filename) {
		FileWriter fw;
		try {
			fw = new FileWriter("C:/SmartTrainingFiles/" + filename,true);
			return new PrintWriter(new BufferedWriter(fw)); //Wrapper vari per fare la println, pi� comoda
		} catch (IOException e) {
			
		} //Apertura in append con flag true
		return null;
	}
	
	/**
	 * 
	 * Il file deve essere inserito nella cartella C:/SmartTrainingFiles/ per essere scritto da zero,
	 *  la funzione ritorna un PrintWriter che punta al file che deve essere scritto da zero
	 * 
	 * @param filename
	 * @return printWriter
	 */
	public static PrintWriter apriFileOverwrite(String filename) {
		FileWriter fw;
		try {
			fw = new FileWriter("C:/SmartTrainingFiles/" + filename, false);
			return new PrintWriter(new BufferedWriter(fw)); //Wrapper vari per fare la println, pi� comoda
		} catch (IOException e) {
	
		} //Apertura in overWrite con flag false
		return null;
		
	}
	
	/**
	 * 
	 * @param filename
	 * @param lineToRemove (numero linea, la prima e' 0, poi 1 ect)
	 * @return operazione di rename completata
	 */
	public static boolean riscriviTranneRiga(String filename, int lineToRemove) {
		
		File tempFile = new File("C:/SmartTrainingFiles/tmp.txt");
		PrintWriter pw = apriFileOverwrite("C:/SmartTrainingFiles/tmp.txt");
		BufferedReader reader = apriFile("C:/SmartTrainingFiles/" + filename);
		String currentLine;
		try {
			int i = 0;
			while ((currentLine = reader.readLine()) != null) {
				if (i != lineToRemove)
					pw.write(currentLine+"\n");
			}
			pw.close();
			reader.close();
			File inputFile = new File("C:/SmartTrainingFiles/" + filename);
			return  tempFile.renameTo(inputFile);
			}
		catch(Exception e) {
			
		}
		return false;
	}

	/**
	 * 
	 * @param id cliente
	 * @return cliente associato ad id
	 */
	public static Cliente leggiCliente(String id) {
		BufferedReader bf = apriFile("utenti.txt");
		String line;
		//username|password|'P' / 'C' / 'A'|id utente|nome|cognome|mail|cf|data nascita|luogo nascita|indirizzo|telefono|numero tessera|punti|ultimo aggiornamento|codice id personal trainer  

		try {
			while((line = bf.readLine()) != null) {
				String[] utente = new String[100];
				utente = line.split("|");
										
				if(id.equals(utente[3]) && utente[2].equals("C")){
					return new Cliente(utente[4], utente[5], utente[6], utente[7], LocalDate.parse(utente[8], formatterData),
							utente[9], utente[10], utente[11], new TesseraSocio(Integer.parseInt(utente[12]), 
									Integer.parseInt(utente[13]), LocalDateTime.parse(utente[14], formatterDataOra)), utente[3]);
				}
			}
		} catch (IOException e) {
			
		}
		return null;
	}

	/**
	 * 
	 * @param id personal trainer
	 * @return PersonalTrainer associato a id
	 */
	public static PersonalTrainer leggiPersonalTrainer(String id) {
		BufferedReader bf = apriFile("utenti.txt");
		String line;
		//username|password|'P' / 'C' / 'A'|id utente|nome|cognome|mail|cf|data nascita|luogo nascita|indirizzo|telefono|numero tessera|punti|ultimo aggiornamento|codice id personal trainer  

		try {
			while((line = bf.readLine()) != null) {
				String[] utente = new String[100];
				utente = line.split("|");
										
				if(id.equals(utente[3]) && utente[2].equals("C")){
					return new PersonalTrainer(utente[4], utente[5], utente[6], utente[7], LocalDate.parse(utente[8], formatterData),
							utente[9], utente[10], utente[11], utente[3]);
				}
			}
		} catch (IOException e) {
			
		}
		return null;	
	}
	
	/**
	 * 
	 * @param prefix del codice generato
	 * @param max caratteri random da inserire nel codice
	 * @return codice
	 */
	public static String generaID(String prefix, int max) {
		Random r = new Random();
		StringBuilder result = new StringBuilder(prefix);
		
		for (int i =0; i < max ; i++) 
			result.append(alphaNumericCharacters.charAt(r.nextInt(alphaNumericCharacters.length())));
	    
		return result.toString();
	}
	
	/**
	 * 
	 * @return numero random
	 */
	public static int generaIntero() {
		Random r = new Random();
		return r.nextInt();
	}


}
