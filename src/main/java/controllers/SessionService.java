// små dele af denne fil er lånt fra https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey

package controllers;

import java.util.List;

import controllers.security.AuthenticationFilter;
import controllers.security.Credentials;
import controllers.security.WrongCredentialsException;
import io.jsonwebtoken.*;

import models.DALException;
import models.dao.BrugerDAO;
import models.dao.DBBrugerDAO;
import models.dao.interfaces.IBrugerDAO;
import models.dto.BrugerDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by emilbonnekristiansen on 07/06/2017.
 */
@Path("/login")
public class SessionService {
    static IBrugerDAO brugerDao = new DBBrugerDAO();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response check(@Context HttpServletRequest request) {
        String i = Jwts.parser().setSigningKey(AuthenticationFilter.tokenSignerKey).parseClaimsJws(request.getHeader("Authorization")).getBody().getIssuer();
        return Response.ok(i).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Credentials credentials) {
        try {
            int oprId = credentials.getOprId();
            String password = credentials.getPassword();

            // Authenticate the user using the credentials provided
            authenticate(oprId, password);

            // Issue a token for the user
            String token = issueToken(oprId);

            // Return the token on the response
            return Response.ok(token).header("Authorization", "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }



    private BrugerDTO authenticate(int oprId, String password) throws WrongCredentialsException {
        try {
            for (BrugerDTO opr : brugerDao.getBrugerList()) {

                if (opr.getOprId()==oprId && opr.getPassword().equals(password)) {
                    return opr;
                }
            }
        } catch (DALException e) {
            e.printStackTrace();
        }
        throw new WrongCredentialsException("Kunne ikke autenticere bruger");
    }

    private String issueToken(int oprId) {
        List<String> roller = null;

        try {
            roller = brugerDao.getBruger(oprId).getRoller();
        } catch (DALException e) {
            e.printStackTrace();
        }

        String compactJws = Jwts.builder()
                .setIssuer("gruppe 18")
                .claim("oprId", oprId)
                .claim("roller", roller)
                .signWith(SignatureAlgorithm.HS512, AuthenticationFilter.tokenSignerKey)
                .compact();
        return compactJws;
    }

}
