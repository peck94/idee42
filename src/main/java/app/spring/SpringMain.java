package app.spring;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.controllers.UserManager;
import java.security.NoSuchAlgorithmException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring main class.
 * @author jonathan
 */
@SpringBootApplication
public class SpringMain {
    private final UserManager _userManager;
    private final SessionManager _sessionManager;
    private final PictureManager _pictureManager;
    
    public SpringMain() throws NoSuchAlgorithmException {
        _userManager = new UserManager();
        _sessionManager = new SessionManager(10 * 60 * 1000);
        _pictureManager = new PictureManager(_userManager);
    }
    
    @Bean
    public UserManager userManager() {
        return _userManager;
    }
    
    @Bean
    public SessionManager sessionManager() {
        return _sessionManager;
    }
    
    @Bean
    public PictureManager pictureManager() {
        return _pictureManager;
    }
}
