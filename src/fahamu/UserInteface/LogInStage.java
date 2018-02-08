package fahamu.UserInteface;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.dataFactory.LogInStageData;
import fahamu.dataFactory.ServerCredentialFactory;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;

public class LogInStage {

    @FXML
    public AnchorPane rootPane;
    @FXML
    public Rectangle logoRectangle;
    @FXML
    public TextField usernameField;
    @FXML
    public JFXButton logInJFXButton;
    @FXML
    public JFXButton forgetPasswordJFXButton;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Rectangle rectangleImage;
    @FXML
    public ProgressIndicator progressIndicator;
    @FXML
    public AnchorPane profileImagePane;

    private final String ADMIN = "admin";
    private final String CASHIER = "cashier";
    //for test only
    private boolean isFirstTimeCashier=true;
    private SalesCategoryUI salesCategoryUICashier;


    @FXML
    public void initialize() {
        // to be moved to constructor
        Image imageLogo = new Image(this.getClass().getResource("data/lbLogo.jpg").toExternalForm());
        Image brulImage = new Image(this.getClass().getResource("data/calculate.jpg").toExternalForm());
        logoRectangle.setFill(new ImagePattern(imageLogo));
        rectangleImage.setFill(new ImagePattern(brulImage));
    }

    public void changeLogo() {
        //TODO: implement change logo method
    }

    public void keyboardKeyClicked(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (usernameField.getText().isEmpty()) usernameField.requestFocus();
            else if (passwordField.getText().isEmpty()) passwordField.requestFocus();
            else logIn();
        } else {
            if (keyEvent.getCode() == KeyCode.TAB) {
                if (keyEvent.getSource().equals(usernameField)) {
                    if (usernameField.getText().isEmpty()) {
                        usernameField.requestFocus();
                    } else passwordField.requestFocus();
                } else if (keyEvent.getSource().equals(passwordField)) {
                    if (passwordField.getText().isEmpty()) passwordField.requestFocus();
                    else logIn();
                }
            }
        }
    }

    public void logIn() {
        //if (actionEvent != null) actionEvent.consume();
        if (validateInputs(usernameField, passwordField)) {
            startBackGroundLogin(usernameField.getText(), passwordField.getText(), progressIndicator);
        }
    }

    public void resetPassword() {
        if (usernameField.getText().isEmpty()) usernameField.requestFocus();
        else {
            JFXSnackbar bar = new JFXSnackbar(profileImagePane);
            bar.enqueue(new JFXSnackbar.SnackbarEvent("Contact Administrator"));
            bar.getStyleClass().add("-fx-font-size: 16px");
            passwordField.clear();
        }
    }

    private String authenticateUser(String username, String password) {
        //check if password is correct
        try {
            if (password.equals(LogInStageData.authenticateUser(username))) {
                //check type of u
                if (LogInStageData.getUserType(username).equals(ADMIN)) {
                    //TODO: call admin scene
                    return ADMIN;
                } else if (LogInStageData.getUserType(username).equals(CASHIER)) {
                    //TODO: call cashier scene
                    return CASHIER;
                }
            }
        } catch (SQLException e) {
            //TODO: to print some error message in password or a any snack bar
            e.printStackTrace();
        }
        return null;
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

    private void enableProgressIndicator(ProgressIndicator p) {
        p.setVisible(true);
    }

    private void disableProgressIndicator(ProgressIndicator p) {
        p.setVisible(false);
    }

    private void disableButtons(JFXButton[] buttons) {
        for (JFXButton bu :
                buttons) {
            bu.setDisable(true);
        }
    }

    private void enableButtons(JFXButton[] buttons) {
        for (JFXButton bu :
                buttons) {
            bu.setDisable(false);
        }
    }

    private void startBackGroundLogin(String username, String password, ProgressIndicator p) {
        //disable buttons and show progress indicator
        disableButtons(new JFXButton[]{logInJFXButton, forgetPasswordJFXButton});
        enableProgressIndicator(progressIndicator);

        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                updateProgress(-1F, 1);
                //authenticate user
                String user = authenticateUser(username, password);
                if (user == null) {
                    cancel();
                    return null;
                } else {
                    return user;
                }
            }
        };
        task.setOnSucceeded(event -> {
            changeScene(task, (Stage) rootPane.getScene().getWindow());
        });

        task.setOnFailed(event -> {
            enableButtons(new JFXButton[]{logInJFXButton, forgetPasswordJFXButton});
            disableProgressIndicator(progressIndicator);
        });
        task.setOnCancelled(event -> {
            enableButtons(new JFXButton[]{logInJFXButton, forgetPasswordJFXButton});
            disableProgressIndicator(progressIndicator);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username is not available or password is incorrect");
            alert.showAndWait();
        });

        p.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private void changeScene(Task<String> task, Stage stage) {
        if (task.getValue().equals(ADMIN)) {
            //for admin user interface
            //TODO: to create admin scene
            enableButtons(new JFXButton[]{logInJFXButton, forgetPasswordJFXButton});
            disableProgressIndicator(progressIndicator);
            AnchorPane pane = new AnchorPane();
            try {
                pane = FXMLLoader.load(getClass().getResource("fxmls/reset.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setResizable(true);
            stage.setScene(new Scene(pane));
            stage.sizeToScene();
        } else {

            //TODO: to create a cashier scene
            //for cashier user interface
            enableButtons(new JFXButton[]{logInJFXButton, forgetPasswordJFXButton});
            disableProgressIndicator(progressIndicator);
            stage.setResizable(true);
            stage.setScene(new Scene(new VBox(new JFXButton("Implement this")), 300, 300));
            stage.sizeToScene();

            /*
            //for testing only
            if (isFirstTimeCashier) {
                //set objects

                salesCategoryUICashier = new SalesCategoryUI(false);
                Platform.runLater(() ->{
                    new MainStage(MainStage.CASHIER_UI, salesCategoryUICashier);
                    MainStage.stageUser.show();
                } );
                isFirstTimeCashier = false;
            }
            */

        }
    }
}

