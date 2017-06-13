package models.dao;

import models.DALException;
import models.dao.interfaces.IBrugerDAO;
import models.dto.BrugerDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
public class BrugerDAO implements IBrugerDAO {
    static private ArrayList<BrugerDTO> brugerList = new ArrayList<BrugerDTO>();

    @Override
    public BrugerDTO getBruger(int oprId) throws DALException {
        for (BrugerDTO bruger : brugerList) {
            if (bruger.getOprId()==oprId) {
                return bruger;
            }
        }
        return null;
    }

    @Override
    public List<BrugerDTO> getBrugerList() throws DALException {
        return brugerList;
    }

    @Override
    public void createBruger(BrugerDTO opr) throws DALException {
        brugerList.add(opr);
    }

    @Override
    public void updateBruger(int oprId, BrugerDTO opr) throws DALException {
        brugerList.remove(getBruger(oprId));
        brugerList.add(opr);
    }

    @Override
    public void deleteBruger(BrugerDTO opr) throws DALException {
        brugerList.remove(opr);
    }
}
