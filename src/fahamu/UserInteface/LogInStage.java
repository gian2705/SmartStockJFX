package fahamu.UserInteface;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.dataFactory.ServerCredentialFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;

public class LogInStage {

    //************************************************//
    //private fields                                   //
    //************************************************//
    public static String password;
    @FXML
    public Rectangle imageRectangle;
    @FXML
    public TextField usernameField;
    @FXML
    public Button logInButton;
    @FXML
    public Button forgetPasswordButton;
    @FXML
    public PasswordField passwordField;
    public static String serverAddress;
    public static String username;
    private boolean serverReachable;


    public LogInStage() {

        if (getServerCredential()) {
            //TODO: implementation needed if server is reachable, the address of server is to be replaced
            //check if server is reachable
            serverReachable = checkServerReachable(new byte[]{(byte) 192, (byte) 168, 0, 2});
        } else {
            //TODO: if server is not reachable
            serverReachable = false;
        }
    }

    @FXML
    public void initialize() {
        // to be moved to constructor
        Image image = new Image(this.getClass().getResource("data/lbLogo.jpg").toExternalForm());
        imageRectangle.setFill(new ImagePattern(image));

    }

    //***********************************//
    //private methods                   //
    //**********************************//
    private boolean getServerCredential() {

        //get server credential from encrypted file
        String credentialFilePath = this.getClass().getResource("data/serverCredential.db.encrypted").getFile();
        if (credentialFilePath.contains("!")) credentialFilePath = credentialFilePath.replace("!", "");

        //get server credential details
        // server credential object initialize the has map which contain server
        //detail for login. the constructor the path of the encrypted file.
        try {

            //get the server credential just before show login interface
            ServerCredentialFactory serverCredentialFactory = new ServerCredentialFactory(credentialFilePath);
            username = serverCredentialFactory.serverDetail.get("username");
            password = serverCredentialFactory.serverDetail.get("password");
            serverAddress = serverCredentialFactory.serverDetail.get("serverAddress");
            return true;

        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkServerReachable(byte[] serverIp) {
        //this boolean check if the server is reachable
        boolean serverReachable;
        try {
            //
            InetAddress inetAddress = InetAddress.getByAddress(serverIp);
            boolean reachable = inetAddress.isReachable(1000);

            if (!reachable) {
                //boolean check for availability of local server
                //check availability of local databases
                MysqlDataSource mysqlDataSource = new MysqlDataSource();
                mysqlDataSource.setUser(username);
                mysqlDataSource.setPassword(password);
                mysqlDataSource.setServerName("localhost");

                Connection connection = null;
                try {
                    connection = mysqlDataSource.getConnection();

                    //if connection success, change a server address to local
                    serverReachable = true;
                    serverAddress = "localhost";

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

}

