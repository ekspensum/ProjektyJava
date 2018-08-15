package model.dao;

import model.dao.Kursy;

public class Dolar implements Waluta {
	
	private String znakKlasyBazowej = "USD";
	private Double mnoznikBid;
	private Double mnoznikAsk;
	private Kursy kursy;
	private Zloty zloty;
	private Euro euro;
	private Frank frank;

	public Dolar(Double mnoznikBid, Double mnoznikAsk, Kursy kursy) {
		this.mnoznikBid = mnoznikBid;
		this.mnoznikAsk = mnoznikAsk;
		this.kursy = kursy;
	}

	public Dolar(Frank frank) {
		this.frank = frank;
	}

	public Dolar(Euro euro) {
		this.euro = euro;
	}

	public Dolar(Zloty zloty) {
		this.zloty = zloty;
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
		if(zloty instanceof Zloty)
			return zloty.getMnoznikBid() * (1 / zloty.getKursy().getPln_usd());
		else if(euro instanceof Euro)
			return euro.getMnoznikBid() * (1 / euro.getKursy().getEur_usd());
		else if(frank instanceof Frank)
			return frank.getMnoznikBid() * (1 / frank.getKursy().getChf_usd());
		return null;
	}

	@Override
	public Double getAsk() {
		if(zloty instanceof Zloty)
			return zloty.getMnoznikAsk() * (1 / zloty.getKursy().getPln_usd());
		else if(euro instanceof Euro)
			return euro.getMnoznikAsk() * (1 / euro.getKursy().getEur_usd());
		else if(frank instanceof Frank)
			return frank.getMnoznikAsk() * (1/ frank.getKursy().getChf_usd());
		return null;
	}

	@Override
	public String getZnakKlasyKonwersji() {
		if(zloty instanceof Zloty)
			return zloty.getZnakKlasyBazowej();
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
