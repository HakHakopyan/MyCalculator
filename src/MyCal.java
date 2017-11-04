//import com.sun.org.apache.xpath.internal.operations.String;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

public class MyCal {
    public static void main(String[] arg) {
        //Expression:
        java.lang.String expression = "((3-2)*(1+2^3)-1)/((100-99)*2-3)";

        System.out.println("Expression:");
        System.out.println(expression);

        Calculator calculator = new Calculator();

        System.out.println(calculator.getResult(expression));

    }
}

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
            {",-", ",0-"}
    };

    private Stack<String> setStackOperations(String token) {
        Stack<String> returnStack = new Stack<>();
        returnStack.clear();

        if (isOpenBracket(token)) {
            stackOperations.push(token);
            return returnStack;
        }

        if (isCloseBracket(token)) {
            while (!stackOperations.empty() && !isOpenBracket(stackOperations.lastElement())) {
                returnStack.push(stackOperations.pop());
            }
            stackOperations.pop();
            return getReverse(returnStack);
        }

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


    private void setStackRPN(Stack<String> inputStack) {

        while (!inputStack.empty()) {
            if (checkToken(inputStack.lastElement())) {
                stackRPN.push(inputStack.pop());
            } else
                errorMessage(inputStack.pop());
        }

    }

    private  Stack<String> getReverse(Stack<String> inputStack) {
        Collections.reverse(inputStack);
        return  inputStack;
    }


    private  void  errorMessage(String token) {
        System.out.println("incorrect character has been entered: " + token);
    }

    public   int getResult(String inputEexpression) {
        Stack<String> stackRPN = new Stack<>();

        try {

            stackRPN = parse(inputEexpression);

            showStack("stack in reversed polish notation:", stackRPN);
        }
        catch (ParseException e){
            System.out.println(e.getMessage());
        }

        return  calculate(stackRPN);
    }

    private  boolean checkToken(String token) {
        return isOperator(token)||isNumber(token) ? true : false;
    }


    private boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
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
        if (inputExpression.charAt(0) == '-') {
            inputExpression = "0" + inputExpression;
        }
        return inputExpression;

    }
    // Change input expression to Reverse Polish Notation
    public Stack<String> parse(String expression) throws ParseException {
        // cleaning stacks
        stackOperations.clear();
        stackRPN.clear();

        // make some preparations
        expression = takeOutLitter(expression);
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
    public Integer calculate(Stack<String> stackRPN) {

        Stack<Integer> stack = new Stack<>();
        stack.clear();

        while (!stackRPN.empty()) {
            String token = stackRPN.pop();
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int right = stack.pop();
                stack.push(doOperation(stack.pop(), right, token));
            }
        }
        return  stack.pop();
    }

    // Get the result of the operation between the left and right numbers
    private  int doOperation(int left, int right, String operator) {

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
                left = (int) Math.pow(left, right);
        }
        return  left;
    }

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
