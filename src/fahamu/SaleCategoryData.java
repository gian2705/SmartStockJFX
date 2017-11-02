package fahamu;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.*;
import java.util.Calendar;

import static fahamu.LogInStage.*;
import static fahamu.StockCategoryUI.categoryList;

class SaleCategoryData {

    private static String localhost = "127.0.0.1";

    //insert a row of data to the cashSale table
    static void insertData(
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
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;

        Date date = new Date(Calendar.getInstance().getTimeInMillis());
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
                " VALUES(\'" + id + "\',\'" + date.toString() + "\',\'" + product + "\'," +
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

    static ObservableList<fahamu.SalesCategoryUI.CashSaleOfDay> getCashSaleOfDay(String user) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setServerName(serverAddress);
        mysqlDataSource.setPassword(password);

        Connection connection = null;
        ObservableList<fahamu.SalesCategoryUI.CashSaleOfDay> allCashSale = FXCollections.observableArrayList();

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
                        new fahamu.SalesCategoryUI.CashSaleOfDay(
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

    static ObservableList<fahamu.SalesCategoryUI.CashTraSaleOfDay> getCashTraSaleOfDay(String user) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setServerName(serverAddress);
        mysqlDataSource.setPassword(password);

        Connection connection = null;
        ObservableList<fahamu.SalesCategoryUI.CashTraSaleOfDay> allCashSale = FXCollections.observableArrayList();

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
                        new fahamu.SalesCategoryUI.CashTraSaleOfDay(
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

    static ObservableList<String> cashierList(String inputDate) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

    static float getTotalSaleOfDay(String user, String date) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

    static ObservableList<fahamu.ReportsCategoryUI.DiscountDetailTableDataClass> getDiscountProduct(String user,
                                                                                                    String date) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        ObservableList<fahamu.ReportsCategoryUI.DiscountDetailTableDataClass> data = FXCollections.observableArrayList();
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
                data.add(new fahamu.ReportsCategoryUI.DiscountDetailTableDataClass(
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

    static ObservableList<XYChart.Series<String, Number>> getSalesByCategory(String fromDate, String toDate) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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
            fahamu.StockCategoryUI.categoryList.clear();
            StockCategoryData.getAllCategories();

            XYChart.Series<String, Number> stringNumberSeries;

            for (String category : categoryList) {
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

    static float getTotalSaleOfDay(String date) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

    static ObservableList<fahamu.ReportsCategoryUI.SalesTableDataClass> getSales(String dateFrom, String dateTo) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        ObservableList<String> dates = FXCollections.observableArrayList();
        ObservableList<fahamu.ReportsCategoryUI.SalesTableDataClass> salesTableDataClassObservableList = FXCollections
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
                            new fahamu.ReportsCategoryUI.SalesTableDataClass(
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

    static float getTotalTraSaleOfDayOfCashier(String user) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

    static float getTotalTraSaleOfDay() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

    static float getTotalDiscount(String user, String date) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

    static ObservableList<fahamu.ReportsCategoryUI.ProductSellHistoryDataClass> getCashSaleHistory(
            String fromDate, String toDate, String product) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        ObservableList<fahamu.ReportsCategoryUI.ProductSellHistoryDataClass> products = FXCollections.observableArrayList();
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
                products.add(new fahamu.ReportsCategoryUI.ProductSellHistoryDataClass(
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

    static ObservableList<String> getCashSaleProductHistory(String fromDate, String toDate) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

    static XYChart.Series<String, Number> getProductCashSaleFrequency(String fromDate, String toDate) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

            int a=1;
            for (String string : products) {
                a++;
                String getFrequency = "SELECT count(product)" +
                        " FROM salesdata.cashSale " +
                        " WHERE  date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\' AND product=\'" + string +"\'";

                Statement statementSeries = connection.createStatement();

                ResultSet resultSetSums = statementSeries.executeQuery(getFrequency);

                if (string.length()>10){
                    string=string.substring(0,10);
                }

                while (resultSetSums.next()) {
                    int frequency= resultSetSums.getInt(1);
                    string=frequency+"."+string;
                    stringNumberSeries.getData().add(new XYChart.Data<>(string,frequency));
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
