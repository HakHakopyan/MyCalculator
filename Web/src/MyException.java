import java.math.BigDecimal;

/**
 * Our class exception, which our methods throw when find error in expression
 * implements {@link ExpressionComponents}
 * @version  1.0
 * @author  Hakop Hakopyan
 */
public class MyException extends Exception implements  ExpressionComponents{
    private  String myException;

    /**
     * @return type String, return value {@link MyException#myException}
     */
    public String getMyException() {
        return myException;
    }

    /**
     * check exception and found the appropriate error description
     * Added only BRACKETS error description
     * @param exception contains character
     */
    private void setMyExceptionWithCheck(String exception) {
        String finalException = "Error: ";
        switch (exception) {
            case OPEN_BRACKET:
                finalException += "Redundant opening parenthesis";
                break;
            case CLOSE_BRACKET:
                finalException += "Redundant closing parenthesis";
                break;
        }

        setMyException(finalException);
    }

    /**
     * Use when there is a description of the error
     * @param exception contains description of the error
     */
    private  void setMyException(String exception) {
            this.myException = "Error: " + exception;
    }

    /**
     * Use when needed to found correct error description
     * Added only BRACKETS error description
     * @param exception contains character or error description
     * @param check determines search for character the appropriate error or not
     */
    MyException(String exception, boolean check) {
        if (check)
            setMyExceptionWithCheck(exception);
        else
            setMyException(exception);
    }

    /**
     * Use when there is a ready error message
     * @param exception contains description of the error
     */
    MyException(String exception) {
        setMyException(exception);
    }

    /**
     * Use when Error with arithmetic operation error
     * @param leftArg the number from the left of the operator
     * @param rightArg the number from the right of the operator
     * @param operator contains operator
     */
    MyException(BigDecimal leftArg, BigDecimal rightArg, String operator) {
        String finalException = "";
        if (operator == DIVISION) {
            finalException += "Division by zero: ";
        }
        else
            finalException += "Arithmetic error: ";
        finalException += "" + leftArg + operator + rightArg;
        setMyException(finalException);
    }
}
