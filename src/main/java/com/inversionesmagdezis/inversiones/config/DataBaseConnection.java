

package main.java.com.inversionesmagdezis.inversiones.config;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DataBaseConnection {
 private static Connection conn;

    private DataBaseConnection() {
    }

    public static Connection getDataBaseConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            DriverManager.getConnection(Credentials.URL_DB, Credentials.USER_DB, Credentials.PASS_DB);
        }
        return conn;
    }   
}
