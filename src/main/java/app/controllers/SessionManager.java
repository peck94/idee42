package app.controllers;

import app.domain.users.Session;
import app.domain.users.SessionKey;
import app.domain.users.User;
import app.domain.utils.HashedString;
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
public class SessionManager {
    // store sessions
    private final LinkedList<Session> sessions;
    private final Map<SessionKey, Session> keys;
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
                    if(diff > timeout) {
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
     * @param timeout Timeout value for session expiration
     */
    public SessionManager(long timeout) {
        sessions = new LinkedList<>();
        keys = new HashMap<>();
        rng = new SecureRandom();
        this.timeout = timeout;
    }
    
    /**
     * Login a user
     * @param user User to login
     * @param password Plaintext password
     * @throws NoSuchAlgorithmException 
     */
    public SessionKey login(User user, String password) throws NoSuchAlgorithmException {
        HashedString hashedPassword = new HashedString(password, false);
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
            throw new RuntimeException("Invalid credentials for " + user.getUsername());
        }
    }
    
    public void logout(String auth) {
        SessionKey key = new SessionKey(auth);
        Session s = keys.get(key);
        
        keys.remove(key);
        sessions.remove(s);
    }
}
