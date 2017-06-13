package controllers;

import controllers.security.Secured;
import models.DALException;
import models.dao.DBProduktBatchKompDAO;
import models.dao.interfaces.IProduktBatchKompDAO;
import models.dto.ProduktBatchKompDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilbonnekristiansen on 12/06/2017.
 */
@Path("/produktbatchkomponenter")
@Secured({"Vaerkfoerer"})
public class ProduktbatchkomponentService {
    static IProduktBatchKompDAO produktBatchKompDao = new DBProduktBatchKompDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProduktBatchKompDTO> index() {
        try {
            return produktBatchKompDao.getProduktBatchKompList();
        } catch (DALException e) {
            e.printStackTrace();
        }
        return new ArrayList<ProduktBatchKompDTO>();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(ProduktBatchKompDTO produktBatchKomp) {
        try {
            produktBatchKompDao.createProduktBatchKomp(produktBatchKomp);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }


    @Path("/{pbId}+{rbId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ProduktBatchKompDTO show(@PathParam("pbId") Integer pbId, @PathParam("rbId") Integer rbId) {
        try {
            return produktBatchKompDao.getProduktBatchKomp(pbId, rbId);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Path("/{pbId}+{rbId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("pbId") Integer pbId, @PathParam("rbId") Integer rbId, ProduktBatchKompDTO produktBatch) {
        try {
            produktBatchKompDao.updateProduktBatchKomp(pbId, rbId, produktBatch);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{pbId}+{rbId}")
    @DELETE
    public void delete(@PathParam("pbId") Integer pbId, @PathParam("rbId") Integer rbId) {
        try {
            produktBatchKompDao.deleteProduktBatchKomp(produktBatchKompDao.getProduktBatchKomp(pbId, rbId));
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
