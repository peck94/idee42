/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import app.domain.pictures.Picture;
import app.domain.users.Email;
import app.domain.users.User;
import app.domain.utils.HashedString;
import app.persistency.DummyPersistencyCommunicator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author jonathan
 */
public class PictureManagerTest {
    
    public PictureManagerTest() {
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
     * Test of addEntry method, of class PictureManager.
     */
    @Test
    public void testAddEntry() throws Exception {
        UserManager uman = new UserManager(new DummyPersistencyCommunicator());
        PictureManager pman = new PictureManager(new DummyPersistencyCommunicator(), uman);
        
        User user = new User(0, "shithead", new HashedString("shit", false),
                    new Email("shit@fuck.com"));
        Picture picture = new Picture("shit".getBytes(), new Date(), 10, 5, BigInteger.ONE, user, new HashSet<>());
        
        uman.addUser(user);
        pman.addEntry(picture);
    }

    /**
     * Test of getPictures method, of class PictureManager.
     */
    @Test
    public void testGetPictures() throws Exception {
        UserManager uman = new UserManager(new DummyPersistencyCommunicator());
        PictureManager pman = new PictureManager(new DummyPersistencyCommunicator(), uman);
        
        User user = new User(0, "shithead", new HashedString("shit", false),
                    new Email("shit@fuck.com"));
        Picture picture = new Picture("shit".getBytes(), new Date(), 10, 5, BigInteger.ONE, user, new HashSet<>());
        
        uman.addUser(user);
        pman.addEntry(picture);
        
        assertEquals(pman.getPictures(user.getUsername()).getTarget().size(), 1);
    }

    /**
     * Test of getRandomPicture method, of class PictureManager.
     */
    @Test
    public void testGetRandomPicture() throws Exception {
        UserManager uman = new UserManager(new DummyPersistencyCommunicator());
        PictureManager pman = new PictureManager(new DummyPersistencyCommunicator(), uman);
        
        User user1 = new User(0, "shithead", new HashedString("shit", false),
                    new Email("shit@fuck.com"));
        User user2 = new User(1, "shithead2", new HashedString("shit", false),
                    new Email("shit@fuck.com"));
        Picture picture1 = new Picture("shit1".getBytes(), new Date(), 10, 5, BigInteger.ONE, user1, new HashSet<>());
        Picture picture2 = new Picture("shit2".getBytes(), new Date(), 10, 5, BigInteger.TEN, user2, new HashSet<>());
        
        uman.addUser(user1);
        uman.addUser(user2);
        pman.addEntry(picture1);
        pman.addEntry(picture2);
        
        Picture p1 = pman.getRandomPicture(user1);
        Picture p2 = pman.getRandomPicture(user2);
        assertEquals(p1, picture2);
        assertEquals(p2, picture1);
    }

    /**
     * Test of upload method, of class PictureManager.
     */
    @Test
    public void testUpload() throws Exception {
        UserManager uman = new UserManager(new DummyPersistencyCommunicator());
        PictureManager pman = new PictureManager(new DummyPersistencyCommunicator(), uman);
        
        User user = new User(0, "shithead", new HashedString("shit", false),
                    new Email("shit@fuck.com"));
        Picture picture = new Picture("shit".getBytes(), new Date(), 10, 5, BigInteger.ONE, user, new HashSet<>());
        
        uman.addUser(user);
        pman.upload(user, new MultipartFile() {

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getOriginalFilename() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getContentType() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isEmpty() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public long getSize() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public byte[] getBytes() throws IOException {
                return "fuck".getBytes();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        assertEquals(pman.getPictures(user.getUsername()).getTarget().size(), 1);
    }

    /**
     * Test of likePicture method, of class PictureManager.
     */
    @Test
    public void testLikePicture() throws Exception {
        UserManager uman = new UserManager(new DummyPersistencyCommunicator());
        PictureManager pman = new PictureManager(new DummyPersistencyCommunicator(), uman);
        
        User user1 = new User(0, "shithead1", new HashedString("shit", false),
                    new Email("shit@fuck.com"));
        User user2 = new User(1, "shithead2", new HashedString("shit", false),
                    new Email("shit@fuck.com"));
        int likes = 10;
        Picture picture = new Picture("shit".getBytes(), new Date(), likes, 5, BigInteger.ONE, user1, new HashSet<>());
        
        uman.addUser(user1);
        uman.addUser(user2);
        pman.addEntry(picture);
        pman.likePicture(user2, picture.getId());
        
        Picture p2 = pman.getPictures(user1.getUsername()).getTarget().get(0);
        assertEquals(p2.getLikes(), likes+1);
        assertTrue(p2.getActors().contains(user2));
    }

    /**
     * Test of dislikePicture method, of class PictureManager.
     */
    @Test
    public void testDislikePicture() throws Exception {
        UserManager uman = new UserManager(new DummyPersistencyCommunicator());
        PictureManager pman = new PictureManager(new DummyPersistencyCommunicator(), uman);
        
        User user1 = new User(0, "shithead1", new HashedString("shit", false),
                    new Email("shit@fuck.com"));
        User user2 = new User(1, "shithead2", new HashedString("shit", false),
                    new Email("shit@fuck.com"));
        int dislikes = 5;
        Picture picture = new Picture("shit".getBytes(), new Date(), 10, dislikes, BigInteger.ONE, user1, new HashSet<>());
        
        uman.addUser(user1);
        uman.addUser(user2);
        pman.addEntry(picture);
        pman.dislikePicture(user2, picture.getId());
        
        Picture p2 = pman.getPictures(user1.getUsername()).getTarget().get(0);
        assertEquals(p2.getDislikes(), dislikes+1);
        assertTrue(p2.getActors().contains(user2));
    }
    
}
