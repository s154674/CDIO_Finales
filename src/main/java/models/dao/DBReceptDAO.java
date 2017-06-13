package models.dao;

import models.DALException;
import models.connector.ImprovedConnector;
import models.dao.interfaces.IReceptDAO;
import models.dto.ReceptDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public class DBReceptDAO implements IReceptDAO {
    ImprovedConnector conn = new ImprovedConnector();
    @Override
    public ReceptDTO getRecept(int receptId) throws DALException {
        try {
        ResultSet rs = conn.doQuery("SELECT * FROM recept WHERE recept_id = " + receptId);
            if (!rs.first()) throw new DALException("recept med Recept ID " + receptId + " findes ikke");
            return new ReceptDTO (rs.getInt("recept_id"), rs.getString("recept_navn"));
        }
        catch (SQLException e) {throw new DALException(e); }
    }

    @Override
    public List<ReceptDTO> getReceptList() throws DALException {
        List<ReceptDTO> list = new ArrayList<ReceptDTO>();
        try {
        ResultSet rs = conn.doQuery("SELECT * FROM recept");
            while (rs.next()) {
                list.add(new ReceptDTO(rs.getInt("recept_id"), rs.getString("recept_navn")));
            }
            rs.close();
        }
        catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public void createRecept(ReceptDTO recept) throws DALException {
        try {
            conn.doUpdate(
                    "INSERT INTO recept(recept_id, recept_navn) VALUES " +
                            "(" + recept.getReceptId() + ", '" + recept.getReceptNavn() + "')"
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void updateRecept(int receptId, ReceptDTO recept) throws DALException {
        try {
            conn.doUpdate(
                    "UPDATE recept SET recept_navn = '" + recept.getReceptNavn() + "' WHERE recept_id = " +
                            recept.getReceptId()
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void deleteRecept(ReceptDTO recept) throws DALException {
        try {
            conn.doUpdate(
                    "DELETE FROM recept WHERE recept_id = " + recept.getReceptId());
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }
}
