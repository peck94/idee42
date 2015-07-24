package app.spring;

import app.controllers.SessionManager;
import app.controllers.UserManager;
import app.domain.users.Email;
import app.domain.users.User;
import app.domain.utils.HashedString;
import java.security.NoSuchAlgorithmException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring main class.
 * @author jonathan
 */
@SpringBootApplication
public class SpringMain {
    private UserManager _userManager;
    private SessionManager _sessionManager;
    
    public SpringMain() throws NoSuchAlgorithmException {
        _userManager = new UserManager();
        _sessionManager = new SessionManager(10 * 60 * 1000);
    }
    
    @Bean
    public UserManager userManager() {
        return _userManager;
    }
    
    @Bean
    public SessionManager sessionManager() {
        return _sessionManager;
    }
}
