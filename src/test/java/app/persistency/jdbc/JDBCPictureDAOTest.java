/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.persistency.jdbc;

import app.domain.pictures.Picture;
import app.domain.users.User;
import app.exceptions.PersistencyException;
import app.persistency.DataAccessProvider;
import app.persistency.PictureDAO;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
public class JDBCPictureDAOTest {
    private DataAccessProvider dap;
    private static final int COUNT = 1000;
    
    public JDBCPictureDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
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
        dap.getPictureDAO().clear();
    }
    
    @After
    public void tearDown() throws PersistencyException {
        // clear table
        dap.getPictureDAO().clear();
    }

    /**
     * Test of create method, of class JDBCPictureDAO.
     */
    @Test
    public void testCreate() throws Exception {
        PictureDAO dao = dap.getPictureDAO();
        List<Long> ids = new ArrayList<>();
        Date d = new Date();
        for(int i = 0; i < COUNT; i++) {
            Picture picture = new Picture(
                    new String("yolo" + i).getBytes(),
                    d, 2*i, i*i, 0, new User(0), new HashSet<>(), false);
            long id = dao.create(picture);
            ids.add(id);
        }
        
        for(int i = 0; i < COUNT; i++) {
            long id = ids.get(i);
            assertTrue(dao.exists(id));
            assertNotNull(dao.get(id));
        }
        
        assertEquals(dao.getAll().size(), COUNT);
        
        for(int i = 0; i < COUNT; i++) {
            long id = ids.get(i);
            Picture picture = new Picture(
                    new String("yolo" + i).getBytes(),
                    d, i*i, 2*i, id, new User(0), new HashSet<>(), false);
            dao.update(picture);
            
            Picture picture2 = dao.get(id);
            assertEquals(picture, picture2);
        }
        
        for(int i = 0; i < COUNT; i++) {
            long id = ids.get(i);
            dao.delete(id);
        }
        
        for(int i = 0; i < COUNT; i++) {
            long id = ids.get(i);
            assertFalse(dao.exists(id));
        }
    }
    
}
