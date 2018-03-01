package fahamu.Ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.events.JFXDrawerEvent;
import fahamu.dataFactory.LogInStageData;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LogInUiController extends BaseUIComponents {

    public JFXDrawersStack drawerStack;
    public JFXDrawer leftDrawer;
    public JFXDrawer rightDrawer;
    public JFXSpinner progressIndicator;
    public StackPane parentStackPane;

    //for test only
    private boolean isFirstTimeCashier = true;
    private SalesCategoryUI salesCategoryUICashier;


    @FXML
    public void initialize() {
//        FlowPane content = new FlowPane();
//        JFXButton leftButton = new JFXButton(LEFT);
//        JFXButton topButton = new JFXButton(TOP);
//        JFXButton rightButton = new JFXButton(RIGHT);
//        JFXButton bottomButton = new JFXButton(BOTTOM);
//        content.getChildren().addAll(leftButton, topButton, rightButton, bottomButton);
//        content.setMaxSize(200, 200);
//
//
//        JFXDrawer leftDrawer = new JFXDrawer();
//        StackPane leftDrawerPane = new StackPane();
//        leftDrawerPane.getStyleClass().add("red-400");
//        leftDrawerPane.getChildren().add(new JFXButton("Left Content"));
//        leftDrawer.setSidePane(leftDrawerPane);
//        leftDrawer.setDefaultDrawerSize(150);
//        leftDrawer.setResizeContent(true);
//        leftDrawer.setOverLayVisible(false);
//        leftDrawer.setResizableOnDrag(true);
//
//
//        JFXDrawer bottomDrawer = new JFXDrawer();
//        StackPane bottomDrawerPane = new StackPane();
//        bottomDrawerPane.getStyleClass().add("deep-purple-400");
//        bottomDrawerPane.getChildren().add(new JFXButton("Bottom Content"));
//        bottomDrawer.setDefaultDrawerSize(150);
//        bottomDrawer.setDirection(JFXDrawer.DrawerDirection.BOTTOM);
//        bottomDrawer.setSidePane(bottomDrawerPane);
//        bottomDrawer.setResizeContent(true);
//        bottomDrawer.setOverLayVisible(false);
//        bottomDrawer.setResizableOnDrag(true);
//
//
//        JFXDrawer rightDrawer = new JFXDrawer();
//        StackPane rightDrawerPane = new StackPane();
//        rightDrawerPane.getStyleClass().add("blue-400");
//        rightDrawerPane.getChildren().add(new JFXButton("Right Content"));
//        rightDrawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
//        rightDrawer.setDefaultDrawerSize(150);
//        rightDrawer.setSidePane(rightDrawerPane);
//        rightDrawer.setOverLayVisible(false);
//        rightDrawer.setResizableOnDrag(true);
//
//
//        JFXDrawer topDrawer = new JFXDrawer();
//        StackPane topDrawerPane = new StackPane();
//        topDrawerPane.getStyleClass().add("green-400");
//        topDrawerPane.getChildren().add(new JFXButton("Top Content"));
//        topDrawer.setDirection(JFXDrawer.DrawerDirection.TOP);
//        topDrawer.setDefaultDrawerSize(150);
//        topDrawer.setSidePane(topDrawerPane);
//
//
//        drawerStack.setContent(content);
//        leftDrawer.setId(LEFT);
//        rightDrawer.setId(RIGHT);
//        bottomDrawer.setId(BOTTOM);
//        topDrawer.setId(TOP);
//
//        leftButton.addEventHandler(MOUSE_PRESSED, e ->drawerStack.toggle(leftDrawer));
//        bottomButton.addEventHandler(MOUSE_PRESSED, e ->drawerStack .toggle(bottomDrawer));
//        rightButton.addEventHandler(MOUSE_PRESSED, e -> drawerStack.toggle(rightDrawer));
//        topButton.addEventHandler(MOUSE_PRESSED, e -> drawerStack.toggle(topDrawer));

        // to be moved to constructor
        try {
            assert LOG_IN_DRAWER_STACK_PANE_CONTENT != null;
            drawerStack.setContent(FXMLLoader.load(LOG_IN_DRAWER_STACK_PANE_CONTENT));
            assert LOG_IN_BANNER_IMAGE != null;
            parentStackPane.setStyle("-fx-background-image: url(" + BACKGROUND_IMAGE_PATH + ");");
            assert LEFT_DRAWER_LAYOUT_FXML != null;
            leftDrawer.setSidePane((VBox) FXMLLoader.load(LEFT_DRAWER_LAYOUT_FXML));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startBackGroundLogin(TextField username,
                                     PasswordField password,
                                     ProgressIndicator p,
                                     JFXButton logInJFXButton,
                                     JFXButton forgetPasswordJFXButton) {
        //disable buttons and show progress indicator
        disableButtons(new JFXButton[]{logInJFXButton, forgetPasswordJFXButton});
        enableProgressIndicator(progressIndicator);

        Task<String> task = new Task<>() {
            @Override
            protected String call() {
                //updateProgress(-1F, 1);
                //authenticate user
                String user = authenticateUser(username.getText(), password.getText());
                if (user == null) {
                    cancel();
                    return null;
                } else {
                    return user;
                }
            }
        };
        task.setOnSucceeded(event ->
                changeScene(task,
                        (Stage) logInJFXButton.getScene().getWindow(),
                        logInJFXButton, forgetPasswordJFXButton));

        task.setOnFailed(event -> {
            enableButtons(new JFXButton[]{logInJFXButton, forgetPasswordJFXButton});
            disableProgressIndicator(progressIndicator);
        });
        task.setOnCancelled(event -> {
            enableButtons(new JFXButton[]{logInJFXButton, forgetPasswordJFXButton});
            disableProgressIndicator(progressIndicator);

            assert WRONG_PASSWORD_ICON != null;
            alertCreator("Error", "Trouble log In",
                    "Username is not available or password is incorrect\n" +
                            "Check your credential and try again", logInJFXButton.getScene().getRoot(),
                    new ImageView(new Image(WRONG_PASSWORD_ICON.toExternalForm())));
            password.clear();
        });
        //p.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private String authenticateUser(String username, String password) {
        //check if password is correct
        try {
            if (password.equals(LogInStageData.authenticateUser(username))) {
                //check type of u
                String CASHIER = "cashier";
                if (LogInStageData.getUserType(username).equals(ADMIN)) {
                    //TODO: call admin scene
                    return ADMIN;
                } else return CASHIER;
            }
        } catch (SQLException e) {
            //TODO: to print some error message in password or a any snack bar
            e.printStackTrace();
        }
        return null;
    }

    private void enableProgressIndicator(ProgressIndicator p) {
        try {
            p.setVisible(true);
        } catch (Throwable ignore) {
        }
    }

    private void disableButtons(JFXButton[] buttons) {
        for (JFXButton bu :
                buttons) {
            bu.setDisable(true);
        }
    }

    private void disableProgressIndicator(ProgressIndicator p) {
        try {

            p.setVisible(false);
        } catch (Throwable ignore) {

        }
    }

    private void enableButtons(JFXButton[] buttons) {
        for (JFXButton bu :
                buttons) {
            bu.setDisable(false);
        }
    }

    private void changeScene(Task<String> task,
                             Stage stage,
                             JFXButton logInJFXButton,
                             JFXButton reset) {
        if (task.getValue().equals(ADMIN)) {
            //for admin user interface
            //TODO: to create admin scene

            //for testing only
            if (isFirstTimeCashier) {
                //set objects

                salesCategoryUICashier = new SalesCategoryUI(false);
                Platform.runLater(() -> {
                    new MainStage(MainStage.ADMIN_UI, salesCategoryUICashier);
                    MainStage.stageAdmin.show();
                    stage.close();
                });
                isFirstTimeCashier = false;
            }
        } else {
            //TODO: to create a cashier scene

            try {
                enableButtons(new JFXButton[]{logInJFXButton, reset});
                disableProgressIndicator(progressIndicator);
                stage.setResizable(true);
                assert SALE_UI_LAYOUT_FXML != null;
                stage.setScene(new Scene(FXMLLoader.load(SALE_UI_LAYOUT_FXML)));
                stage.sizeToScene();
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void translateBannersRight(JFXDrawerEvent jfxDrawerEvent) {
        JFXDrawersStack parent= (JFXDrawersStack) ((JFXDrawer)jfxDrawerEvent.getSource()).getParent();
        FlowPane content = (FlowPane) parent.getContent();
        content.setTranslateX(150f);
    }

    public void translateBannersCenter(JFXDrawerEvent jfxDrawerEvent) {
        JFXDrawersStack parent= (JFXDrawersStack) ((JFXDrawer)jfxDrawerEvent.getSource()).getParent();
        FlowPane content = (FlowPane) parent.getContent();
        content.setTranslateX(0f);
    }

    public void translateBannersLeft(JFXDrawerEvent jfxDrawerEvent) {
        JFXDrawersStack parent= (JFXDrawersStack) ((JFXDrawer)jfxDrawerEvent.getSource()).getParent();
        FlowPane content = (FlowPane) parent.getContent();
        content.setTranslateX(-150f);
    }
}

