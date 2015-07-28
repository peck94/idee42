package app.controllers;

import app.domain.PersistencyCommunicator;
import app.domain.pictures.Picture;
import app.domain.pictures.UserPicturesAssociation;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.DomainException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.web.multipart.MultipartFile;
/**
 * Stores all pictures and their associated users.
 * @author jonathan
 */
public class PictureManager extends Controller {
    // store user manager
    private final UserManager userManager;
    // store associations
    private final Map<String, UserPicturesAssociation> assocs;
    // store pictures
    private final Map<Long, Picture> pictures;
    // store RNG
    private final Random rng;
    // store validator thread
    private Thread validator;
    // store picture timeout
    private final long timeout;
    
    /**
     * This class ensures that expired pictures get marked as such.
     */
    private class Validator implements Runnable {
        @Override
        public void run() {
            while(pictures.size() > 0) {
                long minDiff = timeout;
                Date d = new Date();
                for(Picture p: pictures.values()) {
                    // don't bother with expired ones
                    if(p.isExpired()) {
                        continue;
                    }
                    
                    // check expiry
                    long diff = d.getTime() - p.getDate().getTime();
                    if(diff > timeout) {
                        try{
                            p.setExpired(true);
                        }catch(DomainException e) {
                            System.out.println(e);
                        }
                    }else{
                        minDiff = Math.min(minDiff, diff);
                    }
                }
                
                try{
                    Thread.sleep(minDiff);
                }catch(InterruptedException e) {
                    System.out.println(e);
                    break;
                }
            }
        }
    }
    
    /**
     * Create new picture manager
     * @param communicator
     * @param userManager User manager
     * @param timeout Picture lifetime
     */
    public PictureManager(PersistencyCommunicator communicator, UserManager userManager, long timeout) {
        super(communicator);
        this.userManager = userManager;
        assocs = new HashMap<>();
        pictures = new HashMap<>();
        rng = new Random();
        validator = new Thread();
        this.timeout = timeout;
    }
    
    /**
     * Add an entry to the map
     * @param picture Picture to add
     * @throws app.exceptions.ControllerException
     */
    public void addEntry(Picture picture) throws ControllerException {
        // retrieve the owner of the picture
        User user = picture.getOwner();
        // check whether this person exists
        if(!userManager.exists(user.getUsername())) {
            throw new ControllerException("Unknown user: " + user.getUsername());
        }

        // check for existing association
        if(!assocs.containsKey(user.getUsername())) {
            // you must be new here
            assocs.put(user.getUsername(),
                    new UserPicturesAssociation(user));
        }
        
        // try to update persistency
        try{
            getCommunicator().registerPicture(picture);
            
            /*
            * We need to check the expiry here, because Validator doesn't take
            * into account the possibility of adding pictures after their
            * expiry date. This should only happen during init.
            */
            long diff = (new Date()).getTime() - picture.getDate().getTime();
            if(!picture.isExpired() && diff > timeout) {
                picture.setExpired(true);
            }
        }catch(DomainException e) {
            throw new ControllerException(e);
        }
        
        // add picture to existing association
        assocs.get(user.getUsername()).addPicture(picture);
        // put picture in repo
        pictures.put(picture.getId(), picture);
        
        // start validator if necessary
        if(!validator.isAlive()) {
            validator = new Thread(new Validator());
            validator.start();
        }
    }
    
    /**
     * Get all pictures associated with a certain user.
     * @param username Name of user
     * @return Association of pictures
     * @throws app.exceptions.ControllerException If the user doesn't have any pictures
     */
    public UserPicturesAssociation getPictures(String username)
        throws ControllerException {
        // check whether we have this guy on record
        if(assocs.containsKey(username)) {
            return assocs.get(username);
        }
        
        // apparently not
        throw new ControllerException("No pictures for user " + username);
    }
    
    /**
     * Check if a user has any pictures.
     * @param username Username to check
     * @return 
     */
    public boolean hasPictures(String username) {
        return assocs.containsKey(username) &&
                assocs.get(username).getTarget().size() > 0;
    }
    
    /**
     * Get a random picture.
     * The pictures that belong to the user requesting the random picture are
     * excluded, so that users will never rate their own pictures.
     * TODO: fix this algorithm
     * @param exclude User to exclude
     * @return Random picture not belonging to the excluded user
     * @throws app.exceptions.ControllerException If a random picture can't be retrieved
     */
    public Picture getRandomPicture(User exclude) throws ControllerException {
        // prevent infinite loop
        List<User> users = userManager.getUsers();
        if(users.size() < 2) {
            throw new ControllerException("Not enough users for random picture");
        }
        
        // choose a random picture not owned by excluded user
        List<Picture> pics = new ArrayList<>(pictures.values());
        // verify that we haven't already judged every other picture
        boolean found = false;
        for(Picture p: pics) {
            /*
            * Requirements:
            * 1. different owner from exclude;
            * 2. not expired yet;
            * 3. not yet (dis)liked by exclude.
            */
            if(p.getOwner().getId() != exclude.getId() && !p.isExpired() &&
                    !p.getActors().contains(exclude)) {
                found = true;
                break;
            }
        }
        if(!found) {
            throw new ControllerException("No pictures available at the moment");
        }
        
        // return random pic
        Picture pic;
        do{
            pic = pics.get(rng.nextInt(pics.size()));
        }while(pic.getOwner().equals(exclude));
        
        return pic;
    }
    
    /**
     * Upload a file
     * @param user Owner
     * @param file Image to upload
     * @throws IOException 
     * @throws app.exceptions.ControllerException 
     */
    public void upload(User user, MultipartFile file) throws IOException, ControllerException {
        Picture picture = new Picture(-1, file, user);
        addEntry(picture);
    }
    
    /**
     * Get picture by ID
     * @param id ID of picture
     * @return Picture with this ID
     * @throws ControllerException Picture not found
     */
    public Picture getPictureById(long id) throws ControllerException {
        // fetch the picture from our repo
        if(pictures.containsKey(id)) {
            return pictures.get(id);
        }else{
            throw new ControllerException("No such picture: " + id);
        }
    }
    
    /**
     * Like a picture
     * @param user User that dislikes the picture
     * @param id ID of picture to like
     * @throws app.exceptions.ControllerException
     */
    public void likePicture(User user, long id) throws ControllerException {
        try{
            getPictureById(id).like(user);
        }catch(DomainException e) {
            throw new ControllerException(e);
        }
    }
    
    /**
     * Dislike a picture
     * @param user User that dislikes the picture
     * @param id ID of picture to dislike
     * @throws app.exceptions.ControllerException
     */
    public void dislikePicture(User user, long id) throws ControllerException {
        try{
            getPictureById(id).dislike(user);
        }catch(DomainException e) {
            throw new ControllerException(e);
        }
    }
    
    /**
     * Delete picture by ID.
     * @param pic The picture
     * @throws app.exceptions.ControllerException
     */
    public void deletePicture(Picture pic) throws ControllerException {
        try{
            assocs.get(pic.getOwner().getUsername()).deletePicture(pic);
            pictures.remove(pic.getId());
            pic.delete();
        }catch(DomainException e) {
            throw new ControllerException(e);
        }
    }
}
