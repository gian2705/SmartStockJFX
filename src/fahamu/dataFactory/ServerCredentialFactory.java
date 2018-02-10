package fahamu.dataFactory;

import com.fahamu.tech.FileEncrypt;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class ServerCredentialFactory {

    /**
     * this initialize create object to decrypt a file
     * and then call the method to read data from sqlite file
    */
    public ServerCredentialFactory() {

    }

    /**
     * a private method to get server password and user name
     * from encrypted sqlite file.
     * @param path=location of the sqlite file which contain server detail and its encrypted
     */
    public HashMap<String, String> getServerCredential(String path) {

        HashMap<String, String> serverDetail = new HashMap<>();
        FileEncrypt fileEncrypt=new FileEncrypt();
        //mysql connection object
        Connection connection = null;

        //decrypt a server credential file
        String dataPath = null;
        File decryptedFile=null;
        try {
            File inputFile = new File(path);
            decryptedFile = File.createTempFile("serverCredential", ".db");
            //dataPath is the temporary folder hold sqlite file which contain severCredential
            dataPath = decryptedFile.getPath();
            fileEncrypt.decrypt(FileEncrypt.KEY, inputFile, decryptedFile);


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            connection = DriverManager.getConnection("jdbc:sqlite:" + dataPath);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from serverDetail");
            while (resultSet.next()) {
                serverDetail.put(resultSet.getString(1), resultSet.getString(2));
            }

            //delete the temporary file when done
            if (decryptedFile != null) decryptedFile.delete();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return serverDetail;
    }

}
