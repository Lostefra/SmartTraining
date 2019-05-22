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
				String risposta="";

				while ((richiesta = inSock.readUTF()) != null) {
					System.out.println("ServerThread: ricevuto " + richiesta);
					String[] campi = richiesta.split("|");
					// Elaborazione risposta
					switch(campi[0]) {
					case "login": login(campi, inSock, outSock);
						break;
					default:
					}
					outSock.writeUTF(risposta);
					System.out.println("ServerThread: mandato " + risposta);
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

	private void login(String[] campi, DataInputStream inSock, DataOutputStream outSock) {
		
		
	}
}

