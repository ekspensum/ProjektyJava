package model.dao;

import java.math.BigInteger;

public class WynikiDodajUzytkownika {
	
	private boolean dodano;
	private String nazwa;
	private String login;
	private Integer regon;
	private BigInteger nip;
	private String nrRachunku;
	
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
	public Integer getRegon() {
		return regon;
	}
	public void setRegon(Integer regon) {
		this.regon = regon;
	}
	public BigInteger getNip() {
		return nip;
	}
	public void setNip(BigInteger nip) {
		this.nip = nip;
	}
	public String getNrRachunku() {
		return nrRachunku;
	}
	public void setNrRachunku(String nrRachunku) {
		this.nrRachunku = nrRachunku;
	}

}
