package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Die Klasse ermoeglicht das Abmelden.
 */
@WebServlet(name = "LogOutServlet", value = "/servlets/LogOutServlet")
public class LogOutServlet extends HttpServlet {

	/**
	 * Name: doGet Zweck: Beendet die aktuelle Session und leitet zur login Seite
	 * weiter.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session einlesen
		HttpSession session = req.getSession();

		// Session beenden
		session.invalidate();

		// Weiterleiten
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
		return;

	}
}
