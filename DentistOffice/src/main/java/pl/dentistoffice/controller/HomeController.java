package pl.dentistoffice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.UserService;


@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DentalTreatmentService dentalTreatmentService;


	@RequestMapping(path="/")
	public String home(Model model) {

		
		return "home";
	}
	
	@RequestMapping(path="/doctors")
	public String doctors(Model model) {
		List<Doctor> allDoctors = userService.getAllDoctors();
		model.addAttribute("allDoctors", allDoctors);
		return "doctors";
	}
	
	@RequestMapping(path="/services")
	public String services(Model model) {
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
		model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);		
		return "services";
	}
	@RequestMapping(path="/services", method = RequestMethod.POST)
	public String services(@RequestParam("categoryId") String categoryId, Model model) {
		TreatmentCategory selectedTreatmentCategory = dentalTreatmentService.getTreatmentCategory(Integer.valueOf(categoryId));
		model.addAttribute("selectedTreatmentCategory", selectedTreatmentCategory);	
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
		model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);		
		return "services";
	}	
	
	
	@RequestMapping(path="/agenda")
	public String agenda(Model model) {

		
		return "agenda";
	}

}

