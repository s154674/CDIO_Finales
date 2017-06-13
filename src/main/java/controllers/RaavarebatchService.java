package controllers;

import controllers.security.Secured;
import models.DALException;
import models.dao.DBRaavareBatchDAO;
import models.dao.interfaces.IRaavareBatchDAO;
import models.dto.RaavareBatchDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilbonnekristiansen on 12/06/2017.
 */
@Path("/raavarebatcher")
@Secured({"Vaerkfoerer"})
public class RaavarebatchService {
    static IRaavareBatchDAO raavareBatchDao = new DBRaavareBatchDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RaavareBatchDTO> index() {
        try {
            return raavareBatchDao.getRaavareBatchList();
        } catch (DALException e) {
            e.printStackTrace();
        }
        return new ArrayList<RaavareBatchDTO>();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(RaavareBatchDTO raavareBatch) {
        try {
            raavareBatchDao.createRaavareBatch(raavareBatch);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{raavareId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RaavareBatchDTO show(@PathParam("raavareId") Integer rbId) {
        try {
            return raavareBatchDao.getRaavareBatch(rbId);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Path("/{raavareId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("raavareId") Integer rbId, RaavareBatchDTO raavareBatch) {
        try {
            raavareBatchDao.updateRaavareBatch(rbId, raavareBatch);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{raavareId}")
    @DELETE
    public void delete(@PathParam("raavareId") Integer rbId) {
        try {
            raavareBatchDao.deleteRaavareBatch(raavareBatchDao.getRaavareBatch(rbId));
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
