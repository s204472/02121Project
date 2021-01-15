import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private String MySQLURL = "jdbc:mysql://localhost:3306/minesweeper";
    private String databaseUserName = "root";
    private String databasePassword = "";
    
    private Connection con;

    public DatabaseConfig() throws SQLException {
        try {
        	con = DriverManager.getConnection(MySQLURL, databaseUserName, databasePassword);
		} catch (SQLException e) {
			System.out.println("Error in connection.");
			e.printStackTrace();
		}
    }
    
    public void addToScoreBoard(String name, int score, int xSize, int ySize, int mines) { 	
    	try {
            if (con != null) {
                String query = String.format("INSERT INTO scoreboard (name, score, xsize, ysize, mines) VALUES (\"%s\", %s, %s, %s, %s)", name, score, xSize, ySize, mines) ;
            	Statement st = con.createStatement();
            	st.executeUpdate(query);
            	st.close();
        	}
    	} catch (Exception e) {
    		System.out.println("Error in creation");
    		e.printStackTrace();
        }
    	
    	

    }
    
    
    public ResultSet getScoreBoard() {
    	try {
            if (con != null) {
                String query = "SELECT * FROM scoreboard";
            	Statement st = con.createStatement();
            	ResultSet rs = st.executeQuery(query);
            	//st.close();
            	
            	return rs;
        	}
    	} catch (Exception e) {
    		System.out.println("Error in get");
            e.printStackTrace();
        }
		return null;
    }
}