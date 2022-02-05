public class TypeError extends RuntimeException {
    public TypeError(String operation, Type type1,Type type2,int lineNumber) {
        super("TypeError: Unsupported  Type for " + operation + ": " +type1 + "!=" + type2 + " at line : " + lineNumber);
    }
}
