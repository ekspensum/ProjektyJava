package model.dao;

import java.time.LocalDateTime;

public class UserZalogowany {
	private int idUzytkownik;
	private int idRola;
	private int idOperator;
	private int idAdministrator;
	private String rola;
	private String nazwaFirmy;
	private String imieKlienta;
	private String nazwiskoKlienta;
	private String imieOperatora;
	private String nazwiskoOperatora;
	private String imieAdministratora;
	private String nazwiskoAdministratora;
	private LocalDateTime dataLogowania;
	
	public int getIdUzytkownik() {
		return idUzytkownik;
	}
	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
	}
	public int getIdRola() {
		return idRola;
	}
	public void setIdRola(int idRola) {
		this.idRola = idRola;
	}
	public int getIdOperator() {
		return idOperator;
	}
	public void setIdOperator(int idOperator) {
		this.idOperator = idOperator;
	}
	public int getIdAdministrator() {
		return idAdministrator;
	}
	public void setIdAdministrator(int idAdministrator) {
		this.idAdministrator = idAdministrator;
	}
	public String getRola() {
		return rola;
	}
	public void setRola(String rola) {
		this.rola = rola;
	}
	public String getNazwaFirmy() {
		return nazwaFirmy;
	}
	public void setNazwaFirmy(String nazwaFirmy) {
		this.nazwaFirmy = nazwaFirmy;
	}
	public String getImieKlienta() {
		return imieKlienta;
	}
	public void setImieKlienta(String imieKlienta) {
		this.imieKlienta = imieKlienta;
	}
	public String getNazwiskoKlienta() {
		return nazwiskoKlienta;
	}
	public void setNazwiskoKlienta(String nazwiskoKlienta) {
		this.nazwiskoKlienta = nazwiskoKlienta;
	}
	public String getImieOperatora() {
		return imieOperatora;
	}
	public void setImieOperatora(String imieOperatora) {
		this.imieOperatora = imieOperatora;
	}
	public String getNazwiskoOperatora() {
		return nazwiskoOperatora;
	}
	public void setNazwiskoOperatora(String nazwiskoOperatora) {
		this.nazwiskoOperatora = nazwiskoOperatora;
	}
	public String getImieAdministratora() {
		return imieAdministratora;
	}
	public void setImieAdministratora(String imieAdministratora) {
		this.imieAdministratora = imieAdministratora;
	}
	public String getNazwiskoAdministratora() {
		return nazwiskoAdministratora;
	}
	public void setNazwiskoAdministratora(String nazwiskoAdministratora) {
		this.nazwiskoAdministratora = nazwiskoAdministratora;
	}
	public LocalDateTime getDataLogowania() {
		return dataLogowania;
	}
	public void setDataLogowania(LocalDateTime dataLogowania) {
		this.dataLogowania = dataLogowania;
	}
		
}
