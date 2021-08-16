package servlets;

import databse.Database;
import exceptions.EditProfileException;
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

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Die Klasse bietet die Funktionalitaet damit ein Nutzer seine
 * Profildaten wie Nickname, Biographie, Geschlecht, Hobbies, Geburtsdatum und
 * die Sichtbarkeit seines Profils aendern kann.
 */
@WebServlet(name = "UpdateProfileServlet", value = "/servlets/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {

	/**
	 * Name: doPost Zweck: Nimmt die Post-Anfrage entgegen und schreibt sofern kein
	 * Fehler entsteht die neuen Profildaten in die DB
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session und user-objekt einlesen
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		// Auslesen aus Formular
		String userName = user.getUserName();

		// Auf privates Profil testen
		String privateProfile = req.getParameter("private_profile_checkbox");
		boolean privatesProfil = false;
		if (privateProfile == null) {
			privatesProfil = false;
		} else {
			if (privateProfile.equals("private_profile")) {
				privatesProfil = true;
			}
		}

		// Weitere Daten einlesen
		String userNickname = req.getParameter("user_nickname");
		String userBiography = req.getParameter("user_biography");
		String userHobbies = req.getParameter("user_hobbies");
		String userGender = req.getParameter("user_gender");
		String userBirthday = req.getParameter("user_date_of_birth");
		Date birthdayDate = Date.valueOf(userBirthday);

		// Geburtstag formatieren und abarbeiten
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String todaysDate = LocalDate.now().format(formatter);
		// Schauen ob nutzer ueberhaupt ein Geburtsdatum eingegeben hat oder ob heutiger
		// Tag = Datum
		if (userBirthday.equals(todaysDate)) {
			// Wenn kein Geburtstag dann Geburtstag = 1000-10-10
			String birthdayIsZero = "1000-10-10";
			birthdayDate = Date.valueOf(String.format(birthdayIsZero, formatter));
		}

		// Schreiben in Datenbank
		try {
			System.out.println(Database.updateProfile(userBiography, userGender, userName, privatesProfil, birthdayDate,
					userNickname, userHobbies));
			resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
		} catch (Exception e) {
			// Schreib in DB nicht erfolgreich
			e.printStackTrace();
			req.getSession().setAttribute("editprofileerror", new EditProfileException().getMessage());
			resp.sendRedirect(req.getContextPath() + "/editprofile.jsp");
		}

	}

}
