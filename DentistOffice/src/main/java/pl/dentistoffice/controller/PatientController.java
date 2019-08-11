package pl.dentistoffice.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@Controller
@SessionAttributes({"patient", "image"})
public class PatientController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VisitService visitService;
	
	@RequestMapping(path = "/panels/patientPanel")
	public String patientPanel(Model model) {

		return "patientPanel";
	}
	
	@RequestMapping(path = "/users/patient/assistant/register")
	public String registrationPatientByAssistant(Model model) {
		model.addAttribute("patient", new Patient());
		return "/users/patient/assistant/register";
	}
	
	@RequestMapping(path = "/users/patient/assistant/register", method = RequestMethod.POST)
	public String registrationPatientByAssistant(
					@Valid 
					Patient patient, 			
					BindingResult result, 
					Model model,
					@RequestParam("photo") 
					MultipartFile photo) throws IOException {
		
		patient.setPhoto(photo.getBytes());
		if(!result.hasErrors()) {
			userService.addNewPatient(patient);
			model.addAttribute("success", env.getProperty("successRegisterPatient"));
			return "forward:/message/employee/success";
		} else {
			return "/users/patient/assistant/register";
		}
	}
	
	@RequestMapping(path = "/users/patient/assistant/selectToEdit")
	public String selectPatientToEditByAssistant() {		
		return "/users/patient/assistant/selectToEdit";
	}
	
	@RequestMapping(path = "/users/patient/assistant/selectToEdit", method = RequestMethod.POST)
	public String selectPatientToEditByAssistant(@RequestParam("patientId") String patientId, RedirectAttributes redirectAttributes) {		
		Patient patient = userService.getPatient(Integer.valueOf(patientId));
		redirectAttributes.addFlashAttribute("patient", patient);
		return "redirect:/users/patient/assistant/edit";
	}
	
	@RequestMapping(path = "/users/patient/assistant/edit")
	public String editPatientByAssistant(@ModelAttribute("patient") Patient patient, Model model) {
		model.addAttribute("patient", patient);
		model.addAttribute("image", patient.getPhoto());
		return "/users/patient/assistant/edit";
	}
	
	@RequestMapping(path = "/users/patient/assistant/edit", method = RequestMethod.POST)
	public String editDataPatientByAssistant(
			@Valid
			Patient patient,
			BindingResult result, 
			Model model,
			@RequestParam("photo") 
			MultipartFile photo,
			@SessionAttribute(name = "image")
			byte [] image) throws IOException {
		
		if(photo.getBytes().length == 0) {
			patient.setPhoto(image);			
		}
		if (!result.hasErrors()) {
			userService.editPatient(patient);
			model.addAttribute("success", env.getProperty("successUpdatePatient"));
			return "forward:/message/employee/success";
		} else {
			return "/users/patient/assistant/edit";
		}
	}
	
	@RequestMapping(path = "/users/patient/assistant/searchPatient")
	public String searchPatientByAssistant() {
		return "/users/patient/assistant/searchPatient";
	}
	
	@RequestMapping(path = "/users/patient/assistant/searchResult", method = RequestMethod.POST)
	public String searchPatientByAssistant(@RequestParam(name = "patientData") String patientData, RedirectAttributes redirectAttributes) {
		if(patientData.length() > 20) {
			String substringPatientData = patientData.substring(0, 20);
			List<Patient> searchedPatientList = userService.searchPatient(substringPatientData);			
			redirectAttributes.addFlashAttribute("searchedPatientList", searchedPatientList);
		} else {
			List<Patient> searchedPatientList = userService.searchPatient(patientData);			
			redirectAttributes.addFlashAttribute("searchedPatientList", searchedPatientList);
		}
		return "redirect:/users/patient/assistant/selectToEdit";
	}
	
	@RequestMapping(path = "/users/patient/register")
	public String selfRegistrationPatient(Model model) {
		model.addAttribute("patient", new Patient());
		return "/users/patient/register";
	}
	
	@RequestMapping(path = "/users/patient/register", method = RequestMethod.POST)
	public String selfRegistrationPatient(
					@Valid 
					@ModelAttribute(name = "patient")
					Patient patient, 			
					BindingResult result, 
					Model model,
					@RequestParam("photo") 
					MultipartFile photo) throws IOException {
		
		patient.setPhoto(photo.getBytes());
		if(!result.hasErrors()) {
			userService.addNewPatient(patient);
			model.addAttribute("success", env.getProperty("successRegisterPatient"));
			return "forward:/message/patient/success";
		} else {
			return "/users/patient/register";
		}
	}
	
	@RequestMapping(path = "/users/patient/edit")
	public String selfEditPatient(Model model) {
		Patient patient = userService.getLoggedPatient();
		model.addAttribute("patient", patient);
		model.addAttribute("image", patient.getPhoto());
		return "/users/patient/edit";
	}
	
	@RequestMapping(path = "/users/patient/edit", method = RequestMethod.POST)
	public String selfEditDataPatient(
			@Valid
			@ModelAttribute(name = "patient")
			Patient patient,
			BindingResult result,
			Model model,
			@RequestParam("photo") 
			MultipartFile photo,
			@SessionAttribute(name = "image")
			byte [] image) throws IOException {

		if(photo.getBytes().length == 0) {
			patient.setPhoto(image);			
		}
		if (!result.hasErrors()) {
			userService.editPatient(patient);
			model.addAttribute("success", env.getProperty("successUpdatePatient"));
			return "forward:/message/patient/success";
		} else {
			return "/users/patient/edit";
		}
	}
	
	@RequestMapping(path = "/visit/patient/myVisits")
	public String showMyVisits(Model model) {
		VisitStatus defaultVisitStatus = visitService.getVisitStatus(2);
		Patient loggedPatient = userService.getLoggedPatient();
		List<Visit> visitsByPatientAndStatus = visitService.getVisitsByPatientAndStatus(loggedPatient, defaultVisitStatus);
		List<VisitStatus> visitStatusList = visitService.getVisitStatusList();
		model.addAttribute("visitStatusList", visitStatusList);
		model.addAttribute("defaultVisitStatus", defaultVisitStatus);
		model.addAttribute("visitsByPatientAndStatus", visitsByPatientAndStatus);
		model.addAttribute("patient", loggedPatient);
		return "/visit/patient/myVisits";
	}
	
	@RequestMapping(path = "/visit/patient/myVisits", method = RequestMethod.POST)
	public String showMyVisits(@SessionAttribute(name = "patient") Patient patient, @RequestParam("statusId") String statusId, Model model) {
		VisitStatus actualVisitStatus = visitService.getVisitStatus(Integer.valueOf(statusId));
		List<Visit> visitsByPatientAndStatus = visitService.getVisitsByPatientAndStatus(patient, actualVisitStatus);
		List<VisitStatus> visitStatusList = visitService.getVisitStatusList();
		model.addAttribute("visitStatusList", visitStatusList);
		model.addAttribute("actualVisitStatus", actualVisitStatus);
		model.addAttribute("visitsByPatientAndStatus", visitsByPatientAndStatus);
		return "/visit/patient/myVisits";
	}
}
