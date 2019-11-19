package pl.dentistoffice.rest;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;

@Component
@Getter @Setter
public class TreatmentListWrapper {

	private List<TreatmentCategory> treatmentCategoriesList;
	private List<DentalTreatment> dentalTreatmentsList;
}
