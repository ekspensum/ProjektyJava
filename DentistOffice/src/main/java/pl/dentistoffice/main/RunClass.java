package pl.dentistoffice.main;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.dentistoffice.config.RootConfig;
import pl.dentistoffice.dao.TreatmentRepository;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;

public class RunClass {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		TreatmentRepository treatmentRepository = context.getBean(TreatmentRepository.class);
//		treatmentRepository.saveTreatmentCategory("category1");
//		treatmentRepository.saveTreatmentCategory("category2");
//		treatmentRepository.saveTreatmentCategory("category3");
//		
//		List<TreatmentCategory> treatmentCategory = treatmentRepository.readAllTreatmentCategory();
//		treatmentRepository.saveDentalTreatment("name", "description", 11.22, treatmentCategory);
//		treatmentCategory.remove(1);
//		treatmentCategory.remove(2);
//		treatmentCategory.add(1, treatmentCategory.get(1));
//		treatmentRepository.saveDentalTreatment("name1", "description1", 33.44, treatmentCategory);
//		
//		treatmentRepository.updateTreatmentCategory(1, "categoryNameUpdate");
//		treatmentRepository.updateDentalTreatment(1, "nameUpdate", "descriptionUpdate", 55.66, treatmentCategory);
//		
//		for (Iterator iterator = treatmentCategory.iterator(); iterator.hasNext();) {
//			TreatmentCategory treatmentCategory2 = (TreatmentCategory) iterator.next();
//			System.out.println(treatmentCategory2.getCategoryName());
//		}
//		
//		List<DentalTreatment> dentalTreatment = treatmentRepository.readAllDentalTreatment();
//		Iterator<DentalTreatment> iterator = dentalTreatment.iterator();
//		while (iterator.hasNext()) {
//			System.out.println(iterator.next().getDescription());			
//		}
//		
//		List<TreatmentCategory> categories = treatmentRepository.readAllTreatmentCategory();
//		Iterator<TreatmentCategory> iterator2 = categories.listIterator();
//		while (iterator2.hasNext()) {
//			TreatmentCategory treatmentCategory3 = (TreatmentCategory) iterator2.next();
//			System.out.println(treatmentCategory3.getCategoryName()+" "+treatmentCategory3.getId());
//		}
//		
//		System.out.println(treatmentRepository.readDentalTreatment("me1").get(0).getName());
//		System.out.println(treatmentRepository.readDentalTreatment(2).getDescription());
		
		context.close();
	}

}
