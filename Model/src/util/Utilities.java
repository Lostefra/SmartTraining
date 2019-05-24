package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Utilities {	
	/*
	 * 
	 *  Il file deve essere inserito nel package "files" per essere letto,
	 *  la funzione ritorna un BufferedReader che punta al file che deve essere letto
	 *    
	 */
	public static BufferedReader apriFile(String filename) {
		return new BufferedReader(new InputStreamReader(Utilities.class.getResourceAsStream("C:/SmartTrainingFiles/" + filename)));
	}

}
