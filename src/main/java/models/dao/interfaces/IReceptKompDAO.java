package models.dao.interfaces;

import models.DALException;
import models.dto.ReceptKompDTO;

import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public interface IReceptKompDAO {
    ReceptKompDTO getReceptKomp(int receptId, int raavareId) throws DALException;
    List<ReceptKompDTO> getReceptKompList(int receptId) throws DALException;
    List<ReceptKompDTO> getReceptKompList() throws DALException;
    void createReceptKomp(ReceptKompDTO receptkomponent) throws DALException;
    void updateReceptKomp(int receptId, int raavareId, ReceptKompDTO receptkomponent) throws DALException;
    void deleteReceptKomp(ReceptKompDTO receptkomponent) throws DALException;
}

