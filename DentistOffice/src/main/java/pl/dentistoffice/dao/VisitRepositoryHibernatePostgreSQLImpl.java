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
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.entity.VisitTreatmentComment;

@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class VisitRepositoryHibernatePostgreSQLImpl implements VisitRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
//	for tests
	private Session session;
	
	public VisitRepositoryHibernatePostgreSQLImpl() {
		super();
	}

//	for tests
	public VisitRepositoryHibernatePostgreSQLImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
		this.session = sessionFactory.getCurrentSession();
	}

	protected Session getSession() {
		this.session = sessionFactory.getCurrentSession();
		return session;
	}
	
	@Override
	public void saveVisit(Visit visit) {
		getSession().persist(visit);
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
	public List<Visit> readVisits(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, VisitStatus visitStatus, Doctor doctor) {
		return getSession().createNamedQuery("readVisitsByDateTimeAndStatusAndDoctor", Visit.class).setParameter("dateTimeFrom", dateTimeFrom).setParameter("dateTimeTo", dateTimeTo).setParameter("visitStatus", visitStatus).setParameter("doctor", doctor).getResultList();
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
	public List<Visit> readVisits(Patient patient) {
		return getSession().createNamedQuery("readVisitsByPatient", Visit.class).setParameter("patient", patient).getResultList();
	}

	@Override
	public List<Visit> readVisits(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, Doctor doctor) {
		return getSession().createNamedQuery("readVisitsByDateTimeAndDoctor", Visit.class).setParameter("dateTimeFrom", dateTimeFrom).setParameter("dateTimeTo", dateTimeTo).setParameter("doctor", doctor).getResultList();
	}

	@Override
	public boolean updateVisitOnFinalize(Visit visit) {
		try {
			getSession().saveOrUpdate(visit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void removeVisit(Visit visit) {
		List<VisitTreatmentComment> visitTreatmentCommentList = visit.getVisitTreatmentComment();
		for (VisitTreatmentComment visitTreatmentComment : visitTreatmentCommentList) {
			getSession().createNativeQuery("delete from visit_visittreatmentcomment where visit_id = :visit_id AND visittreatmentcomment_id = :visittreatmentcomment_id").setParameter("visit_id", visit.getId()).setParameter("visittreatmentcomment_id", visitTreatmentComment.getId()).executeUpdate();
			getSession().createNativeQuery("delete from VisitTreatmentComment where id = :id").setParameter("id", visitTreatmentComment.getId()).executeUpdate();
		}
		List<DentalTreatment> treatments = visit.getTreatments();
		for (DentalTreatment dentalTreatment : treatments) {
			getSession().createNativeQuery("delete from visit_dentaltreatment where visits_id = :visits_id AND treatments_id = :treatments_id").setParameter("visits_id", visit.getId()).setParameter("treatments_id", dentalTreatment.getId()).executeUpdate();
		}
		getSession().delete(visit);
	}

	@Override
	public boolean isScheduledVisit(LocalDateTime localDateTime, Doctor doctor) {
		List<Visit> resultList = getSession().createNamedQuery("isScheduledVisitsByDateTime", Visit.class).setParameter("dateTimeVisit", localDateTime).setParameter("doctor", doctor).getResultList();
		if(resultList.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean saveVisitStatus(VisitStatus visitStaus) {
		try {
			getSession().persist(visitStaus);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public VisitStatus readVisitStatus(int id) {
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

	@Override
	public void saveVisitTreatmentComment(VisitTreatmentComment visitTreatmentComment) {
	}

//	with restore Postgre database from backup
	@Override
	public boolean adjustSequenceGeneratorPrimaryKey() {
		try {
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('Visit', 'id'), max(id)) FROM Visit").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('VisitStatus', 'id'), max(id)) FROM VisitStatus").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('Billing', 'id'), max(id)) FROM Billing").uniqueResult();
			getSession().createNativeQuery("SELECT setval(pg_get_serial_sequence('VisitTreatmentComment', 'id'), max(id)) FROM VisitTreatmentComment").uniqueResult();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
