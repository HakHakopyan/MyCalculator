import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Contains method main
public class MyCal {
    public static void main(String[] arg) {
        //Expression:
        try {

            //String expression = "3*+1";
            String expression = getExpression();


            System.out.println("Expression:");
            System.out.println(expression);

            Calculator calculator = new Calculator();

            double result = calculator.getResult(expression);
            System.out.print("Result: ");

            // when the fractional part is zero then print without it
            if (result % 1 == 0)
                System.out.println((int) result );
            else
                System.out.println(result);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        catch (MyEcxeption myEx) {
            System.out.println((myEx.getMyException()));
        }

    }

    private static   String getExpression() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Выражение может содержать целые и вещественные числа, скобки и следующие опретаоры: +-*/^");
        System.out.println("Введите выражение:");

        return  br.readLine();
    }
}
