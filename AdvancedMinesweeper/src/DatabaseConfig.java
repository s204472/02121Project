import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
	private String MySQLURL = "jdbc:mysql://127.0.0.1:3306/minesweepertest";
	private String databaseUserName = "root";
	private String databasePassword = "";
	private Connection con;
	
	public DatabaseConfig() throws SQLException {
		con = DriverManager.getConnection(MySQLURL, databaseUserName, databasePassword);
	}
	public Connection getCon() {
		return con;
	}
}
