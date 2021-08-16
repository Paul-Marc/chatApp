package servlets;

import databse.Database;
import exceptions.CantChatWithYourSelfException;
import exceptions.ContactAlreadyExistsException;
import exceptions.ContactNotFoundException;
import exceptions.NameNotFoundException;
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
 * Zweck: Die Klasse ermoeglicht das hinzufuegen eines neuen Kontakts.
 */
@WebServlet(name = "AddContactServlet", value = "/servlets/AddContactServlet")
public class AddContactServlet extends HttpServlet {

	/**
	 * Name: doGet
	 * Zweck: Überprüft die Nutzereingabe und fügt bei korrekten Eingaben den neuen Kontakt hinzu.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//Session und user-objekt einlesen
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		boolean error = false;
		
		//Username einlesen
		String memberName = req.getParameter("userName");
		//Testen ob user sich selbst als Kontakt hinzufuegen will
		if (user.getUserName().equals(memberName)) {
			try {
				throw new CantChatWithYourSelfException();
			} catch (CantChatWithYourSelfException e) {
				//Fehler ausgeben
				e.printStackTrace();
				e.sendError();
				error = true;
				req.getSession().setAttribute("addcontacterror", "Du kannst dich nicht selbst adden.");
				resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
				return;
			}
		}

		String[] member = { user.getUserName(), memberName };
		//Schauen ob kontakt bereits abgespeichert ist
		if (!user.contactAlreadyExists(memberName)) {
			//Neue Chatgruppe erstellen
			if (!Database.addRoom(member)) {
				try {
					//Fehler bei erstellen der Chatgruppe
					throw new NameNotFoundException();
				} catch (NameNotFoundException e) {
					e.printStackTrace();
					e.sendError();
					error = true;
				}
			} else {
				//Kontakte updaten
				user.updateContacts();
				try {
					//Aktuellen Kontakt auf neuen Kontakt setzen
					user.setCurrentContact(user.getContact(memberName));
				} catch (ContactNotFoundException e) {
					//Fehler bei aktuellen Kontakt auf neuen Kontakt setzen
					e.printStackTrace();
					e.sendError();
					error = true;
				}
			}
		} else {
			try {
				//Fehler weil Kontakt existiert bereits
				throw new ContactAlreadyExistsException();
			} catch (ContactAlreadyExistsException e) {
				e.printStackTrace();
				e.sendError();
				error = true;
			}
		}
		
		if(error) {
			//Fehler wird ausgegeben
			System.out.println("Error. Kontakt konnten icht hinzugefuegt werden!");
			req.getSession().setAttribute("addcontacterror", "Kontakt konnte nicht hinzugefuegt werden.");
		}
		
		//Weiterleitung unabhaengig von Erfolg oder kein Erfolg
		session.setAttribute("user", user);
		resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");
	}
}
