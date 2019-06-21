package pl.dentistoffice.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class WorkingTime implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Lob
	byte [] workingTimeMapByte;
	
	@Transient
	private Map<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>> workingTimeMap;
	
	@OneToOne
	private User userLogged;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getWorkingTimeMapByte() {
		return workingTimeMapByte;
	}

	public void setWorkingTimeMapByte(byte[] workingTimeMapByte) {
		this.workingTimeMapByte = workingTimeMapByte;
	}

	@SuppressWarnings("unchecked")
	public Map<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>> getWorkingTimeMap() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(workingTimeMapByte);
		Map<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>> workingTimeMap = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			workingTimeMap = (Map<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>>) objectInputStream.readObject();
			inputStream.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return workingTimeMap;
	}

	public void setWorkingTimeMap(Map<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>> workingTimeMap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
			outputStream.writeObject(workingTimeMap);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.workingTimeMapByte = byteArrayOutputStream.toByteArray();
	}

	public User getUserLogged() {
		return userLogged;
	}

	public void setUserLogged(User userLogged) {
		this.userLogged = userLogged;
	}
	
}
