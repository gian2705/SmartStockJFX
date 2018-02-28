package fahamu.dataFactory;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogInStageData extends BaseDataClass{

    private static MysqlDataSource mysqlDataSource;
    private static Connection connection;

    static {
        mysqlDataSource = new MysqlDataSource();
    }

    //authenticate the username with password to match in database
    public static String authenticateUser(String user) throws SQLException {

        mysqlDataSource.setUser(serverDetail.get("username"));
        mysqlDataSource.setPassword(serverDetail.get("password"));
        mysqlDataSource.setServerName(serverDetail.get("serverAddress"));

        connection = null;
        String password = null;

        try {

            connection = mysqlDataSource.getConnection();

            Statement statement = connection.createStatement();
            String selectQuery = "SELECT password FROM users.loginInfo WHERE name=\'" + user + "\'";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                password = resultSet.getString("password");
            }

        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return password;
    }

    //get user type
    public static String getUserType(String user) {

        mysqlDataSource.setUser(serverDetail.get("username"));
        mysqlDataSource.setPassword(serverDetail.get("password"));
        mysqlDataSource.setServerName(serverDetail.get("serversAddress"));

        connection = null;
        String type = null;

        try {

            connection = mysqlDataSource.getConnection();


            Statement statement = connection.createStatement();
            String selectQuery = "SELECT type FROM users.loginInfo WHERE name=\'" + user + "\'";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                type = resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return type;
    }

    //get all user
    public static ObservableList<String> getAllUsers(String currentUser) {
        mysqlDataSource.setUser(serverDetail.get("username"));
        mysqlDataSource.setPassword(serverDetail.get("password"));
        mysqlDataSource.setServerName(serverDetail.get("serversAddress"));

        connection = null;
        String selectQuery = "SELECT name FROM users.loginInfo where name!='" + currentUser + "'";

        ObservableList<String> users = FXCollections.observableArrayList();

        try {

            connection = mysqlDataSource.getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                users.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    //add new user
    public static void addUser(String user, String passw, String type) {

        mysqlDataSource.setUser(serverDetail.get("username"));
        mysqlDataSource.setPassword(serverDetail.get("password"));
        mysqlDataSource.setServerName(serverDetail.get("serversAddress"));

        connection = null;

        try {
            connection = mysqlDataSource.getConnection();


            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO users.loginInfo(name,password,type) " +
                    " VALUES(\'" + user + "\',\'" + passw + "\',\'" + type + "\')";
            statement.execute(insertQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //remove user
    public static void removeUser(String user) {

        mysqlDataSource.setUser(serverDetail.get("username"));
        mysqlDataSource.setPassword(serverDetail.get("password"));
        mysqlDataSource.setServerName(serverDetail.get("serversAddress"));

        connection = null;

        try {

            connection = mysqlDataSource.getConnection();


            Statement statement = connection.createStatement();
            String insertQuery = "DELETE FROM users.loginInfo WHERE name=\'" + user + "\'";

            statement.execute(insertQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //update user info
    public static void updateUserInfo(String user, String paswd) {

        mysqlDataSource.setUser(serverDetail.get("username"));
        mysqlDataSource.setPassword(serverDetail.get("password"));
        mysqlDataSource.setServerName(serverDetail.get("serversAddress"));

        connection = null;

        try {

            connection = mysqlDataSource.getConnection();


            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE users.loginInfo SET password=\'" + paswd + "\' WHERE name=\'" + user + "\'";
            statement.execute(updateQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
