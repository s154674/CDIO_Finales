package controllers.security;

/**
 * Created by emilbonnekristiansen on 07/06/2017.
 */
public class WrongCredentialsException extends Exception {
    private static final long serialVersionUID = -5490114628089339322L;
    public WrongCredentialsException(String message) { super(message); }
    public WrongCredentialsException(Exception e) { super(e); }
}

