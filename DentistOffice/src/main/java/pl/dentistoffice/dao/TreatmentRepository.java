package pl.dentistoffice.dao;

import java.util.List;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;

public interface TreatmentRepository {
	
	boolean saveDentalTreatment(DentalTreatment treatment);
	DentalTreatment readDentalTreatment(int id);
	List<DentalTreatment> readDentalTreatment(String treatmentName);
	List<DentalTreatment> readAllDentalTreatment();
	
	boolean saveTreatmentCategory(TreatmentCategory treatmentCategory);
	TreatmentCategory readTreatmentCategory(int id);
	List<TreatmentCategory> readAllTreatmentCategory();
}