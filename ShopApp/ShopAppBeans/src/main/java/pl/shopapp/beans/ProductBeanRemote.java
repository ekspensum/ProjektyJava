package pl.shopapp.beans;

import java.util.List;
import javax.ejb.Remote;

import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;

@Remote
public interface ProductBeanRemote {
	
	public boolean addProduct(String productName, String productDescription, double productPrice, int productUnitsInStock, byte [] buffer, List<Integer> helperListCat, int idUser);
	public boolean updateProduct(String productName, String productDescription, double productPrice, int productUnitsInStock, byte [] buffer, int productIdToEdit, int [] categoryToEdit, int sizeFileImage, int idUser, List<Integer> helperListCat);
	public List<Product> findProduct(String name);
	public List<Product> findProduct(int quantity);
	public List<Product> listProductByCategory(int idCategory);
	public Product getProduct(int idProduct);
	public List<Product> listProductByIdRange(int idFrom, int idTo);

	public boolean addCategory(String categoryName, byte[] buffer, int idUser);
	public List<Category> listCategory();
	public List<Category> getProductCategories(Product p);
}
