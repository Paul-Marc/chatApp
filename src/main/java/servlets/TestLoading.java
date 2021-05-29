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


@WebServlet(name = "TestLoading", value = "/servlets/TestLoading")
public class TestLoading extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = null;
        try {
            user = Database.getUser("user", "123");
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            e.sendError();
        }
        session.setAttribute("user", user);

        resp.sendRedirect(req.getContextPath() +"/index.jsp");
    }
}
