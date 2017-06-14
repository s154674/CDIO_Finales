package controllers.Weight;

public class CancelException extends Exception
{
    private static final long serialVersionUID = -5490114618089339322L;
    public CancelException(String message) { super(message); }
    public CancelException(Exception e) { super(e); }
}