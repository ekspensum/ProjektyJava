package pl.dentistoffice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource(value="classpath:/messages.properties")
public class AdviceController {
	
//	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Environment env;

	@ExceptionHandler(Exception.class)
	public String handleException(Model model, Exception e) {
		model.addAttribute("exception", "Wystapił wyjątek: "+e.getMessage());
	    model.addAttribute("head", env.getProperty("head"));
	    model.addAttribute("footer", env.getProperty("footer"));
		return "error";
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleMaxUploadSizeExceededException(Model model) {
		model.addAttribute("exception", env.getProperty("msgExceedSizeFile"));
	    model.addAttribute("head", env.getProperty("head"));
	    model.addAttribute("footer", env.getProperty("footer"));
		return "error";
	}
	
	@ExceptionHandler(javax.persistence.NoResultException.class)
	public String handleNoResultException(Model model) {
		model.addAttribute("exception", env.getProperty("msgNoResultException"));
	    model.addAttribute("head", env.getProperty("head"));
	    model.addAttribute("footer", env.getProperty("footer"));
		return "error";
	}
	
	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
	@ModelAttribute
	public void globalAttributes(Model model) {
	    model.addAttribute("head", env.getProperty("head"));
	    model.addAttribute("footer", env.getProperty("footer"));
	}
}
