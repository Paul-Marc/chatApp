package servlets;


import databse.Database;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;

@WebServlet(name = "UpdatePWServlet", value = "/servlets/UpdatePWServlet")
public class UpdatePWServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        //Auslesen aus Formular
        String old_pw = req.getParameter("old_pw_input");
        String new_pw = req.getParameter("new_pw_input");
        String new_pw_repeat = req.getParameter("new_pw_repeat_input");

        //Überprüfen ob PW und PW-Wiederholung korrekt sind
        if(session.getAttribute("userpw").toString().equals(old_pw)) {

            if(new_pw.equals(new_pw_repeat)) {
                if(new_pw.length() > 3) {
                    //In Datenbank schreiben
                    System.out.println("Neues PW: " + Database.updatePassword(new_pw,user.getUserName()));
                    session.setAttribute("userpw",new_pw);
                } else {
                    System.out.println("Dein Passwort muss mehr als 3 Zeichen haben.");
                }


            } else {
                System.out.println("Passwort-Wiederholung ist falsch.");
            }

        } else {
            System.out.println("Deine Eingabe: " + old_pw);
            System.out.println("Das eigentliche PW: " + session.getAttribute("userpw").toString());
            System.out.println("Error. Überprüfe Deine Eingaben.");
        }




        resp.sendRedirect(req.getContextPath() +"/changepw.jsp");


    }

}
