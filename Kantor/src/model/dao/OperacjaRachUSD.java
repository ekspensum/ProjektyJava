package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class OperacjaRachUSD implements Operacja {

	@Override
	public boolean kup(DaneTransakcji dane) {

		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kantor?serverTimezone=Europe/Warsaw", "andrzej",
					"1234");

			conn.setAutoCommit(false);
			PreparedStatement queryUSD = conn.prepareStatement(
					"INSERT INTO zapisyRachUSD(idOperacji, tytulOperacji, kwotaWN, kurs, dataOperacji, rachunekUSD_idRachunekUSD) VALUES(null, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			queryUSD.setString(1, dane.getRodzaj() + " " + dane.getZnak());
			queryUSD.setDouble(2, dane.getKwota());
			queryUSD.setDouble(3, dane.getCena());
			queryUSD.setObject(4, LocalDateTime.now());
			queryUSD.setInt(5, dane.getIdRachunkuUSD());
			if (queryUSD.executeUpdate() > 0) {
				ResultSet rs = queryUSD.getGeneratedKeys();
				rs.next();
				PreparedStatement queryPLN = conn.prepareStatement(
						"INSERT INTO zapisyRachPLN(tytulOperacji, kwotaMA, dataOperacji, rachunekPLN_idRachunekPLN, zapisyRachUSD_idOperacji) VALUES(?, ?, ?, ?, ?)");
				queryPLN.setString(1, dane.getRodzaj() + " " + dane.getZnak());
				queryPLN.setDouble(2, dane.getKwota() * dane.getCena());
				queryPLN.setObject(3, LocalDateTime.now());
				queryPLN.setInt(4, dane.getIdRachunkuPLN());
				queryPLN.setInt(5, rs.getInt(1));
				if (queryPLN.executeUpdate() > 0) {
					conn.commit();
					return true;
				} else {
					queryUSD.close();
					queryPLN.close();
					conn.rollback();
				}

			} else {
				queryUSD.close();
				conn.rollback();
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean sprzedaj(DaneTransakcji dane) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kantor?serverTimezone=Europe/Warsaw", "andrzej",
					"1234");

			conn.setAutoCommit(false);
			PreparedStatement queryUSD = conn.prepareStatement(
					"INSERT INTO zapisyRachUSD(idOperacji, tytulOperacji, kwotaMA, kurs, dataOperacji, rachunekUSD_idRachunekUSD) VALUES(null, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			queryUSD.setString(1, dane.getRodzaj() + " " + dane.getZnak());
			queryUSD.setDouble(2, dane.getKwota());
			queryUSD.setDouble(3, dane.getCena());
			queryUSD.setObject(4, LocalDateTime.now());
			queryUSD.setInt(5, dane.getIdRachunkuUSD());
			if (queryUSD.executeUpdate() > 0) {
				ResultSet rs = queryUSD.getGeneratedKeys();
				rs.next();
				PreparedStatement queryPLN = conn.prepareStatement(
						"INSERT INTO zapisyRachPLN(tytulOperacji, kwotaWN, dataOperacji, rachunekPLN_idRachunekPLN, zapisyRachUSD_idOperacji) VALUES(?, ?, ?, ?, ?)");
				queryPLN.setString(1, dane.getRodzaj() + " " + dane.getZnak());
				queryPLN.setDouble(2, dane.getKwota() * dane.getCena());
				queryPLN.setObject(3, LocalDateTime.now());
				queryPLN.setInt(4, dane.getIdRachunkuPLN());
				queryPLN.setInt(5, rs.getInt(1));
				if (queryPLN.executeUpdate() > 0) {
					queryPLN.close();
					queryUSD.close();
					conn.commit();
				}
				return true;
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}
