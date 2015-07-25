/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.parsers;

import app.domain.users.Email;
import app.domain.users.User;
import app.domain.utils.HashedString;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.security.NoSuchAlgorithmException;
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
    public void testToJson() throws ParseException, NoSuchAlgorithmException {
        User user = new User(0, "test", new HashedString("lol", false), new Email("kaka@pipi.com"));
        UserParser parser = new UserParser();
        
        String json = parser.toJson(user);
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        assertEquals(object.get("id").getAsLong(), user.getId());
        assertEquals(object.get("username").getAsString(), user.getUsername());
        assertEquals(object.get("password").getAsString(), user.getPassword().toString());
        assertEquals(object.get("email").getAsString(), user.getEmail().toString());
    }

    /**
     * Test of fromJson method, of class UserParser.
     */
    @Test
    public void testFromJson() throws ParseException, NoSuchAlgorithmException {
        long id = 0;
        String username = "test";
        String password = "yolo";
        HashedString hashedPassword = new HashedString(password, false);
        String email = "kaka@pipi.com";
        
        JsonObject object = new JsonObject();
        object.addProperty("username", username);
        object.addProperty("password", password);
        object.addProperty("email", email);
        object.addProperty("id", id);
        
        UserParser parser = new UserParser();
        User user = parser.fromJson(object.toString());
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(hashedPassword.toString(), user.getPassword().toString());
        assertEquals(email, user.getEmail().toString());
    }
    
}
