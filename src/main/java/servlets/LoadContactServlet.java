package servlets;

import exceptions.ContactNotFoundException;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoadContactServlet", value = "/servlets/LoadContactServlet")
public class LoadContactServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        int roomID = Integer.parseInt(req.getParameter("roomid"));
        if (!user.setCurrentContact(roomID)) {
            try {
                throw new ContactNotFoundException();
            } catch (ContactNotFoundException e) {
                e.printStackTrace();
                e.sendError();
            }
        }

        session.setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() + "/chat.jsp");

    }
}
