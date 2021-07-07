package objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Chat {

    public static void openChat(User user, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        writer.println("<div id=\"content_box\">");
        writer.println("<div class=\"chatBox\">");
        writer.println("<h1>Chatte mit " +user.getCurrentContact().getUserName() +"</h1>");
        writer.println("<table class=\"messageTable\" border=\"1\">");
        if (user.getMessages() != null) {
            for (Message message: user.getMessages()) {
                writer.println("<td class=\"other\">");
                writer.println("<tr> <td class=\"other\">");
                if (!message.getOwner().equals(user.getUserName())) {
                    writer.println(message.getOwner() +": " +message.getMessage());
                }
                writer.println("</td>");
                writer.println("<td class=\"my\">");
                if (message.getOwner().equals(user.getUserName())) {
                    writer.println(message.getOwner() +": " +message.getMessage());
                }
                writer.println("</td>");
            }
        }
        writer.println("</table>");
        writer.println("</div>");
        writer.println("<div class=\"contactBox\">");
        if (user.getContacts() != null) {
            writer.println("<table class=\"contactTable\" border=\"1\">");
            for (Contact contact: user.getContacts()) {
                writer.println("<tr>");
                writer.println("<td>");
                writer.println("<form action=\"servlets/LoadContactServlet\" method=\"post\">");
                writer.println("<input type=\"hidden\" value=\"" +contact.getRoomID() +"\" name=\"roomid\">");
                writer.println("<input type=\"submit\" class=\"contact_button\" value=\"" +contact.getUserName() +"\">");
                writer.println("</td>");
                writer.println("</tr>");
            }
            writer.println("</table>");
        }
        writer.println("</div>");
        writer.println("</div>");
        writer.close();
        try {
            response.sendRedirect(request.getContextPath() +"/chat.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
