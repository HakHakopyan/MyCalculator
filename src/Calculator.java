import java.text.ParseException;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

class Calculator {
    // list of available operators
    private final String OPERATORS = "+-*/^";
    // temporary stack that holds operators and brackets
    private Stack<String> stackOperations = new Stack<String>();
    // stack for holding expression converted to reversed polish notation
    private Stack<String> stackRPN = new Stack<String>();
    //
    private final String[][] replaceSymbols  = {
            {" ",""},
            {"(-", "(0-"},
            {",", "."},
            {"()", ""}
    };

    //
    private Stack<String> setStackOperations(String token) {
        Stack<String> returnStack = new Stack<>();
        returnStack.clear();

        // Always put "(" in stackOperations
        if (isOpenBracket(token)) {
            stackOperations.push(token);
            return returnStack;
        }

        // if ")" then add all elements before first "(" from stackOperations to returnStack
        if (isCloseBracket(token)) {
            while (!stackOperations.empty() && !isOpenBracket(stackOperations.lastElement())) {
                returnStack.push(stackOperations.pop());
            }
            // Delete "(" in stackOperations
            stackOperations.pop();
            return getReverse(returnStack);
        }

        // if precedence operator in token variable less then last operator in stackOperations, then
        // put last operator in stackOperations to returnStack
        if (isOperator(token)) {
            while (!stackOperations.empty() && isOperator(stackOperations.lastElement())
                    && getPrecedence(token) <= getPrecedence(stackOperations.lastElement())) {
                returnStack.push(stackOperations.pop());
            }
            stackOperations.push(token);
            return  getReverse(returnStack);
        }

        returnStack.push(token);

        return returnStack;
    }

    // add to stack this.stackRPN contents of inputStack
    private void setStackRPN(Stack<String> inputStack) throws MyEcxeption {

        while (!inputStack.empty()) {
            // if lastElement operator or number, then add it to stackRPN
            if (checkToken(inputStack.lastElement())) {
                this.stackRPN.push(inputStack.pop());
            } else
                throw new MyEcxeption(inputStack.pop(), true);
        }

    }

    private  Stack<String> getReverse(Stack<String> inputStack) {
        Collections.reverse(inputStack);
        return  inputStack;
    }


    private  void showErrorMessage(String character) {
        System.out.println("incorrect character has been entered: " + character);
    }

    // get result of calculation input expression
    public   double getResult(String inputEexpression) throws MyEcxeption {
        Stack<String> stackRPN;

        // make some preparations
        inputEexpression = takeOutLitter(inputEexpression);

        if (inputEexpression.length() == 0)
            throw new MyEcxeption("the entered expression is empty");

        try {

            stackRPN = parse(inputEexpression);

            showStack("stack in reversed polish notation:", stackRPN);
        }
        catch (ParseException e){
            throw new MyEcxeption(e.getMessage(), false);
        }

        return  calculate(stackRPN);
    }

    private  boolean checkToken(String token) {
        return isOperator(token)||isNumber(token);
    }


    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isOpenBracket(String token) {
        return token.equals("(");
    }

    private boolean isCloseBracket(String token) {
        return token.equals(")");
    }

    private boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }

    private byte getPrecedence(String token) {
        if (token.equals("+") || token.equals("-")) {
            return 1;
        }
        return 2;
    }

    // Change incorrect symbols to correct ones
    private String takeOutLitter(String inputExpression) {
        for (String[] rSymbols: replaceSymbols)
            inputExpression = inputExpression.replace(rSymbols[0], rSymbols[1]);
        if (!inputExpression.isEmpty()) {
            if (inputExpression.charAt(0) == '-') {
                inputExpression = "0" + inputExpression;
            }
        }
        return inputExpression;

    }
    // Change input expression to Reverse Polish Notation
    public Stack<String> parse(String expression) throws ParseException, MyEcxeption {
        // cleaning stacks
        stackOperations.clear();
        stackRPN.clear();

        // splitting input string into tokens
        StringTokenizer stringTokenizer = new StringTokenizer(expression,
                OPERATORS + "()", true);

        // loop for handling each token - shunting-yard algorithm
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            setStackRPN(setStackOperations(token));
        }

        setStackRPN(stackOperations);

        return getReverse(stackRPN);
    }

    // calculate the result of the expression in the reverse Polish notation
    public double calculate(Stack<String> myStackRPN) {

        Stack<Double> stack = new Stack<>();
        stack.clear();

        while (!myStackRPN.empty()) {
            String token = myStackRPN.pop();
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double right = stack.pop();
                stack.push(doOperation(stack.pop(), right, token));
            }
        }
        return  stack.pop();
    }

    // Get the result of the operation between the left and right numbers
    private  double doOperation(double left, double right, String operator) {

        switch (operator) {
            case "-":
                left -= right;
                break;
            case "+":
                left += right;
                break;
            case "*":
                left *= right;
                break;
            case "/":
                    left /= right;
                break;
            case "^":
                left = Math.pow(left, right);
        }
        return  left;
    }

    // Write in console input stack contents
    public void showStack(String prefix, Stack<String> stackRPN){
        Stack<String> stack = new Stack<>();
        stack.addAll(stackRPN);
        System.out.println(prefix);
        while (!stack.empty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();
    }
}
