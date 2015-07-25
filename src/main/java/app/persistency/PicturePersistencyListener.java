package app.persistency;

import app.domain.pictures.Picture;
import app.exceptions.PersistencyException;

/**
 * Persistency listener for pictures.
 * @author jonathan
 */
public class PicturePersistencyListener extends PersistencyListener<Picture> {

    public PicturePersistencyListener(DataAccessObject dao) {
        super(dao);
    }

    @Override
    protected void updateModel(Picture model) throws PersistencyException {
        if(getDAO().exists(model.getId())) {
            getDAO().update(model);
        }else{
            getDAO().create(model);
        }
    }
    
}
