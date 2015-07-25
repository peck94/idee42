package app.domain.pictures;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

/**
 * Represents a picture somebody uploaded to the application.
 * @author jonathan
 */
public class Picture {
    // store the actual image
    private final byte[] image;
    // store time of upload
    private final Date date;
    // store number of likes
    private long likes;
    // store number of dislikes
    private long dislikes;
    // store unique id
    private BigInteger id;
    
    /**
     * Create picture from file
     * @param file File to create image from
     * @throws IOException 
     */
    public Picture(MultipartFile file) throws IOException {
        this.image = file.getBytes();
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(file.getBytes());
            this.id = new BigInteger(digest.digest());
        }catch(NoSuchAlgorithmException e) {
            System.out.println(e);
        }
    }
    
    /**
     * Create picture from params
     * @param image Image
     * @param date Date of upload
     * @param likes No. of likes
     * @param dislikes No. of dislikes
     * @param id ID
     */
    public Picture(byte[] image, Date date, long likes, long dislikes, BigInteger id) {
        this.image = image;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
        this.id = id;
    }
    
    public byte[] getImage() {
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
    
    public BigInteger getId() {
        return id;
    }
    
    public void like() {
        likes++;
    }
    
    public void dislike() {
        dislikes++;
    }
}
