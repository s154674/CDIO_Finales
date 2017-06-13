package models.dao.interfaces;

import models.DALException;
import models.dto.RaavareDTO;

import java.util.List;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
public interface IRaavareDAO {
    RaavareDTO getRaavare(int raavareId) throws DALException;
    List<RaavareDTO> getRaavareList() throws DALException;
    void createRaavare(RaavareDTO raavare) throws DALException;
    void updateRaavare(int raavareId, RaavareDTO raavare) throws DALException;
    void deleteRaavare(RaavareDTO raavare) throws DALException;
}
