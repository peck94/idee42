package app.persistency.jdbc;

import app.persistency.DataAccessProvider;
import app.persistency.PictureDAO;
import app.persistency.UserDAO;

/**
 * JDBC implementation of DAP.
 * @author jonathan
 */
public class JDBCDataAccessProvider implements DataAccessProvider {
    // store DAOs
    private UserDAO userDAO;
    private PictureDAO pictureDAO;
    // store DAC
    private final JDBCDataAccessContext dac;
    
    public JDBCDataAccessProvider(JDBCDataAccessContext dac) {
        this.dac = dac;
    }

    @Override
    public UserDAO getUserDAO() {
        if(userDAO == null) {
            userDAO = new JDBCUserDAO(dac.getConnection());
        }
        
        return userDAO;
    }

    @Override
    public PictureDAO getPictureDAO() {
        if(pictureDAO == null) {
            pictureDAO = new JDBCPictureDAO(dac.getConnection());
        }
        
        return pictureDAO;
    }
    
}
