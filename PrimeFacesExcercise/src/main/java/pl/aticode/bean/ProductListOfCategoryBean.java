package pl.aticode.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ListDataModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.ProductRepository;
import pl.aticode.dao.ProductRepositoryImpl;
import pl.aticode.entity.Category;
import pl.aticode.entity.Product;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
@Getter @Setter
public class ProductListOfCategoryBean {

	private ListDataModel<Product> productModel;
	private Category category;
//	@Getter(value = AccessLevel.NONE) @Setter(value = AccessLevel.NONE)
//	private int page = 0;
	
//	@ManagedProperty(value="#{param.id}")
//	private Long id;

	public ProductListOfCategoryBean() {
		productModel = new ListDataModel<>();
	}

	public String getCategory(Long id) {
		ProductRepository productRepository = new ProductRepositoryImpl();
		final List<Product> productByCategoryList = productRepository.findByCategoryId(id);
		this.category = productByCategoryList.get(0).getCategory();
		productModel.setWrappedData(productByCategoryList);
		return "productListOfCategoryBean";
	}

}
