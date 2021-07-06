package databse;

import exceptions.NameInUseException;
import exceptions.NameNotFoundException;
import objects.Contact;
import objects.Message;
import objects.User;

import java.sql.*;
import java.util.ArrayList;
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
            PreparedStatement statement = connection.prepareStatement("SELECT username, password FROM users WHERE username = ?;");
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (password.equals(resultSet.getString("password"))) {
                    user = new User(userName);
                } else {
                    throw new NameNotFoundException();
                }
            }
            user.setContacts(getContacts(userName));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Database.closeConnection();
        return user;
    }

    public static boolean addUser(String userName, String password) {
        boolean success = false;
        if (doesUserExists(userName)) {
            Database.closeConnection();
            return success;
        }
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?);");
            statement.setString(1, userName);
            statement.setString(2, password);
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
                result.add(new Contact(member[otherUserIndex], "", roomID));
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO rooms (member) VALUES (?);");
            statement.setString(1, memberString);
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
