package app.controllers;

import app.domain.PersistencyCommunicator;
import app.domain.pictures.Picture;
import app.domain.pictures.UserPicturesAssociation;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.DomainException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
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
    private final Map<BigInteger, Picture> pictures;
    // store RNG
    private final Random rng;
    
    /**
     * Create new picture manager
     * @param communicator
     * @param userManager User manager
     */
    public PictureManager(PersistencyCommunicator communicator, UserManager userManager) {
        super(communicator);
        this.userManager = userManager;
        assocs = new HashMap<>();
        pictures = new HashMap<>();
        rng = new Random();
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
        
        // check for duplicate pictures
        if(pictures.containsKey(picture.getId())) {
            throw new ControllerException("Duplicate picture: " + picture.getId());
        }
        
        // check for existing association
        if(!assocs.containsKey(user.getUsername())) {
            // you must be new here
            assocs.put(user.getUsername(),
                    new UserPicturesAssociation(user));
        }
        
        // add picture to existing association
        assocs.get(user.getUsername()).addPicture(picture);
        // put picture in repo
        pictures.put(picture.getId(), picture);
        
        // try to update persistency
        try{
            getCommunicator().registerPicture(picture);
        }catch(DomainException e) {
            throw new ControllerException(e);
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
        Picture picture = new Picture(file, user);
        addEntry(picture);
    }
    
    /**
     * Get picture by ID
     * @param id ID of picture
     * @return Picture with this ID
     * @throws ControllerException Picture not found
     */
    private Picture getPictureById(BigInteger id) throws ControllerException {
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
    public void likePicture(User user, BigInteger id) throws ControllerException {
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
    public void dislikePicture(User user, BigInteger id) throws ControllerException {
        try{
            getPictureById(id).dislike(user);
        }catch(DomainException e) {
            throw new ControllerException(e);
        }
    }
}
