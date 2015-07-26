package app.spring.controllers;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.domain.users.SessionKey;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.SpringException;
import app.spring.messages.Message;
import app.spring.messages.OkMessage;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles uploads of pictures.
 * @author jonathan
 */
@RestController
@RequestMapping(value="/api/upload")
public class UploadController {
    // store session manager
    @Autowired
    private SessionManager sessionManager;
    // store picture manager
    @Autowired
    private PictureManager pictureManager;
    
    /**
     * Upload an image
     * @param file File to upload
     * @param auth Session key
     * @return Message
     * @throws app.exceptions.SpringException For invalid credentials
     */
    @RequestMapping(method=POST)
    public Message upload(
        @RequestParam(value="file") MultipartFile file,
        @RequestHeader(value="auth") String auth) throws SpringException {
        // verify that this user is logged in
        SessionKey key = new SessionKey(auth);
        if(sessionManager.isLoggedIn(key)) {
            // attempt to upload this file
            try{
                User user = sessionManager.getUser(key);
                pictureManager.upload(user, file);
            
                return new OkMessage();
            }catch(ControllerException | IOException ex) {
                throw new SpringException(ex);
            }
        }
        
        throw new SpringException("Invalid session");
    }
}
