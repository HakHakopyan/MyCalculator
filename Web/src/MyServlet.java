import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

//@WebServlet(name = "MyServlet", urlPatterns = {"/MyServlet", "/servlet"});

public class MyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter pw = response.getWriter()) {
            CheckExpression ch;
        /*
        try {

            BigDecimal result = calculator.getResult(expression);
        } catch (MyException ex) {
            pw.println(ex.getMyException());
        }
        */
            pw.println("<!DOCTYPE HTML>");
            pw.print("<html> <head> <title> Calculation Result </title> </head> <body>");

            try {
                Calculator calculator = new Calculator();

                String expression = (String) request.getParameter("expression");

                Result result = calculator.getResult(expression);

                pw.print("<p>");
                result.print(pw);
                pw.print("</p");

            } catch (MyException ex) {
                pw.println("<p>" + ex.getMyException() + "</p>");
            } finally {
                pw.print("</body></html>");
            }
        }
    }
}
