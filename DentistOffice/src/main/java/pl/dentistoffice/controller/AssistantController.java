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

import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.service.UserService;

@Controller
@SessionAttributes({"assistant", "image", "editUserId"})
public class AssistantController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/panels/assistantPanel")
	public String assistantPanel(Model model) {

		return "assistantPanel";
	}

	@RequestMapping(path = "/users/assistant/admin/register")
	public String registrationAssistantByAdmin(Model model) {
		List<Role> allRoles = userService.getAllRoles();
		model.addAttribute("rolesList", allRoles);
		model.addAttribute("assistant", new Assistant());
		return "/users/assistant/admin/register";
	}
	
	@RequestMapping(path = "/users/assistant/admin/register", method = RequestMethod.POST)
	public String registrationAssistantByAdmin(
												@Valid Assistant assistant, 			
												BindingResult result,
												Model model,
												@RequestParam("photo")	MultipartFile photo
												) throws IOException {
		
		boolean dinstinctLogin = userService.checkDinstinctLoginWithRegisterUser(assistant.getUser().getUsername());
		assistant.setPhoto(photo.getBytes());
		if(!result.hasErrors() && assistant.getUser().getRoles().get(0).getId() != assistant.getUser().getRoles().get(1).getId() && dinstinctLogin) {
			userService.addNewAssistant(assistant);
			model.addAttribute("success", env.getProperty("successRegisterAssistant"));
			return "forward:/message/employee/success";
		} else {
			if(assistant.getUser().getRoles().get(0).getId() == assistant.getUser().getRoles().get(1).getId()) {
				model.addAttribute("roleError", env.getProperty("roleError"));
			}
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			model.addAttribute("rolesList", userService.getAllRoles());
			return "/users/assistant/admin/register";
		}
	}
	
	@RequestMapping(path = "/users/assistant/admin/selectToEdit")
	public String selectAssistantToEditByAdmin(Model model) {		
		List<Assistant> allAssistantsList = userService.getAllAssistants();
		model.addAttribute("allAssistantsList", allAssistantsList);
		return "/users/assistant/admin/selectToEdit";
	}
	
	@RequestMapping(path = "/users/assistant/admin/selectToEdit", method = RequestMethod.POST)
	public String selectAssistantToEditByAdmin(@RequestParam("assistantId") String assistantId, RedirectAttributes redirectAttributes) {		
		Assistant assistant = userService.getAssistant(Integer.valueOf(assistantId));
		redirectAttributes.addFlashAttribute("assistant", assistant);
		redirectAttributes.addFlashAttribute("editUserId", assistant.getUser().getId());
		return "redirect:/users/assistant/admin/edit";
	}
	
	@RequestMapping(path = "/users/assistant/admin/edit")
	public String editAssistantByAdmin(@ModelAttribute("assistant") Assistant assistant, Model model) {
		model.addAttribute("assistant", assistant);
		model.addAttribute("rolesList", userService.getAllRoles());	
		model.addAttribute("image", assistant.getPhoto());
		return "/users/assistant/admin/edit";
	}
	
	@RequestMapping(path = "/users/assistant/admin/edit", method = RequestMethod.POST)
	public String editDataAssistantByAdmin(
											@Valid Assistant assistant,
											BindingResult result,
											Model model,
											@SessionAttribute("editUserId") int editUserId,
											@RequestParam("photo") MultipartFile photo,
											@SessionAttribute(name = "image", required = false) byte [] image
											) throws IOException {
		
		boolean dinstinctLogin = userService.checkDinstinctLoginWithEditUser(assistant.getUser().getUsername(), editUserId);
		if(photo.getBytes().length == 0) {
			assistant.setPhoto(image);			
		}
		if (!result.hasErrors() && assistant.getUser().getRoles().get(0).getId() != assistant.getUser().getRoles().get(1).getId() && dinstinctLogin) {
			userService.editAssistant(assistant);
			model.addAttribute("success", env.getProperty("successUpdateAssistant"));
			return "forward:/message/employee/success";
		} else {
			if(assistant.getUser().getRoles().get(0).getId() == assistant.getUser().getRoles().get(1).getId()) {
				model.addAttribute("roleError", env.getProperty("roleError"));
			}
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			model.addAttribute("rolesList", userService.getAllRoles());
			return "/users/assistant/admin/edit";
		}
	}
	
	@RequestMapping(path = "/users/assistant/edit")
	public String selfEditAssistant(Model model) {
		Assistant assistant = userService.getLoggedAssistant();
		model.addAttribute("assistant", assistant);
		model.addAttribute("editUserId", assistant.getUser().getId());
		model.addAttribute("image", assistant.getPhoto());
		return "/users/assistant/edit";
	}
	
	@RequestMapping(path = "/users/assistant/edit", method = RequestMethod.POST)
	public String selfEditDataAssistant(
										@Valid Assistant assistant,
										BindingResult result, 
										Model model,
										@SessionAttribute("editUserId") int editUserId,
										@RequestParam("photo") MultipartFile photo,
										@SessionAttribute(name = "image", required = false) byte [] image
										) throws IOException {

		boolean dinstinctLogin = userService.checkDinstinctLoginWithEditUser(assistant.getUser().getUsername(), editUserId);
		if(photo.getBytes().length == 0) {
			assistant.setPhoto(image);			
		}
		if (!result.hasErrors() && dinstinctLogin) {
			userService.editAssistant(assistant);
			model.addAttribute("success", env.getProperty("successUpdateAssistant"));
			return "forward:/message/employee/success";
		} else {
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			return "/users/assistant/edit";
		}
	}
}
