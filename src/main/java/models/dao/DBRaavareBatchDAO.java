package models.dao;

import models.DALException;
import models.connector.ImprovedConnector;
import models.dao.interfaces.IRaavareBatchDAO;
import models.dto.RaavareBatchDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public class DBRaavareBatchDAO implements IRaavareBatchDAO {
    ImprovedConnector conn = new ImprovedConnector();
    @Override
    public RaavareBatchDTO getRaavareBatch(int rbId) throws DALException {
        try {
        ResultSet rs = conn.doQuery("SELECT * FROM raavarebatch WHERE rb_id = " + rbId);
            if (!rs.first()) throw new DALException("raavarebatch med RaavareBatch ID " + rbId + " findes ikke");
            return new RaavareBatchDTO (rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde"));
        }
        catch (SQLException e) {throw new DALException(e); }
    }

    @Override
    public List<RaavareBatchDTO> getRaavareBatchList() throws DALException {
        List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
        try {
        ResultSet rs = conn.doQuery("SELECT * FROM raavarebatch");
            while (rs.next()) {
                list.add(new RaavareBatchDTO(rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
            }
            rs.close();
        }
        catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public List<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException {
        List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
        try {
        ResultSet rs = conn.doQuery("SELECT * FROM raavarebatch WHERE raavare_id =" + raavareId);
            while (rs.next()) {
                list.add(new RaavareBatchDTO(rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
            }
            rs.close();
        }
        catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
        try {
            conn.doUpdate(
                    "INSERT INTO raavarebatch(rb_id, raavare_id, maengde) VALUES " +
                            "(" + raavarebatch.getRbId() + ", '" + raavarebatch.getRaavareId() + "', '" + raavarebatch.getMaengde() + "')"
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void updateRaavareBatch(int rbid, RaavareBatchDTO raavarebatch) throws DALException {
        try {
            conn.doUpdate(
                    "UPDATE raavarebatch SET maengde = '" + raavarebatch.getMaengde() + "', raavare_id =  '" + raavarebatch.getRaavareId() + "' WHERE rb_id = " +
                            raavarebatch.getRbId()
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void deleteRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
        try {
            conn.doUpdate(
                    "DELETE FROM raavarebatch WHERE rb_id = " + raavarebatch.getRbId());
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

}
