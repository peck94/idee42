package app.spring.controllers;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.domain.pictures.Picture;
import app.domain.users.SessionKey;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.SpringException;
import app.parsers.PictureParser;
import app.spring.messages.Message;
import app.spring.messages.OkMessage;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
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
    private final PictureParser parser;
    
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
        @RequestHeader(value="auth") String auth,
        HttpServletRequest request) throws SpringException {
        // check whether the user is logged in
        SessionKey key = new SessionKey(auth, request.getRemoteAddr());
        if(!sessionManager.isLoggedIn(key)) {
            throw new SpringException("You are not logged in.");
        }
        
        // attempt to like the picture
        long pic = Long.parseLong(id);
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
        @RequestHeader(value="auth") String auth,
        HttpServletRequest request) throws SpringException {
        // check whether the user is logged in
        SessionKey key = new SessionKey(auth, request.getRemoteAddr());
        if(!sessionManager.isLoggedIn(key)) {
            throw new SpringException("You are not logged in.");
        }
        
        // attempt to dislike the picture
        long pic = Long.parseLong(id);
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
     * This method only returns a list of IDs. Returning a JSON array of all
     * pictures at once would risk overloading the network when a user has many
     * pictures.
     * @param auth Session key
     * @return List of IDs
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(value="/api/pictures", method=GET)
    public List<Long> list(
        @RequestHeader(value="auth") String auth,
            HttpServletRequest request) throws SpringException {
        SessionKey key = new SessionKey(auth, request.getRemoteAddr());
        if(!sessionManager.isLoggedIn(key)) {
            throw new SpringException("Invalid session");
        }
        
        try{
            User user = sessionManager.getUser(key);
            List<Picture> pics = pictureManager.getPictures(user.getUsername()).getTarget();
            List<Long> ids = new ArrayList<>();
            for(Picture pic: pics) {
                ids.add(pic.getId());
            }
            return ids;
        }catch(ControllerException e) {
            throw new SpringException(e);
        }
    }
    
    /**
     * Get a picture by ID.
     * Users can only request their own images.
     * @param auth Session key
     * @param id ID of picture
     * @return Picture
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(value="/api/pictures/{id}", method=GET)
    public String getPicture(
        @RequestHeader(value="auth") String auth,
        @PathVariable(value="id") String id,
        HttpServletRequest request) throws SpringException {
        SessionKey key = new SessionKey(auth, request.getRemoteAddr());
        if(!sessionManager.isLoggedIn(key)) {
            throw new SpringException("Invalid session");
        }
        
        try{
            long idPic = Long.parseLong(id);
            Picture pic = pictureManager.getPictureById(idPic);
            User user = sessionManager.getUser(key);
            if(!pictureManager.getPictures(user.getUsername()).getTarget().contains(pic)) {
                throw new SpringException("Invalid picture.");
            }
            
            return parser.toJson(pic);
        }catch(ControllerException | ParseException e) {
            throw new SpringException(e);
        }
    }
    
    /**
     * Delete picture by ID.
     * Users can only delete their own images.
     * @param auth Session key
     * @param id ID of picture
     * @return Picture
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(value="/api/pictures/{id}", method=DELETE)
    public Message delPicture(
        @RequestHeader(value="auth") String auth,
        @PathVariable(value="id") String id,
        HttpServletRequest request) throws SpringException {
        SessionKey key = new SessionKey(auth, request.getRemoteAddr());
        if(!sessionManager.isLoggedIn(key)) {
            throw new SpringException("Invalid session");
        }
        
        try{
            long idPic = Long.parseLong(id);
            Picture pic = pictureManager.getPictureById(idPic);
            User user = sessionManager.getUser(key);
            if(!pictureManager.getPictures(user.getUsername()).getTarget().contains(pic)) {
                throw new SpringException("Invalid picture.");
            }
            
            pictureManager.deletePicture(pic);
            return new OkMessage();
        }catch(ControllerException e) {
            throw new SpringException(e);
        }
    }
}
