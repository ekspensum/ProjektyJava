package model.encje;

import java.time.LocalDateTime;

public class ZapisyRachUSD {
	
	private int idOperacji;
	private String tytulOperacji;
	private double kwotaWN;
	private double kwotaMA;
	private double kurs;
	private LocalDateTime dataOperacji;
	private int idRachunekUSD;
	
	public int getIdOperacji() {
		return idOperacji;
	}
	public void setIdOperacji(int idOperacji) {
		this.idOperacji = idOperacji;
	}
	public String getTytulOperacji() {
		return tytulOperacji;
	}
	public void setTytulOperacji(String tytulOperacji) {
		this.tytulOperacji = tytulOperacji;
	}
	public double getKwotaWN() {
		return kwotaWN;
	}
	public void setKwotaWN(double kwotaWN) {
		this.kwotaWN = kwotaWN;
	}
	public double getKwotaMA() {
		return kwotaMA;
	}
	public void setKwotaMA(double kwotaMA) {
		this.kwotaMA = kwotaMA;
	}
	public double getKurs() {
		return kurs;
	}
	public void setKurs(double kurs) {
		this.kurs = kurs;
	}
	public LocalDateTime getDataOperacji() {
		return dataOperacji;
	}
	public void setDataOperacji(LocalDateTime dataOperacji) {
		this.dataOperacji = dataOperacji;
	}
	public int getIdRachunekUSD() {
		return idRachunekUSD;
	}
	public void setIdRachunekUSD(int idRachunekUSD) {
		this.idRachunekUSD = idRachunekUSD;
	}
	
}
