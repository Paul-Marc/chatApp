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

public class Database {
    protected static Connection connection;
    private static final String DB_Server = "localhost:5432";
    private static final String DB_NAME = "postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234567890";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://" + DB_Server + "/" + DB_NAME;

    private static Connection init() throws SQLException {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return connection;
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("PostgresDb: Treiber konnte nicht gefunden werden. \n"
                    + "Fügen Sie die postgresql.jar in WEB-INF/lib ein!");
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

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    public static User getUser(String userName, String password) throws NameNotFoundException {
        User user = null;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT username, password, hobbies, gender, biography, privateprofile, birthday FROM users WHERE username = ?;");
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (password.equals(resultSet.getString("password"))) {
                    user = new User(userName);
                    user.setPassword(resultSet.getString("password"));
                    user.setHobbies(resultSet.getString("hobbies"));
                    user.setGender(resultSet.getString("gender"));
                    user.setBiography(resultSet.getString("biography"));
                    user.setPrivateprofile(resultSet.getBoolean("privateprofile"));
                    try{
                        user.setDateOfBirth(resultSet.getDate("birthday").toString());
                        user.setContacts(getContacts(userName));
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

    public static boolean addGroup(String groupname, String members) throws SQLException {
        boolean success = false;
        //Ausgangslage groupname und members sind valide Eingaben getestet in aufrufender Funktion
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO rooms (member,chatgroup,groupname) VALUES (?,?,?)");
            statement.setString(1,members);
            statement.setBoolean(2,true);
            statement.setString(3,groupname);
            statement.executeQuery();
            success = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Database.closeConnection();
        return success;
    }

    public static boolean deleteGroup(int id) throws  SQLException {
        boolean success = false;

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM rooms WHERE roomid = ?");
            statement.setInt(1,id);
            statement.executeQuery();
            success = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Database.closeConnection();
        return success;

    }

    public static String getUsersFromGroup(int id) throws  SQLException {
        String results = null;

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT member FROM rooms WHERE roomid = ?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            results = resultSet.getString("member");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Database.closeConnection();
        return results;

    }


    public static ArrayList<String> getAllUsers() throws  SQLException {
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

    public static ArrayList<String> getAllGroupNames() throws  SQLException {
        ArrayList<String> allGroupNames = new ArrayList<String>();

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT groupname FROM rooms WHERE chatgroup = ?");
            statement.setBoolean(1,true);
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




    public static boolean addNewUserToGroup(String username, int groupid) throws SQLException {
        boolean success = false;

        //Nutzer einer Gruppe bekommen
        String user = getUsersFromGroup(groupid);
        List<String> items = new ArrayList<String>(Arrays.asList(user.split("\\s*,\\s*")));

        //Testen ob username bereits dabei ist
        boolean usernameInGroup = false;
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).equals(username)) {
                usernameInGroup = true;
            }
        }

        if(!usernameInGroup) {
            //User ist nicht in der Gruppe -> Also hinzufügen
            System.out.println("Gruppe mit aktuellen Nutzern: " + user);
            boolean addedSuccessfully = items.add(username);

            if (addedSuccessfully) {
                //String wieder zusammensetzen
                StringBuilder stringBuilder = new StringBuilder("");

                for (String singleString : items) {
                    stringBuilder.append(singleString).append(",");
                }

                String newItems = stringBuilder.toString();
                System.out.println("Gruppe mit neuem Stand: " + newItems);

                //in die Datenbank übernehmen
                boolean membersUpdated = updateMembers(newItems,groupid);

                if (membersUpdated) {
                    success = true;
                    System.out.println("Nutzer erfolgreich der Gruppe hinzugefuegt.");
                }

            }
        }
        Database.closeConnection();
        return success;
    }


    public static boolean deleteUserFromGroup(String username, int groupid) throws SQLException {
        boolean success = false;

        //Nutzer einer Gruppe bekommen
        String user = getUsersFromGroup(groupid);
        List<String> items = new ArrayList<String>(Arrays.asList(user.split("\\s*,\\s*")));

        //Testen ob username dabei ist
        boolean usernameInGroup = false;
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).equals(username)) {
                usernameInGroup = true;
            }
        }

        if(usernameInGroup) {
            //User ist in der Gruppe
            System.out.println("Gruppe mit aktuellen Nutzern: " + user);
            boolean successfullRemoval = items.remove(username);

            if (successfullRemoval) {
                //String wieder zusammensetzen
                StringBuilder stringBuilder = new StringBuilder("");

                for (String singleString : items) {
                    stringBuilder.append(singleString).append(",");
                }

                String newItems = stringBuilder.toString();
                System.out.println("Gruppe mit neuem Stand: " + newItems);

                //in die Datenbank übernehmen
                boolean membersUpdated = updateMembers(newItems,groupid);

                if (membersUpdated) {
                    success = true;
                    System.out.println("Nutzer erfolgreich aus Gruppe geloescht.");
                }

            }
        }
        Database.closeConnection();
        return success;
    }

    public static boolean updateMembers(String members, int groupid) {
        boolean success = false;

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE rooms SET member = ? WHERE roomid = ?;");
            statement.setString(1,members);
            statement.setInt(2,groupid);
            statement.executeUpdate();
            success = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            success = false;
        }
        Database.closeConnection();
        return success;
    }


    public static User getUser(String userName) throws SQLException {
        User user = null;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT username, password, hobbies, gender, biography, privateprofile, birthday FROM users WHERE username = ?;");
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                    user = new User(userName);
                    user.setPassword(resultSet.getString("password"));
                    user.setHobbies(resultSet.getString("hobbies"));
                    user.setGender(resultSet.getString("gender"));
                    user.setBiography(resultSet.getString("biography"));
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

    public static boolean updatePassword(String new_pw, String userName) {
        boolean success = false;

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?;");
            statement.setString(1,new_pw);
            statement.setString(2,userName);
            statement.executeUpdate();
            success = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            success = false;
        }
        Database.closeConnection();
        return success;
    }

    public static boolean updateProfile(String userBiography, String userGender, String userName, boolean privateProfile, Date birthday) throws ParseException {
        boolean success = false;

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET biography = ?, gender = ?, privateprofile = ?, birthday = ? WHERE username = ?; ");
            statement.setString(1,userBiography);
            statement.setString(2,userGender);
            statement.setBoolean(3,privateProfile);
            statement.setDate(4, birthday);
            statement.setString(5,userName);
            statement.executeUpdate();
            success = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            success = false;
        }
        Database.closeConnection();
        return success;
    }


    public static boolean addUser(String userName, String password) {
        boolean success = false;
        if (doesUserExists(userName)) {
            Database.closeConnection();
            return success;
        }
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password, privateprofile) VALUES (?, ?, ?);");
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.setBoolean(3,false);
            statement.executeUpdate();
            success = true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Database.closeConnection();
        return success;
    }

    public static boolean doesUserExists(String userName){
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

    public static List<Contact> getContacts(String userName) {
        List<Contact> result = new ArrayList<Contact>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT roomid, member FROM rooms WHERE member LIKE ?;");
            statement.setString(1, "%" + userName + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] member = resultSet.getString("member").split(",");
                int roomID = resultSet.getInt("roomid");
                int otherUserIndex;
                if (member[0].equals(userName)) {
                    otherUserIndex = 1;
                } else {
                    otherUserIndex = 0;
                }
                try {
                    result.add(new Contact(member[otherUserIndex], "", roomID));
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

    public static List<Message> getMessagesFromRoom(int roomid) {
        List<Message> messages = new ArrayList<Message>();
        System.out.println(roomid);
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT message, ownername, time FROM messages WHERE roomid = ? ORDER BY messageid");
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO rooms (member, chatgroup) VALUES (?,?);");
            statement.setString(1, memberString);
            statement.setBoolean(2,false);
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean addMessage(Message message) {
        boolean succsess = false;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO messages (message, ownername, roomid, time) VALUES (?, ?, ?, ?)");
            statement.setString(1, message.getMessage());
            statement.setString(2, message.getOwner());
            statement.setInt(3, message.getRoomID());
            statement.setTimestamp(4, message.getTimeStamp());
            statement.executeUpdate();
            succsess = true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Database.closeConnection();
        return succsess;
    }
}
