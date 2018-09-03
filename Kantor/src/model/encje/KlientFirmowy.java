package model.encje;

import java.time.LocalDateTime;

public class KlientFirmowy {
	private int idKlientFirmowy;
	private String nazwa;
	private String regon;
	private String nip;
	private String kod;
	private String miasto;
	private String ulica;
	private String nrDomu;
	private String nrLokalu;
	private String imiePracownika;
	private String nazwiskoPracownika;
	private String telefonPracownika;
	private LocalDateTime dataDodania;
	private int idUzytkownik;
	private int idAdministrator;
	
	public int getIdKlientFirmowy() {
		return idKlientFirmowy;
	}
	public void setIdKlientFirmowy(int idKlientFirmowy) {
		this.idKlientFirmowy = idKlientFirmowy;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
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
	public String getKod() {
		return kod;
	}
	public void setKod(String kod) {
		this.kod = kod;
	}
	public String getMiasto() {
		return miasto;
	}
	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	public String getNrDomu() {
		return nrDomu;
	}
	public void setNrDomu(String nrDomu) {
		this.nrDomu = nrDomu;
	}
	public String getNrLokalu() {
		return nrLokalu;
	}
	public void setNrLokalu(String nrLokalu) {
		this.nrLokalu = nrLokalu;
	}
	public String getImiePracownika() {
		return imiePracownika;
	}
	public void setImiePracownika(String imiePracownika) {
		this.imiePracownika = imiePracownika;
	}
	public String getNazwiskoPracownika() {
		return nazwiskoPracownika;
	}
	public void setNazwiskoPracownika(String nazwiskoPracownika) {
		this.nazwiskoPracownika = nazwiskoPracownika;
	}
	public String getTelefonPracownika() {
		return telefonPracownika;
	}
	public void setTelefonPracownika(String telefonPracownika) {
		this.telefonPracownika = telefonPracownika;
	}
	public LocalDateTime getDataDodania() {
		return dataDodania;
	}
	public void setDataDodania(LocalDateTime dataDodania) {
		this.dataDodania = dataDodania;
	}
	public int getIdUzytkownik() {
		return idUzytkownik;
	}
	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
	}
	public int getIdAdministrator() {
		return idAdministrator;
	}
	public void setIdAdministrator(int idAdministrator) {
		this.idAdministrator = idAdministrator;
	}

}
