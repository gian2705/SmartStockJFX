package fahamu.UserInteface;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.dataFactory.ServerCredentialFactory;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    public Rectangle logoRectangle;
    @FXML
    public TextField usernameField;
    @FXML
    public Button logInButton;
    @FXML
    public Button forgetPasswordButton;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Rectangle rectangleImage;
    @FXML
    public ProgressIndicator progressIndicator;

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
        Image imageLogo = new Image(this.getClass().getResource("data/lbLogo.jpg").toExternalForm());
        Image brulImage = new Image(this.getClass().getResource("data/calculate.jpg").toExternalForm());
        logoRectangle.setFill(new ImagePattern(imageLogo));
        rectangleImage.setFill(new ImagePattern(brulImage));

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

    public void changeLogo(MouseEvent mouseEvent) {
        //TODO: implement change logo method
    }

    public void enterKeyClicked(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (usernameField.getText().isEmpty()) usernameField.requestFocus();
            else if (passwordField.getText().isEmpty()) passwordField.requestFocus();
            else logIn(null);
        }
    }

    public void logIn(ActionEvent actionEvent) {
        if (actionEvent != null) actionEvent.consume();
        if (validateInputs(usernameField, passwordField)) {
            //get username and password
            final String username = usernameField.getText();
            final String password = passwordField.getText();

            //disable buttons
            usernameField.setDisable(true);
            logInButton.setDisable(true);
            //enable progress Indicator
            progressIndicator.setVisible(true);
            progressIndicator.setProgress(-1f);

            //initiate background task for login
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    updateProgress(-1F, 1);
                    authenticateUser(username, password);
                    return null;
                }
            };
            //task.setOnSucceeded(event1 -> );
            progressIndicator.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
        }

    }

    public void resetPassword(ActionEvent actionEvent) {
        //TODO: reset a password implementation
    }

    private void authenticateUser(String username, String password) {
        //TODO: authenticate user

    }

    private boolean validateInputs(TextField usernameField, PasswordField passwordField) {
        if (usernameField.getText().isEmpty()) {
            usernameField.requestFocus();
            return false;
        } else if (passwordField.getText().isEmpty()) {
            passwordField.requestFocus();
            return false;
        } else return true;
    }


}

