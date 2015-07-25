package app.exceptions;

/**
 * An exception that occurs in the Spring layer.
 * @author jonathan
 */
public class SpringException extends AppException {
    public SpringException(String message) {
        super(message);
    }
    
    public SpringException(Exception ex) {
        super(ex);
    }
}
