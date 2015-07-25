package app.domain;

import app.exceptions.PersistencyException;

/**
 * An interface for classes that listen to some observable.
 * @author jonathan
 */
public interface Listener {
    /**
     * Notify the listener that the model has changed.
     * @param o The model that changed
     * @throws app.exceptions.PersistencyException If the update fails
     */
    void update(Observable o) throws PersistencyException;
}
