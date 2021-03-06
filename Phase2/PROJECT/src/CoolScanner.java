// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: Scanner.flex

import java.io.IOException;

// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
public class CoolScanner implements Lexical{

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;
  public static final int STRING = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1, 1
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\u10ff\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\0\1\3\1\4\22\0\1\3"+
    "\1\5\1\6\2\0\1\7\1\10\1\11\2\7\1\12"+
    "\1\13\1\7\1\14\1\15\1\16\1\17\11\20\1\0"+
    "\1\7\1\5\1\21\1\5\2\0\1\22\3\23\1\24"+
    "\1\23\21\25\1\26\2\25\1\7\1\27\2\7\1\30"+
    "\1\0\1\31\1\32\1\33\1\34\1\35\1\36\1\37"+
    "\1\40\1\41\1\25\1\42\1\43\1\25\1\44\1\45"+
    "\1\46\1\25\1\47\1\50\1\51\1\52\1\53\1\54"+
    "\1\26\1\55\1\25\1\7\1\56\1\7\u0182\0";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[512];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\2\2\1\3\1\4\5\3\2\5\17\6"+
    "\1\3\1\7\1\10\1\1\1\0\1\11\1\12\2\0"+
    "\10\6\1\13\15\6\1\14\1\15\1\16\1\17\1\20"+
    "\1\21\1\0\2\11\1\22\1\23\10\6\1\13\13\6"+
    "\1\13\13\6\1\13\10\6\1\13\5\6\1\13\2\6"+
    "\1\13\26\6";

  private static int [] zzUnpackAction() {
    int [] result = new int[144];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\57\0\136\0\136\0\215\0\274\0\136\0\136"+
    "\0\353\0\u011a\0\u0149\0\u0178\0\u01a7\0\u01d6\0\u0205\0\u0234"+
    "\0\u0263\0\u0292\0\u02c1\0\u02f0\0\u031f\0\u034e\0\u037d\0\u03ac"+
    "\0\u03db\0\u040a\0\u0439\0\u0468\0\u0497\0\u04c6\0\u04f5\0\136"+
    "\0\u0524\0\u0553\0\u0582\0\u05b1\0\u05e0\0\u060f\0\u063e\0\u066d"+
    "\0\u069c\0\u06cb\0\u06fa\0\u0729\0\u0758\0\u0787\0\u063e\0\u07b6"+
    "\0\u07e5\0\u0814\0\u0843\0\u0872\0\u08a1\0\u08d0\0\u08ff\0\u092e"+
    "\0\u095d\0\u098c\0\u09bb\0\u09ea\0\136\0\136\0\136\0\136"+
    "\0\136\0\136\0\u0a19\0\136\0\u0a48\0\u0a77\0\u060f\0\u0aa6"+
    "\0\u0ad5\0\u0b04\0\u0b33\0\u0b62\0\u0b91\0\u0bc0\0\u0bef\0\u0aa6"+
    "\0\u0c1e\0\u0c4d\0\u0c7c\0\u0cab\0\u0cda\0\u0d09\0\u0d38\0\u0d67"+
    "\0\u0d96\0\u0dc5\0\u0df4\0\u0dc5\0\u0e23\0\u0e52\0\u0e81\0\u0eb0"+
    "\0\u0edf\0\u0f0e\0\u0f3d\0\u0f6c\0\u0f9b\0\u0fca\0\u0ff9\0\u0ff9"+
    "\0\u1028\0\u1057\0\u1086\0\u10b5\0\u10e4\0\u1113\0\u1142\0\u1171"+
    "\0\u1142\0\u11a0\0\u11cf\0\u11fe\0\u122d\0\u125c\0\u125c\0\u128b"+
    "\0\u12ba\0\u12ba\0\u12e9\0\u1318\0\u1347\0\u1376\0\u13a5\0\u13d4"+
    "\0\u1403\0\u1432\0\u1461\0\u1490\0\u14bf\0\u14ee\0\u151d\0\u154c"+
    "\0\u157b\0\u15aa\0\u15d9\0\u1608\0\u1637\0\u1666\0\u1695\0\136";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[144];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\3\4\1\5\1\6\1\7\1\10\1\11\1\3"+
    "\1\6\1\12\1\13\1\10\1\14\1\15\1\16\1\6"+
    "\1\17\4\20\2\3\1\20\1\21\1\22\1\20\1\23"+
    "\1\24\2\20\1\25\1\20\1\26\1\27\1\20\1\30"+
    "\1\31\1\32\1\33\1\20\1\34\1\35\1\20\1\36"+
    "\1\37\2\3\1\37\1\3\1\37\1\40\2\37\1\3"+
    "\15\37\1\41\27\37\61\0\1\4\75\0\1\10\45\0"+
    "\1\10\61\0\1\10\5\0\1\10\51\0\1\10\2\0"+
    "\1\15\1\16\1\10\47\0\1\42\3\0\1\43\2\0"+
    "\1\10\52\0\1\44\1\0\2\16\3\0\1\45\1\0"+
    "\1\46\6\0\1\45\36\0\1\44\1\0\2\16\3\0"+
    "\1\45\10\0\1\45\40\0\2\47\1\0\5\47\1\0"+
    "\17\47\1\50\6\47\20\0\2\47\1\0\5\47\1\0"+
    "\26\47\20\0\2\47\1\0\5\47\1\0\15\47\1\51"+
    "\1\47\1\52\6\47\20\0\2\47\1\0\5\47\1\0"+
    "\13\47\1\53\1\47\1\54\10\47\20\0\2\47\1\0"+
    "\5\47\1\0\13\47\1\55\12\47\20\0\2\47\1\0"+
    "\5\47\1\0\1\47\1\56\7\47\1\57\3\47\1\60"+
    "\10\47\20\0\2\47\1\0\5\47\1\0\6\47\1\57"+
    "\5\47\1\61\11\47\20\0\2\47\1\0\5\47\1\0"+
    "\5\47\1\62\7\47\1\63\10\47\20\0\2\47\1\0"+
    "\5\47\1\0\5\47\1\64\20\47\20\0\2\47\1\0"+
    "\5\47\1\0\15\47\1\51\1\47\1\65\6\47\20\0"+
    "\2\47\1\0\5\47\1\0\5\47\1\66\7\47\1\67"+
    "\10\47\20\0\2\47\1\0\5\47\1\0\21\47\1\70"+
    "\4\47\20\0\2\47\1\0\5\47\1\0\10\47\1\71"+
    "\6\47\1\72\6\47\20\0\2\47\1\0\5\47\1\0"+
    "\15\47\1\73\10\47\20\0\2\47\1\0\5\47\1\0"+
    "\10\47\1\74\15\47\57\0\1\10\1\37\2\0\1\37"+
    "\1\0\1\37\1\0\2\37\1\0\15\37\1\0\27\37"+
    "\6\0\1\75\2\0\1\76\15\0\1\77\14\0\1\100"+
    "\2\0\1\101\1\0\1\102\5\0\12\42\1\103\44\42"+
    "\2\43\1\104\1\43\1\105\52\43\17\0\2\44\3\0"+
    "\1\45\10\0\1\45\34\0\2\106\61\0\2\107\1\0"+
    "\3\107\4\0\6\107\37\0\2\110\1\0\5\110\1\0"+
    "\26\110\20\0\2\110\1\0\5\110\1\0\17\110\1\111"+
    "\6\110\20\0\2\110\1\0\5\110\1\0\15\110\1\112"+
    "\10\110\20\0\2\110\1\0\5\110\1\0\5\110\1\113"+
    "\20\110\20\0\2\110\1\0\5\110\1\0\1\110\1\114"+
    "\24\110\20\0\2\110\1\0\5\110\1\0\14\110\1\115"+
    "\11\110\20\0\2\110\1\0\5\110\1\0\20\110\1\116"+
    "\5\110\20\0\2\110\1\0\5\110\1\0\13\110\1\117"+
    "\12\110\20\0\2\110\1\0\5\110\1\0\17\110\1\120"+
    "\6\110\20\0\2\110\1\0\5\110\1\0\1\121\20\110"+
    "\1\120\4\110\20\0\2\110\1\0\5\110\1\0\14\110"+
    "\1\120\4\110\1\120\4\110\20\0\2\110\1\0\5\110"+
    "\1\0\15\110\1\122\10\110\20\0\2\110\1\0\5\110"+
    "\1\0\24\110\1\120\1\110\20\0\2\110\1\0\5\110"+
    "\1\0\11\110\1\123\14\110\20\0\2\110\1\0\5\110"+
    "\1\0\1\110\1\112\17\110\1\124\4\110\20\0\2\110"+
    "\1\0\5\110\1\0\6\110\1\120\17\110\20\0\2\110"+
    "\1\0\5\110\1\0\1\110\1\125\15\110\1\126\6\110"+
    "\20\0\2\110\1\0\5\110\1\0\5\110\1\127\20\110"+
    "\20\0\2\110\1\0\5\110\1\0\22\110\1\116\3\110"+
    "\20\0\2\110\1\0\5\110\1\0\11\110\1\130\14\110"+
    "\20\0\2\110\1\0\5\110\1\0\11\110\1\131\14\110"+
    "\1\0\12\42\1\103\3\42\1\104\40\42\2\0\1\104"+
    "\73\0\2\106\55\0\2\132\1\0\5\132\1\0\26\132"+
    "\20\0\2\132\1\0\5\132\1\0\1\132\1\133\24\132"+
    "\20\0\2\132\1\0\5\132\1\0\13\132\1\134\12\132"+
    "\20\0\2\132\1\0\5\132\1\0\1\132\1\135\24\132"+
    "\20\0\2\132\1\0\5\132\1\0\20\132\1\136\5\132"+
    "\20\0\2\132\1\0\5\132\1\0\21\132\1\137\4\132"+
    "\20\0\2\132\1\0\5\132\1\0\5\132\1\134\20\132"+
    "\20\0\2\132\1\0\5\132\1\0\20\132\1\140\5\132"+
    "\20\0\2\132\1\0\5\132\1\0\11\132\1\141\6\132"+
    "\1\142\5\132\20\0\2\132\1\0\5\132\1\0\16\132"+
    "\1\134\7\132\20\0\2\132\1\0\5\132\1\0\14\132"+
    "\1\143\11\132\20\0\2\132\1\0\5\132\1\0\22\132"+
    "\1\144\3\132\20\0\2\132\1\0\5\132\1\0\21\132"+
    "\1\145\4\132\20\0\2\132\1\0\5\132\1\0\11\132"+
    "\1\146\14\132\20\0\2\132\1\0\5\132\1\0\14\132"+
    "\1\134\11\132\20\0\2\132\1\0\5\132\1\0\4\132"+
    "\1\134\21\132\20\0\2\132\1\0\5\132\1\0\13\132"+
    "\1\140\12\132\20\0\2\147\1\0\5\147\1\0\26\147"+
    "\20\0\2\147\1\0\5\147\1\0\25\147\1\150\20\0"+
    "\2\147\1\0\5\147\1\0\12\147\1\150\13\147\20\0"+
    "\2\147\1\0\5\147\1\0\20\147\1\150\5\147\20\0"+
    "\2\147\1\0\5\147\1\0\11\147\1\151\14\147\20\0"+
    "\2\147\1\0\5\147\1\0\5\147\1\150\20\147\20\0"+
    "\2\147\1\0\5\147\1\0\14\147\1\152\11\147\20\0"+
    "\2\147\1\0\5\147\1\0\21\147\1\153\4\147\20\0"+
    "\2\147\1\0\5\147\1\0\21\147\1\150\4\147\20\0"+
    "\2\147\1\0\5\147\1\0\17\147\1\154\6\147\20\0"+
    "\2\147\1\0\5\147\1\0\11\147\1\155\14\147\20\0"+
    "\2\147\1\0\5\147\1\0\14\147\1\156\11\147\20\0"+
    "\2\157\1\0\5\157\1\0\26\157\20\0\2\157\1\0"+
    "\5\157\1\0\14\157\1\160\11\157\20\0\2\157\1\0"+
    "\5\157\1\0\21\157\1\161\4\157\20\0\2\157\1\0"+
    "\5\157\1\0\17\157\1\162\6\157\20\0\2\157\1\0"+
    "\5\157\1\0\14\157\1\161\11\157\20\0\2\157\1\0"+
    "\5\157\1\0\3\157\1\161\22\157\20\0\2\157\1\0"+
    "\5\157\1\0\7\157\1\161\16\157\20\0\2\163\1\0"+
    "\5\163\1\0\26\163\20\0\2\163\1\0\5\163\1\0"+
    "\22\163\1\164\3\163\20\0\2\163\1\0\5\163\1\0"+
    "\11\163\1\165\14\163\20\0\2\166\1\0\5\166\1\0"+
    "\26\166\20\0\2\166\1\0\5\166\1\0\5\166\1\167"+
    "\20\166\20\0\2\166\1\0\5\166\1\0\14\166\1\170"+
    "\11\166\20\0\2\171\1\0\5\171\1\0\26\171\20\0"+
    "\2\171\1\0\5\171\1\0\7\171\1\172\16\171\20\0"+
    "\2\173\1\0\5\173\1\0\26\173\20\0\2\174\1\0"+
    "\5\174\1\0\26\174\20\0\2\175\1\0\5\175\1\0"+
    "\26\175\20\0\2\176\1\0\5\176\1\0\26\176\20\0"+
    "\2\177\1\0\5\177\1\0\26\177\20\0\2\200\1\0"+
    "\5\200\1\0\26\200\20\0\2\201\1\0\5\201\1\0"+
    "\26\201\20\0\2\202\1\0\5\202\1\0\26\202\20\0"+
    "\2\203\1\0\5\203\1\0\26\203\20\0\2\204\1\0"+
    "\5\204\1\0\26\204\20\0\2\205\1\0\5\205\1\0"+
    "\26\205\20\0\2\206\1\0\5\206\1\0\26\206\20\0"+
    "\2\207\1\0\5\207\1\0\26\207\20\0\2\210\1\0"+
    "\5\210\1\0\26\210\20\0\2\211\1\0\5\211\1\0"+
    "\26\211\20\0\2\212\1\0\5\212\1\0\26\212\20\0"+
    "\2\213\1\0\5\213\1\0\26\213\20\0\2\214\1\0"+
    "\5\214\1\0\26\214\20\0\2\215\1\0\5\215\1\0"+
    "\26\215\20\0\2\216\1\0\5\216\1\0\26\216\20\0"+
    "\2\217\1\0\5\217\1\0\26\217\20\0\2\220\1\0"+
    "\5\220\1\0\26\220\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[5828];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\2\11\2\1\2\11\27\1\1\11\1\1\1\0"+
    "\2\1\2\0\26\1\6\11\1\0\1\11\113\1\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[144];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  public int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  @SuppressWarnings("unused")
  private boolean zzEOFDone;

  /* user code: */
    //public int ICV = 0;
    //public float RCV = 0.0f;
    // public boolean BCV = false;    // may be used in next phase
    //public String SVC = "";
    private StringBuffer string  = new StringBuffer();
    public String stringValue = "";
    private Token token(String token, Type type) {
        return new Token(token, type);
    }
    public Token currentSymbol;


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public CoolScanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  public Token nextToken() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { System.err.println("\nScanner Undefined Token Error: Token " + yytext() + " Is Not Defined At "+ "Line " + (yyline + 1) + " With Character Index " + yycolumn + "\n");
        return token(yytext(), Type.UNDEFINED);
            }
            // fall through
          case 20: break;
          case 2:
            { return token(yytext(), Type.WHITESPACE);
            }
            // fall through
          case 21: break;
          case 3:
            { return token(yytext(), Type.OPERATORS_AND_PUNCTUATION);
            }
            // fall through
          case 22: break;
          case 4:
            { string.append(yytext());
         yybegin(STRING);
            }
            // fall through
          case 23: break;
          case 5:
            { //ICV = Integer.valueOf(yytext());
        return token(yytext(), Type.INTEGER_NUMBER);
            }
            // fall through
          case 24: break;
          case 6:
            { return token(yytext(), Type.IDENTIFIERS);
            }
            // fall through
          case 25: break;
          case 7:
            { string.append(yytext());
            }
            // fall through
          case 26: break;
          case 8:
            { string.append(yytext());
                        stringValue = string.toString();
                        string = new StringBuffer();
                        yybegin(YYINITIAL);
                        return token(stringValue, Type.STRING);
            }
            // fall through
          case 27: break;
          case 9:
            { return token(yytext(), Type.COMMENT);
            }
            // fall through
          case 28: break;
          case 10:
            { //RCV = Float.valueOf(yytext() + "f");
        return token(yytext(), Type.REAL_NUMBER);
            }
            // fall through
          case 29: break;
          case 11:
            { return token(yytext(), Type.RESERVED_KEYWORD);
            }
            // fall through
          case 30: break;
          case 12:
            { string.append("\""); return token(yytext(), Type.ESCAPE_CHAR);
            }
            // fall through
          case 31: break;
          case 13:
            { string.append("'");return token(yytext(), Type.ESCAPE_CHAR);
            }
            // fall through
          case 32: break;
          case 14:
            { string.append("\\");return token(yytext(), Type.ESCAPE_CHAR);
            }
            // fall through
          case 33: break;
          case 15:
            { string.append("\n"); return token(yytext(), Type.ESCAPE_CHAR);
            }
            // fall through
          case 34: break;
          case 16:
            { string.append("\r");return token(yytext(), Type.ESCAPE_CHAR);
            }
            // fall through
          case 35: break;
          case 17:
            { string.append("\t");return token(yytext(), Type.ESCAPE_CHAR);
            }
            // fall through
          case 36: break;
          case 18:
            { //RCV = Float.valueOf(yytext() + "f");
        return token(yytext(), Type.SCIENTIFIC_NOTATION);
            }
            // fall through
          case 37: break;
          case 19:
            { //String absoluteStringValue = yytext().substring(yytext().indexOf("0") + 2);
        //ICV = Integer.parseInt(absoluteStringValue, 16);
        return token(yytext(), Type.HEX);
            }
            // fall through
          case 38: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }

  @Override
  public String nextWord() {
      try {
          currentSymbol = nextToken();
          if (currentSymbol == null) {
              return "$";
          }
          while (currentSymbol.getType() == Type.COMMENT || currentSymbol.getType() == Type.WHITESPACE) {
              currentSymbol = nextToken();
              if (currentSymbol == null) {
                  return "$";
              }
          }

          if(currentSymbol.getType() == Type.INTEGER_NUMBER)
            return "int_constant";

          if(currentSymbol.getType() == Type.REAL_NUMBER)
            return "real_constant";

            if(currentSymbol.getType() == Type.STRING)
            return "string_constant";

          if(currentSymbol.getType() == Type.IDENTIFIERS)
            return "id";

          if(currentSymbol.getType() == Type.OPERATORS_AND_PUNCTUATION &&  currentSymbol.getToken().equals(","))
            return "comma";


          return currentSymbol.getToken();
      } catch (IOException e) {
          e.printStackTrace();
          return null;
      }
  }


}
