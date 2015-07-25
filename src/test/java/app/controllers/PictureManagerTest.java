/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import app.domain.pictures.Picture;
import app.domain.pictures.UserPicturesAssociation;
import app.domain.users.User;
import java.math.BigInteger;
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
        System.out.println("addEntry");
        User user = null;
        Picture picture = null;
        PictureManager instance = null;
        instance.addEntry(user, picture);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPictures method, of class PictureManager.
     */
    @Test
    public void testGetPictures() throws Exception {
        System.out.println("getPictures");
        String username = "";
        PictureManager instance = null;
        UserPicturesAssociation expResult = null;
        UserPicturesAssociation result = instance.getPictures(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRandomPicture method, of class PictureManager.
     */
    @Test
    public void testGetRandomPicture() throws Exception {
        System.out.println("getRandomPicture");
        User exclude = null;
        PictureManager instance = null;
        Picture expResult = null;
        Picture result = instance.getRandomPicture(exclude);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of upload method, of class PictureManager.
     */
    @Test
    public void testUpload() throws Exception {
        System.out.println("upload");
        User user = null;
        MultipartFile file = null;
        PictureManager instance = null;
        instance.upload(user, file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of likePicture method, of class PictureManager.
     */
    @Test
    public void testLikePicture() throws Exception {
        System.out.println("likePicture");
        BigInteger id = null;
        PictureManager instance = null;
        instance.likePicture(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dislikePicture method, of class PictureManager.
     */
    @Test
    public void testDislikePicture() throws Exception {
        System.out.println("dislikePicture");
        BigInteger id = null;
        PictureManager instance = null;
        instance.dislikePicture(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
