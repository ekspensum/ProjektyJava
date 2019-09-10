package pl.dentistoffice.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.User;

@Service
public class ActivationService {

	@Autowired
	private Environment env;
	
	@Autowired
	private SendEmail sendEmail;
	
    @Autowired
    private HibernateSearchService searchsService;
	
	@Autowired
	private UserRepository userRepository;
	
	public ActivationService() {
		super();
	}

//	for tests
	public ActivationService(Environment env, SendEmail sendEmail) {
		super();
		this.env = env;
		this.sendEmail = sendEmail;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void sendEmailWithActivationLink(Patient patient, String contextPath) {
		String activationLink = createActivationLink(patient, contextPath);
		String mailText = createMailText(patient, activationLink);
		String mailSubject = "Potwierdzenie rejestracji konta użytkownika.";
		sendEmail.sendEmail(env, patient.getEmail(), mailSubject, mailText);
	}
	
	public boolean setActivePatient(String activationString) {
		Patient patient = searchsService.searchPatientToActivationByKeywordQuery(activationString);
		if(patient != null && LocalDateTime.now().isBefore(patient.getRegisteredDateTime().plusHours(6))) {
			User user = patient.getUser();
			user.setEnabled(true);
			userRepository.updatePatient(patient);
			return true;
		}
		return false;
	}
	
	public String createActivationString(Patient patient) {
		StringBuilder activationString = new StringBuilder();
		Random rand = new Random();
		int number = rand.nextInt(10000);
		activationString.append(number).append(patient.getEmail());
		return new BCryptPasswordEncoder().encode(activationString);
	}
	
//	PRIVATE METHODS
	private String createMailText(Patient patient, String activationLink) {
			String mailText = "<font color='blue' size='3'>Dzień dobry <b>"+patient.getFirstName()+" "+patient.getLastName()+"</b><br>"
			+ "Twoje konto zostało zarejestrowane.<br>"
			+ "Twój login to: "+patient.getUser().getUsername()+". <br>"
			+ "Poniżej znajduje się link aktywacyjny. Prosimy o jego klikniecie w celu aktywacji konta. Link będzie ważny 6 godzin. "
			+ "W przypadku braku aktywacji konta w tym czasie, konieczna będzie ponowna rejestracja.<br><br>"
			+ activationLink
			+ "<br><br>Pozdrawiamy<br>Dział Obsługi Pacjenta</font><br><br>"+env.getProperty("mailFrom");
		return mailText;
	}
	
	private String createActivationLink(Patient patient, String contextPath) {
		StringBuilder activationLink = new StringBuilder();
		String encryptedActivationString = createActivationString(patient);
		activationLink.append("<a href=\"").append(env.getProperty("host"))
										   .append(contextPath)
										   .append("/users/patient/activation?")
										   .append("activationString=")
										   .append(encryptedActivationString)
										   .append("\">Naciśnij ten link aktywacyjny</a>");
		return activationLink.toString();
	}
	
}
