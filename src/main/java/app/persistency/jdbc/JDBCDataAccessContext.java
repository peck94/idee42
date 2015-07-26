package app.persistency.jdbc;

import app.persistency.DataAccessContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC implementation of DAC.
 * @author jonathan
 */
public class JDBCDataAccessContext implements DataAccessContext {
    // store DB connection
    private Connection conn;
    
    /**
     * Create a new DAC
     * @param host Host to connect to
     * @param username Username
     * @param password Password
     * @param db Database name
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public JDBCDataAccessContext(
            String host,
            String username,
            String password,
            String db) throws ClassNotFoundException, SQLException {
        // load driver
        Class.forName("com.mysql.jdbc.Driver");
        // create connection
        conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db,
                username, password);
    }
    
    public Connection getConnection() {
        return conn;
    }
}
