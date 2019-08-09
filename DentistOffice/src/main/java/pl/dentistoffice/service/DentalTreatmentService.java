package pl.dentistoffice.service;

import java.util.Comparator;
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
		List<DentalTreatment> allDentalTreatments = treatmentRepository.readAllDentalTreatment();
		allDentalTreatments.sort(new Comparator<DentalTreatment>() {

			@Override
			public int compare(DentalTreatment o1, DentalTreatment o2) {
				return o1.getId() - o2.getId();
			}
		});
		return allDentalTreatments;
	}
	
	public DentalTreatment getDentalTreatment(int id) {
		return treatmentRepository.readDentalTreatment(id);
	}
}
