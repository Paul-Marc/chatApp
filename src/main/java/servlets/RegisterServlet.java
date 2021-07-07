package servlets;

import databse.Database;
import exceptions.NameNotFoundException;
import objects.Chat;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "RegisterServlet", value = "/servlets/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");

        boolean usernameAlreadyTaken = false;

        try {
            ArrayList<String> usernames = Database.getAllUsers();
            for (String name : usernames) {
                if (name.equals(userName)) {
                    usernameAlreadyTaken = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (password.equals(repeatPassword) && !usernameAlreadyTaken) {

            try {
                if (password.length() > 3 && userName.length() > 3) {
                    System.out.println(Database.addUser(userName, password));
                    User user = Database.getUser(userName, password);
                    session.setAttribute("user", user);
                    Chat.openChat(user, req, resp);
                } else {
                    System.out.println("Das Password und der Username muessen laenger als 3 Zeichen sein.");
                    resp.sendRedirect(req.getContextPath() + "/register.jsp");
                    return;
                }

            } catch (NameNotFoundException e) {
                e.printStackTrace();
                e.sendError();
                resp.sendRedirect(req.getContextPath() + "/register.jsp");
            }
        } else {
            //Exception noch ausdenken
            if (usernameAlreadyTaken) {
                System.out.println("Nutzername bereits vergeben. Bitte nutze einen anderen.");
            }
            resp.sendRedirect(req.getContextPath() + "/register.jsp");
        }
    }


}
