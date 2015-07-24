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
    public void run(String[] args) {
        SpringApplication.run(SpringMain.class, args);
    }
    
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.run(args);
    }
}
