package app.persistency.jdbc;

import app.domain.users.Email;
import app.domain.users.User;
import app.domain.utils.HashedString;
import app.exceptions.PersistencyException;
import app.persistency.UserDAO;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation for UserDAO.
 * @author jonathan
 */
public class JDBCUserDAO extends JDBCGenericDAO implements UserDAO {
    private final String CREATE = "INSERT INTO users (id, username, password, email) VALUES (?, ?, ?, ?)";
    private final String GET = "SELECT id, username, password, email FROM users WHERE id = ?";
    private final String UPDATE = "UPDATE users SET password = ?, email = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM users WHERE id = ? LIMIT 1";
    private final String LIST = "SELECT * FROM users";

    public JDBCUserDAO(Connection conn) {
        super(conn);
    }
    
    /**
     * Convert a ResultSet into a User
     * @param rs ResultSet to convert
     * @return User object
     * @throws SQLException
     * @throws NoSuchAlgorithmException 
     */
    private User convert(ResultSet rs) throws SQLException, NoSuchAlgorithmException {
        User user = new User(
                    rs.getLong(1),
                    rs.getString(2),
                    new HashedString(rs.getString(3), true),
                    new Email(rs.getString(4)));
        return user;
    }

    @Override
    public void create(User object) throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(CREATE)) {
            s.setLong(1, object.getId());
            s.setString(2, object.getUsername());
            s.setString(3, object.getPassword().toString());
            s.setString(4, object.getEmail().toString());
            s.executeUpdate();
        }catch(SQLException e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public User get(Long id) throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(GET)) {
            s.setLong(1, id);
            
            ResultSet rs = s.executeQuery();
            if(!rs.next()) {
                throw new PersistencyException("User not found: " + id);
            }
            
            return convert(rs);
        }catch(SQLException | NoSuchAlgorithmException e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public void update(User newObject) throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(UPDATE)) {
            s.setString(1, newObject.getPassword().toString());
            s.setString(2, newObject.getEmail().toString());
            s.setLong(3, newObject.getId());
            s.executeUpdate();
        }catch(SQLException e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public void delete(Long id) throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(DELETE)) {
            s.setLong(1, id);
            s.executeUpdate();
        }catch(SQLException e) {
            throw new PersistencyException(e);
        }
    }

    @Override
    public List<User> getAll() throws PersistencyException {
        List<User> users = new ArrayList<>();
        try(PreparedStatement s = getConnection().prepareStatement(LIST)) {
            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                users.add(convert(rs));
            }
            
            return users;
        }catch(SQLException | NoSuchAlgorithmException e) {
            throw new PersistencyException(e);
        }
    }
    
    @Override
    public boolean exists(Long id) throws PersistencyException {
        try(PreparedStatement s = getConnection().prepareStatement(GET)) {
            s.setString(1, id.toString());
            
            ResultSet rs = s.executeQuery();
            return rs.next();
        }catch(SQLException e) {
            throw new PersistencyException(e);
        }
    }
    
}
