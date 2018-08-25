package model.encje;

import java.time.LocalDateTime;

public class ZapisyRachPLN {
	
	private int idOperacji;
	private String tytulOperacji;
	private double kwotaWN;
	private double kwotaMA;
	private LocalDateTime dataOperacji;
	private int idRachunekPLN;
	private int idOperacjiUSD;
	
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
	public LocalDateTime getDataOperacji() {
		return dataOperacji;
	}
	public void setDataOperacji(LocalDateTime dataOperacji) {
		this.dataOperacji = dataOperacji;
	}
	public int getIdRachunekPLN() {
		return idRachunekPLN;
	}
	public void setIdRachunekPLN(int idRachunekPLN) {
		this.idRachunekPLN = idRachunekPLN;
	}
	public int getIdOperacjiUSD() {
		return idOperacjiUSD;
	}
	public void setIdOperacjiUSD(int idOperacjiUSD) {
		this.idOperacjiUSD = idOperacjiUSD;
	}
	
}
