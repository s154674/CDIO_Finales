package models.dao;

import models.DALException;
import models.connector.ImprovedConnector;
import models.dao.interfaces.IReceptKompDAO;
import models.dto.ReceptKompDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public class DBReceptKompDAO implements IReceptKompDAO {
    ImprovedConnector conn = ImprovedConnector.getConnI();
    @Override
    public ReceptKompDTO getReceptKomp(int receptId, int raavareId) throws DALException {
        try {
        ResultSet rs = conn.doQuery("SELECT * FROM receptkomponent WHERE recept_id = " + receptId + " AND raavare_id = " + raavareId + ";");
            if (!rs.first()) throw new DALException("receptkomponent med Recept ID " + receptId + ", der indeholder Raavare ID" + raavareId + " findes ikke");
            return new ReceptKompDTO (rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getInt("tolerance"));
        }
        catch (SQLException e) {throw new DALException(e); }
    }

    @Override
    public List<ReceptKompDTO> getReceptKompList(int receptId) throws DALException {
        List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
        try{
        ResultSet rs = conn.doQuery("SELECT * FROM receptkomponent WHERE recept_id = " + receptId);
            while (rs.next()) {
                list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getInt("tolerance")));
            }
        }
        catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public List<ReceptKompDTO> getReceptKompList() throws DALException {
        List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
        try {
        ResultSet rs = conn.doQuery("SELECT * FROM receptkomponent");
            while (rs.next()) {
                list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
            }
            rs.close();
        }
        catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public void createReceptKomp(ReceptKompDTO receptkomponent) throws DALException {
        try {
            conn.doUpdate(
                    "INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES " +
                            "(" + receptkomponent.getReceptId() + ", '" + receptkomponent.getRaavareId() + "','" + receptkomponent.getNomNetto() + "', '" + receptkomponent.getTolerance() + "')"
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void updateReceptKomp(int receptKompId, int raavareId, ReceptKompDTO receptkomponent) throws DALException {
        try {
            conn.doUpdate(
                    "UPDATE receptkomponent SET raavare_id = '" + receptkomponent.getRaavareId() + "', nom_netto =  '" + receptkomponent.getNomNetto() + "', tolerance =  '" + receptkomponent.getTolerance() + "' WHERE recept_id = " +
                            receptkomponent.getReceptId()
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void deleteReceptKomp(ReceptKompDTO receptkomponent) throws DALException {
        try {
            conn.doUpdate(
                    "DELETE FROM receptkomponent WHERE recept_id = '" + receptkomponent.getReceptId() +"' AND raavare_id = " + receptkomponent.getRaavareId() );
        } catch (SQLException e) {
            throw new DALException(e);
        }

    }


}
