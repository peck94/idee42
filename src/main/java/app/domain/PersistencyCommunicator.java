package app.domain;

import app.domain.pictures.Picture;
import app.domain.users.User;
import app.persistency.DataAccessProvider;
import app.persistency.PersistencyListener;

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
    private final PersistencyListener<User> userListener;
    private final PersistencyListener<Picture> pictureListener;
    
    /**
     * Create new communicator
     * @param dap DAP
     */
    public PersistencyCommunicator(DataAccessProvider dap) {
        this.dap = dap;
        
        userListener = new PersistencyListener<>(dap.getUserDAO());
        pictureListener = new PersistencyListener<>(dap.getPictureDAO());
    }
    
    /**
     * Register a picture to persistency
     * @param picture Picture to register
     */
    public void registerPicture(Picture picture) {
        picture.addListener(pictureListener);
    }
    
    /**
     * Register a user to persistency
     * @param user User to register
     */
    public void registerUser(User user) {
        user.addListener(userListener);
    }
}
