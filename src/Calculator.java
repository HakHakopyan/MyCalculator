import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Implements the expression conversion to Reverse Polish Notation
 * using method parse, that is assisted methods setStackOperations and setStackRPN
 * And calculate expression in RPN using method calculate that is assisted method doOperation
 * when needed to calculate expression apply to the method getResult
 * @version  2.0
 * @author  Hakop Hakopyan
 */
public class Calculator extends  CheckExpression{
    /** temporary stack that holds operators and brackets */
    private Stack<String> stackOperations = new Stack<String>();
    /** stack for holding expression converted to reversed polish notation */
    private Stack<String> stackRPN = new Stack<String>();

    /**
     * if close bracket then put all operators before first open bracket from this.stackOperations to return stack
     * if operator then check precedence operators in token and last operator in this.StackOperations
     * @param token contain character
     * @return numbers and operators
     * @throws MyEcxeption when expression contains extra or wrong characters
     */
    protected Stack<String> setStackOperations(String token) throws MyEcxeption {
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
            // Delete open bracket in stackOperations
            if (stackOperations.empty())
                throw new MyEcxeption(OPEN_BRACKET, true);
            stackOperations.pop();
            return getInverted(returnStack);
        }

        // if precedence operator in token variable less then last operator in stackOperations, then
        // put last operator in stackOperations to returnStack
        if (isOperator(token)) {
            while (!stackOperations.empty() && isOperator(stackOperations.lastElement())
                    && getPrecedence(token) <= getPrecedence(stackOperations.lastElement())) {
                returnStack.push(stackOperations.pop());
            }
            stackOperations.push(token);
            return  getInverted(returnStack);
        }
        if (isNumber(token)) {
            returnStack.push(token);
        } else
            throw new MyEcxeption("Entered incorrect character: " + token);

        return returnStack;
    }

    /**
     * add to stack this.stackRPN contents of inputStack
     * @param inputStack contains numbers and operators
     * @throws MyEcxeption when inputStack contain bracket
     */
    protected void setStackRPN(Stack<String> inputStack) throws MyEcxeption {

        while (!inputStack.empty()) {
            // if lastElement operator or number, then add it to stackRPN
            if (checkSymbolOperaotrOrNumber(inputStack.lastElement())) {
                this.stackRPN.push(inputStack.pop());
            } else
                throw new MyEcxeption(inputStack.pop(), true);
        }

    }

    /**
     * gets the stack and returns it inverted
     * @param inputStack contains Stack
     * @return inverted Stack
     */
    protected   Stack<String> getInverted(Stack<String> inputStack) {
        Collections.reverse(inputStack);
        return  inputStack;
    }

    /**
     * get result of calculation input expression
     * @param inputEexpression contain expression, which need to calculate
     * @return result of calculation
     * @throws MyEcxeption when inputExpression is empty or generate ParseException
     */
    public BigDecimal getResult(String inputEexpression) throws MyEcxeption {
        Stack<String> stackRPN;

        // make some preparations
        inputEexpression = changeIncorrectSymbols(inputEexpression);

        if (inputEexpression == null || inputEexpression.length() == 0 )
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

    /**
     * Change input expression in infix notation to Reverse Polish Notation
     * @param expression in the infix notation
     * @return expression in the Reverse Polish Notation
     * @throws ParseException when generate ParseException
     * @throws MyEcxeption when exception generate {@link Calculator#setStackOperations(String)} or {@link Calculator#setStackOperations(String)}
     */
    public Stack<String> parse(String expression) throws ParseException, MyEcxeption {
        // cleaning stacks
        stackOperations.clear();
        stackRPN.clear();

        // splitting input string into tokens
        StringTokenizer stringTokenizer = new StringTokenizer(expression,
                OPERATORS + EMPTY_BRACKETS, true);

        // loop for handling each token - shunting-yard algorithm
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            setStackRPN(setStackOperations(token));
        }

        setStackRPN(stackOperations);

        return getInverted(stackRPN);
    }

    /**
     * calculate the result of the expression in the reverse Polish notation
     * @param myStackRPN expression in the Reverse Polish Notation
     * @return result of calculation
     * @throws MyEcxeption when exception generate {@link Calculator#doOperation(double, double, String)}
     */
    //
    public BigDecimal calculate(Stack<String> myStackRPN) throws  MyEcxeption {

        Stack<BigDecimal> stack = new Stack<>();
        stack.clear();

        // read element from myStackRPN
        // if it number then push it in stack
        // if it operator then do operation between numbers in stack
        while (!myStackRPN.empty()) {
            String token = myStackRPN.pop();
            if (isNumber(token)) {
                stack.push(new BigDecimal(token));
            } else {
                BigDecimal right = stack.pop();
                if (stack.empty())
                    throw new  MyEcxeption("surplus operator: " + token);
                stack.push(doOperation(stack.pop(), right, token));
            }
        }
        return  stack.pop();
    }

    /**
     * Get the result of the operation between the left and right numbers
     * @param left the number from the left of the operator
     * @param right the number from the right of the operator
     * @param operator contains operator
     * @return result of operation
     * @throws MyEcxeption when an arithmetic error occurred
     */
    //
    private  BigDecimal doOperation(BigDecimal left, BigDecimal right, String operator) throws  MyEcxeption {
        try {
            switch (operator) {
                case "-":
                    left = left.subtract(right);
                    break;
                case "+":
                    left = left.add(right);
                    break;
                case "*":
                    left = left.multiply(right);
                    break;
                case "/":
                    left = left.divide(right);
                    break;
                case "^":
                    left = left.pow(right.intValue());
            }
        } catch (ArithmeticException ex) {
            throw new MyEcxeption(left, right, operator);
        }
        return  left;
    }

    /**
     * Write in console input stack contents
     * @param prefix contain declaration
     * @param stackRPN contain Stack
     */
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
