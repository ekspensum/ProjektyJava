package pl.shopapp.beans;

import java.util.List;

import javax.ejb.Remote;

import pl.shopapp.entites.Product;

@Remote
public interface ProductOperationsRemote {
	
	public void addProduct(Product p);
	public void updateProduct(Product p, int id);
	public List<Product> findProduct(String nme, String description);
	public void deleteProduct(Product p, int id);
}
