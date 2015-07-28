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
    // store intended IP
    private String ip;
    
    /**
     * Create a new session key
     * @param rng RNG to generate key with
     * @param ip Origin IP
     */
    public SessionKey(SecureRandom rng, String ip) {
        key = new BigInteger(512, rng);
    }
    
    /**
     * Create session key from string
     * @param auth String
     * @param ip Origin IP
     */
    public SessionKey(String auth, String ip) {
        key = new BigInteger(auth, 16);
        this.ip = ip;
    }
    
    public BigInteger getKey() {
        return key;
    }
    
    public String getIp() {
        return ip;
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
        return getKey().equals(s.getKey()) && getIp().equals(s.getIp());
    }
}
