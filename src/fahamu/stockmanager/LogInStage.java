package fahamu.stockmanager;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;


public class LogInStage extends Application {

    private static VBox rootLoginStage;
    private boolean isFirstTimeAdmin = true;
    private boolean isFirstTimeCashier = true;
    private SalesCategoryUI salesCategoryUIAdmin;
    private SalesCategoryUI salesCategoryUICashier;

    static Stage stageLogIn;
    static String currentUserName;
    static String password;
    static String serverAddress;
    static String username;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stageLogIn = primaryStage;
        primaryStage.setTitle("LB Pharmacy");

        //get server credential details
        // server credential object initialize the has map which contain server
        //detail for login. the constructor the path of the encrypted file.
        String path = "";
        if (System.getProperty("os.name").equals("Linux")) {
            path = "/usr/bin/Lb/serverCredential.db.encrypted";
        } else {
            //implement window file location

        }

        //get the server credential just before show login interface
        ServerCredential serverCredential = new ServerCredential(path);
        username = serverCredential.serverDetail.get("username");
        password = serverCredential.serverDetail.get("password");
        serverAddress = serverCredential.serverDetail.get("serverAddr");
        //set contents of login stage
        setLogInUI();

        //set scene
        Scene scene = new Scene(rootLoginStage, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        //perform a test to see if a server is reachable
        checkServerReachable();

        primaryStage.show();

    }

    private void setLogInUI() {

        rootLoginStage = new VBox();
        rootLoginStage.setAlignment(Pos.TOP_CENTER);
        rootLoginStage.setPadding(new Insets(16, 4, 4, 16));

        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");

        usernameLabel.setFont(new Font(14));
        passwordLabel.setFont(new Font(14));

        TextField usernameTextField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button logInButton = new Button("SIGN IN");
        Button resetPassword = new Button("Reset Password");
        logInButton.setStyle("-fx-base: green");
        resetPassword.setStyle("-fx-base: green");
        logInButton.setVisible(false);
        resetPassword.setVisible(false);

        Circle circle = new Circle(55);
        circle.setStyle("-fx-background-color: #000000");

        Arc arc = new Arc();
        arc.setStartAngle(0f);
        arc.setLength(180f);
        arc.setType(ArcType.ROUND);
        arc.setRadiusX(15);
        arc.setRadiusY(15);

        VBox inputControl = new VBox();
        inputControl.setSpacing(10);
        inputControl.setPadding(new Insets(20, 20, 30, 20));

        inputControl.getChildren().addAll(
                usernameLabel,
                usernameTextField,
                passwordLabel,
                passwordField
        );

        rootLoginStage.getChildren().addAll(circle, arc, inputControl, logInButton, resetPassword);

        usernameTextField.setOnMouseClicked(event -> {
            resetPassword.setVisible(false);
            logInButton.setVisible(false);
        });

        usernameTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (usernameTextField.getText().isEmpty()) {
                        usernameTextField.requestFocus();
                    } else passwordField.requestFocus();
                }
            }
        });

        passwordField.setOnMouseClicked(event -> {
            if (usernameTextField.getText().isEmpty()) {
                usernameTextField.requestFocus();
                resetPassword.setVisible(false);
                logInButton.setVisible(false);
            } else {
                resetPassword.setVisible(false);
                logInButton.setVisible(true);
            }
        });

        passwordField.setOnKeyReleased(event -> {
            resetPassword.setVisible(false);
            if (event.getCode() == KeyCode.BACK_SPACE) {
                if (passwordField.getText().isEmpty()) {
                    logInButton.setVisible(false);
                }
            } else {
                if (usernameTextField.getText().isEmpty()) usernameTextField.requestFocus();
                else logInButton.setVisible(true);
            }

            //login if its enter
            switch (event.getCode()) {
                case ENTER: {
                    if (usernameTextField.getText().isEmpty()) {

                        usernameTextField.requestFocus();
                        resetPassword.setVisible(false);
                        logInButton.setVisible(false);

                    } else if (passwordField.getText().isEmpty()) {

                        passwordField.requestFocus();
                        resetPassword.setVisible(false);
                        logInButton.setVisible(false);

                    } else {

                        String username = usernameTextField.getText();
                        String password = passwordField.getText();
                        //if authentication is successful
                        if (password.equals(LogInStageData.authenticateUser(username))) {

                            //for login of admin
                            if (LogInStageData.getUserType(username).equals("admin")) {
                                //get current user
                                currentUserName = usernameTextField.getText();

                                usernameTextField.clear();
                                passwordField.clear();

                                //hide login page
                                logInButton.setVisible(false);
                                resetPassword.setVisible(false);
                                stageLogIn.hide();

                                //call admin uer interface
                                if (isFirstTimeAdmin) {
                                    //set the objects
                                    salesCategoryUIAdmin = new SalesCategoryUI(true);
                                    new MainStage(MainStage.ADMIN_UI, salesCategoryUIAdmin);
                                    new StockCategoryUI();
                                    new ExpenditureCategoryUI();

                                    isFirstTimeAdmin = false;
                                }

                                //update total tra sales
                                float traSaleTotal = SaleCategoryData.getTotalTraSaleOfDay();
                                float traCashierSales = SaleCategoryData
                                        .getTotalTraSaleOfDayOfCashier(LogInStage.currentUserName);
                                salesCategoryUIAdmin
                                        .totalTraSale.setText(NumberFormat.getInstance().format(traSaleTotal));
                                salesCategoryUIAdmin
                                        .totalUserTraSales
                                        .setText(NumberFormat.getInstance().format(traCashierSales));

                                //update tables of all sale of the day for the specific user
                                salesCategoryUIAdmin.tableViewSaleTraOfDay
                                        .setItems(SaleCategoryData.getCashTraSaleOfDay(username));
                                salesCategoryUIAdmin.tableViewSalesOfDay
                                        .setItems(SaleCategoryData.getCashSaleOfDay(username));

                                //call admin stage
                                MainStage.stageAdmin.setTitle("Lb Pharmacy-" + LogInStage.currentUserName);
                                MainStage.stageAdmin.show();


                            } else {
                                //get current user
                                currentUserName = usernameTextField.getText();

                                usernameTextField.clear();
                                passwordField.clear();

                                //hide login page
                                logInButton.setVisible(false);
                                resetPassword.setVisible(false);
                                stageLogIn.hide();

                                if (isFirstTimeCashier) {
                                    //set objects
                                    salesCategoryUICashier = new SalesCategoryUI(false);
                                    new MainStage(MainStage.CASHIER_UI, salesCategoryUICashier);

                                    isFirstTimeCashier = false;
                                }

                                //update total tra sales
                                float traSaleTotal = SaleCategoryData.getTotalTraSaleOfDay();
                                float traCashierSales = SaleCategoryData
                                        .getTotalTraSaleOfDayOfCashier(LogInStage.currentUserName);
                                salesCategoryUICashier
                                        .totalTraSale.setText(NumberFormat.getInstance().format(traSaleTotal));
                                salesCategoryUICashier
                                        .totalUserTraSales
                                        .setText(NumberFormat.getInstance().format(traCashierSales));

                                //update tables of all sale of the day for the specific user
                                salesCategoryUICashier.tableViewSaleTraOfDay
                                        .setItems(SaleCategoryData.getCashTraSaleOfDay(username));
                                salesCategoryUICashier.tableViewSalesOfDay
                                        .setItems(SaleCategoryData.getCashSaleOfDay(username));

                                //call the user interface
                                MainStage.stageUser.setTitle("Lb Pharmacy-" + LogInStage.currentUserName);
                                MainStage.stageUser.show();

                                //some keyboard shortcut
                                salesCategoryUICashier.submitCashBill.getScene().getAccelerators().put(
                                        new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN),
                                        salesCategoryUICashier::setSubmitCashBill
                                );
                                salesCategoryUICashier.traCheckButton.getScene().getAccelerators().put(
                                        new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN),
                                        () -> {
                                            if (salesCategoryUICashier.traCheckButton.isSelected()) {
                                                salesCategoryUICashier.traCheckButton.setSelected(false);
                                                salesCategoryUICashier.listBillTable.setStyle("-fx-base: #efeded");
                                            } else {
                                                salesCategoryUICashier.traCheckButton.setSelected(true);
                                                salesCategoryUICashier.listBillTable.setStyle("-fx-base: #00ff00");
                                            }
                                        }
                                );
                                salesCategoryUICashier.wholeSaleCheckBox.getScene().getAccelerators().put(
                                        new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN),
                                        () -> {
                                            if (salesCategoryUICashier.wholeSaleCheckBox.isSelected()) {
                                                salesCategoryUICashier.wholeSaleCheckBox.setSelected(false);
                                                float price = StockCategoryData.sellPrice;
                                                try {
                                                    float product = (price * (Integer.parseInt(salesCategoryUICashier.
                                                            quantityTextField.getText())))
                                                            - Integer.parseInt(salesCategoryUICashier.discountTextField.getText());
                                                    //format number for accountant
                                                    String value = NumberFormat.getInstance().format(product);
                                                    salesCategoryUICashier.amountTextField.setText(value);
                                                } catch (Throwable throwable) {
                                                    salesCategoryUICashier.amountTextField.setText(String.valueOf(0));
                                                }
                                            } else {
                                                salesCategoryUICashier.wholeSaleCheckBox.setSelected(true);
                                                if (salesCategoryUICashier.quantityTextField.getText().isEmpty()) {
                                                    salesCategoryUICashier.amountTextField.setText(String.valueOf(0));
                                                } else {
                                                    int quant = Integer.parseInt(salesCategoryUICashier.quantityTextField.getText());
                                                    float wPrice = StockCategoryData.wSellPrice;
                                                    try {
                                                        float total = (wPrice * quant)
                                                                - Integer.parseInt(salesCategoryUICashier.discountTextField.getText());
                                                        salesCategoryUICashier.amountTextField
                                                                .setText(NumberFormat.getInstance().format(total));

                                                    } catch (Throwable q) {
                                                        salesCategoryUICashier.amountTextField.setText(String.valueOf(0));
                                                    }
                                                }
                                            }
                                        }
                                );


                            }
                        } else {
                            passwordField.requestFocus();
                            logInButton.setVisible(false);
                            resetPassword.setVisible(true);
                        }

                    }
                }
            }
        });

        logInButton.setOnAction(event -> {
            if (usernameTextField.getText().isEmpty()) {

                usernameTextField.requestFocus();
                resetPassword.setVisible(false);
                logInButton.setVisible(false);

            } else if (passwordField.getText().isEmpty()) {

                passwordField.requestFocus();
                resetPassword.setVisible(false);
                logInButton.setVisible(false);

            } else {

                String username = usernameTextField.getText();
                String password = passwordField.getText();
                //if authentication is successful
                if (password.equals(LogInStageData.authenticateUser(username))) {

                    //for login of admin
                    if (LogInStageData.getUserType(username).equals("admin")) {
                        //get current user
                        currentUserName = usernameTextField.getText();

                        usernameTextField.clear();
                        passwordField.clear();

                        //hide login page
                        logInButton.setVisible(false);
                        resetPassword.setVisible(false);
                        stageLogIn.hide();

                        //call admin uer interface
                        if (isFirstTimeAdmin) {
                            //set the objects
                            salesCategoryUIAdmin = new SalesCategoryUI(true);
                            new MainStage(MainStage.ADMIN_UI, salesCategoryUIAdmin);

                            new StockCategoryUI();
                            new ExpenditureCategoryUI();

                            isFirstTimeAdmin = false;
                        }

                        //update total tra sales
                        float traSaleTotal = SaleCategoryData.getTotalTraSaleOfDay();
                        float traCashierSales = SaleCategoryData
                                .getTotalTraSaleOfDayOfCashier(LogInStage.currentUserName);
                        salesCategoryUIAdmin
                                .totalTraSale.setText(NumberFormat.getInstance().format(traSaleTotal));
                        salesCategoryUIAdmin
                                .totalUserTraSales.setText(NumberFormat.getInstance().format(traCashierSales));

                        //update tables of all sale of the day for the specific user
                        salesCategoryUIAdmin.tableViewSaleTraOfDay
                                .setItems(SaleCategoryData.getCashTraSaleOfDay(username));
                        salesCategoryUIAdmin.tableViewSalesOfDay
                                .setItems(SaleCategoryData.getCashSaleOfDay(username));

                        //call admin stage
                        MainStage.stageAdmin.setTitle("Lb Pharmacy-" + LogInStage.currentUserName);
                        MainStage.stageAdmin.show();

                    } else {
                        //get current user
                        currentUserName = usernameTextField.getText();

                        usernameTextField.clear();
                        passwordField.clear();

                        //hide login page
                        logInButton.setVisible(false);
                        resetPassword.setVisible(false);
                        stageLogIn.hide();

                        if (isFirstTimeCashier) {
                            //set objects
                            salesCategoryUICashier = new SalesCategoryUI(false);
                            new MainStage(MainStage.CASHIER_UI, salesCategoryUICashier);

                            isFirstTimeCashier = false;
                        }

                        //update total tra sales
                        float traSaleTotal = SaleCategoryData.getTotalTraSaleOfDay();
                        float traCashierSales = SaleCategoryData
                                .getTotalTraSaleOfDayOfCashier(LogInStage.currentUserName);
                        salesCategoryUICashier
                                .totalTraSale.setText(NumberFormat.getInstance().format(traSaleTotal));
                        salesCategoryUICashier
                                .totalUserTraSales.setText(NumberFormat.getInstance().format(traCashierSales));

                        //update tables of all sale of the day for the specific user
                        salesCategoryUICashier.tableViewSaleTraOfDay
                                .setItems(SaleCategoryData.getCashTraSaleOfDay(username));
                        salesCategoryUICashier.tableViewSalesOfDay
                                .setItems(SaleCategoryData.getCashSaleOfDay(username));

                        //call the user interface
                        MainStage.stageUser.setTitle("Lb Pharmacy-" + LogInStage.currentUserName);
                        MainStage.stageUser.show();

                        //set shortcut
                        salesCategoryUICashier.submitCashBill.getScene().getAccelerators().put(
                                new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN),
                                salesCategoryUICashier::setSubmitCashBill
                        );
                        salesCategoryUICashier.traCheckButton.getScene().getAccelerators().put(
                                new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN),
                                () -> {
                                    if (salesCategoryUICashier.traCheckButton.isSelected()) {
                                        salesCategoryUICashier.traCheckButton.setSelected(false);
                                        salesCategoryUICashier.listBillTable.setStyle("-fx-base: #efeded");
                                    } else {
                                        salesCategoryUICashier.traCheckButton.setSelected(true);
                                        salesCategoryUICashier.listBillTable.setStyle("-fx-base: #00ff00");
                                    }
                                }
                        );
                        salesCategoryUICashier.wholeSaleCheckBox.getScene().getAccelerators().put(
                                new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN),
                                () -> {
                                    if (salesCategoryUICashier.wholeSaleCheckBox.isSelected()) {
                                        salesCategoryUICashier.wholeSaleCheckBox.setSelected(false);
                                        float price = StockCategoryData.sellPrice;
                                        try {
                                            float product = (price * (Integer.parseInt(salesCategoryUICashier.
                                                    quantityTextField.getText())))
                                                    - Integer.parseInt(salesCategoryUICashier.discountTextField.getText());
                                            //format number for accountant
                                            String value = NumberFormat.getInstance().format(product);
                                            salesCategoryUICashier.amountTextField.setText(value);
                                        } catch (Throwable throwable) {
                                            salesCategoryUICashier.amountTextField.setText(String.valueOf(0));
                                        }
                                    } else {
                                        salesCategoryUICashier.wholeSaleCheckBox.setSelected(true);
                                        if (salesCategoryUICashier.quantityTextField.getText().isEmpty()) {
                                            salesCategoryUICashier.amountTextField.setText(String.valueOf(0));
                                        } else {
                                            int quant = Integer.parseInt(salesCategoryUICashier.quantityTextField.getText());
                                            float wPrice = StockCategoryData.wSellPrice;
                                            try {
                                                float total = (wPrice * quant)
                                                        - Integer.parseInt(salesCategoryUICashier.discountTextField.getText());
                                                salesCategoryUICashier.amountTextField
                                                        .setText(NumberFormat.getInstance().format(total));

                                            } catch (Throwable q) {
                                                salesCategoryUICashier.amountTextField.setText(String.valueOf(0));
                                            }
                                        }
                                    }
                                }
                        );

                    }
                } else {
                    passwordField.requestFocus();
                    logInButton.setVisible(false);
                    resetPassword.setVisible(true);
                }
            }
        });

        //reset button clicked
        resetPassword.setOnAction(event -> {
            Popup popup = new Popup();
            GridPane ribbonPane = new GridPane();
            ribbonPane.setPadding(new Insets(6, 6, 6, 6));
            ribbonPane.setStyle("-fx-background-color: green");

            Label message = new Label("CONTACT LB PHARMACY");
            message.setStyle("-fx-background-color: white");
            message.setPadding(new Insets(4, 4, 4, 4));
            message.setFont(new Font(14));

            ribbonPane.getChildren().add(message);

            popup.getContent().add(ribbonPane);
            popup.setAutoHide(true);
            popup.show(stageLogIn);
        });

    }

    private void checkServerReachable() {
        try {
            InetAddress inetAddress = InetAddress.getByAddress(new byte[]{10, 42, 0, 1});
            boolean reachable = inetAddress.isReachable(1000);
            if (!reachable) {
                //boolean check for availability of local server
                boolean isLocalServerAvailable = false;
                //check availability of local databases
                MysqlDataSource mysqlDataSource = new MysqlDataSource();
                mysqlDataSource.setUser(username);
                mysqlDataSource.setPassword(password);
                mysqlDataSource.setServerName("localhost");

                Connection connection = null;
                try {
                    connection = mysqlDataSource.getConnection();

                    //if connection success, change a server address to local
                    isLocalServerAvailable = true;
                    serverAddress = "localhost";

                } catch (SQLException e) {
                    isLocalServerAvailable = false;
                    e.printStackTrace();
                } finally {
                    if (connection != null) try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (!isLocalServerAvailable) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Database Server is not reachable.");
                    alert.showAndWait();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);

    }

}
