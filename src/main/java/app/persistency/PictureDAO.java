package app.persistency;

import app.domain.pictures.Picture;
import java.math.BigInteger;

/**
 * Interface for pictures.
 * @author jonathan
 */
public interface PictureDAO extends DataAccessObject<Picture, BigInteger> {
}
