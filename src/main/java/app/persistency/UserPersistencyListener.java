package app.persistency;

import app.domain.users.User;
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
            getDAO().create(model);
        }
    }

    @Override
    protected void deleteModel(User model) throws PersistencyException {
        getDAO().delete(model.getId());
    }
    
}
