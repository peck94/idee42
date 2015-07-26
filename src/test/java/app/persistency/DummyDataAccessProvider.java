package app.persistency;

import app.domain.pictures.Picture;
import app.domain.users.User;
import java.math.BigInteger;

/**
 * Dummy DAP for use in unit tests.
 * @author jonathan
 */
public class DummyDataAccessProvider implements DataAccessProvider {

    @Override
    public UserDAO getUserDAO() {
        return null;
    }

    @Override
    public PictureDAO getPictureDAO() {
        return null;
    }
    
}
