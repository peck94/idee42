package app.controllers;

import app.domain.PersistencyCommunicator;
import app.domain.users.Session;
import app.domain.users.SessionKey;
import app.domain.users.User;
import app.domain.utils.HashedString;
import app.exceptions.ControllerException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Manages sessions of logged-in users.
 * One of the important tasks of this class is to determine when a session
 * expires, which it does after a certain amount of time has passed without any
 * activity associated with that session. This is implemented as follows.
 * The session manager maintains a linked list of sessions, each containing the
 * time they were last used. New sessions are added at the end of the list, and
 * each time a session is used, it is moved to the last position in the list by
 * removing and then re-adding it. This way, sessions are always sorted from
 * least recently used to most recently used. When checking whether a session
 * has expired, the algorithm can loop over the list, deleting any session that
 * is past its due date, and stop as soon as it encounters a session that hasn't
 * expired yet, since all sessions after that one will be more recent.
 * The thread responsible for monitoring session expiration does not run unless
 * there are active sessions, and it sleeps as long as there can't be any
 * expiring sessions. For a given list of sessions, the thread calculates which
 * one is closest to expiring, and sleeps for that period of time.
 * Each session is identified by a session key, which the manager will return
 * upon generating a new session.
 * @author jonathan
 */
public class SessionManager extends Controller {
    // store sessions
    private final LinkedList<Session> sessions;
    private final Map<SessionKey, Session> keys;
    // store user manager
    private final UserManager userManager;
    // store timeout value
    private final long timeout;
    // store rng
    private final SecureRandom rng;
    
    private class Validator implements Runnable {
        @Override
        public void run() {
            long timeout = 0;
            while(sessions.size() > 0) {
                Date d = new Date();
                
                Iterator<Session> itr = sessions.iterator();
                while(itr.hasNext()) {
                    Session s = itr.next();
                    long diff = d.getTime() - s.getDate().getTime();
                    if(diff > SessionManager.this.timeout) {
                        keys.remove(s.getKey());
                        itr.remove();
                    }else{
                        timeout = diff;
                        break;
                    }
                }
                
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                    break;
                }
            }
        }
    }
    
    /**
     * Create a new session manager
     * @param communicator
     * @param userManager User Manager
     * @param timeout Timeout value for session expiration
     */
    public SessionManager(PersistencyCommunicator communicator, UserManager userManager, long timeout) {
        super(communicator);
        sessions = new LinkedList<>();
        keys = new HashMap<>();
        rng = new SecureRandom();
        this.timeout = timeout;
        this.userManager = userManager;
    }
    
    /**
     * Login a user
     * @param username Username
     * @param password Plaintext password
     * @return Session key for user 
     * @throws app.exceptions.ControllerException 
     */
    public SessionKey login(String username, String password) throws ControllerException {
        try {
            HashedString hashedPassword = new HashedString(password, false);
            User user = userManager.getUser(username);
            if(user.getPassword().equals(hashedPassword)) {
                SessionKey key;
                do{
                    key = new SessionKey(rng);
                }while(keys.containsKey(key));

                Session session = new Session(user, key);
                sessions.add(session);
                keys.put(key, session);

                if(sessions.size() == 1) {
                    Thread t = new Thread(new Validator());
                    t.start();
                }

                return key;
            }else{
                throw new ControllerException("Invalid credentials for " + user.getUsername());
            }
        }catch(NoSuchAlgorithmException e) {
            throw new ControllerException(e);
        }
    }
    
    /**
     * Logout a user
     * @param key Auth key
     * @throws app.exceptions.ControllerException
     */
    public void logout(SessionKey key) throws ControllerException {
        if(!keys.containsKey(key)) {
            throw new ControllerException("Invalid key");
        }
        
        Session s = keys.get(key);
        
        keys.remove(key);
        sessions.remove(s);
    }
    
    /**
     * Check whether a key is valid
     * @param key Key to verify
     * @return Whether or not the key corresponds to an existing session
     */
    public boolean isLoggedIn(SessionKey key) {
        return keys.containsKey(key);
    }
    
    /**
     * Get user by session key
     * @param key Session key
     * @return User of key
     * @throws app.exceptions.ControllerException
     */
    public User getUser(SessionKey key) throws ControllerException {
        if(keys.containsKey(key)) {
            return keys.get(key).getUser();
        }else{
            throw new ControllerException("User not logged in: " + key);
        }
    }
}
