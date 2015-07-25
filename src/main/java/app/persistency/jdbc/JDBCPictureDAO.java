package app.persistency.jdbc;

import app.domain.pictures.Picture;
import app.domain.utils.DateConverter;
import app.exceptions.PersistencyException;
import app.persistency.PictureDAO;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * JDBC implementation for PictureDAO.
 * @author jonathan
 */
public class JDBCPictureDAO extends JDBCGenericDAO implements PictureDAO {
    private final String CREATE = "INSERT INTO pictures (id, image, date, likes, dislikes) VALUES (?, ?, ?, ?, ?)";
    private final String GET = "SELECT id, image, date, likes, dislikes FROM pictures WHERE id = ?";
    private final String UPDATE = "UPDATE pictures SET likes = ?, dislikes = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM pictures WHERE id = ? LIMIT 1";
    private final String LIST = "SELECT * FROM pictures";

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
    private Picture convert(ResultSet rs) throws SQLException, ParseException {
        byte[] decoded = Base64.getDecoder().decode(rs.getString(2));
        Picture picture = new Picture(
                decoded,
                DateConverter.toDate(rs.getString(3)),
                rs.getLong(4),
                rs.getLong(5),
                new BigInteger(rs.getString(1))
        );
        
        return picture;
    }

    @Override
    public void create(Picture object) throws PersistencyException {
        String encoded = new String(Base64.getEncoder().encode(object.getImage()));
        
        try(PreparedStatement s = getConnection().prepareStatement(CREATE)) {
            s.setString(1, object.getId().toString());
            s.setString(2, encoded);
            s.setString(3, DateConverter.fromDate(object.getDate()));
            s.setLong(4, object.getLikes());
            s.setLong(5, object.getDislikes());
            s.executeUpdate();
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
                throw new PersistencyException("User not found: " + id);
            }
            
            return convert(rs);
        }catch(SQLException | ParseException e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public void update(BigInteger id, Picture newObject) throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(UPDATE)) {
            s.setLong(1, newObject.getLikes());
            s.setLong(2, newObject.getDislikes());
            s.setString(3, id.toString());
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
        }catch(SQLException | ParseException e) {
            throw new PersistencyException(e);
        }
    }
}
