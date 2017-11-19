/**
 * Contains all Operators and other components of the expression and characters that need replacing
 * @version  1.0
 * @author  Hakop Hakopyan
 */
public interface ExpressionComponents {
    /** list of available operators */
    String OPERATORS = "+-*/^";
    /** Contain open bracket */
    String OPEN_BRACKET = "(";
    /** Contain close bracket */
    String CLOSE_BRACKET = ")";
    /** Contain representation of an empty expression */
    String EMPTY_BRACKETS = "()";
    /** Contain division operator / */
    String DIVISION = "/";
    /** Contain subtraction operator - */
    char SUBTRACTION = '-';
    /** Contain addition operator + */
    char ADDITION = '+';
    /** Contain scale for division operation + */
    byte SCALE = 25;

    /** expression elements that need to be replaced for correct work with expression */
    String[][] REPLACE_SYMBOLS = {
            {" ", ""},
            {"(-", "(0-"},
            {",", "."},
            {"()", ""}
    };
}
