package app.spring.controllers;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.domain.pictures.Picture;
import app.domain.pictures.UserPicturesAssociation;
import app.domain.users.User;
import java.util.List;
import java.util.Random;
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
    private Random rng;
    
    public CarouselController() {
        rng = new Random();
    }
    
    /**
     * Retrieve a random picture
     * @param auth Session key
     * @return Random picture
     */
    @RequestMapping(method=GET)
    public Picture random(
        @RequestHeader(value="auth") String auth) {
        List<String> names = pictureManager.getNames();
        if(names.size() < 2) {
            throw new RuntimeException("Not enough users");
        }
        
        String name;
        User user = sessionManager.getUser(auth);
        do{
            int index = rng.nextInt(names.size());
            name = names.get(index);
        }while(user.getUsername().equals(name));
        
        UserPicturesAssociation assoc = pictureManager.getPictures(name);
        List<Picture> pix = assoc.getTarget();
        int index = rng.nextInt(pix.size());
        
        return pix.get(index);
    }
}
