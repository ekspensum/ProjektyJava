package model.dao;

import model.dao.Kursy;

public class Zloty implements Waluta {

	private String znakKlasyBazowej = "PLN";
	private Double mnoznikBid;
	private Double mnoznikAsk;
	private Kursy kursy;
	private Dolar dolar;
	private Euro euro;
	private Frank frank;
	
	public Zloty(Double mnoznikBid, Double mnoznikAsk, Kursy kursy) {
		this.mnoznikBid = mnoznikBid;
		this.mnoznikAsk = mnoznikAsk;
		this.kursy = kursy;
	}

	public Zloty(Dolar dolar) {
		this.dolar = dolar;
	}
	
	public Zloty(Euro euro) {
		this.euro = euro;
	}

	public Zloty(Frank frank) {
		this.frank = frank;
	}

	public Double getMnoznikBid() {
		return mnoznikBid;
	}

	public Double getMnoznikAsk() {
		return mnoznikAsk;
	}

	public Kursy getKursy() {
		return kursy;
	}

	@Override
	public Double getBid() {
		if(dolar instanceof Dolar)
			return dolar.getMnoznikBid() * dolar.getKursy().getPln_usd();
		else if(euro instanceof Euro)
			return euro.getMnoznikBid() * euro.getKursy().getPln_eur();
		else if(frank instanceof Frank)
			return frank.getMnoznikBid() * frank.getKursy().getPln_chf();
		return null;
	}
	@Override
	public Double getAsk() {
		if(dolar instanceof Dolar)
			return dolar.getMnoznikAsk() * dolar.getKursy().getPln_usd();
		else if(euro instanceof Euro)
			return euro.getMnoznikAsk() * euro.getKursy().getPln_eur();
		else if(frank instanceof Frank)
			return frank.getMnoznikAsk() * frank.getKursy().getPln_chf();
		return null;
	}

	@Override
	public String getZnakKlasyKonwersji() {
		if(dolar instanceof Dolar)
			return dolar.getZnakKlasyBazowej();
		else if(euro instanceof Euro)
			return euro.getZnakKlasyBazowej();
		else if(frank instanceof Frank)
			return frank.getZnakKlasyBazowej();
		return null;
	}

	@Override
	public String getZnakKlasyBazowej() {
		return znakKlasyBazowej;
	}
	
}
