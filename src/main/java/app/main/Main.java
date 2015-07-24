package app.main;

import app.spring.SpringMain;
import org.springframework.boot.*;

/**
 * Main application class.
 * This class serves as the main entrypoint of the application.
 * It initializes all components.
 * @author jonathan
 */
public class Main {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringMain.class, args);
    }
}
