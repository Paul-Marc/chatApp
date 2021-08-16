package servlets;

import exceptions.ContactNotFoundException;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Zuordnung zu Person: Marc Palfner
 * 
 * Zweck: Mit dieser Klasse kann ein bestehender Kontakt/bzw. das Chatfenster
 * geladen werden.
 */
@WebServlet(name = "LoadContactServlet", value = "/servlets/LoadContactServlet")
public class LoadContactServlet extends HttpServlet {

	/**
	 * Name: doPost Zweck: Nimmt die Post-Anfrage entgegen und versucht einen
	 * anderen Kontakt ins Chatfenster zu bekommen
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session und user-objekt entgegennehmen
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		session.setAttribute("addcontacterror", "");

		// Room id einlesen
		int roomID = Integer.parseInt(req.getParameter("roomid"));

		// Roomid zum aktuellen Kontakt machen
		if (!user.setCurrentContact(roomID)) {
			try {
				throw new ContactNotFoundException();
			} catch (ContactNotFoundException e) {
				// Fehler beim neuen Kontakt laden
				e.printStackTrace();
				e.sendError();
			}
		}

		// Weiterleitung
		session.setAttribute("user", user);
		resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");

	}
}
