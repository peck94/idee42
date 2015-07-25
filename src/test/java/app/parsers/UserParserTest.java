/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.parsers;

import app.domain.users.User;
import java.text.ParseException;
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
public class UserParserTest {
    
    public UserParserTest() {
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
     * Test of toJson method, of class UserParser.
     */
    @Test
    public void testToJson() throws ParseException {
        System.out.println("toJson");
        User user = null;
        UserParser instance = new UserParser();
        String expResult = "";
        String result = instance.toJson(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromJson method, of class UserParser.
     */
    @Test
    public void testFromJson() throws ParseException {
        System.out.println("fromJson");
        String json = "";
        UserParser instance = new UserParser();
        User expResult = null;
        User result = instance.fromJson(json);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
