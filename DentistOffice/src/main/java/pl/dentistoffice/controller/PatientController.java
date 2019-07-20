package pl.dentistoffice.controller;

import java.io.IOException;
import java.time.LocalDateTime;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.service.UserService;

@Controller
@SessionAttributes({"patient", "loggedPatient", "newPatient"})
public class PatientController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
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
					@RequestParam("photo") 
					MultipartFile photo,
					Model model) throws IOException {
		List<Role> patientRole = userService.getPatientRole();
		User user = patient.getUser();
		user.setRoles(patientRole);
		patient.setUser(user);
		patient.setPhoto(photo.getBytes());
		patient.setRegisteredDateTime(LocalDateTime.now());
		if(!result.hasErrors()) {
			userService.addNewPatient(patient);
			model.addAttribute("success", env.getProperty("successRegisterPatient"));
			return "forward:/success";
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
		return "/users/patient/assistant/edit";
	}
	
	@RequestMapping(path = "/users/patient/assistant/edit", method = RequestMethod.POST)
	public String editDataPatientByAssistant(
			@Valid
			Patient patient,
			BindingResult result, 
			@RequestParam("photo") 
			MultipartFile photo,
			Model model) throws IOException {

		patient.setPhoto(photo.getBytes());
		patient.setEditedDateTime(LocalDateTime.now());
		if (!result.hasErrors()) {
			userService.editPatient(patient);
			model.addAttribute("success", env.getProperty("successUpdatePatient"));
			return "forward:/success";
		} else {
			return "/users/patient/assistant/edit";
		}
	}
	
	@RequestMapping(path = "/users/patient/assistant/search")
	public String searchPatientByAssistant() {
		return "/users/patient/assistant/search";
	}
	
	@RequestMapping(path = "/users/patient/assistant/searchResult", method = RequestMethod.POST)
	public String searchPatientByAssistant(@RequestParam(name = "patientData") String patientData, RedirectAttributes redirectAttributes) {
		List<Patient> searchedPatientList = userService.searchPatient(patientData);
		redirectAttributes.addFlashAttribute("searchedPatientList", searchedPatientList);
		return "redirect:/users/patient/assistant/selectToEdit";
	}
	
	@RequestMapping(path = "/users/patient/register")
	public String selfRegistrationPatient(Model model) {
		model.addAttribute("newPatient", new Patient());
		return "/users/patient/register";
	}
	
	@RequestMapping(path = "/users/patient/register", method = RequestMethod.POST)
	public String selfRegistrationPatient(
					@Valid 
					@ModelAttribute(name = "newPatient")
					Patient newPatient, 			
					BindingResult result, 
					@RequestParam("photo") 
					MultipartFile photo,
					Model model) throws IOException {
		List<Role> patientRole = userService.getPatientRole();
		User user = newPatient.getUser();
		user.setRoles(patientRole);
		newPatient.setUser(user);
		newPatient.setPhoto(photo.getBytes());
		newPatient.setRegisteredDateTime(LocalDateTime.now());
		if(!result.hasErrors()) {
			userService.addNewPatient(newPatient);
			model.addAttribute("success", env.getProperty("successRegisterPatient"));
			return "forward:/success";
		} else {
			return "/users/patient/register";
		}
	}
	
	@RequestMapping(path = "/users/patient/edit")
	public String selfEditPatient(Model model) {
		Patient loggedPatient = userService.getLoggedPatient();
		model.addAttribute("loggedPatient", loggedPatient);
		return "/users/patient/edit";
	}
	
	@RequestMapping(path = "/users/patient/edit", method = RequestMethod.POST)
	public String selfEditDataPatient(
			@Valid
			@ModelAttribute(name = "loggedPatient")
			Patient loggedPatient,
			BindingResult result, 
			@RequestParam("photo") 
			MultipartFile photo,
			Model model) throws IOException {

		loggedPatient.setPhoto(photo.getBytes());
		loggedPatient.setEditedDateTime(LocalDateTime.now());
		if (!result.hasErrors()) {
			userService.editPatient(loggedPatient);
			model.addAttribute("success", env.getProperty("successUpdatePatient"));
			return "forward:/success";
		} else {
			return "/users/patient/edit";
		}
	}
}
