package app.spring.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main Spring controller.
 * @author jonathan
 */
@RestController
public class MainController {
    @RequestMapping("/api")
    public String index() {
        return "Hello, world!";
    }
}
