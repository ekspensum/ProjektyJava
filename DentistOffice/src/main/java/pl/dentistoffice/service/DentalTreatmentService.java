package pl.dentistoffice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dentistoffice.dao.TreatmentRepository;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.entity.User;


@Service
public class DentalTreatmentService {
	
	@Autowired
	private TreatmentRepository treatmentRepository;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private HibernateSearchService searchsService;
	
	public DentalTreatmentService() {
	}

//	for tests
	public DentalTreatmentService(TreatmentRepository treatmentRepository, UserService userService, HibernateSearchService searchsService) {
		this.treatmentRepository = treatmentRepository;
		this.userService = userService;
		this.searchsService = searchsService;
	}

	public boolean addNewDentalTreatment(DentalTreatment dentalTreatment) {
		dentalTreatment.setRegisteredDateTime(LocalDateTime.now());
		User loggedUser = userService.getLoggedUser();
		dentalTreatment.setUserLogged(loggedUser);
		if(treatmentRepository.saveDentalTreatment(dentalTreatment)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean editDentalTreatment(DentalTreatment dentalTreatment) {
		List<TreatmentCategory> currentTreatmentsCategoryList = createCurrentTreatmentsCategoryList(dentalTreatment);
		dentalTreatment.setTreatmentCategory(currentTreatmentsCategoryList);
		dentalTreatment.setEditedDateTime(LocalDateTime.now());
		User loggedUser = userService.getLoggedUser();
		dentalTreatment.setUserLogged(loggedUser);
		if(treatmentRepository.updateDentalTreatment(dentalTreatment)) {
			return true;
		} else {
			return false;
		}
	}

	public List<DentalTreatment> getDentalTreatmentsList(){
		List<DentalTreatment> allDentalTreatments = treatmentRepository.readAllDentalTreatment();
		allDentalTreatments.sort(new Comparator<DentalTreatment>() {

			@Override
			public int compare(DentalTreatment o1, DentalTreatment o2) {
				return o1.getId() - o2.getId();
			}
		});
		return allDentalTreatments;
	}
	
	public DentalTreatment getDentalTreatment(int id) {
		return treatmentRepository.readDentalTreatment(id);
	}
	
	public List<DentalTreatment> searchDentalTreatment(String text){
		List<DentalTreatment> searchedTreatments = searchsService.searchDentalTreatmentNameDescriptionByKeywordQuery(text);
		searchedTreatments.sort(new Comparator<DentalTreatment>() {

			@Override
			public int compare(DentalTreatment o1, DentalTreatment o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return searchedTreatments;
	}
	
	
	public boolean addTreatmentCategory(TreatmentCategory treatmentCategory) {
		User loggedUser = userService.getLoggedUser();
		treatmentCategory.setUserLogged(loggedUser);
		treatmentCategory.setRegisteredDateTime(LocalDateTime.now());
		if(treatmentRepository.saveTreatmentCategory(treatmentCategory)) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<TreatmentCategory> getTreatmentCategoriesList(){
		List<TreatmentCategory> allTreatmentCategory = treatmentRepository.readAllTreatmentCategory();
		allTreatmentCategory.sort(new Comparator<TreatmentCategory>() {

			@Override
			public int compare(TreatmentCategory o1, TreatmentCategory o2) {
				return o1.getId() - o2.getId();
			}
		});
		return allTreatmentCategory;
	}

	public List<TreatmentCategory> getTreatmentCategoriesListToEdit(){
		List<TreatmentCategory> allTreatmentCategory = treatmentRepository.readAllTreatmentCategoryAboveFirstId();
		allTreatmentCategory.sort(new Comparator<TreatmentCategory>() {

			@Override
			public int compare(TreatmentCategory o1, TreatmentCategory o2) {
				return o1.getId() - o2.getId();
			}
		});
		return allTreatmentCategory;
	}
	
	public TreatmentCategory getTreatmentCategory(int id) {
		return treatmentRepository.readTreatmentCategory(id);
	}
	
	public boolean editTreatmentCategory(TreatmentCategory treatmentCategory) {
		User loggedUser = userService.getLoggedUser();
		treatmentCategory.setUserLogged(loggedUser);
		treatmentCategory.setEditedDateTime(LocalDateTime.now());
		if(treatmentRepository.updateTreatmentCategory(treatmentCategory)) {
			return true;
		} else {
			return false;
		}
	}
	
//	PRIVATE METHODS
	private List<TreatmentCategory> createCurrentTreatmentsCategoryList(DentalTreatment dentalTreatment){
		List<TreatmentCategory> selectedIdCategories = dentalTreatment.getTreatmentCategory(); //only id is selected on page. CategoryName was't change
		List<TreatmentCategory> currentCategoryList = new ArrayList<>();
		TreatmentCategory currentCategory;
		for (TreatmentCategory category : selectedIdCategories) {
			currentCategory = new TreatmentCategory();
			currentCategory.setId(category.getId());
			currentCategoryList.add(currentCategory);
		}
		return currentCategoryList;
	}	
}
