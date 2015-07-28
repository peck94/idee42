package app.spring.controllers;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.domain.pictures.Picture;
import app.domain.users.SessionKey;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.SpringException;
import app.parsers.PictureParser;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles operations on pictures.
 * The following constraints are enforced:
 * <ul>
 * <li>only logged-in users can rate pictures</li>
 * <li>users can't rate their own pictures</li>
 * <li>users can only rate a picture once</li>
 * </ul>
 * @author jonathan
 */
@RestController
public class PictureController {
    // store picture manager
    @Autowired
    private PictureManager pictureManager;
    // store session manager
    @Autowired
    private SessionManager sessionManager;
    // parser
    private PictureParser parser;
    
    public PictureController() {
        parser = new PictureParser();
    }
    
    /**
     * Send a like to a picture
     * @param id ID of picture to like
     * @param auth Session token
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(value="/api/like", method=POST)
    public void like(
        @RequestParam(value="id") String id,
        @RequestHeader(value="auth") String auth) throws SpringException {
        // check whether the user is logged in
        SessionKey key = new SessionKey(auth);
        if(!sessionManager.isLoggedIn(key)) {
            throw new SpringException("You are not logged in.");
        }
        
        // attempt to like the picture
        BigInteger pic = new BigInteger(id);
        try{
            // get the user
            User user = sessionManager.getUser(key);
            pictureManager.likePicture(user, pic);
        }catch(ControllerException e) {
            throw new SpringException(e);
        }
    }
    
    /**
     * Send a dislike to a picture
     * @param id ID of picture to dislike
     * @param auth Session token
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(value="/api/dislike", method=POST)
    public void dislike(
        @RequestParam(value="id") String id,
        @RequestHeader(value="auth") String auth) throws SpringException {
        // check whether the user is logged in
        SessionKey key = new SessionKey(auth);
        if(!sessionManager.isLoggedIn(key)) {
            throw new SpringException("You are not logged in.");
        }
        
        // attempt to dislike the picture
        BigInteger pic = new BigInteger(id);
        try{
            // get the user
            User user = sessionManager.getUser(key);
            pictureManager.dislikePicture(user, pic);
        }catch(ControllerException e) {
            throw new SpringException(e);
        }
    }
    
    /**
     * Get a listing of all pictures owned by a certain user.
     * @param auth Session key
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(value="/api/pictures", method=GET)
    public String list(
        @RequestHeader(value="auth") String auth) throws SpringException {
        SessionKey key = new SessionKey(auth);
        if(!sessionManager.isLoggedIn(key)) {
            throw new SpringException("Invalid session");
        }
        
        try{
            User user = sessionManager.getUser(key);
            List<Picture> pics = pictureManager.getPictures(user.getUsername()).getTarget();
            return parser.toJsonArray(pics);
        }catch(ControllerException | ParseException e) {
            throw new SpringException(e);
        }
    }
}
