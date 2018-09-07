package model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.encje.Administrator;
import model.encje.DaneDolar;
import model.encje.DaneEuro;
import model.encje.DaneFrank;
import model.encje.KlientFirmowy;
import model.encje.KlientPrywatny;
import model.encje.Operator;
import model.encje.RachunekPLN;
import model.encje.Uzytkownik;

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

	public Connection connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			return DriverManager.getConnection("jdbc:mysql://localhost/kantor?serverTimezone=Europe/Warsaw", "andrzej",	"1234");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean dodajRekordDaneDolar(double bid, double ask, int idOperatora, String imieOperatora,
			String nazwiskoOperatora) {
		try {
			PreparedStatement query = conn.prepareStatement(
					"INSERT INTO daneDolar(bid, ask, imieOperatora, nazwiskoOperatora, dataDodania, operator_idOperator) VALUES(?, ?, ?, ?, ?, ?)");
			query.setDouble(1, bid);
			query.setDouble(2, ask);
			query.setString(3, imieOperatora);
			query.setString(4, nazwiskoOperatora);
			query.setObject(5, LocalDateTime.now());
			query.setInt(6, idOperatora);
			if (query.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
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

	public List<DaneDolar> odczytListaDaneDolar() {
		try {
			DaneDolar dd = null;
			List<DaneDolar> listaDaneDolar = new ArrayList<>();
			Statement query = conn.createStatement();
			ResultSet rs = query.executeQuery(
					"SELECT idDolar, znak, bid, ask, daneDolar.dataDodania, imie, nazwisko FROM daneDolar INNER JOIN operator ON operator_idOperator = idOperator ORDER BY daneDolar.dataDodania DESC LIMIT 10");
			while (rs.next()) {
				dd = new DaneDolar();
				dd.setIdDolar(rs.getInt(1));
				dd.setZnak(rs.getString(2));
				dd.setBid(rs.getDouble(3));
				dd.setAsk(rs.getDouble(4));
				dd.setDataDodania(rs.getObject(5, LocalDateTime.class));
				dd.setImieOperatora(rs.getString(6));
				dd.setNazwiskoOperatora(rs.getString(7));
				listaDaneDolar.add(dd);
			}
			return listaDaneDolar;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Boolean dodajRekordDaneEuro(double bid, double ask, int idOperatora, String imieOperatora,
			String nazwiskoOperatora) {
		try {
			PreparedStatement query = conn.prepareStatement(
					"INSERT INTO daneEuro(bid, ask, imieOperatora, nazwiskoOperatora, dataDodania, operator_idOperator) VALUES(?, ?, ?, ?, ?, ?)");
			query.setDouble(1, bid);
			query.setDouble(2, ask);
			query.setString(3, imieOperatora);
			query.setString(4, nazwiskoOperatora);
			query.setObject(5, LocalDateTime.now());
			query.setInt(6, idOperatora);
			if (query.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
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

	public List<DaneEuro> odczytListaDaneEuro() {
		try {
			DaneEuro de = null;
			List<DaneEuro> listaDaneEuro = new ArrayList<>();
			Statement query = conn.createStatement();
			ResultSet rs = query.executeQuery(
					"SELECT idEuro, znak, bid, ask, daneEuro.dataDodania, imie, nazwisko FROM daneEuro INNER JOIN operator ON operator_idOperator = idOperator ORDER BY daneEuro.dataDodania DESC LIMIT 10");
			while (rs.next()) {
				de = new DaneEuro();
				de.setIdEuro(rs.getInt(1));
				de.setZnak(rs.getString(2));
				de.setBid(rs.getDouble(3));
				de.setAsk(rs.getDouble(4));
				de.setDataDodania(rs.getObject(5, LocalDateTime.class));
				de.setImieOperatora(rs.getString(6));
				de.setNazwiskoOperatora(rs.getString(7));
				listaDaneEuro.add(de);
			}
			return listaDaneEuro;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Boolean dodajRekordDaneFrank(double bid, double ask, int idOperatora, String imieOperatora,
			String nazwiskoOperatora) {
		try {
			PreparedStatement query = conn.prepareStatement(
					"INSERT INTO daneFrank(bid, ask, imieOperatora, nazwiskoOperatora, dataDodania, operator_idOperator) VALUES(?, ?, ?, ?, ?, ?)");
			query.setDouble(1, bid);
			query.setDouble(2, ask);
			query.setString(3, imieOperatora);
			query.setString(4, nazwiskoOperatora);
			query.setObject(5, LocalDateTime.now());
			query.setInt(6, idOperatora);
			if (query.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
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

	public List<DaneFrank> odczytListaDaneFrank() {
		try {
			DaneFrank df = null;
			List<DaneFrank> listaDaneFrank = new ArrayList<>();
			Statement query = conn.createStatement();
			ResultSet rs = query.executeQuery(
					"SELECT idFrank, znak, bid, ask, daneFrank.dataDodania, imie, nazwisko FROM daneFrank INNER JOIN operator ON operator_idOperator = idOperator ORDER BY daneFrank.dataDodania DESC LIMIT 10");
			while (rs.next()) {
				df = new DaneFrank();
				df.setIdFrank(rs.getInt(1));
				df.setZnak(rs.getString(2));
				df.setBid(rs.getDouble(3));
				df.setAsk(rs.getDouble(4));
				df.setDataDodania(rs.getObject(5, LocalDateTime.class));
				df.setImieOperatora(rs.getString(6));
				df.setNazwiskoOperatora(rs.getString(7));
				listaDaneFrank.add(df);
			}
			return listaDaneFrank;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public KoszykDaneWalut daneBidAskWalut() {
		try {
			try {
				Statement query = conn.createStatement();
				ResultSet rs = query.executeQuery(
						"SELECT daneDolar.bid, daneDolar.ask, daneEuro.bid, daneEuro.ask, daneFrank.bid, daneFrank.ask FROM daneDolar, daneEuro, daneFrank ORDER BY daneDolar.dataDodania DESC, daneEuro.dataDodania DESC, daneFrank.dataDodania DESC LIMIT 1");
				if (rs.first()) {
					KoszykDaneWalut kdw = new KoszykDaneWalut();
					kdw.setDolarBid(rs.getDouble(1));
					kdw.setDolarAsk(rs.getDouble(2));
					kdw.setEuroBid(rs.getDouble(3));
					kdw.setEuroAsk(rs.getDouble(4));
					kdw.setFrankBid(rs.getDouble(5));
					kdw.setFrankAsk(rs.getDouble(6));
					return kdw;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public UserZalogowany logowanie(String login, String haslo) {
		try {
			PreparedStatement query = conn.prepareStatement(
					"SELECT idUzytkownik, rola_idRola, idOperator, rola, nazwa, KlientPrywatny.imie, KlientPrywatny.nazwisko, operator.imie, operator.nazwisko, administrator.imie, administrator.nazwisko, idAdministrator, usd, eur, chf FROM uzytkownik LEFT JOIN administrator ON idUzytkownik = administrator.uzytkownik_idUzytkownik LEFT JOIN operator ON idUzytkownik = operator.uzytkownik_idUzytkownik LEFT JOIN klientfirmowy ON idUzytkownik = klientfirmowy.uzytkownik_idUzytkownik LEFT JOIN klientprywatny ON idUzytkownik = klientprywatny.uzytkownik_idUzytkownik LEFT JOIN rola ON rola_idRola = idRola WHERE login = ? AND haslo = ?");
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
					uz.setIdAdministrator(rs.getInt(12));
					uz.setUsd(rs.getBoolean(13));
					uz.setEur(rs.getBoolean(14));
					uz.setChf(rs.getBoolean(15));
					uz.setDataLogowania(LocalDateTime.now());
					return uz;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public StanyRachunkow odcztRachKlientFirmowy(int idUzytkownia) {

		StanyRachunkow sr = new StanyRachunkow();
		try {
			PreparedStatement queryPLN = conn.prepareStatement(
					"SELECT SUM(kwotaWN) - SUM(kwotaMA) AS stanPLN, idRachunekPLN, nrRachunku FROM uzytkownik INNER JOIN klientFirmowy ON idUzytkownik = uzytkownik_idUzytkownik INNER JOIN rachunekPLN ON idKlientFirmowy = klientFirmowy_idKlientFirmowy INNER JOIN zapisyRachPLN ON idRachunekPLN = rachunekPLN_idRachunekPLN WHERE uzytkownik.idUzytkownik = ?");
			queryPLN.setInt(1, idUzytkownia);
			if (queryPLN.execute()) {
				ResultSet rs = queryPLN.executeQuery();
				if (rs.first()) {
					sr.setStanPLN(rs.getDouble(1));
					sr.setIdRachunkuPLN(rs.getInt(2));
					sr.setNrRachunkuPLN(rs.getString(3));
				}
			}
			PreparedStatement queryUSD = conn.prepareStatement(
					"SELECT SUM(kwotaWN) - SUM(kwotaMA) AS stanUSD, idRachunekUSD, nrRachunku FROM uzytkownik INNER JOIN klientFirmowy ON idUzytkownik = uzytkownik_idUzytkownik INNER JOIN rachunekUSD ON idKlientFirmowy = klientFirmowy_idKlientFirmowy INNER JOIN zapisyrachUSD ON idRachunekUSD = rachunekUSD_idRachunekUSD WHERE uzytkownik.idUzytkownik = ?");
			queryUSD.setInt(1, idUzytkownia);
			if (queryUSD.execute()) {
				ResultSet rs = queryUSD.executeQuery();
				if (rs.first()) {
					sr.setStanUSD(rs.getDouble(1));
					sr.setIdRachunkuUSD(rs.getInt(2));
					sr.setNrRachunkuUSD(rs.getString(3));
				}
			}
			return sr;
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
		return null;
	}

	public StanyRachunkow odcztRachKlientPrywatny(int idUzytkownia) {

		StanyRachunkow sr = new StanyRachunkow();
		try {
			PreparedStatement queryPLN = conn.prepareStatement(
					"SELECT SUM(kwotaWN) - SUM(kwotaMA) AS stanPLN, idRachunekPLN, nrRachunku, idUzytkownik FROM uzytkownik INNER JOIN klientPrywatny ON idUzytkownik = uzytkownik_idUzytkownik INNER JOIN rachunekPLN ON idKlientPrywatny = klientPrywatny_idKlientPrywatny INNER JOIN zapisyrachPLN ON idRachunekPLN = rachunekPLN_idRachunekPLN WHERE uzytkownik.idUzytkownik = ?");
			queryPLN.setInt(1, idUzytkownia);
			if (queryPLN.execute()) {
				ResultSet rs = queryPLN.executeQuery();
				if (rs.first()) {
					sr.setStanPLN(rs.getDouble(1));
					sr.setIdRachunkuPLN(rs.getInt(2));
					sr.setNrRachunkuPLN(rs.getString(3));
				}
			}
			PreparedStatement queryUSD = conn.prepareStatement(
					"SELECT SUM(kwotaWN) - SUM(kwotaMA) AS stanUSD, idRachunekUSD, nrRachunku, idUzytkownik FROM uzytkownik INNER JOIN klientPrywatny ON idUzytkownik = uzytkownik_idUzytkownik INNER JOIN rachunekUSD ON idKlientPrywatny = klientPrywatny_idKlientPrywatny INNER JOIN zapisyrachUSD ON idRachunekUSD = rachunekUSD_idRachunekUSD WHERE uzytkownik.idUzytkownik = ?");
			queryUSD.setInt(1, idUzytkownia);
			if (queryUSD.execute()) {
				ResultSet rs = queryUSD.executeQuery();
				if (rs.first()) {
					sr.setStanUSD(rs.getDouble(1));
					sr.setIdRachunkuUSD(rs.getInt(2));
					sr.setNrRachunkuUSD(rs.getString(3));
				}
			}
			return sr;
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
		return null;
	}

	public WynikiDodajUzytkownika dodajKlientaFirmowego(Uzytkownik u, KlientFirmowy kf, RachunekPLN pln) {

		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{call dodajKlientaFirmowego(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, u.getLogin());
			proc.setString(2, u.getHaslo());
			proc.setInt(3, u.getIdRola());
			proc.setString(4, kf.getNazwa());
			proc.setString(5, kf.getRegon());
			proc.setString(6, kf.getNip());
			proc.setString(7, kf.getKod());
			proc.setString(8, kf.getMiasto());
			proc.setString(9, kf.getUlica());
			proc.setString(10, kf.getNrDomu());
			proc.setString(11, kf.getNrLokalu());
			proc.setString(12, kf.getImiePracownika());
			proc.setString(13, kf.getNazwiskoPracownika());
			proc.setString(14, kf.getTelefonPracownika());
			proc.setObject(15, LocalDateTime.now());
			proc.setInt(16, kf.getIdAdministrator());
			proc.setString(17, pln.getNrRachunku());
			proc.executeQuery();
			ResultSet rs = proc.getResultSet();
			if (rs.next()) {
				WynikiDodajUzytkownika wdu = new WynikiDodajUzytkownika();
				if (rs.getBoolean(1)) {
					wdu.setDodano(rs.getBoolean(1));
					return wdu;
				} else {
					wdu.setDodano(rs.getBoolean(1));
					wdu.setNazwa(rs.getString(2));
					wdu.setLogin(rs.getString(3));
					wdu.setRegon(rs.getString(4));
					wdu.setNip(rs.getString(5));
					wdu.setNrRachunku(rs.getString(6));
					return wdu;
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
		return null;
	}

	public WynikiDodajUzytkownika dodajKlientaPrywatnego(Uzytkownik u, KlientPrywatny kp, RachunekPLN pln) {

		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{call dodajKlientaPrywatnego(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, u.getLogin());
			proc.setString(2, u.getHaslo());
			proc.setInt(3, u.getIdRola());
			proc.setString(4, kp.getImie());
			proc.setString(5, kp.getNazwisko());
			proc.setString(6, kp.getPesel());
			proc.setString(7, kp.getKod());
			proc.setString(8, kp.getMiasto());
			proc.setString(9, kp.getUlica());
			proc.setString(10, kp.getNrDomu());
			proc.setString(11, kp.getNrLokalu());
			proc.setString(12, kp.getTelefon());
			proc.setObject(13, LocalDateTime.now());
			proc.setInt(14, kp.getIdAdministrator());
			proc.setString(15, pln.getNrRachunku());
			proc.executeQuery();
			ResultSet rs = proc.getResultSet();
			if (rs.next()) {
				WynikiDodajUzytkownika wdu = new WynikiDodajUzytkownika();
				if (rs.getBoolean(1)) {
					wdu.setDodano(rs.getBoolean(1));
					return wdu;
				} else {
					wdu.setDodano(rs.getBoolean(1));
					wdu.setImie(rs.getString(2));
					wdu.setNazwisko(rs.getString(3));
					wdu.setLogin(rs.getString(4));
					wdu.setPesel(rs.getString(5));
					wdu.setNrRachunku(rs.getString(6));
					return wdu;
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

		return null;
	}

	public WynikiDodajUzytkownika dodajOperatora(Uzytkownik u, Operator o) {

		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{call dodajOperatora(?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, u.getLogin());
			proc.setString(2, u.getHaslo());
			proc.setInt(3, u.getIdRola());
			proc.setString(4, o.getImie());
			proc.setString(5, o.getNazwisko());
			proc.setString(6, o.getPesel());
			proc.setString(7, o.getTelefon());
			proc.setObject(8, LocalDateTime.now());
			proc.setInt(9, o.getIdAdministrator());
			proc.executeQuery();
			ResultSet rs = proc.getResultSet();
			if (rs.next()) {
				WynikiDodajUzytkownika wdu = new WynikiDodajUzytkownika();
				if (rs.getBoolean(1)) {
					wdu.setDodano(rs.getBoolean(1));
					return wdu;
				} else {
					wdu.setDodano(rs.getBoolean(1));
					wdu.setImie(rs.getString(2));
					wdu.setNazwisko(rs.getString(3));
					wdu.setLogin(rs.getString(4));
					wdu.setPesel(rs.getString(5));
					return wdu;
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

		return null;
	}

	public WynikiDodajUzytkownika dodajAdministratora(Uzytkownik u, Administrator a) {

		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{call dodajAdministratora(?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, u.getLogin());
			proc.setString(2, u.getHaslo());
			proc.setInt(3, u.getIdRola());
			proc.setString(4, a.getImie());
			proc.setString(5, a.getNazwisko());
			proc.setString(6, a.getPesel());
			proc.setString(7, a.getTelefon());
			proc.setObject(8, LocalDateTime.now());
			proc.setInt(9, a.getIdAdministrator());
			proc.executeQuery();
			ResultSet rs = proc.getResultSet();
			if (rs.next()) {
				WynikiDodajUzytkownika wdu = new WynikiDodajUzytkownika();
				if (rs.getBoolean(1)) {
					wdu.setDodano(rs.getBoolean(1));
					return wdu;
				} else {
					wdu.setDodano(rs.getBoolean(1));
					wdu.setImie(rs.getString(2));
					wdu.setNazwisko(rs.getString(3));
					wdu.setLogin(rs.getString(4));
					wdu.setPesel(rs.getString(5));
					return wdu;
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
		return null;
	}

	public List<KlientWyszukaj> wyszukajKlientaFirmowego(String login, String nazwa, String regon, String nip) {

		KlientWyszukaj kw = null;
		List<KlientWyszukaj> listaKF = new ArrayList<>();
		String sql = "SELECT login, nazwa, regon, nip, idUzytkownik, usd, eur, chf, idRachunekUSD, idRachunekEUR, idRachunekCHF FROM uzytkownik INNER JOIN klientFirmowy ON idUzytkownik = uzytkownik_idUzytkownik LEFT JOIN rachunekUSD ON idKlientFirmowy = rachunekUSD.klientFirmowy_idKlientFirmowy LEFT JOIN rachunekEUR ON idKlientFirmowy = rachunekEUR.klientFirmowy_idKlientFirmowy LEFT JOIN rachunekCHF ON idKlientFirmowy = rachunekCHF.klientFirmowy_idKlientFirmowy WHERE login LIKE '%"
				+ login + "%' AND nazwa LIKE '%" + nazwa + "%' AND regon LIKE '%" + regon + "%' AND nip LIKE '%" + nip
				+ "%' LIMIT 20";
		try {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				kw = new KlientWyszukaj();
				kw.setLogin(rs.getString(1));
				kw.setNazwa(rs.getString(2));
				kw.setRegon(rs.getString(3));
				kw.setNip(rs.getString(4));
				kw.setIdUzytkownik(rs.getInt(5));
				kw.setUsd(rs.getInt(6));
				kw.setEur(rs.getInt(7));
				kw.setChf(rs.getInt(8));
				kw.setIdRachunekUSD(rs.getInt(9));
				kw.setIdRachunekEUR(rs.getInt(10));
				kw.setIdRachunekCHF(rs.getInt(11));
				listaKF.add(kw);
			}
			return listaKF;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return null;
	}

	public List<KlientWyszukaj> wyszukajKlientaPrywatnego(String login, String nazwisko, String pesel) {

		
		KlientWyszukaj kw = null;
		List<KlientWyszukaj> listaKP = new ArrayList<>();
		String sql = "SELECT login, imie, nazwisko, pesel, idUzytkownik, usd, eur, chf, idRachunekUSD, idRachunekEUR, idRachunekCHF FROM uzytkownik INNER JOIN klientPrywatny ON idUzytkownik = uzytkownik_idUzytkownik LEFT JOIN rachunekUSD ON idKlientPrywatny = rachunekUSD.klientPrywatny_idKlientPrywatny LEFT JOIN rachunekEUR ON idKlientPrywatny = rachunekEUR.klientPrywatny_idKlientPrywatny LEFT JOIN rachunekCHF ON idKlientPrywatny = rachunekCHF.klientPrywatny_idKlientPrywatny WHERE login LIKE '%"
				+ login + "%' AND nazwisko LIKE '%" + nazwisko + "%' AND pesel LIKE '%" + pesel + "%' LIMIT 20";
		try {
//			// konieczne wywołanie drugiego połączenia ponieważ pierwsze jest zamykane przy wyszukiwaniu klienta firmowego
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/kantor?serverTimezone=Europe/Warsaw", "andrzej",
//					"1234");
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				kw = new KlientWyszukaj();
				kw.setLogin(rs.getString(1));
				kw.setImie(rs.getString(2));
				kw.setNazwisko(rs.getString(3));
				kw.setPesel(rs.getString(4));
				kw.setIdUzytkownik(rs.getInt(5));
				kw.setUsd(rs.getInt(6));
				kw.setEur(rs.getInt(7));
				kw.setChf(rs.getInt(8));
				kw.setIdRachunekUSD(rs.getInt(9));
				kw.setIdRachunekEUR(rs.getInt(10));
				kw.setIdRachunekCHF(rs.getInt(11));
				listaKP.add(kw);
			}
			return listaKP;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return null;
	}

	public boolean czyPosiadaRachunekKF(String waluta, int idUzytkownika) {
		
		try {
			if(waluta != null) {
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery("SELECT idRachunekUSD, idRachunekEUR, idRachunekCHF FROM uzytkownik INNER JOIN klientFirmowy ON idUzytkownik = uzytkownik_idUzytkownik LEFT JOIN rachunekUSD ON idKlientFirmowy = rachunekUSD.klientFirmowy_idKlientFirmowy LEFT JOIN rachunekEUR ON idKlientFirmowy = rachunekEUR.klientFirmowy_idKlientFirmowy LEFT JOIN rachunekCHF ON idKlientFirmowy = rachunekCHF.klientFirmowy_idKlientFirmowy WHERE idUzytkownik = "+idUzytkownika+"");
				if(rs.first()) {
					if(waluta.equals("0") && rs.getInt(1) > 0) // 0 == USD
						return true;	
					if(waluta.equals("1") && rs.getInt(2) > 0) // 1 == EUR
						return true;
					if(waluta.equals("2") && rs.getInt(3) > 0) // 2 == CHF
						return true;		
				}				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return false;
	}
	
	public boolean czyPosiadaRachunekKP(String waluta, int idUzytkownika) {
		
		try {
			if(waluta != null) {
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery("SELECT idRachunekUSD, idRachunekEUR, idRachunekCHF FROM uzytkownik INNER JOIN klientPrywatny ON idUzytkownik = uzytkownik_idUzytkownik LEFT JOIN rachunekUSD ON idKlientPrywatny = rachunekUSD.klientPrywatny_idKlientPrywatny LEFT JOIN rachunekEUR ON idKlientPrywatny = rachunekEUR.klientPrywatny_idKlientPrywatny LEFT JOIN rachunekCHF ON idKlientPrywatny = rachunekCHF.klientPrywatny_idKlientPrywatny WHERE idUzytkownik = "+idUzytkownika+"");
				if(rs.first()) {
					if(waluta.equals("0") && rs.getInt(1) > 0) // 0 == USD
						return true;	
					if(waluta.equals("1") && rs.getInt(2) > 0) // 1 == EUR
						return true;
					if(waluta.equals("2") && rs.getInt(3) > 0) // 2 == CHF
						return true;		
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return false;
	}

}
