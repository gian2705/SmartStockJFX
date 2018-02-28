package fahamu.Ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXSpinner;
import fahamu.dataFactory.LogInStageData;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LogInUiController extends BaseUIComponents {


    private final String ADMIN = "admin";
    //for test only
    private boolean isFirstTimeCashier = true;
    private SalesCategoryUI salesCategoryUICashier;

    public JFXDrawersStack drawerStack;
    public JFXDrawer leftDrawer;
    public JFXDrawer rightDrawer;
    public JFXButton testDrawer;
    public Rectangle rectangleImage;
    public JFXSpinner progressIndicator;
    public StackPane parentStackPane;


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
        Image brulImage = new Image(getResourceAsUrl("res/image/calculate.jpg").toString());
        Image signUP = new Image(getResourceAsUrl("res/image/googleSign.png").toString());

        rectangleImage.setFill(new ImagePattern(brulImage));

        try {

            leftDrawer.setSidePane((VBox)FXMLLoader
                    .load(getResourceAsUrl("res/fxmls/logInLeftDrawerContents.fxml")));
            leftDrawer.setOverLayVisible(false);
            leftDrawer.setResizableOnDrag(true);

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
        task.setOnSucceeded(event -> {
            changeScene(task, (Stage) parentStackPane.getScene().getWindow(),logInJFXButton,forgetPasswordJFXButton);
        });

        task.setOnFailed(event -> {
            enableButtons(new JFXButton[]{logInJFXButton, forgetPasswordJFXButton});
            disableProgressIndicator(progressIndicator);
        });
        task.setOnCancelled(event -> {
            enableButtons(new JFXButton[]{logInJFXButton, forgetPasswordJFXButton});
            disableProgressIndicator(progressIndicator);
            alertCreator("Error", "Trouble log In",
                    "Username is not available or password is incorrect\n" +
                            "Check your credential and try again", parentStackPane,
                    new ImageView(getResourceAsUrl("/res/image/manicon.png").toExternalForm()));
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
        }catch (Throwable ignore){ }
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
        }catch (Throwable ignore){

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
            enableButtons(new JFXButton[]{logInJFXButton,reset });
            disableProgressIndicator(progressIndicator);
            Parent pane = null;
            try {
                pane = FXMLLoader.load(getResourceAsUrl("res/fxmls/sellerUi.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setResizable(true);
            stage.setScene(new Scene(pane));
            stage.sizeToScene();
            stage.centerOnScreen();
        }
    }

    public void openDrawer(MouseEvent mouseEvent) {
        drawerStack.toggle(leftDrawer);
        leftDrawer.toFront();
    }
}

