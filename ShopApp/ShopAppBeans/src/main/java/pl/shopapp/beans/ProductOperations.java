package pl.shopapp.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import pl.shopapp.entites.Product;

/**
 * Session Bean implementation class ProductOperations
 */
@Stateful
@LocalBean
public class ProductOperations implements ProductOperationsRemote, ProductOperationsLocal {

    /**
     * Default constructor. 
     */
    public ProductOperations() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addProduct(Product p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(Product p, int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> findProduct(String nme, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(Product p, int id) {
		// TODO Auto-generated method stub
		
	}

}
