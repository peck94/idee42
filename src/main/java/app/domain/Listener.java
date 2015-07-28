package app.domain;

import app.exceptions.DomainException;

/**
 * An interface for classes that listen to some observable.
 * @author jonathan
 */
public interface Listener {
    /**
     * Notify the listener that the model has changed.
     * @param o The model that changed
     * @throws app.exceptions.DomainException If update fails
     */
    void update(Observable o) throws DomainException;
    
    /**
     * Notify the listener that the model has been deleted.
     * @param o The model that was deleted
     * @throws DomainException 
     */
    void delete(Observable o) throws DomainException;
}
