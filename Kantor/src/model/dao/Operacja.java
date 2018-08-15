package model.dao;

public interface Operacja {
	
	public Operacja kupuj(Double kwota, Waluta waluta);
	public Operacja sprzedaj(Double kwota, Waluta waluta);
}
