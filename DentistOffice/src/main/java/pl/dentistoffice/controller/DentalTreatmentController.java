package pl.dentistoffice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.service.DentalTreatmentService;

@Controller
@PropertySource(value="classpath:/messages.properties")
public class DentalTreatmentController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private DentalTreatmentService dentalTreatmentService;
	
	@RequestMapping(path = "/control/addTreatment")
	public String addDentalTreatment(Model model) {
		model.addAttribute("dentalTreatment", new DentalTreatment());
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
		model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);
		return "/control/addTreatment";
	}
	
	@RequestMapping(path = "/control/addTreatment", method = RequestMethod.POST)
	public String addDentalTreatment(@Valid DentalTreatment dentalTreatment, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			if(dentalTreatmentService.addNewDentalTreatment(dentalTreatment)) {
				model.addAttribute("success", env.getProperty("successAddTreatment"));
				return "forward:/message/employee/success";					
			} else {
				model.addAttribute("exception", env.getProperty("exceptionAddTreatment"));
				return "forward:/message/employee/error";
			}
		} else {
			List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
			model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);
			return "/control/addTreatment";
		}
	}
	
	@RequestMapping(path = "/control/addTreatmentCategory")
	public String addTreatmentCategory(Model model) {
		model.addAttribute("treatmentCategory", new TreatmentCategory());
		return "/control/addTreatmentCategory";
	}
	
	@RequestMapping(path = "/control/addTreatmentCategory", method = RequestMethod.POST)
	public String addTreatmentCategory(@Valid TreatmentCategory treatmentCategory, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			if(dentalTreatmentService.addTreatmentCategory(treatmentCategory)) {
				model.addAttribute("success", env.getProperty("successAddTreatmentCategory"));
				return "forward:/message/employee/success";					
			} else {
				model.addAttribute("exception", env.getProperty("exceptionAddTreatmentCategory"));
				return "forward:/message/employee/error";
			}
		} else {
			return "/control/addTreatmentCategory";
		}
	}
}
