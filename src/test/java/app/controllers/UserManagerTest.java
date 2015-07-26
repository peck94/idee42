/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import app.domain.users.Email;
import app.domain.users.User;
import app.domain.utils.HashedString;
import app.exceptions.ControllerException;
import app.persistency.DummyPersistencyCommunicator;
import java.security.NoSuchAlgorithmException;
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
    @Test(expected=ControllerException.class)
    public void testGetUser() throws ControllerException {
        UserManager man = new UserManager(new DummyPersistencyCommunicator());
        man.getUser("shithead");
    }

    /**
     * Test of addUser method, of class UserManager.
     */
    @Test
    public void testAddUser() throws Exception {
        UserManager man = new UserManager(new DummyPersistencyCommunicator());
        User user = new User(0, "shithead", new HashedString("shit", false),
                new Email("shit@fuck.com"));
        man.addUser(user);
        
        assertEquals(user, man.getUser(user.getUsername()));
    }

    /**
     * Test of exists method, of class UserManager.
     */
    @Test
    public void testExists() throws NoSuchAlgorithmException, ControllerException {
        UserManager man = new UserManager(new DummyPersistencyCommunicator());
        User user = new User(0, "shithead", new HashedString("shit", false),
                new Email("shit@fuck.com"));
        man.addUser(user);
        
        assertTrue(man.exists(user.getUsername()));
        assertFalse(man.exists("fuckwad"));
    }

    /**
     * Test of getUsers method, of class UserManager.
     */
    @Test
    public void testGetUsers() throws NoSuchAlgorithmException, ControllerException {
        UserManager man = new UserManager(new DummyPersistencyCommunicator());
        int count = 1000;
        for(int i = 0; i < count; i++) {
            User user = new User(0, "shithead" + i, new HashedString("shit", false),
                    new Email("shit@fuck.com"));
            man.addUser(user);
        }
        
        assertEquals(man.getUsers().size(), count);
    }
    
}
