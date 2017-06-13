package models.dao.interfaces;

import models.DALException;
import models.dto.ProduktBatchDTO;

import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public interface IProduktBatchDAO {
    ProduktBatchDTO getProduktBatch(int pbId) throws DALException;
    List<ProduktBatchDTO> getProduktBatchList() throws DALException;
    void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException;
    void updateProduktBatch(int pbId, ProduktBatchDTO produktbatch) throws DALException;
    void deleteProduktBatch(ProduktBatchDTO produktbatch) throws DALException;
}

