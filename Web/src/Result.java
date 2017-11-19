import java.io.PrintWriter;
import java.math.BigDecimal;

public class Result {

    private String expressionInput="";
    private String expressionRPN="";
    BigDecimal result=null;

    public String getExpressionInput() {
        return expressionInput;
    }

    public void setExpressionInput(String expressionInput) {
        this.expressionInput = expressionInput;
    }

    public String getExpressionRPN() {
        return expressionRPN;
    }

    public void setExpressionRPN(String expressionRPN) {
        this.expressionRPN = expressionRPN;
    }

    public BigDecimal getResult() {

        return new BigDecimal(result.toString());
    }

    public void setResult(BigDecimal result) {
        this.result = new BigDecimal(result.toString());
    }

    public  void  print(PrintWriter pw) {
        pw.println("Input expression: " + getExpressionInput() + " ");
        pw.println("Expression in reversed polish notation: " + getExpressionRPN() + " ");
        pw.print("The result of calculating the expression : ");
        BigDecimal result = getResult();

        if (result.remainder(BigDecimal.ONE).movePointRight(result.scale()).abs().intValue() == 0) {
            pw.println(result.toBigInteger());
        } else
            pw.println(result);
    }
}
