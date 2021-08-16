package servlets;

import databse.Database;
import exceptions.InvalidInputException;
import exceptions.NameNotFoundException;
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
 * Zuordnung zu Person: Marc Palfner
 * 
 * Zweck: Die Klasse bietet die Funktionalitaet damit sich ein bestehender
 * Nutzer anmelden kann.
 */
@WebServlet(name = "LoginServlet", value = "/servlets/Loginservlet")
public class LoginServlet extends HttpServlet {

	/**
	 * Name: doPost Zweck: Nimmt die Post-Anfrage entgegen und loggt den Nutzer ein
	 * und schreibt ihn in die Session sofern die Nutzerdaten korrekt sind.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session einlesen
		HttpSession session = req.getSession();

		// Nutzer eingaben entgegennehmen
		String userName = req.getParameter("username");
		String pw = req.getParameter("password");

		User user = null;
		try {
			// Schauen ob Felder richtig ausgefuellt wurden
			if (userName.length() > 0 && pw.length() > 0) {
				try {
					// Auf DB-Ebene schauen ob Nutzer gefunden wurde
					user = Database.getUser(userName, pw);

					// Wenn gefunden dann einloggen und zu session hinzufuegen
					session.setAttribute("userpw", user.getPassword());
					session.setAttribute("user", user);
				} catch (Exception e) {
					// Nutzer wurde nicht gefunden bzw. fehlgeschlagene SQL-Abfrage
					e.printStackTrace();
					session.setAttribute("loginerror", new InvalidInputException().getMessage());
					resp.sendRedirect(req.getContextPath() + "/index.jsp");
					return;
				}

			} else {
				// Felder sind leer
				session.setAttribute("loginerror", new InvalidInputException().getMessage());
				resp.sendRedirect(req.getContextPath() + "/index.jsp");
				return;

			}

			// Erfolgreiche Anmeldung => Weiterleiten zu Chats
			resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
		} catch (NameNotFoundException e) {
			// Fehler => vermutlich durch nicht gefundenen Nutzer
			session.setAttribute("loginerror", new InvalidInputException().getMessage());
			e.printStackTrace();
			e.sendError();
			resp.sendRedirect(req.getContextPath() + "/index.jsp");
		}

	}
}
