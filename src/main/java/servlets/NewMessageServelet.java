package servlets;

import databse.Database;
import objects.Message;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet (name = "NewMessageServelet", value = "/servlets/NewMessageServelet")
public class NewMessageServelet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() +"/chat.jsp");
            return;
        }
        List<Message> messages = user.getMessages();

        if (messages == null) {
            messages = new ArrayList<Message>();
        }

        if (req.getParameter("newMessage") != null) {
            Message newMessage = new Message(req.getParameter("newMessage"), user, System.currentTimeMillis());
            messages.add(newMessage);
            Database.addMessage(newMessage);
        }
        session.setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() +"/chat.jsp");
    }

}
