/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

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
public class UserManagerTest {
    
    public UserManagerTest() {
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
     * Test of getUser method, of class UserManager.
     */
    @Test
    public void testGetUser() throws Exception {
        System.out.println("getUser");
        String username = "";
        UserManager instance = null;
        User expResult = null;
        User result = instance.getUser(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addUser method, of class UserManager.
     */
    @Test
    public void testAddUser() throws Exception {
        System.out.println("addUser");
        User user = null;
        UserManager instance = null;
        instance.addUser(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exists method, of class UserManager.
     */
    @Test
    public void testExists() {
        System.out.println("exists");
        String username = "";
        UserManager instance = null;
        boolean expResult = false;
        boolean result = instance.exists(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsers method, of class UserManager.
     */
    @Test
    public void testGetUsers() {
        System.out.println("getUsers");
        UserManager instance = null;
        List<User> expResult = null;
        List<User> result = instance.getUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
