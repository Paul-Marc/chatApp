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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "RegisterServlet", value = "/servlets/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");

        if (password.equals(repeatPassword)) {
            System.out.println(Database.addUser(userName, password));
            try {
                User user = Database.getUser(userName, password);
                session.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/chat.jsp");
            } catch (NameNotFoundException e) {
                e.printStackTrace();
                e.sendError();
                resp.sendRedirect(req.getContextPath() + "/register.jsp");
            }
        }
    }


}
