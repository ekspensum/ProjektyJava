package pl.aticode.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.ProductRepository;
import pl.aticode.dao.ProductRepositoryImpl;
import pl.aticode.entity.Product;

@ManagedBean
@ApplicationScoped
@Getter @Setter
public class TopProductsBean {
	
	private Product selectedProduct;
	private List<SelectItem> topProductList;
	private ProductRepository productRepository;
	
	public TopProductsBean() {
		productRepository = ProductRepositoryImpl.getInstance();
		topProductList = new ArrayList<>();
		List<Product> topProdctsFromDatabase = productRepository.getTopProdcts();
		for (Product product : topProdctsFromDatabase) {
			topProductList.add(new SelectItem(product, product.getName()));
		}
	}
	
	

}
