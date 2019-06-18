package pl.dentistoffice.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;

@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class TreatmentRepositoryImpl implements TreatmentRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
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
	public List<DentalTreatment> readDentalTreatment(String treatmentName) {
		return getSession().createNamedQuery("readDentalTreatmentByNme", DentalTreatment.class).setParameter("name", "%"+treatmentName+"%").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DentalTreatment> readAllDentalTreatment() {
		return getSession().createQuery("from DentalTreatment").getResultList();
	}

	@Override
	public boolean saveTreatmentCategory(String category) {
		try {
			TreatmentCategory treatmentCategory = new TreatmentCategory();
			treatmentCategory.setCategoryName(category);
			getSession().persist(treatmentCategory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateTreatmentCategory(int id, String categoryName) {
		try {
			TreatmentCategory treatmentCategory = getSession().find(TreatmentCategory.class, id);
			treatmentCategory.setCategoryName(categoryName);
			getSession().update(treatmentCategory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public TreatmentCategory readTreatmentCategory(int id) {
		return getSession().find(TreatmentCategory.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreatmentCategory> readAllTreatmentCategory() {
		return getSession().createQuery("from TreatmentCategory").getResultList();
	}

}
