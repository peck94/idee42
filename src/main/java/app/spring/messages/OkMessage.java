package app.spring.messages;

/**
 * HTTP 200 OK message.
 * @author jonathan
 */
public class OkMessage extends Message {
    public OkMessage() {
        super("OK", 200);
    }
}
