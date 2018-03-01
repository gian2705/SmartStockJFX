package fahamu.dataFactory;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.Ui.SalesCategoryUI;
import fahamu.Ui.StockCategoryUI;
import fahamu.provider.BaseDataClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;


public class StockCategoryData extends BaseDataClass {

    //the selling price of the specific product
    public static float sellPrice;
    public static float wSellPrice;

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

    StockCategoryData() {

    }

    //get all product for update
    public static ObservableList<String> getProductNames() {
        ObservableList<String> stockRetailProducts = FXCollections.observableArrayList();
        String getAllProducts = "SELECT product FROM stockdata.retailStock   ORDER BY product";

        connection = null;
        try {
            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get the products
            ResultSet resultSet = statement.executeQuery(getAllProducts);
            while (resultSet.next()) {
                stockRetailProducts.add(resultSet.getString("product"));
            }
        } catch (SQLException ignore) {

        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return stockRetailProducts;
    }

    public static String getUnit(String condition) {

        String query = "SELECT unit FROM stockdata.retailStock WHERE product=\'" + condition + "\';";
        connection = null;
        String data = null;
        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get the results
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) data = resultSet.getString("unit");
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

    public static String getSellPrice(String condition) {
        String query = "SELECT sell FROM stockdata.retailStock WHERE product=\'" + condition + "\'";
        connection = null;
        String formattedPrice = null;
        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get results from selected database
            ResultSet resultSet = statement.executeQuery(query);
            //store the sell price and the number separate formatted separate
            while (resultSet.next()) sellPrice = resultSet.getFloat("sell");
            //format the number for accountant
            NumberFormat format = NumberFormat.getInstance();
            formattedPrice = format.format(sellPrice);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return formattedPrice;
    }

    public static void getWholeSellPrice(String condition) {
        String query = "SELECT wsell FROM stockdata.retailStock WHERE product=\'" + condition + "\'";
        connection = null;
        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get results from selected database
            ResultSet resultSet = statement.executeQuery(query);
            //store the sell price and the number separate formatted separate
            while (resultSet.next()) wSellPrice = resultSet.getFloat("wsell");

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

    public static String getCategory(String condition) {
        String query = "SELECT category FROM stockdata.retailStock WHERE product=\'" + condition + "\';";
        String category = null;
        connection = null;
        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get the results
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) category = resultSet.getString("category");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return category;
    }

    public static void getAllCategories() {

        connection = null;
        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //get the results
            String query = "SELECT * FROM stockdata.categoryList ";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) StockCategoryUI.categoryList.add(resultSet.getString(1));

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

    public static void removeCategory(String condition) {

        connection = null;
        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //get the results
            String query = "DELETE FROM stockdata.categoryList WHERE category=\'" + condition + "\'";
            statement.execute(query);

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

    public static void removeUnit(String condition) {

        connection = null;
        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //get the results
            String query = "DELETE FROM stockdata.unitList WHERE unit=\'" + condition + "\'";
            statement.execute(query);

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

    //get all unit
    public static void getAllUnit() {

        connection = null;

        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //get the results
            String query = "SELECT * FROM stockdata.unitList ";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) StockCategoryUI.unitList.add(resultSet.getString(1));

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

    public static String getShelf(String condition) {
        connection = null;
        String query = "SELECT shelf FROM stockdata.retailStock WHERE product=\'" + condition + "\';";
        String shelf = null;
        try {
            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get the results
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) shelf = resultSet.getString("shelf");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return shelf;
    }

    public static float getProfit(String condition) {
        connection = null;
        String query = "SELECT sell-purchase FROM stockdata.retailStock WHERE product=\'" + condition + "\'";
        float profitPrice = 0;

        try {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get the results
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) profitPrice = resultSet.getFloat(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return profitPrice;
    }

    public static float getPurchase(String condition) {
        connection = null;
        String query = "SELECT purchase FROM stockdata.retailStock WHERE product=\'" + condition + "\'";
        float purchasePrice = 0;
        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get the results
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) purchasePrice = resultSet.getFloat("purchase");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return purchasePrice;

    }

    //get a specific quantity of the product
    public static int getProductQuantity(String condition) {
        int quantity = 0;
        String quantityQuery = "SELECT quantity FROM stockdata.retailStock WHERE product=\'" + condition + "\'";

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        try {
            try {
                //check the address given if its reachable
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get the results
            ResultSet resultSet = statement.executeQuery(quantityQuery);
            while (resultSet.next()) quantity = resultSet.getInt("quantity");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return quantity;
    }

    public static String getProductQuantityCheckStatus(String condition) {
        String q_status = null;
        String quantityQuery = "SELECT q_status FROM stockdata.retailStock WHERE product=\'" + condition + "\'";

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        try {
            try {
                //check the address given if its reachable
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get the results
            ResultSet resultSet = statement.executeQuery(quantityQuery);
            while (resultSet.next()) q_status = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return q_status;
    }

    public static int getWholeProductQuantity(String condition) {
        int quantity = 0;
        String quantityQuery = "SELECT wquantity FROM stockdata.retailStock WHERE product=\'" + condition + "\'";

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        try {
            try {
                //check the address given if its reachable
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get the results
            ResultSet resultSet = statement.executeQuery(quantityQuery);
            while (resultSet.next()) quantity = resultSet.getInt("wquantity");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return quantity;
    }

    //get Product reorder level
    public static int getReorderLevel(String condition) {
        connection = null;
        String query = "SELECT reorder FROM stockdata.retailStock WHERE product=\'" + condition + "\'";
        int reoderLevel = 0;
        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //get the results
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) reoderLevel = resultSet.getInt("reorder");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return reoderLevel;
    }

    //get all stock
    public static ObservableList<StockCategoryUI.StockList> getStockList() {
        String selectQuery = "SELECT * FROM stockdata.retailStock";

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;

        ObservableList<StockCategoryUI.StockList> data = FXCollections.observableArrayList();

        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                data.add(
                        //this table of current stock contents
                        new StockCategoryUI.StockList(
                                resultSet.getString("product"),
                                resultSet.getString("unit"),
                                resultSet.getString("category"),
                                resultSet.getInt("quantity"),
                                resultSet.getString("q_status"),
                                resultSet.getInt("wquantity"),
                                resultSet.getInt("reorder"),
                                resultSet.getString("supplier"),
                                resultSet.getFloat("purchase"),
                                resultSet.getFloat("sell"),
                                resultSet.getFloat("wsell"),
                                resultSet.getFloat("profit"),
                                resultSet.getFloat("times"),
                                resultSet.getString("expire"),
                                resultSet.getString("shelf")
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

    //when a bill is submitted as payed in the cash sale update the remaining product
    //also this method will be called when you update the stock
    public static void updateProductQuantity(String product, int quantity) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);
        String updateQuery = "UPDATE stockdata.retailStock SET quantity=" + quantity + ",profit=sell-purchase ," +
                "times=sell/purchase WHERE product=\'" + product + "\'" ;

        connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException ignore) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //update the product info
            statement.execute(updateQuery);
        } catch (SQLException q) {
            q.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateWholeQuantityOfProduct(String product, int wquantity) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;

        String updateQuery = "UPDATE stockdata.retailStock SET " +
                "wquantity=" + wquantity + " WHERE product=\'" + product + "\'";
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException ignore) {
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

    public static void updateWholeSalePrice(String product, float wsell) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;

        String updateQuery = "UPDATE stockdata.retailStock SET " +
                "wsell=" + wsell + " WHERE product=\'" + product + "\'";
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException ignore) {
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

    //insert a new product category
    public static void insertProductCategory(String category) {

        connection = null;
        try {

            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //get the results
            String query = "INSERT INTO stockdata.categoryList(category) VALUES(\'" + category + "\')";

            statement.execute(query);

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

    //insert new product unit
    public static void insertProductUnit(String unit) {
        connection = null;
        try {
            mysqlDataSource.setUser(username);
            mysqlDataSource.setPassword(password);
            mysqlDataSource.setServerName(serverAddress);

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //get the results
            String query = "INSERT INTO stockdata.unitList(unit) VALUES(\'" + unit + "\')";

            statement.execute(query);

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

    //update the purchase
    public static void updatePurchasePrice(String product, float purchase) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);
        String updateQuery = "UPDATE stockdata.retailStock SET purchase=" + purchase + "," +
                "profit=sell-purchase, times=sell/purchase WHERE product=\'" + product + "\'" ;

        connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //update the product info
            statement.execute(updateQuery);
        } catch (SQLException q) {
            q.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateSellPrice(String product, float sellPrice) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);
        String updateQuery = "UPDATE stockdata.retailStock SET sell=" + sellPrice + ",profit=sell-purchase," +
                "times=sell/purchase WHERE product=\'" + product + "\'";

        connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //update the product info
            statement.execute(updateQuery);
        } catch (SQLException q) {
            q.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //update to current supplier
    public static void updateCurrentSupplier(String product, String supplier) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);
        String updateQuery = "UPDATE stockdata.retailStock SET supplier=\'"
                + supplier + "\' WHERE product=\'" + product + "\'";

        connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();

            //update the product info
            statement.execute(updateQuery);
        } catch (SQLException q) {
            q.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //delete a row
    public static void deleteProduct(String product) {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        String deleteQuery = "DELETE FROM stockdata.retailStock WHERE product=\'" + product + "\'";

        connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //execute delete query
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

    //update the product in the table
    public static void updateProductParameters(
            String condition,
            String product,
            String unity,
            String category,
            String shelf,
            int quantity,
            int wquantity,
            String q_status,
            int reorder,
            String supplier,
            float purchase,
            float sellPrice,
            float wSellPrice,
            String expire) {

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
            String updateQuery = "UPDATE stockdata.retailStock SET " +
                    "product=\'" + product + "\'," +
                    "unit=\'" + unity + "\'," +
                    "category=\'" + category + "\'," +
                    "shelf=\'" + shelf + "\'," +
                    "quantity=" + quantity + "," +
                    "wquantity=" + wquantity + "," +
                    "q_status=\'" + q_status + "\'," +
                    "reorder=" + reorder + "," +
                    "supplier=\'" + supplier + "\'," +
                    "purchase=" + purchase + "," +
                    "sell=" + sellPrice + "," +
                    "wsell=" + wSellPrice + "," +
                    "profit=sell-purchase," +
                    "times= sell/purchase," +
                    "expire=\'" + expire + "\'" +
                    " WHERE product =\'" + condition + "\'";

            //perform the update
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

    //insert new Stock
    public static void insertDataIntoStock(
            String product,
            String unit,
            String category,
            String shelf,
            int quantity,
            int wQuantity,
            String q_status,
            int reorder,
            String supplier,
            float purchase,
            float sell,
            float wSellPrice,
            float profit,
            float times,
            String expire) {

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
                    "stockdata.retailStock " +
                    "(product, " +
                    "unit, " +
                    "category," +
                    " shelf, " +
                    "quantity, " +
                    "wquantity," +
                    "q_status," +
                    "reorder, " +
                    " supplier, " +
                    " purchase," +
                    " sell," +
                    "wsell," +
                    " profit," +
                    " times, " +
                    " expire ) " +
                    "VALUES(" +
                    "\'" + product + "\'," +
                    "\'" + unit + "\'," +
                    "\'" + category + "\'," +
                    "\'" + shelf + "\'," +
                    "" + quantity + "," +
                    "" + wQuantity + "," +
                    "\'" + q_status + "\'," +
                    "" + reorder + "," +
                    "\'" + supplier + "\'," +
                    "" + purchase + "," +
                    "" + sell + "," +
                    "" + wSellPrice + "," +
                    "" + profit + "," +
                    "" + times + "," +
                    "\'" + expire + "\')";

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

    //get the quick stock reports from database
    public static String[] getQuickStockReport() {

        String[] reports = new String[4];

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;

        try {

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException eq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }

            Statement statement = connection.createStatement();

            //fetch resources
            String zeroStock = "SELECT count(product) FROM stockdata.retailStock where quantity=0";
            String reorder = "SELECT count(product) FROM stockdata.retailStock where quantity<=reorder";
            String expired = "SELECT count(product) FROM stockdata.retailStock where expire<=curdate()";
            String totalStock = "SELECT count(product) FROM stockdata.retailStock ";

            ResultSet zeroResults = statement.executeQuery(zeroStock);
            while (zeroResults.next()) {

                reports[0] = zeroResults.getString(1);

            }

            ResultSet reorderResults = statement.executeQuery(reorder);
            while (reorderResults.next()) {
                reports[1] = reorderResults.getString(1);

            }
            ResultSet expireResults = statement.executeQuery(expired);
            while (expireResults.next()) {
                reports[2] = expireResults.getString(1);
            }
            ResultSet totalStockResults = statement.executeQuery(totalStock);
            while (totalStockResults.next()) {
                reports[3] = totalStockResults.getString(1);
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
        return reports;
    }

    public static ObservableList<String> getExpiredProduct() {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery = "SELECT product FROM stockdata.retailStock WHERE expire<=curdate()";
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException q) {
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

    public static ObservableList<SalesCategoryUI.ExpiredProduct> getExpiredProductDetail() {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery = "SELECT product,quantity,expire FROM stockdata.retailStock WHERE expire<=curdate()";
        ObservableList<SalesCategoryUI.ExpiredProduct> data = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException q) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                data.add(new SalesCategoryUI.ExpiredProduct(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getString(3)
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

    public static ObservableList<String> getNearExpiredProduct() {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery = "SELECT product FROM stockdata.retailStock WHERE expire>=curdate() AND expire<=curdate()+90";
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException q) {
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

    public static ObservableList<SalesCategoryUI.NearExpire> getNearExpiredProductDetail() {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery = "SELECT product,quantity,expire FROM stockdata.retailStock " +
                "WHERE expire>=curdate() AND expire<=curdate()+90";
        ObservableList<SalesCategoryUI.NearExpire> data = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException q) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                data.add(new SalesCategoryUI.NearExpire(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getString(3)
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

    public static ObservableList<String> getOrderList() {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery = "SELECT product FROM stockdata.retailStock WHERE quantity<=reorder";
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException q) {
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

    public static ObservableList<SalesCategoryUI.OrderList> getOrderListDetail() {

        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        connection = null;
        String selectQuery = "SELECT product,quantity,supplier,purchase " +
                "FROM stockdata.retailStock WHERE quantity<=reorder";
        ObservableList<SalesCategoryUI.OrderList> data = FXCollections.observableArrayList();
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException q) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                data.add(new SalesCategoryUI.OrderList(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getFloat(4)
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
