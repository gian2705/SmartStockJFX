package fahamu.UserInteface;

import fahamu.dataFactory.BaseDataClass;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class Main extends Application {

    //public static String currentUserName;

    private String BUSINESS_NAME = "STOCK MANAGER"; //default name
    private Scene sceneMain;

    @Override
    public void init() {
        //TODO: initialize all user data when application ready to roll out
        BUSINESS_NAME = "Lb Pharmacy";
        byte[] serverIPv4Address = new byte[]{(byte) 192, (byte) 168, 0, 2};
        String serverCredentialFilePath = "data/serverCredential.db.encrypted";
        BaseDataClass baseDataClass=new BaseDataClass();

        //run in background service to check if server is reachable
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                baseDataClass.mysqlServerCheck(serverIPv4Address,serverCredentialFilePath);
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            baseDataClass.showDataBaseErrorDialog(new BaseUIComponents(), (StackPane) sceneMain.getRoot());
        });
        new Thread(task).start();
    }

    @Override
    public void start(Stage primaryStage) {
        //set scene
        URL location = Main.this.getClass().getResource("fxmls/loginStage.fxml");
        FXMLLoader loader = new FXMLLoader(location);
        try {
            sceneMain = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(sceneMain);
        primaryStage.setResizable(false);
        primaryStage.setTitle(BUSINESS_NAME);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /*
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
                                    SaleCategoryData.getTotalTraSaleOfDayOfCashier(Main.currentUserName);
                            salesCategoryUIAdmin.totalTraSale.setText(NumberFormat.getInstance().format(traSaleTotal));
                            salesCategoryUIAdmin.totalUserTraSales.setText(NumberFormat.getInstance().
                                    format(traCashierSales));
                            //update tables of all sale of the day for the specific user
                            salesCategoryUIAdmin.tableViewSaleTraOfDay
                                    .setItems(SaleCategoryData.getCashTraSaleOfDay(username));
                            salesCategoryUIAdmin.tableViewSalesOfDay
                                    .setItems(SaleCategoryData.getCashSaleOfDay(username));

                            //call admin stage
                            MainStage.stageAdmin.setTitle("Lb Pharmacy-" + Main.currentUserName);

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
                                    .getTotalTraSaleOfDayOfCashier(Main.currentUserName);
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
                            MainStage.stageUser.setTitle("Lb Pharmacy-" + Main.currentUserName);
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
    */
}
