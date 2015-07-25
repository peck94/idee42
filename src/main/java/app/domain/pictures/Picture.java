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
    private final Image image;
    // store time of upload
    private final Date date;
    // store number of likes
    private final long likes;
    // store number of dislikes
    private final long dislikes;
    
    /**
     * Create picture from file
     * @param file File to create image from
     * @throws IOException 
     */
    public Picture(MultipartFile file) throws IOException {
        this.image = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
    }
    
    /**
     * Create picture from params
     * @param image Image
     * @param date Date of upload
     * @param likes No. of likes
     * @param dislikes No. of dislikes
     */
    public Picture(Image image, Date date, long likes, long dislikes) {
        this.image = image;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
    }
    
    public Image getImage() {
        return image;
    }
    
    public Date getDate() {
        return date;
    }
    
    public long getLikes() {
        return likes;
    }
    
    public long getDislikes() {
        return dislikes;
    }
}
