package pl.dentistoffice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.service.DentalTreatmentService;

@Controller
@SessionAttributes(names = {"dentalTreatment", "treatmentCategory"})
public class DentalTreatmentController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private DentalTreatmentService dentalTreatmentService;

public DentalTreatmentController() {
	}

public DentalTreatmentController(Environment env, DentalTreatmentService dentalTreatmentService) {
		this.env = env;
		this.dentalTreatmentService = dentalTreatmentService;
	}

//	FOR ADD DENTAL TREATMENT
	@RequestMapping(path = "/control/addTreatment")
	public String addDentalTreatment(Model model) {
		model.addAttribute("dentalTreatment", new DentalTreatment());
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
		model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);
		return "/control/addTreatment";
	}
	
	@RequestMapping(path = "/control/addTreatment", method = RequestMethod.POST)
	public String addDentalTreatment(@Valid DentalTreatment dentalTreatment, BindingResult result, Model model) {
		if(!result.hasErrors() && dentalTreatment.getTreatmentCategory().get(0).getId() != dentalTreatment.getTreatmentCategory().get(1).getId()) {
			if(dentalTreatmentService.addNewDentalTreatment(dentalTreatment)) {
				model.addAttribute("success", env.getProperty("successAddTreatment"));
				return "forward:/message/employee/success";					
			} else {
				model.addAttribute("exception", env.getProperty("exceptionAddTreatment"));
				return "forward:/message/employee/error";
			}
		} else {
			if(dentalTreatment.getTreatmentCategory().get(0).getId() == dentalTreatment.getTreatmentCategory().get(1).getId()) {
				model.addAttribute("categoryError", env.getProperty("categoryError"));
			}
			List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
			model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);
			return "/control/addTreatment";
		}
	}

//	FOR EDIT DENTAL TREATMENT
	@RequestMapping(path = "/control/searchTreatment")
	public String searchDentalTreatment() {
		return "/control/searchTreatment";
	}
	
	@RequestMapping(path = "/control/searchResult", method = RequestMethod.POST)
	public String searchDentalTreatment(@RequestParam(name = "treatmentData") String treatmentData, RedirectAttributes redirectAttributes) {
		if(treatmentData.length() > 20) {
			String substringTreatmentData = treatmentData.substring(0, 20);
			List<DentalTreatment> searchedTreatmentList = dentalTreatmentService.searchDentalTreatment(substringTreatmentData);			
			redirectAttributes.addFlashAttribute("searchedTreatmentList", searchedTreatmentList);
		} else {
			List<DentalTreatment> searchedTreatmentList = dentalTreatmentService.searchDentalTreatment(treatmentData);					
			redirectAttributes.addFlashAttribute("searchedTreatmentList", searchedTreatmentList);
		}
		return "redirect:/control/selectTreatment";
	}
	
	@RequestMapping(path = "/control/selectTreatment")
	public String selectDentalTreatmentToEdit() {
		return "/control/selectTreatment";
	}

	@RequestMapping(path = "/control/selectedTreatmentToEdit", method = RequestMethod.POST)
	public String selectDentalTreatmentToEdit(@RequestParam("treatmentId") String treatmentId, RedirectAttributes redirectAttributes) {
		DentalTreatment selectedTreatment = dentalTreatmentService.getDentalTreatment(Integer.valueOf(treatmentId));
		redirectAttributes.addFlashAttribute("dentalTreatment", selectedTreatment);
		return "redirect:/control/editTreatment";
	}
	
	@RequestMapping(path = "/control/editTreatment")
	public String editDentalTreatment(@ModelAttribute(name = "dentalTreatment") DentalTreatment dentalTreatment, Model model) {
		model.addAttribute("dentalTreatment", dentalTreatment);
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
		model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);
		return "/control/editTreatment";
	}
	
	@RequestMapping(path = "/control/editTreatment", method = RequestMethod.POST)
	public String editDentalTreatment(@Valid DentalTreatment dentalTreatment, BindingResult result, Model model) {
		if(!result.hasErrors() && dentalTreatment.getTreatmentCategory().get(0).getId() != dentalTreatment.getTreatmentCategory().get(1).getId()) {
			if(dentalTreatmentService.editDentalTreatment(dentalTreatment)) {
				model.addAttribute("success", env.getProperty("successEditTreatment"));
				return "forward:/message/employee/success";				
			} else {
				model.addAttribute("exception", env.getProperty("exceptionEditTreatment"));
				return "forward:/message/employee/error";
			}
		} else {
			if(dentalTreatment.getTreatmentCategory().get(0).getId() == dentalTreatment.getTreatmentCategory().get(1).getId()) {
				model.addAttribute("categoryError", env.getProperty("categoryError"));
			}
			List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
			model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);
			return "/control/editTreatment";			
		}
	}
	
//	FOR ADD TREATMENT CATEGORY
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

//	FOR EDIT TREATMENT CATEGORY
	@RequestMapping(path = "/control/selectTreatmentCategory")
	public String selectTreatmentCategory(Model model) {
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesListToEdit();
		model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);
		return "/control/selectTreatmentCategory";
	}
	
	@RequestMapping(path = "/control/selectTreatmentCategory", method = RequestMethod.POST)
	public String selectTreatmentCategory(@RequestParam("categoryId") String categoryId, RedirectAttributes redirectAttributes) {
		TreatmentCategory selectedTreatmentCategory = dentalTreatmentService.getTreatmentCategory(Integer.valueOf(categoryId));
		redirectAttributes.addFlashAttribute("treatmentCategory", selectedTreatmentCategory);
		return "redirect:/control/editTreatmentCategory";
	}
	
	@RequestMapping(path = "/control/editTreatmentCategory")
	public String editTreatmentCategory(@ModelAttribute("treatmentCategory") TreatmentCategory treatmentCategory, Model model) {
		model.addAttribute("treatmentCategory", treatmentCategory);
		return "/control/editTreatmentCategory";
	}
	
	@RequestMapping(path = "/control/editTreatmentCategory", method = RequestMethod.POST)
	public String editTreatmentCategory(@Valid TreatmentCategory treatmentCategory, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			if(dentalTreatmentService.editTreatmentCategory(treatmentCategory)) {
				model.addAttribute("success", env.getProperty("successEditTreatmentCategory"));
				return "forward:/message/employee/success";				
			} else {
				model.addAttribute("exception", env.getProperty("exceptionEditTreatmentCategory"));
				return "forward:/message/employee/error";
			}
		} else {
			return "/control/editTreatmentCategory";			
		}
	}
}
