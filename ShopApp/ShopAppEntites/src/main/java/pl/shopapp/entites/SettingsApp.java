package pl.shopapp.entites;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: SettingsApp
 *
 */
@Entity

public class SettingsApp implements Serializable {
	   
	@Id
	private int id;
	private int minCharInPass;
	private int maxCharInPass;
	private int upperCaseInPass;
	private int numbersInPass;
	private int minCharInLogin;
	private int maxCharInLogin;
	private int sessionTime;
	private int idUser;
	private LocalDateTime dateTime;
	
	private static final long serialVersionUID = 1L;

	public SettingsApp() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public int getMinCharInPass() {
		return this.minCharInPass;
	}

	public void setMinCharInPass(int minCharInPass) {
		this.minCharInPass = minCharInPass;
	}
	public int getMaxCharInPass() {
		return maxCharInPass;
	}
	public void setMaxCharInPass(int maxCharInPass) {
		this.maxCharInPass = maxCharInPass;
	}
	public int getUpperCaseInPass() {
		return upperCaseInPass;
	}
	public void setUpperCaseInPass(int upperCaseInPass) {
		this.upperCaseInPass = upperCaseInPass;
	}
	public int getNumbersInPass() {
		return numbersInPass;
	}
	public void setNumbersInPass(int numbersInPass) {
		this.numbersInPass = numbersInPass;
	}
	public int getMinCharInLogin() {
		return minCharInLogin;
	}
	public void setMinCharInLogin(int minCharInLogin) {
		this.minCharInLogin = minCharInLogin;
	}
	public int getMaxCharInLogin() {
		return maxCharInLogin;
	}
	public void setMaxCharInLogin(int maxCharInLogin) {
		this.maxCharInLogin = maxCharInLogin;
	}
	public int getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(int sessionTime) {
		this.sessionTime = sessionTime;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
   
}
