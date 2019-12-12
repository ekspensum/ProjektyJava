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

import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.InitApplicationService;
import pl.dentistoffice.service.UserService;

@Controller
@SessionAttributes({"admin", "image", "editUserId"})
public class AdminController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HibernateSearchService searchsService; 
	
	@Autowired
	private InitApplicationService initApplicationService; 
	
	public AdminController() {
	}

	public AdminController(Environment env, UserService userService, HibernateSearchService searchsService,
			InitApplicationService initApplicationService) {
		this.env = env;
		this.userService = userService;
		this.searchsService = searchsService;
		this.initApplicationService = initApplicationService;
	}

	@RequestMapping(path = "/panels/adminPanel")
	public String adminPanel(Model model) {
		return "adminPanel";
	}

	@RequestMapping(path = "/users/admin/owner/register")
	public String registrationAdmin(Model model) {
		model.addAttribute("rolesList", userService.getEmployeeRolesWithoutId(5));
		model.addAttribute("admin", new Admin());
		return "/users/admin/owner/register";
	}
	
	@RequestMapping(path = "/users/admin/owner/register", method = RequestMethod.POST)
	public String registrationAdminByOwner(
											@Valid Admin admin, 			
											BindingResult result,
											Model model,
											@RequestParam("photo") MultipartFile photo
											) throws IOException {
		
		User user = admin.getUser();
		String username = user.getUsername();
		boolean dinstinctLogin = userService.checkDinstinctLoginWithRegisterUser(username);
		admin.setPhoto(photo.getBytes());
		if(!result.hasErrors() && dinstinctLogin) {
			try {
				userService.addNewAdmin(admin);
				model.addAttribute("success", env.getProperty("successRegisterUser"));
				return "forward:/message/employee/success";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("exception", env.getProperty("exceptionRegisterUser"));
				return "forward:/message/employee/error";
			}
		} else {
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			model.addAttribute("rolesList", userService.getEmployeeRolesWithoutId(5));
			return "/users/admin/owner/register";
		}
	}
	
	@RequestMapping(path = "/users/admin/owner/selectToEdit")
	public String selectAdminToEditByOwner(Model model) {		
		List<Admin> allAdminsList = userService.getAllAdmins();
		model.addAttribute("allAdminsList", allAdminsList);
		return "/users/admin/owner/selectToEdit";
	}
	
	@RequestMapping(path = "/users/admin/owner/selectToEdit", method = RequestMethod.POST)
	public String selectAdminToEditByOwner(@RequestParam("adminId") String adminId, RedirectAttributes redirectAttributes) {		
		Admin admin = userService.getAdmin(Integer.valueOf(adminId));
		redirectAttributes.addFlashAttribute("admin", admin);
		redirectAttributes.addFlashAttribute("editUserId", admin.getUser().getId());
		return "redirect:/users/admin/owner/edit";
	}
	
	@RequestMapping(path = "/users/admin/owner/edit")
	public String editAdminByOwner(@ModelAttribute("admin") Admin admin, Model model) {
		model.addAttribute("admin", admin);
		model.addAttribute("rolesList", userService.getEmployeeRolesWithoutId(5));	
		model.addAttribute("image", admin.getPhoto());
		return "/users/admin/owner/edit";
	}
	
	@RequestMapping(path = "/users/admin/owner/edit", method = RequestMethod.POST)
	public String editAdminByOwner(
									@Valid Admin admin,
									BindingResult result,
									Model model,
									@SessionAttribute("editUserId") int editUserId,
									@RequestParam("photo") MultipartFile photo,
									@SessionAttribute(name = "image", required = false) byte [] image
									) throws IOException {
		
		boolean dinstinctLogin = userService.checkDinstinctLoginWithEditUser(admin.getUser().getUsername(), editUserId);
		if(photo.getBytes().length == 0) {
			admin.setPhoto(image);			
		}
		if (!result.hasErrors() && dinstinctLogin) {
			try {
				userService.editAdmin(admin);
				model.addAttribute("success", env.getProperty("successUpdateUser"));
				return "forward:/message/employee/success";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("exception", env.getProperty("exceptionUpdateUser"));
				return "forward:/message/employee/error";
			}
		} else {
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			model.addAttribute("rolesList", userService.getEmployeeRolesWithoutId(5));
			return "/users/admin/owner/edit";
		}
	}
	
	@RequestMapping(path = "/users/admin/edit")
	public String selfEditAdmin(Model model) {
		Admin loggedAdmin = userService.getLoggedAdmin();
		model.addAttribute("admin", loggedAdmin);
		model.addAttribute("editUserId", loggedAdmin.getUser().getId());
		model.addAttribute("rolesList", userService.getEmployeeRolesWithoutId(5));	
		model.addAttribute("image", loggedAdmin.getPhoto());
		return "/users/admin/edit";
	}
	
	@RequestMapping(path = "/users/admin/edit", method = RequestMethod.POST)
	public String selfEditAdmin(
								@Valid Admin admin,
								BindingResult result,
								Model model,
								@SessionAttribute("editUserId") int editUserId,
								@RequestParam("photo") MultipartFile photo,
								@SessionAttribute(name = "image", required = false) byte [] image
								) throws IOException {
		
		boolean dinstinctLogin = userService.checkDinstinctLoginWithEditUser(admin.getUser().getUsername(), editUserId);
		if(photo.getBytes().length == 0) {
			admin.setPhoto(image);			
		}
		if (!result.hasErrors() && dinstinctLogin) {
			try {
				userService.editAdmin(admin);
				model.addAttribute("success", env.getProperty("successUpdateUser"));
				return "forward:/message/employee/success";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("exception", env.getProperty("exceptionUpdateUser"));
				return "forward:/message/employee/error";
			}
		} else {
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			model.addAttribute("rolesList", userService.getEmployeeRolesWithoutId(5));
			return "/users/admin/edit";
		}
	}
	
	@RequestMapping(path = "/control/indexing")
	public String indexingDatabase(Model model) {
		if(searchsService.updateIndex()) {
			model.addAttribute("msg", env.getProperty("indexingOk"));
		} else {
			model.addAttribute("msgError", env.getProperty("indexingError"));
		}
		return "/control/indexing";
	}
	
	@RequestMapping(path = "/control/adjusting")
	public String adjustGeneratorPrimaryKey(Model model) {
		if(initApplicationService.adjustSequenceGeneratorPrimaryKeyPostgreDB()) {
			model.addAttribute("msg", env.getProperty("adjustingOk"));
		} else {
			model.addAttribute("msgError", env.getProperty("adjustingError"));
		}
		return "/control/adjusting";
	}
}
