package fahamu.dataFactory;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.UserInteface.ExpenditureCategoryUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static fahamu.UserInteface.LogInStage.*;
import static fahamu.UserInteface.Main.serverDetail;

public class ExpenditureCategoryData {

    private static String localhost;
    private static MysqlDataSource mysqlDataSource;
    private static Connection connection;
    private static String username=serverDetail.get("username");
    private static String password=serverDetail.get("password");
    private static String serverAddress=serverDetail.get("serverAddress");

    static {
        mysqlDataSource = new MysqlDataSource();
        localhost = "localhost";
    }

    //get list of expenditure
    public static ObservableList<String> getCategoryList() {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        ObservableList<String> expenditure = FXCollections.observableArrayList();
        connection = null;
        String selectQuery = "SELECT name FROM expensedata.expenseCategory";
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException s) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                expenditure.add(resultSet.getString(1));
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
        return expenditure;
    }

    //get all expenses and its detail
    public static void getExpensesDetails() {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery = "SELECT * FROM expensedata.expenseList";
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException s) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                ExpenditureCategoryUI.expensesListObservableList.add(
                        new ExpenditureCategoryUI.ExpensesList(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getFloat(5)
                        )
                );
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

    }

    //insert expenditure data
    public static void insertExpenditureData(String date, String name, String description, String user, float amount) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String insertQuery = "INSERT INTO expensedata.expenseList(" +
                "date," +
                "name," +
                "description," +
                "user," +
                "amount) VALUES(" +
                "\'" + date + "\'," +
                "\'" + name + "\'," +
                "\'" + description + "\'," +
                "\'" + user + "\'," +
                "" + amount + ")";
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException s) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
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

    //insert new category
    public static void insertNewCategory(String category) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String insertQuery = "INSERT INTO expensedata.expenseCategory VALUES(" +
                "\'" + category + "\')";
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException s) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
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
}
