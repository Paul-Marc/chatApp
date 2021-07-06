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
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@WebServlet(name = "UpdateProfileServlet", value = "/servlets/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet{


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        //Auslesen aus Formular
        String userName = user.getUserName();

        System.out.println("Der aktuelle Nutzer heißt: " + userName);
        String privateProfile = req.getParameter("private_profile_checkbox");
        System.out.println(privateProfile);
        boolean privatesProfil = false;
        if (privateProfile == null) {
            privatesProfil = false;
        } else {
            if (privateProfile.equals("private_profile")) {
                privatesProfil = true;
            }
        }

        String userBiography = req.getParameter("user_biography");
        String userGender = req.getParameter("user_gender");
        String userBirthday = req.getParameter("user_date_of_birth");
        Date birthdayDate = Date.valueOf(userBirthday);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todaysDate = LocalDate.now().format(formatter);
        if (userBirthday.equals(todaysDate)) {
            String birthdayIsZero = "1000-10-10";
            birthdayDate = Date.valueOf(String.format(birthdayIsZero, formatter));
        }

        System.out.println("Nutzer hat Gebbi am: " + userBirthday + " & " + birthdayDate.toString());



        //Schreiben in Datenbank
            try {
                System.out.println(Database.updateProfile(userBiography,userGender,userName,privatesProfil, birthdayDate));
                resp.sendRedirect(req.getContextPath() + "/chat.jsp");
            } catch (Exception e) {
                //Hier noch richtige Exception
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/chat.jsp");
            }

    }



}
