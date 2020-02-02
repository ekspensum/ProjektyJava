package pl.shopapp.webservice;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.transaction.SystemException;

import pl.shopapp.webservice.model.CategoryJson;
import pl.shopapp.webservice.model.MainBoardXml;
import pl.shopapp.webservice.model.ProductDataJson;
import pl.shopapp.webservice.model.ProductsJson;
import pl.shopapp.webservice.model.RamMemoryXml;


@Local
public interface ShopResourceLocal {
	
	public byte [] getProcessors();
	public byte [] getHardDisks();
	public byte [] getProductById(int id);
	public byte [] getProductsById(int idFrom, int idTo);
	public List<MainBoardXml> getMainBoardXmls();
	public List<RamMemoryXml> getRamMemoryXml();

	public List<ProductsJson> getAllProcessorsJson();	
	public List<ProductsJson> getAllMainBoardJson();
	public List<ProductsJson> getAllRamMemoryJson();
	public List<ProductsJson> getAllHardDisksJson();
	public ProductsJson getProductJsonById(int id);
	public List<ProductDataJson> getProductJsonByName(String name);
	public Map<String, List<ProductsJson>> getProductsJsonByIdRange(int idFrom, int idTo);
	
	public String updateProductData(ProductDataJson pdj) throws IllegalStateException, SecurityException, SystemException;
	public String addNewProductFormParam(String login, String password, String productName, String productDescription, double productPrice, int productUnitsInStock, String category1Id, String category2Id, String base64Image) throws IllegalStateException, SecurityException, SystemException;
	public String addNewProductFormParamMap(Map<String, String> mapParam) throws NumberFormatException, IllegalStateException, SecurityException, SystemException;
	public String addNewProductJson(ProductDataJson pdj) throws IllegalStateException, SecurityException, SystemException;
	
	public List<CategoryJson> getAllCategory();
}
