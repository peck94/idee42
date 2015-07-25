package app.exceptions;

/**
 * Exceptions from persistency layer.
 * @author jonathan
 */
public class PersistencyException extends AppException {

    public PersistencyException(String message) {
        super(message);
    }
    
    public PersistencyException(Exception message) {
        super(message);
    }
    
}
