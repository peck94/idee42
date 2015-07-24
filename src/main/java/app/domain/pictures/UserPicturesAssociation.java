package app.domain.pictures;

import app.domain.users.User;
import app.domain.utils.Association;
import java.util.LinkedList;

/**
 * An association between a user and his pictures.
 * @author jonathan
 */
public class UserPicturesAssociation extends Association<User, LinkedList<Picture>> {

    public UserPicturesAssociation(User source) {
        super(source, new LinkedList<>());
    }
    
    @Override
    public LinkedList<Picture> getTarget() {
        return new LinkedList<>(super.getTarget());
    }
    
    /**
     * Add a picture to this association
     * @param picture Picture to add
     */
    public void addPicture(Picture picture) {
        super.getTarget().add(picture);
    }
}
