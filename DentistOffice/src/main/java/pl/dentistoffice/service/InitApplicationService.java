package pl.dentistoffice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.dentistoffice.dao.TreatmentRepository;
import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.dao.VisitRepository;
import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.entity.VisitStatus;

@Service
public class InitApplicationService {
	
	private static final Logger log = Logger.getLogger(InitApplicationService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired
	private TreatmentRepository treatmentRepository;
		
	@PostConstruct
    public void insertBasicDataToDatabase() {
		List<Role> allRoles = userRepository.readAllRoles();
		if(allRoles.size() == 0) {
			String [] roleArray = {"Select", "ROLE_DOCTOR", "ROLE_PATIENT", "ROLE_ASSISTANT", "ROLE_ADMIN"};
			String [] roleNameArray = {"Wybierz rolę", "Stomatolog", "Pacjent", "Asystent", "Administrator"};
			Role role;
			for (int i = 0; i < 5; i++) {
				role = new Role();
				role.setRole(roleArray[i]);
				role.setRoleName(roleNameArray[i]);
				if(userRepository.saveRole(role)) {
					log.info("Added Role record: "+role.getRole());					
				}
			}
		}
		
		List<VisitStatus> allVisitStatuses = visitRepository.readAllVisitStatus();
		if(allVisitStatuses.size() == 0) {
			String [] statusNameArray = {"PLANNED", "EXECUTED"};
			String [] descriptionArray = {"Planowana", "Zrealizowana"};
			VisitStatus visitStatus;
			for (int i = 0; i < 2; i++) {
				visitStatus = new VisitStatus();
				visitStatus.setStatusName(statusNameArray[i]);
				visitStatus.setDescription(descriptionArray[i]);
				if(visitRepository.saveVisitStatus(visitStatus)) {
					log.info("Added VisitStatus record: "+visitStatus.getStatusName());					
				}
			}
		}
		
		List<DentalTreatment> allDentalTreatment = treatmentRepository.readAllDentalTreatment();
		if(allDentalTreatment.size() == 0) {
			DentalTreatment dentalTreatment = new DentalTreatment();
			dentalTreatment.setName("Wybierz zabieg");
			dentalTreatment.setDescription("Domyślne pole wyboru.");
			dentalTreatment.setPrice(0.0);
			if(treatmentRepository.saveDentalTreatment(dentalTreatment)) {
				log.info("Added DentalTreatment record: "+dentalTreatment.getName());				
			}
		}
		
		List<TreatmentCategory> allTreatmentCategory = treatmentRepository.readAllTreatmentCategory();
		if(allTreatmentCategory.size() == 0) {
			TreatmentCategory treatmentCategory = new TreatmentCategory();
			treatmentCategory.setCategoryName("Wybierz kategorię zabiegu");
			if(treatmentRepository.saveTreatmentCategory(treatmentCategory)) {
				log.info("Added TreatmentCategory record: "+treatmentCategory.getCategoryName());
			}
		}
		
		List<User> allUsers = userRepository.readAllUsers();
		if(allUsers.size() == 0) {
			Role selectedRole = new Role();
			selectedRole.setId(5); //Role Admin
			List<Role> selectedRoleList = new ArrayList<>();
			selectedRoleList.add(selectedRole);
			User user = new User();
			user.setEnabled(true);
			user.setUsername("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setRoles(selectedRoleList);
			Admin admin = new Admin();
			admin.setFirstName("Administrator");
			admin.setLastName("Administrator");
			admin.setPesel("00000000000");
			admin.setPhone("00000000");
			admin.setEmail("admin@mail.com");
			admin.setRegisteredDateTime(LocalDateTime.now());
			admin.setUser(user);
			userRepository.saveAdmin(admin);
			log.info("Added User record: "+admin.getFirstName());
		}
    }
	
	public boolean adjustSequenceGeneratorPrimaryKeyPostgreDB() {
		if(visitRepository.adjustSequenceGeneratorPrimaryKey() 
				&& userRepository.adjustSequenceGeneratorPrimaryKey() 
				&& treatmentRepository.adjustSequenceGeneratorPrimaryKey()) {
			return true;
		} else {
			return false;
		}
	}
}
