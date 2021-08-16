package servlets;

import databse.Database;
import objects.Message;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Zuordnung zu Person: Marc Palfner
 * 
 * Zweck: Die Klasse/Das Servlet ermoeglicht das absenden einer neuen Nachricht
 * im Chatfenster.
 */
@WebServlet(name = "NewMessageServelet", value = "/servlets/NewMessageServelet")
public class NewMessageServelet extends HttpServlet {

	/**
	 * Name: doPost Zweck: Nimmt die Post-Anfrage entgegen und schreibt die neue
	 * Nachricht in die DB und ins Chatfenster
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session und user-objekt einlesen
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		try {
			// Schauen ob user = null ist
			if (user == null) {
				resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
				return;
			}
			// Auf leere Nachricht testen
			if (req.getParameter("newMessage").length() == 0 || req.getParameter("newMessage").equals("")
					|| req.getParameter("newMessage") == null) {
				resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
				return;
			}
			// Liste mit allen Nachrichten des Nutzers
			List<Message> messages = user.getMessages();

			// Wenn noch keine Eintraege existieren => neues Objekt als ArrayList<Message>
			if (messages == null) {
				messages = new ArrayList<Message>();
			}

			if (req.getParameter("newMessage") != null) {
				// Neue Nachricht erzeugen
				Message newMessage = new Message(user.getCurrentContact().getRoomID(), req.getParameter("newMessage"),
						user.getUserName(), new Timestamp(System.currentTimeMillis()));
				// Schreiben in DB und ins messages-objekt
				messages.add(newMessage);
				Database.addMessage(newMessage);
			}
		} catch (Exception e) {
			// Fehler beim erzeugen der Nachricht
			System.out.println(e.getMessage());
			resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
			return;
		}
		session.setAttribute("user", user);
		resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
	}

}
