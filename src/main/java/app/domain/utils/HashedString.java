package app.domain.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class mediates between pure and hashed versions of strings.
 * @author jonathan
 */
public class HashedString {
    // store hashed string
    private String hashed;
    
    /**
     * Create a new hashed string
     * @param input Input string
     * @param hashed Whether or not the input is already hashed
     * @throws NoSuchAlgorithmException 
     */
    public HashedString(String input, boolean hashed) throws NoSuchAlgorithmException {
        if(hashed) {
            this.hashed = input;
        }else{
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(input.getBytes());
            
            StringBuilder buffer = new StringBuilder();
            for(byte b: digest.digest()) {
                buffer.append(String.format("%02X", b));
            }
            this.hashed = buffer.toString();
        }
    }
    
    @Override
    public String toString() {
        return hashed;
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof HashedString) && !(o instanceof String)) {
            return false;
        }
        
        return toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return hashed.hashCode();
    }
}
