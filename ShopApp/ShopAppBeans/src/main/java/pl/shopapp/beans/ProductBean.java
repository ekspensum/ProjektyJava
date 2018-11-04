package pl.shopapp.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import pl.shopapp.entites.Product;

/**
 * Session Bean implementation class ProductOperations
 */
@Stateful
//@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class ProductBean implements ProductBeanRemote, ProductBeanLocal {

    /**
     * Default constructor. 
     */
    public ProductBean() {
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
