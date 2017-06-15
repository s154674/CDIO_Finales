// DENNE FIL ER (for det meste) LÅNT FRA https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey


package controllers.security;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    //final public static Key tokenSignerKey = MacProvider.generateKey();
    static String secret = "Tingskerokaykandufattedet";
    static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
    final public static Key tokenSignerKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Validate the token
            validateToken(token);


        } catch (InvalidTokenException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token) throws InvalidTokenException {
        try {
            Jwts.parser().setSigningKey(tokenSignerKey).parseClaimsJws(token);
            // vi kan GODT stole på denne her token

        } catch (Exception e) {
            // Vi kan IKKE stole på denne her token
            throw new InvalidTokenException("Token er ikke godkent");
        }
    }
}