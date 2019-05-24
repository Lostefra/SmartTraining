package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Attori.Cliente;
import Attori.PersonalTrainer;
import Attori.TesseraSocio;


public class Utilities {		

	static DateTimeFormatter formatterDataOra = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	static DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("mm:ss");
	
	/*
	 * 
	 *  Il file deve essere inserito nel package "files" per essere letto,
	 *  la funzione ritorna un BufferedReader che punta al file che deve essere letto
	 *    
	 */
	public static BufferedReader apriFile(String filename) {
		return new BufferedReader(new InputStreamReader(Utilities.class.getResourceAsStream("C:/SmartTrainingFiles/" + filename)));
	}
	
	public static PrintWriter apriFileAppend(String filename) {
		FileWriter fw;
		try {
			fw = new FileWriter("C:/SmartTrainingFiles/" + filename,true);
			return new PrintWriter(new BufferedWriter(fw)); //Wrapper vari per fare la println, più comoda
		} catch (IOException e) {
			
		} //Apertura in append con flag true
		return null;
	}
	
	public static PrintWriter apriFileOverwrite(String filename) {
		FileWriter fw;
		try {
			fw = new FileWriter("C:/SmartTrainingFiles/" + filename, false);
			return new PrintWriter(new BufferedWriter(fw)); //Wrapper vari per fare la println, più comoda
		} catch (IOException e) {
	
		} //Apertura in overWrite con flag false
		return null;
		
	}

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

}
