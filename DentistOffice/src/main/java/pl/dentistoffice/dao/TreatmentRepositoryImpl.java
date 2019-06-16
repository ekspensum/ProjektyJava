package pl.dentistoffice.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;

@Repository
public class TreatmentRepositoryImpl implements TreatmentRepository {
	
	@Autowired
	private SessionFactory session;
	
	protected Session getSession() {
		return session.getCurrentSession();
	}

	@Override
	public boolean saveDentalTreatment(String name, String description, double price, List<TreatmentCategory> categories) {
		try {
			DentalTreatment treatment = new DentalTreatment();
			treatment.setName(name);
			treatment.setDescription(description);
			treatment.setPrice(price);
			treatment.setTreatmentCategory(categories);
			getSession().persist(treatment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateDentalTreatment(int id, String name, String description, double price, List<TreatmentCategory> categories) {
		try {
			DentalTreatment treatment = getSession().find(DentalTreatment.class, id);
			treatment.setName(name);
			treatment.setDescription(description);
			treatment.setPrice(price);
			treatment.setTreatmentCategory(categories);
			getSession().persist(treatment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public DentalTreatment readDentalTreatment(int id) {
		return getSession().find(DentalTreatment.class, id);
	}

	@Override
	public DentalTreatment readDentalTreatment(String treatmentName) {
		getSession().createNamedQuery("readDentalTreatmentByNme", DentalTreatment.class).setParameter("name", treatmentName).getResultList();
		return null;
	}

	@Override
	public List<DentalTreatment> readAllDentalTreatment() {
		return getSession().createNamedQuery("readAllDentalTreatment", DentalTreatment.class).getResultList();
	}

	@Override
	public boolean saveTreatmentCategory(String category) {
		return false;
	}

	@Override
	public boolean updateTreatmentCategory(String category) {
		return false;
	}

	@Override
	public TreatmentCategory readTreatmentCategory(int id) {
		return null;
	}

	@Override
	public List<TreatmentCategory> readAllTreatmentCategory() {
		return null;
	}

	
}
