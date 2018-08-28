package model.dao;

public interface Operacja {
	
	boolean kup(DaneTransakcji dane);
	boolean sprzedaj(DaneTransakcji dane);
}
