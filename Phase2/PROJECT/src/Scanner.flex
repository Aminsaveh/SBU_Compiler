package com.compiler;

%%

%class CoolScanner
%public
%unicode
%line
%column
%function nextToken
%type Token

%{
    //public int ICV = 0;
    //public float RCV = 0.0f;
    // public boolean BCV = false;    // may be used in next phase
    //public String SVC = "";
    private StringBuffer string  = new StringBuffer();
    public String stringValue = "";
    private Token token(String token, Type type) {
        return new Token(token, type);
    }
%}

 // base Macros
Letter = [a-zA-Z]
Digit = [0-9]
Underscore = "_"
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]               //  \r\n  indicates end of line   
WhiteSpace = {LineTerminator} | [ \t\f]    //tab is considered as whiteSpace

//Comment Section
MultiLineComment = "/*" [^*] ~"*/" | "/*"~"*/"             
LineComment = "//" {InputCharacter}* {LineTerminator}?   //find all things after "//" expect enter or end of file as comment
Comment = {LineComment} | {MultiLineComment}    


//Identifires
Identifiers = {Letter} ({Underscore} | {Letter} | {Digit}) {0, 30}


Sign = [-]?
// Numbers
DecimalInteger = {Sign}{Digit}+
HexaDecimal ={Sign} "0"("x"|"X")({Digit}| [a-fA-F])+
RealNumber = {Sign}{Digit}+"."{Digit}*
ScientificNotation = ({RealNumber} | {DecimalInteger})("e"|"E")("+"|"-"){Digit}*

// Reserved Keywords
Let = "let"
Void = "void"
Int = "int"
Real = "real"
Bool = "bool"
String = "string"
Static = "static"
Class = "class"
For = "for"
Rof = "rof"
Loop = "loop"
Pool = "pool"
While = "while"
Break = "break"
Continue = "continue"
If = "if"
Fi = "fi"
Else = "else"
Then = "then"
New = "new"
Array = "Array"
Return = "return"
In_String = "in_string"
In_Int = "in_int"
Print = "print"
Len = "len"
True = "true"           //not mentioned but it may be necessary
False = "false"        //not mentioned but it may be necessary
ReservedKeywords = {Let} | {Void} | {Int} | {Real} | {Bool} | {String} | {Static} | {Class} | 
{For} | {Rof} | {Loop} | {Pool} | {While} |{Break} | {Continue} | {If} | {Fi} | {Else} | {Then} |
 {New} | {Array} | {Return}|{In_String}|{In_Int}|{Len}|{Print}| {True}| {False}

// Operators and Punctuations

add = "+"                                 
unaryminus = "-" 
production = "*"                           
division = "/" 
additionassignment = "+="                  
subtractionassignment = "-="
productionassignment = "*="               
divisionassignment = "/="
increment = "++"                           
decrement = "--"
less = "<"                                 
greater = ">"
lessequal = "<="                          
greaterequal = ">="    
notequal = "!="                           
 equal = "=="
assignment = "="  
mod = "%"                          
logicaland = "&&"                         
logicalor = "||"      
bitwiseand = "&"                          
bitwiseor = "|"
stringliteral = "\""
bitwisexor = "^"
not = "!"
dot = "."
colon = ","
semicolon = ";"
openingbraces = "["                       
closingbraces = "]"
openingparenthesis = "("                  
closingparenthesis = ")"
openingcurlybraces = "{"                  
closingcurlybraces = "}"


OperatorsAndPunctuation = {add} | {unaryminus} | {production} |  {division} | {additionassignment} | {subtractionassignment} | 
                    {productionassignment} | {divisionassignment} | {increment} | {decrement} | {less} | {greater} | 
                    {greaterequal} |{lessequal} | {notequal} | {equal} | {assignment} | {mod} | {logicaland} | {logicalor} | {bitwiseand} | 
                    {bitwiseor} | {stringliteral} |{bitwisexor} | {not} | {dot} | {colon} | {semicolon} | {openingbraces} | 
                    {closingbraces} | {openingparenthesis} | {closingparenthesis} | {openingcurlybraces} | {closingcurlybraces}





//Bolean    not exactly mentioned but we think it may necessary for next phase
Boolean = {True}| {False}
// States
%state STRING

%%

<YYINITIAL> {
    {stringliteral} {
        string.append(yytext());
         yybegin(STRING);
    }
    {Comment} {
        return token(yytext(), Type.COMMENT);
    }
    {ReservedKeywords} {
        return token(yytext(), Type.RESERVED_KEYWORD);
    }
    {OperatorsAndPunctuation} {
        return token(yytext(), Type.OPERATORS_AND_PUNCTUATION);
    }
    {Identifiers} {
        return token(yytext(), Type.IDENTIFIERS);
    }
    {DecimalInteger} {
        //ICV = Integer.valueOf(yytext());
        return token(yytext(), Type.INTEGER_NUMBER);
    }
    {RealNumber} {
        //RCV = Float.valueOf(yytext() + "f");
        return token(yytext(), Type.REAL_NUMBER);
    }
    {HexaDecimal} {
        //String absoluteStringValue = yytext().substring(yytext().indexOf("0") + 2);
        //ICV = Integer.parseInt(absoluteStringValue, 16);
        return token(yytext(), Type.HEX);
    }
    {ScientificNotation} {
        //RCV = Float.valueOf(yytext() + "f");
        return token(yytext(), Type.SCIENTIFIC_NOTATION);
    }
    {WhiteSpace} {
        return token(yytext(), Type.WHITESPACE);
    }
    [^] {
        System.err.println("\nScanner Undefined Token Error: Token " + yytext() + " Is Not Defined At "+ "Line " + (yyline + 1) + " With Character Index " + yycolumn + "\n");
        return token(yytext(), Type.UNDEFINED);
    }
}

<STRING> {
   {stringliteral}  {
                        string.append(yytext());
                        stringValue = string.toString();
                        string = new StringBuffer();
                        yybegin(YYINITIAL);
                        return token(stringValue, Type.STRING);
                    }
    [^\r\n\t\"\'\\]+  {string.append(yytext());}
    "\\r"   {string.append("\r");return token(yytext(), Type.ESCAPE_CHAR);}
    "\\n"   { string.append("\n"); return token(yytext(), Type.ESCAPE_CHAR);}
    "\\t"   { string.append("\t");return token(yytext(), Type.ESCAPE_CHAR);}
    "\\\'"  { string.append("'");return token(yytext(), Type.ESCAPE_CHAR);}
    "\\\""  { string.append("\""); return token(yytext(), Type.ESCAPE_CHAR);}
    "\\\\"   {string.append("\\");return token(yytext(), Type.ESCAPE_CHAR);}
}

[^] {
        System.err.println("\nScanner Undefined Token Error: Token " + yytext() + " Is Not Defined At "+ "Line " + (yyline + 1) + " With Character Index " + yycolumn + "\n");
        return token(yytext(), Type.UNDEFINED);
}