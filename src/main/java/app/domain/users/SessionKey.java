package app.domain.users;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Represents a session key.
 * @author jonathan
 */
public class SessionKey {
    // store key
    private BigInteger key;
    
    /**
     * Create a new session key
     * @param rng RNG to generate key with
     */
    public SessionKey(SecureRandom rng) {
        key = new BigInteger(512, rng);
    }
    
    /**
     * Create session key from string
     * @param auth String
     */
    public SessionKey(String auth) {
        key = new BigInteger(auth, 16);
    }
    
    public BigInteger getKey() {
        return key;
    }
    
    @Override
    public String toString() {
        return key.toString(16);
    }
    
    @Override
    public int hashCode() {
        return key.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof SessionKey)) {
            return false;
        }
        
        SessionKey s = (SessionKey) o;
        return s.getKey().equals(key);
    }
}
