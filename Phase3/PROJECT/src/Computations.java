import java.util.ArrayList;

public class Computations {

    private static String loadAndOperate(Descriptor firstOperandDes, Descriptor secondOperandDes, String operationCommand, String storeCommand, String loadCommand, String variableName0, String variableName1) {
        String variableName = CodeGeneratorImp.GenerateVariable();
        AssemblyWriter.appendComment("Command " + operationCommand + firstOperandDes.name + ", " + secondOperandDes.name );
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t1", secondOperandDes.name);
        AssemblyWriter.appendCommandToCode(loadCommand, variableName0, "0($t0)");
        AssemblyWriter.appendCommandToCode(loadCommand, variableName1, "0($t1)");
        AssemblyWriter.appendCommandToCode(operationCommand, variableName0, variableName0, variableName1);
        return variableName;
    }

    private static void generate2OperandCommands(Descriptor firstOperandDes, Descriptor secondOperandDes, Type resultType, String operationCommand, String storeCommand, String loadCommand, String variableName0, String variableName1) {
        String variableName = loadAndOperate(firstOperandDes, secondOperandDes, operationCommand, storeCommand, loadCommand, variableName0, variableName1);
        AssemblyWriter.appendCommandToData(variableName, "word", "0");
        AssemblyWriter.appendCommandToCode(storeCommand, variableName0, variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
    }

    private static void multiply(Descriptor firstOperandDes, Descriptor secondOperandDes, Type resultType, String operationCommand, String storeCommand, String loadCommand, String variableName0, String variableName1) {
        String variableName = loadAndOperate(firstOperandDes, secondOperandDes, operationCommand, storeCommand, loadCommand, variableName0, variableName1);
        AssemblyWriter.appendCommandToCode("mfhi", "$t1");
        AssemblyWriter.appendCommandToCode("mflo", "$t0");
        AssemblyWriter.appendCommandToData(variableName, "space", "64");
        AssemblyWriter.appendCommandToCode(storeCommand, variableName0, variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
    }

    public static String continueLabel;
    public static String afterCompareLabel;
    public static String variableNameOfContinue;

    private static void divide(Descriptor firstOperandDes, Descriptor secondOperandDes, Type resultType, String operationCommand, String storeCommand, String loadCommand, String variableName0, String variableName1) {

        String variableName = loadAndOperate(firstOperandDes, secondOperandDes, operationCommand, storeCommand, loadCommand, variableName0, variableName1);
        AssemblyWriter.appendCommandToCode("mfhi", "$t1");
        AssemblyWriter.appendCommandToCode("mflo", "$t0");
        AssemblyWriter.appendCommandToData(variableName, "word", "0");

        AssemblyWriter.appendCommandToCode(storeCommand, variableName0, variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
    }

    private static void generateMinusMinusAfterCommand(Descriptor firstOperandDes, Type resultType, String operationCommand) {
        String variableName = CodeGeneratorImp.GenerateVariable();
        AssemblyWriter.appendComment("MinusMinusAfter" + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");
        AssemblyWriter.appendCommandToCode(operationCommand, "$t0", "$t0", "-1");
        AssemblyWriter.appendCommandToData(variableName, "word", "0");
        AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
    }

    private static void generatePlusPlusAfterCommand(Descriptor firstOperandDes, Type resultType, String operationCommand) {
        String variableName = CodeGeneratorImp.GenerateVariable();
        AssemblyWriter.appendComment("PlusPlusAfter" + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");

        AssemblyWriter.appendCommandToCode(operationCommand, "$t0", "$t0", "0x1");
        AssemblyWriter.appendCommandToData(variableName, "word", "0");
        AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
    }


    private static void generateNotCommand(Descriptor firstOperandDes, Type resultType, String operationCommand) {
        String variableName = CodeGeneratorImp.GenerateVariable();
        AssemblyWriter.appendComment("binary " + operationCommand + " expression of " + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");
        AssemblyWriter.appendCommandToCode(operationCommand, "$t0", "$t0");
        AssemblyWriter.appendCommandToData(variableName, "word", "0");
        AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
    }

    public static void Operate(Descriptor firstOperand ,Descriptor  secondOperand,String operation,int line) {
        try {
            Descriptor firstOperandDes = firstOperand;
            Descriptor secondOperandDes = secondOperand;
            Type resultType = firstOperandDes.type;
            String storeCommand = "sw", loadCommand = "lw";
            String variableName0 = "$f0", variableName1 = "$f1";
            String extention = null;

            switch (resultType) {
                case INTEGER_NUMBER:
                    extention = "";
                    storeCommand = "sw";
                    loadCommand = "lw";
                    variableName0 = "$t0";
                    variableName1 = "$t1";
                    break;
                case REAL_NUMBER:
                    extention = ".s";
                    storeCommand = "s.s";
                    loadCommand = "l.s";
                    variableName0 = "$f0";
                    variableName1 = "$f1";
                    break;
                default:
                    resultType = null;
            }

            switch (operation) {
                case "Add":
                    if(firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "add" + extention, storeCommand, loadCommand, variableName0, variableName1);
                    }else
                        throw new TypeError("Add",firstOperandDes.type,Type.INTEGER_NUMBER,line);
                    break;
                case "Sub":
                    if(firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "sub" + extention, storeCommand, loadCommand, variableName0, variableName1);
                    }else
                        throw new TypeError("Sub",firstOperandDes.type,Type.INTEGER_NUMBER,line);
                    break;
                case "Divide":
                    if(firstOperandDes.type == secondOperandDes.type) {
                        divide(firstOperandDes, secondOperandDes, resultType, "div" + extention, storeCommand, loadCommand, variableName0, variableName1);
                    }else{
                            throw new TypeError("Divide",firstOperandDes.type,Type.INTEGER_NUMBER,line);
                        }
                    break;
                case "Mult":
                    if(firstOperandDes.type == secondOperandDes.type) {
                        multiply(firstOperandDes, secondOperandDes, resultType, "mul" + extention, resultType == Type.INTEGER_NUMBER ? "sd" : "s.s", loadCommand, variableName0, variableName1);
                    }else{
                            throw new TypeError("Mult",firstOperandDes.type,Type.INTEGER_NUMBER,line);
                        }
                    break;
                case "MOD":
                    if (firstOperandDes.type == Type.INTEGER_NUMBER && secondOperandDes.type == Type.INTEGER_NUMBER) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "rem", storeCommand, loadCommand, variableName0, variableName1);
                    } else
                        throw new TypeError("Mod",firstOperandDes.type,Type.INTEGER_NUMBER,line);
                        break;
                case "AND":
                    if(firstOperandDes.type == Type.INTEGER_NUMBER) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "and", "sw", "lw", variableName0, variableName1);
                    }else{
                        throw new TypeError("AND",firstOperandDes.type,Type.INTEGER_NUMBER,line);
                    }
                    break;
                case "OR":
                    if(firstOperandDes.type == Type.INTEGER_NUMBER) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "or", "sw", "lw", variableName0, variableName1);
                    }else{
                        throw new TypeError("OR",firstOperandDes.type,Type.INTEGER_NUMBER,line);
                    }

                    break;
                case "XOR":
                    if(firstOperandDes.type == Type.INTEGER_NUMBER) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "xor", "sw", "lw", variableName0, variableName1);
                    }else{
                        throw new TypeError("XOR",firstOperandDes.type,Type.INTEGER_NUMBER,line);
                    }
                    break;





                case "Equal":
                    if (firstOperandDes.type == Type.REAL_NUMBER && firstOperandDes.type == secondOperandDes.type) {
                        String temp = CodeGeneratorImp.GenerateVariable();
                        String continueLabel = CodeGeneratorImp.generateNewLabel();
                        AssemblyWriter.appendComment("Real Compare");
                        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
                        AssemblyWriter.appendCommandToCode("la", "$t1", secondOperandDes.name);
                        AssemblyWriter.appendCommandToCode("l.s", "$f0", "0($t0)");
                        AssemblyWriter.appendCommandToCode("l.s", "$f1", "0($t1)");
                        AssemblyWriter.appendCommandToCode("c.eq.s", "$f0", "$f1");
                        AssemblyWriter.appendCommandToCode("bc1t", continueLabel);
                        AssemblyWriter.appendCommandToCode("li", "$t0", "0");
                        AssemblyWriter.addLabel(continueLabel);
                        AssemblyWriter.appendCommandToCode("sw", "$t0", temp);
                        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(temp, Type.INTEGER_NUMBER));
                    } else if(firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "seq", storeCommand, loadCommand, variableName0, variableName1);
                    }else{
                        throw new TypeError("Equal",firstOperandDes.type,secondOperand.type,line);
                }
                    break;
                case "Smaller":
                    if (firstOperandDes.type == Type.REAL_NUMBER && firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "c.lt.s", storeCommand, loadCommand, variableName0, variableName1);
                    } else if (firstOperandDes.type == Type.INTEGER_NUMBER && firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "slt", storeCommand, loadCommand, variableName0, variableName1);
                    }
                    else{
                        throw new TypeError("Smaller",firstOperandDes.type,secondOperand.type,line);
                    }
                    break;
                case "BiggerEqual":
                    if (firstOperandDes.type == Type.REAL_NUMBER && firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(secondOperandDes, firstOperandDes, resultType, "c.le.s", storeCommand, loadCommand, variableName0, variableName1);
                    } else if (firstOperandDes.type == Type.INTEGER_NUMBER && firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "sge", storeCommand, loadCommand, variableName0, variableName1);
                    }else{
                        throw new TypeError("BiggerEqual",firstOperandDes.type,secondOperand.type,line);
                    }
                    break;
                case "Bigger":
                    if (firstOperandDes.type == Type.REAL_NUMBER && firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(secondOperandDes, firstOperandDes, resultType, "c.lt.s", storeCommand, loadCommand, variableName0, variableName1);
                    } else if (firstOperandDes.type == Type.INTEGER_NUMBER && firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "sgt", storeCommand, loadCommand, variableName0, variableName1);
                    }else{
                        throw new TypeError("Bigger",firstOperandDes.type,secondOperand.type,line);
                    }
                    break;
                case "SmallerEqual":
                    if (firstOperandDes.type == Type.REAL_NUMBER && firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "c.le.s", storeCommand, loadCommand, variableName0, variableName1);
                    } else if (firstOperandDes.type == Type.INTEGER_NUMBER && firstOperandDes.type == secondOperandDes.type) {
                        generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "sle", storeCommand, loadCommand, variableName0, variableName1);
                    }else{
                        throw new TypeError("SmallerEqual",firstOperandDes.type,secondOperand.type,line);
                    }
                    break;
                case "NotEqual":
                    if(firstOperandDes.type==secondOperand.type) {
                        if(firstOperandDes.type == Type.REAL_NUMBER){
                            String temp = CodeGeneratorImp.GenerateVariable();
                            String continueLabel = CodeGeneratorImp.generateNewLabel();
                            AssemblyWriter.appendComment("Real Compare");
                            AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
                            AssemblyWriter.appendCommandToCode("la", "$t1", secondOperandDes.name);
                            AssemblyWriter.appendCommandToCode("l.s", "$f0", "0($t0)");
                            AssemblyWriter.appendCommandToCode("l.s", "$f1", "0($t1)");
                            AssemblyWriter.appendCommandToCode("c.eq.s", "$f0", "$f1");
                            AssemblyWriter.appendCommandToCode("bc1f", continueLabel);
                            AssemblyWriter.appendCommandToCode("li", "$t0", "0");
                            AssemblyWriter.addLabel(continueLabel);
                            AssemblyWriter.appendCommandToCode("sw", "$t0", temp);
                        }else {
                            generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "sne", "sw", "lw", variableName0, variableName1);
                        }
                    }else{
                        throw new TypeError("NOTEqual",firstOperandDes.type,secondOperand.type,line);
                    }
                    break;



                case "PlusPlus":
                    if (firstOperandDes.type == Type.INTEGER_NUMBER) {
                        generatePlusPlusAfterCommand(firstOperandDes, resultType, "addi");
                    } else
                        throw new TypeError("PlusPLusAfter", firstOperandDes.type,Type.INTEGER_NUMBER,line);
                        break;
                case "MinusMinus":
                    if (firstOperandDes.type == Type.INTEGER_NUMBER) {
                        generateMinusMinusAfterCommand(firstOperandDes, resultType, "addi");
                    } else
                        throw new TypeError("MinusMinusAfter", firstOperandDes.type,Type.INTEGER_NUMBER,line);
                        break;
                case "NOT":
                    if(firstOperandDes.type == Type.INTEGER_NUMBER){
                    generateNotCommand(firstOperandDes, resultType, "not");
                    }else{
                        throw new TypeError("NOT",firstOperandDes.type,Type.INTEGER_NUMBER,line);
                    }
                    break;
                default:
                    resultType = null;
            }
        }catch (Exception e){
                CodeGeneratorImp.errors.add(line);
                System.out.println(e.getMessage());
        }
    }
}
