

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class CodeGeneratorImp implements CodeGenerator {
    public static Stack<Object> semanticStack = new Stack<>();
    public static Stack<SymbolTable> symbolTableLocalStack = new Stack<>();
    public static Stack<SymbolTable> symbolTableGlobalStack = new Stack<>();
    public static int variableCount = 0;
    public CoolScanner scanner;
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
                    PlusPlusComputation();
                    break;
                case "MinusMinus":
                    MinusMinusComputation();
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
                    compileWhile();
                    break;
                case "CompleteWhile":
                    completeWhile();
                    break;
                case "If":
                    compileIf();
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
            }


        } catch (Exception e) {


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
    // TODO: We need to test this section.

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

    /*
    public void pushInt(){
        int fooIntValue = 0;
        System.out.println(fooIntValue);
        Descriptor descriptor = (Descriptor) GlobalSymbolTable.getSymbolTable().getDescriptor("$" + fooIntValue);
        boolean hasDescriptor = descriptor != null;
        if (!hasDescriptor) {
            String variableName = GenerateVariable();
            descriptor = new GlobalVariableDescriptor(variableName, Type.INTEGER_NUMBER);
            descriptor.setValue(String.valueOf(fooIntValue));
            AssemblyWriter.appendComment("integer constant");
            AssemblyWriter.appendCommandToCode("li", "$t0", String.valueOf(fooIntValue));
            AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
            GlobalSymbolTable.getSymbolTable().addDescriptor("$" + fooIntValue, descriptor);
            AssemblyWriter.appendCommandToData(variableName, "word", "0");
            AssemblyWriter.appendDebugLine(variableName);
        }
        semanticStack.push(descriptor);
    }
     */

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
    /* ---------------------------- End: If Statement --------------------------- */

    /* ---------------------------- Begin: While Loop --------------------------- */

    // Containers
    public static String startOfConditionLabel;
    public static String endOfWhileLabel;

    // Methods
    public void compileWhile() {
        Descriptor fooValueDescriptor = (Descriptor) semanticStack.pop();
        AssemblyWriter.appendComment("while code for " + fooValueDescriptor);
        AssemblyWriter.appendCommandToCode("la", "$t0", fooValueDescriptor.name);
        AssemblyWriter.appendCommandToCode("lw", "$t1", "0($t0)");
        endOfWhileLabel = generateNewLabel();
        AssemblyWriter.appendCommandToCode("beqz", "$t1", endOfWhileLabel);
    }
    public static void startWhileCondition() {
        startOfConditionLabel = generateNewLabel();
        AssemblyWriter.appendComment("start condition of while");
        AssemblyWriter.addLabel(startOfConditionLabel);
    }

    public static void completeWhile() {
        AssemblyWriter.appendComment("end of while");
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
    /* ------------------------------ End: For Loop ----------------------------- */

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
        if(type.toString().contains("ARRAY")) {
            System.out.println("GlobalArray :" + id + " " + type);
            SymbolTable top = symbolTableGlobalStack.pop();
            if (!top.symbolTable.containsKey(id)) {
                top.symbolTable.put(id, new ArrayDescriptor(id, type, true));
                symbolTableGlobalStack.push(top);
            }
        }else {
            System.out.println("AddGlobalId :" + id + " " + type);
            SymbolTable top = symbolTableGlobalStack.pop();
            top.symbolTable.put(id, new LocalVarDscp(id, type));
            symbolTableGlobalStack.push(top);
        }
    }
    public void AddLocalId(){
        String id = (String) semanticStack.pop();
        Type type = (Type) semanticStack.pop();
        if(type.toString().contains("ARRAY")){
            System.out.println("LocalArray :" + id + " " + type);
            SymbolTable top = symbolTableLocalStack.pop();
            if(!top.symbolTable.containsKey(id)){
                top.symbolTable.put(id,  new ArrayDescriptor(id,type,true));
               symbolTableLocalStack.push(top);
            }
        }else{
            System.out.println("AddLocalId :" + id + " " + type);
            SymbolTable top = symbolTableLocalStack.pop();
            top.symbolTable.put(id,new LocalVarDscp(id,type));
            symbolTableLocalStack.push(top);
        }

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
                System.out.println(top.symbolTable.get(id).name);
                String name = top.symbolTable.get(id).name;
                Descriptor temp = top.symbolTable.get(id);
                temp.name = name;
                semanticStack.push(temp);
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
        System.out.println("Assignment ");
        System.out.println(semanticStack.size());
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
        if (leftSide.type.toString().contains("ARRAY")) {
            int index = Integer.parseInt(((VariableDescriptor) rightSide).getValue());
            AssemblyWriter.appendCommandToCode("li", "$t0", String.valueOf(index));
            AssemblyWriter.appendCommandToCode("la", "$t1", leftSide.name);
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
        Computations.Operate(firstOperand,secondOperand,"OR");
    }

    public void XORComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("XOR Computation");
        Computations.Operate(firstOperand,secondOperand,"XOR");
    }

    public void ANDComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("AND Computation");
        Computations.Operate(firstOperand,secondOperand,"AND");
    }

    public void BiggerComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Bigger Computation");
        Computations.Operate(firstOperand,secondOperand,"Bigger");
    }

    public void SmallerComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Smaller Computation");
        Computations.Operate(firstOperand,secondOperand,"Smaller");
    }

    public void SmallerEqualComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("SmallerEqual Computation");
        Computations.Operate(firstOperand,secondOperand,"SmallerEqual");
    }

    public void BiggerEqualComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("BiggerEqual Computation");
        Computations.Operate(firstOperand,secondOperand,"BiggerEqual");
    }

    public void EqualComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Equal Computation");
        Computations.Operate(firstOperand,secondOperand,"Equal");
    }

    public void NotEqualComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("NotEqual Computation");
        Computations.Operate(firstOperand,secondOperand,"NotEqual");
    }

    public void NOTComputation(){
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Not Computation");
        Computations.Operate(firstOperand,firstOperand,"NOT");
    }

    public void PlusPlusComputation(){
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("PlusPlus Computation");
        Computations.Operate(firstOperand,firstOperand,"PlusPlus");
    }

    public void MinusMinusComputation(){
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("MinusMinus Computation");
        Computations.Operate(firstOperand,firstOperand,"MinusMinus");
    }

    public void AddComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Add Computation");
        Computations.Operate(firstOperand,secondOperand,"Add");
    }

    public void SubComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Sub Computation");
        Computations.Operate(firstOperand,secondOperand,"Sub");
    }

    public void MultComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Mult Computation");
        Computations.Operate(firstOperand,secondOperand,"Mult");
    }

    public void DivideComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        System.out.println("Divide Computation");
        Computations.Operate(firstOperand,secondOperand,"Divide");
    }

    public void AddAssignComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        semanticStack.push(firstOperand);
        System.out.println("AddAssign Computation");
        Computations.Operate(firstOperand,secondOperand,"Add");
        Assignment();
    }
    public void SubAssignComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        semanticStack.push(firstOperand);
        System.out.println("SubAssign Computation");
        Computations.Operate(firstOperand,secondOperand,"Sub");
        Assignment();
    }
    public void MultAssignComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        semanticStack.push(firstOperand);
        System.out.println("MultAssign Computation");
        Computations.Operate(firstOperand,secondOperand,"Mult");
        Assignment();
    }
    public void DivideAssignComputation(){
        Descriptor secondOperand = (Descriptor) semanticStack.pop();
        Descriptor firstOperand = (Descriptor) semanticStack.pop();
        semanticStack.push(firstOperand);
        System.out.println("DivideAssign Computation");
        Computations.Operate(firstOperand,secondOperand,"Divide");
        Assignment();
    }

    public void Cast(){
        Descriptor des = (Descriptor) semanticStack.pop();
        Type type = (Type) semanticStack.pop();
        if (type == Type.INTEGER_NUMBER) {
            RealToInt(des, type);
        } else if (type == Type.REAL_NUMBER) {
            IntToReal(des, type);
        } else {
            String srcType = des.type.toString();
            String destType = type.toString();
            //new CastError(srcType, destType).error();
        }
    }




    public void RealToInt(Descriptor firstOperandDes, Type resultType){
        AssemblyWriter.appendComment("binary " + "Convert" + " expression of " + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");
        AssemblyWriter.appendCommandToCode("mtc1", "$t0", "$f0");
        AssemblyWriter.appendCommandToCode("cvt.w.s", "$f1", "$f0");
//        AssemblyFileWriter.appendCommandToData(firstOperandDes.getName(), "word", "0");
        AssemblyWriter.appendCommandToCode("s.s", "$f1", firstOperandDes.name);
//        AssemblyFileWriter.appendDebugLine(firstOperandDes.getName());
        semanticStack.push(new LocalVarDscp(firstOperandDes.name, resultType));
        System.out.println("RealToInt");
    }

    public void IntToReal(Descriptor firstOperandDes, Type resultType){
        AssemblyWriter.appendComment("binary " + "Convert" + " expression of " + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");
        AssemblyWriter.appendCommandToCode("mtc1", "$t0", "$f0");
        AssemblyWriter.appendCommandToCode("cvt.s.w", "$f1", "$f0");
//        AssemblyFileWriter.appendCommandToData(firstOperandDes.getName(), "word", "0");
        AssemblyWriter.appendCommandToCode("s.s", "$f1", firstOperandDes.name);
//        AssemblyFileWriter.appendDebugLine(firstOperandDes.getName());
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
                AssemblyWriter.appendComment("integer constant");
                AssemblyWriter.appendCommandToCode("li", "$t0", String.valueOf(scanner.ICV));
                AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
                Top.symbolTable.put("$" + scanner.ICV, descriptor);
                AssemblyWriter.appendCommandToData(variableName, "word", "0");
                AssemblyWriter.appendDebugLine(variableName);
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
            AssemblyWriter.appendComment("Real constant");
            AssemblyWriter.appendCommandToCode("li.s", "$f0", String.valueOf(scanner.RCV));
            AssemblyWriter.appendCommandToCode("s.s", "$f0", variableName);
            Top.symbolTable.put("$" + scanner.RCV, descriptor);
            AssemblyWriter.appendCommandToData(variableName, "word", "0");
            AssemblyWriter.appendDebugLine(variableName);
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
            AssemblyWriter.appendComment("string constant");
            AssemblyWriter.appendCommandToData(variableName, "asciiz",  scanner.stringValue);
            Top.symbolTable.put("$$" + scanner.stringValue, descriptor);
            AssemblyWriter.appendDebugLine(variableName);
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
            AssemblyWriter.appendComment("Boolean constant");
            AssemblyWriter.appendCommandToCode("li", "$t0", String.valueOf(1));
            AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
            Top.symbolTable.put("$" + 1, descriptor);
            AssemblyWriter.appendCommandToData(variableName, "word", "0");
            AssemblyWriter.appendDebugLine(variableName);
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
            AssemblyWriter.appendComment("Boolean constant");
            AssemblyWriter.appendCommandToCode("li", "$t0", String.valueOf(0));
            AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
            Top.symbolTable.put("$" + 0, descriptor);
            AssemblyWriter.appendCommandToData(variableName, "word", "0");
            AssemblyWriter.appendDebugLine(variableName);
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
        /*
        if (nameOfArrayDes.getIsLocal()) {
            DescriptorChecker.checkContainsDescriptor(nameOfArrayDes);
        } else {
            DescriptorChecker.checkContainsDescriptorGlobal(nameOfArrayDes);
        }

         */
        //TypeChecker.checkArrayType(nameOfArrayDes.getType(), newArrayType);
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





    // shitty methods will go here
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

