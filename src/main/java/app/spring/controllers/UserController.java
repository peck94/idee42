package app.spring.controllers;

import app.controllers.UserManager;
import app.domain.users.User;
import app.parsers.UserParser;
import app.spring.messages.Message;
import app.spring.messages.OkMessage;
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
    private UserParser parser;
    @Autowired
    private UserManager userManager;
    
    public UserController() {
        parser = new UserParser();
    }
    
    /**
     * Create a new user
     * @param body JSON string representing the user to create
     * @return Status message
     */
    @RequestMapping(method=POST)
    public Message create(@RequestBody String body) {
        User user = parser.fromJson(body);
        userManager.addUser(user);
        
        return new OkMessage();
    }
    
    /**
     * List all users.
     * TODO: remove this method when in production!
     * @return List of users
     */
    @RequestMapping(method=GET)
    public String list() {
        return parser.toJson(userManager.getUsers());
    }
}
