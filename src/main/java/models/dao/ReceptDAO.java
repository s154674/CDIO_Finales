package models.dao;

import models.DALException;
import models.dao.interfaces.IReceptDAO;
import models.dto.ReceptDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
public class ReceptDAO implements IReceptDAO {
    private ArrayList<ReceptDTO> receptList = new ArrayList<ReceptDTO>();

    @Override
    public ReceptDTO getRecept(int receptId) throws DALException {
        for (ReceptDTO recept : receptList) {
            if (recept.getReceptId()==receptId) {
                return recept;
            }
        }
        return null;
    }

    @Override
    public List<ReceptDTO> getReceptList() throws DALException {
        return receptList;
    }

    @Override
    public void createRecept(ReceptDTO recept) throws DALException {
        receptList.add(recept);
    }

    @Override
    public void updateRecept(int receptId, ReceptDTO recept) throws DALException {
        receptList.remove(getRecept(receptId));
        receptList.add(recept);
    }

    @Override
    public void deleteRecept(ReceptDTO recept) throws DALException {
        receptList.remove(recept);
    }
}
