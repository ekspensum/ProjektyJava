import javax.swing.*;
import java.sql.*;

public class ConnectURL {
    public static void main(String[] args) {

        // Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://LAPTOP-NNVTPJL4\\SQLEXPRESS:1433;" +
                "databaseName=SerwBud;";

        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        int colNum = Integer.parseInt(JOptionPane.showInputDialog("Enter column number: "));
        System.out.println(colNum);

        try {
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl, "UserTest", "andy11");

            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT * FROM kosztorys";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2)+ " " + rs.getString(3)+ " " + rs.getString(4));
            }
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }

}
