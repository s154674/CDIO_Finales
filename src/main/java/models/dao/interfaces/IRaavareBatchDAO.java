package models.dao.interfaces;

import models.DALException;
import models.dto.RaavareBatchDTO;

import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public interface IRaavareBatchDAO {
    RaavareBatchDTO getRaavareBatch(int rbId) throws DALException;
    List<RaavareBatchDTO> getRaavareBatchList() throws DALException;
    List<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException;
    void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException;
    void updateRaavareBatch(int rbid, RaavareBatchDTO raavarebatch) throws DALException;
    void deleteRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException;
}

