package controller;


import java.io.*;
import java.net.*;

public class MainServer {

	public static void main(String[] args) throws IOException {
		
		//la settiamo fissa 10101
		int port = 10101;

		ServerSocket serverSocket = null;
		Socket clientSocket = null;

		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			System.out.println("Server: avviato ");
			System.out.println("Server: creata la server socket: " + serverSocket);
		} catch (Exception e) {
			System.out.println("Server: problemi nella creazione della server socket: " + e.getMessage());
			e.printStackTrace();
			serverSocket.close();
			System.exit(3);
		}

		try {
			while (true) {
				System.out.println("Server: in attesa di richieste...\n");

				try {
					clientSocket = serverSocket.accept();
					System.out.println("Server: connessione accettata: " + clientSocket);
				} catch (Exception e) {
					System.out.println("Server: problemi nella accettazione della connessione: " + e.getMessage());
					e.printStackTrace();
					continue;
				}

				try {
					new ServerThread(clientSocket).start();
				} catch (Exception e) {
					System.out.println("Server: problemi nel server thread: " + e.getMessage());
					e.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Server: termino...");
			System.exit(2);
		}
	}
}

/* ----------------------------------------------------------------------------- */

class ServerThread extends Thread {
	private Socket clientSocket = null;

	public ServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		System.out.println("Attivazione figlio: " + Thread.currentThread().getName());

		DataInputStream inSock;
		DataOutputStream outSock;

		try {
			inSock = new DataInputStream(clientSocket.getInputStream());
			outSock = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException ioe) {
			System.out.println("Problemi nella creazione degli stream di input/output su socket: ");
			ioe.printStackTrace();
			return;
		}

		try {
			try {
				String richiesta;
				while ((richiesta = inSock.readUTF()) != null) {
					System.out.println("ServerThread: ricevuto " + richiesta);
					String[] campi = new String[2000];
					campi = richiesta.split("|");
					// Elaborazione risposta
					switch(campi[0]) {
					case "login": login(campi, inSock, outSock); // richiesta = login|username|password
						break;
					case "log": log(campi, inSock, outSock); // richiesta = log
						break;
					case "registrazione": registrazione(campi, inSock, outSock); // richiesta = registrazione|username|password|'P' / 'C' / 'A'|nome|cognome|mail|cf|data nascita|luogo nascita|indirizzo|telefono|codice id personal trainer  
						break;
					case "visualizzaStoricoCliente": visualizzaStoricoCliente(campi, inSock, outSock);
					default:
					}
					
				}
			} catch (EOFException eof) {
				System.out.println("Raggiunta la fine delle ricezioni, chiudo...");
				clientSocket.close();
				System.out.println("ServerThread: termino...");
				System.exit(0);
			} catch (SocketTimeoutException ste) {
				System.out.println("Timeout scattato: ");
				ste.printStackTrace();
				clientSocket.close();
				System.exit(1);
			} catch (Exception e) {
				System.out.println("Problemi, i seguenti : ");
				e.printStackTrace();
				System.out.println("Chiudo ed esco...");
				clientSocket.close();
				System.exit(2);
			}
		} catch (IOException ioe) {
			System.out.println("Problemi nella chiusura della socket: ");
			ioe.printStackTrace();
			System.out.println("Chiudo ed esco...");
			System.exit(3);
		}
	}
	
	private void visualizzaStoricoCliente(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		// TODO Auto-generated method stub
		
	}

	// richiesta = registrazione|username|password|'P' / 'C' / 'A'|nome|cognome|mail|cf|data nascita|luogo nascita|indirizzo|telefono|codice id personal trainer  
	private void registrazione(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
		String line;
		try {
			while((line = bf_utenti.readLine()) != null) {
				
				String[] utente = new String[100];
				utente = line.split("|");
				
				//confronto username, email e codice fiscale, che devono essere univoche nel sistema
				if(campi[1].equals(utente[0])) {
					outSock.writeUTF("username");
					return;
				}
				if(campi[6].equals(utente[6])) {
					outSock.writeUTF("email");
					return;
				}
				if(campi[7].contentEquals(utente[7])) {
					outSock.writeUTF("cf");
					return;
				}

			}
		}	
			catch(Exception e) {
				
			}
		
	}

	private void log(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		BufferedReader bf_log = Utilities.apriFile("log.txt");
		String line;
		try {
			while((line = bf_log.readLine()) != null) {
				outSock.writeUTF(line);	//viene passato al client ogni stringa contenuta nel log
			}
		} catch (IOException e) {
			}
		
	}

	private void login(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		BufferedReader bf_utenti = Utilities.apriFile("utenti.txt");
		String line;
		boolean found = false;
		try {
			while((line = bf_utenti.readLine()) != null && !found) {
				
				String[] utente = new String[100];
				utente = line.split("|");
				
				//confronto username e password arrivati con username e password nel file
				if(campi[1].equals(utente[0]) && campi[2].equals(utente[1])) {
					found = true;
					if(!utente[2].equals("C"))	//se non � cliente, passo direttamente la stringa che rappresenta l'utente 
						outSock.writeUTF(line);
					else {
						BufferedReader bf_orari = Utilities.apriFile("orariIngressoUscita.txt");
						
					}
				}
			}
			if(!found) //segnalo errore passando una stringa di null, cos� qualunque campo letto non � valido
				outSock.writeUTF("null|null|null|null|null|null|null|null|null|null|null|null");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
