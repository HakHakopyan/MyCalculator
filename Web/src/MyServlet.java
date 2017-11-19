import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import  javax.servlet.http.*;
import java.io.IOException;

//@WebServlet(name = "MyServlet", urlPatterns = {"/MyServlet", "/servlet"});

public class MyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int i = 1;
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
