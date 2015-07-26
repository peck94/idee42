package app.persistency;

import app.domain.PersistencyCommunicator;
import app.domain.pictures.Picture;
import app.domain.users.User;
import app.exceptions.DomainException;

/**
 * Dummy persistency communicator for use in unit tests.
 * @author jonathan
 */
public class DummyPersistencyCommunicator extends PersistencyCommunicator {

    public DummyPersistencyCommunicator() {
        super(new DummyDataAccessProvider());
    }
    
    @Override
    public void registerPicture(Picture picture) throws DomainException {
        return;
    }
    
    @Override
    public void registerUser(User user) throws DomainException {
        return;
    }
}
