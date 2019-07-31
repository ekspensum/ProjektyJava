package pl.dentistoffice.service;

import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.CacheMode;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dentistoffice.entity.Patient;


@Service
public class HibernateSearchService {

	private SessionFactory session;
	
	@Autowired
	public HibernateSearchService(SessionFactory session) {
		super();
		this.session = session;
		initializeHibernateSearch();
	}

	public boolean initializeHibernateSearch() {

		FullTextSession fullTextSession = Search.getFullTextSession(session.openSession());
		try {
			fullTextSession.createIndexer()
			.batchSizeToLoadObjects(15)
			.cacheMode(CacheMode.NORMAL)
			.threadsToLoadObjects(3)
			.startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Patient> searchPatientNamePeselStreetPhoneByKeywordQuery(String text){
		FullTextSession fullTextSession = Search.getFullTextSession(session.openSession());
		
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


}
