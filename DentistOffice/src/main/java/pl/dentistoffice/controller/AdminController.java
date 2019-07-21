package pl.dentistoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.dentistoffice.service.IndexingService;
import pl.dentistoffice.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IndexingService indexingService; 
	
	@RequestMapping(path = "/panels/adminPanel")
	public String adminPanel(Model model) {

		return "adminPanel";
	}

	@RequestMapping(path = "/users/admin/indexing")
	public String indexingDatabase(Model model) {
		if(indexingService.createOrUpdateIndexesDatabase()) {
			model.addAttribute("msg", env.getProperty("indexingOk"));
		} else {
			model.addAttribute("msgError", env.getProperty("indexingError"));
		}
		return "/users/admin/indexing";
	}
}
