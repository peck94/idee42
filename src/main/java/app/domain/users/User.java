package app.domain.users;

import app.domain.utils.HashedString;

/**
 * Represents a user of the application.
 * Users contain the following information:
 * <ul>
 * <li>username</li>
 * <li>password</li>
 * <li>e-mail address</li>
 * </ul>
 * @author jonathan
 */
public class User {
    // store username
    private final String username;
    // store password
    private final HashedString password;
    // store e-mail address
    private final Email email;
    
    /**
     * Create a user
     * @param username Username
     * @param password Password
     * @param email E-mail address
     */
    public User(String username, HashedString password, Email email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public HashedString getPassword() {
        return password;
    }
    
    public Email getEmail() {
        return email;
    }
}
