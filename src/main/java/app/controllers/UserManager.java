package app.controllers;

import app.domain.users.User;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manages the list of users.
 * @author jonathan
 */
public class UserManager {
    // store known users
    private Map<String, User> users;
    
    public UserManager() {
        users = new HashMap<>();
    }
    
    /**
     * Get a user by name
     * @param username Username
     * @return User corresponding to username
     */
    public User getUser(String username) {
        if(users.containsKey(username)) {
            return users.get(username);
        }
        
        throw new RuntimeException("Unknown user: " + username);
    }
    
    /**
     * Add a user to the repository
     * @param user User to add
     */
    public void addUser(User user) {
        if(!users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
        }else{
            throw new RuntimeException("User already exists: " + user.getUsername());
        }
    }
    
    /**
     * Check whether a user exists
     * @param username Name of user
     * @return Whether or not user exists
     */
    public boolean exists(String username) {
        return users.containsKey(username);
    }
    
    public Set<User> getUsers() {
        return new HashSet<>(users.values());
    }
}
