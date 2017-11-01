import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

public class MyCal {
    public static void main(String[] arg) {
        String s = "(1+1)/2*(3-2)";

        Cl cl = new Cl();

        try {
            cl.parse(s);
        }
        catch (ParseException ps){

        }
        cl.showStack();
        System.out.println(cl.calcul());

        //System.out.println(1);
    }
}

class Cl {
    // list of available operators
    private final String OPERATORS = "+-*/";
    // temporary stack that holds operators, functions and brackets
    private Stack<String> stackOperations = new Stack<String>();
    // stack for holding expression converted to reversed polish notation
    private Stack<String> stackRPN = new Stack<String>();
    // stack for holding the calculations result
    private Stack<String> stackAnswer = new Stack<String>();

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
    public void parse(String expression) throws ParseException {
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
    }

    public Integer calcul() {
        int left = 0, right = 0, res = 0;

        while (!stackRPN.empty()) {
            String token = stackRPN.pop();
            if(isNumber(token)) {
                if(!stackRPN.empty() && isNumber(stackRPN.peek())) {
                    res += left;
                    left = Integer.parseInt(token);
                }
                else {
                        //left = res;
                        right = Integer.parseInt(token);
                    }
            } else switch (token) {
                case "-":
                    left -= right;
                    break;
                case "+":
                    left += right;
                    break;
                case "*":
                    left *= right;
                    break;
                case  "/":
                    left /= right;
                    break;
            }
        }
        return left;
    }

    public void showStack(){
        Stack<String> stack = new Stack<>();
        stack.addAll(stackRPN);

        while (!stack.empty()) {
            System.out.println(stack.pop());
        }
    }
}
