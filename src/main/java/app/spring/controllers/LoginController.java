package app.spring.controllers;

import app.controllers.SessionManager;
import app.controllers.UserManager;
import app.domain.users.SessionKey;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.SpringException;
import app.spring.messages.Message;
import app.spring.messages.OkMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @return Session key
     * @throws app.exceptions.SpringException If credentials are invalid
     */
    @RequestMapping(value="/api/login", method=POST)
    public String login(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password)
        throws SpringException{
        try{
            User user = userManager.getUser(username);
            SessionKey key = sessionManager.login(user, password);
            
            return key.toString();
        }catch(Exception e) {
            throw new SpringException("Invalid credentials for " + username);
        }
    }
    
    /**
     * Log a user out
     * @param auth Session key of user
     * @return Status message
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(value="/api/logout", method=POST)
    public Message logout(
        @RequestHeader(value="auth") String auth) throws SpringException {
        try{
            sessionManager.logout(auth);
        
            return new OkMessage();
        }catch(ControllerException ex) {
            throw new SpringException(ex);
        }
    }
}
