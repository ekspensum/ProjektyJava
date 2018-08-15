package model.dao;

import java.time.LocalDateTime;


public class OperacjaRachPLN implements Operacja {

	private int idKlienta;
	private String rola;
	private String tytulOperacji;
	private String znakKlasyBazowej;
	private String znakKlasyKonwersji;
	private Double kwotaPLN;
	private Double kwotaWaluty;
	private Double kurs;
	private LocalDateTime dataOperacji;
	
	public OperacjaRachPLN() {
	}

	public OperacjaRachPLN(int idKlienta, String rola, String tytulOperacji, String znakKlasyBazowej, String znakKlasyKonwersji,
			Double kwotaPLN, Double kwotaWaluty, Double kurs, LocalDateTime dataOperacji) {
		this.idKlienta = idKlienta;
		this.rola = rola;
		this.tytulOperacji = tytulOperacji;
		this.znakKlasyBazowej = znakKlasyBazowej;
		this.znakKlasyKonwersji = znakKlasyKonwersji;
		this.kwotaPLN = kwotaPLN;
		this.kwotaWaluty = kwotaWaluty;
		this.kurs = kurs;
		this.dataOperacji = dataOperacji;
	}

	public int getIdKlienta() {
		return idKlienta;
	}

	public String getRola() {
		return rola;
	}

	public String getTytulOperacji() {
		return tytulOperacji;
	}

	public String getZnakKlasyBazowej() {
		return znakKlasyBazowej;
	}

	public String getZnakKlasyKonwersji() {
		return znakKlasyKonwersji;
	}

	public Double getKwotaPLN() {
		return kwotaPLN;
	}

	public Double getKurs() {
		return kurs;
	}

	public LocalDateTime getDataOperacji() {
		return dataOperacji;
	}

	public Double getKwotaWaluty() {
		return kwotaWaluty;
	}

	@Override
	public Operacja kupuj(Double kwota, Waluta waluta) {
		if(waluta instanceof Dolar) {
			idKlienta = 1;
			rola = "Klient firmowy";
			tytulOperacji = "Zakup waluty " + waluta.getZnakKlasyKonwersji();
			znakKlasyBazowej = waluta.getZnakKlasyBazowej();
			znakKlasyKonwersji = waluta.getZnakKlasyKonwersji();
			kwotaPLN = kwota * waluta.getAsk();
			kwotaWaluty = kwota;
			kurs = waluta.getAsk();
			dataOperacji = LocalDateTime.now();
			return new OperacjaRachPLN(idKlienta, rola, tytulOperacji, znakKlasyBazowej, znakKlasyKonwersji, kwotaPLN, kwotaWaluty, kurs, dataOperacji);
		}

		
		return null;
	}

	@Override
	public Operacja sprzedaj(Double kwota, Waluta waluta) {
		// TODO Auto-generated method stub
		return null;
	}

}
