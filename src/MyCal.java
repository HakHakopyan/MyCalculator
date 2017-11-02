import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

public class MyCal {
    public static void main(String[] arg) {
        //Expression:
        String s = "((3-2)*(1+2)-1)/((100-99)*2-3)";

        System.out.println("Expression:");
        System.out.println(s);

        Cl cl = new Cl();

        try {

            Stack<String> stackRPN = cl.parse(s);

            cl.showStack(stackRPN);

            System.out.println("Result = " + cl.calcul(stackRPN));
        }
        catch (ParseException e){
            System.out.println(e.getMessage());
        }
    }
}

class Cl {
    // list of available operators
    private final String OPERATORS = "+-*/";
    // temporary stack that holds operators and brackets
    private Stack<String> stackOperations = new Stack<String>();
    // stack for holding expression converted to reversed polish notation
    private Stack<String> stackRPN = new Stack<String>();

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
    public Stack<String> parse(String expression) throws ParseException {
        // cleaning stacks
        stackOperations.clear();
        stackRPN.clear();

        // make some preparations
        expression = expression.replace(" ", "").replace("(-", "(0-")
                .replace(",-", ",0-");
        if (expression.charAt(0) == '-') {
            expression = "0" + expression;
        }
        // splitting input string into tokens
        StringTokenizer stringTokenizer = new StringTokenizer(expression,
                OPERATORS + "()", true);

        // loop for handling each token - shunting-yard algorithm
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if (isOpenBracket(token)) {
                stackOperations.push(token);
            } else if (isCloseBracket(token)) {
                while (!stackOperations.empty()
                        && !isOpenBracket(stackOperations.lastElement())) {
                    stackRPN.push(stackOperations.pop());
                }
                stackOperations.pop();
            } else if (isNumber(token)) {
                    stackRPN.push(token);
            } else if (isOperator(token)) {
                while (!stackOperations.empty()
                        && isOperator(stackOperations.lastElement())
                        && getPrecedence(token) <= getPrecedence(stackOperations
                        .lastElement())) {
                    stackRPN.push(stackOperations.pop());
                }
                stackOperations.push(token);
            }
        }
        while (!stackOperations.empty()) {
            stackRPN.push(stackOperations.pop());
        }

        // reverse stack
        Collections.reverse(stackRPN);

        return stackRPN;
    }

    public Integer calcul(Stack<String> stackRPN) {

        Stack<Integer> stack = new Stack<>();
        stack.clear();
        int left = 0, right = 0;

        while (!stackRPN.empty()) {
            String token = stackRPN.pop();
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                right = stack.pop();
                left = stack.pop();
                switch (token) {
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
                }
                stack.push(left);
            }
        }
        return  stack.pop();
    }

    public void showStack(Stack<String> stackRPN){
        Stack<String> stack = new Stack<>();
        stack.addAll(stackRPN);
        System.out.println("stack in reversed polish notation:");
        while (!stack.empty()) {
            System.out.print(stack.pop());
        }
        System.out.println();
    }
}
