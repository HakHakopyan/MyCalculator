public class CheckExpression implements  ExpressionComponents {

    public  boolean checkSymbolOperaotrOrNumber(String symbol) {
        return isOperator(symbol)||isNumber(symbol);
    }

    public boolean isNumber(String symbol) {
        try {
            Double.parseDouble(symbol);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isOpenBracket(String symbol) {
        return symbol.equals(OPEN_BRACKET);
    }

    public boolean isCloseBracket(String symbol) {
        return symbol.equals(CLOSE_BRACKET);
    }

    public boolean isOperator(String symbol) {
        return OPERATORS.contains(symbol);
    }

    // return
    public  byte getPrecedence(String symbol) {
        if (symbol.equals("+") || symbol.equals("-")) {
            return 1;
        }
        return 2;
    }

    // Change incorrect symbols to correct ones
    public String changeIncorrectSymbols(String inputExpression) {
        for (String[] rSymbols: REPLACE_SYMBOLS)
            inputExpression = inputExpression.replace(rSymbols[0], rSymbols[1]);
        if (!inputExpression.isEmpty()) {
            if (inputExpression.charAt(0) == '-') {
                inputExpression = "0" + inputExpression;
            }
        }
        return inputExpression;

    }
}
