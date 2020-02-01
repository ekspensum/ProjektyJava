package pl.dentistoffice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dentistoffice.dao.TreatmentRepository;
import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.dao.VisitRepository;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Owner;
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
			String [] roleArray = {"Select", "ROLE_DOCTOR", "ROLE_PATIENT", "ROLE_ASSISTANT", "ROLE_ADMIN", "ROLE_OWNER"};
			String [] roleNameArray = {"Wybierz rolę", "Stomatolog", "Pacjent", "Asystent", "Administrator", "Właściciel"};
			Role role;
			for (int i = 0; i < 6; i++) {
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
			selectedRole.setId(6); //Role Owner
			List<Role> selectedRoleList = new ArrayList<>();
			selectedRoleList.add(selectedRole);
			User user = new User();
			user.setEnabled(true);
			user.setUsername("owner");
			user.setPassword("$2a$10$7yz5gXu716tAxRppjgamxeqHx/JW.YAniVJJUc6odcxN45L2/cz0O");
			user.setRoles(selectedRoleList);
			Owner owner = new Owner();
			owner.setFirstName("Imie właścicela");
			owner.setLastName("Nazwisko właścicela");
			owner.setRegisteredDateTime(LocalDateTime.now());
			owner.setUser(user);
			userRepository.saveOwner(owner);
			log.info("Added Owner record: "+owner.getFirstName());
		}
		
//		String password = new BCryptPasswordEncoder().encode("???");
//		System.out.println(password);
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
