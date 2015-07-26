/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.persistency.jdbc;

import app.domain.users.Email;
import app.domain.users.User;
import app.domain.utils.HashedString;
import app.exceptions.PersistencyException;
import app.persistency.DataAccessProvider;
import app.persistency.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jonathan
 */
public class JDBCUserDAOTest {
    private DataAccessProvider dap;
    private static final int COUNT = 1000;
    
    public JDBCUserDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException, ClassNotFoundException, SQLException, PersistencyException {
        // load config
        Properties config = new Properties();
        config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.test.properties"));
        
        // init persistency
        String host = config.getProperty("jdbc.host");
        String username = config.getProperty("jdbc.username");
        String password = config.getProperty("jdbc.password");
        String db = config.getProperty("jdbc.db");
        JDBCDataAccessContext dac = new JDBCDataAccessContext(host,
            username,
            password,
            db);
        this.dap = new JDBCDataAccessProvider(dac);
        
        // clear table
        dap.getUserDAO().clear();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class JDBCUserDAO.
     */
    @Test
    public void testCreate() throws Exception {
        UserDAO dao = dap.getUserDAO();
        for(int i = 0; i < COUNT; i++) {
            User user = new User(i, "test" + i,
                    new HashedString("pass" + i, false),
                    new Email("test" + i + "@gmail.com"));
            dao.create(user);
            
            User user2 = dao.get(new Long(i));
            assertEquals(user, user2);
        }
        
        for(int i = 0; i < COUNT; i++) {
            assertTrue(dao.exists(new Long(i)));
            assertNotNull(dao.get(new Long(i)));
        }
        
        assertEquals(dao.getAll().size(), COUNT);
        
        for(int i = 0; i < COUNT; i++) {
            User user = new User(i, "test" + i,
                    new HashedString("yolo" + i, false),
                    new Email("shit" + i + "@gmail.com"));
            dao.update(user);
            
            User user2 = dao.get(new Long(i));
            assertEquals(user, user2);
        }
        
        for(int i = 0; i < COUNT; i++) {
            dao.delete(new Long(i));
        }
        
        for(int i = 0; i < COUNT; i++) {
            assertFalse(dao.exists(new Long(i)));
        }
    }
    
}
