package model.dao;

public class DaneTransakcji {
	
	private int index;
	private int idUzytkownika;
	private double kwota;
	private double cena;
	private String znak;
	private String rodzaj;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getIdUzytkownika() {
		return idUzytkownika;
	}
	public void setIdUzytkownika(int idUzytkownika) {
		this.idUzytkownika = idUzytkownika;
	}
	public double getKwota() {
		return kwota;
	}
	public void setKwota(double kwota) {
		this.kwota = kwota;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getZnak() {
		return znak;
	}
	public void setZnak(String znak) {
		this.znak = znak;
	}
	public String getRodzaj() {
		return rodzaj;
	}
	public void setRodzaj(String rodzaj) {
		this.rodzaj = rodzaj;
	}

}
