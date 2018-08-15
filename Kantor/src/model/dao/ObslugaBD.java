package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ObslugaBD {

	
	Connection conn = null;
	
	public ObslugaBD() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kantor?serverTimezone=UTC", "andrzej", "1234");
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pobierzDane() {
		try {
			Statement stat = conn.createStatement();
			stat.executeQuery("select * from uzytkownik");
				System.out.println(true);
			ResultSet result = stat.getResultSet();
			System.out.println(result.first());	
			System.out.println(result.getString(1));
			
			

			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
