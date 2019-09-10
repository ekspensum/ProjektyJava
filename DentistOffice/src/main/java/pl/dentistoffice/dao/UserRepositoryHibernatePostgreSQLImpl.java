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
import pl.dentistoffice.entity.Owner;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;


@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserRepositoryHibernatePostgreSQLImpl implements UserRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
//	for tests
	private Session session;
	
	public UserRepositoryHibernatePostgreSQLImpl() {
		super();
	}

//	for tests
	public UserRepositoryHibernatePostgreSQLImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
		this.session = sessionFactory.getCurrentSession();
	}

	protected Session getSession() {
		session = sessionFactory.getCurrentSession();
		return session;
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
		return getSession().createNamedQuery("findRoleWithoutOwner", Role.class).getResultList();
	}

	@Override
	public void saveDoctor(Doctor doctor) {
		getSession().persist(doctor);
	}

	@Override
	public void updateDoctor(Doctor doctor) {
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
			getSession().persist(patient);
	}

	@Override
	public void updatePatient(Patient patient) {
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
	public void saveAssistant(Assistant assistant) {
			getSession().persist(assistant);
	}

	@Override
	public void updateAssistant(Assistant assistant) {
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
			getSession().persist(admin);
	}

	@Override
	public void updateAdmin(Admin admin) {
		getSession().saveOrUpdate(admin);
	}

	@Override
	public Admin readAdmin(int id) {
		return getSession().find(Admin.class, id);
	}

	@Override
	public Admin readAdmin(String username) {
		return getSession().createNamedQuery("findAdminByUserName", Admin.class).setParameter("username", username).getSingleResult();
	}

	@Override
	public List<Admin> readAllAdmins() {
		return getSession().createQuery("from Admin", Admin.class).getResultList();
	}
	
	@Override
	public void saveOwner(Owner owner) {
		getSession().persist(owner);
	}

	@Override
	public User readUser(String username) {
		return getSession().createNamedQuery("findUserByUserName", User.class).setParameter("username", username).getSingleResult();
	}

	@Override
	public User readUser(int id) {
		return getSession().find(User.class, id);
	}

	@Override
	public List<User> readAllUsers() {
		return getSession().createQuery("from Users", User.class).getResultList();
	}

	//	with restore Postgre database from backup
	@Override
	public boolean adjustSequenceGeneratorPrimaryKey() {
		try {
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('Role', 'id'), max(id)) FROM Role").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('Users', 'id'), max(id)) FROM Users").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('Doctor', 'id'), max(id)) FROM Doctor").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('Patient', 'id'), max(id)) FROM Patient").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('Assistant', 'id'), max(id)) FROM Assistant").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('Admin', 'id'), max(id)) FROM Admin").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('Owner', 'id'), max(id)) FROM Owner").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('WorkingWeek', 'id'), max(id)) FROM WorkingWeek").uniqueResult();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
