package Attori;
import java.time.LocalDateTime;

public class TesseraSocio {
	private int numero;
	private int saldoPunti;
	private LocalDateTime ultimoAggiornamento;
	
	public TesseraSocio(int num) {
		this.numero = num;
		this.saldoPunti = 0;
		ultimoAggiornamento = LocalDateTime.now();
	}
	
	//serve quando si istanzia un cliente gia' esistente e quindi gia' in possesso di una tessera socio
	public TesseraSocio(int num, int saldo, LocalDateTime ultimoAggiornamento) {
		this.numero = num;
		this.saldoPunti = saldo;
		this.ultimoAggiornamento = ultimoAggiornamento;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getSaldoPunti() {
		return saldoPunti;
	}

	public void setSaldoPunti(int saldoPunti) {
		this.saldoPunti = saldoPunti;
	}

	public LocalDateTime getUltimoAggiornamento() {
		return ultimoAggiornamento;
	}
	
	//Quando invochiamo il metodo per aggiornare passiamo LocalDateTime.now()
	public void setUltimoAggiornamento(LocalDateTime ultimoAggiornamento) {
		this.ultimoAggiornamento = ultimoAggiornamento;
	}
	
	
}
