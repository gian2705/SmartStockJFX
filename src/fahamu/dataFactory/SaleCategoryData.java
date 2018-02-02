package fahamu.dataFactory;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.UserInteface.ReportsCategoryUI;
import fahamu.UserInteface.SalesCategoryUI;
import fahamu.UserInteface.StockCategoryUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static fahamu.UserInteface.LogInStage.*;

public class SaleCategoryData {

    private static String localhost;
    private static MysqlDataSource mysqlDataSource;
    private static Connection connection;

    static {
        mysqlDataSource = new MysqlDataSource();
        localhost = "localhost";
    }

    //insert a row of data to the cashSale table
    public static void insertData(
            String id,
            String product,
            String category,
            String unit,
            int quantity,
            float amount,
            float discount,
            String user) {
        String insertQuery;
        //initialize connection to database server

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;


        insertQuery = "INSERT INTO salesdata.cashSale(" +
                "id," +
                "date," +
                "product," +
                "category," +
                "unit," +
                "quantity," +
                "amount," +
                "discount," +
                "user) " +
                " VALUES(\'" + id + "\', curdate() ,\'" + product + "\'," +
                "\'" + category + "\',\'" + unit + "\'," + quantity + "," + amount + "," + discount + ",\'" + user + "\')";
        try {
            try {

                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement insertStatement = connection.createStatement();

            //insert the value to the cash sale table
            insertStatement.execute(insertQuery);
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

    public static ObservableList<SalesCategoryUI.CashSaleOfDay> getCashSaleOfDay(String user) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setServerName(serverAddress);
        mysqlDataSource.setPassword(password);

        connection = null;
        ObservableList<SalesCategoryUI.CashSaleOfDay> allCashSale = FXCollections.observableArrayList();

        String selectQuery = "SELECT * FROM salesdata.cashSale WHERE user=\'" + user + "\' AND date=curdate()";
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
                allCashSale.add(
                        new SalesCategoryUI.CashSaleOfDay(
                                resultSet.getString("date"),
                                resultSet.getString("product"),
                                resultSet.getString("category"),
                                resultSet.getInt("quantity"),
                                resultSet.getFloat("amount")
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

        return allCashSale;
    }

    public static ObservableList<SalesCategoryUI.CashTraSaleOfDay> getCashTraSaleOfDay(String user) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setServerName(serverAddress);
        mysqlDataSource.setPassword(password);

        connection = null;
        ObservableList<SalesCategoryUI.CashTraSaleOfDay> allCashSale = FXCollections.observableArrayList();

        String selectQuery = "SELECT * FROM salesdata.cashSale WHERE user=\'" + user + "\' AND date=curdate() AND id='n/n'";
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
                allCashSale.add(
                        new SalesCategoryUI.CashTraSaleOfDay(
                                resultSet.getString("date"),
                                resultSet.getString("product"),
                                resultSet.getString("category"),
                                resultSet.getInt("quantity"),
                                resultSet.getFloat("amount")
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

        return allCashSale;
    }

    public static ObservableList<String> cashierList(String inputDate) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery = "SELECT DISTINCT user FROM salesdata.cashSale WHERE date=\'" + inputDate + "\'";
        ObservableList<String> data = FXCollections.observableArrayList();

        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                data.add(resultSet.getString(1));
            }

        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    public static float getTotalSaleOfDay(String user, String date) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery
                = "SELECT sum(amount) FROM salesdata.cashSale WHERE user=\'" + user + "\' AND date=\'" + date + "\'";
        float sum = 0;
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
                sum = resultSet.getFloat(1);
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
        return sum;
    }

    public static ObservableList<ReportsCategoryUI.DiscountDetailTableDataClass> getDiscountProduct(String user,
                                                                                                    String date) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<ReportsCategoryUI.DiscountDetailTableDataClass> data = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT product,amount,discount " +
                    "FROM salesdata.cashSale WHERE date=\'" + date + "\' AND user=\'" + user + "\' AND discount>0";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                data.add(new ReportsCategoryUI.DiscountDetailTableDataClass(
                        resultSet.getString(1),
                        resultSet.getFloat(2),
                        resultSet.getFloat(3)
                ));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    public static ObservableList<XYChart.Series<String, Number>> getSalesByCategory(String fromDate, String toDate) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<XYChart.Series<String, Number>> series = FXCollections.observableArrayList();
        ObservableList<String> dates = FXCollections.observableArrayList();

        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String getDate = "SELECT DISTINCT date FROM salesdata.cashSale WHERE date>=\'"
                    + fromDate + "\'  AND date<=\'" + toDate + "\'";

            Statement statementDate = connection.createStatement();
            //get dates range
            ResultSet resultSetDate = statementDate.executeQuery(getDate);
            while (resultSetDate.next()) {
                dates.add(resultSetDate.getString(1));
            }
            //get categories
            StockCategoryUI.categoryList.clear();
            StockCategoryData.getAllCategories();

            XYChart.Series<String, Number> stringNumberSeries;

            for (String category : StockCategoryUI.categoryList) {
                stringNumberSeries = new XYChart.Series<>();
                stringNumberSeries.setName(category);

                //get sums on dates range
                for (String date : dates) {

                    String getSums = "SELECT sum(amount)" +
                            " FROM salesdata.cashSale " +
                            " WHERE date=\'" + date + "\' AND category=\'" + category + "\'";

                    Statement statementSeries = connection.createStatement();

                    ResultSet resultSetSums = statementSeries.executeQuery(getSums);

                    while (resultSetSums.next()) {
                        stringNumberSeries.getData().add(new XYChart.Data<>(date, resultSetSums.getFloat(1)));
                    }

                }
                //add series before repeat the loop
                series.add(stringNumberSeries);
            }

        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return series;
    }

    public static float getTotalSaleOfDay(String date) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery
                = "SELECT sum(amount) FROM salesdata.cashSale WHERE date=\'" + date + "\'";
        float sum = 0;
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
                sum = resultSet.getFloat(1);
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
        return sum;
    }

    public static ObservableList<ReportsCategoryUI.SalesTableDataClass> getSales(String dateFrom, String dateTo) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<String> dates = FXCollections.observableArrayList();
        ObservableList<ReportsCategoryUI.SalesTableDataClass> salesTableDataClassObservableList = FXCollections
                .observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String getDate = "SELECT DISTINCT date FROM salesdata.cashSale WHERE date>=\'"
                    + dateFrom + "\'  AND date<=\'" + dateTo + "\'";

            Statement statementDate = connection.createStatement();
            //get dates range
            ResultSet resultSetDate = statementDate.executeQuery(getDate);
            while (resultSetDate.next()) {
                dates.add(resultSetDate.getString(1));
            }

            //get sums on dates range
            for (String date : dates) {

                String getSums = "SELECT sum(amount), sum(discount) FROM salesdata.cashSale WHERE date=\'" + date + "\'";
                Statement statementSum = connection.createStatement();

                ResultSet resultSetSums = statementSum.executeQuery(getSums);

                while (resultSetSums.next()) {
                    salesTableDataClassObservableList.add(
                            new ReportsCategoryUI.SalesTableDataClass(
                                    date,
                                    resultSetSums.getFloat(1),
                                    resultSetSums.getFloat(2)
                            )
                    );
                }

            }

        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return salesTableDataClassObservableList;
    }

    public static float getTotalTraSaleOfDayOfCashier(String user) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery =
                "SELECT sum(amount) FROM salesdata.cashSale WHERE user=\'" + user + "\' AND id='n/n' AND date=curdate()";
        float sum = 0;
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
                sum = resultSet.getFloat(1);
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
        return sum;
    }

    public static float getTotalTraSaleOfDay() {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery =
                "SELECT sum(amount) FROM salesdata.cashSale WHERE  id='n/n' AND date=curdate()";
        float sum = 0;
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
                sum = resultSet.getFloat(1);
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
        return sum;
    }

    public static float getTotalDiscount(String user, String date) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery = "SELECT sum(discount) FROM salesdata.cashSale where user=\'" + user + "\' AND date=\'" + date + "\'";
        float sum = 0;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                sum = resultSet.getFloat(1);
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
        return sum;
    }

    public static ObservableList<ReportsCategoryUI.ProductSellHistoryDataClass> getCashSaleHistory(
            String fromDate, String toDate, String product) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<ReportsCategoryUI.ProductSellHistoryDataClass> products = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String selectQuery = "SELECT date,quantity" +
                    " FROM salesdata.cashSale " +
                    " WHERE product=\'" + product + "\' AND date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                products.add(new ReportsCategoryUI.ProductSellHistoryDataClass(
                        resultSet.getString(1),
                        resultSet.getInt(2)
                ));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    public static ObservableList<String> getCashSaleProductHistory(String fromDate, String toDate) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<String> products = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String selectQuery = "SELECT DISTINCT product " +
                    " FROM salesdata.cashSale " +
                    " WHERE date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                products.add(resultSet.getString(1));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    public static XYChart.Series<String, Number> getProductCashSaleFrequency(String fromDate, String toDate) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<String> products = FXCollections.observableArrayList();
        XYChart.Series<String, Number> stringNumberSeries;
        stringNumberSeries = new XYChart.Series<>();
        stringNumberSeries.setName("Cash Sale");

        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String getProducts = "SELECT DISTINCT product FROM salesdata.cashSale  WHERE date>=\'"
                    + fromDate + "\'  AND date<=\'" + toDate + "\' ORDER BY product";

            Statement statementDate = connection.createStatement();
            //get dates range
            ResultSet resultSetDate = statementDate.executeQuery(getProducts);
            while (resultSetDate.next()) {
                products.add(resultSetDate.getString(1));
            }

            int a = 1;
            for (String string : products) {
                a++;
                String getFrequency = "SELECT count(product)" +
                        " FROM salesdata.cashSale " +
                        " WHERE  date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\' AND product=\'" + string + "\'";

                Statement statementSeries = connection.createStatement();

                ResultSet resultSetSums = statementSeries.executeQuery(getFrequency);

                if (string.length() > 10) {
                    string = string.substring(0, 10);
                }

                while (resultSetSums.next()) {
                    int frequency = resultSetSums.getInt(1);
                    string = frequency + "." + string;
                    stringNumberSeries.getData().add(new XYChart.Data<>(string, frequency));
                }
            }

        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return stringNumberSeries;
    }

}
