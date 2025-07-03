package paryle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {
    private static Connection mysqlconfig;

    public static Connection configDB() throws SQLException {
      try {
            String url = "jdbc:mysql://localhost:3306/hotelparyle";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig = DriverManager.getConnection(url, user, pass);
            System.out.println("Database Connected!");
        } catch (SQLException e) {
            System.err.println("Koneksi gagal " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Koneksi ke database gagal: " + e.getMessage());
        }
        return mysqlconfig;
    }

    public static void main(String[] args) {
        try {
            configDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

