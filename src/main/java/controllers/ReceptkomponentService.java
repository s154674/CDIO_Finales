package controllers;

import controllers.security.Secured;
import models.DALException;
import models.dao.DBReceptKompDAO;
import models.dao.interfaces.IReceptKompDAO;
import models.dto.ReceptDTO;
import models.dto.ReceptKompDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilbonnekristiansen on 11/06/2017.
 */
@Path("/receptkomponenter")
@Secured({"Farmaceut"})
public class ReceptkomponentService {
    static IReceptKompDAO receptkompDao = new DBReceptKompDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReceptKompDTO> index() {
        try {
            return receptkompDao.getReceptKompList();
        } catch (DALException e) {
            e.printStackTrace();
        }
        return new ArrayList<ReceptKompDTO>();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(ReceptKompDTO receptkomp) {
        try {
            receptkompDao.createReceptKomp(receptkomp);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{receptId}+{raavareId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ReceptKompDTO show(@PathParam("receptId") Integer receptId, @PathParam("raavareId") Integer raavareId) {
        try {
            return receptkompDao.getReceptKomp(receptId, raavareId);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Path("/{receptId}+{raavareId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("receptId") Integer receptId, @PathParam("raavareId") Integer raavareId, ReceptKompDTO receptKomp) {
        try {
            receptkompDao.updateReceptKomp(receptId, raavareId, receptKomp);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{receptId}+{raavareId}")
    @DELETE
    public void delete(@PathParam("receptId") Integer receptId, @PathParam("raavareId") Integer raavareId) {
        try {
            receptkompDao.deleteReceptKomp(receptkompDao.getReceptKomp(receptId, raavareId));
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
