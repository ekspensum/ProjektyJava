package pl.dentistoffice.dao;

import java.util.List;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;

public interface TreatmentRepository {
	
	boolean saveDentalTreatment(String name, String description, double price, List<TreatmentCategory> categories);
	boolean updateDentalTreatment(int id, String name, String description, double price, List<TreatmentCategory> categories);
	DentalTreatment readDentalTreatment(int id);
	List<DentalTreatment> readDentalTreatment(String treatmentName);
	List<DentalTreatment> readAllDentalTreatment();
	
	boolean saveTreatmentCategory(String category);
	boolean updateTreatmentCategory(int id, String categoryName);
	TreatmentCategory readTreatmentCategory(int id);
	List<TreatmentCategory> readAllTreatmentCategory();
}