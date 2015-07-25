package app.exceptions;

/**
 * Base class for other exceptions.
 * @author jonathan
 */
public abstract class AppException extends Exception {
    public AppException(String message) {
        super(message);
    }
    
    public AppException(Exception ex) {
        super(ex);
    }
}
