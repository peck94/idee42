package app.domain.pictures;

import app.domain.Observable;
import app.domain.users.User;
import app.exceptions.DomainException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
    private User owner;
    // store likers and dislikers
    private Set<User> actors;
    
    /**
     * Create picture from file
     * @param file File to create image from
     * @param owner Owner of picture
     * @throws IOException 
     */
    public Picture(MultipartFile file, User owner) throws IOException {
        super();
        this.image = file.getBytes();
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        this.owner = owner;
        this.actors = new HashSet<>();
        
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
     * @param actors Users who liked or disliked the picture
     */
    public Picture(byte[] image, Date date, long likes, long dislikes,
            BigInteger id, User owner, Set<User> actors) {
        this.image = image;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
        this.id = id;
        this.owner = owner;
        this.actors = actors;
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
     * Set owner of picture.
     * NOTE: this method does not update persistency, since it is supposed to be
     * used *only* at init time for populating picture owners with their full
     * user objects. It throws a DomainException should you attempt to set an
     * owner with a different ID than the previous one.
     * @param owner New owner
     * @throws app.exceptions.DomainException
     */
    public void setOwner(User owner) throws DomainException {
        if(this.owner != null && this.owner.getId() != owner.getId()) {
            throw new DomainException("Cannot change the owner of a picture.");
        }
        
        this.owner = owner;
    }
    
    public Set<User> getActors() {
        return new HashSet<>(actors);
    }
    
    /**
     * Cast a like for this picture.
     * Invalidates the model.
     * @param user User who likes this picture
     * @throws app.exceptions.DomainException
     */
    public void like(User user) throws DomainException {
        // check whether this user has already liked/disliked
        if(actors.contains(user)) {
            throw new DomainException("One vote per user per picture!");
        }
        
        // like the picture
        likes++;
        actors.add(user);
        invalidate();
    }
    
    /**
     * Cast a dislike for this picture.
     * Invalidates the model.
     * @param user User who dislikes this picture
     * @throws app.exceptions.DomainException
     */
    public void dislike(User user) throws DomainException {
        // check whether this user has already liked/disliked
        if(actors.contains(user)) {
            throw new DomainException("One vote per user per picture!");
        }
        
        // dislike the picture
        dislikes++;
        actors.add(user);
        invalidate();
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Picture)) {
            return false;
        }
        
        Picture p = (Picture) o;
        return p.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
