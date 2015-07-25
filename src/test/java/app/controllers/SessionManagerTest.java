/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import app.domain.users.SessionKey;
import app.domain.users.User;
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
public class SessionManagerTest {
    
    public SessionManagerTest() {
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
     * Test of login method, of class SessionManager.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        User user = null;
        String password = "";
        SessionManager instance = null;
        SessionKey expResult = null;
        SessionKey result = instance.login(user, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logout method, of class SessionManager.
     */
    @Test
    public void testLogout() throws Exception {
        System.out.println("logout");
        String auth = "";
        SessionManager instance = null;
        instance.logout(auth);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isLoggedIn method, of class SessionManager.
     */
    @Test
    public void testIsLoggedIn() {
        System.out.println("isLoggedIn");
        String key = "";
        SessionManager instance = null;
        boolean expResult = false;
        boolean result = instance.isLoggedIn(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUser method, of class SessionManager.
     */
    @Test
    public void testGetUser() throws Exception {
        System.out.println("getUser");
        String auth = "";
        SessionManager instance = null;
        User expResult = null;
        User result = instance.getUser(auth);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
