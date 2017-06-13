package models.dao;

import models.DALException;
import models.connector.ImprovedConnector;
import models.dao.interfaces.IBrugerDAO;
import models.dto.BrugerDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Johan on 07-06-2017.
 */
public class DBBrugerDAO implements IBrugerDAO {
    ImprovedConnector conn = new ImprovedConnector();

    @Override
    public BrugerDTO getBruger(int brugerId) throws DALException {
        try {
            ResultSet rs = conn.doQuery("SELECT * FROM bruger WHERE bruger_id = " + brugerId);
            if (!rs.next()) throw new DALException("Operatoeren " + brugerId + " findes ikke");
            BrugerDTO bruger = new BrugerDTO();
            bruger.setOprId(rs.getInt("bruger_id"));
            bruger.setOprNavn(rs.getString("bruger_navn"));
            bruger.setIni(rs.getString("ini"));
            bruger.setCpr(rs.getString("cpr"));
            bruger.setPassword(rs.getString("password"));
            bruger.setRoller(Arrays.asList(rs.getString("roller").split(", ")));
            rs.close();
            return bruger;
        }
        catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public List<BrugerDTO> getBrugerList() throws DALException {
        List<BrugerDTO> list = new ArrayList<BrugerDTO>();
        try {
            ResultSet rs = conn.doQuery("SELECT * FROM bruger");
            while (rs.next()) {
                list.add(new BrugerDTO(rs.getInt("bruger_id"), rs.getString("bruger_navn"), rs.getString("ini"), rs.getString("cpr"), rs.getString("password"),(Arrays.asList(rs.getString("roller").split(", ")))));
            }
            rs.close();
        }
        catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public void createBruger(BrugerDTO bruger) throws DALException {
        String roller = String.join(", ", bruger.getRoller());
        try {
            conn.doUpdate(
                    "INSERT INTO bruger(bruger_id, bruger_navn, ini, cpr, password, roller) VALUES " +
                            "('" + bruger.getOprId() + "', '"+ bruger.getOprNavn() + "', '" + bruger.getIni() + "', '" +
                            bruger.getCpr() + "', '" + bruger.getPassword() + "', '" + roller + "')"
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void updateBruger(int oprId, BrugerDTO bruger) throws DALException {
        String roller = String.join(", ", bruger.getRoller());
        try {
            conn.doUpdate(
                    "UPDATE bruger SET  bruger_navn = '" + bruger.getOprNavn() + "', ini =  '" + bruger.getIni() +
                            "', cpr = '" + bruger.getCpr() + "', password = '" + bruger.getPassword() + "', roller = '" + roller + "' WHERE bruger_id = " +
                            bruger.getOprId()
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void deleteBruger(BrugerDTO bruger) throws DALException {
        try {
            conn.doUpdate(
                    "DELETE FROM bruger WHERE  bruger_id = '" + bruger.getOprId() + "'"
            );
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }
}
