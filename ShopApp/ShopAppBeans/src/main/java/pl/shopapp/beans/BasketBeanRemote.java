package pl.shopapp.beans;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface BasketBeanRemote {
	
	public List<BasketData> getBasketData();
	public void addBasketRow(int productId, int quantity, String productName, double price, List<BasketData> basketDataList);
	public void deleteBasketRow(int index,  List<BasketData> basketDataList);

}
