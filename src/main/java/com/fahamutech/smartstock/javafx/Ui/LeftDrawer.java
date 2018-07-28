package com.fahamutech.smartstock.javafx.Ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXSnackbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class LeftDrawer extends BaseUIComponents{

    public JFXButton jFXButtonCloseDrawer;
    private LogInUiController logInUiController;

    public TextField usernameField;
    public JFXButton logInJFXButton;
    public JFXButton forgetPasswordJFXButton;
    public PasswordField passwordField;
    public AnchorPane profileImagePane;
    public Rectangle logoRectangle;


    @FXML
    public void initialize(){
        logInUiController=new LogInUiController();
        Image imageLogo = new Image(BUSINESS_LOGO.toExternalForm());
        logoRectangle.setFill(new ImagePattern(imageLogo));
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
        if (validateInputs(usernameField, passwordField)) {
            logInUiController.startBackGroundLogin(usernameField, passwordField,
                    logInUiController.progressIndicator,logInJFXButton,forgetPasswordJFXButton);
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

    private boolean validateInputs(TextField usernameField, PasswordField passwordField) {
        if (usernameField.getText().isEmpty()) {
            usernameField.requestFocus();
            return false;
        } else if (passwordField.getText().isEmpty()) {
            passwordField.requestFocus();
            return false;
        } else return true;
    }

    public void closeDrawer(ActionEvent actionEvent) {
        JFXButton button= (JFXButton) actionEvent.getSource();
        JFXDrawer  parent = (JFXDrawer) button.getParent().getParent().getParent().getParent();
        if (parent.isShown())parent.close();
    }
}
