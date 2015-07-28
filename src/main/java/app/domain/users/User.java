package app.domain.users;

import app.domain.Observable;
import app.domain.utils.HashedString;
import app.exceptions.DomainException;
import java.security.NoSuchAlgorithmException;

/**
 * Represents a user of the application.
 * Users contain the following information:
 * <ul>
 * <li>id</li>
 * <li>username</li>
 * <li>password</li>
 * <li>e-mail address</li>
 * </ul>
 * @author jonathan
 */
public class User extends Observable {
    // store id
    private long id;
    // store username
    private final String username;
    // store password
    private final HashedString password;
    // store e-mail address
    private final Email email;
    
    /**
     * Create a user
     * @param id ID of user
     * @param username Username
     * @param password Password
     * @param email E-mail address
     */
    public User(long id, String username, HashedString password, Email email) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    /**
     * Create new user by ID only.
     * This is useful for applications that only require the primary key.
     * However, directly using the primary key type outside of the model and
     * persistency is bad practice.
     * @param id ID of user
     * @throws NoSuchAlgorithmException 
     */
    public User(long id) throws NoSuchAlgorithmException {
        super();
        this.id = id;
        this.username = "";
        this.password = new HashedString("", true);
        this.email = new Email("");
    }
    
    public long getId() {
        return id;
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
    
    /**
     * Set the ID.
     * This should only happen upon creation of a new Picture.
     * The convention is that anything with an ID of -1 is new.
     * @param id
     * @throws DomainException 
     */
    public void setId(long id) throws DomainException {
        if(this.id != -1) {
            throw new DomainException("Can't modify the ID after init!");
        }
        
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof User)) {
            return false;
        }
        
        User u = (User) o;
        return u.getId() == getId();
    }

    @Override
    public int hashCode() {
        return (int)getId();
    }
}
