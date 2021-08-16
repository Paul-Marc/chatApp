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

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Die Klasse/das Servlet ruft die Seite zum Profilbearbeiten auf und
 * übermittelt Informationen die als Platzhalter dienen werden.
 */
@WebServlet(name = "EditProfileServlet", value = "/servlets/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {

	/**
	 * Name: doGet Zweck: Lädt die Editprofile Seite und behebt Fehler sofern der
	 * Geburtstag noch nicht hinterlegt wurde.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session und Userobjekt bekommen
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		// Zur moeglichen Fehlerbehebung Userinfos ausgeben
		System.out.println("Bio: " + user.getBiography());
		System.out.println("Gender: " + user.getGender());
		System.out.println("Nickname" + user.getNickname());
		System.out.println("Hobbies: " + user.getHobbies());
		System.out.println("Privates Profil: " + user.isPrivateprofile());

		// Wenn Nutzer kein festgelegten Geburtstag hat
		if (user.getDateOfBirth() == null || user.getDateOfBirth().equals("1000-10-10")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			// Geburtstag auf aktuelles Datum setzen
			user.setDateOfBirth(LocalDate.now().format(formatter));
			System.out.println(user.getDateOfBirth());
		}

		// Weiterleiten
		req.setAttribute("user", user);
		resp.sendRedirect(req.getContextPath() + "/editprofile.jsp");

	}
}