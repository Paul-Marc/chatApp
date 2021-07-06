package servlets;

import objects.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "EditProfileServlet", value = "/servlets/EditProfileServlet")
public class EditProfileServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();


        User user = (User) session.getAttribute("user");
        System.out.println("Bio: " + user.getBiography());
        System.out.println("Gender: " + user.getGender());
        if (user.getDateOfBirth() == null || user.getDateOfBirth().equals("1000-10-10")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            user.setDateOfBirth(LocalDate.now().format(formatter));
            System.out.println(user.getDateOfBirth());
        }
        System.out.println("Privates Profil: " + user.isPrivateprofile());
        req.setAttribute("user",user);
        resp.sendRedirect(req.getContextPath() +"/editprofile.jsp");



    }
}