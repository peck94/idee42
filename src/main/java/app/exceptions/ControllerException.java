package app.exceptions;

/**
 * An exception that occurs in the controller layer.
 * @author jonathan
 */
public class ControllerException extends AppException {
    public ControllerException(String message) {
        super(message);
    }
    
    public ControllerException(Exception ex) {
        super(ex);
    }
}
