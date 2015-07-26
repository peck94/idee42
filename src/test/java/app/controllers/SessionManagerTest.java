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
        UserManager uman = new UserManager(new DummyPersistencyCommunicator());
        SessionManager sman = new SessionManager(new DummyPersistencyCommunicator(), uman, 5);
        String password = "shit";
        User user = new User(0, "shithead", new HashedString(password, false),
                    new Email("shit@fuck.com"));
        
        uman.addUser(user);
        assertNotNull(sman.login(user.getUsername(), password));
        
        boolean ok = false;
        try{
            sman.login(user.getUsername(), "kak");
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
        UserManager uman = new UserManager(new DummyPersistencyCommunicator());
        SessionManager sman = new SessionManager(new DummyPersistencyCommunicator(), uman, 5);
        String password = "shit";
        User user = new User(0, "shithead", new HashedString(password, false),
                    new Email("shit@fuck.com"));
        
        uman.addUser(user);
        SessionKey key = sman.login(user.getUsername(), password);
        sman.logout(key);
        
        assertFalse(sman.isLoggedIn(key));
    }

    /**
     * Test of isLoggedIn method, of class SessionManager.
     */
    @Test
    public void testIsLoggedIn() throws Exception {
        UserManager uman = new UserManager(new DummyPersistencyCommunicator());
        SessionManager sman = new SessionManager(new DummyPersistencyCommunicator(), uman, 5);
        String password = "shit";
        User user = new User(0, "shithead", new HashedString(password, false),
                    new Email("shit@fuck.com"));
        
        uman.addUser(user);
        SessionKey key = sman.login(user.getUsername(), password);
        assertTrue(sman.isLoggedIn(key));
    }

    /**
     * Test of getUser method, of class SessionManager.
     */
    @Test
    public void testGetUser() throws Exception {
        UserManager uman = new UserManager(new DummyPersistencyCommunicator());
        long timeout = 5;
        SessionManager sman = new SessionManager(new DummyPersistencyCommunicator(), uman, timeout);
        String password = "shit";
        User user = new User(0, "shithead", new HashedString(password, false),
                    new Email("shit@fuck.com"));
        
        uman.addUser(user);
        SessionKey key = sman.login(user.getUsername(), password);
        assertEquals(sman.getUser(key), user);
        
        Thread.sleep(2*timeout);
        assertFalse(sman.isLoggedIn(key));
    }
    
}
