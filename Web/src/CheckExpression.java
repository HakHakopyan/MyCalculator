import java.math.BigDecimal;

/**
 * Contains all the necessary methods for working with the elements of the expression:
 * replacement, finding of conformity (определение соответсвия)
 * @version  1.0
 * @author Hakop Hakopyan
 */
public class CheckExpression implements  ExpressionComponents {
    /**
     * checks if the character is a number
     * @param symbol contain character, which need to check
     * @return (type boolean) true if character is number, false when not
     */

    public boolean isNumber(String symbol) {
        try {
            new BigDecimal(symbol);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * checks if the character is a open bracket
     * @param symbol contain character, which need to check
     * @return true if character is open bracket, false when not
     */
    public boolean isOpenBracket(String symbol) {
        return symbol.equals(OPEN_BRACKET);
    }

    /**
     * checks if the character is a close bracket
     * @param symbol contain character, which need to check
     * @return (type boolean) true if character is close bracket, false when not
     */
    public boolean isCloseBracket(String symbol) {
        return symbol.equals(CLOSE_BRACKET);
    }

    /**
     * checks if the character is a operator
     * @param symbol contain character, which need to check
     * @return true if character is operator, false when not
     */
    public boolean isOperator(String symbol) {
        return OPERATORS.contains(symbol);
    }

    /**
     * checks if the character is a operator or number
     * @param symbol contain character, which need to check
     * @return true if character is operator or number, false when not
     */
    public  boolean checkSymbolOperaotrOrNumber(String symbol) {
        return isOperator(symbol)||isNumber(symbol);
    }

    /**
     * Get operator precedence
     * + and - have precedence 1
     * *, / and ^ - have precedence 2
     * @param operator contain operator for wich to need return precedence, which need to check
     * @return true if character is operator, false when not
     */
    public  byte getPrecedence(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        }
        return 2;
    }

    /**
     *
     * @param inputExpression contain expression in which to need change incorrect characters to correct ones
     * @return expression with changed characters
     */
    public String changeIncorrectSymbols(String inputExpression) {
        for (String[] rSymbols: REPLACE_SYMBOLS)
            inputExpression = inputExpression.replace(rSymbols[0], rSymbols[1]);
        if (!inputExpression.isEmpty()) {
            if (inputExpression.charAt(0) == SUBTRACTION || inputExpression.charAt(0) == ADDITION) {
                inputExpression = "0" + inputExpression;
            }
        }
        return inputExpression;

    }
}
