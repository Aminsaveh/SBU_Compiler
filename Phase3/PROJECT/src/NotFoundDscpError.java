
public class NotFoundDscpError extends RuntimeException {
    public NotFoundDscpError(String id ,int lineNumber) {
        super("NotFoundDscpError: Not found descriptor :  "  + id + " at line : " + lineNumber);
    }
}