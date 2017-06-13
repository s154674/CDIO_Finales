package controllers.security;

/**
 * Created by emilbonnekristiansen on 08/06/2017.
 */
public class NotAuthorizedException extends Exception {
    private static final long serialVersionUID = -5490114628089339322L;
    public NotAuthorizedException(String message) { super(message); }
    public NotAuthorizedException(Exception e) { super(e); }
}

