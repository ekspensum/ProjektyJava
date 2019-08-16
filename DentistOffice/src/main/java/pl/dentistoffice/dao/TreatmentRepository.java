package pl.dentistoffice.dao;

import java.util.List;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;

public interface TreatmentRepository {
	
	boolean saveDentalTreatment(DentalTreatment treatment);
	boolean updateDentalTreatment(DentalTreatment treatment);
	DentalTreatment readDentalTreatment(int id);
	List<DentalTreatment> readAllDentalTreatment();
	
	boolean saveTreatmentCategory(TreatmentCategory treatmentCategory);
	boolean updateTreatmentCategory(TreatmentCategory treatmentCategory);
	TreatmentCategory readTreatmentCategory(int id);
	List<TreatmentCategory> readAllTreatmentCategory();
	List<TreatmentCategory> readAllTreatmentCategoryAboveFirstId();
	
	public boolean adjustSequenceGeneratorPrimaryKey();
}