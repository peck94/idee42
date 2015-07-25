package app.main;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.controllers.UserManager;
import app.domain.PersistencyCommunicator;
import app.persistency.DataAccessProvider;
import app.persistency.jdbc.JDBCDataAccessContext;
import app.persistency.jdbc.JDBCDataAccessProvider;
import java.util.Properties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring main class.
 * @author jonathan
 */
@SpringBootApplication
public class SpringMain {
    private final UserManager _userManager;
    private final SessionManager _sessionManager;
    private final PictureManager _pictureManager;
    private final PersistencyCommunicator communicator;
    
    public SpringMain() throws Exception {
        // load config
        Properties config = new Properties();
        config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        
        // init persistency
        String host = config.getProperty("jdbc.host");
        String username = config.getProperty("jdbc.username");
        String password = config.getProperty("jdbc.password");
        String db = config.getProperty("jdbc.db");
        JDBCDataAccessContext dac = new JDBCDataAccessContext(host,
            username,
            password,
            db);
        DataAccessProvider dap = new JDBCDataAccessProvider(dac);
        
        // init domain
        communicator = new PersistencyCommunicator(dap);
        
        // init controllers
        long timeout = Long.parseLong(config.getProperty("sessions.timeout"));
        _userManager = new UserManager(communicator);
        _sessionManager = new SessionManager(communicator, timeout);
        _pictureManager = new PictureManager(communicator, _userManager);
    }
    
    @Bean
    public UserManager userManager() {
        return _userManager;
    }
    
    @Bean
    public SessionManager sessionManager() {
        return _sessionManager;
    }
    
    @Bean
    public PictureManager pictureManager() {
        return _pictureManager;
    }
}
