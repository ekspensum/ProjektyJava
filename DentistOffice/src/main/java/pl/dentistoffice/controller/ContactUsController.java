package pl.dentistoffice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pl.dentistoffice.service.EmailContactService;
import pl.dentistoffice.service.SendEmail;


@Controller
@PropertySource(value = "/static/properties/prompt.properties")
public class ContactUsController {

	@Autowired
	private Environment env;

	@Autowired
	private EmailContactService emailContactService;

	@Autowired
	private SendEmail sendEmail;

	@RequestMapping(path = "/contact")
	public String showContactPage(Model model) {
		model.addAttribute("emailContactService", emailContactService);
		model.addAttribute("messagePrompt", env.getProperty("messageContactForm"));
		model.addAttribute("subjectPrompt", env.getProperty("subjectContactForm"));
		model.addAttribute("replyMailPrompt", env.getProperty("replyMailContactForm"));
		return "contact";
	}

	@RequestMapping(path = "/contact", method = RequestMethod.POST)
	public String sendMessage(@Valid EmailContactService emailContactService, BindingResult result,
			@RequestParam("attachment") MultipartFile file, Model model) throws Exception {
			String mailText = "<font color='blue' size='3'>"
							+ emailContactService.getMessage()
							+ "<br><br><br>"
							+ "Adres e-mail nadawcy: "+emailContactService.getReplyMail() + "\n"
							+ "</font><br><br>";
		if (!result.hasErrors()) {
			try {
				sendEmail.sendEmail(env, 
									env.getProperty("mailFrom"), // in this case mailTo == mailFrom
									emailContactService.getSubject(), 
									mailText,
									emailContactService.getReplyMail(), 
									file.getBytes(), 
									file.getOriginalFilename());
				model.addAttribute("alert", "YES");
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("alert", "NO");
			}			
		}
		return "contact";
	}

}
