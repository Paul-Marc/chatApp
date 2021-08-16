package databse;

import exceptions.NameInUseException;
import exceptions.NameNotFoundException;
import objects.Contact;
import objects.Message;
import objects.User;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Zuordnung zu Person: Paul Conrad(Nutzerprofile,Fremde Nutzer,Gruppe
 * erstellen) & Marc Palfner(Login,Registrieren,Chatfunktionalität)
 * 
 * Zweck: Die Klasse deckt die Anbindung zur Datenbank ab. Es gibt diverse
 * Methoden, welche die nötige DB-Seitige Funktionalität gewährleisten.
 */
public class Database {
	protected static Connection connection;
	private static final String DB_Server = "207.154.234.136:5432";
	private static final String DB_NAME = "2021-Quickchat";
	private static final String DB_USER = "2021-Quickchat";
	private static final String DB_PASSWORD = "076f7f285e51357130928a4b30d3b5f6";
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://" + DB_Server + "/" + DB_NAME;

	/**
	 * Db Initialisieren
	 * 
	 * @return
	 * @throws SQLException
	 */
	private static Connection init() throws SQLException {
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			return connection;
		} catch (ClassNotFoundException classNotFoundException) {
			System.out.println("PostgresDb: Treiber konnte nicht gefunden werden. \n"
					+ "FÃ¼gen Sie die postgresql.jar in WEB-INF/lib ein!");
		}
		return null;
	}

	public static Connection getConnection() throws SQLException {
		try {
			return (connection == null || connection.isClosed() ? init() : connection);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return init();
		}
	}

	/**
	 * Connection beenden
	 */
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	/**
	 * Gibt Userobjekt anhand von username und pw zurück
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws NameNotFoundException
	 */
	public static User getUser(String userName, String password) throws NameNotFoundException {
		User user = null;
		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT username, password, hobbies, gender, biography, privateprofile, birthday, nickname FROM users WHERE username = ?;");
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (password.equals(resultSet.getString("password"))) {
					user = new User(userName);
					user.setPassword(resultSet.getString("password"));
					user.setNickname(resultSet.getString("nickname"));
					user.setHobbies(resultSet.getString("hobbies"));
					user.setGender(resultSet.getString("gender"));
					user.setBiography(resultSet.getString("biography"));
					user.setPrivateprofile(resultSet.getBoolean("privateprofile"));
					user.setContacts(getContacts(userName));
					try {
						user.setDateOfBirth(resultSet.getDate("birthday").toString());
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					throw new NameNotFoundException();
				}
			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Database.closeConnection();
		return user;
	}

	/**
	 * Erstellt Gruppe anhand des Gruppennamens und der Mitglieder-Liste
	 * 
	 * @param groupname
	 * @param members
	 * @return
	 * @throws SQLException
	 */
	public static boolean addGroup(String groupname, String members) throws SQLException {
		boolean success = false;
		// Ausgangslage groupname und members sind valide Eingaben getestet in
		// aufrufender Funktion
		try {
			System.out.println("Name: " + groupname + ": " + members);
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO rooms (member,chatgroup,groupname) VALUES (?,?,?)");
			statement.setString(1, members);
			statement.setBoolean(2, true);
			statement.setString(3, groupname);
			statement.executeQuery();
			success = true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Database.closeConnection();
		return success;
	}

	/**
	 * Löscht eine Gruppe aus der Datenbank
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static boolean deleteGroup(int id) throws SQLException {
		boolean success = false;

		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement("DELETE FROM rooms WHERE roomid = ?");
			statement.setInt(1, id);
			statement.executeQuery();
			success = true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Database.closeConnection();
		return success;

	}

	/**
	 * Gibt die Nutzer einer Gruppe zurück
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static String getUsersFromGroup(int id) throws SQLException {
		String results = null;

		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT member FROM rooms WHERE roomid = ?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				results = resultSet.getString("member");
			} else {
				System.out.println("Resultset ist leer in getUsersFromGroup()");
			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Database.closeConnection();
		return results;

	}

	/**
	 * Gibt alle Nutzer der App zurück
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> getAllUsers() throws SQLException {
		ArrayList<String> allUsers = new ArrayList<String>();

		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT username FROM users");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				allUsers.add(resultSet.getString("username"));
			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Database.closeConnection();
		return allUsers;

	}

	/**
	 * Gibt alle Gruppennamen zurück um zu schauen ob ein Name schon vergeben ist.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> getAllGroupNames() throws SQLException {
		ArrayList<String> allGroupNames = new ArrayList<String>();

		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT groupname FROM rooms WHERE chatgroup = ?");
			statement.setBoolean(1, true);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				allGroupNames.add(resultSet.getString("groupname"));
			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Database.closeConnection();
		return allGroupNames;

	}

	/**
	 * Neuer Nutzer wird einer Gruppe hinzugefügt mittels Nutzername und der
	 * Gruppenid
	 * 
	 * @param username
	 * @param groupid
	 * @return
	 * @throws SQLException
	 */
	public static boolean addNewUserToGroup(String username, int groupid) throws SQLException {
		boolean success = false;

		// Nutzer einer Gruppe bekommen
		String user = getUsersFromGroup(groupid);
		List<String> items = new ArrayList<String>(Arrays.asList(user.split("\\s*,\\s*")));

		// Testen ob username bereits dabei ist
		boolean usernameInGroup = false;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).equals(username)) {
				usernameInGroup = true;
			}
		}

		if (!usernameInGroup) {
			// User ist nicht in der Gruppe -> Also hinzufuegen
			System.out.println("Gruppe mit aktuellen Nutzern: " + user);
			boolean addedSuccessfully = items.add(username);

			if (addedSuccessfully) {
				// String wieder zusammensetzen
				StringBuilder stringBuilder = new StringBuilder("");

				for (String singleString : items) {
					stringBuilder.append(singleString).append(",");
				}

				String newItems = stringBuilder.toString();
				System.out.println("Gruppe mit neuem Stand: " + newItems);

				// in die Datenbank uebernehmen
				boolean membersUpdated = updateMembers(newItems, groupid);

				if (membersUpdated) {
					success = true;
					System.out.println("Nutzer erfolgreich der Gruppe hinzugefuegt.");
				}

			}
		}
		Database.closeConnection();
		return success;
	}

	/**
	 * Nutzer aus Gruppe entfernen anhand des Nutzernamens und der Gruppenid
	 * 
	 * @param username
	 * @param groupid
	 * @return
	 * @throws SQLException
	 */
	public static boolean deleteUserFromGroup(String username, int groupid) throws SQLException {
		boolean success = false;

		// Nutzer einer Gruppe bekommen
		String user = getUsersFromGroup(groupid);
		List<String> items = new ArrayList<String>(Arrays.asList(user.split("\\s*,\\s*")));

		// Testen ob username dabei ist
		boolean usernameInGroup = false;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).equals(username)) {
				usernameInGroup = true;
			}
		}

		if (usernameInGroup) {
			// User ist in der Gruppe
			System.out.println("Gruppe mit aktuellen Nutzern: " + user);
			boolean successfullRemoval = items.remove(username);

			if (successfullRemoval) {
				// String wieder zusammensetzen
				StringBuilder stringBuilder = new StringBuilder("");

				for (String singleString : items) {
					stringBuilder.append(singleString).append(",");
				}

				String newItems = stringBuilder.toString();
				System.out.println("Gruppe mit neuem Stand: " + newItems);

				// in die Datenbank Ã¼bernehmen
				boolean membersUpdated = updateMembers(newItems, groupid);

				if (membersUpdated) {
					success = true;
					System.out.println("Nutzer erfolgreich aus Gruppe geloescht.");
				}

			}
		}
		Database.closeConnection();
		return success;
	}

	/**
	 * Die Gruppen mitglieder aktualisieren
	 * 
	 * @param members
	 * @param groupid
	 * @return
	 */
	public static boolean updateMembers(String members, int groupid) {
		boolean success = false;

		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement("UPDATE rooms SET member = ? WHERE roomid = ?;");
			statement.setString(1, members);
			statement.setInt(2, groupid);
			statement.executeUpdate();
			success = true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			success = false;
		}
		Database.closeConnection();
		return success;
	}

	/**
	 * Nutzer-Objekt anhand des Nutzernamens zurückgeben
	 * 
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	public static User getUser(String userName) throws SQLException {
		User user = null;
		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT username, password, hobbies, gender, biography, privateprofile, birthday, nickname FROM users WHERE username = ?;");
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user = new User(userName);
				user.setPassword(resultSet.getString("password"));
				user.setHobbies(resultSet.getString("hobbies"));
				user.setGender(resultSet.getString("gender"));
				user.setBiography(resultSet.getString("biography"));
				user.setNickname(resultSet.getString("nickname"));
				user.setPrivateprofile(resultSet.getBoolean("privateprofile"));
				try {
					Date date = resultSet.getDate("birthday");
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String dateAsString = dateFormat.format(date);
					user.setDateOfBirth(dateAsString);
				} catch (Exception e) {
					e.printStackTrace();
					user.setDateOfBirth("Unbekannt");
				}

			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Database.closeConnection();
		return user;
	}

	/**
	 * Passwort ändern
	 * 
	 * @param new_pw
	 * @param userName
	 * @return
	 */
	public static boolean updatePassword(String new_pw, String userName) {
		boolean success = false;

		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("UPDATE users SET password = ? WHERE username = ?;");
			statement.setString(1, new_pw);
			statement.setString(2, userName);
			statement.executeUpdate();
			success = true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			success = false;
		}
		Database.closeConnection();
		return success;
	}

	/**
	 * Profil ändern
	 * 
	 * @param userBiography
	 * @param userGender
	 * @param userName
	 * @param privateProfile
	 * @param birthday
	 * @param nickname
	 * @return
	 * @throws ParseException
	 */
	public static boolean updateProfile(String userBiography, String userGender, String userName,
			boolean privateProfile, Date birthday, String nickname, String hobbies) throws ParseException {
		boolean success = false;

		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE users SET biography = ?, gender = ?, privateprofile = ?, birthday = ?, nickname = ?, hobbies = ? WHERE username = ?; ");
			statement.setString(1, userBiography);
			statement.setString(2, userGender);
			statement.setBoolean(3, privateProfile);
			statement.setDate(4, birthday);
			statement.setString(5, nickname);
			statement.setString(6, hobbies);
			statement.setString(7, userName);
			statement.executeUpdate();
			success = true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			success = false;
		}
		Database.closeConnection();
		return success;
	}

	/**
	 * Neuen nutzer hinzufügen bei Registrierung
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public static boolean addUser(String userName, String password) {
		boolean success = false;
		if (doesUserExists(userName)) {
			Database.closeConnection();
			return success;
		}
		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO users (username, password, privateprofile) VALUES (?, ?, ?);");
			statement.setString(1, userName);
			statement.setString(2, password);
			statement.setBoolean(3, false);
			statement.executeUpdate();
			success = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		Database.closeConnection();
		return success;
	}

	/**
	 * Schauen ob ein Nutzer mit bestimmtem Namen existiert.
	 * 
	 * @param userName
	 * @return
	 */
	public static boolean doesUserExists(String userName) {
		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();
			Database.closeConnection();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return false;
	}

	/**
	 * Userid wird anhand des Unsernamens zurückgegeben
	 * 
	 * @param userName
	 * @return
	 */
	public static int getUserID(String userName) {
		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT userid FROM users WHERE  username = ?;");
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int userid = resultSet.getInt("userid");
				Database.closeConnection();
				return userid;
			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Database.closeConnection();
		return -1;
	}

	/**
	 * Alle Kontakte eines Nutzers werden zurückgegeben
	 * 
	 * @param userName
	 * @return
	 */
	public static List<Contact> getContacts(String userName) {
		List<Contact> result = new ArrayList<Contact>();
		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT roomid, member, chatgroup, groupname FROM rooms WHERE member LIKE ?;");
			statement.setString(1, "%" + userName + "%");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				boolean isGroup = resultSet.getBoolean("chatgroup");
				String groupName = resultSet.getString("groupname");
				String[] member = resultSet.getString("member").split(",");
				int roomID = resultSet.getInt("roomid");
				int otherUserIndex;
				if (member[0].equals(userName)) {
					otherUserIndex = 1;
				} else {
					otherUserIndex = 0;
				}
				try {
					// Schauen ob Gruppe
					if (isGroup) {
						// Ist Gruppe
						result.add(new Contact(groupName, roomID));
					} else {
						// Ist keine Gruppe
						try {
							result.add(new Contact(member[otherUserIndex], roomID));
						} catch (Exception e) {
							System.out.println(e.getMessage());
							result.add(new Contact(member[0], roomID));
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Database.closeConnection();
		return result;
	}

	/**
	 * Alle nachrichten aus einer Gruppe werden zurückgegeben
	 * 
	 * @param roomid
	 * @return
	 */
	public static List<Message> getMessagesFromRoom(int roomid) {
		List<Message> messages = new ArrayList<Message>();
		System.out.println(roomid);
		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT message, ownername, time FROM messages WHERE roomid = ? ORDER BY messageid");
			statement.setInt(1, roomid);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String messageText = resultSet.getString("message");
				String ownerName = resultSet.getString("ownername");
				ownerName = ownerName.replace(",", "");
				Timestamp time = resultSet.getTimestamp("time");
				messages.add(new Message(roomid, messageText, ownerName, time));
			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Database.closeConnection();
		return messages;
	}

	/**
	 * Neuer Room wird angelegt
	 * 
	 * @param member
	 * @return
	 */
	public static boolean addRoom(String[] member) {
		try {
			for (int i = 0; i < member.length; i++) {
				if (!doesUserExists(member[i])) {
					System.out.println("hit");
					return false;
				}
			}
			Connection connection = Database.getConnection();
			String memberString = "";
			for (int i = 0; i < member.length; i++) {
				memberString += member[i] + ",";
			}
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO rooms (member, chatgroup) VALUES (?,?);");
			statement.setString(1, memberString);
			statement.setBoolean(2, false);
			statement.executeUpdate();
			return true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return false;
	}

	/**
	 * Neue Nachricht wird in die Datenbank geschrieben
	 * 
	 * @param message
	 * @return
	 */
	public static boolean addMessage(Message message) {
		boolean success = false;
		try {
			Connection connection = Database.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO messages (message, ownername, roomid, time) VALUES (?, ?, ?, ?)");
			statement.setString(1, message.getMessage());
			statement.setString(2, message.getOwner());
			statement.setInt(3, message.getRoomID());
			statement.setTimestamp(4, message.getTimeStamp());
			statement.executeUpdate();
			success = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		Database.closeConnection();
		return success;
	}
	
	
}
