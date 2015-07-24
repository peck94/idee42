package app.domain.pictures;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Represents a picture somebody uploaded to the application.
 * @author jonathan
 */
public class Picture {
    // store the actual image
    private Image image;
    // store time of upload
    private Date date;
    
    /**
     * Create picture from file
     * @param file File to create image from
     * @throws IOException 
     */
    public Picture(MultipartFile file) throws IOException {
        this.image = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
    }
    
    public Image getImage() {
        return image;
    }
    
    public Date getDate() {
        return date;
    }
}
