package app.persistency;

/**
 * An abstract class for DAPs.
 * @author jonathan
 */
public interface DataAccessProvider {
    UserDAO getUserDAO();
    PictureDAO getPictureDAO();
}
