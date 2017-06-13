package models.dao;

import models.DALException;
import models.connector.ImprovedConnector;
import models.dao.interfaces.IRaavareDAO;
import models.dto.RaavareDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public class DBRaavareDAO implements IRaavareDAO {
    ImprovedConnector conn = new ImprovedConnector();
    @Override
    public RaavareDTO getRaavare(int raavareId) throws DALException {
        try {
        ResultSet rs = conn.doQuery("SELECT * FROM raavare WHERE raavare_id = " + raavareId + ";");
            if (!rs.first()) throw new DALException("raavare med Raavare ID " + raavareId + " findes ikke");
            return new RaavareDTO (rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer"));
        }
        catch (SQLException e) {throw new DALException(e); }
    }

    @Override
    public List<RaavareDTO> getRaavareList() throws DALException {
        List<RaavareDTO> list = new ArrayList<RaavareDTO>();
        try {
        ResultSet rs = conn.doQuery("SELECT * FROM raavare");
            while (rs.next()) {
                list.add(new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer")));
            }
            rs.close();
        }
        catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public void createRaavare(RaavareDTO raavare) throws DALException {
        try {
            conn.doUpdate(
                    "INSERT INTO raavare(raavare_id, raavare_navn, leverandoer) VALUES " +
                            "(" + raavare.getRaavareId() + ", '" + raavare.getRaavareNavn() + "', '" + raavare.getLeverandoer() + "')"
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void updateRaavare(int raavareId, RaavareDTO raavare) throws DALException {
        try {
            conn.doUpdate(
                    "UPDATE raavare SET raavare_navn = '" + raavare.getRaavareNavn() + "', leverandoer =  '" + raavare.getLeverandoer() + "' WHERE raavare_id = " +
                            raavare.getRaavareId()
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void deleteRaavare(RaavareDTO raavare) throws DALException {
        try {
            conn.doUpdate(
                    "DELETE FROM raavare WHERE raavare_id = " + raavare.getRaavareId());
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }
}
