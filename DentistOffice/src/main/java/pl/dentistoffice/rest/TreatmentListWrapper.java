package pl.dentistoffice.rest;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;

@Component
@Getter @Setter
@JsonSerialize
public class TreatmentListWrapper {

	private List<TreatmentCategory> treatmentCategoriesList;
	private List<DentalTreatment> dentalTreatmentsList;
	
	public TreatmentListWrapper() {
	}

	public TreatmentListWrapper(List<TreatmentCategory> treatmentCategoriesList, List<DentalTreatment> dentalTreatmentsList) {
		this.treatmentCategoriesList = treatmentCategoriesList;
		this.dentalTreatmentsList = dentalTreatmentsList;
	}
	
	
}
