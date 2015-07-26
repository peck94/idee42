package app.spring.controllers;

import app.controllers.UserManager;
import app.domain.users.User;
import app.exceptions.ControllerException;
import app.exceptions.SpringException;
import app.parsers.NewUserParser;
import app.parsers.UserParser;
import app.spring.messages.Message;
import app.spring.messages.OkMessage;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring controller for user operations.
 * @author jonathan
 */
@RestController
@RequestMapping(value="/api/user")
public class UserController {
    // store parser
    private final UserParser parser;
    // store user manager
    @Autowired
    private UserManager userManager;
    
    public UserController() {
        // use NewUserParser because the ID won't be filled in
        parser = new NewUserParser();
    }
    
    /**
     * Create a new user
     * @param body JSON string representing the user to create
     * @return Status message
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(method=POST)
    public Message create(@RequestBody String body) throws SpringException {
        // attempt to create a new user
        try {
            User user = parser.fromJson(body);
            userManager.addUser(user);
        } catch (ControllerException | ParseException ex) {
            throw new SpringException(ex);
        }
        
        return new OkMessage();
    }
    
    /**
     * List all users.
     * TODO: remove this method when in production!
     * @return List of users
     * @throws app.exceptions.SpringException
     */
    @RequestMapping(method=GET)
    public String list() throws SpringException {
        try{
            return parser.toJsonArray(userManager.getUsers());
        }catch(ParseException ex) {
            throw new SpringException(ex);
        }
    }
}
