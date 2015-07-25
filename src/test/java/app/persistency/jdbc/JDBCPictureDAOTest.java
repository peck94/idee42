/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.persistency.jdbc;

import app.domain.pictures.Picture;
import java.math.BigInteger;
import java.util.List;
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
    
    public JDBCPictureDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class JDBCPictureDAO.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Picture object = null;
        JDBCPictureDAO instance = null;
        instance.create(object);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class JDBCPictureDAO.
     */
    @Test
    public void testGet() throws Exception {
        System.out.println("get");
        BigInteger id = null;
        JDBCPictureDAO instance = null;
        Picture expResult = null;
        Picture result = instance.get(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class JDBCPictureDAO.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Picture newObject = null;
        JDBCPictureDAO instance = null;
        instance.update(newObject);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class JDBCPictureDAO.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        BigInteger id = null;
        JDBCPictureDAO instance = null;
        instance.delete(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAll method, of class JDBCPictureDAO.
     */
    @Test
    public void testGetAll() throws Exception {
        System.out.println("getAll");
        JDBCPictureDAO instance = null;
        List<Picture> expResult = null;
        List<Picture> result = instance.getAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exists method, of class JDBCPictureDAO.
     */
    @Test
    public void testExists() throws Exception {
        System.out.println("exists");
        BigInteger id = null;
        JDBCPictureDAO instance = null;
        boolean expResult = false;
        boolean result = instance.exists(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
