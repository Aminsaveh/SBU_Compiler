public class CastError extends RuntimeException {
    public CastError(Type type1,Type type2,int lineNumber) {
        super("CastError: Unsupported Cast type for : "  + type1 + "ToType" + type2  + " at line : " + lineNumber);
    }
}
