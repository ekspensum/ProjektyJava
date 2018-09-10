package model.dao;

import java.time.LocalDateTime;

public class Statystyka {

	private int idUzytkownika;
	private String nrRachunkuPLN;
	private String tytulOperacji;
	private double kwotaWN_PLN;
	private double kwotaMA_PLN;
	private LocalDateTime dataOperacji;
	private String nrRachunkuUSD;
	private double kwotaWN_USD;
	private double kwotaMA_USD;
	private double kursUSD;	
	private String nrRachunkuEUR;
	private double kwotaWN_EUR;
	private double kwotaMA_EUR;
	private double kursEUR;
	private String nrRachunkuCHF;	
	private double kwotaWN_CHF;
	private double kwotaMA_CHF;
	private double kursCHF;
	private String waluta;
	private LocalDateTime dataOd;
	private LocalDateTime dataDo;
	
	public int getIdUzytkownika() {
		return idUzytkownika;
	}
	public void setIdUzytkownika(int idUzytkownika) {
		this.idUzytkownika = idUzytkownika;
	}
	public String getNrRachunkuPLN() {
		return nrRachunkuPLN;
	}
	public void setNrRachunkuPLN(String nrRachunkuPLN) {
		this.nrRachunkuPLN = nrRachunkuPLN;
	}
	public String getTytulOperacji() {
		return tytulOperacji;
	}
	public void setTytulOperacji(String tytulOperacji) {
		this.tytulOperacji = tytulOperacji;
	}
	public double getKwotaWN_PLN() {
		return kwotaWN_PLN;
	}
	public void setKwotaWN_PLN(double kwotaWN_PLN) {
		this.kwotaWN_PLN = kwotaWN_PLN;
	}
	public double getKwotaMA_PLN() {
		return kwotaMA_PLN;
	}
	public void setKwotaMA_PLN(double kwotaMA_PLN) {
		this.kwotaMA_PLN = kwotaMA_PLN;
	}
	public LocalDateTime getDataOperacji() {
		return dataOperacji;
	}
	public void setDataOperacji(LocalDateTime dataOperacji) {
		this.dataOperacji = dataOperacji;
	}
	public String getNrRachunkuUSD() {
		return nrRachunkuUSD;
	}
	public void setNrRachunkuUSD(String nrRachunkuUSD) {
		this.nrRachunkuUSD = nrRachunkuUSD;
	}
	public double getKwotaWN_USD() {
		return kwotaWN_USD;
	}
	public void setKwotaWN_USD(double kwotaWN_USD) {
		this.kwotaWN_USD = kwotaWN_USD;
	}
	public double getKwotaMA_USD() {
		return kwotaMA_USD;
	}
	public void setKwotaMA_USD(double kwotaMA_USD) {
		this.kwotaMA_USD = kwotaMA_USD;
	}
	public double getKursUSD() {
		return kursUSD;
	}
	public void setKursUSD(double kursUSD) {
		this.kursUSD = kursUSD;
	}
	public String getNrRachunkuEUR() {
		return nrRachunkuEUR;
	}
	public void setNrRachunkuEUR(String nrRachunkuEUR) {
		this.nrRachunkuEUR = nrRachunkuEUR;
	}
	public double getKwotaWN_EUR() {
		return kwotaWN_EUR;
	}
	public void setKwotaWN_EUR(double kwotaWN_EUR) {
		this.kwotaWN_EUR = kwotaWN_EUR;
	}
	public double getKwotaMA_EUR() {
		return kwotaMA_EUR;
	}
	public void setKwotaMA_EUR(double kwotaMA_EUR) {
		this.kwotaMA_EUR = kwotaMA_EUR;
	}
	public double getKursEUR() {
		return kursEUR;
	}
	public void setKursEUR(double kursEUR) {
		this.kursEUR = kursEUR;
	}
	public String getNrRachunkuCHF() {
		return nrRachunkuCHF;
	}
	public void setNrRachunkuCHF(String nrRachunkuCHF) {
		this.nrRachunkuCHF = nrRachunkuCHF;
	}
	public double getKwotaWN_CHF() {
		return kwotaWN_CHF;
	}
	public void setKwotaWN_CHF(double kwotaWN_CHF) {
		this.kwotaWN_CHF = kwotaWN_CHF;
	}
	public double getKwotaMA_CHF() {
		return kwotaMA_CHF;
	}
	public void setKwotaMA_CHF(double kwotaMA_CHF) {
		this.kwotaMA_CHF = kwotaMA_CHF;
	}
	public double getKursCHF() {
		return kursCHF;
	}
	public void setKursCHF(double kursCHF) {
		this.kursCHF = kursCHF;
	}
	public String getWaluta() {
		return waluta;
	}
	public void setWaluta(String waluta) {
		this.waluta = waluta;
	}
	public LocalDateTime getDataOd() {
		return dataOd;
	}
	public void setDataOd(LocalDateTime dataOd) {
		this.dataOd = dataOd;
	}
	public LocalDateTime getDataDo() {
		return dataDo;
	}
	public void setDataDo(LocalDateTime dataDo) {
		this.dataDo = dataDo;
	}
	
}
