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
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Zuordnung zu Person: Marc Palfner
 * 
 * Zweck: Die Klasse bietet die Funktionalitaet damit sich ein Nutzer
 * registrieren kann.
 */
@WebServlet(name = "RegisterServlet", value = "/servlets/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	/**
	 * Name: doPost Zweck: Nimmt die Post-Anfrage entgegen und schreibt den neuen
	 * User in die DB (Sofern kein Fehler entsteht)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session entgegennehmen
		HttpSession session = req.getSession();

		// Nutzereingaben lesen
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String repeatPassword = req.getParameter("repeatPassword");

		// Schauen ob Nutzername bereits vergeben ist
		boolean usernameAlreadyTaken = false;
		try {
			// Alle Nutzer/Gruppen durchgehen und Namen abgleichen
			ArrayList<String> usernames = Database.getAllUsers();
			ArrayList<String> groupnames = Database.getAllGroupNames();
			for (String name : usernames) {
				if (name.equals(userName)) {
					// Name bereits vergeben
					usernameAlreadyTaken = true;
				}
			}

			for (String s : groupnames) {
				if (s.equals(userName)) {
					// Gruppe mit gleichem Namen => Auch folglich vergeben
					usernameAlreadyTaken = true;
				}
			}
		} catch (SQLException throwables) {
			// Exception durch SQL abfragen
			throwables.printStackTrace();
			req.getSession().setAttribute("registererror", "Fehler beim registrieren.");
			resp.sendRedirect(req.getContextPath() + "/register.jsp");
			return;
		}

		// Wenn passwort-wiederholung passt und Nutzername noch nicht vergeben ist
		if (password.equals(repeatPassword) && !usernameAlreadyTaken) {

			try {
				// Passwort und Username muessen mehr als 3 Zeichen haben
				if (password.length() > 3 && userName.length() > 3) {
					// Test bestanden => Einfuegen in DB
					System.out.println(Database.addUser(userName, password));
					User user = Database.getUser(userName, password);
					session.setAttribute("user", user);
					resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
				} else {
					// Fehlermeldung durch zu kurze Eingaben
					System.out.println("Das Password und der Username muessen laenger als 3 Zeichen sein.");
					req.getSession().setAttribute("registererror",
							"Password und Username muessen laenger als 3 Zeichen sein.");
					resp.sendRedirect(req.getContextPath() + "/register.jsp");
					return;
				}

			} catch (NameNotFoundException e) {
				// Fehler beim in die DB schreiben oder anschlieﬂendem auslesen
				e.printStackTrace();
				e.sendError();
				req.getSession().setAttribute("registererror", "Fehler beim registrieren.");
				resp.sendRedirect(req.getContextPath() + "/register.jsp");
				return;
			}
		} else {

			if (usernameAlreadyTaken) {
				// Fehlermeldung da Nutzername bereits vergeben ist
				System.out.println("Nutzername bereits vergeben. Bitte nutze einen anderen.");
				req.getSession().setAttribute("registererror",
						"Nutzername bereits vergeben. Bitte nutze einen anderen.");
				resp.sendRedirect(req.getContextPath() + "/register.jsp");
				return;
			}

			req.getSession().setAttribute("registererror", "Fehler beim registrieren.");
			resp.sendRedirect(req.getContextPath() + "/register.jsp");
		}
	}

}
