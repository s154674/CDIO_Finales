package controllers;

import controllers.security.Secured;
import models.DALException;
import models.dao.DBRaavareDAO;
import models.dao.interfaces.IRaavareDAO;
import models.dto.RaavareDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@Path("/raavarer")
@Secured({"Farmaceut","Administrator"})
public class RaavareService {
    static IRaavareDAO raavareDao = new DBRaavareDAO();

    @GET
    @Secured({"Farmaceut","Administrator", "Vaerkfoerer"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<RaavareDTO> index() {
        try {
            return raavareDao.getRaavareList();
        } catch (DALException e) {
            e.printStackTrace();
        }
        return new ArrayList<RaavareDTO>();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(RaavareDTO raavare) {
        try {
            raavareDao.createRaavare(raavare);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{raavareId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RaavareDTO show(@PathParam("raavareId") Integer raavareId) {
        try {
            return raavareDao.getRaavare(raavareId);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Path("/{raavareId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("raavareId") Integer raavareId, RaavareDTO raavare) {
        try {
            raavareDao.updateRaavare(raavareId, raavare);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Path("/{raavareId}")
    @DELETE
    public void delete(@PathParam("raavareId") Integer raavareId) {
        try {
            raavareDao.deleteRaavare(raavareDao.getRaavare(raavareId));
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}