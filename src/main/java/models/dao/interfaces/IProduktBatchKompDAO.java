package models.dao.interfaces;

import models.DALException;
import models.dto.ProduktBatchKompDTO;

import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public interface IProduktBatchKompDAO {
    ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException;
    List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException;
    List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException;
    void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException;
    void updateProduktBatchKomp(int pbId, int rbId, ProduktBatchKompDTO produktbatchkomponent) throws DALException;
    void deleteProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException;
}
