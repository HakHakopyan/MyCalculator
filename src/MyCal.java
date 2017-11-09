import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyCal {
    public static void main(String[] arg) {
        //Expression:
        try {

            //String expression = "3*+1";
            String expression = getExpression();


            System.out.println("Expression:");
            System.out.println(expression);

            Calculator calculator = new Calculator();

            System.out.println("Result: " + calculator.getResult(expression));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static   String getExpression() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Выражение может содержать целые и вещественные числа, скобки и следующие опретаоры: +-*/^");
        System.out.println("Введите выражение:");

        return  br.readLine();
    }
}
