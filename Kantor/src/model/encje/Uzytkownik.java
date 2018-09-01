package model.encje;

public class Uzytkownik {
	
	private int idUzytkownik;
	private String login;
	private String haslo;
	private int idRola;
	
	public int getIdUzytkownik() {
		return idUzytkownik;
	}
	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getHaslo() {
		return haslo;
	}
	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}
	public int getIdRola() {
		return idRola;
	}
	public void setIdRola(int idRola) {
		this.idRola = idRola;
	}	
}
