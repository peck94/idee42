package app.domain.users;

/**
 * Represents a user of the application.
 * Users contain the following information:
 * <ul>
 * <li>username</li>
 * <li>e-mail address</li>
 * </ul>
 * @author jonathan
 */
public class User {
    // store username
    private final String username;
    // store e-mail address
    private final Email email;
    
    /**
     * Create a user
     * @param username Username
     * @param email E-mail address
     */
    public User(String username, Email email) {
        this.username = username;
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public Email getEmail() {
        return email;
    }
}
