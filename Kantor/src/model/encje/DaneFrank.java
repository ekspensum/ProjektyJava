package model.encje;

import java.time.LocalDateTime;

public class DaneFrank {
	
	private int idFrank;
	private String znak;
	private Double bid;
	private Double ask;
	private LocalDateTime dataDodania;
	private int idOperator;
	private String imieOperatora;
	private String nazwiskoOperatora;
	
	public int getIdFrank() {
		return idFrank;
	}
	public void setIdFrank(int idFrank) {
		this.idFrank = idFrank;
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
	
}
