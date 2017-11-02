package fahamu;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

import static fahamu.LogInStage.*;

class StockCategoryData {

    //the selling price of the specific product
    static float sellPrice;
    static float wSellPrice;

    private static String localhost = "localhost";
    private static String selectDatabase = "use stockdata";

    StockCategoryData() {

    }

    //get all product for update
    static ObservableList<String> getProductNames() {
        ObservableList<String> stockRetailProducts = FXCollections.observableArrayList();
        String getAllProducts = "SELECT product FROM retailStock   ORDER BY product";

        Connection connection = null;
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
            //set the database
            statement.execute(selectDatabase);
            //get the products
            ResultSet resultSet = statement.executeQuery(getAllProducts);
            while (resultSet.next()) {
                stockRetailProducts.add(resultSet.getString("product"));
            }
        } catch (SQLException e) {

        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return stockRetailProducts;
    }

    static String getUnit(String condition) {

        String query = "SELECT unit FROM retailStock WHERE product=\'" + condition + "\';";
        Connection connection = null;
        String data = null;
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
            //select database first
            statement.execute(selectDatabase);
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

    static String getSellPrice(String condition) {
        String query = "SELECT sell FROM retailStock WHERE product=\'" + condition + "\'";
        Connection connection = null;
        String formattedPrice = null;
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
            //select database first
            statement.execute(selectDatabase);
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

    static void getWholeSellPrice(String condition) {
        String query = "SELECT wsell FROM retailStock WHERE product=\'" + condition + "\'";
        Connection connection = null;
        String formattedPrice = null;
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
            //select database first
            statement.execute(selectDatabase);
            //get results from selected database
            ResultSet resultSet = statement.executeQuery(query);
            //store the sell price and the number separate formatted separate
            while (resultSet.next()) wSellPrice = resultSet.getFloat("wsell");
            //format the number for accountant
            NumberFormat format = NumberFormat.getInstance();
            formattedPrice = format.format(wSellPrice);
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

    static String getCategory(String condition) {
        String query = "SELECT category FROM retailStock WHERE product=\'" + condition + "\';";
        String category = null;
        Connection connection = null;
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
            //select database
            statement.execute(selectDatabase);
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

    static void getAllCategories() {

        Connection connection = null;
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
            String query = "SELECT * FROM stockdata.categoryList ";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) fahamu.StockCategoryUI.categoryList.add(resultSet.getString(1));

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

    static void removeCategory(String condition) {

        Connection connection = null;
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

    static void removeUnit(String condition) {

        Connection connection = null;
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
    static void getAllUnit() {

        Connection connection = null;

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
            String query = "SELECT * FROM stockdata.unitList ";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) fahamu.StockCategoryUI.unitList.add(resultSet.getString(1));

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

    static String getShelf(String condition) {
        Connection connection = null;
        String query = "SELECT shelf FROM retailStock WHERE product=\'" + condition + "\';";
        String shelf = null;
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
            //select database
            statement.execute(selectDatabase);
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

    static float getProfit(String condition) {
        Connection connection = null;
        String query = "SELECT sell-purchase FROM retailStock WHERE product=\'" + condition + "\'";
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
            //select database
            statement.execute(selectDatabase);
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

    static float getPurchase(String condition) {
        Connection connection = null;
        String query = "SELECT purchase FROM retailStock WHERE product=\'" + condition + "\'";
        float purchasePrice = 0;
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
            //select database
            statement.execute(selectDatabase);
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
    static int getProductQuantity(String condition) {
        int quantity = 0;
        String quantityQuery = "SELECT quantity FROM retailStock WHERE product=\'" + condition + "\'";
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        try {
            try {
                //check the address given if its reachable
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //select database
            statement.execute(selectDatabase);
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

    static String getProductQuantityCheckStatus(String condition) {
        String q_status = null;
        String quantityQuery = "SELECT q_status FROM retailStock WHERE product=\'" + condition + "\'";
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        try {
            try {
                //check the address given if its reachable
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //select database
            statement.execute(selectDatabase);
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

    static int getWholeProductQuantity(String condition) {
        int quantity = 0;
        String quantityQuery = "SELECT wquantity FROM retailStock WHERE product=\'" + condition + "\'";
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        try {
            try {
                //check the address given if its reachable
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //select database
            statement.execute(selectDatabase);
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
    static int getReorderLevel(String condition) {
        Connection connection = null;
        String query = "SELECT reorder FROM retailStock WHERE product=\'" + condition + "\'";
        int reoderLevel = 0;
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
            //select database
            statement.execute(selectDatabase);
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
    static ObservableList<fahamu.StockCategoryUI.StockList> getStockList() {
        String selectQuery = "SELECT * FROM retailStock";
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;

        ObservableList<fahamu.StockCategoryUI.StockList> data = FXCollections.observableArrayList();

        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //select the database
            statement.execute(selectDatabase);
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                data.add(
                        //this table of current stock contents
                        new fahamu.StockCategoryUI.StockList(
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
    static void updateProductQuantity(String product, int quantity) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);
        String updateQuery = "UPDATE retailStock SET quantity=" + quantity + ",profit=sell-purchase ," +
                "times=sell/purchase WHERE product=\'" + product + "\'" ;

        Connection connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException ignore) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //select database to use
            statement.execute(selectDatabase);
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

    static void updateWholeQuantityOfProduct(String product, int wquantity) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;

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

    static void updateWholeSalePrice(String product, float wsell) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;

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
    static void insertProductCategory(String category) {

        Connection connection = null;
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
    static void insertProductUnit(String unit) {
        Connection connection = null;
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
    static void updatePurchasePrice(String product, float purchase) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);
        String updateQuery = "UPDATE retailStock SET purchase=" + purchase + "," +
                "profit=sell-purchase, times=sell/purchase WHERE product=\'" + product + "\'" ;

        Connection connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //select database to use
            statement.execute(selectDatabase);
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

    static void updateSellPrice(String product, float sellPrice) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);
        String updateQuery = "UPDATE retailStock SET sell=" + sellPrice + ",profit=sell-purchase," +
                "times=sell/purchase WHERE product=\'" + product + "\'";

        Connection connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //select database to use
            statement.execute(selectDatabase);
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
    static void updateCurrentSupplier(String product, String supplier) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);
        String updateQuery = "UPDATE retailStock SET supplier=\'" + supplier + "\' WHERE product=\'" + product + "\'";

        Connection connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //select database to use
            statement.execute(selectDatabase);
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
    static void deleteProduct(String product) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        String deleteQuery = "DELETE FROM retailStock WHERE product=\'" + product + "\'";

        Connection connection = null;
        try {
            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException sq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }
            Statement statement = connection.createStatement();
            //select a database
            statement.execute(selectDatabase);
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
    static void updateProductParameters(
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
            String updateQuery = "UPDATE retailStock SET " +
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
            //select database
            statement.execute(selectDatabase);
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
    static void insertDataIntoStock(
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
                    "retailStock " +
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
            // select database
            statement.execute(selectDatabase);
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
    static String[] getQuickStockReport() {

        String[] reports = new String[4];
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;

        try {

            try {
                connection = mysqlDataSource.getConnection();
            } catch (SQLException eq) {
                mysqlDataSource.setServerName(localhost);
                connection = mysqlDataSource.getConnection();
            }

            Statement statement = connection.createStatement();
            //select database
            statement.execute(selectDatabase);
            //fetch data
            String zeroStock = "SELECT count(product) FROM retailStock where quantity=0";
            String reorder = "SELECT count(product) FROM retailStock where quantity<=reorder";
            String expired = "SELECT count(product) FROM retailStock where expire<=curdate()";
            String totalStock = "SELECT count(product) FROM retailStock ";

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

    static ObservableList<String> getExpiredProduct() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

    static ObservableList<fahamu.SalesCategoryUI.ExpiredProduct> getExpiredProductDetail() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String selectQuery = "SELECT product,quantity,expire FROM stockdata.retailStock WHERE expire<=curdate()";
        ObservableList<fahamu.SalesCategoryUI.ExpiredProduct> data = FXCollections.observableArrayList();
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
                data.add(new fahamu.SalesCategoryUI.ExpiredProduct(
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

    static ObservableList<String> getNearExpiredProduct() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

    static ObservableList<fahamu.SalesCategoryUI.NearExpire> getNearExpiredProductDetail() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String selectQuery = "SELECT product,quantity,expire FROM stockdata.retailStock " +
                "WHERE expire>=curdate() AND expire<=curdate()+90";
        ObservableList<fahamu.SalesCategoryUI.NearExpire> data = FXCollections.observableArrayList();
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
                data.add(new fahamu.SalesCategoryUI.NearExpire(
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

    static ObservableList<String> getOrderList() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
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

    static ObservableList<fahamu.SalesCategoryUI.OrderList> getOrderListDetail() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerName(serverAddress);

        Connection connection = null;
        String selectQuery = "SELECT product,quantity,supplier,purchase " +
                "FROM stockdata.retailStock WHERE quantity<=reorder";
        ObservableList<fahamu.SalesCategoryUI.OrderList> data = FXCollections.observableArrayList();
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
                data.add(new fahamu.SalesCategoryUI.OrderList(
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
