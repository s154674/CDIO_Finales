package models.dao;

        import models.DALException;
        import models.connector.ImprovedConnector;
        import models.connector.OldConnector;
        import models.dao.interfaces.IProduktBatchDAO;
        import models.dto.ProduktBatchDTO;

        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public class DBProduktBatchDAO implements IProduktBatchDAO {
    ImprovedConnector conn = new ImprovedConnector();
    @Override
    public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
        try {
            ResultSet rs = conn.doQuery("SELECT * FROM produktbatch WHERE pb_id = " + pbId);
            if (!rs.first()) throw new DALException("produktbatch'et " + pbId + " findes ikke");
            return new ProduktBatchDTO (rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recept_id"));
        }
        catch (SQLException e) {throw new DALException(e); }
    }

    @Override
    public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
        List<ProduktBatchDTO> list = new ArrayList<ProduktBatchDTO>();
        try {
            ResultSet rs = conn.doQuery("SELECT * FROM produktbatch");
            while (rs.next()) {
                list.add(new ProduktBatchDTO(rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recept_id")));
            }
            rs.close();
        } catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
        try {
            conn.doUpdate(
                    "INSERT INTO produktbatch(pb_id, status, recept_id) VALUES " +
                            "(" + produktbatch.getPbId() + ", '" + produktbatch.getStatus() + "', '" + produktbatch.getReceptId() + "')"
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void updateProduktBatch(int pbId, ProduktBatchDTO produktbatch) throws DALException {
        try {
            conn.doUpdate(
                    "UPDATE produktbatch SET status = '" + produktbatch.getStatus() + "', recept_id =  '" + produktbatch.getReceptId() + "' WHERE pb_id = " +
                            pbId
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void deleteProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
        try {
            conn.doUpdate(
                    "DELETE FROM produktbatch WHERE pb_id = '" + produktbatch.getPbId() + "'"
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

}
