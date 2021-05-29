package servlets;

import databse.Database;
import exceptions.ContactAlreadyExistsException;
import exceptions.ContactNotFoundException;
import exceptions.NameNotFoundException;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddContactServlet", value = "/servlets/AddContactServlet")
public class AddContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String memberName = req.getParameter("userName");
        String[] member = {user.getUserName(), memberName};
        try {
            if (!user.contactAlreadyExists(memberName)) {
                if (!Database.addRoom(member)) {
                    try {
                        throw new NameNotFoundException();
                    } catch (NameNotFoundException e) {
                        e.printStackTrace();
                        e.sendError();
                    }
                } else {
                    user.updateContacts();
                    try {
                        user.setCurrentContact(user.getContact(memberName));
                    } catch (ContactNotFoundException e) {
                        e.printStackTrace();
                        e.sendError();
                    }
                }
            }
        } catch (ContactAlreadyExistsException e) {
            e.printStackTrace();
            e.sendError();
        }
        session.setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() + "/chat.jsp");
    }
}
