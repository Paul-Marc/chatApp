package servlets;

import databse.Database;
import exceptions.InvalidGroupException;
import exceptions.InvalidInputException;
import objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Die Klasse ermoeglicht das Erstellen einer neuen Gruppe und das
 * eintragen der Informationen in der Datenbank
 */
@WebServlet(name = "NewGroupToDBServlet", value = "/servlets/NewGroupToDBServlet")
public class NewGroupToDBServlet extends HttpServlet {

	/**
	 * Name: doPost Zweck: Nimmt die Post-Anfrage entgegen und schreibt bei
	 * korrekten Eingaben die Gruppe in die DB
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// user-objekt einlesen
		User user = (User) req.getAttribute("user");

		// Gruppen-infos einlesen
		String groupname = req.getParameter("group_name_input");
		String members = req.getParameter("group_members_input");

		// Schauen ob Gruppe schon existiert
		boolean groupAlreadyExists = false;
		try {
			ArrayList<String> existingGroupNames = Database.getAllGroupNames();

			for (String s : existingGroupNames) {
				if (groupname.equals(s)) {
					// Gruppe existiert bereits
					groupAlreadyExists = true;
				}
			}
		} catch (SQLException throwables) {
			// Fehler bei SQl-Abfrage
			throwables.printStackTrace();
			req.getSession().setAttribute("grouperror", new InvalidGroupException().getMessage());
			resp.sendRedirect(req.getContextPath() + "/createnewgroup.jsp");
			return;
		}

		// Gruppennamen Validieren
		if (groupname.length() > 0 && members.length() > 1 && !groupAlreadyExists) {

			// Mitglieder auf richtigkeit pruefen
			List<String> memberList = new ArrayList<String>(Arrays.asList(members.split("\\s*,\\s*")));
			ArrayList<String> allUsers;
			try {
				// Schauen ob Mitgliedernamen korrekt sind
				allUsers = Database.getAllUsers();
				boolean validGroupMembers = true;
				for (int i = 0; i < memberList.size(); i++) {
					if (!allUsers.contains(memberList.get(i))) {
						// Nutzer konnten nicht gefunden werden
						validGroupMembers = false;
						System.out.println("Folgender Nutzer konnte nicht gefunden werden: " + memberList.get(i));
						req.getSession().setAttribute("grouperror",
								"Folgender Nutzer konnte nicht gefunden werden: " + memberList.get(i));
						resp.sendRedirect(req.getContextPath() + "/createnewgroup.jsp");
						return;
					}
				}

				if (validGroupMembers && memberList.size() > 1) {
					// String wieder zusammensetzen
					StringBuilder stringBuilder = new StringBuilder("");

					for (String singleString : memberList) {
						stringBuilder.append(singleString).append(",");
					}

					String membersWithComma = stringBuilder.toString();

					// In DB schreiben
					boolean successfulTransaction = Database.addGroup(groupname, membersWithComma);

					if (successfulTransaction) {
						// Gruppe erfolgreich erstellt
						System.out.println("Gruppe erfolgreich erstellt!");
					} else {
						// Gruppe konnte nicht erstellt werden
						System.out.println("Fehler beim Gruppe in die Datenbank einfuegen!");
						req.getSession().setAttribute("grouperror", new InvalidGroupException().getMessage());
					}

				} else {
					// Gruppe nicht erstellt => Gruppenmitglieder nicht Valide oder zu wenig
					// mitglieder < 2
					System.out.println("Gruppe konnte nicht erstellt werden.");
					req.getSession().setAttribute("grouperror", new InvalidGroupException().getMessage());
				}

			} catch (SQLException throwables) {
				// Error durch SQL-Befehl
				throwables.printStackTrace();
				req.getSession().setAttribute("grouperror", new InvalidGroupException().getMessage());
				resp.sendRedirect(req.getContextPath() + "/createnewgroup.jsp");
				return;
			}

		} else {
			// Fehler durch ungueltigen Gruppennamen oder bei der Mitgliederzahl
			System.out.println("Ungueltiger Gruppenname oder Mitgliederzahl.");
			req.getSession().setAttribute("grouperror",
					"Entweder ist der Gruppenname schon vergeben oder deine Eingaben sind zu kurz.");
			resp.sendRedirect(req.getContextPath() + "/createnewgroup.jsp");
			return;
		}

		resp.sendRedirect(req.getContextPath() + "/servlets/LoadChatsServlet");

	}
}
