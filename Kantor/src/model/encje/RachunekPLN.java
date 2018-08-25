package model.encje;

import java.time.LocalDateTime;

public class RachunekPLN {
	
	private int idRachunekPLN;
	private String nrRachunku;
	private String znak;
	private LocalDateTime dataUtworzenia;
	private int idKlientFirmowy;
	private int idKlientPrywatny;
	private int idAdministrator;
	
	public int getIdRachunekPLN() {
		return idRachunekPLN;
	}
	public void setIdRachunekPLN(int idRachunekPLN) {
		this.idRachunekPLN = idRachunekPLN;
	}
	public String getNrRachunku() {
		return nrRachunku;
	}
	public void setNrRachunku(String nrRachunku) {
		this.nrRachunku = nrRachunku;
	}
	public String getZnak() {
		return znak;
	}
	public void setZnak(String znak) {
		this.znak = znak;
	}
	public LocalDateTime getDataUtworzenia() {
		return dataUtworzenia;
	}
	public void setDataUtworzenia(LocalDateTime dataUtworzenia) {
		this.dataUtworzenia = dataUtworzenia;
	}
	public int getIdKlientFirmowy() {
		return idKlientFirmowy;
	}
	public void setIdKlientFirmowy(int idKlientFirmowy) {
		this.idKlientFirmowy = idKlientFirmowy;
	}
	public int getIdKlientPrywatny() {
		return idKlientPrywatny;
	}
	public void setIdKlientPrywatny(int idKlientPrywatny) {
		this.idKlientPrywatny = idKlientPrywatny;
	}
	public int getIdAdministrator() {
		return idAdministrator;
	}
	public void setIdAdministrator(int idAdministrator) {
		this.idAdministrator = idAdministrator;
	}
	
}
