package databse;

import objects.Contact;
import objects.Message;
import objects.User;

import java.sql.*;

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
    //Schließt die Connection
    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static Boolean isUserNameAvailable(String userName) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT username FROM users WHERE username = ?;");
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return false;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return true;
    }

    public static boolean addUser(String userName, String password) {
        boolean success = false;
        if (!isUserNameAvailable(userName)) {
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
        return success;
    }

    public static User getUser(String userName, String password) {
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
                    System.out.println("no match");
                    return null;
                }


                statement = connection.prepareStatement("SELECT roomid FROM rooms WHERE member LIKE ?;");
                statement.setString(1, "'%" +userName +"%'");
                resultSet = statement.executeQuery();
                System.out.println(resultSet.getString("member"));

                while (resultSet.next()) {
                    int roomID = resultSet.getInt("roomid");
                    String secondUser = resultSet.getString("member");
                    secondUser = secondUser.replace(" " , "");
                    secondUser = secondUser.replace(userName, "");
                    Contact newContact = new Contact(secondUser, "bio", roomID);
                    user.addContact(newContact);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public static boolean addMessage(Message message) {
        boolean succsess = false;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO messages (message, ownername, roomid, date) VALUES (?, ?, ?)");
            statement.setString(1, message.getMessage());
            statement.setString(2, message.getOwner().getUserName());
            statement.setInt(3, message.getRoomID());
            statement.executeUpdate();
            succsess = true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return succsess;
    }
}
