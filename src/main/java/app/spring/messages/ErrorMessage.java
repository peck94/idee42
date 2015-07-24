package app.spring.messages;

/**
 * 500 Internal Server Error message.
 * @author jonathan
 */
public class ErrorMessage extends Message {
    public ErrorMessage(String error) {
        super(error, 500);
    }
}
