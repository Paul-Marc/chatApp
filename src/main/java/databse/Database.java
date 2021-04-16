package databse;


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

    public static boolean getUser() {
        return false;
    }
}
