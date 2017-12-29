package fahamu.UserInteface;

import com.mysql.cj.jdbc.MysqlDataSource;
import fahamu.dataFactory.LogInStageData;
import fahamu.dataFactory.SaleCategoryData;
import fahamu.dataFactory.ServerCredentialFactory;
import fahamu.dataFactory.StockCategoryData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;


public class LogInStage extends Application {

    //*********************************************//
    //private fields                               //
    //*********************************************//
    private VBox rootLoginStage;
    private BorderPane rootLogInProgress;
    private boolean isFirstTimeAdmin = true;
    private boolean isFirstTimeCashier = true;
    private SalesCategoryUI salesCategoryUIAdmin;
    private SalesCategoryUI salesCategoryUICashier;
    private ProgressIndicator progressIndicatorLogIn = new ProgressIndicator();
    private PasswordField passwordField;
    private TextField usernameTextField;
    private Button resetPassword = new Button("Reset Password");
    private Button logInButton = new Button("Login");

    //**************************************************//
    //package private fields                            //
    //**************************************************//
    private Scene sceneMain;
    private Scene sceneLoginProgress;
    static Stage stageLogIn;
    static String currentUserName;

    //************************************************//
    //public fields                                   //
    //************************************************//
    public static String password;
    public static String serverAddress;
    public static String username;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stageLogIn = primaryStage;
        stageLogIn.setAlwaysOnTop(true);
        stageLogIn.initStyle(StageStyle.UTILITY);
        primaryStage.setTitle("LB Pharmacy");

        //get server credential details
        // server credential object initialize the has map which contain server
        //detail for login. the constructor the path of the encrypted file.
        String path = null;
        try {
            path = LogInStage.class.getClass().getResource("data/serverCredential.db.encrypted").toExternalForm();
            getServerCredential(path);
            initializeLoginStage(primaryStage);

        } catch (Throwable e) {
            if (path == null) {
                initializeLoginStage(primaryStage);
            }
        }


    }

    private void getServerCredential(String path) {
        //get the server credential just before show login interface
        ServerCredentialFactory serverCredentialFactory = new ServerCredentialFactory(path);
        username = serverCredentialFactory.serverDetail.get("username");
        password = serverCredentialFactory.serverDetail.get("password");
        serverAddress = serverCredentialFactory.serverDetail.get("serverAddress");
    }

    private void initializeLoginStage(Stage primaryStage) {

        //set contents of login stage
        setLogInUI();

        //set scene
        sceneMain = new Scene(rootLoginStage, 300, 400);
        String css = this.getClass().getResource("data/style.css").toExternalForm();
        sceneLoginProgress = new Scene(rootLogInProgress, 550, 300);
        rootLogInProgress.setId("pane");
        sceneLoginProgress.getStylesheets().add(css);
        primaryStage.setScene(sceneMain);
        primaryStage.setResizable(false);

        //perform a test to see if a server is reachable
        checkServerReachable(new byte[]{(byte) 192, (byte) 168, 0, 2});

        primaryStage.show();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

    }

    private void setLogInUI() {

        rootLoginStage = new VBox();
        rootLogInProgress = new BorderPane();
        rootLogInProgress.setCenter(progressIndicatorLogIn);
        progressIndicatorLogIn.setProgress(1);
        rootLoginStage.setAlignment(Pos.TOP_CENTER);
        rootLoginStage.setPadding(new Insets(16, 4, 4, 16));

        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");

        usernameLabel.setFont(new Font(14));
        passwordLabel.setFont(new Font(14));

        usernameTextField = new TextField();
        passwordField = new PasswordField();

        logInButton.setStyle("-fx-base: green");
        resetPassword.setStyle("-fx-base: green");
        logInButton.setVisible(false);
        resetPassword.setVisible(false);

        Image image = new Image(this.getClass().getResource("data/lbLogo.jpg").toExternalForm());
        Rectangle rectangle = new Rectangle(200, 200);
        rectangle.setFill(new ImagePattern(image));
        rectangle.setArcWidth(200);
        rectangle.setArcHeight(200);

        VBox inputControl = new VBox();
        inputControl.setSpacing(5);
        inputControl.setPadding(new Insets(10, 20, 10, 20));

        inputControl.getChildren().addAll(
                usernameLabel,
                usernameTextField,
                passwordLabel,
                passwordField
        );

        rootLoginStage.getChildren().addAll(rectangle, inputControl, logInButton, resetPassword);

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
                if (usernameTextField.getText().isEmpty()) {
                    usernameTextField.requestFocus();
                    passwordField.clear();
                } else logInButton.setVisible(true);
            }

            //login if its enter
            switch (event.getCode()) {
                case ENTER: {
                    if (!passwordField.getText().isEmpty()) {
                        stageLogIn.setScene(sceneLoginProgress);
                        stageLogIn.centerOnScreen();
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                updateProgress(-1F, 1);
                                login();
                                updateProgress(1, 1);
                                return null;
                            }
                        };
                        task.setOnSucceeded(event1 -> stageLogIn.setScene(sceneMain));
                        progressIndicatorLogIn.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                    }
                }
            }
        });

        logInButton.setOnAction(event -> {
            stageLogIn.setScene(sceneLoginProgress);
            stageLogIn.centerOnScreen();
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateProgress(-1F, 1);
                    login();
                    updateProgress(1, 1);
                    return null;
                }
            };
            task.setOnSucceeded(event1 -> stageLogIn.setScene(sceneMain));
            progressIndicatorLogIn.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
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

    private boolean checkServerReachable(byte[] serverIp) {
        //this boolean check if the server is reachable
        boolean serverReachable;
        try {
            //
            InetAddress inetAddress = InetAddress.getByAddress(serverIp);
            boolean reachable = inetAddress.isReachable(1000);
            if (!reachable) {
                //boolean check for availability of local server
                boolean isLocalServerAvailable;
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
                    serverReachable = true;
                    serverAddress = "localhost";

                } catch (SQLException e) {
                    isLocalServerAvailable = false;
                    serverReachable = false;
                } finally {
                    if (connection != null) try {
                        connection.close();
                    } catch (SQLException ignore) {
                    }
                }

                if (!isLocalServerAvailable) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Server Is Not Reachable.\nCheck your connection");
                        stageLogIn.setIconified(true);
                        alert.showAndWait();
                        stageLogIn.setIconified(false);
                    });

                }
            } else serverReachable = true;
        } catch (IOException e) {
            serverReachable = false;
            e.printStackTrace();
        }

        return serverReachable;
    }

    private void login() {
        if (usernameTextField.getText().isEmpty()) {
            Platform.runLater(() -> {
                usernameTextField.requestFocus();
                resetPassword.setVisible(false);
                logInButton.setVisible(false);
            });

        } else if (passwordField.getText().isEmpty()) {
            Platform.runLater(() -> {
                passwordField.requestFocus();
                resetPassword.setVisible(false);
                logInButton.setVisible(false);
            });

        } else {

            //check for server reachable before login
            boolean reachable = checkServerReachable(new byte[]{(byte) 192, (byte) 168, 0, 2});
            if (reachable) {
                String username = usernameTextField.getText();
                String password = passwordField.getText();
                //if authentication is successful
                if (password.equals(LogInStageData.authenticateUser(username))) {
                    //for login of admin
                    if (LogInStageData.getUserType(usernameTextField.getText()).equals("admin")) {

                        //get current user
                        currentUserName = usernameTextField.getText();
                        //hide login page
                        Platform.runLater(() -> {
                            logInButton.setVisible(false);
                            resetPassword.setVisible(false);
                        });

                        //call admin uer interface
                        if (isFirstTimeAdmin) {
                            //set the objects
                            salesCategoryUIAdmin = new SalesCategoryUI(true);
                            Platform.runLater(() -> {
                                new MainStage(MainStage.ADMIN_UI, salesCategoryUIAdmin);
                                new StockCategoryUI();
                                new ExpenditureCategoryUI();
                                isFirstTimeAdmin = false;
                            });
                        }

                        //update total tra sales
                        Platform.runLater(() -> {
                            float traSaleTotal = SaleCategoryData.getTotalTraSaleOfDay();
                            float traCashierSales =
                                    SaleCategoryData.getTotalTraSaleOfDayOfCashier(LogInStage.currentUserName);
                            salesCategoryUIAdmin.totalTraSale.setText(NumberFormat.getInstance().format(traSaleTotal));
                            salesCategoryUIAdmin.totalUserTraSales.setText(NumberFormat.getInstance().
                                    format(traCashierSales));
                            //update tables of all sale of the day for the specific user
                            salesCategoryUIAdmin.tableViewSaleTraOfDay
                                    .setItems(SaleCategoryData.getCashTraSaleOfDay(username));
                            salesCategoryUIAdmin.tableViewSalesOfDay
                                    .setItems(SaleCategoryData.getCashSaleOfDay(username));

                            //call admin stage
                            MainStage.stageAdmin.setTitle("Lb Pharmacy-" + LogInStage.currentUserName);

                            stageLogIn.hide();
                            usernameTextField.clear();
                            passwordField.clear();
                            MainStage.stageAdmin.show();

                        });

                    } else {
                        //get current user
                        currentUserName = usernameTextField.getText();

                        //hide login page
                        Platform.runLater(() -> {
                            logInButton.setVisible(false);
                            resetPassword.setVisible(false);
                        });

                        if (isFirstTimeCashier) {
                            //set objects

                            salesCategoryUICashier = new SalesCategoryUI(false);
                            Platform.runLater(() -> new MainStage(MainStage.CASHIER_UI, salesCategoryUICashier));
                            isFirstTimeCashier = false;
                        }

                        //update total tra sales
                        Platform.runLater(() -> {
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
                            stageLogIn.hide();
                            usernameTextField.clear();
                            passwordField.clear();
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
                        });

                    }
                } else {
                    Platform.runLater(() -> {
                        passwordField.requestFocus();
                        logInButton.setVisible(false);
                        resetPassword.setVisible(true);
                    });
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
