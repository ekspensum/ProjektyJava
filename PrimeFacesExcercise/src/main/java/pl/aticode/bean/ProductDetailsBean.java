package pl.aticode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

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
		productRepository = ProductRepositoryImpl.getInstance();
	}

//	public String displayPage() {
//		product = productRepository.findById(productId);
//		return "productDetails";
//	}
	
	public void setProductId(Long productId) {
		product = productRepository.findById(productId);
	}
}
