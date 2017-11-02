package fahamu;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class ExpenditureCategoryData {

    private static String username = "root";
    private static String password = "@Joshua&5715";
    private static String serverAddress = "10.42.0.1";
    private static String localhost = "localhost";

    //get list of expenditure
    static ObservableList<String> getCategoryList() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        ObservableList<String> expenditure = FXCollections.observableArrayList();
        Connection connection = null;
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
    static void getExpensesDetails() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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
                fahamu.ExpenditureCategoryUI.expensesListObservableList.add(
                        new fahamu.ExpenditureCategoryUI.ExpensesList(
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
    static void insertExpenditureData(
            String date,
            String name,
            String description,
            String user,
            float amount) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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
    static void insertNewCategory(String category) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String insertQuery = "INSERT INTO expensedata.expenseCategory VALUES(" +
                "\'"+category+"\')";
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
