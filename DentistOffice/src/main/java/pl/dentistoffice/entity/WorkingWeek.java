package pl.dentistoffice.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
public class WorkingWeek implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Type(type="org.hibernate.type.BinaryType")
	byte [] workingWeekMapByte;
	
	@Transient
	private Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap;
	
	@OneToOne
	private User userLogged;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getWorkingWeekMapByte() {
		return workingWeekMapByte;
	}

	public void setWorkingWeekMapByte(byte[] workingWeekMapByte) {
		this.workingWeekMapByte = workingWeekMapByte;
	}

	@SuppressWarnings("unchecked")
	public Map<DayOfWeek, Map<LocalTime, Boolean>> getWorkingWeekMap() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(workingWeekMapByte);
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			workingWeekMap = (Map<DayOfWeek, Map<LocalTime, Boolean>>) objectInputStream.readObject();
			inputStream.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return workingWeekMap;
	}

	public void setWorkingWeekMap(Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
			outputStream.writeObject(workingWeekMap);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.workingWeekMapByte = byteArrayOutputStream.toByteArray();
	}

	public User getUserLogged() {
		return userLogged;
	}

	public void setUserLogged(User userLogged) {
		this.userLogged = userLogged;
	}
	
}
