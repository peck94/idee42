package app.spring.controllers;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.controllers.UserManager;
import app.domain.pictures.Picture;
import app.domain.users.SessionKey;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.SpringException;
import app.parsers.NewUserParser;
import app.parsers.UserParser;
import app.spring.messages.Message;
import app.spring.messages.OkMessage;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring controller for user operations.
 * @author jonathan
 */
@RestController
@RequestMapping(value="/api/user")
public class UserController {
    // store parser
    private final UserParser parser;
    // store user manager
    @Autowired
    private UserManager userManager;
    // store session manager
    @Autowired
    private SessionManager sessionManager;
    // picture manager
    @Autowired
    private PictureManager pictureManager;
    
    public UserController() {
        // use NewUserParser because the ID won't be filled in
        parser = new NewUserParser();
    }
    
    /**
     * Create a new user
     * @param body JSON string representing the user to create
     * @return Status message
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(method=POST)
    public Message create(@RequestBody String body) throws SpringException {
        // attempt to create a new user
        try {
            User user = parser.fromJson(body);
            if(!user.getEmail().verify()) {
                throw new SpringException("Invalid e-mail address.");
            }
            userManager.addUser(user);
        } catch (ControllerException | ParseException ex) {
            throw new SpringException(ex);
        }
        
        return new OkMessage();
    }
    
    /**
     * Delete account.
     * This involves deleting the user and all his pictures.
     * @param auth Session key of account to delete
     * @return Message
     * @throws SpringException 
     */
    @RequestMapping(method=DELETE)
    public Message delete(@RequestHeader(value="auth") String auth) throws SpringException {
        // check session
        SessionKey key = new SessionKey(auth);
        if(!sessionManager.isLoggedIn(key)) {
            throw new SpringException("Invalid session.");
        }
        
        try{
            // delete user
            User user = sessionManager.getUser(key);
            userManager.deleteUser(user);
            // delete all his pictures
            if(pictureManager.hasPictures(user.getUsername())) {
                for(Picture pic: pictureManager.getPictures(user.getUsername()).getTarget()) {
                    pictureManager.deletePicture(pic);
                }
            }
            // logout
            sessionManager.logout(key);
            
            return new OkMessage();
        }catch(ControllerException e) {
            throw new SpringException(e);
        }
    }
}
