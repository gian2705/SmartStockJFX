package fahamu;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class LogInStageData {

    private static String username;
    private static String password = "@Joshua&5715";
    private static String serverAddress = "10.42.0.1";
    private static String localhost = "localhost";


    //authenticate the username with password to match in database
    static String authenticateUser(String user) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String password = null;

        try {
            try {

                connection = mysqlDataSource.getConnection();

            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }

            Statement statement = connection.createStatement();
            String selectQuery = "SELECT password FROM users.loginInfo WHERE name=\'" + user + "\'";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                password = resultSet.getString(1);
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
        return password;
    }

    //get user type
    static String getUserType(String user) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String type = null;

        try {
            try {

                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }

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
    static ObservableList<String> getAllUsers(String currentUser){
        MysqlDataSource mysqlDataSource=new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection=null;
        String selectQuery="SELECT name FROM users.loginInfo where name!='"+currentUser+"'";

        ObservableList<String> users= FXCollections.observableArrayList();

        try {
            try {
                connection = mysqlDataSource.getConnection();
            }catch (SQLException s){
                mysqlDataSource.setServerName(localhost);
                connection=mysqlDataSource.getConnection();
            }

            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(selectQuery);
            while (resultSet.next()){
                users.add(resultSet.getString(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (connection!=null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    //add new user
    static void addUser(String user, String passw, String type) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;

        try {
            try {

                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }

            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO users.loginInfo(name,password,type) " +
                    " VALUES(\'"+user+"\',\'" + passw + "\',\'"+type+"\')";
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
    static void removeUser(String user){
        MysqlDataSource mysqlDataSource = new MysqlDataSource();

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;

        try {
            try {

                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }

            Statement statement = connection.createStatement();
            String insertQuery = "DELETE FROM users.loginInfo WHERE name=\'"+user+"\'";

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
    static void updateUserInfo(String user, String paswd) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;

        try {
            try {

                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }

            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE users.loginInfo SET password=\'"+paswd+"\' WHERE name=\'" + user + "\'";
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
