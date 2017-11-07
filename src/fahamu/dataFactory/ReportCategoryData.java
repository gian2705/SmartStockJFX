package fahamu.dataFactory;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static fahamu.UserInteface.LogInStage.*;
import static fahamu.UserInteface.ReportsCategoryUI.purchaseSeries;
import static fahamu.UserInteface.ReportsCategoryUI.salesSeries;
import static fahamu.UserInteface.StockCategoryUI.categoryList;

public class ReportCategoryData {

    public static void getSalesReport(String fromDate, String toDate) {

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
}
