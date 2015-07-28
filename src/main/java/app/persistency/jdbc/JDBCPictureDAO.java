package app.persistency.jdbc;

import app.domain.pictures.Picture;
import app.domain.users.User;
import app.domain.utils.DateConverter;
import app.exceptions.PersistencyException;
import app.persistency.PictureDAO;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * JDBC implementation for PictureDAO.
 * @author jonathan
 */
public class JDBCPictureDAO extends JDBCGenericDAO implements PictureDAO {
    private final String CREATE = "INSERT INTO pictures (image, date, likes, dislikes, owner, actors, expired) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String GET = "SELECT id, image, date, likes, dislikes, owner, actors, expired FROM pictures WHERE id = ?";
    private final String UPDATE = "UPDATE pictures SET likes = ?, dislikes = ?, actors = ?, expired = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM pictures WHERE id = ? LIMIT 1";
    private final String LIST = "SELECT * FROM pictures";
    private final String CLEAR = "DELETE FROM pictures";

    public JDBCPictureDAO(Connection conn) {
        super(conn);
    }
    
    /**
     * Convert a ResultSet into a Picture
     * @param rs ResultSet to convert
     * @return User object
     * @throws SQLException
     * @throws NoSuchAlgorithmException 
     */
    private Picture convert(ResultSet rs) throws PersistencyException {
        try{
            Set<User> actors = new HashSet<>();
            if(rs.getString(7).length() > 0) {
                String[] elements = rs.getString(7).split(",");
                for(String element: elements) {
                    actors.add(new User(Long.parseLong(element)));
                }
            }
            
            byte[] decoded = Base64.getDecoder().decode(rs.getString(2));
            Picture picture = new Picture(
                    decoded,
                    DateConverter.toDate(rs.getString(3)),
                    rs.getLong(4),
                    rs.getLong(5),
                    new BigInteger(rs.getString(1)),
                    new User(rs.getLong(6)),
                    actors,
                    rs.getBoolean(7)
            );

            return picture;
        }catch(Exception e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public BigInteger create(Picture object) throws PersistencyException {
        String encoded = new String(Base64.getEncoder().encode(object.getImage()));
        StringJoiner actors = new StringJoiner(",");
        for(User user: object.getActors()) {
            actors.add(user.getId() + "");
        }
        
        try(PreparedStatement s = getConnection().prepareStatement(CREATE,
                new String[]{"id"})) {
            s.setString(1, encoded);
            s.setString(2, DateConverter.fromDate(object.getDate()));
            s.setLong(3, object.getLikes());
            s.setLong(4, object.getDislikes());
            s.setLong(5, object.getOwner().getId());
            s.setString(6, actors.toString());
            s.setBoolean(7, object.isExpired());
            s.executeUpdate();
            
            ResultSet rs = s.getGeneratedKeys();
            rs.next();
            return new BigInteger(rs.getString(1));
        }catch(SQLException e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public Picture get(BigInteger id) throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(GET)) {
            s.setString(1, id.toString());
            
            ResultSet rs = s.executeQuery();
            if(!rs.next()) {
                throw new PersistencyException("Picture not found: " + id);
            }
            
            return convert(rs);
        }catch(Exception e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public void update(Picture newObject) throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(UPDATE)) {
            StringJoiner actors = new StringJoiner(",");
            for(User user: newObject.getActors()) {
                actors.add(user.getId() + "");
            }
            
            s.setLong(1, newObject.getLikes());
            s.setLong(2, newObject.getDislikes());
            s.setString(3, actors.toString());
            s.setString(4, newObject.getId().toString());
            s.setBoolean(5, newObject.isExpired());
            s.executeUpdate();
        }catch(SQLException e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public void delete(BigInteger id) throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(DELETE)) {
            s.setString(1, id.toString());
            s.executeUpdate();
        }catch(SQLException e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public List<Picture> getAll() throws PersistencyException {
        List<Picture> pictures = new ArrayList<>();
        try(PreparedStatement s = getConnection().prepareStatement(LIST)) {
            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                pictures.add(convert(rs));
            }
            
            return pictures;
        }catch(Exception e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public boolean exists(BigInteger id) throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(GET)) {
            s.setString(1, id.toString());
            
            ResultSet rs = s.executeQuery();
            return rs.next();
        }catch(SQLException e) {
            throw new PersistencyException(e);
        }
    }
    
    @Override
    public void clear() throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(CLEAR)) {
            s.executeUpdate();
        }catch(SQLException e) {
            throw new PersistencyException(e);
        }
    }
}
