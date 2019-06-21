package pl.dentistoffice.dao;

import java.util.List;

import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;


public interface UserRepository {

	boolean saveUser(User user);
	User readUser(int id);
	
	boolean saveRole(Role role);
	Role readRole(int id);
	List<Role> readAllRoles(); 
	
	boolean saveDoctor(Doctor doctor);
	Doctor readDoctor(int id);
	List<Doctor> readAllDoctors();

	boolean savePatient(Patient patient);
	Patient readPatient(int id);
	List<Patient> readAllPatients();
	
	boolean saveAssistant(Assistant assistant);
	Assistant readAssistant(int id);
	List<Assistant> readAllAssistants();
	
	boolean saveAdmin(Admin admin);
	Admin readAdmin(int id);
	List<Admin> readAllAdmins();
}
