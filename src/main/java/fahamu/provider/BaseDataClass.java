package fahamu.provider;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.Ui.BaseUIComponents;
import fahamu.dataFactory.ServerCredentialFactory;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class BaseDataClass extends Resources {

    protected static HashMap<String, String> serverDetail;
    private boolean serverReachable;
    protected static MysqlDataSource mysqlDataSource;
    protected static final String SERVER_ADDRESS = "serverAddress";
    protected static final String SERVER_USERNAME = "username";
    protected static final String SERVER_PASSWORD = "password";
    protected static final String SERVER_DATABASE = "database";

    private String serverAddress;
    private String userName;
    private String databaseName;
    private String databasePassword;

//    static {
//
//
//    }

    public BaseDataClass() {

    }

    public BaseDataClass(String serverAddress, String username, String password, String database) {
        this.serverAddress = serverAddress;
        this.userName = username;
        this.databasePassword = password;
        this.databaseName = database;

        //initialize the server detail
        serverDetail = getDataBaseDetail();

        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(serverDetail.get(SERVER_USERNAME));
        mysqlDataSource.setPassword(serverDetail.get(SERVER_PASSWORD));
        mysqlDataSource.setServerName(serverDetail.get(SERVER_ADDRESS));
        mysqlDataSource.setDatabaseName(serverDetail.get(SERVER_DATABASE));

    }

    private HashMap<String, String> getDataBaseDetail() {
        HashMap<String, String> data = new HashMap<>();
        data.put(SERVER_ADDRESS, getServerAddress());
        data.put(SERVER_USERNAME, getUserName());
        data.put(SERVER_PASSWORD, getDatabasePassword());
        data.put(SERVER_DATABASE, getDatabaseName());
        return data;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    @Deprecated
    public void mysqlServerCheck(byte[] serverIPv4Address, String dataPath) {
        if (getServerCredential(dataPath)) {
            //TODO: implementation needed if server is reachable, the address of server is to be replaced
            //check if server is reachable
            serverReachable = checkServerReachable(serverIPv4Address);
        } else {
            //TODO: if it fails to get a credential of a server
            serverReachable = false;
        }
    }

    public void checkDatabaseStatus(String address) {
        try {
            InetAddress inet4Address = Inet4Address.getByName(address);
            serverReachable = checkServerReachable(inet4Address.getAddress());
        } catch (UnknownHostException ignore) {
            serverReachable = false;
        }
    }

    /**
     * this method is used for development only
     * during production must be changed to fit the general requirement
     *
     * @param credentialFilePath =path of encrypted file
     * @return true if successfully get the server credential
     */
    @Deprecated
    private boolean getServerCredential(String credentialFilePath) {
        try {
            //get the server credential just before show login interface
            ServerCredentialFactory serverCredentialFactory = new ServerCredentialFactory();
            serverDetail = serverCredentialFactory.getServerCredential(credentialFilePath);
            return true;

        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * check a server on the give IPv4 address if its reachable, if not it check
     * for local host too.
     *
     * @param serverIp=byte array of the server IPv4 address
     * @return true if its reachable on the given IP or local host and
     * false if no mysql server available in a given IPv4 address and local host
     */
    private boolean checkServerReachable(byte[] serverIp) {
        //this boolean check if the server is reachable
        boolean serverReachable;
        try {
            //
            InetAddress inetAddress = InetAddress.getByAddress(serverIp);
            boolean reachable = inetAddress.isReachable(10000);

            if (!reachable) {
                //boolean check for availability of local server
                //check availability of local databases
                MysqlDataSource mysqlDataSource = new MysqlDataSource();
                mysqlDataSource.setUser(serverDetail.get("username"));
                mysqlDataSource.setPassword(serverDetail.get("password"));
                mysqlDataSource.setServerName("localhost");

                Connection connection = null;
                try {
                    connection = mysqlDataSource.getConnection();

                    //if connection success, change a server address to local
                    serverReachable = true;
                    serverDetail.remove("serverAddress");
                    serverDetail.put("serverAddress", "localhost");

                } catch (SQLException e) {
                    serverReachable = false;
                } finally {
                    if (connection != null) try {
                        connection.close();
                    } catch (SQLException ignore) {
                    }
                }

            } else serverReachable = true;
        } catch (IOException e) {
            serverReachable = false;
            e.printStackTrace();
        }
        return serverReachable;
    }

    public void showDataBaseErrorDialog(BaseUIComponents baseUIComponents, StackPane parentPane) {
        if (!serverReachable) {
            Platform.runLater(() -> baseUIComponents.alertCreator("Error",
                    "Database Problem",
                    "Database server at IP :" + serverDetail.get("serverAddress") +
                            " \nIts not available, configure your database server.\n" +
                            "Possible troubleshoot:" +
                            "\n1.Make sure you are connected \n" +
                            "2.Check if your wifi is on or cable is connected\n" +
                            "3.Check if server is up and running", parentPane
                    , new ImageView(DATABASE_ERROR_ICON.toExternalForm())));
        }
    }

}
