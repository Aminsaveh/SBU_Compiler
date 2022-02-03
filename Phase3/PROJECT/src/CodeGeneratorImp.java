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
                case "AddLocalid":
                    AddLocalId();
                    break;
                case "Assignment":
                    Assignment();
                    break;
                case "pushMethod":
                    PushMethod(scanner.currentSymbol.getToken());
                    break;
                case "pushIdDcl":
                    PushIdDcl(scanner.currentSymbol.getToken());
                    break;
                case "pushGlobalIdDcl":
                     PushGlobalIdDcl(scanner.currentSymbol.getToken());
                     break;
                     case ""

            }


        } catch (Exception e) {


        }
    }

    /* --------------------------- Begin: If Statement -------------------------- */
    // Containers
    public static String afterIfLabel;
    public static String afterElseLabel;

    // Methods
    public void compileIf() {
        Descriptor fooValueDescriptor = (Descriptor) semanticStack.pop();
        afterIfLabel = generateNewLabel();
        afterElseLabel = generateNewLabel();
        AssemblyWriter.appendComment("if code for " + fooValueDescriptor);
        AssemblyWriter.appendCommandToCode("la", "$t0", fooValueDescriptor.name);
        AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t0)");
        AssemblyWriter.appendCommandToCode("beqz", "$t1", afterIfLabel);
    }
    public static void completeIf() {
        AssemblyWriter.appendComment("complete if code");
        AssemblyWriter.appendCommandToCode("j", afterElseLabel);
        AssemblyWriter.addLabel(afterIfLabel);
        AssemblyWriter.addLabel(afterElseLabel);
    }
    public static void elseCode() {
        AssemblyWriter.appendComment("else code");
        AssemblyWriter.deleteLabel(afterIfLabel);
        AssemblyWriter.addLabel(afterIfLabel);
    }
    public static void completeElse() {
        AssemblyWriter.appendComment("complete else code");
        AssemblyWriter.deleteLabel(afterElseLabel);
        AssemblyWriter.addLabel(afterElseLabel);
    }
    // Comments
    // case "if":
    //     new If((Descriptor) SemanticStack.pop()).compile();
    //     break;
    // case "completeIf":
    //     If.completeIf();
    //     break;
    // case "else":
    //     If.elseCode();
    //     break;
    // case "completeElse":
    //     If.completeElse();
    //     break;
    /* ---------------------------- End: If Statement --------------------------- */

    /* ---------------------------- Begin: While Loop --------------------------- */

    // Containers
    public static String startOfConditionLabel;
    public static String endOfWhileLabel;

    // Methods
    public void compile() {
        Descriptor fooValueDescriptor = (Descriptor) semanticStack.pop();
        AssemblyWriter.appendComment("while code for " + fooValueDescriptor);
        AssemblyWriter.appendCommandToCode("la", "$t0", fooValueDescriptor.name);
        AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t0)");
        endOfWhileLabel = generateNewLabel();
        AssemblyWriter.appendCommandToCode("beqz", "$t1", endOfWhileLabel);
    }
    public static void startCondition() {
        startOfConditionLabel = generateNewLabel();
        AssemblyWriter.appendComment("start condition of while");
        AssemblyWriter.addLabel(startOfConditionLabel);
    }

    public static void completeWhile() {
        AssemblyWriter.appendComment("end of while");
        AssemblyWriter.appendCommandToCode("j", startOfConditionLabel);
        AssemblyWriter.addLabel(endOfWhileLabel);
    }
    // Comments
    // case "startConditionWhile":
    //     While.startCondition();
    //     break;
    // case "whileJumpZero":
    //     new While((Descriptor) SemanticStack.pop()).compile();
    //     break;
    // case "completeWhile":
    //     While.completeWhile();
    //     break;
    /* ----------------------------- End: While Loop ---------------------------- */

    /* ----------------------------- Begin: For Loop ---------------------------- */
    // Containers
    public static String startOfForConditionLabel;
    public static String endOfForLabel;
    public static String startOfStepLabel;

    // Methods
    public void compileFor() {
        Descriptor fooDescriptor = (Descriptor) semanticStack.pop();
        AssemblyWriter.appendComment("FOR code for " + fooDescriptor);
        AssemblyWriter.appendCommandToCode("la", "$t0", fooDescriptor.name);
        AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t0)");
        endOfForLabel = generateNewLabel();
        AssemblyWriter.appendCommandToCode("beqz", "$t1", endOfForLabel);
    }

    public static void startForCondition() {
        startOfForConditionLabel = generateNewLabel();
        AssemblyWriter.appendComment("start condition of for");
        AssemblyWriter.addLabel(startOfForConditionLabel);
    }

    public static void completeFor() {
        startOfStepLabel = generateNewLabel();
        AssemblyWriter.appendComment("end of FOR");
        AssemblyWriter.appendCommandToCode("j", startOfStepLabel);
    }

    public static void stepForStatement() {
        AssemblyWriter.appendComment("step of FOR");
        AssemblyWriter.addLabel(startOfStepLabel);
    }

    public static void completeStepOfFor() {
        AssemblyWriter.appendComment("complete step of FOR");
        AssemblyWriter.appendCommandToCode("j", startOfForConditionLabel);
        AssemblyWriter.addLabel(endOfForLabel);
    }
    // Comments
    // case "startConditionFor":
    //     For.startCondition();
    //     break;
    // case "forJumpZero":
    //     new For((Descriptor) SemanticStack.pop()).compile();
    //     break;
    // case "completeFor":
    //     For.completeFor();
    //     For.stepStatement();
    //     For.completeStepOfFor();
    //     break;
    /* ------------------------------ End: For Loop ----------------------------- */

    /* ---------------------------- Begin: Operations --------------------------- */

    // Methods
    public void PlusPlusCommand() {
        Descriptor firstOperandDes = (Descriptor) semanticStack.pop();
        String operationCommand = "++";
        String variableName =  GenerateVariable();
        AssemblyWriter.appendComment("binary " + "++" + " expression of " + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");

        AssemblyWriter.appendCommandToCode(operationCommand, "$t0", "$t0", "0x1");
        AssemblyWriter.appendCommandToData(variableName, "word", "0");
        AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
        AssemblyWriter.appendDebugLine(variableName);
        semanticStack.push(new LocalVarDscp(variableName,  Type.INTEGER_NUMBER));
    }

    // Comments
    // case "plusPlus":
    // firstOperand = (Descriptor) SemanticStack.pop();
    // new PlusPlus(firstOperand).compile();
    // System.out.println("code gen of plus plus");
    // break;
    /* ----------------------------- End: Operations ---------------------------- */



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
        System.out.println("Read Integer : " + variableName);
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
        System.out.println("Read String : " + variableName);
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

        System.out.println("Print : " + var.name);
    }

    public void PushType(String typeStr){
        Type type = StringToType(typeStr);
        semanticStack.push(type);
        System.out.println("PushType : " + type);
    }

    public void PushClassId(String classId){
        symbolTableGlobalStack.push(new SymbolTable(classId));
        System.out.println("PushClassId : " + classId);
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
        System.out.println("AddGlobalId :" + id + " " + type);
        SymbolTable top = symbolTableGlobalStack.pop();
        top.symbolTable.put(id,new LocalVarDscp(id,type));
        symbolTableGlobalStack.push(top);
    }
    public void AddLocalId(){
        String id = (String) semanticStack.pop();
        Type type = (Type) semanticStack.pop();
        System.out.println("AddLocalId :" + id + " " + type);
        SymbolTable top = symbolTableLocalStack.pop();
        top.symbolTable.put(id,new LocalVarDscp(id,type));
        symbolTableLocalStack.push(top);
    }

    public void PushId(String id){
        System.out.println("PushId : " + id);
        boolean isLocal = false;
        if(!symbolTableLocalStack.isEmpty()) {
            SymbolTable symbolTable = symbolTableLocalStack.pop();
            if (symbolTable.symbolTable.containsKey(id)) {
                isLocal = true;
                System.out.println("LocalVar Pushed");
                semanticStack.push(symbolTable.symbolTable.get(id));
            }
            symbolTableLocalStack.push(symbolTable);
        }
        if(!isLocal &&!symbolTableGlobalStack.isEmpty()){
            SymbolTable top = symbolTableGlobalStack.pop();
            if(top.symbolTable.containsKey(id)){
                isLocal=true;
                System.out.println("GlobalVar Pushed" );
                semanticStack.push(top.symbolTable.get(id));
            }
            symbolTableGlobalStack.push(top);
        }

        if(!isLocal){
            //todo error
        }

    }

    public void PushIdDcl(String id){
        //todo check in symboltables
        semanticStack.add(id);
    }
    public void PushGlobalIdDcl(String id){
        //todo check in symboltables
        semanticStack.add(id);
    }


    public void Assignment(){
        Descriptor rightSide = (Descriptor) semanticStack.pop();
        Descriptor leftSide = (Descriptor) semanticStack.pop();
        System.out.println("Assignment " + leftSide.name + " = " + rightSide.name);
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

    public void PushMethod(String methodId){
        symbolTableLocalStack.push(new SymbolTable(methodId));
        System.out.println("PushMethod : " + methodId);
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

    // shitty methods will go here
    private static int labelIndex = 0;
    public static String generateNewLabel() {
        ++labelIndex;
        return "lbl" + labelIndex;
    }
}

