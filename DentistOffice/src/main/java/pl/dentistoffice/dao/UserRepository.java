package pl.dentistoffice.dao;

import java.util.List;

import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Owner;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;


public interface UserRepository {
	
	boolean saveRole(Role role);
	Role readRole(int id);
	List<Role> readAllRoles();
	
	void saveDoctor(Doctor doctor);
	void updateDoctor(Doctor doctor);
	Doctor readDoctor(int id);
	Doctor readDoctor(String username);
	List<Doctor> readAllDoctors();

	void savePatient(Patient patient);
	void updatePatient(Patient patient);
	Patient readPatient(int id);
	Patient readPatient(String username);
	Patient readPatientByToken(String token);
	
	void saveAssistant(Assistant assistant);
	void updateAssistant(Assistant assistant);
	Assistant readAssistant(int id);
	Assistant readAssistant(String username);
	List<Assistant> readAllAssistants();
	
	void saveAdmin(Admin admin);
	void updateAdmin(Admin admin);
	Admin readAdmin(int id);
	Admin readAdmin(String username);
	List<Admin> readAllAdmins();
	
	void saveOwner(Owner owner);
	
	User readUser(String username);
	User readUser(int id);
	List<User> readAllUsers();
	
	public boolean adjustSequenceGeneratorPrimaryKey();
}
