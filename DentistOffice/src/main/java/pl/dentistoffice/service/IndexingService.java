package pl.dentistoffice.service;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IndexingService {

	@Autowired
	private SessionFactory session;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean createOrUpdateIndexesDatabase() {

		Session currentSession = session.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(currentSession);
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
}
