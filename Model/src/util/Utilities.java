package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.regex.Pattern;

import model.Cliente;
import model.PersonalTrainer;
import model.TesseraSocio;


public class Utilities {		

	public static DateTimeFormatter formatterDataOra = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	public static DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");
	public static DateTimeFormatter formatterRecupero = DateTimeFormatter.ofPattern("mm:ss");
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
		try {
			if(filename.startsWith("C:/"))
				return new BufferedReader(new FileReader(new File(filename)));
			else
				return new BufferedReader(new FileReader(new File("C:/SmartTrainingFiles/" + filename)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
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
			if(filename.startsWith("C:/"))
				fw = new FileWriter(filename,true);
			else
				fw = new FileWriter("C:/SmartTrainingFiles/" + filename,true);
			return new PrintWriter(new BufferedWriter(fw)); //Wrapper vari per fare la println, più comoda
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
			if(filename.startsWith("C:/"))
				fw = new FileWriter(filename,false);
			else
				fw = new FileWriter("C:/SmartTrainingFiles/" + filename,false);
			return new PrintWriter(new BufferedWriter(fw)); //Wrapper vari per fare la println, più comoda
		} catch (IOException e) {
	
		} //Apertura in overWrite con flag false
		return null;
		
	}
	
	/**
	 * 
	 * @param filename
	 * @param lineToRemove (numero linea, la prima e' 1, poi 2 ect)
	 * @return operazione di rename completata
	 */
	public static boolean riscriviTranneRiga(String filename, int lineToRemove) {
		
		File tempFile = new File("C:/SmartTrainingFiles/tmp.txt");
		PrintWriter pw = apriFileOverwrite("C:/SmartTrainingFiles/tmp.txt");
		BufferedReader reader = apriFile(filename);
		String currentLine;
		try {
			int i = 1;
			while ((currentLine = reader.readLine()) != null) {
				if (i != lineToRemove)
					pw.write(currentLine+"\n");
				i++;
			}
			pw.close();
			reader.close();
			Files.delete(Paths.get("C:/SmartTrainingFiles/"+ filename));
			File inputFile = new File("C:/SmartTrainingFiles/" + filename);
			return  tempFile.renameTo(inputFile);
		}
		catch(Exception e) {
			e.printStackTrace();
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
		try {
			while((line = bf.readLine()) != null) {
				String[] utente = new String[100];
				utente = line.split(Pattern.quote("|"));
										
				if(id.equals(utente[3]) && utente[2].equals("C")){
					bf.close();
					return new Cliente(utente[4], utente[5], utente[6], utente[7], LocalDate.parse(utente[8], formatterData),
							utente[9], utente[10], utente[11], new TesseraSocio(Integer.parseInt(utente[12]), 
									Integer.parseInt(utente[13]), LocalDateTime.parse(utente[14], formatterDataOra)), utente[3]);
				}
			}
			bf.close();
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
		
		try {
			while((line = bf.readLine()) != null) {
				String[] utente = new String[100];
				utente = line.split(Pattern.quote("|"));
										
				if(id.equals(utente[3]) && utente[2].equals("P")){
					bf.close();
					return new PersonalTrainer(utente[4], utente[5], utente[6], utente[7], LocalDate.parse(utente[8], formatterData),
							utente[9], utente[10], utente[11], utente[3]);
				}
			}
			bf.close();
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
		int res =  r.nextInt();
		if(res < 0)
			return - res;
		return res;
	}
	
	/**
	 * @param upperBound
	 * @return numero random
	 */
	public static int generaIntero(int upperBound) {
		Random r = new Random();
		return r.nextInt(upperBound);
	}

	/**
	 * 
	 * Ritorna il cliente dallo username dato
	 * 
	 * @param username
	 * @return Cliente
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static Cliente getCliente(String username) throws NumberFormatException, IOException {
		BufferedReader reader = apriFile("utenti.txt");
		Cliente cliente = null;
		String currentLine;
		String[] utente = new String[200];
		
		while ((currentLine = reader.readLine()) != null) {
			utente = currentLine.split(Pattern.quote("|"));
			
			if (utente[0].equals(username))
				cliente = new Cliente(utente[4], utente[5], utente[6], utente[7], LocalDate.parse(utente[8], formatterData),
						utente[9], utente[10], utente[11], new TesseraSocio(Integer.parseInt(utente[12]), 
								Integer.parseInt(utente[13]), LocalDateTime.parse(utente[14], formatterDataOra)), utente[3]);
		}
		reader.close();

		return cliente;

	}

	/**
	 * 
	 * Ritorna il Personal trainer dallo username dato
	 * 
	 * @param username
	 * @return Personal Trainer
	 * @throws NumberFormatException
	 * @throws IOException
<<<<<<< HEAD
	 */

	public static PersonalTrainer getPersonalTrainer(String username) throws NumberFormatException, IOException {
		BufferedReader reader = apriFile("utenti.txt");
		PersonalTrainer personalTrainer = null;
		String currentLine;
		String[] utente = new String[200];
		
		while ((currentLine = reader.readLine()) != null) {
			utente = currentLine.split(Pattern.quote("|"));
			
			if (utente[0].equals(username))
				personalTrainer = new PersonalTrainer(utente[4], utente[5], utente[6], utente[7], LocalDate.parse(utente[8], formatterData),
						utente[9], utente[10], utente[11], utente[3]);
		}
		reader.close();
		return personalTrainer;


	}

	public static boolean eliminazione(int riga) {
		BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
		String line = null;
		int i = 0;
		try {
			for(; i < riga; i++) {
				line = bf_utenti.readLine();
			}
			String[] utente = new String[100];
			utente = line.split(Pattern.quote("|"));			
			//se sei qui hai trovato l'utente da eliminare,
			bf_utenti.close();
			//eliminazione
			Utilities.riscriviTranneRiga("utenti.txt", riga);
			//inserimento riga senza dati ( username e password )
			PrintWriter pw = Utilities.apriFileAppend("utenti.txt");
			pw.println("deleted|deleted|C|" +utente[3] +"|"+utente[4]+"|"+utente[5]+"|"+ utente[6]+"|"+utente[7]+"|"+utente[8]+"|"+utente[9]+"|"+utente[10]+"|"+utente[11]+"|"+utente[12]+"|"+utente[13]+"|"+utente[14]+"|null");
			pw.close();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}


}
