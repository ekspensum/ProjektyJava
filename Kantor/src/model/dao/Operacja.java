package model.dao;

public interface Operacja {
	
	boolean kup(DaneTransakcji dane);
	boolean sprzedaj(DaneTransakcji dane);
	boolean dodajRachunekKlienta(String nrRachunku, int idUzytkownika, int idAdministrtor);
	boolean ustawRachunekKlienta(int idUzytkownika, int opcja);
}
