package pl.shopapp.beans;

import java.util.List;
import javax.ejb.Remote;

import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;

@Remote
public interface ProductBeanRemote {
	
	public boolean addProduct(Product p, List<Integer> helperListCat, int idUser);
	public void updateProduct(Product p, int id);
	public List<Product> findProduct(String name, String description);
	public void deleteProduct(Product p, int idUser);
	public List<Product> listProductByCategory(int idCategory);
	public Product getProduct(int idProduct);
	
	public boolean addCategory(Category cat, int userId);
	public List<Category> listCategory();

}
