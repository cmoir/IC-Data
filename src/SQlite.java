import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SQlite {

	static String filename = "ICdata.db";
	static String url = "jdbc:sqlite:E:/Java/sqlite/db/" + filename;
    public static void createNewDatabase(String url) {
    	 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createNewTable(String url) {
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS familyTracker (\n"
                + "	id integer PRIMARY KEY,\n"
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
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return conn;
    }
        
    public static void insert(String name, double networth, double planets, String url) {
        String sql = "INSERT INTO familyTracker(name,networth,planets) VALUES(?,?,?)";
 
        try (Connection conn = connect(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, networth);
            pstmt.setDouble(3, planets);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectAll(){
        String sql = "SELECT id, name, networth, planets FROM familyTracker";
        
        try (Connection conn = connect(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("name") + "\t" +
                                   rs.getDouble("networth") + "\t" +
                				   rs.getDouble("planets"));	
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}

