package model.encje;

import java.time.LocalDateTime;

public class Operator {
	private int idOperator;
	private String imie;
	private String nazwisko;
	private String pesel;
	private String telefon;
	private LocalDateTime dataDodania;
	private int idUzytkownik;
	private int idAdministrator;
	
	public int getIdOperator() {
		return idOperator;
	}
	public void setIdOperator(int idOperator) {
		this.idOperator = idOperator;
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
