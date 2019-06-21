package pl.dentistoffice.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.dentistoffice.entity.Billing;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;

@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class VisitRepositoryHibernateLocalDBImpl implements VisitRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean saveVisit(Visit visit) {
		try {
			getSession().persist(visit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Visit readVisit(int id) {
		return getSession().find(Visit.class, id);
	}

	@Override
	public List<Visit> readVisits(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
		return getSession().createNamedQuery("readVisitsByDateTime", Visit.class).setParameter("dateTimeFrom", dateTimeFrom).setParameter("dateTimeTo", dateTimeTo).getResultList();
	}

	@Override
	public List<Visit> readVisits(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, VisitStatus visitStatus) {
		return getSession().createNamedQuery("readVisitsByDateTimeAndStatus", Visit.class).setParameter("dateTimeFrom", dateTimeFrom).setParameter("dateTimeTo", dateTimeTo).setParameter("visitStatus", visitStatus).getResultList();
	}

	@Override
	public List<Visit> readVisits(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, boolean visitConfirmation) {
		return getSession().createNamedQuery("readVisitsByDateTimeAndConfirmation", Visit.class).setParameter("dateTimeFrom", dateTimeFrom).setParameter("dateTimeTo", dateTimeTo).setParameter("visitConfirmation", visitConfirmation).getResultList();
	}

	@Override
	public List<Visit> readVisits(Patient patient, VisitStatus visitStatus) {
		return getSession().createNamedQuery("readVisitsByPatientAndStatus", Visit.class).setParameter("patient", patient).setParameter("visitStatus", visitStatus).getResultList();
	}

	@Override
	public List<Visit> readVisits(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, Doctor doctor) {
		return getSession().createNamedQuery("readVisitsByDateTimeAndDoctor", Visit.class).setParameter("dateTimeFrom", dateTimeFrom).setParameter("dateTimeTo", dateTimeTo).setParameter("doctor", doctor).getResultList();
	}

	@Override
	public boolean saveVistStatus(VisitStatus visitStaus) {
		try {
			getSession().persist(visitStaus);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public VisitStatus readVisitStaus(int id) {
		return getSession().find(VisitStatus.class, id);
	}

	@Override
	public List<VisitStatus> readAllVisitStatus() {
		return getSession().createQuery("from VisitStatus", VisitStatus.class).getResultList();
	}

	@Override
	public boolean saveBilling(Billing billing) {
		try {
			getSession().persist(billing);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Billing readBilling(int id) {
		return getSession().find(Billing.class, id);
	}

	@Override
	public List<Billing> readBilings(LocalDate dateFrom, LocalDate dateTo) {
		return getSession().createNamedQuery("readBilingsByDate", Billing.class).setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).getResultList();
	}

	@Override
	public List<Billing> readBilings(LocalDate dateFrom, LocalDate dateTo, Patient patient) {
		return getSession().createNamedQuery("readBilingsByDateAndPatient", Billing.class).setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).setParameter("patient", patient).getResultList();
	}

	@Override
	public List<Billing> readBilings(LocalDate dateFrom, LocalDate dateTo, Doctor doctor) {
		return getSession().createNamedQuery("readBilingsByDateAndDoctor", Billing.class).setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).setParameter("doctor", doctor).getResultList();
	}


}
