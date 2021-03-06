package app.persistency;

import app.exceptions.PersistencyException;
import java.util.List;

/**
 * Generic interface for DAOs.
 * @author jonathan
 * @param <T> Type of object to create a DAO for.
 * @param <K> Type of primary key
 */
public interface DataAccessObject<T, K> {
    /**
     * Create an object in persistency.
     * This will generate a new, unique primary key.
     * @param object Model of object to create
     * @return Primary key of created object
     * @throws app.exceptions.PersistencyException
     */
    K create(T object) throws PersistencyException;
    
    /**
     * Get an object from persistency
     * @param id ID of object to get
     * @return Object with specified ID
     * @throws app.exceptions.PersistencyException
     */
    T get(K id) throws PersistencyException;
    
    /**
     * Update an existing object
     * @param newObject Model of object with new data
     * @throws app.exceptions.PersistencyException
     */
    void update(T newObject) throws PersistencyException;
    
    /**
     * Delete an object from persistency.
     * @param id ID of object to delete
     * @throws app.exceptions.PersistencyException
     */
    void delete(K id) throws PersistencyException;
    
    /**
     * Get all objects from persistency.
     * @return List of all objects
     * @throws app.exceptions.PersistencyException
     */
    List<T> getAll() throws PersistencyException;
    
    /**
     * Check whether an object exists in persistency
     * @param id ID of object
     * @return Whether or not the object exists
     * @throws PersistencyException 
     */
    boolean exists(K id) throws PersistencyException;
    
    /**
     * Delete all entries associated with this DAO.
     * @throws PersistencyException 
     */
    void clear() throws PersistencyException;
}
