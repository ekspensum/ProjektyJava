package pl.dentistoffice.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.service.DentalTreatmentService;

@RestController
@RequestMapping(path = "/mobile")
public class DentalTreatmentRestController {
	
	@Autowired
	private DentalTreatmentService dentalTreatmentService;
	@Autowired
	private TreatmentListWrapper treatmentListWrapper;
	@Autowired
	private AuthRestController authRestController;
	
	public DentalTreatmentRestController() {
	}

	public DentalTreatmentRestController(DentalTreatmentService dentalTreatmentService,
			TreatmentListWrapper treatmentListWrapper, AuthRestController authRestController) {
		this.dentalTreatmentService = dentalTreatmentService;
		this.treatmentListWrapper = treatmentListWrapper;
		this.authRestController = authRestController;
	}

	//	for aspect
	public AuthRestController getAuthRestController() {
		return authRestController;
	}

	@GetMapping(path="/services")
	public TreatmentListWrapper services() {
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
		treatmentListWrapper.setTreatmentCategoriesList(treatmentCategoriesList);
		return treatmentListWrapper;
	}
	
	@GetMapping(path="/treatments")
	public TreatmentListWrapper treatments() {
		List<DentalTreatment> dentalTreatmentsList = dentalTreatmentService.getDentalTreatmentsList();
		treatmentListWrapper.setDentalTreatmentsList(dentalTreatmentsList);
		return treatmentListWrapper;
	}
}
