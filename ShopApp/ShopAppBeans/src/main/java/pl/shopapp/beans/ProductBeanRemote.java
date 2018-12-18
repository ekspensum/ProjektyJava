package pl.shopapp.beans;

import java.util.List;
import javax.ejb.Remote;

import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;

@Remote
public interface ProductBeanRemote {
	
	public boolean addProduct(Product p, List<Integer> helperListCat, int idUser);
	public boolean updateProduct(Product p, int sizeFileImage);
	public List<Product> findProduct(String name);
	public List<Product> findProduct(int quantity);
	public void deleteProduct(Product p, int idUser);
	public List<Product> listProductByCategory(int idCategory);
	public Product getProduct(int idProduct);
		
	public boolean addCategory(Category cat, int userId);
	public List<Category> listCategory();
	public List<Category> getProductCategories(Product p);
}
