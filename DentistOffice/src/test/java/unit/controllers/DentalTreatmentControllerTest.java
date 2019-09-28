package unit.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.controller.DentalTreatmentController;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.service.DentalTreatmentService;

public class DentalTreatmentControllerTest {
	
	private MockMvc mockMvc;
	private DentalTreatmentController dentalTreatmentController;
	
	@Mock
	private DentalTreatmentService dentalTreatmentService;
	@Mock
	private Environment env;
	@Mock
	private Model model;
	@Mock
	private BindingResult result;
	@Mock
	RedirectAttributes redirectAttributes;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp");       
        
        dentalTreatmentController = new DentalTreatmentController(env, dentalTreatmentService);
		mockMvc = MockMvcBuilders.standaloneSetup(dentalTreatmentController).setViewResolvers(viewResolver).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddDentalTreatmentModel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/control/addTreatment"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/control/addTreatment"));
	}

	@Test
	public void testAddDentalTreatmentDentalTreatmentBindingResultModel() throws Exception {
		TreatmentCategory treatmentCategory1 = new TreatmentCategory();
		treatmentCategory1.setId(1);
		TreatmentCategory treatmentCategory2 = new TreatmentCategory();
		treatmentCategory2.setId(2);
		List<TreatmentCategory> treatmentCategoriesList = new ArrayList<>();
		treatmentCategoriesList.add(treatmentCategory1);
		treatmentCategoriesList.add(treatmentCategory2);
		DentalTreatment dentalTreatment = new DentalTreatment();	
		dentalTreatment.setTreatmentCategory(treatmentCategoriesList);
		
		when(dentalTreatmentService.addNewDentalTreatment(dentalTreatment)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/control/addTreatment")
				.sessionAttr("dentalTreatment", dentalTreatment))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/success"));

		when(dentalTreatmentService.addNewDentalTreatment(dentalTreatment)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.post("/control/addTreatment")
				.sessionAttr("dentalTreatment", dentalTreatment))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/error"));

		when(dentalTreatmentService.addNewDentalTreatment(dentalTreatment)).thenReturn(true);
		treatmentCategory2.setId(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/control/addTreatment")
				.sessionAttr("dentalTreatment", dentalTreatment))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/control/addTreatment"));
	}

	@Test
	public void testSearchDentalTreatment() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/control/searchTreatment"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/control/searchTreatment"));
	}

	@Test
	public void testSearchDentalTreatmentStringRedirectAttributes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/control/searchResult")
				.param("treatmentData", "treatmentData"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/control/selectTreatment"));
		
		DentalTreatment dentalTreatment = new DentalTreatment();
		dentalTreatment.setId(33);
		List<DentalTreatment> treatments = new ArrayList<>();
		treatments.add(dentalTreatment);
		String treatmentData = "treatmentDataNumberCharsAbove_20";
		when(dentalTreatmentService.searchDentalTreatment(treatmentData.substring(0, 20))).thenReturn(treatments);
		@SuppressWarnings("unchecked")
		List<DentalTreatment> searchedTreatmentList = (List<DentalTreatment>) mockMvc.perform(MockMvcRequestBuilders.post("/control/searchResult")
				.param("treatmentData", treatmentData))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/control/selectTreatment"))
				.andReturn().getFlashMap().get("searchedTreatmentList");
		assertEquals(33, searchedTreatmentList.get(0).getId());
	}

	@Test
	public void testSelectDentalTreatmentToEdit() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/control/selectTreatment"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/control/selectTreatment"));
	}

	@Test
	public void testSelectDentalTreatmentToEditStringRedirectAttributes() throws Exception {
		DentalTreatment dentalTreatment = new DentalTreatment();
		dentalTreatment.setId(33);
		when(dentalTreatmentService.getDentalTreatment(44)).thenReturn(dentalTreatment);
		DentalTreatment searchedTreatment = (DentalTreatment) mockMvc.perform(MockMvcRequestBuilders.post("/control/selectedTreatmentToEdit")
				.param("treatmentId", "44"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/control/editTreatment"))
				.andReturn().getFlashMap().get("dentalTreatment");
		assertEquals(33, searchedTreatment.getId());
	}

	@Test
	public void testEditDentalTreatmentDentalTreatmentModel() throws Exception {
		DentalTreatment dentalTreatment = new DentalTreatment();
		dentalTreatment.setId(33);
		DentalTreatment dentalTreatmentActual = (DentalTreatment) mockMvc.perform(MockMvcRequestBuilders.get("/control/editTreatment")
				.sessionAttr("dentalTreatment", dentalTreatment))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/control/editTreatment"))
				.andReturn().getRequest().getAttribute("dentalTreatment");
		assertEquals(33, dentalTreatmentActual.getId());
	}

	@Test
	public void testEditDentalTreatmentDentalTreatmentBindingResultModel() throws Exception {
		TreatmentCategory treatmentCategory1 = new TreatmentCategory();
		treatmentCategory1.setId(1);
		TreatmentCategory treatmentCategory2 = new TreatmentCategory();
		treatmentCategory2.setId(2);
		List<TreatmentCategory> treatmentCategoriesList = new ArrayList<>();
		treatmentCategoriesList.add(treatmentCategory1);
		treatmentCategoriesList.add(treatmentCategory2);
		DentalTreatment dentalTreatment = new DentalTreatment();	
		dentalTreatment.setTreatmentCategory(treatmentCategoriesList);
		
		when(dentalTreatmentService.editDentalTreatment(dentalTreatment)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/control/editTreatment")
				.sessionAttr("dentalTreatment", dentalTreatment))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/success"));

		when(dentalTreatmentService.editDentalTreatment(dentalTreatment)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.post("/control/editTreatment")
				.sessionAttr("dentalTreatment", dentalTreatment))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/error"));

		when(dentalTreatmentService.editDentalTreatment(dentalTreatment)).thenReturn(true);
		treatmentCategory2.setId(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/control/editTreatment")
				.sessionAttr("dentalTreatment", dentalTreatment))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/control/editTreatment"));		
	}

	@Test
	public void testAddTreatmentCategoryModel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/control/addTreatmentCategory"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/control/addTreatmentCategory"));
	}

	@Test
	public void testAddTreatmentCategoryTreatmentCategoryBindingResultModel() throws Exception {
		TreatmentCategory treatmentCategory = new TreatmentCategory();
		treatmentCategory.setCategoryName("categoryName");
		
		when(dentalTreatmentService.addTreatmentCategory(treatmentCategory)).thenReturn(true);		
		mockMvc.perform(MockMvcRequestBuilders.post("/control/addTreatmentCategory")
				.sessionAttr("treatmentCategory", treatmentCategory))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/success"));
		
		when(dentalTreatmentService.addTreatmentCategory(treatmentCategory)).thenReturn(false);		
		mockMvc.perform(MockMvcRequestBuilders.post("/control/addTreatmentCategory")
				.sessionAttr("treatmentCategory", treatmentCategory))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/error"));
		
		treatmentCategory.setCategoryName(";");
		when(dentalTreatmentService.addTreatmentCategory(treatmentCategory)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/control/addTreatmentCategory")
				.sessionAttr("treatmentCategory", treatmentCategory)
				.sessionAttr("result", result))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/control/addTreatmentCategory"));

	}

	@Test
	public void testSelectTreatmentCategoryModel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/control/selectTreatmentCategory"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/control/selectTreatmentCategory"));
	}

	@Test
	public void testSelectTreatmentCategoryStringRedirectAttributes() throws Exception {
		TreatmentCategory treatmentCategory = new TreatmentCategory();
		treatmentCategory.setId(33);
		when(dentalTreatmentService.getTreatmentCategory(44)).thenReturn(treatmentCategory);
		TreatmentCategory treatmentCategoryActual = (TreatmentCategory) mockMvc.perform(MockMvcRequestBuilders.post("/control/selectTreatmentCategory")
				.param("categoryId", "44"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/control/editTreatmentCategory"))
				.andReturn().getFlashMap().get("treatmentCategory");
		assertEquals(33, treatmentCategoryActual.getId());
	}

	@Test
	public void testEditTreatmentCategoryTreatmentCategoryModel() throws Exception {
		TreatmentCategory treatmentCategory = new TreatmentCategory();
		treatmentCategory.setId(33);
		
		TreatmentCategory treatmentCategoryActual = (TreatmentCategory) mockMvc.perform(MockMvcRequestBuilders.get("/control/editTreatmentCategory")
				.sessionAttr("treatmentCategory", treatmentCategory))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/control/editTreatmentCategory"))
				.andReturn().getRequest().getAttribute("treatmentCategory");
		assertEquals(33, treatmentCategoryActual.getId());
	}

	@Test
	public void testEditTreatmentCategoryTreatmentCategoryBindingResultModel() throws Exception {
		TreatmentCategory treatmentCategory = new TreatmentCategory();
		treatmentCategory.setId(33);
		
		when(dentalTreatmentService.editTreatmentCategory(treatmentCategory)).thenReturn(true);
		TreatmentCategory treatmentCategoryActual = (TreatmentCategory) mockMvc
				.perform(MockMvcRequestBuilders.post("/control/editTreatmentCategory")
				.sessionAttr("treatmentCategory", treatmentCategory))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/success"))
				.andReturn().getRequest().getAttribute("treatmentCategory");
		assertEquals(33, treatmentCategoryActual.getId());
		
		when(dentalTreatmentService.editTreatmentCategory(treatmentCategory)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.post("/control/editTreatmentCategory")
				.sessionAttr("treatmentCategory", treatmentCategory))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/error"));
		
		when(dentalTreatmentService.editTreatmentCategory(treatmentCategory)).thenReturn(true);
		treatmentCategory.setCategoryName(";");
		mockMvc.perform(MockMvcRequestBuilders.post("/control/editTreatmentCategory")
				.sessionAttr("treatmentCategory", treatmentCategory))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/control/editTreatmentCategory"));
	}

}
