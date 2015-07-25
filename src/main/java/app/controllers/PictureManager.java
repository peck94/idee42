package app.controllers;

import app.domain.pictures.Picture;
import app.domain.pictures.UserPicturesAssociation;
import app.domain.users.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.web.multipart.MultipartFile;

/**
 * Stores all pictures and their associated users.
 * @author jonathan
 */
public class PictureManager {
    // store user manager
    private final UserManager userManager;
    // store associations
    private final Map<String, UserPicturesAssociation> assocs;
    // store RNG
    private final Random rng;
    
    /**
     * Create new picture manager
     * @param userManager User manager
     */
    public PictureManager(UserManager userManager) {
        this.userManager = userManager;
        assocs = new HashMap<>();
        rng = new Random();
    }
    
    /**
     * Add an entry to the map
     * @param user Owner of the picture
     * @param picture Picture to add
     */
    public void addEntry(User user, Picture picture) {
        if(!assocs.containsKey(user.getUsername())) {
            assocs.put(user.getUsername(),
                    new UserPicturesAssociation(user));
        }
        
        assocs.get(user.getUsername()).addPicture(picture);
    }
    
    /**
     * Get all pictures associated with a certain user.
     * @param username Name of user
     * @return Association of pictures
     */
    public UserPicturesAssociation getPictures(String username) {
        if(assocs.containsKey(username)) {
            return assocs.get(username);
        }
        
        return null;
    }
    
    /**
     * Get a random picture.
     * The pictures that belong to the user requesting the random picture are
     * excluded, so that users will never rate their own pictures.
     * @param exclude User to exclude
     * @return Random picture not belonging to the excluded user
     */
    public Picture getRandomPicture(User exclude) {
        List<User> users = userManager.getUsers();
        if(users.size() < 2) {
            throw new RuntimeException("Not enough users");
        }
        
        User selected;
        do{
            int index = rng.nextInt(users.size());
            selected = users.get(index);
        }while(selected.getUsername().equals(exclude.getUsername()));
        
        UserPicturesAssociation assoc = assocs.get(selected.getUsername());
        List<Picture> pics = assoc.getTarget();
        int index = rng.nextInt(pics.size());
        
        return pics.get(index);
    }
    
    /**
     * Upload a file
     * @param user Owner
     * @param file Image to upload
     * @throws IOException 
     */
    public void upload(User user, MultipartFile file) throws IOException {
        Picture picture = new Picture(file);
        addEntry(user, picture);
    }
}
