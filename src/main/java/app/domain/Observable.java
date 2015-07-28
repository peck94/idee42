package app.domain;

import app.exceptions.DomainException;
import java.util.LinkedList;
import java.util.List;

/**
 * An abstract class for domain models that can be observed from some other
 * layer, such as persistency.
 * @author jonathan
 */
public abstract class Observable {
    // store list of listeners
    private final List<Listener> listeners;
    
    public Observable() {
        listeners = new LinkedList<>();
    }
    
    /**
     * Add a listener to the list
     * @param l Listener to add
     */
    public void addListener(Listener l) {
        listeners.add(l);
    }
    
    /**
     * Remove a listener from the list
     * @param l Listener to remove
     */
    public void removeListener(Listener l) {
        listeners.remove(l);
    }
    
    /**
     * Update all listeners.
     * @throws app.exceptions.DomainException
     */
    public void invalidate() throws DomainException {
        for(Listener l: listeners) {
            l.update(this);
        }
    }
    
    /**
     * Tell all listeners we're going down.
     * @throws DomainException 
     */
    public void delete() throws DomainException {
        for(Listener l: listeners) {
            l.delete(this);
        }
    }
}
