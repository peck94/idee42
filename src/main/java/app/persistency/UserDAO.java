package app.persistency;

import app.domain.users.User;

/**
 * Interface for User DAOs.
 * @author jonathan
 */
public interface UserDAO extends DataAccessObject<User, Long> {
}
