/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.persistency.jdbc;

import app.domain.pictures.Picture;
import app.persistency.DataAccessProvider;
import app.persistency.PictureDAO;
import java.math.BigInteger;
import java.util.Date;
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
    public void tearDown() {
    }

    /**
     * Test of create method, of class JDBCPictureDAO.
     */
    @Test
    public void testCreate() throws Exception {
        PictureDAO dao = dap.getPictureDAO();
        Date d = new Date();
        for(int i = 0; i < COUNT; i++) {
            BigInteger id = new BigInteger("" + i);
            Picture picture = new Picture(
                    new String("yolo" + i).getBytes(),
                    d, 2*i, i*i, id);
            dao.create(picture);
            
            Picture picture2 = dao.get(id);
            assertEquals(picture, picture2);
        }
        
        for(int i = 0; i < COUNT; i++) {
            BigInteger id = new BigInteger("" + i);
            assertTrue(dao.exists(id));
            assertNotNull(dao.get(id));
        }
        
        assertEquals(dao.getAll().size(), COUNT);
        
        for(int i = 0; i < COUNT; i++) {
            BigInteger id = new BigInteger("" + i);
            Picture picture = new Picture(
                    new String("yolo" + i).getBytes(),
                    d, i*i, 2*i, id);
            dao.update(picture);
            
            Picture picture2 = dao.get(id);
            assertEquals(picture, picture2);
        }
        
        for(int i = 0; i < COUNT; i++) {
            BigInteger id = new BigInteger("" + i);
            dao.delete(id);
        }
        
        for(int i = 0; i < COUNT; i++) {
            BigInteger id = new BigInteger("" + i);
            assertFalse(dao.exists(id));
        }
    }
    
}
