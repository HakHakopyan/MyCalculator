// Contains all Operators and other components of the expression and characters that need replacing
public interface ExpressionComponents {
    // list of available operators
    String OPERATORS = "+-*/^";

    String OPEN_BRACKET = "(";
    String CLOSE_BRACKET = ")";

    String DIVISION = "/";

    // expression elements that need to be replaced for correct work with expression
    String[][] REPLACE_SYMBOLS = {
            {" ", ""},
            {"(-", "(0-"},
            {",", "."},
            {"()", ""}
    };
}
