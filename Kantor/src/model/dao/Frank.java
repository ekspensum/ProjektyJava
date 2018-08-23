package model.dao;

import model.dao.Kursy;

public class Frank implements Waluta {

	private String znakKlasyBazowej = "CHF";
	private double mnoznikBid;
	private double mnoznikAsk;
	private Kursy kursy;
	private Dolar dolar;
	private Euro euro;
	private Zloty zloty;

	public Frank(double mnoznikBid, double mnoznikAsk, Kursy kursy) {
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

	@Override
	public double getAsk() {
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
