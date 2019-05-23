package Acquisto;

import java.util.ArrayList;
import java.util.List;

import Attori.Cliente;
import Attori.TesseraSocio;

public class AcquistoController {
	private ProdottiPalestra prodotti;
	private ScontiPalestra sconti;
	private Cliente cliente;
	private TesseraSocio tes;
	private List<Prodotto> prodottiSelezionati;
	private final String emailPalestra;
	
	public AcquistoController(Cliente c) {
		this.prodotti = new ProdottiPalestra();
		this.sconti = new ScontiPalestra();
		this.cliente = c;
		this.tes = c.getTes();
		this.prodottiSelezionati = new ArrayList<Prodotto>();
		this.emailPalestra = "ciao@gmail.com";
	}
	
	public List<Prodotto> applicaFiltro() {
		List<Prodotto> prodottiFiltrati = new ArrayList<Prodotto>();
		
		return prodottiFiltrati;
	}
	
	
	
	
	
	
	
	
		
	public int getSaldoPunti() {
		return tes.getSaldoPunti();
	}
	
	public List<Sconto> sconti() {
		return sconti.getListaSconti();
	}
	
	public List<Sconto> getScontiDisponibili(int puntiSullaTessera, float sommaSpesa) {
		ArrayList<Sconto> disp = new ArrayList<Sconto>();
		for (Sconto sconto : sconti()) {
			if(sconto.isAvailable(puntiSullaTessera, sommaSpesa))
				disp.add(sconto);
		}
		return disp;
	}
	
	public List<Prodotto> aggiungiProdotto(Prodotto p) {
		prodottiSelezionati.add(p);
		return prodottiSelezionati;
	}
	
	public float applicaSconto(Sconto s) {
		return (float) 1.0;
	}
}
