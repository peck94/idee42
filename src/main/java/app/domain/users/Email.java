package app.domain.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an e-mail address.
 * This class encapsulates verification of addresses.
 * @author jonathan
 */
public class Email {
    // regex every email must satisfy
    private static final Pattern pattern;
    // store matcher
    private final Matcher matcher;
    // store e-mail address string
    private final String email;
    
    static{
        // initialize regex
        pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
    }
    
    /**
     * Create a new e-mail address
     * @param email String representation of e-mail address
     */
    public Email(String email) {
        this.email = email;
        this.matcher = pattern.matcher(email);
    }
    
    /**
     * Verify the address.
     * @return True if valid, false otherwise.
     */
    public boolean verify() {
        return matcher.matches();
    }
    
    @Override
    public String toString() {
        return email;
    }
}
