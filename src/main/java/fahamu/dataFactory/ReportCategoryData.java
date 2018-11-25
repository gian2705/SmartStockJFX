package fahamu.dataFactory;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.Ui.ReportsCategoryUI;
import fahamu.provider.BaseDataClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static fahamu.Ui.StockCategoryUI.categoryList;

public class ReportCategoryData extends BaseDataClass {

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

    public static ObservableList<XYChart.Series> getGrossProfitReportGraphData(String fromDate, String toDate) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        try {
            mysqlDataSource.setUseSSL(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = null;
        ObservableList<XYChart.Series> series = FXCollections.observableArrayList();

        /*
        the hash map used to store product as key
        and its quantity sold as value. And the List store products as key
        to retrieve the value next
         */
        HashMap<String, Integer> productQuantityMap = new HashMap<>();
        ArrayList<String> products = new ArrayList<>();

        try {
            try {

                connection = mysqlDataSource.getConnection();

            } catch (SQLException s) {
                mysqlDataSource.setServerName("localhost");
                connection = mysqlDataSource.getConnection();
            }

            /*
            get all categories from stock resources.
            this call populate categoryList static variable with resources
             */
            categoryList.clear();
            StockCategoryData.getAllCategories();

            /*
            get all product sold in a given range'
            statement and resultSet variable used as a global-local variable
            and the series used to populate the gross profit
             */

            Statement statement;
            ResultSet resultSet;
            XYChart.Series<String, Number> salesSeries;
            XYChart.Series<String, Number> grossProfitSeries;
            salesSeries = new XYChart.Series<>();
            grossProfitSeries = new XYChart.Series<>();
            grossProfitSeries.setName("Gross Profit");
            salesSeries.setName("Sales");

            /*
            select a distinct product from cashSale table on the given range
             */
            String selectAllSoldProducts = "SELECT DISTINCT product " +
                    "FROM salesdata.cashSale " +
                    "WHERE date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectAllSoldProducts);

            String productForList;
            while (resultSet.next()) {
                productForList = resultSet.getString("product");
                products.add(productForList);

            }

            /*
                get sum of all quantity sold on the given range and save it in a map
                 */
            for (String product :
                    products) {

                String selectSumOfQuantitySold = "SELECT SUM(quantity) as sum" +
                        " FROM salesdata.cashSale WHERE product=\'" + product + "\' AND " +
                        " date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSumOfQuantitySold);
                while (resultSet.next()) {

                    int quantities = resultSet.getInt("sum");
                    productQuantityMap.put(product, quantities);

                }
            }

            /*
            now we iterate the sum of profit of all sold products.
             */
            for (String category :
                    categoryList) {

                /*
                get total cashSale on the given range of date
                */
                String selectSumSale = "SELECT sum(amount) as sum" +
                        " FROM salesdata.cashSale " +
                        " WHERE date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\' AND category=\'" + category + "\'";
                float totalCashSale = 0;

                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSumSale);
                while (resultSet.next()) {
                    totalCashSale = resultSet.getFloat("sum");
                }

                float profit = 0;
                for (String product :
                        products) {

                    String selectProfitOfProductFromStock = "SELECT profit  " +
                            "FROM stockdata.retailStock " +
                            "WHERE product=\'" + product + "\' AND category=\'" + category + "\'";

                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(selectProfitOfProductFromStock);
                    while (resultSet.next()) {
                        /*
                        get total quantity sold of a product,
                        and return the total profit
                        */

                        float sumProfit = productQuantityMap.get(product) * resultSet.getFloat("profit");
                        profit = profit + sumProfit;
                    }

                }

                /*
                populate the table with resources
                */


                salesSeries.getData().add(new XYChart.Data<>(category, totalCashSale));
                grossProfitSeries.getData().add(new XYChart.Data<>(category, profit));

            }

            series.addAll(salesSeries, grossProfitSeries);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return series;
    }

    public static ObservableList<ReportsCategoryUI.GrossProfitTableViewData> getGrossProfitReportTableData(
            String fromDate, String toDate) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<ReportsCategoryUI.GrossProfitTableViewData> data = FXCollections.observableArrayList();

        /*
        the hash map used to store product as key
        and its quantity sold as value. And the List store products as key
        to retrieve the value next
         */
        HashMap<String, Integer> productQuantityMap = new HashMap<>();
        ArrayList<String> products = new ArrayList<>();

        try {
            try {

                connection = mysqlDataSource.getConnection();

            } catch (SQLException s) {
                mysqlDataSource.setServerName("localhost");
                connection = mysqlDataSource.getConnection();
            }

            /*
            get all categories from stock resources.
            this call populate categoryList static variable with resources
             */
            categoryList.clear();
            StockCategoryData.getAllCategories();

            /*
            get all product sold in a given range'
            statement and resultSet variable used as a global-local variable
             */

            Statement statement;
            ResultSet resultSet;

            /*
            select a distinct product from cashSale table on the given range
             */
            String selectAllSoldProducts = "SELECT DISTINCT product " +
                    "FROM salesdata.cashSale " +
                    "WHERE date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectAllSoldProducts);

            String productForList;
            while (resultSet.next()) {
                productForList = resultSet.getString("product");
                products.add(productForList);

            }

            /*
                get sum of all quantity sold on the given range and save it in a map
                 */
            for (String product :
                    products) {

                String selectSumOfQuantitySold = "SELECT SUM(quantity) as sum" +
                        " FROM salesdata.cashSale WHERE product=\'" + product + "\' AND " +
                        " date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSumOfQuantitySold);
                while (resultSet.next()) {

                    int quantities = resultSet.getInt("sum");
                    productQuantityMap.put(product, quantities);

                }
            }

            /*
            now we iterate the sum of profit of all sold products
             */
            for (String category :
                    categoryList) {

                /*
                get total cashSale on the given range of date
                */
                String selectSumSale = "SELECT sum(amount) as sum" +
                        " FROM salesdata.cashSale " +
                        " WHERE date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\' AND category=\'" + category + "\'";
                float totalCashSale = 0;

                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSumSale);
                while (resultSet.next()) {
                    totalCashSale = resultSet.getFloat("sum");
                }

                float profit = 0;
                for (String product :
                        products) {

                    String selectProfitOfProductFromStock = "SELECT profit  " +
                            "FROM stockdata.retailStock " +
                            "WHERE product=\'" + product + "\' AND category=\'" + category + "\'";

                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(selectProfitOfProductFromStock);
                    while (resultSet.next()) {
                        /*
                        get total quantity sold of a product,
                        and return the total profit
                        */

                        float sumProfit = productQuantityMap.get(product) * resultSet.getFloat("profit");
                        profit = profit + sumProfit;
                    }

                }

                /*
                populate the table with resources
                */
                String totalSale = NumberFormat.getInstance().format(totalCashSale);
                String profitTotal = NumberFormat.getInstance().format(profit);

                data.add(new ReportsCategoryUI.GrossProfitTableViewData(
                        category,
                        totalSale,
                        profitTotal
                ));
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

        return data;
    }
}
