package pl.dentistoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.InitApplicationService;
import pl.dentistoffice.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HibernateSearchService searchsService; 
	
	@Autowired
	private InitApplicationService initApplicationService; 
	
	@RequestMapping(path = "/panels/adminPanel")
	public String adminPanel(Model model) {

		return "adminPanel";
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
