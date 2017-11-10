package fahamu.dataFactory;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.UserInteface.ReportsCategoryUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import static fahamu.UserInteface.LogInStage.*;
import static fahamu.UserInteface.ReportsCategoryUI.purchaseSeries;
import static fahamu.UserInteface.ReportsCategoryUI.salesSeries;
import static fahamu.UserInteface.StockCategoryUI.categoryList;

public class ReportCategoryData {

    public static void getGrossProfitReportGraphData(String fromDate, String toDate) {

        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException s) {
                mysqlDataSource.setServerName("localhost");
                connection = mysqlDataSource.getConnection();
            }

            /*
            categories used as x-axis of the graph
             */
            categoryList.clear();
            StockCategoryData.getAllCategories();

            Statement statement;
            ResultSet resultSet;
            for (String category :
                    categoryList) {
                String selectSumOfSalesQuery = "SELECT sum(amount) as sum " +
                        "FROM salesdata.cashSale where category=\'" + category + "\' " +
                        "AND date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

                /*
                because in database we have two source of purchase
                 */
                String selectSumOfCashPurchaseQuery = "SELECT sum(amount) as sum " +
                        "FROM purchasedata.cashPurchase where category=\'" + category + "\' " +
                        "AND date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

                String selectSumOfCreditPurchaseQuery = "SELECT sum(amount) as sum " +
                        "FROM purchasedata.creditPurchase where category=\'" + category + "\' " +
                        "AND date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

                //to sum credit and cash purchase sum
                //it initialize to zero every time loop repeat
                float sum = 0;

                /*
                find sum of sales for given category
                 */
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSumOfSalesQuery);
                while (resultSet.next()) {
                    salesSeries.getData().add(new XYChart.Data<>(category, resultSet.getFloat("sum")));
                }

                /*
                to find sum of all kind of purchase
                 */
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSumOfCashPurchaseQuery);
                while (resultSet.next()) {
                    sum = resultSet.getFloat("sum");
                }
                resultSet = statement.executeQuery(selectSumOfCreditPurchaseQuery);
                while (resultSet.next()) {
                    sum = sum + resultSet.getFloat("sum");
                    purchaseSeries.getData().add(new XYChart.Data<>(category, sum));
                }

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

    public static ObservableList<ReportsCategoryUI.GrossProfitTableViewData> getGrossProfitReportTableData(
            String fromDate, String toDate) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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
            get all categories from stock data.
            this call populate categoryList static variable with data
             */
            categoryList.clear();
            StockCategoryData.getAllCategories();

            /*
            get all product sold in a given range'
            statement and resultSet variable used as a global-local variable
             */
            Statement statement;
            ResultSet resultSet;

            String selectAllSoldProducts = "SELECT product,quantity " +
                    "FROM salesdata.cashSale " +
                    "WHERE date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectAllSoldProducts);
            while (resultSet.next()) {
                String product = resultSet.getString("product");
                int quantity = resultSet.getInt("quantity");

                products.add(product);
                productQuantityMap.put(product, quantity);
            }

            /*
            now we iterate the sum of profit of all sold products
             */
            for (String category :
                    categoryList) {

                for (String product :
                        products) {

                    String selectProfitOfProductFromStock = "SELECT profit  " +
                            "FROM stockdata.retailStock " +
                            "WHERE product=\'" + product + "\' AND category=\'" + category + "\'";

                /*
                This variables used to populate gross profit table
                sum is used to find total of credit and cash purchase,
                initialized to zero every time loop repeat
                */
                    float saleSum = 0;
                    float sumPurchase = 0;
                    int grossProfitSum;

                /*
                find sum of profit for given product and category
                 */
                    statement = connection.createStatement();
                    //resultSet = statement.executeQuery(selectSumOfSalesQuery);
                    while (resultSet.next()) {
                        saleSum = resultSet.getFloat("sum");
                    }

                /*
                to find sum of all kind of purchase
                 */
                    statement = connection.createStatement();
                    //resultSet = statement.executeQuery(selectSumOfCashPurchaseQuery);
                    while (resultSet.next()) {
                        sumPurchase = resultSet.getFloat("sum");
                    }
                    //resultSet = statement.executeQuery(selectSumOfCreditPurchaseQuery);
                    while (resultSet.next()) {
                        sumPurchase = sumPurchase + resultSet.getFloat("sum");
                    }

                    grossProfitSum = (int) (saleSum - sumPurchase);

                /*
                populate the gross profit table with data
                 */

                    data.add(new ReportsCategoryUI.GrossProfitTableViewData(
                            category,
                            saleSum,
                            sumPurchase,
                            grossProfitSum
                    ));


                }
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
