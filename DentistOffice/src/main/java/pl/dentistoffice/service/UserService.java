package pl.dentistoffice.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.WorkingTime;

@Service
public class UserService {
	
	@Autowired
	public UserRepository userRepository;

	public void setWorkingTime(int idDoctor, Map<LocalDate, Boolean> workingDate, Map<LocalTime, Boolean> workingHours) {
		Doctor doctor = userRepository.readDoctor(idDoctor);
		WorkingTime workingTime = doctor.getWorkingTime();
		Map<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>> workingTimeMap = workingTime.getWorkingTimeMap();
		workingTimeMap.put(workingDate, workingHours);
		workingTime.setWorkingTimeMap(workingTimeMap);
		doctor.setWorkingTime(workingTime);
		userRepository.saveDoctor(doctor);
	}
	
	public WorkingTime getWorkingTime(Doctor doctor) {
		return doctor.getWorkingTime();
	}
}
