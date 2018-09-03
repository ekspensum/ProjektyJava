package model.dao;

public class WynikiDodajUzytkownika {
	
	private boolean dodano;
	private String nazwa;
	private String login;
	private String regon;
	private String nip;
	private String nrRachunku;
	private String imie;
	private String nazwisko;
	private String pesel;
	
	public boolean isDodano() {
		return dodano;
	}
	public void setDodano(boolean dodano) {
		this.dodano = dodano;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getRegon() {
		return regon;
	}
	public void setRegon(String regon) {
		this.regon = regon;
	}
	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}
	public String getNrRachunku() {
		return nrRachunku;
	}
	public void setNrRachunku(String nrRachunku) {
		this.nrRachunku = nrRachunku;
	}
	public String getImie() {
		return imie;
	}
	public void setImie(String imie) {
		this.imie = imie;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	public String getPesel() {
		return pesel;
	}
	public void setPesel(String pesel) {
		this.pesel = pesel;
	}
}
