package app.controllers;

import app.domain.PersistencyCommunicator;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.DomainException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the list of users.
 * @author jonathan
 */
public class UserManager extends Controller {
    // store known users
    private final Map<String, User> users;
    
    public UserManager(PersistencyCommunicator communicator) {
        super(communicator);
        users = new HashMap<>();
    }
    
    /**
     * Get a user by name
     * @param username Username
     * @return User corresponding to username
     * @throws app.exceptions.ControllerException
     */
    public User getUser(String username) throws ControllerException {
        if(users.containsKey(username)) {
            return users.get(username);
        }
        
        throw new ControllerException("Unknown user: " + username);
    }

    /**
     * Add a user to the repository
     * @param user User to add
     * @throws app.exceptions.ControllerException
     */
    public void addUser(User user) throws ControllerException {
        if(!users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
            try{
                getCommunicator().registerUser(user);
            }catch(DomainException e) {
                throw new ControllerException(e);
            }
        }else{
            throw new ControllerException("User already exists: " + user.getUsername());
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

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}
