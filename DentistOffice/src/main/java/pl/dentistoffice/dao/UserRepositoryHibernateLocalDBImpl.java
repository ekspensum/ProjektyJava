package pl.dentistoffice.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.entity.WorkingTime;


@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserRepositoryHibernateLocalDBImpl implements UserRepository {

	@Autowired
	private SessionFactory session;
	
	protected Session getSession() {
		return session.getCurrentSession();
	}
	
	@Override
	public boolean saveUser(User user) {
		try {
			getSession().persist(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public User readUser(int id) {
		return getSession().find(User.class, id);
	}

	
	@Override
	public boolean saveRole(Role role) {
		try {
			getSession().persist(role);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Role readRole(int id) {
		return getSession().find(Role.class, id);
	}

	@Override
	public List<Role> readAllRoles() {
		return getSession().createQuery("from Role", Role.class).getResultList();
	}

	@Override
	public boolean saveDoctor(Doctor doctor) {
		try {
			User user = doctor.getUser();
			getSession().persist(user);
			WorkingTime workingTime = doctor.getWorkingTime();
			getSession().persist(workingTime);
			getSession().persist(doctor);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Doctor readDoctor(int id) {
		return getSession().find(Doctor.class, id);
	}

	@Override
	public List<Doctor> readAllDoctors() {
		return getSession().createQuery("from Doctor", Doctor.class).getResultList();
	}

	@Override
	public boolean savePatient(Patient patient) {
		try {
			User user = patient.getUser();
			getSession().persist(user);
			getSession().persist(patient);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Patient readPatient(int id) {
		return getSession().find(Patient.class, id);
	}

	@Override
	public List<Patient> readAllPatients() {
		return getSession().createQuery("from Patient", Patient.class).getResultList();
	}

	@Override
	public boolean saveAssistant(Assistant assistant) {
		try {
			User user = assistant.getUser();
			getSession().persist(user);
			getSession().persist(assistant);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Assistant readAssistant(int id) {
		return getSession().find(Assistant.class, id);
	}

	@Override
	public List<Assistant> readAllAssistants() {
		return getSession().createQuery("from Assistant", Assistant.class).getResultList();
	}

	@Override
	public boolean saveAdmin(Admin admin) {
		try {
			User user = admin.getUser();
			getSession().persist(user);
			getSession().persist(admin);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Admin readAdmin(int id) {
		return getSession().find(Admin.class, id);
	}

	@Override
	public List<Admin> readAllAdmins() {
		return getSession().createQuery("from Admin", Admin.class).getResultList();
	}

}
