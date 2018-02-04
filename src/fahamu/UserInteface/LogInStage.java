package fahamu.UserInteface;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.dataFactory.LogInStageData;
import fahamu.dataFactory.ServerCredentialFactory;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;

public class LogInStage {

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
    public static String password;
    @FXML
    public AnchorPane popUp;
    private boolean serverReachable;
    private byte[] serverIPv4Address;
    private final String ADMIN="admin";
    private final String CASHIER="cashier";


    public LogInStage()  {
        serverIPv4Address=new byte[]{(byte) 192, (byte) 168, 0, 2};
        if (getServerCredential()) {
            //TODO: implementation needed if server is reachable, the address of server is to be replaced
            //check if server is reachable
            serverReachable = checkServerReachable(serverIPv4Address);
        } else {
            //TODO: if it fails to get a credential of a server
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

    /**
     * check a server on the give IPv4 address if its reachable, if not it check
     * for local host too.
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
        //if (actionEvent != null) actionEvent.consume();
        if (validateInputs(usernameField, passwordField)) {
            startBackGroundLogin(usernameField.getText(),passwordField.getText(),progressIndicator);
        }
    }

    public void resetPassword(ActionEvent actionEvent) {
        if (usernameField.getText().isEmpty())usernameField.requestFocus();
        else {
            Alert alertType=new Alert(Alert.AlertType.INFORMATION);
            alertType.setContentText("Contact admin");
            alertType.showAndWait();
            passwordField.clear();
        }
    }

    private void authenticateUser(String username, String password) {
        //check server if its reachable
        if (checkServerReachable(serverIPv4Address)){
            //check if password is correct
            if (password.equals(LogInStageData.authenticateUser(username))){
                //check type of u
                if (LogInStageData.getUserType(username).equals(ADMIN)){
                    //TODO: call admin scene

                }else if (LogInStageData.getUserType(username).equals(CASHIER)){
                    //TODO: call cashier UI

                }
            }
        }
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

    private void enableIndicator(ProgressIndicator p){
        p.setVisible(true);
    }

    private void disableProgress(ProgressIndicator p){
        p.setVisible(false);
    }

    private void disableButtons(Button[] buttons){
        for (Button bu :
                buttons) {
            bu.setDisable(true);
        }
    }

    private void enableButtons(Button[] buttons){
        for (Button bu :
                buttons) {
            bu.setDisable(false);
        }
    }

    private void startBackGroundLogin(String username,String password,ProgressIndicator p){
        //disable buttons and show progress indicator
        disableButtons(new Button[]{logInButton,forgetPasswordButton});
        enableIndicator(progressIndicator);

       Task<Void> task= new Task<>() {
           @Override
           protected Void call() throws Exception {
               updateProgress(-1F, 1);
               //authenticate user
               authenticateUser(username, password);
               return null;
           }
       } ;
       task.setOnSucceeded(event -> {
           enableButtons(new Button[]{logInButton,forgetPasswordButton});
           disableProgress(progressIndicator);
       });
       task.setOnFailed(event -> {
           enableButtons(new Button[]{logInButton,forgetPasswordButton});
           disableProgress(progressIndicator);
       });
        p.progressProperty().bind(task.progressProperty());
       new Thread(task).start();
    }
}

