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
            
            this.hashed = new String(digest.digest());
        }
    }
    
    @Override
    public String toString() {
        return hashed;
    }
}
