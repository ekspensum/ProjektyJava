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
public class TreatmentRepositoryHibernatePostgreSQLImpl implements TreatmentRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
//	for tests
	private Session session;
	
	public TreatmentRepositoryHibernatePostgreSQLImpl() {
	}

//	for tests
	public TreatmentRepositoryHibernatePostgreSQLImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.session = sessionFactory.getCurrentSession();
	}

	protected Session getSession() {
		this.session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public boolean saveDentalTreatment(DentalTreatment treatment) {
		try {
			getSession().persist(treatment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateDentalTreatment(DentalTreatment treatment) {
		try {
			getSession().saveOrUpdate(treatment);
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
	public List<DentalTreatment> readAllDentalTreatment() {
		return getSession().createQuery("from DentalTreatment", DentalTreatment.class).getResultList();
	}

	@Override
	public boolean saveTreatmentCategory(TreatmentCategory treatmentCategory) {
		try {
			getSession().persist(treatmentCategory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateTreatmentCategory(TreatmentCategory treatmentCategory) {
		try {
			getSession().saveOrUpdate(treatmentCategory);
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

	@Override
	public List<TreatmentCategory> readAllTreatmentCategory() {
		return getSession().createQuery("from TreatmentCategory", TreatmentCategory.class).getResultList();
	}
	
	@Override
	public List<TreatmentCategory> readAllTreatmentCategoryAboveFirstId() {
		return getSession().createNamedQuery("findTreatmentCategoryAboveFirstId", TreatmentCategory.class).getResultList();
	}

//	with restore Postgre database from backup
	@Override
	public boolean adjustSequenceGeneratorPrimaryKey() {
		try {
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('dentaltreatment', 'id'), max(id)) FROM dentaltreatment").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('treatmentcategory', 'id'), max(id)) FROM treatmentcategory").uniqueResult();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
