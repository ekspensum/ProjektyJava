package pl.shopapp.webservice;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.PathParam;


@Local
public interface ShopResourceLocal {
	
	public byte [] getProcessors();
	public byte [] getHardDisks();
	public byte [] getProductById(@PathParam("id") int id);
	public byte [] getProductsById(@PathParam("idFrom") int idFrom, @PathParam("idTo") int idTo);
	public List<MainBoardXml> getMainBoardXmls();
	public List<RamMemoryXml> getRamMemoryXml();
	
	public List<MainBoardJson> getAllMainBoardJson();
	public List<RamMemoryJson> getAllRamMemoryJson();
	
}
