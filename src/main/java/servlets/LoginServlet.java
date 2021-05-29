package servlets;

import databse.Database;
import exceptions.NameNotFoundException;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet (name = "LoginServlet", value = "/servlets/Loginservlet")
public class LoginServlet  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userName = req.getParameter("username");
        String pw = req.getParameter("password");

        User user = null;
        try {
            user = Database.getUser(userName, pw);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            e.sendError();
        }
        session.setAttribute("user", user);

        resp.sendRedirect(req.getContextPath() +"/chat.jsp");
    }
}
