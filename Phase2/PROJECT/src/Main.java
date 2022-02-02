import java.io.*;

public class Main {
    public static void parseAndWriteOutput(Parser parser, String outputFilePath) throws IOException {
        File myObj = new File(outputFilePath);
        myObj.createNewFile();
        FileWriter myWriter = new FileWriter(outputFilePath);
        try {
            parser.parse();
            myWriter.write("Syntax is correct!");
            myWriter.close();

        } catch (Exception e) {
            myWriter.write("Syntax is wrong!");
            System.out.println(e.toString());
            myWriter.close();
        }
    }
    public static void main(String[] args) throws IOException {
        String inputCoolFilePath = "", outputFilePath = "", tablePath = "";
        if (args.length >= 6) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--input")) {
                    inputCoolFilePath = args[i + 1];
                }
                if (args[i].equals("--output")) {
                    outputFilePath = args[i + 1];
                }
                if (args[i].equals("--table")) {
                    tablePath = args[i + 1];
                }
            }

            CoolScanner scanner = new CoolScanner(new FileReader(inputCoolFilePath));
            CodeGeneratorImp cg = new CodeGeneratorImp();
            Parser parser = new Parser(scanner, cg, tablePath);
            parseAndWriteOutput(parser, outputFilePath);

        } else {
            System.out.println("Run like bellow:\njava <javaClassFile> --input <inputCoolFilePath> --output <outputFilePath> --table <tablePath>");
            return;
        }
    }
}