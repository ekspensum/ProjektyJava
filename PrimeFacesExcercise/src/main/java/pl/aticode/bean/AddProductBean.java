package pl.aticode.bean;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.CategoryRepository;
import pl.aticode.dao.CategoryRepositoryImpl;
import pl.aticode.dao.ProductRepository;
import pl.aticode.dao.ProductRepositoryImpl;
import pl.aticode.entity.Category;
import pl.aticode.entity.Product;

@ManagedBean
@RequestScoped
@Getter @Setter
public class AddProductBean {

	private final static Logger logger = LoggerFactory.getLogger(AddProductBean.class);
	
	private UploadedFile file;
	private Product product;
	private ProductRepository productRepository;
	private Category selectedCategory;
	private List<SelectItem> categoryList;
	private CategoryRepository categoryRepository;
	
	public AddProductBean() {
		productRepository = ProductRepositoryImpl.getInstance();
		product = new Product();
		categoryList = new ArrayList<>();
		categoryRepository = CategoryRepositoryImpl.getInstance();
		List<Category> categorysFromDatabase = categoryRepository.findAll();
		for (Category category : categorysFromDatabase) {
			categoryList.add(new SelectItem(category, category.getName()));
		}
	}
	
	public void saveProduct() {
		try {
			byte[] bytePhoto = new byte[(int) file.getSize()];
			InputStream inputStream = file.getInputstream();
			inputStream.read(bytePhoto);
			product.setPhoto(bytePhoto);
			inputStream.close();    		
			product.setCategory(selectedCategory);
			productRepository.saveOrUpdate(product);
			FacesMessage message = new FacesMessage("Sukces", product.getName() + " został zapisany.");
			FacesContext.getCurrentInstance().addMessage("addProduct", message);
			logger.info("New product has been added: ", product.getName());
			product = new Product();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Niepowodzenie", "Nie udało się zapisać produktu do bazy danych!.");
			FacesContext.getCurrentInstance().addMessage("addProduct", message);
			logger.error("ERROR save product ", e.getMessage());
		}
	}

}
