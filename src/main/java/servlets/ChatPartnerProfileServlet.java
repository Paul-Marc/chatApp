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

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Die Klasse ermoeglicht das Einsehen des Profils des aktuellen
 * Chatpartners. Im Servlet werden die nötigen Chatpartner-Daten vorbereitet.
 */
@WebServlet(name = "ChatPartnerProfileServlet", value = "/servlets/ChatPartnerProfileServlet")
public class ChatPartnerProfileServlet extends HttpServlet {

	/**
	 * Name: doGet Zweck: Bereitet das Profil des Chatpartners vor und schaut ob
	 * dessen Profil öffentlich oder privat ist.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session und user-objekt einlesen
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		User chatpartner = null;
		try {
			// Profil des aktuellen Chatpartners bekommen
			chatpartner = Database.getUser(user.getCurrentContact().getUserName());
			try {

				// Schauen ob Profil des Chatpartners privat ist
				if (chatpartner.isPrivateprofile() == true) {
					// Profil ist privat => Seine Daten durch "Nicht Oeffentlich" ersetzen
					User privaterChatpartner = new User(chatpartner.getUserName());
					privaterChatpartner.setBiography("Nicht Oeffentlich.");
					privaterChatpartner.setGender("Nicht Oeffentlich.");
					privaterChatpartner.setHobbies("Nicht Oeffentlich.");
					privaterChatpartner.setNickname("Nicht Oeffentlich.");
					privaterChatpartner.setDateOfBirth("Nicht Oeffentlich.");

					req.getSession().setAttribute("chatpartner", privaterChatpartner);

				} else {
					// Profil ist oeffentlich
					System.out.println("Oeffentliches profil wird geladen");

					// Wenn Geburtstag = 1000-10-10 => kein festgelegtes Datum
					if (chatpartner.getDateOfBirth().equals("1000-10-10")) {
						chatpartner.setDateOfBirth("Unbekannt");
					}
					req.getSession().setAttribute("chatpartner", chatpartner);

					// Fuer testzwecke
					User partner = (User) session.getAttribute("chatpartner");
					System.out.println("Bio: " + partner.getBiography());
				}

			} catch (Exception e) {
				// Fehler beim erstellen des Profils des Chatpartners
				System.out.println(e.toString());
			}
		} catch (Exception e) {
			// Fehler bei Chatpartner aus DB auslesen
			System.out.println(e.toString());
			resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
			return;
		}

		resp.sendRedirect(req.getContextPath() + "/chatpartnerprofile.jsp");

	}

}
