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
public abstract class PersistencyListener<T extends Observable> implements Listener {
    // store DAO
    private final DataAccessObject dao;
    
    /**
     * Create new listener
     * @param dao DAO associated with this listener
     */
    public PersistencyListener(DataAccessObject dao) {
        this.dao = dao;
    }

    @Override
    public void update(Observable o) throws DomainException {
        T model = (T) o;
        try{
            updateModel(model);
        }catch(PersistencyException e) {
            throw new DomainException(e);
        }
    }
    
    @Override
    public void delete(Observable o) throws DomainException {
        T model = (T) o;
        try{
            deleteModel(model);
        }catch(PersistencyException e) {
            throw new DomainException(e);
        }
    }
    
    protected DataAccessObject getDAO() {
        return dao;
    }
    
    protected abstract void updateModel(T model) throws PersistencyException;
    protected abstract void deleteModel(T model) throws PersistencyException;
    
}
