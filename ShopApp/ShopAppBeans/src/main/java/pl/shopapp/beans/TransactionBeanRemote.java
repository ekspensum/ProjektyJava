package pl.shopapp.beans;

import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Remote;

import pl.shopapp.entites.Transaction;


@Remote
public interface TransactionBeanRemote {
	
	public boolean newTransaction(int idUser, List<BasketData> basketList);
	public List<Transaction> getTransactionsData(int idUser, LocalDateTime dateFrom, LocalDateTime dateTo, String sortBy);
	public List<Transaction> getNoExecOrder(LocalDateTime from, LocalDateTime to);
	public boolean execOrder(String [] idTransactionArray, int idUser);
}
