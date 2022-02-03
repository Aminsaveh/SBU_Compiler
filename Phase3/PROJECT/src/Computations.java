import java.util.ArrayList;

public class Computations {

    private static String loadAndOperate(Descriptor firstOperandDes, Descriptor secondOperandDes, String operationCommand, String storeCommand, String loadCommand, String variableName0, String variableName1) {
        String variableName = CodeGeneratorImp.GenerateVariable();
        AssemblyWriter.appendComment("binary " + operationCommand + " expression of " + firstOperandDes.name + ", " + secondOperandDes.name );
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
        AssemblyWriter.appendDebugLine(variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
    }

    private static void multiply(Descriptor firstOperandDes, Descriptor secondOperandDes, Type resultType, String operationCommand, String storeCommand, String loadCommand, String variableName0, String variableName1) {
        String variableName = loadAndOperate(firstOperandDes, secondOperandDes, operationCommand, storeCommand, loadCommand, variableName0, variableName1);
        AssemblyWriter.appendCommandToCode("mfhi", "$t1");
        AssemblyWriter.appendCommandToCode("mflo", "$t0");
        AssemblyWriter.appendCommandToData(variableName, "space", "64");
        AssemblyWriter.appendCommandToCode(storeCommand, variableName0, variableName);
        AssemblyWriter.appendDebugLine(variableName);
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
        AssemblyWriter.appendDebugLine(variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
    }

    private static void generateMinusMinusCommand(Descriptor firstOperandDes, Type resultType, String operationCommand/*, boolean isBeforeExpression*/) {
        String variableName = CodeGeneratorImp.GenerateVariable();
        AssemblyWriter.appendComment("binary " + "--" + " expression of " + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");

        AssemblyWriter.appendCommandToCode(operationCommand, "$t0", "$t0", "-1");
        AssemblyWriter.appendCommandToData(variableName, "word", "0");
        AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
        AssemblyWriter.appendDebugLine(variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
        /*if (isBeforeExpression){
        }
        else {
        }*/
    }

    private static void generatePlusPlusCommand(Descriptor firstOperandDes, Type resultType, String operationCommand/*, boolean isBeforeExpression*/) {
        String variableName = CodeGeneratorImp.GenerateVariable();
        AssemblyWriter.appendComment("binary " + "++" + " expression of " + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");

        AssemblyWriter.appendCommandToCode(operationCommand, "$t0", "$t0", "0x1");
        AssemblyWriter.appendCommandToData(variableName, "word", "0");
        AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
        AssemblyWriter.appendDebugLine(variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
        /*if (isBeforeExpression){
        }
        else {
        }*/
    }

    private static void generateCompare(Descriptor firstOperandDes, Descriptor secondOperandDes) {
        variableNameOfContinue = CodeGeneratorImp.GenerateVariable();
        continueLabel = CodeGeneratorImp.generateNewLabel();
        afterCompareLabel = CodeGeneratorImp.generateNewLabel();
        AssemblyWriter.appendComment("compare of real");
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t1", secondOperandDes.name);
        AssemblyWriter.appendCommandToCode("l.s", "$f0", "0($t0)");
        AssemblyWriter.appendCommandToCode("l.s", "$f1", "0($t1)");
        AssemblyWriter.appendCommandToCode("c.eq.s", "$f0", "$f1");
        AssemblyWriter.appendCommandToCode("bc1t", afterCompareLabel);
        AssemblyWriter.appendCommandToCode("li", "$t0", "0");
        AssemblyWriter.appendCommandToCode("sw", "$t0", variableNameOfContinue);
        AssemblyWriter.addLabel(continueLabel);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableNameOfContinue, Type.INTEGER_NUMBER));
    }

    private static void generateNotCommand(Descriptor firstOperandDes, Type resultType, String operationCommand) {
        String variableName = CodeGeneratorImp.GenerateVariable();
        AssemblyWriter.appendComment("binary " + operationCommand + " expression of " + firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("la", "$t0", firstOperandDes.name);
        AssemblyWriter.appendCommandToCode("lw", "$t0", "0($t0)");

        AssemblyWriter.appendCommandToCode(operationCommand, "$t0", "$t0");
        AssemblyWriter.appendCommandToData(variableName, "word", "0");
        AssemblyWriter.appendCommandToCode("sw", "$t0", variableName);
        AssemblyWriter.appendDebugLine(variableName);
        CodeGeneratorImp.semanticStack.push(new LocalVarDscp(variableName, resultType));
    }

    public static void Operate(Descriptor firstOperand ,Descriptor  secondOperand,String operation) {
        System.out.println("BinaryExpr");
        Descriptor firstOperandDes = firstOperand;
        Descriptor secondOperandDes = secondOperand;
        //TypeChecker.checkType(firstOperandDes.getType(), secondOperandDes.getType(), "add");
        if (firstOperandDes.isLocal) {
           // DescriptorChecker.checkContainsDescriptor(firstOperandDes);
        } else {
           // DescriptorChecker.checkContainsDescriptorGlobal(firstOperandDes);
        }
        if (secondOperandDes.isLocal) {
           // DescriptorChecker.checkContainsDescriptor(secondOperandDes);
        } else {
           // DescriptorChecker.checkContainsDescriptorGlobal(secondOperandDes);
        }

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
            case STRING:
                // extention = "";
                // TODO
                break;
            default:
                resultType = null;
        }

        switch (operation) {
            // Arithmatic
            case "Add":
                generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "add"+ extention, storeCommand, loadCommand, variableName0, variableName1);
                break;
            case "Sub":
                generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "sub"+ extention, storeCommand, loadCommand, variableName0, variableName1);
                break;
            case "Divide":
                //TODO: division by 0
                divide(firstOperandDes, secondOperandDes, resultType, "div"+ extention, storeCommand, loadCommand, variableName0, variableName1);
                break;
            case "Mutlt":
                multiply(firstOperandDes, secondOperandDes, resultType, "mul"+ extention, resultType==Type.INTEGER_NUMBER ? "sd" : "s.s", loadCommand, variableName0, variableName1);
                break;
            case "%":
                if(firstOperandDes.type == Type.INTEGER_NUMBER && secondOperandDes.type == Type.INTEGER_NUMBER ) {
                    generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "rem", storeCommand, loadCommand, variableName0, variableName1);
                }
                else
                    //throw new TypeError("%", firstOperandDes.type, firstOperandDes.type);
                break;
            // and, or, xor
            case "AND":
                generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "and", "sw", "lw", variableName0, variableName1);
                break;
            case "OR":
                generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "or", "sw", "lw", variableName0, variableName1);
                break;
            case "XOR":
                generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "xor", "sw", "lw", variableName0, variableName1);
                break;
            case "~":
                generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "nor", "sw", "lw", variableName0, variableName1);
                break;
            // Comparison
            case "Equal":
//                generateCompare(firstOperand, secondOperand);
                if (firstOperandDes.type == Type.REAL_NUMBER){     //TODO (for all). better: firstOperandDes.getType() == secondOperandDes.getType() == Type.REAL_NUMBER
                    generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "c.eq.s", storeCommand, loadCommand, variableName0, variableName1);
                }
                else {
                    generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "seq", storeCommand, loadCommand, variableName0, variableName1);
                }
                break;
            case "Smaller":
                if (firstOperandDes.type == Type.REAL_NUMBER){
                    generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "c.lt.s", storeCommand, loadCommand, variableName0, variableName1);
                }
                else if(firstOperandDes.type == Type.INTEGER_NUMBER) {
                    generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "slt", storeCommand, loadCommand, variableName0, variableName1);
                }
                break;
            case "BiggerEqual":
                if (firstOperandDes.type == Type.REAL_NUMBER){
                    generate2OperandCommands(secondOperandDes, firstOperandDes, resultType, "c.le.s", storeCommand, loadCommand, variableName0, variableName1);
                }
                else if(firstOperandDes.type == Type.INTEGER_NUMBER) {
                    generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "sge", storeCommand, loadCommand, variableName0, variableName1);
                }
                break;
            case "Bigger":
                if (firstOperandDes.type == Type.REAL_NUMBER){
                    generate2OperandCommands(secondOperandDes, firstOperandDes, resultType, "c.lt.s", storeCommand, loadCommand, variableName0, variableName1);
                }
                else if(firstOperandDes.type == Type.INTEGER_NUMBER) {
                    generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "sgt", storeCommand, loadCommand, variableName0, variableName1);
                }
                break;
            case "SmallerEqual":
                if (firstOperandDes.type == Type.REAL_NUMBER){
                    generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "c.le.s", storeCommand, loadCommand, variableName0, variableName1);
                }
                else if(firstOperandDes.type == Type.INTEGER_NUMBER) {
                    generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "sle", storeCommand, loadCommand, variableName0, variableName1);
                }
                break;
            case "NotEqual":
                generate2OperandCommands(firstOperandDes, secondOperandDes, resultType, "sne", "sw", "lw", variableName0, variableName1);
                break;
            // Unary
            case "++":
                if(firstOperandDes.type == Type.INTEGER_NUMBER) {
                    generatePlusPlusCommand(firstOperandDes, resultType, "addi");
                }
                else
                    //throw new TypeError("++", firstOperandDes.getType());
                break;
            case "--":
                if(firstOperandDes.type == Type.INTEGER_NUMBER) {
                    generateMinusMinusCommand(firstOperandDes, resultType, "addi");
                }
                else
                  // throw new TypeError("--", firstOperandDes.getType());
                break;
            case "NOT":
                //TODO: check if its logical (not int or ...)
                generateNotCommand(firstOperandDes, resultType, "not");
                break;
            default:
                resultType = null;
        }
    }
}
