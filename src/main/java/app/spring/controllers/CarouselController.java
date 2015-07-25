package app.spring.controllers;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.domain.pictures.Picture;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.SpringException;
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
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private PictureManager pictureManager;
    
    /**
     * Retrieve a random picture
     * @param auth Session key
     * @return Random picture
     * @throws app.exceptions.SpringException If a picture can't be retrieved
     */
    @RequestMapping(method=GET)
    public Picture random(
        @RequestHeader(value="auth") String auth) throws SpringException {
        if(sessionManager.isLoggedIn(auth)) {
            try{
                User user = sessionManager.getUser(auth);
                return pictureManager.getRandomPicture(user);
            }catch(ControllerException ex) {
                throw new SpringException(ex);
            }
        }else{
            throw new SpringException("Invalid session");
        }
    }
}
