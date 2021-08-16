package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databse.Database;
import objects.Contact;
import objects.Message;
import objects.User;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Die Klasse schreibt zur Einhaltung der 3-Schichten-Architektur alle
 * Nachrichten und Kontakte in Strings und fuegt diese der Session bei.
 */
@WebServlet(name = "LoadChatsServlet", value = "/servlets/LoadChatsServlet")
public class LoadChatsServlet extends HttpServlet {

	/**
	 * Name: doGet Zweck: Alle Kontakte und die aktuellen Nachrichten werden in
	 * Strings gepackt und ins Frontend übertragen.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Session und user-objekt einlesen
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		// Listen für Nachrichten und Kontakte
		List<Message> messages = user.getMessages();
		List<Contact> contacts = user.getContacts();

		// String für Nachrichten
		String nachrichten = "";

		// Laden der Nachrichten
		if (messages != null) {
			for (int i = 0; i < messages.size(); i++) {

				nachrichten = nachrichten + "<tr>";

				nachrichten = nachrichten + "<td class='other'>";
				// Fremde Nachrichten
				if (!messages.get(i).getOwner().equals(user.getUserName())) {
					String temp = messages.get(i).getOwner() + ": ";
					String temp2 = messages.get(i).getMessage();
					nachrichten = nachrichten + temp;
					nachrichten = nachrichten + temp2;
				}
				nachrichten = nachrichten + "</td>";

				nachrichten = nachrichten + "<td class='my'>";
				// Eigene Nachrichten
				if (messages.get(i).getOwner().equals(user.getUserName())) {
					String temp = user.getUserName() + ": ";
					String temp2 = messages.get(i).getMessage();
					nachrichten = nachrichten + temp;
					nachrichten = nachrichten + temp2;
				}
				nachrichten = nachrichten + "</td>";

				nachrichten = nachrichten + "<tr>";

			}
		}

		// 1. String für Nachrichten zur Session hinzufügen
		req.getSession().setAttribute("chatverlauf", nachrichten);

		// String für Kontakte
		String kontakte = "";

		// Wenn es kontakte gibt dann diese in HTML tags in String packen
		if (contacts != null) {
			for (int i = 0; i < contacts.size(); i++) {
				kontakte = kontakte + "<table class='contactTable' border='1'>";
				kontakte = kontakte + "<tr>";
				kontakte = kontakte + "<td>";
				kontakte = kontakte + "<form action='servlets/LoadContactServlet' method='post'>";
				int temp1 = contacts.get(i).getRoomID();
				String temp2 = contacts.get(i).getUserName();
				kontakte = kontakte + "<input type='hidden' value='";
				kontakte = kontakte + String.valueOf(temp1);
				kontakte = kontakte + "' name='roomid'>";

				kontakte = kontakte + "<input type='submit' class='contact_button' value='";
				kontakte = kontakte + temp2;
				kontakte = kontakte + "'>";
				kontakte = kontakte + "</form>";
				kontakte = kontakte + "</td>";
				kontakte = kontakte + "</tr>";
				kontakte = kontakte + "</table>";
			}
		}

		// Buttons für Gruppen hinzufügen
		ArrayList<String> allGroups = null;
		try {
			allGroups = Database.getAllGroupNames();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Schauen ob aktueller Kontakt eine Gruppe ist
		boolean isgroup = false;
		try {
			for (String s : allGroups) {
				if (s.equals(user.getCurrentContact().getUserName())) {
					isgroup = true;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		String groupOptions = "";
		String addToGroup = "";
		String groupMembers = "";

		if (isgroup) {
			// Ist eine Gruppe => Gruppen-Buttons anzeigen
			groupOptions = groupOptions
					+ "<form id='leave_group' action='servlets/LeaveGroupServlet' method='post'>\r\n"
					+ "    	<input type='submit'  value='Gruppe Verlassen'>\r\n" + "    </form>";

			addToGroup = addToGroup
					+ "<form id='userToGroup' action='servlets/AddUserToGroupServlet' method='post'>\r\n"
					+ "    	<input id='enter_new_groupuser' type='text' name='newUser'>\r\n"
					+ "    	<input id='new_groupuser_input' type='submit' value='Zur Gruppe hinzufuegen'>\r\n"
					+ "    </form>";

			int i = user.getCurrentContact().getRoomID();
			try {
				groupMembers = groupMembers + "Gruppenmitglieder: " + Database.getUsersFromGroup(i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Attribute setzen
		req.getSession().setAttribute("leaveGroup", groupOptions);
		req.getSession().setAttribute("addToGroup", addToGroup);
		req.getSession().setAttribute("groupMembers", groupMembers);

		req.getSession().setAttribute("kontaktliste", kontakte);

		// Weiterleitung
		resp.sendRedirect(req.getContextPath() + "/chat.jsp");

	}

}
