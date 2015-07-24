package app.domain.pictures;

import app.domain.users.User;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores all pictures and their associated users.
 * @author jonathan
 */
public class PictureManager {
    // store associations
    private final Map<String, UserPicturesAssociation> assocs;
    
    public PictureManager() {
        assocs = new HashMap<>();
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
}
