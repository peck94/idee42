package app.spring.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring controller for user operations.
 * @author jonathan
 */
@RestController
@RequestMapping(value="/api/user")
public class UserController {
    @RequestMapping(method=POST)
    public void create(@RequestBody String body) {
        
    }
}
