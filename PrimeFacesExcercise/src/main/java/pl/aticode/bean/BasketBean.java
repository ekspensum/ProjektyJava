package pl.aticode.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ListDataModel;

import lombok.Getter;
import lombok.Setter;
import pl.aticode.dao.ProductRepository;
import pl.aticode.dao.ProductRepositoryImpl;
import pl.aticode.entity.Product;
import pl.aticode.entity.ProductOrderItem;


@ManagedBean
@SessionScoped
@Getter @Setter
public class BasketBean {

	private List<ProductOrderItem> productOrderItemList;
	private ProductRepository productRepository;

	public BasketBean() {
		productOrderItemList = new ArrayList<>();
		productRepository = ProductRepositoryImpl.getInstance();
	}
	
	public String addProduct(Long productId) {
		final Product product = productRepository.findById(productId);
		ProductOrderItem productOrderItem = new ProductOrderItem();
		productOrderItem.setProduct(product);
		productOrderItem.setProductQuantity(1);
		productOrderItemList.add(productOrderItem);
		return "productListOfCategory";
	}
	
	public String removeProduct(Long productId) {
		Iterator<ProductOrderItem> iterator = productOrderItemList.iterator();
		while(iterator.hasNext()) {
			ProductOrderItem productOrderItem = iterator.next();
			if(productOrderItem.getProduct().getId() == productId) {
				productOrderItemList.remove(productOrderItem);
				break;
			}
		}
		return "productListOfCategory";
	}
}
