package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.Query;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;

import model.encje.DaneDolar;
import model.encje.DaneEuro;

public class ObslugaBD {

	Connection conn = null;

	public ObslugaBD() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kantor?serverTimezone=Europe/Warsaw", "andrzej",
					"1234");

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
	}

	public Boolean dodajRekordDaneDolar(Double bid, Double ask, int idOperatora) throws SQLException {
		try {
			PreparedStatement query = conn.prepareStatement(
					"INSERT INTO daneDolar(bid, ask, dataDodania, operator_idOperator) VALUES(?, ?, ?, ?)");
			query.setDouble(1, bid);
			query.setDouble(2, ask);
			query.setObject(3, LocalDateTime.now());
			query.setInt(4, idOperatora);
			if (query.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return false;

	}

	public List<DaneDolar> odczytListaDaneDolar() throws SQLException {
		try {
			DaneDolar dd = null;
			List<DaneDolar> listaDaneDolar = new ArrayList<>();
			Statement query = conn.createStatement();
			ResultSet rs = query.executeQuery("SELECT * from daneDolarView ORDER BY dataDodania DESC LIMIT 10");
			while (rs.next()) {
				dd = new DaneDolar();
				dd.setIdDolar(rs.getInt(1));
				dd.setZnak(rs.getString(2));
				dd.setBid(rs.getDouble(3));
				dd.setAsk(rs.getDouble(4));
				dd.setDataDodania(rs.getObject(5, LocalDateTime.class));
				dd.setIdOperator(rs.getInt(6));
				listaDaneDolar.add(dd);
			}
			return listaDaneDolar;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	public Boolean dodajRekordDaneEuro(Double bid, Double ask, int idOperatora) throws SQLException {
		try {
			PreparedStatement query = conn.prepareStatement(
					"INSERT INTO daneEuro(bid, ask, dataDodania, operator_idOperator) VALUES(?, ?, ?, ?)");
			query.setDouble(1, bid);
			query.setDouble(2, ask);
			query.setObject(3, LocalDateTime.now());
			query.setInt(4, idOperatora);
			if (query.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return false;

	}
	public List<DaneEuro> odczytListaDaneEuro() throws SQLException {
		try {
			DaneEuro de = null;
			List<DaneEuro> listaDaneEuro = new ArrayList<>();
			Statement query = conn.createStatement();
			ResultSet rs = query.executeQuery("SELECT * from daneEuro ORDER BY dataDodania DESC LIMIT 10");
			while (rs.next()) {
				de = new DaneEuro();
				de.setIdEuro(rs.getInt(1));
				de.setZnak(rs.getString(2));
				de.setBid(rs.getDouble(3));
				de.setAsk(rs.getDouble(4));
				de.setDataDodania(rs.getObject(5, LocalDateTime.class));
				de.setIdOperator(rs.getInt(6));
				listaDaneEuro.add(de);
			}
			return listaDaneEuro;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	public UserZalogowany logowanie(String login, String haslo) throws SQLException {
		try {
			PreparedStatement query = conn.prepareStatement(
					"SELECT idUzytkownik, rola_idRola, idOperator, rola, nazwa, imie, nazwisko, opImie, opNazwisko, adminImie, adminNazwisko FROM uzytkownik LEFT JOIN administrator ON idUzytkownik = administrator.uzytkownik_idUzytkownik LEFT JOIN operator ON idUzytkownik = operator.uzytkownik_idUzytkownik LEFT JOIN klientfirmowy ON idUzytkownik = klientfirmowy.uzytkownik_idUzytkownik LEFT JOIN klientprywatny ON idUzytkownik = klientprywatny.uzytkownik_idUzytkownik LEFT JOIN rola ON rola_idRola = idRola WHERE login = ? AND haslo = ?");
			query.setString(1, login);
			query.setString(2, haslo);
			if (query.execute()) {
				ResultSet rs = query.executeQuery();
				if (rs.next()) {
					UserZalogowany uz = new UserZalogowany();
					uz.setIdUzytkownik(rs.getInt(1));
					uz.setIdRola(rs.getInt(2));
					uz.setIdOperator(rs.getInt(3));
					uz.setRola(rs.getString(4));
					uz.setNazwaFirmy(rs.getString(5));
					uz.setImieKlienta(rs.getString(6));
					uz.setNazwiskoKlienta(rs.getString(7));
					uz.setImieOperatora(rs.getString(8));
					uz.setNazwiskoOperatora(rs.getString(9));
					uz.setImieAdministratora(rs.getString(10));
					uz.setNazwiskoAdministratora(rs.getString(11));
					uz.setDataLogowania(LocalDateTime.now());
					return uz;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
}
