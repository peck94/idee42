package app.domain.pictures;

import app.domain.Observable;
import app.domain.users.User;
import app.exceptions.DomainException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

/**
 * Represents a picture somebody uploaded to the application.
 * @author jonathan
 */
public class Picture extends Observable {
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
    // store owner
    private final User owner;
    
    /**
     * Create picture from file
     * @param file File to create image from
     * @throws IOException 
     */
    public Picture(MultipartFile file, User owner) throws IOException {
        super();
        this.image = file.getBytes();
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        this.owner = owner;
        
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
     * @param owner Owner of picture
     */
    public Picture(byte[] image, Date date, long likes, long dislikes, BigInteger id, User owner) {
        this.image = image;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
        this.id = id;
        this.owner = owner;
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
    
    public User getOwner() {
        return owner;
    }
    
    /**
     * Cast a like for this picture.
     * Invalidates the model.
     * @throws app.exceptions.DomainException
     */
    public void like() throws DomainException {
        likes++;
        invalidate();
    }
    
    /**
     * Cast a dislike for this picture.
     * Invalidates the model.
     * @throws app.exceptions.DomainException
     */
    public void dislike() throws DomainException {
        dislikes++;
        invalidate();
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Picture)) {
            return false;
        }
        
        Picture p = (Picture) o;
        return p.getId().equals(getId()) &&
                p.getDate().equals(getDate()) &&
                p.getLikes() == getLikes() &&
                p.getDislikes() == getDislikes() &&
                Arrays.equals(p.getImage(), getImage()) &&
                p.getOwner().equals(getOwner());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
