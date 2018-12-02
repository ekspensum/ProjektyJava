package pl.shopapp.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;


/**
 * Session Bean implementation class BasketBean
 */
@Stateful
@StatefulTimeout(unit=TimeUnit.MINUTES, value=30) //value must corresponding with web session interval. Is possibility set timeout SFSB on server  
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class BasketBean implements BasketBeanRemote, BasketBeanLocal {

	private BasketData basketData;
	private List<BasketData> basketDataList;
	private UUID sessionUUID;
	
    /**
     * Default constructor. 
     */
    public BasketBean() {
    	basketDataList = new ArrayList<>();

    }
    
    @Override
	public List<BasketData> getBasketData() {
		return basketDataList;
	}
	
    @Override
	public void addBasketRow(int productId, int quantity, String productName, double price, List<BasketData> basketDataList) {
		basketData = new BasketData();
		basketData.setProductId(productId);
		basketData.setQuantity(quantity);
		basketData.setProductName(productName);
		basketData.setPrice(price);
		this.basketDataList.add(basketData);
	}

    @Override
	public void deleteBasketRow(int index, List<BasketData> basketDataList) {
		basketDataList.remove(index);
	}

	@PostConstruct
    private void initSession(){
        //generationg session id
        setSessionUUID(UUID.randomUUID());
    }

	public UUID getSessionUUID() {
		return sessionUUID;
	}

	public void setSessionUUID(UUID sessionUUID) {
		this.sessionUUID = sessionUUID;
	}
    
}
