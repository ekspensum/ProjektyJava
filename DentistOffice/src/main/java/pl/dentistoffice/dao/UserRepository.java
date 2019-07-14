package pl.dentistoffice.dao;

import java.util.List;

import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Role;


public interface UserRepository {
	
	boolean saveRole(Role role);
	Role readRole(int id);
	List<Role> readAllRoles(); 
	
	void saveDoctor(Doctor doctor);
	void updateDoctor(Doctor doctor);
	Doctor readDoctor(int id);
	List<Doctor> readAllDoctors();

	void savePatient(Patient patient);
	void updatePatient(Patient patient);
	Patient readPatient(int id);
	List<Patient> readAllPatients();
	
	void saveAssistant(Assistant assistant);
	void updateAssistant(Assistant assistant);
	Assistant readAssistant(int id);
	List<Assistant> readAllAssistants();
	
	void saveAdmin(Admin admin);
	void updateAdmin(Admin admin);
	Admin readAdmin(int id);
	List<Admin> readAllAdmins();
}
