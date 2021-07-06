package servlets;

import databse.Database;
import exceptions.InvalidInputException;
import exceptions.NameNotFoundException;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet (name = "LoginServlet", value = "/servlets/Loginservlet")
public class LoginServlet  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userName = req.getParameter("username");
        String pw = req.getParameter("password");

        User user = null;
        try {

            if(userName.length() > 0 && pw.length() > 0) {
                try{
                    user = Database.getUser(userName, pw);
                    System.out.println("Das PW lautet:" + user.getPassword());
                    session.setAttribute("userpw",user.getPassword());
                    System.out.println(user.toString());
                    System.out.println(user.getBiography());
                    System.out.println(user.getDateOfBirth());
                    session.setAttribute("user", user);
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("loginerror", new InvalidInputException().getMessage());
                    resp.sendRedirect(req.getContextPath() +"/index.jsp");
                    return;
                }


            } else {
                session.setAttribute("loginerror", new InvalidInputException().getMessage());
                resp.sendRedirect(req.getContextPath() +"/index.jsp");
                return;

            }





            resp.sendRedirect(req.getContextPath() +"/chat.jsp");
        } catch (NameNotFoundException e) {
            session.setAttribute("loginerror", new InvalidInputException().getMessage());
            e.printStackTrace();
            e.sendError();
            resp.sendRedirect(req.getContextPath() +"/index.jsp");
        }

    }
}
