import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SQlite {
    public static void createNewDatabase(String url) {
    	 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                //System.out.println("The driver name is " + meta.getDriverName());
               // System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createNewTable(String url) {
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS familyTracker (\n"
                + "	id integer PRIMARY KEY,\n"
        		+ " turn real,\n"
                + " family test NOT NULL, \n"
                + "	name text NOT NULL,\n"
                + "	networth real,\n"
                + "	planets real\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
    public static Connection connect(String url) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
           // System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return conn;
    }
        
    public static void insert(int turn, String family, String name, int networth, int planets, Connection conn) {
        String sql = "INSERT INTO familyTracker(turn,family,name,networth,planets) VALUES(?,?,?,?,?)";
 
        try (//Connection conn = connect(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, turn);
        	pstmt.setString(2, family);
            pstmt.setString(3, name);
            pstmt.setInt(4, networth);
            pstmt.setInt(5, planets);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectAll(String url, String family){
        String sql = "SELECT id, turn, family, name, networth, planets FROM familyTracker where family = '"+ family+"'";
        
        try (Connection conn = connect(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("turn") +  "\t" + 
                				   rs.getString("family") + "\t" +
                                   rs.getString("name") + "\t" +
                                   rs.getInt("networth") + "\t" +
                				   rs.getInt("planets"));	
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static int selectTurn(String url, String family){
        String sql = "SELECT MAX(turn) as maxturn FROM familyTracker where family = '"+ family+"'";
        int turn = -33;
        try (Connection conn = connect(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            turn = rs.getInt("maxturn");	
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return turn;
    }



}

