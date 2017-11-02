package fahamu;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

class PurchaseCategoryData {

    private static String username = "root";
    private static String password = "@Joshua&5715";
    private static String serverAddress = "10.42.0.1";
    private static String localhost = "localhost";

    static float amountOfCreditInvoice = 0;

    //add supplier information
    static void addSupplierInfo(String supplierName,
                                String supplierPostAddress,
                                String shopLocation,
                                String supplierContacts) {
        Connection connection = null;
        try {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException l) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String insertQuery = "INSERT INTO suppliers.supplierInfo VALUES" +
                    "(\'" + supplierName + "\'," +
                    "\'" + supplierPostAddress + "\'," +
                    "\'" + shopLocation + "\'," +
                    "\'" + supplierContacts + "\')";
            Statement statement = connection.createStatement();
            statement.execute(insertQuery);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Supplier Successful Added");
            alert.show();

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

    //create a specific supplier table
    static void removeSupplier(String supplier) {
        Connection connection = null;
        try {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException l) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String deleteQuery = "DELETE FROM suppliers.supplierInfo WHERE name=\'" + supplier + "\'";

            Statement statement = connection.createStatement();
            statement.execute(deleteQuery);

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

    //insert data into cash purchase table
    static void insertDataIntoCashPurchase(
            String date,
            String receipt,
            String product,
            String category,
            String unit,
            String supplier,
            int squantity,
            int quantity,
            float purchase,
            float amount) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO " +
                    "purchasedata.cashPurchase " +
                    "(date, " +
                    "receipt, " +
                    "product," +
                    "category, " +
                    "unit, " +
                    " supplier, " +
                    " squantity," +
                    " quantity," +
                    " purchase," +
                    " amount) " +
                    "VALUES(" +
                    "\'" + date + "\'," +
                    "\'" + receipt + "\'," +
                    "\'" + product + "\'," +
                    "\'" + category + "\'," +
                    "\'" + unit + "\'," +
                    "\'" + supplier + "\'," +
                    "" + squantity + "," +
                    "" + quantity + "," +
                    "" + purchase + "," +
                    "" + amount + ")";

            //perform the update
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

    //insert data into credit purchase table
    static void insertDataIntoCreditPurchase(
            String date,
            String due,
            String invoice,
            String product,
            String category,
            String unit,
            String supplier,
            int squantity,
            int quantity,
            float purchase,
            float amount) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO " +
                    "purchasedata.creditPurchase " +
                    "(date, " +
                    "due, " +
                    "invoice, " +
                    "product," +
                    "category, " +
                    "unit, " +
                    " supplier, " +
                    " squantity, "+
                    " quantity," +
                    " purchase," +
                    " amount," +
                    " status) " +
                    "VALUES(" +
                    "\'" + date + "\'," +
                    "\'" + due + "\'," +
                    "\'" + invoice + "\'," +
                    "\'" + product + "\'," +
                    "\'" + category + "\'," +
                    "\'" + unit + "\'," +
                    "\'" + supplier + "\'," +
                    "" + squantity + "," +
                    "" + quantity + "," +
                    "" + purchase + "," +
                    "" + amount + "," +
                    "\'not paid\')";
            //perform the update
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

    //get suppliers
    static ObservableList<String> getSuppliers() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        ObservableList<String> suppliers = FXCollections.observableArrayList();

        Connection connection = null;
        try {

            String selectQuery = "SELECT name FROM suppliers.supplierInfo";
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                suppliers.add(resultSet.getString("name"));
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
        return suppliers;
    }

    //get invoices
    static ObservableList<String> getDueInvoices(String supplier) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        ObservableList<String> invoices = FXCollections.observableArrayList();

        Connection connection = null;
        try {

            String selectQuery = "SELECT DISTINCT invoice FROM purchasedata.creditPurchase WHERE supplier=\'" +
                    supplier + "\' and status='not paid'";
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                invoices.add(resultSet.getString(1));
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
        return invoices;
    }

    static ObservableList<String> getDueInvoices() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        ObservableList<String> invoices = FXCollections.observableArrayList();

        Connection connection = null;
        try {

            String selectQuery = "SELECT DISTINCT invoice FROM purchasedata.creditPurchase WHERE status='not paid'";
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                invoices.add(resultSet.getString(1));
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
        return invoices;
    }

    //get credit purchase product
    static ObservableList<fahamu.PurchaseCategoryUI.InvoiceDetail> getCreditInvoiceDetail(String invoice, String supplier) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        ObservableList<fahamu.PurchaseCategoryUI.InvoiceDetail> invoiceDetails = FXCollections.observableArrayList();
        try {

            String selectQuery = "SELECT product," +
                    "unit," +
                    "quantity," +
                    "purchase," +
                    "amount " +
                    "FROM purchasedata.creditPurchase WHERE invoice=\'" + invoice + "\' AND supplier=\'" + supplier + "\'";

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            amountOfCreditInvoice = 0;
            while (resultSet.next()) {
                //get total amount of the invoice
                amountOfCreditInvoice = amountOfCreditInvoice + resultSet.getFloat(5);
                invoiceDetails.addAll(
                        new fahamu.PurchaseCategoryUI.InvoiceDetail(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getInt(3),
                                resultSet.getFloat(4),
                                resultSet.getFloat(5)
                        )
                );
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
        return invoiceDetails;
    }

    //get all credit purchased product not paid yet
    static void getCreditPurchasedProduct() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        try {

            String selectQuery = "SELECT DISTINCT " +
                    "invoice," +
                    "timestampdiff(day,curdate(),due)," +
                    "due," +
                    "status," +
                    "supplier " +
                    "FROM purchasedata.creditPurchase where status='not paid' ";
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                fahamu.PurchaseCategoryUI.creditPurchaseData.addAll(
                        new fahamu.PurchaseCategoryUI.DueInvoiceListTable(
                                resultSet.getString(1),
                                resultSet.getInt(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        )
                );
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
    }

    //get expire invoice
    static String getExpiredInvoice() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String string = null;
        try {

            String selectQuery = "SELECT " +
                    "count(DISTINCT invoice) " +
                    "FROM purchasedata.creditPurchase " +
                    "where timestampdiff(day,curdate(),due)<=0 and status=\'not paid\' ";
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                string = resultSet.getString(1);
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
        return string;
    }

    //get near expire invoice
    static String getNearExpireInvoice() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String string = null;
        try {

            String selectQuery = "SELECT " +
                    "count(DISTINCT invoice) " +
                    "FROM purchasedata.creditPurchase " +
                    "where timestampdiff(day,curdate(),due)<=5 and status=\'not paid\'";
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                string = resultSet.getString(1);
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
        return string;
    }

    //get total invoice
    static String getUnPaidProductsCount() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String string = null;
        try {

            String selectQuery = "SELECT " +
                    "count(DISTINCT invoice) " +
                    "FROM purchasedata.creditPurchase where status=\'not paid\'";
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                string = resultSet.getString(1);
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
        return string;
    }

    //get total amount of the credit purchase which not paid
    static String getTotalAmountOfNotPaidCreditPurchase() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String formattedAmount = null;
        float amount = 0;
        try {

            String selectQuery = "select sum(amount) from purchasedata.creditPurchase where status='not paid'";
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                amount = resultSet.getFloat(1);
            }
            formattedAmount = NumberFormat.getInstance().format(amount);

        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return formattedAmount + " TZS";
    }

    //pay the invoice
    static void payCreditInvoice(String invoice) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        try {

            String updateQuery;
            updateQuery = "update purchasedata.creditPurchase set status='paid' where invoice=\'" + invoice + "\'";
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            statement.execute(updateQuery);

        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static ObservableList<String> getAllCashReceipt() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        ObservableList<String> data = FXCollections.observableArrayList();
        String selectQuery = "SELECT receipt FROM purchasedata.receipt";

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
                data.add(resultSet.getString(1));
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

    static ObservableList<String> getAllInvoice() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        ObservableList<String> data = FXCollections.observableArrayList();
        String selectQuery = "SELECT invoice FROM purchasedata.invoice";

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
                data.add(resultSet.getString(1));
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

    static ObservableList<fahamu.PurchaseCategoryUI.CashPurchaseList> getCurrentPurchase(
            String date,
            String receipt,
            String supplier
    ) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        ObservableList<fahamu.PurchaseCategoryUI.CashPurchaseList> data = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM purchasedata.cashPurchase " +
                "WHERE date=\'" + date + "\' " +
                "AND receipt=\'" + receipt + "\' " +
                "AND supplier=\'" + supplier + "\'";

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
                data.add(
                        new fahamu.PurchaseCategoryUI.CashPurchaseList(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getInt(7),
                                resultSet.getFloat(8),
                                resultSet.getFloat(9)
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
        return data;
    }

    static ObservableList<fahamu.PurchaseCategoryUI.CreditPurchaseList> getCurrentCreditPurchase(
            String date,
            String invoice,
            String supplier
    ) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        ObservableList<fahamu.PurchaseCategoryUI.CreditPurchaseList> data = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM purchasedata.creditPurchase " +
                "WHERE date=\'" + date + "\' " +
                "AND invoice=\'" + invoice + "\' " +
                "AND supplier=\'" + supplier + "\'";

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
                data.add(
                        new fahamu.PurchaseCategoryUI.CreditPurchaseList(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getInt(8),
                                resultSet.getFloat(9),
                                resultSet.getFloat(10),
                                resultSet.getString(11)
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
        return data;
    }

    static float getAmountOfCurrentCashPurchase(String date, String receipt, String supplier) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        float sum = 0;
        String selectQuery = "SELECT sum(amount) FROM purchasedata.cashPurchase " +
                "WHERE date=\'" + date + "\'" +
                " AND receipt=\'" + receipt + "\'" +
                " AND supplier=\'" + supplier + "\'";
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

    static float getAmountOfCurrentCreditPurchase(String date, String invoice, String supplier) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        float sum = 0;
        String selectQuery = "SELECT sum(amount) FROM purchasedata.creditPurchase " +
                "WHERE date=\'" + date + "\'" +
                " AND invoice=\'" + invoice + "\'" +
                " AND supplier=\'" + supplier + "\'";
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

    static ObservableList<fahamu.SalesCategoryUI.CashierSale> getCreditPurchasedHistory(
            String fromDate, String toDate, String product) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        ObservableList<fahamu.SalesCategoryUI.CashierSale> products = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String selectQuery = "SELECT date,quantity,squantity " +
                    " FROM purchasedata.creditPurchase " +
                    " WHERE product=\'" + product + "\' AND date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                products.add(new fahamu.SalesCategoryUI.CashierSale(
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
        return products;
    }

    static ObservableList<String> getCreditPurchaseProductHistory(String fromDate, String toDate) {
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
                    " FROM purchasedata.creditPurchase " +
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

    static ObservableList<fahamu.ReportsCategoryUI.DiscountDetailTableDataClass> getCashPurchasedHistory(
            String fromDate, String toDate, String product) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        ObservableList<fahamu.ReportsCategoryUI.DiscountDetailTableDataClass> products = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String selectQuery = "SELECT date,quantity,squantity " +
                    " FROM purchasedata.cashPurchase " +
                    " WHERE product=\'" + product + "\' AND date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                products.add(new fahamu.ReportsCategoryUI.DiscountDetailTableDataClass(
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
        return products;
    }

    static ObservableList<String> getCashPurchaseProductHistory(String fromDate, String toDate) {
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
                    " FROM purchasedata.cashPurchase " +
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

    static void updateCashPurchaseProductParameter(
            String dateCondition,
            String receiptCondition,
            String supplierCondition,
            String productCondition,
            String date,
            int quantity,
            float purchase) {

        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String updateQuery = "UPDATE purchasedata.cashPurchase SET " +
                "date=\'" + date + "\', " +
                "quantity=" + quantity + ", " +
                "purchase=" + purchase + ", " +
                "amount=" + quantity * purchase + "  " +
                "WHERE product=\'" + productCondition + "\' " +
                "AND date=\'" + dateCondition + "\' " +
                "AND receipt=\'" + receiptCondition + "\' " +
                "AND supplier=\'" + supplierCondition + "\' ";
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException s) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
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

    static void updateCreditPurchaseProductParameter(
            String dateCondition,
            String invoiceCondition,
            String supplierCondition,
            String productCondition,
            String date,
            int quantity,
            float purchase) {

        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String updateQuery = "UPDATE purchasedata.creditPurchase SET " +
                "date=\'" + date + "\', " +
                "quantity=" + quantity + ", " +
                "purchase=" + purchase + ", " +
                "amount=" + quantity * purchase + "  " +
                "WHERE product=\'" + productCondition + "\' " +
                "AND date=\'" + dateCondition + "\' " +
                "AND invoice=\'" + invoiceCondition + "\' " +
                "AND supplier=\'" + supplierCondition + "\' ";
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException s) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
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

    static void insertReceipt(String receipt) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String insertQuery = "INSERT INTO purchasedata.receipt(date,receipt) " +
                "VALUES(curdate(), \'" + receipt + "\')";
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

    static void removeReceipt(String receipt) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;

        String deleteQuery = "DELETE  FROM purchasedata.receipt where receipt=\'" + receipt + "\'";

        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException s) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            statement.execute(deleteQuery);

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

    static void insertInvoice(String invoice) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String insertQuery = "INSERT INTO purchasedata.invoice(date,invoice) " +
                "VALUES(curdate(), \'" + invoice + "\')";
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

    static void removeInvoice(String invoice) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;

        String deleteQuery = "DELETE  FROM purchasedata.invoice where invoice=\'" + invoice + "\'";

        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException s) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            statement.execute(deleteQuery);

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
