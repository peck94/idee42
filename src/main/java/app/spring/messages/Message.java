package app.spring.messages;

/**
 * A response message from the Spring controllers.
 * @author jonathan
 */
public class Message {
    // store message
    private final String message;
    // store code (HTTP status code)
    private final int code;
    
    /**
     * Create a new message
     * @param message Textual message
     * @param code HTTP status code
     */
    public Message(String message, int code) {
        this.message = message;
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public int getCode() {
        return code;
    }
}
