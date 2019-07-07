package pl.dentistoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dentistoffice.dao.TreatmentRepository;
import pl.dentistoffice.entity.DentalTreatment;

@Service
public class DentalTreatmentService {
	
	@Autowired
	private TreatmentRepository treatmentRepository;

	public List<DentalTreatment> getDentalTreatmentsList(){
		return treatmentRepository.readAllDentalTreatment();
	}
	
	public DentalTreatment getDentalTreatment(int id) {
		return treatmentRepository.readDentalTreatment(id);
	}
}
