package app.domain.users;

import java.util.Date;

/**
 * Represents a session of a logged-in user.
 * TODO: prevent session hijacking.
 * @author jonathan
 */
public class Session {
    // last updated
    private Date date;
    // user associated to this session
    private final User user;
    // store key
    private final SessionKey key;
    
    public Session(User user, SessionKey key) {
        this.user = user;
        this.date = new Date();
        this.key = key;
    }
    
    public SessionKey getKey() {
        return key;
    }
    
    public User getUser() {
        return user;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void update() {
        this.date = new Date();
    }
}
