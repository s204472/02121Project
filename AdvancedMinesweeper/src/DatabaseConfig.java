import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
	private String MySQLURL = "jdbc:mysql://localhost:3306/minesweepertest";
	private String databseUserName = "root";
	private String databasePassword = "";
	private Connection con;
	
	public DatabaseConfig() throws SQLException {
		con = DriverManager.getConnection(MySQLURL, databseUserName, databasePassword);
	}
	public Connection getCon() {
		return con;
	}
}
