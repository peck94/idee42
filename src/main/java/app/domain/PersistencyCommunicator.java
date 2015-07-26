package app.domain;

import app.domain.pictures.Picture;
import app.domain.users.User;
import app.exceptions.DomainException;
import app.persistency.DataAccessProvider;
import app.persistency.PicturePersistencyListener;
import app.persistency.UserPersistencyListener;

/**
 * A communicator linking domain and persistency.
 * User and Picture models must be registered here in order to be tracked for
 * changes by persistency.
 * @author jonathan
 */
public class PersistencyCommunicator {
    // store DAP
    private final DataAccessProvider dap;
    // persistency listeners
    private final UserPersistencyListener userListener;
    private final PicturePersistencyListener pictureListener;
    
    /**
     * Create new communicator
     * @param dap DAP
     */
    public PersistencyCommunicator(DataAccessProvider dap) {
        this.dap = dap;
        
        userListener = new UserPersistencyListener(dap.getUserDAO());
        pictureListener = new PicturePersistencyListener(dap.getPictureDAO());
    }
    
    /**
     * Register a picture to persistency
     * @param picture Picture to register
     * @throws app.exceptions.DomainException
     */
    public void registerPicture(Picture picture) throws DomainException {
        // attach listener to the picture
        picture.addListener(pictureListener);
        // update for good measure
        pictureListener.update(picture);
    }
    
    /**
     * Register a user to persistency
     * @param user User to register
     * @throws app.exceptions.DomainException
     */
    public void registerUser(User user) throws DomainException {
        // attach listener to user
        user.addListener(userListener);
        // update for good measure
        userListener.update(user);
    }
}
