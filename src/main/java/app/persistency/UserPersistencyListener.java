package app.persistency;

import app.domain.users.User;
import app.exceptions.DomainException;
import app.exceptions.PersistencyException;

/**
 * Persistency listener for users.
 * @author jonathan
 */
public class UserPersistencyListener extends PersistencyListener<User> {

    public UserPersistencyListener(DataAccessObject dao) {
        super(dao);
    }

    @Override
    protected void updateModel(User model) throws PersistencyException {
        if(getDAO().exists(model.getId())) {
            getDAO().update(model);
        }else{
            long id = (long) getDAO().create(model);
            try {
                model.setId(id);
            } catch (DomainException ex) {
                throw new PersistencyException(ex);
            }
        }
    }

    @Override
    protected void deleteModel(User model) throws PersistencyException {
        getDAO().delete(model.getId());
    }
    
}
