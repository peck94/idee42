package app.spring.controllers;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.domain.pictures.Picture;
import app.domain.users.SessionKey;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.SpringException;
import app.parsers.PictureParser;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller handles the distribution of images to users for rating.
 * @author jonathan
 */
@RestController
@RequestMapping(value="/api/carousel")
public class CarouselController {
    // store session manager
    @Autowired
    private SessionManager sessionManager;
    // store picture manager
    @Autowired
    private PictureManager pictureManager;
    // store parser
    private final PictureParser parser;
    
    public CarouselController() {
        parser = new PictureParser();
    }
    
    /**
     * Retrieve a random picture
     * @param auth Session key
     * @return Random picture
     * @throws app.exceptions.SpringException If a picture can't be retrieved
     */
    @RequestMapping(method=GET)
    public String random(
        @RequestHeader(value="auth") String auth) throws SpringException {
        // check whether this token belongs to a logged-in user
        SessionKey key = new SessionKey(auth);
        if(sessionManager.isLoggedIn(key)) {
            // attempt to fetch a random picture
            try{
                User user = sessionManager.getUser(key);
                Picture picture = pictureManager.getRandomPicture(user);
                
                return parser.toJson(picture);
            }catch(ControllerException | ParseException ex) {
                throw new SpringException(ex);
            }
        }else{
            throw new SpringException("Invalid session");
        }
    }
}
