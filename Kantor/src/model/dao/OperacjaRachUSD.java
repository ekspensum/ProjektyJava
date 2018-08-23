package model.dao;

public class OperacjaRachUSD implements Operacja {

	
	
	
	
	
	@Override
	public Operacja kup(DaneTransakcji dane) {
		ObslugaBD bd = new ObslugaBD();
		
		System.out.println("kup USD");
		
		
		return null;
	}

	@Override
	public Operacja sprzedaj(DaneTransakcji dane) {
		
		System.out.println("sprzedaj USD");
		
		
		
		return null;
	}



}
