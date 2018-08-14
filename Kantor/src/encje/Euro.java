package encje;

import dao.Kursy;

public class Euro implements Waluta {

	private String znakKlasyBazowej = "EUR";
	private Double mnoznikBid;
	private Double mnoznikAsk;
	private Kursy kursy;
	private Dolar dolar;
	private Zloty zloty;
	private Frank frank;

	public Euro(Double mnoznikBid, Double mnoznikAsk, Kursy kursy) {
		this.mnoznikBid = mnoznikBid;
		this.mnoznikAsk = mnoznikAsk;
		this.kursy = kursy;
	}
	
	public Euro(Frank frank) {
		this.frank = frank;
	}

	public Euro(Zloty zloty) {
		this.zloty = zloty;
	}

	public Euro(Dolar dolar) {
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
