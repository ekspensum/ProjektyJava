package pl.shopapp.webservice;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import pl.shopapp.webservice.model.CategoryJson;
import pl.shopapp.webservice.model.HardDisksJson;
import pl.shopapp.webservice.model.MainBoardJson;
import pl.shopapp.webservice.model.MainBoardXml;
import pl.shopapp.webservice.model.ProcessorsJson;
import pl.shopapp.webservice.model.ProductDataJson;
import pl.shopapp.webservice.model.ProductsJson;
import pl.shopapp.webservice.model.RamMemoryJson;
import pl.shopapp.webservice.model.RamMemoryXml;


@Local
public interface ShopResourceLocal {
	
	public byte [] getProcessors();
	public byte [] getHardDisks();
	public byte [] getProductById(int id);
	public byte [] getProductsById(int idFrom, int idTo);
	public List<MainBoardXml> getMainBoardXmls();
	public List<RamMemoryXml> getRamMemoryXml();

	public List<ProcessorsJson> getAllProcessorsJson();	
	public List<MainBoardJson> getAllMainBoardJson();
	public List<RamMemoryJson> getAllRamMemoryJson();
	public List<HardDisksJson> getAllHardDisksJson();
	public ProductsJson getProductJsonById(int id);
	public List<ProductDataJson> getProductJsonByName(String name);
	public Map<String, List<ProductsJson>> getProductsJsonByIdRange(int idFrom, int idTo);
	
	public String updateProductData(ProductDataJson pdj);
	public String addNewProductFormParam(String login, String password, String productName, String productDescription, double productPrice, int productUnitsInStock, String category1Name, String category2Name, String base64Image);
	public String addNewProductFormParamMap(Map<String, String> mapParam);
	public String addNewProductJson(ProductDataJson pdj);
	
	public List<CategoryJson> getAllCategory();
}
