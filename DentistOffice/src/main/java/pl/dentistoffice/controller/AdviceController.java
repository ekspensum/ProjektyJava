package pl.dentistoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;


@ControllerAdvice
public class AdviceController {
	
	@Autowired
	private Environment env;

	@ExceptionHandler(Exception.class)
	public String handleException(Model model, Exception e) {
		model.addAttribute("exception", "Wystapił wyjątek: "+e.getMessage());
		return "/message/error";
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleMaxUploadSizeExceededException(Model model) {
		model.addAttribute("exception", env.getProperty("msgExceedSizeFile"));
		return "/message/error";
	}
	
	@ExceptionHandler(javax.persistence.NoResultException.class)
	public String handleNoResultException(Model model) {
		model.addAttribute("exception", env.getProperty("msgNoResultException"));
		return "/message/error";
	}
	
	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
	@ModelAttribute
	public void globalAttributes(Model model) {
//	    model.addAttribute("footer", env.getProperty("footer"));
	}
}
