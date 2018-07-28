package com.fahamutech.smartstock.javafx.dataFactory;

import com.fahamutech.smartstock.javafx.Ui.PurchaseCategoryUI;
import com.fahamutech.smartstock.javafx.Ui.ReportsCategoryUI;
import com.fahamutech.smartstock.javafx.Ui.SalesCategoryUI;
import com.fahamutech.smartstock.javafx.provider.BaseDataClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

public class PurchaseCategoryData extends BaseDataClass {

    //**********private fields*************//
    private static String localhost;
    private static Connection connection;



    public static float amountOfCreditInvoice = 0;
    private static String username=serverDetail.get("username");
    private static String password=serverDetail.get("password");
    private static String serverAddress=serverDetail.get("serverAddress");


    //add supplier information
    public static void addSupplierInfo(String supplierName, String supplierPostAddress, String shopLocation,
                                       String supplierContacts) {
        connection = null;
        try {

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException l) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String insertQuery = "INSERT INTO suppliers VALUES" +
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
    public static void removeSupplier(String supplier) {
        connection = null;
        try {


            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException l) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String deleteQuery = "DELETE FROM suppliers WHERE name=\'" + supplier + "\'";

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

    //insert resources into cash purchase table
    public static void insertDataIntoCashPurchase(
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
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO " +
                    "cash_purchase " +
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

    //insert resources into credit purchase table
    public static void insertDataIntoCreditPurchase(
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

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO " +
                    "credit_purchase " +
                    "(date, " +
                    "due, " +
                    "invoice, " +
                    "product," +
                    "category, " +
                    "unit, " +
                    " supplier, " +
                    " squantity, " +
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
    public static ObservableList<String> getSuppliers() {


        ObservableList<String> suppliers = FXCollections.observableArrayList();

        connection = null;
        try {

            String selectQuery = "SELECT name FROM suppliers";
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
    public static ObservableList<String> getDueInvoices(String supplier) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        ObservableList<String> invoices = FXCollections.observableArrayList();

        connection = null;
        try {

            String selectQuery = "SELECT DISTINCT invoice FROM credit_purchase WHERE supplier=\'" +
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

    //get credit purchase product
    public static ObservableList<PurchaseCategoryUI.InvoiceDetail> getCreditInvoiceDetail(String invoice,
                                                                                          String supplier) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<PurchaseCategoryUI.InvoiceDetail> invoiceDetails = FXCollections.observableArrayList();
        try {

            String selectQuery = "SELECT product," +
                    "unit," +
                    "quantity," +
                    "purchase," +
                    "amount " +
                    "FROM credit_purchase WHERE invoice=\'" + invoice + "\' AND supplier=\'" + supplier + "\'";

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
                        new PurchaseCategoryUI.InvoiceDetail(
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
    public static void getCreditPurchasedProduct() {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        try {

            String selectQuery = "SELECT DISTINCT " +
                    "invoice," +
                    "timestampdiff(day,curdate(),due) as remain," +
                    "date," +
                    "status," +
                    "supplier " +
                    "FROM credit_purchase where status='not paid' ";
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                PurchaseCategoryUI.creditPurchaseData.addAll(
                        new PurchaseCategoryUI.DueInvoiceListTable(
                                resultSet.getString("invoice"),
                                resultSet.getInt("remain"),
                                resultSet.getString("date"),
                                resultSet.getString("status"),
                                resultSet.getString("supplier")
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
    public static String getExpiredInvoice() {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String string = null;
        try {

            String selectQuery = "SELECT " +
                    "count(DISTINCT invoice) " +
                    "FROM credit_purchase " +
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
    public static String getNearExpireInvoice() {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String string = null;
        try {

            String selectQuery = "SELECT " +
                    "count(DISTINCT invoice) " +
                    "FROM credit_purchase " +
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
    public static String getUnPaidProductsCount() {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String string = null;
        try {

            String selectQuery = "SELECT " +
                    "count(DISTINCT invoice) " +
                    "FROM credit_purchase where status=\'not paid\'";
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
    public static String getTotalAmountOfNotPaidCreditPurchase() {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String formattedAmount = null;
        float amount = 0;
        try {

            String selectQuery = "select sum(amount) from credit_purchase where status='not paid'";
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
    public static void payCreditInvoice(String invoice) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        try {

            String updateQuery;
            updateQuery = "update credit_purchase set status='paid' where invoice=\'" + invoice + "\'";
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

    public static ObservableList<String> getAllCashReceipt() {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<String> data = FXCollections.observableArrayList();
        String selectQuery = "SELECT receipt FROM receipt";

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

    public static ObservableList<String> getAllInvoice() {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<String> data = FXCollections.observableArrayList();
        String selectQuery = "SELECT invoice FROM invoice";

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

    public static ObservableList<PurchaseCategoryUI.CashPurchaseList> getCurrentPurchase(
            String date,
            String receipt,
            String supplier
    ) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<PurchaseCategoryUI.CashPurchaseList> data = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM cash_purchase " +
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
                        new PurchaseCategoryUI.CashPurchaseList(
                                resultSet.getString("date"),
                                resultSet.getString("receipt"),
                                resultSet.getString("product"),
                                resultSet.getString("category"),
                                resultSet.getString("unit"),
                                resultSet.getString("supplier"),
                                resultSet.getInt("quantity"),
                                resultSet.getFloat("purchase"),
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
        return data;
    }

    public static ObservableList<PurchaseCategoryUI.CreditPurchaseList> getCurrentCreditPurchase(
            String date,
            String invoice,
            String supplier
    ) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<PurchaseCategoryUI.CreditPurchaseList> data = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM credit_purchase " +
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
                        new PurchaseCategoryUI.CreditPurchaseList(
                                resultSet.getString("date"),
                                resultSet.getString("due"),
                                resultSet.getString("invoice"),
                                resultSet.getString("product"),
                                resultSet.getString("category"),
                                resultSet.getString("unit"),
                                resultSet.getString("supplier"),
                                resultSet.getInt("quantity"),
                                resultSet.getFloat("purchase"),
                                resultSet.getFloat("amount"),
                                resultSet.getString("status")
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

    public static float getAmountOfCurrentCashPurchase(String date, String receipt, String supplier) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        float sum = 0;
        String selectQuery = "SELECT sum(amount) FROM cash_purchase " +
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

    public static float getAmountOfCurrentCreditPurchase(String date, String invoice, String supplier) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        float sum = 0;
        String selectQuery = "SELECT sum(amount) FROM credit_purchase " +
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

    public static ObservableList<SalesCategoryUI.CashierSale> getCreditPurchasedHistory(
            String fromDate, String toDate, String product) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<SalesCategoryUI.CashierSale> products = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String selectQuery = "SELECT date,quantity,squantity " +
                    " FROM credit_purchase " +
                    " WHERE product=\'" + product + "\' AND date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                products.add(new SalesCategoryUI.CashierSale(
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

    public static ObservableList<String> getCreditPurchaseProductHistory(String fromDate, String toDate) {
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
                    " FROM credit_purchase " +
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

    public static ObservableList<ReportsCategoryUI.DiscountDetailTableDataClass> getCashPurchasedHistory(
            String fromDate, String toDate, String product) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        ObservableList<ReportsCategoryUI.DiscountDetailTableDataClass> products = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException e) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            String selectQuery = "SELECT date,quantity,squantity " +
                    " FROM cash_purchase " +
                    " WHERE product=\'" + product + "\' AND date>=\'" + fromDate + "\' AND date<=\'" + toDate + "\'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                products.add(new ReportsCategoryUI.DiscountDetailTableDataClass(
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

    public static ObservableList<String> getCashPurchaseProductHistory(String fromDate, String toDate) {

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
                    " FROM cash_purchase " +
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

    public static void updateCashPurchaseProductParameter(
            String dateCondition,
            String receiptCondition,
            String supplierCondition,
            String productCondition,
            String date,
            int quantity,
            float purchase) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String updateQuery = "UPDATE cash_purchase SET " +
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

    public static void updateCreditPurchaseProductParameter(
            String dateCondition,
            String invoiceCondition,
            String supplierCondition,
            String productCondition,
            String date,
            int quantity,
            float purchase) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String updateQuery = "UPDATE credit_purchase SET " +
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

    public static void insertReceipt(String receipt) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String insertQuery = "INSERT INTO receipt(date,receipt) " +
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

    public static void removeReceipt(String receipt) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;

        String deleteQuery = "DELETE  FROM receipt where receipt=\'" + receipt + "\'";

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

    public static void insertInvoice(String invoice) {
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String insertQuery = "INSERT INTO invoice(date,invoice) " +
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

    public static void removeInvoice(String invoice) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;

        String deleteQuery = "DELETE  FROM invoice where invoice=\'" + invoice + "\'";

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
