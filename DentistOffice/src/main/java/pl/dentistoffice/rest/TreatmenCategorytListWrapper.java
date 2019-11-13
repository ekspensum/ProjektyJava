package pl.dentistoffice.rest;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import pl.dentistoffice.entity.TreatmentCategory;

@Component
@Getter @Setter
public class TreatmenCategorytListWrapper {

	private List<TreatmentCategory> treatmentCategoriesList;
	
}
