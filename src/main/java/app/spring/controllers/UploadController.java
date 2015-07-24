package app.spring.controllers;

import app.controllers.PictureManager;
import app.controllers.SessionManager;
import app.domain.pictures.Picture;
import app.domain.users.User;
import app.spring.messages.Message;
import app.spring.messages.OkMessage;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles uploads of pictures.
 * @author jonathan
 */
@RestController
@RequestMapping(value="/api/upload")
public class UploadController {
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private PictureManager pictureManager;
    
    /**
     * Upload an image
     * @param file File to upload
     * @param auth Session key
     * @return Message
     * @throws IOException 
     */
    @RequestMapping(method=POST)
    public Message upload(
        @RequestParam(value="file") MultipartFile file,
        @RequestHeader(value="auth") String auth) throws IOException {
        User user = sessionManager.getUser(auth);
        Picture picture = new Picture(file);
        pictureManager.addEntry(user, picture);
        
        return new OkMessage();
    }
}
