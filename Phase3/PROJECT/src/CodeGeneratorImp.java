import java.util.Stack;

public class CodeGeneratorImp implements CodeGenerator {
    private static Stack<Object> semanticStack = new Stack<>();
    private static Stack<SymbolTable> symbolTableLocalStack = new Stack<>();
    private static Stack<SymbolTable> symbolTableGlobalStack = new Stack<>();
    private static int variableCount = 0;
    private CoolScanner scanner;
    CodeGeneratorImp(CoolScanner scanner){
        this.scanner = scanner;
    }



    @Override
    public void doSemantic(String sem) {
        try {
            switch (sem) {
                case "ReadInt":
                    ReadInt();
                    break;
                case "ReadStr" :
                    ReadStr();
                    break;
                case "Print" :
                    Print((Descriptor) semanticStack.pop());
                    break;
                case "pushClassId":
                    PushClassId(scanner.currentSymbol.getToken());
                    break;
                case "pushId":
                    PushId(scanner.currentSymbol.getToken());
                    break;
                case "pushType" :
                    PushType(scanner.currentSymbol.getToken());
                    break;
                case "pushGlobalTopType":
                    PushGlobalTopType();
                    break;
                case "AddGlobalId":
                    AddGlobalId();
                    break;
                case "pushLocalTopType":
                    PushLocalTopType();
                    break;
                case "AddLocalId":
                    AddLocalId();
                    break;
                case "Assignment":
                    Assignment();
                    break;




            }


        } catch (Exception e) {


        }
    }

    public void ReadInt() {
        AssemblyWriter.appendComment("read integer");
        AssemblyWriter.appendCommandToCode("li", "$v0", "5");
        AssemblyWriter.appendCommandToCode("syscall");
        AssemblyWriter.appendCommandToCode("move", "$t0", "$v0");
        String variableName =  GenerateVariable();
        AssemblyWriter.appendCommandToData(variableName, "word", "0");
        AssemblyWriter.appendCommandToCode("la", "$t1", variableName);
        AssemblyWriter.appendCommandToCode("sw", "$t0", "0($t1)");
        AssemblyWriter.appendDebugLine(variableName);
        semanticStack.push(new LocalVarDscp(variableName, Type.INTEGER_NUMBER));
    }

    public void ReadStr() {
        AssemblyWriter.appendComment("read string");
        AssemblyWriter.appendCommandToCode("li", "$v0", "8");
        AssemblyWriter.appendCommandToCode("la", "$a0", "strbuffer");
        AssemblyWriter.appendCommandToCode("li", "$a1", "20");
        AssemblyWriter.appendCommandToCode("move", "$t0", "$a0");
        AssemblyWriter.appendCommandToCode("sw", "$t0", "stradr");
        String variableName = GenerateVariable();
        AssemblyWriter.appendCommandToData(variableName, "space", "20");
        AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
        semanticStack.push(new LocalVarDscp(variableName, Type.STRING));
        AssemblyWriter.appendCommandToCode("syscall");
    }


    public void Print(Descriptor var){
        boolean isInteger = var.type == Type.INTEGER_NUMBER;
        boolean isFloat = var.type == Type.REAL_NUMBER;
        String outputType = isInteger ? "1" : isFloat ? "2" : "4";
        String comment = isInteger ? "integer" : isFloat ? "float" : "string";
        AssemblyWriter.appendComment("print" + " " + comment + " (" + var.name + ")");
        AssemblyWriter.appendCommandToCode("li", "$v0", outputType);
        AssemblyWriter.appendCommandToCode("la", "$t0", var.name);
        if (isFloat) {
            AssemblyWriter.appendCommandToCode("l.s", "$f0", "0($t0)");
            AssemblyWriter.appendCommandToCode("mov.s", "$f12", "$f0");
        } else {
            AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");
            AssemblyWriter.appendCommandToCode("move", "$a0", "$t0");
        }
        AssemblyWriter.appendCommandToCode("syscall");
        if (isFloat || isInteger) {
            AssemblyWriter.appendComment("new line");
            AssemblyWriter.appendCommandToCode("li", "$v0", "4");
            AssemblyWriter.appendCommandToCode("la", "$a0", "nl");
            AssemblyWriter.appendCommandToCode("syscall");
        }
    }

    public void PushType(String typeStr){
        Type type = StringToType(typeStr);
        semanticStack.push(type);
    }

    public void PushClassId(String classId){
        symbolTableGlobalStack.push(new SymbolTable(classId));
    }

    public void PushGlobalTopType(){
        String id = (String) semanticStack.pop();
        Type type = (Type) semanticStack.pop();
        SymbolTable top = symbolTableGlobalStack.pop();
        top.symbolTable.put(id,new LocalVarDscp(id,type));
        symbolTableGlobalStack.push(top);
        semanticStack.push(type);
    }

    public void PushLocalTopType(){
        String id = (String) semanticStack.pop();
        Type type = (Type) semanticStack.pop();
        SymbolTable top = symbolTableLocalStack.pop();
        top.symbolTable.put(id,new LocalVarDscp(id,type));
        symbolTableLocalStack.push(top);
        semanticStack.push(type);
    }

    public void AddGlobalId(){
        String id = (String) semanticStack.pop();
        Type type = (Type) semanticStack.pop();
        SymbolTable top = symbolTableGlobalStack.pop();
        top.symbolTable.put(id,new LocalVarDscp(id,type));
        symbolTableGlobalStack.push(top);
    }
    public void AddLocalId(){
        String id = (String) semanticStack.pop();
        Type type = (Type) semanticStack.pop();
        SymbolTable top = symbolTableLocalStack.pop();
        top.symbolTable.put(id,new LocalVarDscp(id,type));
        symbolTableLocalStack.push(top);
    }

    public void PushId(String id){
        if(!symbolTableLocalStack.isEmpty()){
            SymbolTable symbolTable = symbolTableLocalStack.pop();
            if(symbolTable.symbolTable.containsKey(id)){
                semanticStack.push(symbolTable.symbolTable.get(id));
            }
        }else{
            if(!symbolTableGlobalStack.isEmpty()){
                SymbolTable symbolTable = symbolTableLocalStack.pop();
                if(symbolTable.symbolTable.containsKey(id)){
                    semanticStack.push(symbolTable.symbolTable.get(id));
                }
            }
        }
    }


    public void Assignment(){
        System.out.println("code gen of assignment");
        Descriptor rightSide = (Descriptor) semanticStack.pop();
        Descriptor leftSide = (Descriptor) semanticStack.pop();
        System.out.println(rightSide.type);
        System.out.println(leftSide.type);
        AssemblyWriter.appendComment("assignment " + leftSide.name + " = " + rightSide.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", leftSide.name);
        AssemblyWriter.appendCommandToCode("la", "$t1", rightSide.name);
        AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t1)");
        AssemblyWriter.appendCommandToCode("sw", "$t1", "0($t0)");
        AssemblyWriter.appendDebugLine(leftSide.name);
        /*
        if (TypeChecker.isArrayType(des.getType())) {
            int index = Integer.parseInt(((VariableDescriptor) rightSide).getValue());
            AssemblyWriter.appendCommandToCode("li", "$t0", String.valueOf(index));
            AssemblyWriter.appendCommandToCode("la", "$t1", des.getName());
            AssemblyWriter.appendCommandToCode("li", "$t4", String.valueOf(4)); //TODO: convert 4 to size of
            AssemblyWriter.appendCommandToCode("mul", "$t0", "$t0", "$t4");
            AssemblyWriter.appendCommandToCode("add", "$t1", "$t1", "$t0");
            AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t1)");
            Descriptor leftSide = (Descriptor) SemanticStack.pop();
            AssemblyWriter.appendCommandToCode("sw", "$t1", leftSide.getName());
            AssemblyWriter.appendDebugLine(leftSide.getName());
        } else {
            Descriptor leftSide = (Descriptor) des;
            AssemblyWriter.appendComment("assignment " + leftSide.getName() + " = " + rightSide.getName());
            AssemblyWriter.appendCommandToCode("la", "$t0", leftSide.getName());
            AssemblyWriter.appendCommandToCode("la", "$t1", rightSide.getName());
            AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t1)");
            AssemblyWriter.appendCommandToCode("sw", "$t1", "0($t0)");
            AssemblyWriter.appendDebugLine(leftSide.getName());
        }
         */
    }


    Type StringToType(String type) {
        Type res;
        switch (type) {
            case "bool":
            case "int":
                res = Type.INTEGER_NUMBER;
                break;
            case "double":
                res = Type.REAL_NUMBER;
                break;
            case "string":
                res = Type.STRING;
                break;
            default:
                res = null;
        }
        return res;
    }



    public String GenerateVariable(){
        variableCount++;
        return "var" + (variableCount);
    }

}

