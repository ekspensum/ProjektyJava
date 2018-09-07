package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class OperacjaRachEUR implements Operacja {

	@Override
	public boolean kup(DaneTransakcji dane) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sprzedaj(DaneTransakcji dane) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dodajRachunekKlienta(String nrRachunku, int idUzytkownika, int idAdministrtor) {

		ObslugaBD bd = new ObslugaBD();
		Connection conn = bd.connectDB();
		Statement stm = null;
		try {
			String sqlRola = "SELECT rola_idRola FROM uzytkownik WHERE idUzytkownik = " + idUzytkownika + "";
			Statement stmRola = conn.createStatement();
			ResultSet srRola = stmRola.executeQuery(sqlRola);
			if (srRola.first()) {
				if (srRola.getInt(1) == 3) {
					conn.setAutoCommit(false);
					String sqlIdKF = "SELECT idKlientFirmowy FROM uzytkownik INNER JOIN klientFirmowy ON idUzytkownik = uzytkownik_idUzytkownik WHERE idUzytkownik = "
							+ idUzytkownika + "";
					Statement stmKF = conn.createStatement();
					ResultSet rsKF = stmKF.executeQuery(sqlIdKF);
					if (rsKF.first()) {
						PreparedStatement query = conn.prepareStatement(
								"INSERT INTO rachunekEUR(nrRachunku, dataUtworzenia, klientFirmowy_idKlientFirmowy, administrator_idAdministrator) VALUES(?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
						query.setString(1, nrRachunku);
						query.setObject(2, LocalDateTime.now());
						query.setInt(3, rsKF.getInt(1));
						query.setInt(4, idAdministrtor);
						if (query.executeUpdate() > 0) {
							ResultSet rsIdRachunku = query.getGeneratedKeys();
							rsIdRachunku.next();
							PreparedStatement aktywacja = conn.prepareStatement(
									"INSERT INTO zapisyRachEUR VALUES(null, 'Aktywacja', 0.0, 0.0, 0.0, ?, ?)");
							aktywacja.setObject(1, LocalDateTime.now());
							aktywacja.setInt(2, rsIdRachunku.getInt(1));
							if (aktywacja.executeUpdate() > 0) {
								String sqlUp = "UPDATE uzytkownik SET eur = 1 WHERE idUzytkownik = " + idUzytkownika
										+ "";
								stm = conn.createStatement();
								if (stm.executeUpdate(sqlUp) > 0) {
									conn.commit();
									return true;
								}
							}
						}
					}

				} else if (srRola.getInt(1) == 4) {
					conn.setAutoCommit(false);
					String sqlIdKP = "SELECT idKlientPrywatny FROM uzytkownik INNER JOIN klientPrywatny ON idUzytkownik = uzytkownik_idUzytkownik WHERE idUzytkownik = "
							+ idUzytkownika + "";
					Statement stmKP = conn.createStatement();
					ResultSet rsKP = stmKP.executeQuery(sqlIdKP);
					if (rsKP.first()) {
						PreparedStatement query = conn.prepareStatement(
								"INSERT INTO rachunekEUR(nrRachunku, dataUtworzenia, klientPrywatny_idKlientPrywatny, administrator_idAdministrator) VALUES(?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
						query.setString(1, nrRachunku);
						query.setObject(2, LocalDateTime.now());
						query.setInt(3, rsKP.getInt(1));
						query.setInt(4, idAdministrtor);
						if (query.executeUpdate() > 0) {
							ResultSet rsIdRachunku = query.getGeneratedKeys();
							rsIdRachunku.next();
							PreparedStatement aktywacja = conn.prepareStatement(
									"INSERT INTO zapisyRachEUR VALUES(null, 'Aktywacja', 0.0, 0.0, 0.0, ?, ?)");
							aktywacja.setObject(1, LocalDateTime.now());
							aktywacja.setInt(2, rsIdRachunku.getInt(1));
							if (aktywacja.executeUpdate() > 0) {
								String sqlUp = "UPDATE uzytkownik SET eur = 1 WHERE idUzytkownik = " + idUzytkownika
										+ "";
								stm = conn.createStatement();
								if (stm.executeUpdate(sqlUp) > 0) {
									conn.commit();
									return true;
								}
							}
						}
					}
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean ustawRachunekKlienta(int idUzytkownika, int opcja) {
		ObslugaBD bd = new ObslugaBD();
		Connection conn = bd.connectDB();
		try {
			String sql = "UPDATE uzytkownik SET eur = " + opcja + " WHERE idUzytkownik = " + idUzytkownika + "";
			Statement stm = conn.createStatement();
			if (stm.executeUpdate(sql) > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
