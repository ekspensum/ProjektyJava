package pl.dentistoffice.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.service.DentalTreatmentService;

@RestController
@RequestMapping(path = "/mobile")
public class DentalTreatmentRestController {
	
	@Autowired
	private DentalTreatmentService dentalTreatmentService;
	
	@Autowired
	private TreatmenCategorytListWrapper treatmentCategoryListWrapper;

	@GetMapping(path="/services")
	public TreatmenCategorytListWrapper services(Model model) {
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
		treatmentCategoryListWrapper.setTreatmentCategoriesList(treatmentCategoriesList);
		return treatmentCategoryListWrapper;
	}
}
