package app.persistency;

import app.domain.Listener;
import app.domain.Observable;
import app.exceptions.DomainException;
import app.exceptions.PersistencyException;

/**
 * Listens for changes in domain models and propagates them to persistency.
 * @author jonathan
 * @param <T> Type of Observable
 */
public class PersistencyListener<T extends Observable> implements Listener {
    // store DAO
    private DataAccessObject dao;
    
    public PersistencyListener(DataAccessObject dao) {
        this.dao = dao;
    }

    @Override
    public void update(Observable o) throws DomainException {
        T model = (T) o;
        try {
            dao.update(model);
        } catch (PersistencyException ex) {
            throw new DomainException(ex);
        }
    }
    
}
