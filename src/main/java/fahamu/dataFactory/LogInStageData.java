package fahamu.dataFactory;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.provider.BaseDataClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogInStageData extends BaseDataClass {

    private static MysqlDataSource mysqlDataSource;
    private static Connection connection;

    static {
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(serverDetail.get("username"));
        mysqlDataSource.setPassword(serverDetail.get("password"));
        mysqlDataSource.setServerName(serverDetail.get("serverAddress"));
        mysqlDataSource.setDatabaseName(serverDetail.get("database"));
    }

    //authenticate the username with password to match in database
    public static String authenticateUser(String user) throws SQLException {

        connection = null;
        String password = null;

        try {

            connection = mysqlDataSource.getConnection();

            Statement statement = connection.createStatement();
            String selectQuery = "SELECT password FROM login_info WHERE name=\'" + user + "\'";
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

    //get user user_type
    public static String getUserType(String user) {

        connection = null;
        String user_type = null;

        try {

            connection = mysqlDataSource.getConnection();


            Statement statement = connection.createStatement();
            String selectQuery = "SELECT user_type FROM login_info WHERE name=\'" + user + "\'";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                user_type = resultSet.getString(1);
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
        return user_type;
    }

    //get all user
    public static ObservableList<String> getAllUsers(String currentUser) {

        connection = null;
        String selectQuery = "SELECT name FROM login_info where name!='" + currentUser + "'";

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
    public static void addUser(String user, String passw, String user_type) {

        connection = null;

        try {
            connection = mysqlDataSource.getConnection();


            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO login_info(name,password,user_type) " +
                    " VALUES(\'" + user + "\',\'" + passw + "\',\'" + user_type + "\')";
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

        connection = null;

        try {

            connection = mysqlDataSource.getConnection();


            Statement statement = connection.createStatement();
            String insertQuery = "DELETE FROM login_info WHERE name=\'" + user + "\'";

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

        connection = null;

        try {

            connection = mysqlDataSource.getConnection();


            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE login_info SET password=\'" + paswd + "\' WHERE name=\'" + user + "\'";
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
