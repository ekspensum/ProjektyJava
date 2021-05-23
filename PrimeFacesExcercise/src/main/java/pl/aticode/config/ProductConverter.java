package pl.aticode.config;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import pl.aticode.dao.ProductRepository;
import pl.aticode.dao.ProductRepositoryImpl;
import pl.aticode.entity.Product;

public class ProductConverter implements Converter<Product> {

	private ProductRepository productRepository;
	
	public ProductConverter() {
		productRepository = ProductRepositoryImpl.getInstance();
	}

	@Override
	public Product getAsObject(FacesContext context, UIComponent component, String id) {
		return productRepository.findById(Long.valueOf(id));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Product product) {
		return product.getId().toString();
	}

}
