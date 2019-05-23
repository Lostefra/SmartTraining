package controller;
import java.net.*;
import java.io.*;

public class MainClientCliente {

	public static void main(String[] args) throws IOException {
		//fissi localhost 10101
		InetAddress addr = InetAddress.getByName("localhost");
		int port = 10101;

		

		// Creazione socket di I/O
		Socket socket = null;
		DataInputStream inSock = null;
		DataOutputStream outSock = null;
		BufferedReader stdIn = null;

		try {
			socket = new Socket(addr, port);
			socket.setSoTimeout(30000); // Solo se richiesto
			System.out.println("Creata la socket: " + socket);
			inSock = new DataInputStream(socket.getInputStream());
			outSock = new DataOutputStream(socket.getOutputStream());
		} catch (IOException ioe) {
			System.out.println("Problemi nella creazione degli stream su socket: ");
			ioe.printStackTrace();
			System.exit(4);
		}
//	QUI VA CORPO DEL CLIENT, VANNO GESTITE INTERAZIONI CON VIEW
/*		String cycleMessage = "\n^D(Unix)/^Z(Win) per terminare, altrimenti X: ";
		System.out.print(cycleMessage);
		String richiesta;

		while ((richiesta = stdIn.readLine()) != null) {
			outSock.writeUTF("Provola");
			String result = inSock.readUTF();

			System.out.print(cycleMessage);
		}
*/
		// Libero le risorse
		try {
			outSock.flush();
			socket.shutdownOutput();
			socket.shutdownInput();
		} catch (IOException e) {
			System.out.println("Problemi nella chiusura della socket: ");
			e.printStackTrace();
			System.exit(5);
		} catch (Exception e) {
			System.out.println("Error: ");
			e.printStackTrace();
			System.exit(5);
		}
	}
}
