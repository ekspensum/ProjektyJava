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
import pl.dentistoffice.entity.WorkingWeek;


@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserRepositoryHibernateLocalDBImpl implements UserRepository {

	@Autowired
	private SessionFactory session;
	
	protected Session getSession() {
		return session.getCurrentSession();
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
	public void saveDoctor(Doctor doctor) {
		User user = doctor.getUser();
		getSession().persist(user);
		WorkingWeek workingWeek = doctor.getWorkingWeek();
		getSession().persist(workingWeek);
		getSession().persist(doctor);
	}

	@Override
	public void updateDoctor(Doctor doctor) {
		User user = doctor.getUser();
		getSession().saveOrUpdate(user);
		WorkingWeek workingWeek = doctor.getWorkingWeek();
		getSession().saveOrUpdate(workingWeek);
		getSession().saveOrUpdate(doctor);
	}

	@Override
	public Doctor readDoctor(int id) {
		return getSession().find(Doctor.class, id);
	}

	@Override
	public Doctor readDoctor(String username) {
		return getSession().createNamedQuery("findDoctorByUserName", Doctor.class).setParameter("username", username).getSingleResult();
	}

	@Override
	public List<Doctor> readAllDoctors() {
		return getSession().createQuery("from Doctor", Doctor.class).getResultList();
	}

	@Override
	public void savePatient(Patient patient) {
			User user = patient.getUser();
			getSession().persist(user);
			getSession().persist(patient);
	}

	@Override
	public void updatePatient(Patient patient) {
		User user = patient.getUser();
		getSession().saveOrUpdate(user);
		getSession().saveOrUpdate(patient);
	}

	@Override
	public Patient readPatient(int id) {
		return getSession().find(Patient.class, id);
	}

	@Override
	public Patient readPatient(String username) {
		return getSession().createNamedQuery("findPatientByUserName", Patient.class).setParameter("username", username).getSingleResult();
	}

	@Override
	public List<Patient> readAllPatients() {
		return getSession().createQuery("from Patient", Patient.class).getResultList();
	}

	@Override
	public void saveAssistant(Assistant assistant) {
			User user = assistant.getUser();
			getSession().persist(user);
			getSession().persist(assistant);
	}

	@Override
	public void updateAssistant(Assistant assistant) {
		User user = assistant.getUser();
		getSession().update(user);
		getSession().update(assistant);
	}

	@Override
	public Assistant readAssistant(int id) {
		return getSession().find(Assistant.class, id);
	}

	@Override
	public Assistant readAssistant(String username) {
		return getSession().createNamedQuery("findAssistantByUserName", Assistant.class).setParameter("username", username).getSingleResult();
	}

	@Override
	public List<Assistant> readAllAssistants() {
		return getSession().createQuery("from Assistant", Assistant.class).getResultList();
	}

	@Override
	public void saveAdmin(Admin admin) {
			User user = admin.getUser();
			getSession().persist(user);
			getSession().persist(admin);
	}

	@Override
	public void updateAdmin(Admin admin) {
		User user = admin.getUser();
		getSession().saveOrUpdate(user);
		getSession().saveOrUpdate(admin);
	}

	@Override
	public Admin readAdmin(int id) {
		return getSession().find(Admin.class, id);
	}

	@Override
	public Admin readAdmin(String username) {
		return null;
	}

	@Override
	public List<Admin> readAllAdmins() {
		return getSession().createQuery("from Admin", Admin.class).getResultList();
	}

}
