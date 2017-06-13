package models.dao.interfaces;

import models.DALException;
import models.dto.BrugerDTO;

import java.util.List;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
public interface IBrugerDAO {
    BrugerDTO getBruger(int oprId) throws DALException;
    List<BrugerDTO> getBrugerList() throws DALException;
    void createBruger(BrugerDTO opr) throws DALException;
    void updateBruger(int oprId, BrugerDTO opr) throws DALException;
    void deleteBruger(BrugerDTO opr) throws DALException;
}
