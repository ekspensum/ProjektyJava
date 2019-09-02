package pl.dentistoffice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import pl.dentistoffice.service.ActivationService;
import pl.dentistoffice.service.ReCaptchaService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@Controller
@SessionAttributes({"patient", "image", "editUserId"})
public class PatientController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	private ActivationService activationService;
	
	@Autowired
	private ReCaptchaService reCaptchaService;
	
	
	@RequestMapping(path = "/panels/patientPanel")
	public String patientPanel(Model model) {
		return "patientPanel";
	}
	
//	FOR SEARCHING PATIENT BY ASSISTANT
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
		return "redirect:/users/patient/assistant/selectPatient";
	}
	
	@RequestMapping(path = "/users/patient/assistant/selectPatient")
	public String selectPatientToEditByAssistant() {		
		return "/users/patient/assistant/selectPatient";
	}
	
//	FOR REGISTER PATIENT BY ASSISTANT
	@RequestMapping(path = "/users/patient/assistant/register")
	public String registrationPatientByAssistant(Model model) {
		model.addAttribute("patient", new Patient());
		return "/users/patient/assistant/register";
	}
	
	@RequestMapping(path = "/users/patient/assistant/register", method = RequestMethod.POST)
	public String registrationPatientByAssistant(
												@Valid Patient patient, 			
												BindingResult result, 
												Model model,
												@RequestParam("photo") MultipartFile photo,
												HttpServletRequest servletRequest
												) throws IOException {
		
		boolean dinstinctLogin = userService.checkDinstinctLoginWithRegisterUser(patient.getUser().getUsername());
		patient.setPhoto(photo.getBytes());

		if(!result.hasErrors() && dinstinctLogin) {		
			try {
				userService.addNewPatient(patient, servletRequest.getContextPath());
				model.addAttribute("success", env.getProperty("successRegisterPatient"));
				return "forward:/message/employee/success";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("exception", env.getProperty("exceptionRegisterPatient"));
				return "forward:/message/patient/error";
			}
		} else {
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			return "/users/patient/assistant/register";
		}
	}
	
//	FOR EDIT DATA PATIENT BY ASSISTANT	
	@RequestMapping(path = "/users/patient/assistant/selectedPatientToEdit", method = RequestMethod.POST)
	public String selectPatientToEditByAssistant(@RequestParam("patientId") String patientId, RedirectAttributes redirectAttributes) {		
		Patient patient = userService.getPatient(Integer.valueOf(patientId));
		redirectAttributes.addFlashAttribute("patient", patient);
		redirectAttributes.addFlashAttribute("editUserId", patient.getUser().getId());
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
											@Valid Patient patient,
											BindingResult result, 
											Model model,
											@SessionAttribute("editUserId") int editUserId,
											@RequestParam("photo") MultipartFile photo,
											@SessionAttribute(name = "image", required = false) byte [] image
											) throws IOException {
		
		boolean dinstinctLogin = userService.checkDinstinctLoginWithEditUser(patient.getUser().getUsername(), editUserId);
		if(photo.getBytes().length == 0) {
			patient.setPhoto(image);			
		}
		if (!result.hasErrors() && dinstinctLogin) {
			try {
				userService.editPatient(patient);
				model.addAttribute("success", env.getProperty("successUpdatePatient"));
				return "forward:/message/employee/success";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("exception", env.getProperty("exceptionUpdatePatient"));
				return "forward:/message/employee/success";
			}
		} else {
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			return "/users/patient/assistant/edit";
		}
	}

//	FOR BROWSE PATIENT DATA BY ASSISTANT
	@RequestMapping(path = "/users/patient/assistant/selectedPatientToShow", method = RequestMethod.POST)
	public String selectPatientForBrowseByAssistant(@RequestParam("patientId") String patientId, RedirectAttributes redirectAttributes) {		
		Patient patient = userService.getPatient(Integer.valueOf(patientId));
		redirectAttributes.addFlashAttribute("patient", patient);
		return "redirect:/users/patient/assistant/showPatient";
	}
		
	@RequestMapping(path = "/users/patient/assistant/showPatient")
	public String showPatientForBrowseByAssistant(@ModelAttribute(name = "patient") Patient patient, Model model) {
		VisitStatus defaultVisitStatus = visitService.getVisitStatus(2);
		List<Visit> visitsByPatientAndStatus = visitService.getVisitsByPatientAndStatus(patient, defaultVisitStatus);
		List<VisitStatus> visitStatusList = visitService.getVisitStatusList();
		model.addAttribute("visitStatusList", visitStatusList);
		model.addAttribute("defaultVisitStatus", defaultVisitStatus);
		model.addAttribute("visitsByPatientAndStatus", visitsByPatientAndStatus);
		model.addAttribute("patient", patient);
		return "/users/patient/assistant/showPatient";
	}

	@RequestMapping(path = "/users/patient/assistant/showPatient", method = RequestMethod.POST)
	public String showPatientForBrowseByAssistant(@ModelAttribute(name = "patient") Patient patient, @RequestParam("statusId") String statusId, Model model) {
		VisitStatus actualVisitStatus = visitService.getVisitStatus(Integer.valueOf(statusId));
		List<Visit> visitsByPatientAndStatus = visitService.getVisitsByPatientAndStatus(patient, actualVisitStatus);
		List<VisitStatus> visitStatusList = visitService.getVisitStatusList();
		model.addAttribute("visitStatusList", visitStatusList);
		model.addAttribute("actualVisitStatus", actualVisitStatus);
		model.addAttribute("visitsByPatientAndStatus", visitsByPatientAndStatus);
		model.addAttribute("patient", patient);
		return "/users/patient/assistant/showPatient";
	}
	
//	FOR SELF REGISTER PATIENT
	@RequestMapping(path = "/users/patient/register")
	public String selfRegistrationPatient(Model model) {
		model.addAttribute("patient", new Patient());
		return "/users/patient/register";
	}
	
	@RequestMapping(path = "/users/patient/register", method = RequestMethod.POST)
	public String selfRegistrationPatient(
										  @Valid Patient patient, 			
										  BindingResult result, 
										  Model model,
										  @RequestParam("photo") MultipartFile photo,
										  HttpServletRequest servletRequest,
										  @RequestParam(name = "g-recaptcha-response") String reCaptchaResponse
										  ) throws IOException {
		
		boolean dinstinctLogin = userService.checkDinstinctLoginWithRegisterUser(patient.getUser().getUsername());
		patient.setPhoto(photo.getBytes());
		
		boolean verifyreCaptcha = reCaptchaService.verify(reCaptchaResponse);
		
		if(!result.hasErrors() && dinstinctLogin && verifyreCaptcha) {
			try {
				userService.addNewPatient(patient, servletRequest.getContextPath());
				model.addAttribute("success", env.getProperty("successRegisterPatient"));
				return "forward:/message/patient/success";				
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("exception", env.getProperty("exceptionRegisterPatient"));
				return "forward:/message/patient/error";	
			}
		} else {
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			if(!verifyreCaptcha) {
				model.addAttribute("reCaptchaError", env.getProperty("reCaptchaError"));
			}
			return "/users/patient/register";
		}
	}
	
	@RequestMapping(value = "/users/patient/activation")
	public String getActivationString(@RequestParam("activationString") String activationString, Model model) {
		if(activationService.setActivePatient(activationString)) {
			model.addAttribute("patientActivationMessage", env.getRequiredProperty("patientActivationTrue"));
		} else {
			model.addAttribute("patientActivationMessage", env.getRequiredProperty("patientActivationFalse"));
		}		
		return "/users/patient/activation";
	}

//	FOR SELF EDIT DATA PATIENT
	@RequestMapping(path = "/users/patient/edit")
	public String selfEditPatient(Model model) {
		Patient patient = userService.getLoggedPatient();
		model.addAttribute("patient", patient);
		model.addAttribute("editUserId", patient.getUser().getId());
		model.addAttribute("image", patient.getPhoto());
		return "/users/patient/edit";
	}
	
	@RequestMapping(path = "/users/patient/edit", method = RequestMethod.POST)
	public String selfEditDataPatient(	@Valid Patient patient,
										BindingResult result,
										Model model,
										@SessionAttribute("editUserId") int editUserId,
										@RequestParam("photo") MultipartFile photo,
										@SessionAttribute(name = "image", required = false) byte [] image
										) throws IOException {

		boolean dinstinctLogin = userService.checkDinstinctLoginWithEditUser(patient.getUser().getUsername(), editUserId);
		if(photo.getBytes().length == 0) {
			patient.setPhoto(image);			
		}
		if (!result.hasErrors() && dinstinctLogin) {
			try {
				userService.editPatient(patient);
				model.addAttribute("success", env.getProperty("successUpdatePatient"));
				return "forward:/message/patient/success";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("exception", env.getProperty("exceptionUpdatePatient"));
				return "forward:/message/employee/success";
			}
		} else {
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			return "/users/patient/edit";
		}
	}


}
