/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import app.domain.users.Email;
import app.domain.users.SessionKey;
import app.domain.users.User;
import app.domain.utils.HashedString;
import app.persistency.DummyPersistencyCommunicator;
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
        SessionManager sman = new SessionManager(new DummyPersistencyCommunicator(), 5);
        String password = "shit";
        User user = new User(0, "shithead", new HashedString(password, false),
                    new Email("shit@fuck.com"));
        
        assertNotNull(sman.login(user, password));
        
        boolean ok = false;
        try{
            sman.login(user, "kak");
        }catch(Exception e) {
            ok = true;
        }
        assertTrue(ok);
    }

    /**
     * Test of logout method, of class SessionManager.
     */
    @Test
    public void testLogout() throws Exception {
        SessionManager sman = new SessionManager(new DummyPersistencyCommunicator(), 5);
        String password = "shit";
        User user = new User(0, "shithead", new HashedString(password, false),
                    new Email("shit@fuck.com"));
        
        SessionKey key = sman.login(user, password);
        sman.logout(key.toString());
        
        assertFalse(sman.isLoggedIn(key.toString()));
    }

    /**
     * Test of isLoggedIn method, of class SessionManager.
     */
    @Test
    public void testIsLoggedIn() throws Exception {
        SessionManager sman = new SessionManager(new DummyPersistencyCommunicator(), 5);
        String password = "shit";
        User user = new User(0, "shithead", new HashedString(password, false),
                    new Email("shit@fuck.com"));
        
        SessionKey key = sman.login(user, password);
        assertTrue(sman.isLoggedIn(key.toString()));
    }

    /**
     * Test of getUser method, of class SessionManager.
     */
    @Test
    public void testGetUser() throws Exception {
        SessionManager sman = new SessionManager(new DummyPersistencyCommunicator(), 5);
        String password = "shit";
        User user = new User(0, "shithead", new HashedString(password, false),
                    new Email("shit@fuck.com"));
        
        SessionKey key = sman.login(user, password);
        assertEquals(sman.getUser(key.toString()), user);
    }
    
}
