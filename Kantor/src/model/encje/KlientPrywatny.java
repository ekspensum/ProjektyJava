package model.encje;

import java.time.LocalDateTime;

public class KlientPrywatny {
	private int idKlientPrywatny;
	private String imie;
	private String nazwisko;
	private String pesel;
	private String kod;
	private String miasto;
	private String ulica;
	private String nrDomu;
	private String nrLokalu;
	private String telefon;
	private LocalDateTime dataDodania;
	private int idUzytkownik;
	private int idAdministrator;
	
	public int getIdKlientPrywatny() {
		return idKlientPrywatny;
	}
	public void setIdKlientPrywatny(int idKlientPrywatny) {
		this.idKlientPrywatny = idKlientPrywatny;
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
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
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
