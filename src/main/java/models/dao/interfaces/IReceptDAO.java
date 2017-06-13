package models.dao.interfaces;

import models.DALException;
import models.dto.ReceptDTO;

import java.util.List;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
public interface IReceptDAO {
    ReceptDTO getRecept(int receptId) throws DALException;
    List<ReceptDTO> getReceptList() throws DALException;
    void createRecept(ReceptDTO recept) throws DALException;
    void updateRecept(int receptId, ReceptDTO recept) throws DALException;
    void deleteRecept(ReceptDTO recept) throws DALException;
}
