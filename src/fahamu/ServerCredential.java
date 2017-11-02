package fahamu;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;

public class ServerCredential {
    //a map contain detail of a server from sqlite file
    HashMap<String, String> serverDetail;

    ServerCredential() {
        getServerCredential();
    }

    /**
     * a private method to get server password and user name
     */
    private void getServerCredential() {
        serverDetail = new HashMap<>();
        Connection connection = null;
        Path dataPath = Paths.get(System.getProperty("user.dir"), "/src/serverCredential.db");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataPath.toString());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from serverDetail;");
            while (resultSet.next()) {
                serverDetail.put(resultSet.getString(1), resultSet.getString(2));
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
