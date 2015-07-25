/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.persistency.jdbc;

import app.domain.users.User;
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
public class JDBCUserDAOTest {
    
    public JDBCUserDAOTest() {
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
     * Test of create method, of class JDBCUserDAO.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        User object = null;
        JDBCUserDAO instance = null;
        instance.create(object);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class JDBCUserDAO.
     */
    @Test
    public void testGet() throws Exception {
        System.out.println("get");
        Long id = null;
        JDBCUserDAO instance = null;
        User expResult = null;
        User result = instance.get(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class JDBCUserDAO.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        User newObject = null;
        JDBCUserDAO instance = null;
        instance.update(newObject);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class JDBCUserDAO.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Long id = null;
        JDBCUserDAO instance = null;
        instance.delete(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAll method, of class JDBCUserDAO.
     */
    @Test
    public void testGetAll() throws Exception {
        System.out.println("getAll");
        JDBCUserDAO instance = null;
        List<User> expResult = null;
        List<User> result = instance.getAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exists method, of class JDBCUserDAO.
     */
    @Test
    public void testExists() throws Exception {
        System.out.println("exists");
        Long id = null;
        JDBCUserDAO instance = null;
        boolean expResult = false;
        boolean result = instance.exists(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
