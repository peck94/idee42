package app.persistency.jdbc;

import java.sql.Connection;

/**
 * Base class for all JDBC DAOs.
 * @author jonathan
 */
public class JDBCGenericDAO {
    // store connection
    private final Connection conn;
    
    /**
     * New DAO.
     * @param conn JDBC connection 
     */
    public JDBCGenericDAO(Connection conn) {
        this.conn = conn;
    }
    
    protected Connection getConnection() {
        return conn;
    }
}
