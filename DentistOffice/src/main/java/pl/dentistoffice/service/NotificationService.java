package pl.dentistoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Visit;


@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NotificationService {

	@Autowired
	private Environment env;
	
	@Autowired
	private SendEmail sendEmail;
	
	@Autowired
	private UserService userService;
	
//	for tests
	public NotificationService(Environment env, SendEmail sendEmail) {
		super();
		this.env = env;
		this.sendEmail = sendEmail;
	}

	public void sendEmailWithRegisterNotification(Object user) throws Exception {	
		String login = null, firstName = null, lastName = null, email = null, mailText;
		
		if(user instanceof Doctor) {
			Doctor doctor = (Doctor) user;
			login = doctor.getUser().getUsername();
			firstName = doctor.getFirstName();
			lastName = doctor.getLastName();
			email = doctor.getEmail();
		} else if(user instanceof Assistant) {
			Assistant assistant = (Assistant) user;
			login = assistant.getUser().getUsername();
			firstName = assistant.getFirstName();
			lastName = assistant.getLastName();
			email = assistant.getEmail();
		} else if(user instanceof Admin) {
			Admin admin = (Admin) user;
			login = admin.getUser().getUsername();
			firstName = admin.getFirstName();
			lastName = admin.getLastName();
			email = admin.getEmail();
		} else {
			throw new Exception("Powiadomienie przy rejestracji pracownika - nie odaleziono użytkownika!");
		}
		
		mailText = "<font color='blue' size='3'>Dzień dobry <b>"+firstName+" "+lastName+".</b><br>"
				+ "Twoje konto zostało zarejestrowane - możesz się zalogować.<br>"
				+ "Twój login to: "+login+". <br>"
				+ "Twoje hasło przekaże Ci administrator. <br>"
				+ "<br><br>Pozdrawiamy<br>Dział HR</font><br><br>"
				+ "<a href=\"mailto:"+env.getProperty("mailFrom")+"\" >Napisz do nas</a>";
		
		String mailSubject = "Potwierdzenie rejestracji konta użytkownika.";
		sendEmail.sendEmail(env, email, mailSubject, mailText);
	}
	
	public void sendEmailWithVisitDateNotification(Visit visit) {
		String[] dayOfWeekPolish = userService.dayOfWeekPolish();
		String mailText = "<font color='blue' size='3'>Dzień dobry <b>"+visit.getPatient().getFirstName()+" "+visit.getPatient().getLastName()+".</b><br>"
				+ "Twoja wizyta została zaplanowana na: "+dayOfWeekPolish[visit.getVisitDateTime().getDayOfWeek().getValue()]+" "+visit.getVisitDateTime().toLocalDate()+" na godzinę: "+visit.getVisitDateTime().toLocalTime()+"<br>"
				+ "Wybrany lekarz to: "+visit.getDoctor().getFirstName()+" "+visit.getDoctor().getLastName()+".<br>"
				+ "Korzystając z opcji dostępnej na Twoim koncie możesz także odwołać tą wizytę.<br>"
				+ "<br><br>Pozdrawiamy<br>Dział Obsługi Pacjenta</font><br><br>"
				+ "<a href=\"mailto:"+env.getProperty("mailFrom")+"\" >Napisz do nas</a>";
		
		String mailSubject = "Potwierdzenie zaplanowania nowej wizyty.";
		sendEmail.sendEmail(env, visit.getPatient().getEmail(), mailSubject, mailText);
	}
	
	public void sendEmailWithVisitCancelNotification(Visit visit) {
		String[] dayOfWeekPolish = userService.dayOfWeekPolish();
		String mailText = "<font color='blue' size='3'>Dzień dobry <b>"+visit.getPatient().getFirstName()+" "+visit.getPatient().getLastName()+".</b><br>"
				+ "Odwołałeś swoją wizytę, która była zaplanowana na: "+dayOfWeekPolish[visit.getVisitDateTime().getDayOfWeek().getValue()]+" "+visit.getVisitDateTime().toLocalDate()+" na godzinę: "+visit.getVisitDateTime().toLocalTime()+"<br>"
				+ "Lekarz w odwołanej wizycie to: "+visit.getDoctor().getFirstName()+" "+visit.getDoctor().getLastName()+".<br>"
				+ "Korzystając z opcji dostępnej na Twoim koncie możesz także zaplanować nowy termin wizyty.<br>"
				+ "<br><br>Pozdrawiamy<br>Dział Obsługi Pacjenta</font><br><br>"
				+ "<a href=\"mailto:"+env.getProperty("mailFrom")+"\" >Napisz do nas</a>";
		
		String mailSubject = "Potwierdzenie odwołania wizyty.";
		sendEmail.sendEmail(env, visit.getPatient().getEmail(), mailSubject, mailText);
	}
}
