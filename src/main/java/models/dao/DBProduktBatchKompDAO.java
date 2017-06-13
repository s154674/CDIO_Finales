package models.dao;

import models.DALException;
import models.connector.ImprovedConnector;
import models.connector.OldConnector;
import models.dao.interfaces.IProduktBatchKompDAO;
import models.dto.ProduktBatchKompDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public class DBProduktBatchKompDAO implements IProduktBatchKompDAO {
    ImprovedConnector conn = new ImprovedConnector();
    @Override
    public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException {
        try {
            ResultSet rs = conn.doQuery("SELECT * FROM produktbatchkomponent WHERE pb_id = " + pbId + " AND rb_id = " + rbId + ";");
            if (!rs.first()) throw new DALException("produktbatchkomponenten med Produkt ID " + pbId + " og RåvareBatch ID" + rbId + " findes ikke");
            return new ProduktBatchKompDTO (rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("bruger_id"));
        }
        catch (SQLException e) {throw new DALException(e); }

    }

    @Override
    public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException {
        List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
        try {
        ResultSet rs = conn.doQuery("SELECT * FROM produktbatchkomponent WHERE pb_id =" + pbId);
            while (rs.next()) {
                list.add(new ProduktBatchKompDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("bruger_id")));
            }
            rs.close();
        }
        catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException {
        List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
        try {
            ResultSet rs = conn.doQuery("SELECT * FROM produktbatchkomponent");
            while (rs.next()) {
                list.add(new ProduktBatchKompDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("bruger_id")));
            }
            rs.close();
        }
        catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
        try {
            conn.doUpdate(
                    "INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto, bruger_id) VALUES " +
                            "(" + produktbatchkomponent.getPbId() + ", '" + produktbatchkomponent.getRbId() + "', '" + produktbatchkomponent.getTara() + "', '" + produktbatchkomponent.getNetto() + "', '" + produktbatchkomponent.getOprId() + "')"
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void updateProduktBatchKomp(int pbId, int rbId, ProduktBatchKompDTO produktbatchkomponent) throws DALException {
        try {
            conn.doUpdate(
                    "UPDATE produktbatchkomponent SET tara = '" + produktbatchkomponent.getTara() + "', netto =  '" + produktbatchkomponent.getNetto() + "', bruger_id =  '" +produktbatchkomponent.getOprId() + "' WHERE pb_id = " +
                            pbId + " AND rb_id = " + rbId
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void deleteProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
        try {
            conn.doUpdate(
                    "DELETE FROM produktbatchkomponent WHERE pb_id = '" + produktbatchkomponent.getPbId() + "' AND rb_id = " + produktbatchkomponent.getRbId()
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }
}
