package app.spring.controllers;

import app.controllers.PictureManager;
import app.exceptions.ControllerException;
import app.exceptions.SpringException;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles liking and disliking of pictures.
 * @author jonathan
 */
@RestController
public class RateController {
    @Autowired
    private PictureManager pictureManager;
    
    /**
     * Send a like to a picture
     * @param id ID of picture to like
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(value="/api/like", method=POST)
    public void like(
        @RequestParam(value="id") String id) throws SpringException {
        BigInteger pic = new BigInteger(id);
        
        try{
            pictureManager.likePicture(pic);
        }catch(ControllerException e) {
            throw new SpringException(e);
        }
    }
    
    /**
     * Send a dislike to a picture
     * @param id ID of picture to dislike
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(value="/api/dislike", method=POST)
    public void dislike(
        @RequestParam(value="id") String id) throws SpringException {
        BigInteger pic = new BigInteger(id);
        
        try{
            pictureManager.dislikePicture(pic);
        }catch(ControllerException e) {
            throw new SpringException(e);
        }
    }
}
