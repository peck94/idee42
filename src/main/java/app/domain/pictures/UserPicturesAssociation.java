package app.domain.pictures;

import app.domain.users.User;
import app.domain.utils.Association;
import java.util.ArrayList;

/**
 * An association between a user and his pictures.
 * @author jonathan
 */
public class UserPicturesAssociation extends Association<User, ArrayList<Picture>> {

    public UserPicturesAssociation(User source) {
        super(source, new ArrayList<>());
    }
    
    @Override
    public ArrayList<Picture> getTarget() {
        return new ArrayList<>(super.getTarget());
    }
    
    /**
     * Add a picture to this association
     * @param picture Picture to add
     */
    public void addPicture(Picture picture) {
        super.getTarget().add(picture);
    }
    
    /**
     * Remove a picture from this association.
     * @param picture Picture to remove
     */
    public void deletePicture(Picture picture) {
        super.getTarget().remove(picture);
    }
}
