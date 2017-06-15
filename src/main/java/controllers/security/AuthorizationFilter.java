// DENNE FIL ER (for det meste) LÅNT FRA https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey


package controllers.security;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Jwts;
import models.DALException;
import models.dao.BrugerDAO;
import models.dao.DBBrugerDAO;
import models.dao.interfaces.IBrugerDAO;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by emilbonnekristiansen on 08/06/2017.
 */
@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    private IBrugerDAO brugerDao = new DBBrugerDAO();

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the resource class which matches with the requested URL
        // Extract the roles declared by it
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<String> classRoles = extractRoles(resourceClass);

        // Get the resource method which matches with the requested URL
        // Extract the roles declared by it
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<String> methodRoles = extractRoles(resourceMethod);


        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        int oprId = Jwts.parser().setSigningKey(AuthenticationFilter.tokenSignerKey).parseClaimsJws(token).getBody().get("oprId",Integer.class);
        List<String> brugerRoller = null;
        try {
            brugerRoller = brugerDao.getBruger(oprId).getRoller();
        } catch (DALException e) {
            e.printStackTrace();
        }

        try {

            // Check if the user is allowed to execute the method
            // The method annotations override the class annotations
            if (methodRoles.isEmpty()) {
                checkPermissions(brugerRoller, classRoles);
            } else {
                checkPermissions(brugerRoller, methodRoles);
            }

        } catch (NotAuthorizedException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN).build());
        }
    }

    // Extract the roles from the annotated element
    private List<String> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<String>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<String>();
            } else {
                String[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(List<String> brugerRoller, List<String> allowedRoles) throws NotAuthorizedException {
        boolean allowed = false;
        for (String brugerRolle : brugerRoller) {
            for (String allowedRolle : allowedRoles) {
                if (brugerRolle.equals(allowedRolle)) {
                    allowed = true;
                }
            }
        }

        if (!allowed){
            throw new NotAuthorizedException("Brugeren har ikke adgang");
        }
    }
}
