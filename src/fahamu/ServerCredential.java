package fahamu;

import com.fahamu.tech.FileEncrypt;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

class ServerCredential {
    //a map contain detail of a server from sqlite file
    HashMap<String, String> serverDetail;
    private FileEncrypt fileEncrypt;

    ServerCredential(String path) {
        getServerCredential(path);
        fileEncrypt = new FileEncrypt();
    }

    /**
     * a private method to get server password and user name
     * from encrypted sqlite file.
     * @param path=location of the sqlite file which contain server detail and its encrypted
     */
    private void getServerCredential(String path) {

        serverDetail = new HashMap<>();
        Connection connection = null;

        //decrypt a server credential file
        String dataPath = null;
        try {
            File inputFile = new File(path);
            File decryptedFile = File.createTempFile("serverCredential", ".db");
            dataPath = decryptedFile.getPath();
            System.out.println(dataPath);
            fileEncrypt.decrypt("Mary has one cat", inputFile, decryptedFile);

            decryptedFile.deleteOnExit();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            connection = DriverManager.getConnection("jdbc:sqlite:" + dataPath);
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
