package servlets;


import databse.Database;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ChatPartnerProfileServlet", value = "/servlets/ChatPartnerProfileServlet")
public class ChatPartnerProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet geladen..");
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        User chatpartner = null;
        try {
            chatpartner = Database.getUser(user.getCurrentContact().getUserName());
            System.out.println("Der Partner heisst: " + chatpartner.getUserName());
            try {

                if (chatpartner.isPrivateprofile() == true) {
                    //Profil ist privat
                    User privaterChatpartner = new User(chatpartner.getUserName());
                    privaterChatpartner.setBiography("Nicht öffentlich.");
                    privaterChatpartner.setGender("Nicht öffentlich.");
                    privaterChatpartner.setHobbies("Nicht öffentlich.");
                    privaterChatpartner.setDateOfBirth("Nicht öffentlich.");
                    System.out.println("Privates profil wird geladen");
                    req.getSession().setAttribute("chatpartner", privaterChatpartner);


                } else {
                    //Profil ist öffentlich
                    System.out.println("Oeffentliches profil wird geladen");
                    if(chatpartner.getDateOfBirth().equals("1000-10-10")) {
                        chatpartner.setDateOfBirth("Unbekannt");
                    }
                    req.getSession().setAttribute("chatpartner", chatpartner);
                    User partner = (User) session.getAttribute("chatpartner");
                    System.out.println("Bio: " + partner.getBiography());
                }

            } catch (Exception e) {
                System.out.println("Fehler innerhalb 2. try/catch");
                System.out.println(e.toString());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            resp.sendRedirect(req.getContextPath() + "/chat.jsp");
            return;
        }



        resp.sendRedirect(req.getContextPath() + "/chatpartnerprofile.jsp");


    }

}
