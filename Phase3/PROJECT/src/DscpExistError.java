
public class DscpExistError extends RuntimeException {
    public DscpExistError(String id ,int lineNumber) {
            super("DscpExistError: Descriptor with this id has already defined : "  + id + " at line : " + lineNumber);
        }
    }
