

import java.util.*;

public class CodeGeneratorImp implements CodeGenerator {
    public static Stack<Object> semanticStack = new Stack<>();
    public static Stack<SymbolTable> symbolTableLocalStack = new Stack<>();
    public static Stack<SymbolTable> symbolTableGlobalStack = new Stack<>();
    public static List<String>  classes = new ArrayList<>();
    public static int variableCount = 0;
    public static List<Integer>  errors = new ArrayList<>();
    public CoolScanner scanner;
    public boolean isPlusPlusAfter = false;
    public boolean isMinusMinusAfter = false;
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
                    AddGlobalId("/$$",Type.INTEGER_NUMBER);
                    break;
                case "pushLocalTopType":
                    PushLocalTopType();
                    break;
                case "AddLocalid":
                    AddLocalId("/$$",Type.INTEGER_NUMBER);
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
                case "OR" :
                case "LogicalOR":
                    ORComputation();
                    break;
                case "XOR":
                    XORComputation();
                    break;
                case "AND":
                case "LogicalAND":
                    ANDComputation();
                    break;
                case "NOT":
                    NOTComputation();
                    break;
                case "Bigger":
                    BiggerComputation();
                    break;
                case "BiggerEqual":
                    BiggerEqualComputation();
                    break;
                case "Smaller":
                    SmallerComputation();
                    break;
                case "SmallerEqual":
                    SmallerEqualComputation();
                    break;
                case "NotEqual":
                    NotEqualComputation();
                    break;
                case "Equal":
                    EqualComputation();
                    break;
                case "Add":
                    AddComputation();
                    break;
                case "Sub":
                    SubComputation();
                    break;
                case "Mult":
                    MultComputation();
                    break;
                case "Divide":
                    DivideComputation();
                    break;
                case "PlusPlus":
                    PlusPlusBeforeComputation();
                    break;
                case "MinusMinus":
                    MinusMinusBeforeComputation();
                    break;
                case "PlusPlusAfter":
                    PlusPlusAfterComputation();
                    break;
                case "MinusMinusAfter":
                    MinusMinusAfterComputation();
                    break;
                case "Add1Assign":
                    Add1Assign();
                    break;
                case "Sub1Assign":
                    Sub1Assign();
                    break;
                case "AddAssign" :
                    AddAssignComputation();
                    break;
                case "SubAssign":
                    SubAssignComputation();
                    break;
                case "MultAssign":
                    MultAssignComputation();
                    break;
                case "DivideAssign":
                    DivideAssignComputation();
                    break;
                case "Cast":
                    Cast();
                    break;
                case "StartForCondition":
                    startForCondition();
                    break;
                case "ForJumpZero":
                    compileFor();
                    break;
                case "CompleteFor":
                    completeFor();
                    stepForStatement();
                    completeStepOfFor();
                    break;
                case "StartWhileCondition":
                    startWhileCondition();
                    break;
                case "WhileJumpZero":
                    StartWhile();
                    break;
                case "CompleteWhile":
                    completeWhile();
                    break;
                case "If":
                    StartIf();
                    break;
                case "CompleteIf":
                    completeIf();
                    break;
                case "Else":
                    elseCode();
                    break;
                case "CompleteElse":
                    completeElse();
                    break;

                case "Return":
                    returnStatement();
                    break;
                case "ArrayAccess":
                    ArrayAccess();
                    break;
                case "pushInt":
                    PushInt();
                    break;
                case "pushReal":
                    PushReal();
                    break;
                case "pushStr":
                    PushStr();
                    break;
                case "TrueConst":
                    TrueConst();
                    break;
                case "FalseConst":
                    FalseConst();
                    break;
                case "popPushArrayType":
                    PopPushArrayType();
                    break;
                case "SetArrayDscp":
                    SetArrayDscp();
                    break;
                case "getLen":
                    GetLen();
                    break;
                case "unreachable":
                    Unreachable();
                    break;
                case "pushClassProperty":
                    PushClassProperty();
                    break;
            }


        } catch (Exception e) {

            CodeGeneratorImp.errors.add(scanner.yyline);
        }
    }

    public void returnStatement(){
        Descriptor fooValue =  (Descriptor) semanticStack.pop();
        AssemblyWriter.addLabel(Computations.afterCompareLabel);
        AssemblyWriter.appendCommandToCode("li", "$t0", "1");
        AssemblyWriter.appendCommandToCode("sw", "$t0", Computations.variableNameOfContinue);
        AssemblyWriter.appendCommandToCode("b", Computations.continueLabel);
        AssemblyWriter.appendComment("return " + fooValue.name);
        AssemblyWriter.appendCommandToCode("li", "$v0", "10");
        AssemblyWriter.appendCommandToCode("la", "$t0", fooValue.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");
        AssemblyWriter.appendCommandToCode("move", "$a0", "$t0");
        AssemblyWriter.appendCommandToCode("syscall");
    }
    /* ------------------------------ Begin: Array ------------------------------ */

    public void ArrayAccess(){
        System.out.println("ArrayAccess");
        Descriptor index = (Descriptor) semanticStack.pop();
        Descriptor arrName = (Descriptor) semanticStack.pop();
        System.out.println("Array access with name " + arrName.name + " at " + index.getValue());
        AssemblyWriter.appendComment("Array access with name " + arrName.name + " at " + index.getValue());
        AssemblyWriter.appendCommandToCode("la", "$t0", arrName.name);
        AssemblyWriter.appendCommandToCode("li", "$t4", "4");
        AssemblyWriter.appendCommandToCode("li", "$t1", String.valueOf(index.getValue()));
        AssemblyWriter.appendCommandToCode("mul", "$t1", "$t1", "$t4");
        AssemblyWriter.appendCommandToCode("add", "$t0", "$t0", "$t1");
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");
        LocalVarDscp lvd = new LocalVarDscp(GenerateVariable(), changeArrayTypeToElementType(arrName.type));
        AssemblyWriter.appendCommandToData(lvd.name, "word", "0");
        semanticStack.push(lvd);
        AssemblyWriter.appendCommandToCode("sw", "$t0", lvd.name);
    }
    /* ------------------------------- End: Array ------------------------------- */



    /* --------------------------- Begin: If Statement -------------------------- */
    // Containers
    public static String afterIfLabel;
    public static String afterElseLabel;

    // Methods
    public void StartIf() {
        Descriptor fooValueDescriptor = (Descriptor) semanticStack.pop();
        afterIfLabel = generateNewLabel();
        afterElseLabel = generateNewLabel();
        AssemblyWriter.appendComment("Start If" + fooValueDescriptor);
        AssemblyWriter.appendCommandToCode("la", "$t0", fooValueDescriptor.name);
        AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t0)");
        AssemblyWriter.appendCommandToCode("beqz", "$t1", afterIfLabel);
    }
    public static void completeIf() {
        AssemblyWriter.appendComment("completeIf");
        AssemblyWriter.appendCommandToCode("j", afterElseLabel);
        AssemblyWriter.addLabel(afterIfLabel);
        AssemblyWriter.addLabel(afterElseLabel);
    }
    public static void elseCode() {
        AssemblyWriter.appendComment("elseCode");
        AssemblyWriter.deleteLabel(afterIfLabel);
        AssemblyWriter.addLabel(afterIfLabel);
    }
    public static void completeElse() {
        AssemblyWriter.appendComment("completeElse");
        AssemblyWriter.deleteLabel(afterElseLabel);
        AssemblyWriter.addLabel(afterElseLabel);
    }
    /* ---------------------------- End: If Statement --------------------------- */

    /* ---------------------------- Begin: While Loop --------------------------- */

    // Containers
    public static String startOfConditionLabel;
    public static String endOfWhileLabel;

    // Methods
    public void StartWhile() {
        Descriptor fooValueDescriptor = (Descriptor) semanticStack.pop();
        AssemblyWriter.appendComment("StartWhile  " + fooValueDescriptor);
        AssemblyWriter.appendCommandToCode("la", "$t0", fooValueDescriptor.name);
        AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t0)");
        endOfWhileLabel = generateNewLabel();
        AssemblyWriter.appendCommandToCode("beqz", "$t1", endOfWhileLabel);
    }
    public static void startWhileCondition() {
        startOfConditionLabel = generateNewLabel();
        AssemblyWriter.appendComment("startWhileCondition");
        AssemblyWriter.addLabel(startOfConditionLabel);
    }

    public static void completeWhile() {
        AssemblyWriter.appendComment("completeWhile");
        AssemblyWriter.appendCommandToCode("j", startOfConditionLabel);
        AssemblyWriter.addLabel(endOfWhileLabel);
    }
    /* ----------------------------- End: While Loop ---------------------------- */

    /* ----------------------------- Begin: For Loop ---------------------------- */
    // Containers
    public static String startOfForConditionLabel;
    public static String endOfForLabel;
    public static String startOfStepLabel;

    // Methods
    public void compileFor() {
        Descriptor fooDescriptor = (Descriptor) semanticStack.pop();
        AssemblyWriter.appendComment("For for " + fooDescriptor);
        AssemblyWriter.appendCommandToCode("la", "$t0", fooDescriptor.name);
        AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t0)");
        endOfForLabel = generateNewLabel();
        AssemblyWriter.appendCommandToCode("beqz", "$t1", endOfForLabel);
    }

    public static void startForCondition() {
        startOfForConditionLabel = generateNewLabel();
        AssemblyWriter.appendComment("startForCondition");
        AssemblyWriter.addLabel(startOfForConditionLabel);
    }

    public static void completeFor() {
        startOfStepLabel = generateNewLabel();
        AssemblyWriter.appendComment("completeFor");
        AssemblyWriter.appendCommandToCode("j", startOfStepLabel);
    }

    public static void stepForStatement() {
        AssemblyWriter.appendComment("stepForStatement");
        AssemblyWriter.addLabel(startOfStepLabel);
    }

    public static void completeStepOfFor() {
        AssemblyWriter.appendComment("complete step of FOR");
        AssemblyWriter.appendCommandToCode("j", startOfForConditionLabel);
        AssemblyWriter.addLabel(endOfForLabel);
    }
    /* ------------------------------ End: For Loop ----------------------------- */


    public void ReadInt() {
        AssemblyWriter.appendComment("Read Int");
        AssemblyWriter.appendCommandToCode("li", "$v0", "5");
        AssemblyWriter.appendCommandToCode("syscall");
        AssemblyWriter.appendCommandToCode("move", "$t0", "$v0");
        String variableName =  GenerateVariable();
        AssemblyWriter.appendCommandToData(variableName, "word", "0");
        AssemblyWriter.appendCommandToCode("la", "$t1", variableName);
        AssemblyWriter.appendCommandToCode("sw", "$t0", "0($t1)");
        System.out.println("Read Integer : " + variableName);
        semanticStack.push(new LocalVarDscp(variableName, Type.INTEGER_NUMBER));
    }

    public void ReadStr() {
        AssemblyWriter.appendComment("Read String");
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
        boolean isReal = var.type == Type.REAL_NUMBER;
        boolean isString = var.type == Type.STRING;
        String outputType="";
        String comment ="";
        if(isReal){
            outputType = "2";
            comment = "Real";
        }
        if(isInteger){
            outputType = "1";
            comment = "Integer";
        }
        if(isString){
            outputType = "4";
            comment = "String";
        }
        AssemblyWriter.appendComment("Print" + " " + comment + " (" + var.name + ")");
        AssemblyWriter.appendCommandToCode("li", "$v0", outputType);
        AssemblyWriter.appendCommandToCode("la", "$t0", var.name);
        if (isReal) {
            AssemblyWriter.appendCommandToCode("l.s", "$f0", "0($t0)");
            AssemblyWriter.appendCommandToCode("mov.s", "$f12", "$f0");
        } else {
            AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");
            AssemblyWriter.appendCommandToCode("move", "$a0", "$t0");
        }
        AssemblyWriter.appendCommandToCode("syscall");
        if (isReal || isInteger) {
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
        try {
            symbolTableGlobalStack.push(new SymbolTable(classId));
            if (classes.contains(classId)) {
                throw new DscpExistError(classId, scanner.yyline);
            }
            classes.add(classId);
            System.out.println("PushClassId : " + classId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void PushGlobalTopType(){
        String id = (String) semanticStack.pop();
        Type type = (Type) semanticStack.pop();
        AddGlobalId(id,type);
        semanticStack.push(type);
    }

    public void PushLocalTopType(){
        String id = (String) semanticStack.pop();
        Type type = (Type) semanticStack.pop();
        AddLocalId(id,type);
        semanticStack.push(type);
    }

    public void AddGlobalId(String _id,Type _type){
        try {
            String id;
            Type type;
            if (_id.contains("/$$")) {
                id = (String) semanticStack.pop();
                type = (Type) semanticStack.pop();
            } else {
                id = _id;
                type = _type;
            }
            SymbolTable table = symbolTableGlobalStack.pop();
            if (type.toString().contains("ARRAY")) {
                if (table.symbolTable.containsKey(id) && table.symbolTable.get(id).type.toString().contains("ARRAY")) {
                    symbolTableGlobalStack.push(table);
                    throw new DscpExistError(id, scanner.yyline);
                } else {
                    System.out.println("GlobalArray :" + id + " " + type);
                    table.symbolTable.put(id, new ArrayDescriptor(id, type, true));
                    symbolTableGlobalStack.push(table);
                }
            } else {
                if (table.symbolTable.containsKey(id) && !table.symbolTable.get(id).type.toString().contains("ARRAY")) {
                    symbolTableGlobalStack.push(table);
                    throw new DscpExistError(id, scanner.yyline);
                } else {
                    System.out.println("AddGlobalId :" + id + " " + type);
                    table.symbolTable.put(id, new LocalVarDscp(id, type));
                    symbolTableGlobalStack.push(table);
                }
            }
        }catch (Exception e){
            CodeGeneratorImp.errors.add(scanner.yyline);
                System.out.println(e.getMessage());
        }
    }

    public void AddLocalId(String _id,Type _type){
        try {
            String id;
            Type type;
            if(_id.contains("NOT")) {
                id = (String) semanticStack.pop();
                type = (Type) semanticStack.pop();
            }else{
                id = _id;
                type = _type;
            }
            SymbolTable table = symbolTableLocalStack.pop();
            if (type.toString().contains("ARRAY")) {
                if (table.symbolTable.containsKey(id) && table.symbolTable.get(id).type.toString().contains("ARRAY")) {
                    symbolTableLocalStack.push(table);
                    throw new DscpExistError(id, scanner.yyline);
                } else {
                    System.out.println("LocalArray :" + id + " " + type);
                    table.symbolTable.put(id, new ArrayDescriptor(id, type, true));
                    symbolTableLocalStack.push(table);
                }
            } else {
                if (table.symbolTable.containsKey(id) && !table.symbolTable.get(id).type.toString().contains("ARRAY")) {
                    symbolTableLocalStack.push(table);
                    throw new DscpExistError(id, scanner.yyline);
                } else {
                    System.out.println("AddLocalId :" + id + " " + type);
                    table.symbolTable.put(id, new LocalVarDscp(id, type));
                    symbolTableLocalStack.push(table);
                }
            }

        }catch (Exception e){
            CodeGeneratorImp.errors.add(scanner.yyline);
            System.out.println(e.getMessage());
        }
    }

    public void PushId(String id){
        try {
            System.out.println("PushId : " + id);
            boolean isLocal = false;
            if (!symbolTableLocalStack.isEmpty()) {
                SymbolTable symbolTable = symbolTableLocalStack.pop();
                if (symbolTable.symbolTable.containsKey(id)) {
                    isLocal = true;
                    System.out.println("LocalVar Pushed");
                    semanticStack.push(symbolTable.symbolTable.get(id));
                }
                symbolTableLocalStack.push(symbolTable);
            }
            if (!isLocal && !symbolTableGlobalStack.isEmpty()) {
                SymbolTable top = symbolTableGlobalStack.pop();
                if (top.symbolTable.containsKey(id)) {
                    isLocal = true;
                    System.out.println("GlobalVar Pushed");
                    System.out.println(top.symbolTable.get(id).name);
                    String name = top.symbolTable.get(id).name;
                    Descriptor temp = top.symbolTable.get(id);
                    temp.name = name;
                    semanticStack.push(temp);
                }
                symbolTableGlobalStack.push(top);
            }

            if (!isLocal) {
                if(classes.contains(id)){
                    semanticStack.push(id);
                }else {
                    throw new NotFoundDscpError(id, scanner.yyline);
                }
            }
        }catch (Exception e){
            CodeGeneratorImp.errors.add(scanner.yyline);
            System.out.println(e.getMessage());
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
        try {
            System.out.println("Assignment ");
            Descriptor rightSide = (Descriptor) semanticStack.pop();
            if(isPlusPlusAfter||isMinusMinusAfter){
                System.out.println("AfterPlusMinus");
                Descriptor rightRightSide = (Descriptor) semanticStack.pop();
            }
            Descriptor leftSide = (Descriptor) semanticStack.pop();
            System.out.println("Assignment " + leftSide.name + " = " + rightSide.name);
            if(leftSide.type == rightSide.type) {
                AssemblyWriter.appendComment("Assignment " + leftSide.name + "Type : " + leftSide.type + " = " + rightSide.name + "Type" + rightSide.type);
                AssemblyWriter.appendCommandToCode("la", "$t0", leftSide.name);
                AssemblyWriter.appendCommandToCode("la", "$t1", rightSide.name);
                AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t1)");
                AssemblyWriter.appendCommandToCode("sw", "$t1", "0($t0)");
                if(isPlusPlusAfter || isMinusMinusAfter) {
                    semanticStack.push(rightSide);
                    if(isPlusPlusAfter){
                        scanner.ICV = 1;
                        PushInt();
                    }else{
                        scanner.ICV = -1;
                        PushInt();
                    }
                    isPlusPlusAfter=false;
                    isMinusMinusAfter=false;
                    semanticStack.push(rightSide);
                    AddComputation();
                    Assignment();
                }
            }else{
                errors.add(scanner.yyline);
                throw new TypeError("Assignment",leftSide.type,rightSide.type,scanner.yyline);
            }
        }catch (Exception e){
            isPlusPlusAfter=false;
            isMinusMinusAfter=false;
            CodeGeneratorImp.errors.add(scanner.yyline);
            System.out.println(e.getMessage());
        }
    }


    public void PushMethod(String methodId){
        symbolTableLocalStack.push(new SymbolTable(methodId));
        System.out.println("PushMethod : " + methodId);
    }


    Type StringToType(String type) {
        Type res;
        switch (type) {
            case "bool":
                res = Type.Boolean;
                break;
            case "int":
                res = Type.INTEGER_NUMBER;
                break;
            case "real":
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


    public void ORComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("OR Computation");
        Computations.Operate(firstOperand,secondOperand,"OR",scanner.yyline);
    }

    public void XORComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("XOR Computation");
        Computations.Operate(firstOperand,secondOperand,"XOR",scanner.yyline);
    }

    public void ANDComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("AND Computation");
        Computations.Operate(firstOperand,secondOperand,"AND",scanner.yyline);
    }

    public void BiggerComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Bigger Computation");
        Computations.Operate(firstOperand,secondOperand,"Bigger",scanner.yyline);
    }

    public void SmallerComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Smaller Computation");
        Computations.Operate(firstOperand,secondOperand,"Smaller",scanner.yyline);
    }

    public void SmallerEqualComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("SmallerEqual Computation");
        Computations.Operate(firstOperand,secondOperand,"SmallerEqual",scanner.yyline);
    }

    public void BiggerEqualComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("BiggerEqual Computation");
        Computations.Operate(firstOperand,secondOperand,"BiggerEqual",scanner.yyline);
    }

    public void EqualComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Equal Computation");
        Computations.Operate(firstOperand,secondOperand,"Equal",scanner.yyline);
    }

    public void NotEqualComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("NotEqual Computation");
        Computations.Operate(firstOperand,secondOperand,"NotEqual",scanner.yyline);
    }

    public void NOTComputation(){
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Not Computation");
        Computations.Operate(firstOperand,firstOperand,"NOT",scanner.yyline);
    }

    public void PlusPlusBeforeComputation(){
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("PlusPlusBefore Computation");
        Computations.Operate(firstOperand,firstOperand,"PlusPlusBefore",scanner.yyline);
    }
    public void PlusPlusAfterComputation(){
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("PlusPlusBefore Computation");
        Computations.Operate(firstOperand,firstOperand,"PlusPlus",scanner.yyline);
        isPlusPlusAfter = true;
        semanticStack.push(firstOperand);
    }

    public void MinusMinusBeforeComputation(){
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("MinusMinusAfter Computation");
        Computations.Operate(firstOperand,firstOperand,"MinusMinusBefore",scanner.yyline);
    }
    public void MinusMinusAfterComputation(){
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("MinusMinusAfter Computation");
        Computations.Operate(firstOperand,firstOperand,"MinusMinus",scanner.yyline);
        isMinusMinusAfter = true;
        semanticStack.push(firstOperand);
    }

    public void Add1Assign(){
        scanner.ICV = 1;
        PushInt();
        AddAssignComputation();
        System.out.println("Add1Assign");
    }

    public void Sub1Assign(){
        scanner.ICV = -1;
        PushInt();
        AddAssignComputation();
        System.out.println("Sub1Assign");
    }

    public void AddComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Add Computation");
        Computations.Operate(firstOperand,secondOperand,"Add",scanner.yyline);
    }

    public void SubComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Sub Computation");
        Computations.Operate(firstOperand,secondOperand,"Sub",scanner.yyline);
    }

    public void MultComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Mult Computation");
        Computations.Operate(firstOperand,secondOperand,"Mult",scanner.yyline);
    }

    public void DivideComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Divide Computation");
        Computations.Operate(firstOperand,secondOperand,"Divide",scanner.yyline);
    }

    public void AddAssignComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        semanticStack.push(firstOperand);
        System.out.println("AddAssign Computation");
        Computations.Operate(firstOperand,secondOperand,"Add",scanner.yyline);
        Assignment();
    }
    public void SubAssignComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        semanticStack.push(firstOperand);
        System.out.println("SubAssign Computation");
        Computations.Operate(firstOperand,secondOperand,"Sub",scanner.yyline);
        Assignment();
    }
    public void MultAssignComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        semanticStack.push(firstOperand);
        System.out.println("MultAssign Computation");
        Computations.Operate(firstOperand,secondOperand,"Mult",scanner.yyline);
        Assignment();
    }
    public void DivideAssignComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        semanticStack.push(firstOperand);
        System.out.println("DivideAssign Computation");
        Computations.Operate(firstOperand,secondOperand,"Divide",scanner.yyline);
        Assignment();
    }


    public void Cast(){
        try {
            Descriptor des = (Descriptor) semanticStack.pop();
            Type type = (Type) semanticStack.pop();
            if (type == Type.INTEGER_NUMBER && des.type == Type.REAL_NUMBER) {
                RealToInt(des, type);
            } else if (type == Type.REAL_NUMBER && des.type == Type.INTEGER_NUMBER) {
                IntToReal(des, type);
            } else {
                throw new CastError(des.type, type, scanner.yyline);
            }
        }catch (Exception e){
            CodeGeneratorImp.errors.add(scanner.yyline);
            System.out.println(e.getMessage());
        }
    }

    public void RealToInt(Descriptor firstOperandDes, Type resultType){
        AssemblyWriter.appendComment("RealToInt" + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");
        AssemblyWriter.appendCommandToCode("mtc1", "$t0", "$f0");
        AssemblyWriter.appendCommandToCode("cvt.w.s", "$f1", "$f0");
        AssemblyWriter.appendCommandToCode("s.s", "$f1", firstOperandDes.name);
        semanticStack.push(new LocalVarDscp(firstOperandDes.name, resultType));
        System.out.println("RealToInt");
    }

    public void IntToReal(Descriptor firstOperandDes, Type resultType){
        AssemblyWriter.appendComment("IntToReal" + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");
        AssemblyWriter.appendCommandToCode("mtc1", "$t0", "$f0");
        AssemblyWriter.appendCommandToCode("cvt.s.w", "$f1", "$f0");
        AssemblyWriter.appendCommandToCode("s.s", "$f1", firstOperandDes.name);
        semanticStack.push(new LocalVarDscp(firstOperandDes.name, resultType));
        System.out.println("IntToReal");
    }

    public void PushInt(){
        System.out.println("PushInt");
        System.out.println(scanner.ICV);
        SymbolTable Top = symbolTableGlobalStack.pop();
        if(!Top.symbolTable.containsKey("$" + scanner.ICV)){
                String variableName = GenerateVariable();
                Descriptor descriptor = new LocalVarDscp(variableName, Type.INTEGER_NUMBER);
                descriptor.setValue(String.valueOf(scanner.ICV));
                AssemblyWriter.appendComment("ICV");
                AssemblyWriter.appendCommandToCode("li", "$t0", String.valueOf(scanner.ICV));
                AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
                Top.symbolTable.put("$" + scanner.ICV, descriptor);
                AssemblyWriter.appendCommandToData(variableName, "word", "0");
                semanticStack.push(descriptor);
                symbolTableGlobalStack.push(Top);
            }
        else
            {
                semanticStack.push((Descriptor)Top.symbolTable.get("$" + scanner.ICV));
                symbolTableGlobalStack.push(Top);
            }
        System.out.println("PushInt");
    }
    public void PushReal(){
        System.out.println("PushReal");
        System.out.println(scanner.RCV);
        SymbolTable Top = symbolTableGlobalStack.pop();
        if(!Top.symbolTable.containsKey("$" + scanner.RCV)){

            String variableName = GenerateVariable();
            Descriptor descriptor = new LocalVarDscp(variableName, Type.REAL_NUMBER);
            descriptor.setValue(String.valueOf(scanner.RCV));
            AssemblyWriter.appendComment("RCV");
            AssemblyWriter.appendCommandToCode("li.s", "$f0", String.valueOf(scanner.RCV));
            AssemblyWriter.appendCommandToCode("s.s", "$f0", variableName);
            Top.symbolTable.put("$" + scanner.RCV, descriptor);
            AssemblyWriter.appendCommandToData(variableName, "word", "0");
            semanticStack.push(descriptor);
            symbolTableGlobalStack.push(Top);
        }
        else
        {
            semanticStack.push((Descriptor)Top.symbolTable.get("$" + scanner.RCV));
            symbolTableGlobalStack.push(Top);
        }
        System.out.println("PushReal");
    }


    public void PushStr(){
        System.out.println("PushStr");
        System.out.println(scanner.stringValue);
        SymbolTable Top = symbolTableGlobalStack.pop();
        if(!Top.symbolTable.containsKey("$$" + scanner.stringValue)){

            String variableName = GenerateVariable();
            Descriptor descriptor = new LocalVarDscp(variableName, Type.STRING);
            descriptor.setValue(String.valueOf(scanner.stringValue));
            AssemblyWriter.appendComment("SCV");
            AssemblyWriter.appendCommandToData(variableName, "asciiz",  scanner.stringValue);
            Top.symbolTable.put("$$" + scanner.stringValue, descriptor);
            semanticStack.push(descriptor);
            symbolTableGlobalStack.push(Top);
        }
        else
        {
            semanticStack.push((Descriptor)Top.symbolTable.get("$$" + scanner.stringValue));
            symbolTableGlobalStack.push(Top);
        }
        System.out.println("PushStr");
    }
    public void TrueConst(){
        System.out.println("TrueConst");
        System.out.println(1);
        SymbolTable Top = symbolTableGlobalStack.pop();
        if(!Top.symbolTable.containsKey("$" + 1)){

            String variableName = GenerateVariable();
            Descriptor descriptor = new LocalVarDscp(variableName, Type.INTEGER_NUMBER);
            descriptor.setValue(String.valueOf(1));
            AssemblyWriter.appendComment("BCV");
            AssemblyWriter.appendCommandToCode("li", "$t0", String.valueOf(1));
            AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
            Top.symbolTable.put("$" + 1, descriptor);
            AssemblyWriter.appendCommandToData(variableName, "word", "0");
            semanticStack.push(descriptor);
            symbolTableGlobalStack.push(Top);
        }
        else
        {
            semanticStack.push((Descriptor)Top.symbolTable.get("$" + 1));
            symbolTableGlobalStack.push(Top);
        }
        System.out.println("TrueConst");
    }
    public void FalseConst(){
        System.out.println("FalseConst");
        System.out.println(0);
        SymbolTable Top = symbolTableGlobalStack.pop();
        if(!Top.symbolTable.containsKey("$" + 0)){

            String variableName = GenerateVariable();
            Descriptor descriptor = new LocalVarDscp(variableName, Type.INTEGER_NUMBER);
            descriptor.setValue(String.valueOf(0));
            AssemblyWriter.appendComment("BCV");
            AssemblyWriter.appendCommandToCode("li", "$t0", String.valueOf(0));
            AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
            Top.symbolTable.put("$" + 0, descriptor);
            AssemblyWriter.appendCommandToData(variableName, "word", "0");
            semanticStack.push(descriptor);
            symbolTableGlobalStack.push(Top);
        }
        else
        {
            semanticStack.push((Descriptor)Top.symbolTable.get("$" + 0));
            symbolTableGlobalStack.push(Top);
        }
        System.out.println("FalseConst");
    }
    public void PopPushArrayType(){
        System.out.println("PopPushArrayType");
        Type type = (Type)semanticStack.pop();
        Type resType = null;
        switch (type) {
            case INTEGER_NUMBER:
                resType = Type.INT_ARRAY;
                break;
            case REAL_NUMBER:
                resType = Type.REAL_ARRAY;
                break;
            case STRING:
                resType = Type.STRING_ARRAY;
                break;
        }
        System.out.println(resType);
        semanticStack.push(resType);
    }

    public void SetArrayDscp(){
        System.out.println("SetArrayDscp");
        System.out.println("SetArrayDscp");
        Descriptor sizeDescriptor = (Descriptor) semanticStack.pop();
        System.out.println(sizeDescriptor.name);
        Type newArrayType = (Type) semanticStack.pop();
        System.out.println(newArrayType.toString());
        ArrayDescriptor nameOfArrayDes = (ArrayDescriptor) semanticStack.pop();
        System.out.println(nameOfArrayDes.name);
        nameOfArrayDes.size = Integer.parseInt(sizeDescriptor.getValue());
        if (nameOfArrayDes.isLocal) {
            SymbolTable top = symbolTableLocalStack.pop();
            top.symbolTable.put(nameOfArrayDes.name, nameOfArrayDes);
            symbolTableLocalStack.push(top);
        } else {
            SymbolTable top = symbolTableGlobalStack.pop();
            top.symbolTable.put(nameOfArrayDes.name, nameOfArrayDes);
            symbolTableGlobalStack.push(top);
        }
        System.out.println(nameOfArrayDes.name + sizeDescriptor.getValue());
        AssemblyWriter.appendCommandToData(nameOfArrayDes.name, "space", String.valueOf(4 * Integer.parseInt(sizeDescriptor.getValue())));
    }

    public void GetLen(){
        try {
            Descriptor descriptor = (Descriptor) semanticStack.pop();
            if(descriptor instanceof  ArrayDescriptor){
                scanner.ICV = ((ArrayDescriptor) descriptor).size;
                PushInt();
                System.out.println("GetLen : " + ((ArrayDescriptor) descriptor).size);
            }else{
                throw new TypeError("GetLen",Type.INT_ARRAY,descriptor.type, scanner.yyline);
            }
        } catch (Exception e) {
            errors.add(scanner.yyline);
            System.out.println(e.getMessage());
        }
    }
    public void Unreachable(){
        System.out.println("Unreachable");
        AssemblyWriter.appendComment("Unreachable Code");
    }

    public void PushClassProperty(){
        try {
            String classId = (String) semanticStack.pop();
            String propertyId = scanner.currentSymbol.getToken();
            Stack<SymbolTable> temp;
            boolean found = false;
            temp = (Stack<SymbolTable>) symbolTableGlobalStack.clone();
            while (!temp.isEmpty()) {
                SymbolTable tmp = temp.pop();
                if (tmp.scopeName.contains(classId)) {
                    found = true;
                    if (tmp.symbolTable.containsKey(propertyId)) {
                        semanticStack.push(tmp.symbolTable.get(propertyId));
                        break;
                    } else {
                        throw new NotFoundDscpError(propertyId, scanner.yyline);
                    }
                }
            }
            if (!found) {
                throw new NotFoundDscpError(classId, scanner.yyline);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("PushClassProperty");
    }




    // utils
    public static String GenerateVariable(){
        variableCount++;
        return "var" + (variableCount);
    }
    Type changeArrayTypeToElementType(Type arrType) {
        Type res;
        switch (arrType) {
            case REAL_ARRAY:
                res = Type.REAL_NUMBER;
                break;
            case INT_ARRAY:
                res = Type.INTEGER_NUMBER;
                break;
            case STRING_ARRAY:
                res = Type.STRING;
                break;
            default:
                res = null;
        }
        return res;
    }
    private static int labelIndex = 0;
    public static String generateNewLabel() {
        ++labelIndex;
        return "lbl" + labelIndex;
    }
}

