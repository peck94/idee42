package app.domain;

/**
 * An interface for classes that listen to some observable.
 * @author jonathan
 */
public interface Listener {
    /**
     * Notify the listener that the model has changed.
     * @param o The model that changed
     */
    void update(Observable o);
}
