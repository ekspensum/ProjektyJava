package pl.shopapp.beans;

import java.util.List;
import javax.ejb.Remote;


@Remote
public interface TransactionBeanRemote {
	
	public boolean newTransaction(int idUser, List<BasketData> basketList);

}
