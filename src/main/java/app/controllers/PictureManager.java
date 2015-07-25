package app.controllers;

import app.domain.pictures.Picture;
import app.domain.pictures.UserPicturesAssociation;
import app.domain.users.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores all pictures and their associated users.
 * @author jonathan
 */
public class PictureManager {
    // store associations
    private final Map<String, UserPicturesAssociation> assocs;
    // store usernames
    private final List<String> names;
    
    public PictureManager() {
        assocs = new HashMap<>();
        names = new ArrayList<>();
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
            names.add(user.getUsername());
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
     * Get list of names
     * @return
     */
    public List<String> getNames() {
        return new ArrayList<>(names);
    }
}
