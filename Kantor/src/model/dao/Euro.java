package model.dao;

import model.dao.Kursy;

public class Euro implements Waluta {

	private String znakKlasyBazowej = "EUR";
	private double mnoznikBid;
	private double mnoznikAsk;
	private Kursy kursy;
	private Dolar dolar;
	private Zloty zloty;
	private Frank frank;

	public Euro(double mnoznikBid, double mnoznikAsk, Kursy kursy) {
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

	public double getMnoznikBid() {
		return mnoznikBid;
	}
	
	public double getMnoznikAsk() {
		return mnoznikAsk;
	}
	
	public Kursy getKursy() {
		return kursy;
	}

	@Override
	public double getBid() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("null")
	@Override
	public double getAsk() {
		// TODO Auto-generated method stub
		return (Double) null;
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
