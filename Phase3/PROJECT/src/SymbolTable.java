import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    public String scopeName;
    public Map<String, Descriptor> symbolTable = new HashMap<>();
    public SymbolTable(String scopeName) {
        this.scopeName = scopeName;
    }


}
