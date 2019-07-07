package pl.dentistoffice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
	
	@Autowired
	private Environment env;

//	@ExceptionHandler(Exception.class)
//	public String handleException(Model model, Exception e) {
//		model.addAttribute("exception", "Wystapił wyjątek: "+e);
//		return "handleError";
//	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleMaxUploadSizeExceededException(Model model) {
		model.addAttribute("exception", env.getProperty("msgExceedSizeFile"));
		return "handleError";
	}
	
	@InitBinder
	public void dataBinding(WebDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		dateFormat.setLenient(false);
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
//	@ModelAttribute
//	public void addAttributes(Model model) {
//	    model.addAttribute("head", env.getProperty("head"));
//	    model.addAttribute("footer", env.getProperty("footer"));
//	}
}
