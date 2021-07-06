package servlets;


import objects.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "ChangePWServlet", value = "/servlets/ChangePWServlet")
public class ChangePWServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Passwort-Aendern Seite aufgerufen...");

        resp.sendRedirect(req.getContextPath() +"/changepw.jsp");


    }

}
