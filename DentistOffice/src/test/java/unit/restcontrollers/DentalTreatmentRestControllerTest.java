package unit.restcontrollers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.rest.AuthRestController;
import pl.dentistoffice.rest.DentalTreatmentRestController;
import pl.dentistoffice.rest.TreatmentListWrapper;
import pl.dentistoffice.service.DentalTreatmentService;

public class DentalTreatmentRestControllerTest {

	private MockMvc mockMvc;
	private DentalTreatmentRestController dentalTreatmentRestController;
	private TreatmentListWrapper treatmentListWrapper;
	private List<TreatmentCategory> treatmentCategoriesList;
	private List<DentalTreatment> dentalTreatmentsList;
	
	@Mock
	private DentalTreatmentService dentalTreatmentService;
	@Mock
	private AuthRestController authRestController;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		treatmentCategoriesList = new ArrayList<>();
		dentalTreatmentsList = new ArrayList<>();
		treatmentListWrapper = new TreatmentListWrapper(treatmentCategoriesList, dentalTreatmentsList);
		dentalTreatmentRestController = new DentalTreatmentRestController(dentalTreatmentService, treatmentListWrapper, authRestController);
		mockMvc = MockMvcBuilders.standaloneSetup(dentalTreatmentRestController).build();
	}

	@Test
	public void testServices() throws Exception {
		TreatmentCategory treatmentCategory = new TreatmentCategory();
		treatmentCategory.setId(13);
		treatmentCategory.setCategoryName("categoryName13");
		treatmentCategoriesList.add(treatmentCategory);
		when(dentalTreatmentService.getTreatmentCategoriesList()).thenReturn(treatmentCategoriesList);

		mockMvc.perform(MockMvcRequestBuilders.get("/mobile/services"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$..[0].id", hasItem(13)))
		.andExpect(jsonPath("$..[0].categoryName", hasItem("categoryName13")));
	}

	@Test
	public void testTreatments() throws Exception {
		DentalTreatment dentalTreatment = new DentalTreatment();
		dentalTreatment.setId(66);
		dentalTreatment.setPrice(33.44);
		dentalTreatmentsList.add(dentalTreatment);
		when(dentalTreatmentService.getDentalTreatmentsList()).thenReturn(dentalTreatmentsList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/mobile/treatments"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$..[0].id", hasItem(66)))
		.andExpect(jsonPath("$..[0].price", hasItem(33.44)));
	}

}
