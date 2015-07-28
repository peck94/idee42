package app.spring;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.controllers.UserManager;
import app.domain.PersistencyCommunicator;
import app.domain.pictures.Picture;
import app.domain.users.User;
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
    // store controllers
    private final UserManager _userManager;
    private final SessionManager _sessionManager;
    private final PictureManager _pictureManager;
    // store persistency communicator
    private final PersistencyCommunicator communicator;
    
    /**
     * This constructor initializes all parts of the application.
     * @throws Exception 
     */
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
        _sessionManager = new SessionManager(communicator, _userManager, timeout);
        _pictureManager = new PictureManager(communicator, _userManager);
        
        /**
         * TODO: the below code is fine for now, but won't scale with many
         * users. Find alternative to loading everything in at init time.
         * Most obvious choice is some sort of caching mechanism.
         */
        
        // load all users
        for(User user: dap.getUserDAO().getAll()) {
            _userManager.addUser(user);
        }
        
        // load all pictures
        for(Picture picture: dap.getPictureDAO().getAll()) {
            // set full owner (only ID is loaded)
            picture.setOwner(dap.getUserDAO().get(picture.getOwner().getId()));
            // add picture
            _pictureManager.addEntry(picture);
        }
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
