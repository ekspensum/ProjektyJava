package pl.aticode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.ListDataModel;

import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.ProductRepository;
import pl.aticode.dao.ProductRepositoryImpl;
import pl.aticode.entity.Product;

@ManagedBean
@RequestScoped
@Getter @Setter
public class ProductDetailsBean {

	private Product product;
	private ProductRepository productRepository;
	
	@ManagedProperty(value = "#{param.id}")
	private Long productId;

	public ProductDetailsBean() {
		productRepository = new ProductRepositoryImpl();
		
		System.out.println("CONSTR");
	}

	public String displayPage() {
		System.out.println("ID "+productId);
		
		product = productRepository.findById(productId);
		return "productDetailsBean";
	}

}
