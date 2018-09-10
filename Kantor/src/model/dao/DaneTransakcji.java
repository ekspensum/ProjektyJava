package model.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DaneTransakcji {
	
	private int index;
	private int idUzytkownika;
	private int idRachunkuPLN;
	private int idRachunkuUSD;
	private int idRachunkuEUR;
	private int idRachunkuCHF;
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
	public int getIdRachunkuPLN() {
		return idRachunkuPLN;
	}
	public void setIdRachunkuPLN(int idRachunkuPLN) {
		this.idRachunkuPLN = idRachunkuPLN;
	}
	public int getIdRachunkuUSD() {
		return idRachunkuUSD;
	}
	public void setIdRachunkuUSD(int idRachunkuUSD) {
		this.idRachunkuUSD = idRachunkuUSD;
	}
	public int getIdRachunkuEUR() {
		return idRachunkuEUR;
	}
	public void setIdRachunkuEUR(int idRachunkuEUR) {
		this.idRachunkuEUR = idRachunkuEUR;
	}
	public int getIdRachunkuCHF() {
		return idRachunkuCHF;
	}
	public void setIdRachunkuCHF(int idRachunkuCHF) {
		this.idRachunkuCHF = idRachunkuCHF;
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
	
	public void wykonajTransakcje(DaneTransakcji dt, HttpServletRequest request, HttpServletResponse response) {
		
		if (dt != null) {
			Operacja[] op = new Operacja[3];
			op[0] = new OperacjaRachUSD();
			op[1] = new OperacjaRachEUR();
			op[2] = new OperacjaRachCHF();

			if ("Sprzedaj".equals(dt.getRodzaj()) && dt.getZnak().equals("USD")) {
				if (op[dt.getIndex()].sprzedaj(dt))
					request.setAttribute("komunikatSprzedajUSD",
							"Transakcja sprzedaży waluty " + dt.getZnak() + " zakończona powodzeniem.");
				else
					request.setAttribute("komunikatSprzedajUSD",
							"Transakcja sprzedaży waluty " + dt.getZnak() + " nie powiodła się.");

			} else if ("Kup".equals(dt.getRodzaj()) && dt.getZnak().equals("USD")) {
				if (op[dt.getIndex()].kup(dt))
					request.setAttribute("komunikatKupUSD",
							"Transakcja kupna waluty " + dt.getZnak() + " zakończona powodzeniem.");
				else
					request.setAttribute("komunikatKupUSD",
							"Transakcja kupna waluty " + dt.getZnak() + " nie powiodła się.");
			} else if ("Sprzedaj".equals(dt.getRodzaj()) && dt.getZnak().equals("EUR")) {
				if (op[dt.getIndex()].sprzedaj(dt))
					request.setAttribute("komunikatSprzedajEUR",
							"Transakcja sprzedaży waluty " + dt.getZnak() + " zakończona powodzeniem.");
				else
					request.setAttribute("komunikatSprzedajEUR",
							"Transakcja sprzedaży waluty " + dt.getZnak() + " nie powiodła się.");

			} else if ("Kup".equals(dt.getRodzaj()) && dt.getZnak().equals("EUR")) {
				if (op[dt.getIndex()].kup(dt))
					request.setAttribute("komunikatKupEUR",
							"Transakcja kupna waluty " + dt.getZnak() + " zakończona powodzeniem.");
				else
					request.setAttribute("komunikatKupEUR",
							"Transakcja kupna waluty " + dt.getZnak() + " nie powiodła się.");
			} else if ("Sprzedaj".equals(dt.getRodzaj()) && dt.getZnak().equals("CHF")) {
				if (op[dt.getIndex()].sprzedaj(dt))
					request.setAttribute("komunikatSprzedajCHF",
							"Transakcja sprzedaży waluty " + dt.getZnak() + " zakończona powodzeniem.");
				else
					request.setAttribute("komunikatSprzedajCHF",
							"Transakcja sprzedaży waluty " + dt.getZnak() + " nie powiodła się.");

			} else if ("Kup".equals(dt.getRodzaj()) && dt.getZnak().equals("CHF")) {
				if (op[dt.getIndex()].kup(dt))
					request.setAttribute("komunikatKupCHF",
							"Transakcja kupna waluty " + dt.getZnak() + " zakończona powodzeniem.");
				else
					request.setAttribute("komunikatKupCHF",
							"Transakcja kupna waluty " + dt.getZnak() + " nie powiodła się.");
			} else
				request.setAttribute("komunikat", "Transakcja nie powiodła się.");

		} else
			request.setAttribute("komunikat", "Transakcja nie powiodła się.");
	}
	
}
