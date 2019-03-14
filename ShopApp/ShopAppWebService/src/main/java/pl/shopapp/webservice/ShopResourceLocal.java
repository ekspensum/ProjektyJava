package pl.shopapp.webservice;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;


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
	public Map<String, List<ProductsJson>> getProductsJsonByIdRange(int idFrom, int idTo);
}
