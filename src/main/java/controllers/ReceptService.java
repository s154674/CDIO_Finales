package controllers;

import controllers.security.Secured;
import models.DALException;
import models.dao.DBReceptDAO;
import models.dao.ReceptDAO;
import models.dao.interfaces.IReceptDAO;
import models.dto.RaavareDTO;
import models.dto.ReceptDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
@Path("/recepter")
@Secured({"Farmaceut"})
public class ReceptService {
    static IReceptDAO receptDao = new DBReceptDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReceptDTO> index() {
        try {
            return receptDao.getReceptList();
        } catch (DALException e) {
            e.printStackTrace();
        }
        return new ArrayList<ReceptDTO>();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(ReceptDTO recept) {
        try {
            receptDao.createRecept(recept);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{receptId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ReceptDTO show(@PathParam("receptId") Integer receptId) {
        try {
            return receptDao.getRecept(receptId);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Path("/{receptId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("receptId") Integer receptId, ReceptDTO recept) {
        try {
            receptDao.updateRecept(receptId, recept);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{receptId}")
    @DELETE
    public void delete(@PathParam("receptId") Integer receptId) {
        try {
            receptDao.deleteRecept(receptDao.getRecept(receptId));
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    

}
