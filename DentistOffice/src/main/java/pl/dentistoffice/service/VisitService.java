package pl.dentistoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dentistoffice.dao.VisitRepository;
import pl.dentistoffice.entity.Visit;

@Service
public class VisitService {
	
	@Autowired
	public VisitRepository visitRepository;

	public void addNewVisit(Visit visit) {
		visitRepository.saveVisit(visit);
	}
}
