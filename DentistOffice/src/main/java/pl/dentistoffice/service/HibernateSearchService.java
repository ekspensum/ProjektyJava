package pl.dentistoffice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.CacheMode;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Visit;


@Service
public class HibernateSearchService {

	private SessionFactory session;
	
	@Autowired
	public HibernateSearchService(SessionFactory session) {
		super();
		this.session = session;
		FullTextSession fullTextSession = Search.getFullTextSession(session.openSession());
		try {
			fullTextSession.createIndexer()
			.batchSizeToLoadObjects(15)
			.cacheMode(CacheMode.NORMAL)
			.threadsToLoadObjects(3)
			.startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean updateIndex() {

		FullTextSession fullTextSession = Search.getFullTextSession(session.openSession());
		try {
//			fullTextSession.getSearchFactory().optimize();
			fullTextSession.createIndexer()
			.batchSizeToLoadObjects(15)
			.cacheMode(CacheMode.NORMAL)
			.threadsToLoadObjects(3)
			.startAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Patient> searchPatientNamePeselStreetPhoneByKeywordQuery(String text){
		FullTextSession fullTextSession = Search.getFullTextSession(session.getCurrentSession());
		
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
									.buildQueryBuilder()
									.forEntity(Patient.class)
									.get();
		
		Query luceneQuery = queryBuilder
							.keyword()
							.fuzzy()
				            .withEditDistanceUpTo(2)
				            .withPrefixLength(0)
							.onFields("lastName", "pesel", "street", "phone")
							.matching(text)
							.createQuery();
		
		return fullTextSession.createFullTextQuery(luceneQuery, Patient.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Visit> searchVisitDateAndStatusByKeywordQuery(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo){
		FullTextSession fullTextSession = Search.getFullTextSession(session.getCurrentSession());
		
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
									.buildQueryBuilder()
									.forEntity(Visit.class)
									.get();
		
		Query luceneQuery = queryBuilder.bool()
							.must(queryBuilder
							.range()
							.onField("visitDateTime")
							.from(dateTimeFrom)
							.to(dateTimeTo)
							.createQuery())
							.must(queryBuilder
							.keyword()
							.onField("status.statusName")
							.matching("PLANNED")
							.createQuery()).createQuery();
		
		return fullTextSession.createFullTextQuery(luceneQuery, Visit.class).getResultList();
	}
}
