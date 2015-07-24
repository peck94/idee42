package app.spring;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;

/**
 * Main Spring controller.
 * This class serves as the main entrypoint of the application.
 * It simply launches Spring.
 * @author jonathan
 */
@Controller
@EnableAutoConfiguration
public class MainController {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainController.class, args);
    }
}
