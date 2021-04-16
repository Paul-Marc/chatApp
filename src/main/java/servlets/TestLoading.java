package servlets;

import databse.Database;
import objects.Contact;
import objects.Message;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TestLoading", value = "/servlets/TestLoading")
public class TestLoading extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = new User("testUser");
        User udo = new User("udo");

        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message("test100", user));
        messages.add(new Message("test2", udo));
        messages.add(new Message("test3", udo));
        messages.add(new Message("test45", user));
        user.setMessages(messages);

        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(new Contact(udo, "Bin am leben jo!!"));
        user.setContacts(contacts);


        session.setAttribute("user", user);

        resp.sendRedirect(req.getContextPath() +"/chat.jsp");
    }
}
