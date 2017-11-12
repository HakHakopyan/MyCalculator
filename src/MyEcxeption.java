public class MyEcxeption extends Exception{
    private  String myException;

    public String getMyException() {
        return myException;
    }

    private void setMyExceptionWithCheck(String exception) {
        String finalException = "Error: ";
        switch (exception) {
            case "(":
                finalException += "Redundant opening parenthesis";
                break;
            case ")":
                finalException += "Redundant closing parenthesis";
                break;
        }

        this.myException = finalException;
    }

    private  void setMyException(String exception) {
            this.myException = exception;
    }

    MyEcxeption(String exception, boolean check) {
        if (check)
            setMyExceptionWithCheck(exception);
        else
            setMyException(exception);
    }

    MyEcxeption(String exception) {
        setMyException(exception);
    }
}
