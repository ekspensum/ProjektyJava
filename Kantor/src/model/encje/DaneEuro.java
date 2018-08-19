package model.encje;

import java.time.LocalDateTime;

public class DaneEuro {
	
	private int idEuro;
	private String znak;
	private Double bid;
	private Double ask;
	private LocalDateTime dataDodania;
	private int idOperator;
	private int idUzytkownik;
	
	public int getIdEuro() {
		return idEuro;
	}
	public void setIdEuro(int idEuro) {
		this.idEuro = idEuro;
	}
	public String getZnak() {
		return znak;
	}
	public void setZnak(String znak) {
		this.znak = znak;
	}
	public Double getBid() {
		return bid;
	}
	public void setBid(Double bid) {
		this.bid = bid;
	}
	public Double getAsk() {
		return ask;
	}
	public void setAsk(Double ask) {
		this.ask = ask;
	}
	public LocalDateTime getDataDodania() {
		return dataDodania;
	}
	public void setDataDodania(LocalDateTime dataDodania) {
		this.dataDodania = dataDodania;
	}
	public int getIdOperator() {
		return idOperator;
	}
	public void setIdOperator(int idOperator) {
		this.idOperator = idOperator;
	}
	public int getIdUzytkownik() {
		return idUzytkownik;
	}
	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
	}
	
}
