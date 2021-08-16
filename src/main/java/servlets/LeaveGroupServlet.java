package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databse.Database;
import objects.Contact;
import objects.User;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Der aktuelle Nutzer wird aus der aktuell offenen Gruppe entfernt.
 */
@WebServlet(name = "LeaveGroupServlet", value = "/servlets/LeaveGroupServlet")
public class LeaveGroupServlet extends HttpServlet {

	/**
	 * Nimmt Post-Anfrage entgegen und entfernt den Benutzer anhand seines Namens
	 * und der RoomId aus der Gruppe
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session und user-objekt einlesen
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		// Aktuelle Gruppe abspeichern
		Contact currentGroup = user.getCurrentContact();

		try {
			// Versuche Nutzer auf DB-Ebene aus Gruppe zu entfernen mittels username und
			// GruppenID
			System.out.println(Database.deleteUserFromGroup(user.getUserName(), user.getCurrentContact().getRoomID()));
			try {
				// Aktuellen Kontakt auf den ersten setzen
				user.setCurrentContact(user.getContacts().get(0));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			// Liste mit kontakten einlesen
			List<Contact> contacts = user.getContacts();

			// Gruppe aus aktuellen Kontakten entfernen
			contacts.remove(currentGroup);

			// Kontakte updaten
			user.updateContacts();
			resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
		} catch (Exception e) {
			// Exception bei Gruppe verlassen
			System.out.println(e.getMessage());
			resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
		}
	}

}
