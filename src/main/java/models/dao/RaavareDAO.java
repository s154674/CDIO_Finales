package models.dao;

import models.DALException;
import models.dao.interfaces.IRaavareDAO;
import models.dto.RaavareDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
public class RaavareDAO implements IRaavareDAO {
    private ArrayList<RaavareDTO> raavareList = new ArrayList<RaavareDTO>();

    @Override
    public RaavareDTO getRaavare(int raavareId) throws DALException {
        for (RaavareDTO raavare : raavareList) {
            if (raavare.getRaavareId()==raavareId) {
                return raavare;
            }
        }
        return null;
    }

    @Override
    public List<RaavareDTO> getRaavareList() throws DALException {
        return raavareList;
    }

    @Override
    public void createRaavare(RaavareDTO raavare) throws DALException {
        raavareList.add(raavare);
    }

    @Override
    public void updateRaavare(int raavareId, RaavareDTO raavare) throws DALException {
        raavareList.remove(getRaavare(raavareId));
        raavareList.add(raavare);
    }

    @Override
    public void deleteRaavare(RaavareDTO raavare) throws DALException {
        raavareList.remove(raavare);
    }
}
