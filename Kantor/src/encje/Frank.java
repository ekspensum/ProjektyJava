package encje;

import dao.Kursy;

public class Frank implements Waluta {

	private String znakKlasyBazowej = "CHF";
	private Double mnoznikBid;
	private Double mnoznikAsk;
	private Kursy kursy;
	private Dolar dolar;
	private Euro euro;
	private Zloty zloty;

	public Frank(Double mnoznikBid, Double mnoznikAsk, Kursy kursy) {
		this.mnoznikBid = mnoznikBid;
		this.mnoznikAsk = mnoznikAsk;
		this.kursy = kursy;
	}

	public Frank(Zloty zloty) {
		this.zloty = zloty;
	}

	public Frank(Euro euro) {
		this.euro = euro;
	}

	public Frank(Dolar dolar) {
		this.dolar = dolar;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getAsk() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getZnakKlasyKonwersji() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getZnakKlasyBazowej() {
		return znakKlasyBazowej;
	}
	
	
}
