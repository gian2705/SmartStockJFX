package fahamu.dataFactory;

import com.fahamu.tech.FileEncrypt;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class ServerCredentialFactory {
    //a map contain detail of a server from sqlite file
    public HashMap<String, String> serverDetail;
    private FileEncrypt fileEncrypt;

    /**
     * this initialize create object to decrypt a file
     * and then call the method to read data from sqlite file
     *
     * @param path=location of encrypted sqlite file which contain server Credential
     */
    public ServerCredentialFactory(String path) {

        //initialize file encrypt class
        fileEncrypt = new FileEncrypt();
        //call encrypt file method
        getServerCredential(path);
    }

    /**
     * a private method to get server password and user name
     * from encrypted sqlite file.
     * @param path=location of the sqlite file which contain server detail and its encrypted
     */
    private void getServerCredential(String path) {

        serverDetail = new HashMap<>();
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
            ResultSet resultSet = statement.executeQuery("select * from serverDetail;");
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
    }

}
