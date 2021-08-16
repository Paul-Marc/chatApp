package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
 * Zweck: Ein weiterer Nutzer kann der aktuellen Gruppe hinzugefügt werden
 */
@WebServlet(name = "AddUserToGroupServlet", value = "/servlets/AddUserToGroupServlet")
public class AddUserToGroupServlet extends HttpServlet {

	/**
	 * Nimmt die Post-Anfrage entgegen und fügt sofern valide einen neuen Nutzer der
	 * aktuellen Gruppe hinzu
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session und user-objekt einlesen
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		// Eingabe einlesen
		String nutzer = req.getParameter("newUser");

		// Aktuellen Kontakt laden
		Contact contact = user.getCurrentContact();

		// Schauen ob Eingabe = der aktuelle Benutzer ist
		if (nutzer.equals(user.getUserName())) {

			resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
			return;
		}

		// Schauen ob Eingabe zu kurz bzw. nicht vorhanden ist
		if (nutzer.length() < 1 || nutzer.equals("") || nutzer == null) {

			resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
			return;
		}

		// Schauen ob nutzer ueberhaupt existiert
		boolean doesUserExist = false;
		try {
			ArrayList<String> allUsers = Database.getAllUsers();
			for (String s : allUsers) {
				if (s.equals(nutzer)) {
					// Nutzer existiert
					doesUserExist = true;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// Nutzer in Datenbank schreiben
		if (doesUserExist) {
			try {
				System.out.println(Database.addNewUserToGroup(nutzer, contact.getRoomID()));
				System.out.println("Nutzer " + nutzer + " erfolgreich in Gruppe hinzugefuegt!");
			} catch (Exception e) {
				// Fehler beim hinzufuegen des Nutzers auf DB-Ebene
				System.out.println("Neues Gruppenmitglied konnte nicht hinzugefuegt werden!");
				System.out.println(e.getMessage());
			}
		} else {
			// Der user existiert nicht
			System.out.println("Der User " + nutzer + " konnte nicht gefunden werden");
		}

		resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");

	}

}
