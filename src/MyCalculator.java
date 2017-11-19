import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * contains static method main
 * @version 2.0
 * @author  Hakop Hakopyan
 */
public class MyCalculator {
    /**
     * work with console
     * read expression from console and write messages for user
     * use class {@link Calculator} and method {@link Calculator#getResult(String)}
     * @param args we not used
     */
    public  static  void  main(String[] args) {
        try {
            Calculator calculator = new Calculator();

            System.out.println("Выражение может содержать вещественные числа, скобки и следующие операторы: + - * / ^");
            System.out.println("Введите выражение и нажмите Enter:");

            String expression = readExpressionFromConsole();

            BigDecimal result = calculator.getResult(expression);
            //result = result.stripTrailingZeros(); if 10 then result=1E+1 - problems))

            System.out.print("Результат подсчета выражения: ");
            System.out.println(result);

            if (result.remainder(BigDecimal.ONE).movePointRight(result.scale()).abs().intValue() == 0) {
                System.out.println(result.toBigInteger());
            } else
                System.out.println(result);

        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        } catch (MyException ex) {
            System.out.println(ex.getMyException());
        }
    }

    /**
     *  read expression from the console and return it
     * @return type String, expression entered by the user
     * @throws IOException when problems with console
     */
    public  static  String readExpressionFromConsole() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        return  br.readLine();
    }
}
