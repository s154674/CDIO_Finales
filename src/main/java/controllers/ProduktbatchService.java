package controllers;

import controllers.security.Secured;
import models.DALException;
import models.dao.DBProduktBatchDAO;
import models.dao.interfaces.IProduktBatchDAO;
import models.dto.ProduktBatchDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilbonnekristiansen on 12/06/2017.
 */
@Path("/produktbatcher")
@Secured({"Vaerkfoerer"})
public class ProduktbatchService {
    static IProduktBatchDAO produktBatchDao = new DBProduktBatchDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProduktBatchDTO> index() {
        try {
            return produktBatchDao.getProduktBatchList();
        } catch (DALException e) {
            e.printStackTrace();
        }
        return new ArrayList<ProduktBatchDTO>();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(ProduktBatchDTO produktBatch) {
        try {
            produktBatchDao.createProduktBatch(produktBatch);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{pbId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ProduktBatchDTO show(@PathParam("pbId") Integer pbId) {
        try {
            return produktBatchDao.getProduktBatch(pbId);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Path("/{pbId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("pbId") Integer pbId, ProduktBatchDTO produktBatch) {
        try {
            produktBatchDao.updateProduktBatch(pbId, produktBatch);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{pbId}")
    @DELETE
    public void delete(@PathParam("pbId") Integer pbId) {
        try {
            produktBatchDao.deleteProduktBatch(produktBatchDao.getProduktBatch(pbId));
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
