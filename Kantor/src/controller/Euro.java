package controller;

import model.dao.ObslugaBD;

public class Euro implements Waluta {

	private String znak = "EUR";
	private Double kwota;
	private Double cena;
	
	public Euro(Double kwota, Double cena) {

		this.kwota = kwota;
		this.cena = cena;
	}

	@Override
	public void kup() {
		// TODO Auto-generated method stub
		ObslugaBD bd = new ObslugaBD();
	}

	@Override
	public void sprzedaj() {
		// TODO Auto-generated method stub
		
	}


	
	
}
