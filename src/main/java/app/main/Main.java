package app.main;

import app.spring.SpringMain;
import org.springframework.boot.*;

/**
 * Main Spring controller.
 * This class serves as the main entrypoint of the application.
 * It simply launches Spring.
 * @author jonathan
 */
public class Main {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringMain.class, args);
    }
}
