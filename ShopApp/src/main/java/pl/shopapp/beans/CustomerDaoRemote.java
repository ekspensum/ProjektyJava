package pl.shopapp.beans;

import java.util.List;

import javax.ejb.Remote;

import pl.shopapp.entites.FirmCustomer;
import pl.shopapp.entites.PrivateCustomer;

@Remote
public interface CustomerDaoRemote {

	public void addPrivateCustomer(PrivateCustomer pc);
	public void updatePrivateCustomer(PrivateCustomer pc);
	public List<PrivateCustomer> findPrivateCustomer(String lastName, String pesel);
	public void addFirmCustomer(FirmCustomer fc);
	public void updateFirmCustomer(FirmCustomer pc);
	public List<FirmCustomer> findFirmCustomer(String firmName, String taxNo, String regon);
	
}
