package app.exceptions;

/**
 * An exception that occurs in the domain layer.
 * @author jonathan
 */
public class DomainException extends AppException {
    public DomainException(String message) {
        super(message);
    }
    
    public DomainException(Exception ex) {
        super(ex);
    }
}
