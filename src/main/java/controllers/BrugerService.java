package controllers;

import controllers.security.Secured;
import models.DALException;
import models.dao.DBBrugerDAO;
import models.dao.interfaces.IBrugerDAO;
import models.dto.BrugerDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
@Path("/brugere")
public class BrugerService {
    static IBrugerDAO brugerDao = new DBBrugerDAO();


    @GET
    @Secured({"Administrator"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<BrugerDTO> index() {
        try {
            return brugerDao.getBrugerList();
        } catch (DALException e) {
            e.printStackTrace();
        }
        return new ArrayList<BrugerDTO>();
    }

    @POST
    @Secured({"Administrator"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(BrugerDTO opr) {
        try {
            brugerDao.createBruger(opr);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/{oprId}")
    @Produces(MediaType.APPLICATION_JSON)
    public BrugerDTO show(@PathParam("oprId") Integer oprId) {
        try {
            return brugerDao.getBruger(oprId);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PUT

    @Path("/{oprId}")
    @Secured({"Administrator"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("oprId") Integer oprId, BrugerDTO opr) {
        try {
            boolean wasAdmin = brugerDao.getBruger(oprId).getRoller().contains("Administrator");
            boolean isAdminAfter = opr.getRoller().contains("Administrator");
            boolean isLastAdmin = true;
            int sum = 0;
            for (BrugerDTO brugerDTO : brugerDao.getBrugerList()) {
                if (brugerDTO.getRoller().contains("Administrator")){
                    sum++;
                }
            }
            if (sum>1)
                isLastAdmin = false;
            if (!(wasAdmin && isLastAdmin && !isAdminAfter)){
                brugerDao.updateBruger(oprId, opr);
            }
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @DELETE
    @Path("/{oprId}")
    @Secured({"Administrator"})
    public void delete(@PathParam("oprId") Integer oprId) {
        try {
            BrugerDTO bruger = brugerDao.getBruger(oprId);
            boolean isAdmin = false;
            for (String rolle : bruger.getRoller()) {
                if (rolle.equals("Administrator")){
                    isAdmin = true;
                }
            }

            if (!isAdmin) {
                brugerDao.deleteBruger(brugerDao.getBruger(oprId));
            }
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
