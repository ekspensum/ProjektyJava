package model.dao;

public class OperacjaRachEUR implements Operacja {

	
	
	@Override
	public Operacja kup(DaneTransakcji dane) {
		
		
		System.out.println("Kup EUR");
		
		return null;
	}

	@Override
	public Operacja sprzedaj(DaneTransakcji dane) {
		
		
		
		System.out.println("Sprzdaj EUR");
		
		
		return null;
	}

}
