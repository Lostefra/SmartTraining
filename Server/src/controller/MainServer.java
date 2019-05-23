package controller;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
					case "registrazione": registrazione(campi, inSock, outSock); // richiesta = registrazione|username|password|'P' / 'C'|nome|cognome|mail|cf|data nascita|luogo nascita|indirizzo|telefono|codice id personal trainer  
						break;
					case "visualizzaStoricoCliente": visualizzaStoricoCliente(campi, inSock, outSock); //richiesta = visualizzaStoricoCliente|idCliente
						break;
					case "visualizzaStoricoPT": visualizzaStoricoPT(campi, inSock, outSock); //richiesta = visualizzaStoricoPT
						break;
					case "visualizzaAttuali": visualizzaAttuali(campi, inSock, outSock); //richiesta = visualizzaAttuali|idCliente
						break;
						//bisogna ricordarsi di invalidare richieste dopo inserimento
					case "visualizzaRichieste": visualizzaRichieste(campi, inSock, outSock); //richiesta = visualizzaRichieste|idPersonalTrainer
						break;
					case "gestioneAccount": gestioneAccount(campi, inSock, outSock);
						break;
					case "creazioneID": creazioneID(campi, inSock, outSock);
						break;
					case "acquisto": acquisto(campi, inSock, outSock);
						break;
					case "richiediScheda": richiediScheda(campi, inSock, outSock);
						break;
					case "inserisciScheda": inserisciScheda(campi, inSock, outSock);
						break;
					default:
						System.out.println("wua wua wua wuaaaaaaa..");
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
	
	private void inserisciScheda(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		// TODO Auto-generated method stub
		
	}

	private void richiediScheda(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		// TODO Auto-generated method stub
		
	}

	private void acquisto(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		// TODO Auto-generated method stub
		
	}

	private void creazioneID(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		// TODO Auto-generated method stub
		
	}

	private void gestioneAccount(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		// TODO Auto-generated method stub
		
	}

	//richiesta = visualizzaRichieste|idPersonalTrainer
	private void visualizzaRichieste(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		BufferedReader bf_richieste = Utilities.apriFile("richieste.txt");
		String line;
		try {
			while((line = bf_richieste.readLine()) != null) {
				String[] richiesta = new String[100];
				richiesta = line.split("|");
				if(richiesta[2].equals(campi[1])) {	//idPersonalTrainer uguale in richiesta e campo passato
					outSock.writeUTF(line);	//viene passato al clientPT la scheda 
				}		
			}
		} catch (IOException e) {
			}
		

	}

	private void visualizzaAttuali(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		BufferedReader bf_schede = Utilities.apriFile("schede.txt");
		String line;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			while((line = bf_schede.readLine()) != null) {
				String[] scheda = new String[100];
				scheda = line.split("|");
				LocalDate dataFineValidita = LocalDate.parse(scheda[5], formatter).plusWeeks(Integer.parseInt(scheda[6]));
				
				//facendo così le schede che terminano la validita' oggi compaiono tra le attuali
				boolean dataNONok = dataFineValidita.isBefore(LocalDate.now());
				if(scheda[1].equals(campi[1]) && !dataNONok) {	//idcliente uguale in scheda e campo passato
					outSock.writeUTF(line);	//viene passato al client la scheda 
					BufferedReader bf_inner = Utilities.apriFile("eserciziAlimenti.txt");
					String temp;
					while((temp = bf_inner.readLine()) != null) {
						String[] esercizioAlimento = new String[100];
						esercizioAlimento = temp.split("|");
						if(scheda[0].contentEquals(esercizioAlimento[0])) {
							outSock.writeUTF(temp);	//viene passato al client l'esercizio/alimento associato alla scheda
						}
					}
					
				}
			}
		} catch (IOException e) {
			}
		
	}

	//OSSERVAZIONE: lato Client si può capire quando arriva l'ultimo esercizio/alimento usando lenght. Scheda e pasto alimenti hanno lunghezza differente
	//richiesta = visualizzaStoricoPT
	private void visualizzaStoricoPT(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		BufferedReader bf_schede = Utilities.apriFile("schede.txt");
		String line;
		try {
			while((line = bf_schede.readLine()) != null) {
				String[] scheda = new String[100];
				scheda = line.split("|");
				outSock.writeUTF(line);	//viene passato al clientPT ogni scheda una per volta 
				BufferedReader bf_inner = Utilities.apriFile("eserciziAlimenti.txt");
				String temp;
				while((temp = bf_inner.readLine()) != null) {
					String[] esercizioAlimento = new String[100];
					esercizioAlimento = temp.split("|");
					if(scheda[0].contentEquals(esercizioAlimento[0])) {
						outSock.writeUTF(temp);	//viene passato al client l'esercizio/alimento associato alla scheda
					}
			
				}
			}
		} catch (IOException e) {
		
		}
	}

	//OSSERVAZIONE: lato Client si può capire quando arriva l'ultimo esercizio/alimento usando lenght. Scheda e pasto alimenti hanno lunghezza differente
	//richiesta = visualizzaStoricoCliente|idCliente
	private void visualizzaStoricoCliente(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		BufferedReader bf_schede = Utilities.apriFile("schede.txt");
		String line;
		try {
			while((line = bf_schede.readLine()) != null) {
				String[] scheda = new String[100];
				scheda = line.split("|");
				if(scheda[1].equals(campi[1])) {	//idcliente uguale in scheda e campo passato
					outSock.writeUTF(line);	//viene passato al client la scheda 
					BufferedReader bf_inner = Utilities.apriFile("eserciziAlimenti.txt");
					String temp;
					while((temp = bf_inner.readLine()) != null) {
						String[] esercizioAlimento = new String[100];
						esercizioAlimento = temp.split("|");
						if(scheda[0].contentEquals(esercizioAlimento[0])) {
							outSock.writeUTF(temp);	//viene passato al client l'esercizio/alimento associato alla scheda
						}
					}
					
				}
			}
		} catch (IOException e) {
			}
		
	}

	// richiesta = registrazione|username|password|'P' / 'C'|nome|cognome|mail|cf|data nascita|luogo nascita|indirizzo|telefono|codice id personal trainer  
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
					if(!utente[2].equals("C"))	//se non è cliente, passo direttamente la stringa che rappresenta l'utente 
						outSock.writeUTF(line);
					else {
						//BufferedReader bf_orari = Utilities.apriFile("orariIngressoUscita.txt");
						
					}
				}
			}
			if(!found) //segnalo errore passando una stringa di null, così qualunque campo letto non è valido
				outSock.writeUTF("null|null|null|null|null|null|null|null|null|null|null|null");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

