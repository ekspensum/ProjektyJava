package pl.aticode.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.ProductRepository;
import pl.aticode.dao.ProductRepositoryImpl;
import pl.aticode.entity.Category;
import pl.aticode.entity.Product;

@ManagedBean
@ApplicationScoped
@Getter @Setter
public class ProductListOfCategoryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ProductRepository productRepository;
	private Category category;
	private List<Product> productByCategoryList;
	

	public ProductListOfCategoryBean() {
		productRepository = ProductRepositoryImpl.getInstance();
	}
	
	public String showPage(Long categoryId) {
		productByCategoryList = productRepository.findByCategoryId(categoryId);
		return "productListOfCategory";
	}	
}
