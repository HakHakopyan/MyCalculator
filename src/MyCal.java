
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
