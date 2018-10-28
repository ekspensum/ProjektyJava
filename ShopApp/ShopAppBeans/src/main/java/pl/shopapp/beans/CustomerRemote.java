package pl.shopapp.beans;

import java.util.List;

import javax.ejb.Remote;

import pl.shopapp.dao.FirmCustomer;
import pl.shopapp.dao.PrivateCustomer;

@Remote
public interface CustomerRemote {
	
	public void addPrivateCustomer(PrivateCustomer pc);
	public void updatePrivateCustomer(PrivateCustomer pc);
	public List<PrivateCustomer> findPrivateCustomer(String lastName, String pesel);
	public void addFirmCustomer(FirmCustomer fc);
	public void updateFirmCustomer(FirmCustomer pc);
	public List<FirmCustomer> findFirmCustomer(String firmName, String taxNo, String regon);

}
