package servlets;

import databse.Database;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Die Klasse bietet die Funktionalitaet damit ein Nutzer sein Passwort
 * aendern kann.
 */
@WebServlet(name = "UpdatePWServlet", value = "/servlets/UpdatePWServlet")
public class UpdatePWServlet extends HttpServlet {

	/**
	 * Name: doPost Zweck: Nimmt die Post-Anfrage entgegen
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session und User-Objekt einlesen
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		try {

			// Auslesen aus Formular
			String old_pw = req.getParameter("old_pw_input");
			String new_pw = req.getParameter("new_pw_input");
			String new_pw_repeat = req.getParameter("new_pw_repeat_input");

			// Ueberpruefe ob PW und PW-Wiederholung korrekt sind
			if (session.getAttribute("userpw").toString().equals(old_pw)) {

				if (new_pw.equals(new_pw_repeat)) {
					if (new_pw.length() > 3) {
						// In Datenbank schreiben
						System.out.println("Neues PW: " + Database.updatePassword(new_pw, user.getUserName()));
						session.setAttribute("userpw", new_pw);
						req.getSession().setAttribute("newpwsuccess", "Neues Passwort erfolgreich erstellt.");
						resp.sendRedirect(req.getContextPath() + "/changepw.jsp");
						return;
					} else {
						// Passwort ist zu kurz
						System.out.println("Dein Passwort muss mehr als 3 Zeichen haben.");
						req.getSession().setAttribute("newpwerror",
								"Dein neues Passwort muss mehr als 3 Zeichen haben.");
						resp.sendRedirect(req.getContextPath() + "/changepw.jsp");
						return;
					}

				} else {
					// Die passwort wiederholung stimmt nicht
					System.out.println("Passwort-Wiederholung ist falsch.");
					req.getSession().setAttribute("newpwerror", "Wiederholung des Passworts ist falsch.");
					resp.sendRedirect(req.getContextPath() + "/changepw.jsp");
					return;
				}

			} else {
				// Altes Password ist falsch
				req.getSession().setAttribute("newpwerror", "Dein altes Passwort stimmt nicht.");
				resp.sendRedirect(req.getContextPath() + "/changepw.jsp");
				return;
			}

		} catch (Exception e) {
			// Exception => Fehler beim Passwort aendern aufgetreten
			req.getSession().setAttribute("newpwerror", "Fehler beim Passwort aendern.");
			resp.sendRedirect(req.getContextPath() + "/changepw.jsp");
			return;
		}

	}

}
