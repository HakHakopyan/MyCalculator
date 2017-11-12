public class MyEcxeption extends Exception implements  ExpressionComponents{
    private  String myException;

    public String getMyException() {
        return myException;
    }

    // check exception and found the appropriate error description
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

        setMyException(exception);
    }

    // Use when there is a description of the error
    private  void setMyException(String exception) {
            this.myException = "Error: " + exception;
    }

    // Use when needed to found correct error description
    // Added only BRACKETS error description
    MyEcxeption(String exception, boolean check) {
        if (check)
            setMyExceptionWithCheck(exception);
        else
            setMyException(exception);
    }

    MyEcxeption(String exception) {
        setMyException(exception);
    }

    // Use when Error with arithmetic operation
    MyEcxeption(double leftArg, double rightArg, String operator) {
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
