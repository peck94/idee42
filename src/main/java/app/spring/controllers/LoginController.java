package app.spring.controllers;

import app.controllers.SessionManager;
import app.controllers.UserManager;
import app.domain.users.User;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring controller for login operations.
 * @author jonathan
 */
@RestController
public class LoginController {
    @Autowired
    private UserManager userManager;
    @Autowired
    private SessionManager sessionManager;
    
    /**
     * Login as a user
     * @param username Name of user
     * @param password Password of user 
     */
    @RequestMapping(value="/api/login", method=POST)
    public void login(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password) {
        try{
            User user = userManager.getUser(username);
            sessionManager.login(user, password);
        }catch(Exception e) {
            throw new RuntimeException("Invalid credentials for " + username);
        }
    }
    
    @RequestMapping(value="/api/logout", method=GET)
    public void logout(
        @RequestParam(value="auth") String auth) {
        sessionManager.logout(auth);
    }
}
