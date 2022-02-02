package com.compiler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Main {

    private String inputFile;
    private String outputFile;

    public Main(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        try {
            scan();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scan() throws IOException {
        CoolScanner scanner = new CoolScanner(new FileReader(inputFile));
        File output = new File(this.outputFile);
        FileWriter fileWriter = new FileWriter(output);
        fileWriter.write("<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <title>Compiler Cool Project</title>\n" +
                "    <link rel=\"stylesheet\" href=\"./styles.css\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <link\n" +
                "      href=\"https://fonts.googleapis.com/css?family=Karla\"\n" +
                "      rel=\"stylesheet\"\n" +
                "    />\n" +
                "  </head>\n" +
                "  <body>\n");
        fileWriter.flush();
        Token token = null;
        int previousLine = -1;
        String currentLineHtml = "";
        boolean fakeBoolean = false;
        while (true) {
            if (fakeBoolean)
                break;
            try {
                token = scanner.nextToken();
                if (scanner.yyatEOF()) {
                    currentLineHtml += "</pre></div>\n";
                    fileWriter.write(currentLineHtml);
                    fileWriter.flush();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Type type = token.getType();
            int currentLine = scanner.yyline;
            System.out.println(currentLine);
            if (currentLine > previousLine) {
                if (previousLine != -1) {
                    currentLineHtml += "</pre></div>\n";
                    fileWriter.write(currentLineHtml);
                    fileWriter.flush();
                }
                previousLine = currentLine;
                currentLineHtml = "<div><pre><span class=\"line-number\">" + (currentLine +1) +"</span>";
            }


            System.out.println(token.getType() + "     :     " + token.getToken());
            switch (token.getType()) {
                case WHITESPACE: {
                    currentLineHtml += token.getToken();
                    break;
                }
                case RESERVED_KEYWORD: {
                    currentLineHtml += "<span class=\"reserved-keywords\">" + token.getToken() + "</span>";
                    break;
                }
                case IDENTIFIERS: {
                    currentLineHtml += "<span class=\"identifiers\">" + token.getToken() + "</span>";
                    break;
                }
                case HEX:
                case INTEGER_NUMBER: {
                    currentLineHtml += "<span class=\"integer-numbers\">" + token.getToken() + "</span>";
                    break;
                }
                case REAL_NUMBER:
                case SCIENTIFIC_NOTATION: {
                    currentLineHtml += "<span class=\"real-numbers\">" + token.getToken() + "</span>";
                    break;
                }
                case STRING: {
                    currentLineHtml += "<span class=\"strings\">" + token.getToken() + "</span>";
                    break;
                }
                case ESCAPE_CHAR: {
                    currentLineHtml += "<span class=\"special-characters\">" + token.getToken() + "</span>";
                    break;
                }
                case COMMENT: {
                    currentLineHtml += "<span class=\"comments\">" + token.getToken() + "</span>";
                    break;
                }
                case OPERATORS_AND_PUNCTUATION: {

                    currentLineHtml += "<span class=\"operators-and-punctuations\">" + token.getToken() + "</span>";
                    break;
                }
                case UNDEFINED: {
                    currentLineHtml += "<span class=\"undefined-token\">" + token.getToken() + "</span>";
                    break;
                }

            }
        }
        fileWriter.write("  </body>\n" +
                "</html>");
        fileWriter.flush();
        fileWriter.close();
    }


    public static void main(String[] args) {
        new Main("./src/test.txt", "./src/output.html");
    }
}
