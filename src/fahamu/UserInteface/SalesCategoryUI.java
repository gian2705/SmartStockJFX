package fahamu.UserInteface;

import fahamu.dataFactory.SaleCategoryData;
import fahamu.dataFactory.StockCategoryData;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Popup;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.Calendar;

public class SalesCategoryUI {

    /*
    package private field
     */
    TabPane tabPane;
    /*
    private fields
     */
    private SplitPane splitPane;
    private Button addToCartButton;
    private ComboBox<String> productNameChooser;
    private TextField receivedTextField;
    private TextField totalAmountTextField;
    private TextField changesTextField;
    private TextField invisibleTotalAmountTextField;
    Tab salesTableViewTab;
    Button submitCashBill;
    RadioButton traCheckButton;
    CheckBox wholeSaleCheckBox;
    TableView<CashSaleTableBill> listBillTable;
    TableView<CashSaleOfDay> tableViewSalesOfDay;
    TableView<CashTraSaleOfDay> tableViewSaleTraOfDay;
    TextField totalTraSale;
    TextField totalUserTraSales;
    TextField quantityTextField;
    TextField amountTextField;
    TextField discountTextField;

    SalesCategoryUI(boolean isAdmin) {

        cashSaleReports(isAdmin);

    }

    //the cash sale section
    GridPane setCashSaleUI() {
        //for admin
        GridPane cashSaleUI = new GridPane();
        cashSaleUI.setPadding(new Insets(10, 10, 10, 10));
        cashSaleUI.setId("cash_sale");
        RowConstraints row0 = new RowConstraints();
        ColumnConstraints column0 = new ColumnConstraints(300);
        ColumnConstraints column1 = new ColumnConstraints();
        row0.setVgrow(Priority.ALWAYS);
        row0.setFillHeight(true);
        column0.setHgrow(Priority.ALWAYS);
        column1.setHgrow(Priority.ALWAYS);
        column1.setFillWidth(true);
        cashSaleUI.getRowConstraints().add(row0);
        cashSaleUI.getColumnConstraints().addAll(column0, column1);

        setCashSaleDataInputPaneAdmin(cashSaleUI);

        return cashSaleUI;
    }

    private void setCashSaleDataInputPaneAdmin(GridPane cashSaleUI) {
        Label dataInputTitle = new Label("CASH SALE");
        Label productNameLabel = new Label("Product Name :");
        Label priceLabel = new Label("Price(TZS) :");
        Label wholeSaleLabel = new Label("WholeSale");
        Label quantityLabel = new Label("Quantity :");
        Label amountLabel = new Label("Amount(TZS) :");
        Label remainInStockLabel = new Label("Remain In Stock :");
        Label totalAmountLabel = new Label("Total Bill(TZS) :");
        Label shelfLabel = new Label("Shelf :");
        Label discountLabel = new Label("Discount(TZS) :");
        Label totalSale = new Label("Total Sale :");
        Label yourSale = new Label("Your Sale :");
        Label cashReceivedLabel = new Label("Received(TZS) :");
        Label changesLabel = new Label("Changes (TZS) :");

        addToCartButton = new Button("Add To Cart");
        Button removeFromCart = new Button("Remove Selection");
        Button cancelCashSaleButton = new Button("Cancel");
        submitCashBill = new Button("Submit Bill");
        Button refreshTraButton = new Button("Refresh");
        refreshTraButton.setDefaultButton(true);

        discountTextField = new TextField();
        receivedTextField = new TextField();
        TextField priceTextField = new TextField();
        quantityTextField = new TextField();
        amountTextField = new TextField();
        changesTextField = new TextField();
        totalAmountTextField = new TextField();
        TextField shelfTextField = new TextField();
        TextField remainInStockTextField = new TextField();
        invisibleTotalAmountTextField = new TextField();

        traCheckButton = new RadioButton();
        wholeSaleCheckBox = new CheckBox();
        wholeSaleCheckBox.setIndeterminate(false);
        wholeSaleCheckBox.setSelected(false);

        totalTraSale = new TextField();
        totalUserTraSales = new TextField();
        productNameChooser = new ComboBox<>(StockCategoryData.getProductNames());
        productNameChooser.autosize();

        discountTextField.setText(String.valueOf(0));
        receivedTextField.setText(String.valueOf(0));
        changesTextField.setText(String.valueOf(0));
        totalTraSale.setEditable(false);
        totalUserTraSales.setEditable(false);
        quantityTextField.setPromptText("Click me...");
        invisibleTotalAmountTextField.setText(String.valueOf(0));
        productNameChooser.promptTextProperty().setValue("choose product");

        dataInputTitle.setFont(new Font(16));
        productNameLabel.setFont(new Font(14));
        totalSale.setFont(new Font(14));
        yourSale.setFont(new Font(14));
        cashReceivedLabel.setFont(new Font(14));
        priceLabel.setFont(new Font(14));
        discountLabel.setFont(new Font(14));
        quantityLabel.setFont(new Font(14));
        amountLabel.setFont(new Font(14));
        remainInStockLabel.setFont(new Font(14));
        changesLabel.setFont(new Font(14));
        shelfLabel.setFont(new Font(14));
        remainInStockTextField.setFont(new Font(14));

        productNameChooser.setEditable(true);
        priceTextField.setEditable(false);
        amountTextField.setEditable(false);
        shelfTextField.setEditable(false);
        remainInStockTextField.setEditable(false);
        changesTextField.setEditable(false);
        remainInStockTextField.setStyle("-fx-background-color: #13a715");
        shelfTextField.setStyle("-fx-background-color: #13a715");
        priceTextField.setStyle("-fx-background-color: #13a715");
        amountTextField.setStyle("-fx-background-color: #13a715");
        totalAmountTextField.setStyle("-fx-background-color: #13a715");
        changesTextField.setStyle("-fx-background-color: #13a715");

        addToCartButton.setDefaultButton(true);
        submitCashBill.setDefaultButton(true);
        removeFromCart.setVisible(false);
        totalAmountTextField.setEditable(false);

        //total sale of the day table view
        //create table view sales of the day;
        tableViewSalesOfDay = new TableView<>();
        TableColumn<CashSaleOfDay, String> dateColumn = new TableColumn<>("Date");
        TableColumn<CashSaleOfDay, String> productColumn = new TableColumn<>("Product");
        TableColumn<CashSaleOfDay, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<CashSaleOfDay, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<CashSaleOfDay, Float> amountColumn = new TableColumn<>("Amount(TZS)");
        tableViewSalesOfDay.getColumns().addAll(
                dateColumn,
                productColumn,
                categoryColumn,
                quantityColumn,
                amountColumn);

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        dateColumn.setPrefWidth(100);
        productColumn.setPrefWidth(180);
        categoryColumn.setPrefWidth(80);
        amountColumn.setPrefWidth(100);
        tableViewSalesOfDay.autosize();

        //tra sales of the day table
        tableViewSaleTraOfDay = new TableView<>();
        tableViewSaleTraOfDay.setStyle("-fx-base: #00ff00");
        TableColumn<CashTraSaleOfDay, String> dateTraColumn = new TableColumn<>("Date");
        TableColumn<CashTraSaleOfDay, String> productTraColumn = new TableColumn<>("Product");
        TableColumn<CashTraSaleOfDay, String> categoryTraColumn = new TableColumn<>("Category");
        TableColumn<CashTraSaleOfDay, Integer> quantityTraColumn = new TableColumn<>("Quantity");
        TableColumn<CashTraSaleOfDay, Float> amountTraColumn = new TableColumn<>("Amount(TZS)");
        tableViewSaleTraOfDay.getColumns().addAll(
                dateTraColumn,
                productTraColumn,
                categoryTraColumn,
                quantityTraColumn,
                amountTraColumn);

        dateTraColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        productTraColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        categoryTraColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityTraColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        amountTraColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        dateTraColumn.setPrefWidth(100);
        productTraColumn.setPrefWidth(180);
        categoryTraColumn.setPrefWidth(80);
        amountTraColumn.setPrefWidth(100);
        tableViewSaleTraOfDay.autosize();

        int firstRow = 0;
        int secondColumn = 1;

        //put alert and sales of the day in the tab
        tabPane = new TabPane();

        salesTableViewTab = new Tab("All Cash Sales");
        Tab alertOfStockTabPane = new Tab("Reports");
        Tab traSalesTabPane = new Tab("Sales Of Day");

        tabPane.setPadding(new Insets(0, 0, 0, 6));
        alertOfStockTabPane.setClosable(false);
        traSalesTabPane.setClosable(false);


        salesTableViewTab.setContent(tableViewSalesOfDay);
        alertOfStockTabPane.setContent(splitPane);

        //vertical box for tra sales preview and total
        HBox hBox = new HBox();

        hBox.setPadding(new Insets(6, 0, 6, 0));
        hBox.setSpacing(8);
        hBox.getChildren().addAll(yourSale, totalUserTraSales, totalSale, totalTraSale, refreshTraButton);

        GridPane traSalesVBox = new GridPane();
        RowConstraints r0 = new RowConstraints(30);
        RowConstraints r1 = new RowConstraints();
        ColumnConstraints c0 = new ColumnConstraints();
        traSalesVBox.getColumnConstraints().addAll(c0);
        traSalesVBox.getRowConstraints().addAll(r0, r1);
        r1.setVgrow(Priority.ALWAYS);
        c0.setHgrow(Priority.ALWAYS);

        traSalesVBox.add(hBox, 0, 0);
        traSalesVBox.add(tableViewSaleTraOfDay, 0, 1);

        traSalesTabPane.setContent(traSalesVBox);

        tabPane.getTabs().addAll(
                traSalesTabPane,
                alertOfStockTabPane);
        cashSaleUI.add(tabPane, secondColumn, firstRow);

        //content of input control panel
        GridPane gridPaneDataInput = new GridPane();
        ColumnConstraints col0 = new ColumnConstraints(105);
        ColumnConstraints col1 = new ColumnConstraints();
        RowConstraints rw0 = new RowConstraints(30);
        RowConstraints rw1 = new RowConstraints(30);
        RowConstraints rw2 = new RowConstraints(30);
        RowConstraints rw3 = new RowConstraints(30);
        RowConstraints rw4 = new RowConstraints(30);
        RowConstraints rw5 = new RowConstraints(30);
        RowConstraints rw6 = new RowConstraints(30);
        RowConstraints rw7 = new RowConstraints(30);
        RowConstraints rw8 = new RowConstraints(30);
        RowConstraints rw9 = new RowConstraints(30);

        gridPaneDataInput.getColumnConstraints().addAll(col0, col1);
        gridPaneDataInput.getRowConstraints().addAll(rw0, rw1, rw2, rw3, rw4, rw5, rw6, rw7, rw8, rw9);
        gridPaneDataInput.add(dataInputTitle, 1, 0);
        gridPaneDataInput.add(productNameLabel, 0, 1);
        gridPaneDataInput.add(productNameChooser, 1, 1);
        gridPaneDataInput.add(shelfLabel, 0, 2);
        gridPaneDataInput.add(shelfTextField, 1, 2);
        gridPaneDataInput.add(remainInStockLabel, 0, 3);
        gridPaneDataInput.add(remainInStockTextField, 1, 3);
        gridPaneDataInput.add(priceLabel, 0, 4);
        gridPaneDataInput.add(priceTextField, 1, 4);
        gridPaneDataInput.add(wholeSaleLabel, 0, 5);
        gridPaneDataInput.add(wholeSaleCheckBox, 1, 5);
        gridPaneDataInput.add(quantityLabel, 0, 6);
        gridPaneDataInput.add(quantityTextField, 1, 6);
        gridPaneDataInput.add(discountLabel, 0, 7);
        gridPaneDataInput.add(discountTextField, 1, 7);
        gridPaneDataInput.add(amountLabel, 0, 8);
        gridPaneDataInput.add(amountTextField, 1, 8);
        gridPaneDataInput.add(addToCartButton, 0, 9);
        gridPaneDataInput.add(cancelCashSaleButton, 1, 9);


        //cart table view and bill table view
        listBillTable = new TableView<>();
        ObservableList<CashSaleTableBill> billData = FXCollections.observableArrayList();
        TableColumn<CashSaleTableBill, String> productBill = new TableColumn<>("Product");
        TableColumn<CashSaleTableBill, Integer> quantityBill = new TableColumn<>("Quantity");
        TableColumn<CashSaleTableBill, Integer> amountBill = new TableColumn<>("Amount(TZS)");
        productBill.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityBill.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        amountBill.setCellValueFactory(new PropertyValueFactory<>("amount"));
        productBill.setMinWidth(150);
        quantityBill.setPrefWidth(48);
        amountBill.setPrefWidth(78);
        listBillTable.autosize();
        listBillTable.getColumns().addAll(productBill, quantityBill, amountBill);

        VBox vBox = new VBox();
        HBox hBoxSubmit = new HBox();
        HBox hBoxTotalAmount = new HBox();
        HBox receivedCashHBox = new HBox();
        HBox changesHBox = new HBox();
        hBoxTotalAmount.setAlignment(Pos.TOP_LEFT);
        receivedCashHBox.setAlignment(Pos.TOP_LEFT);
        changesHBox.setAlignment(Pos.TOP_LEFT);
        receivedCashHBox.setSpacing(8);
        changesHBox.setSpacing(8);
        receivedCashHBox.getChildren().addAll(cashReceivedLabel, receivedTextField);
        changesHBox.getChildren().addAll(changesLabel, changesTextField);
        hBoxTotalAmount.getChildren().add(totalAmountLabel);
        hBoxTotalAmount.getChildren().add(totalAmountTextField);
        hBoxTotalAmount.getChildren().add(traCheckButton);
        hBoxSubmit.getChildren().add(submitCashBill);
        hBoxSubmit.getChildren().add(removeFromCart);
        vBox.getChildren().add(hBoxTotalAmount);
        vBox.getChildren().addAll(receivedCashHBox);
        vBox.getChildren().addAll(changesHBox);
        vBox.getChildren().add(listBillTable);
        vBox.getChildren().add(hBoxSubmit);

        vBox.setPadding(new Insets(4, 0, 0, 0));
        vBox.setSpacing(4);
        hBoxTotalAmount.setSpacing(2);
        hBoxSubmit.setSpacing(107);

        //this hold the bill table view and the input control pane
        GridPane inputPaneAndBillTablePane = new GridPane();
        RowConstraints row0 = new RowConstraints(310);
        RowConstraints row1 = new RowConstraints();
        ColumnConstraints column0 = new ColumnConstraints();
        inputPaneAndBillTablePane.getRowConstraints().addAll(row0, row1);
        inputPaneAndBillTablePane.getColumnConstraints().addAll(column0);
        row1.setVgrow(Priority.ALWAYS);
        int secondRow = 1;
        int firstColumn = 0;
        inputPaneAndBillTablePane.add(gridPaneDataInput, firstColumn, firstRow);
        inputPaneAndBillTablePane.add(vBox, firstColumn, secondRow);


        //reurn salling goods
        tableViewSalesOfDay.setOnMouseClicked(event -> {
            switch (event.getButton()){
                case SECONDARY:{
                    double x = event.getSceneX()+50;
                    double y = event.getSceneY()+70;
                    Popup popup = new Popup();
                    popup.setX(x);
                    popup.setY(y);

                    ListView<String> list = new ListView<>();
                    ObservableList<String> data = FXCollections.observableArrayList();
                    data.addAll("Delete ", "Update ", "Refresh");
                    list.setItems(data);
                    list.setStyle("-fx-background-color: white");
                    list.setPrefSize(100, 130);
                }
            }
        });
        //wholesale check box
        wholeSaleCheckBox.setOnMouseClicked(event -> {
            if (productNameChooser.getSelectionModel().isEmpty()) {
                productNameChooser.requestFocus();
                productNameChooser.show();
            } else {
                if (wholeSaleCheckBox.isSelected()) {
                    if (quantityTextField.getText().isEmpty()) {
                        amountTextField.setText(String.valueOf(0));
                    } else {
                        int quant = Integer.parseInt(quantityTextField.getText());
                        float wPrice = StockCategoryData.wSellPrice;
                        try {
                            float total = (wPrice * quant) - Integer.parseInt(discountTextField.getText());
                            amountTextField.setText(NumberFormat.getInstance().format(total));

                        } catch (Throwable q) {
                            amountTextField.setText(String.valueOf(0));
                        }
                    }
                    quantityTextField.requestFocus();
                    //event.consume();
                } else {
                    float price = StockCategoryData.sellPrice;
                    try {
                        float product = (price * (Integer.parseInt(quantityTextField.getText())))
                                - Integer.parseInt(discountTextField.getText());
                        //format number for accountant
                        String value = NumberFormat.getInstance().format(product);
                        amountTextField.setText(value);
                    } catch (Throwable throwable) {
                        amountTextField.setText(String.valueOf(0));
                    }
                    quantityTextField.requestFocus();
                }

            }


        });
        //simple calculator
        receivedTextField.setOnKeyReleased(event -> {
            if (event.getCode().isDigitKey() && !invisibleTotalAmountTextField.getText().isEmpty()) {

                int received = Integer.parseInt(receivedTextField.getText());
                int change = received - Integer.parseInt(invisibleTotalAmountTextField.getText());
                changesTextField.setText(NumberFormat.getInstance().format(change));

            } else {
                switch (event.getCode()) {
                    case BACK_SPACE: {
                        if (!invisibleTotalAmountTextField.getText().isEmpty()) {
                            try {

                                int received = Integer.parseInt(receivedTextField.getText());
                                int change = received - Integer.parseInt(invisibleTotalAmountTextField.getText());
                                changesTextField.setText(NumberFormat.getInstance().format(change));
                            } catch (NumberFormatException ignore) {
                                if (receivedTextField.getText().isEmpty()) {
                                    changesTextField.clear();
                                }
                            }

                        } else event.consume();
                    }
                    break;
                }
            }

        });

        //refresh tra sales
        refreshTraButton.setOnAction(event -> {
            //update total tra sales
            float traSaleTotal1 = SaleCategoryData.getTotalTraSaleOfDay();
            float traCashierSales1 = SaleCategoryData
                    .getTotalTraSaleOfDayOfCashier(LogInStage.currentUserName);
            totalTraSale.setText(NumberFormat.getInstance().format(traSaleTotal1));
            totalUserTraSales.setText(NumberFormat.getInstance().format(traCashierSales1));

        });

        productNameChooser.getEditor().setOnMouseClicked(event -> {

            ObservableList<String> products = StockCategoryData.getProductNames();
            productNameChooser.setItems(products);
            removeFromCart.setVisible(false);
            productNameChooser.getSelectionModel().clearSelection();
            productNameChooser.show();
            quantityTextField.clear();
            changesTextField.clear();
            receivedTextField.clear();
            shelfTextField.clear();
            remainInStockTextField.clear();
            priceTextField.clear();
            amountTextField.clear();

        });

        productNameChooser.getEditor().setOnKeyPressed(event -> {
            removeFromCart.setVisible(false);

            switch (event.getCode()) {
                case ENTER:
                    //if select is not empty
                    if (!productNameChooser.getSelectionModel().isEmpty()) {
                        String condition = productNameChooser.getSelectionModel().getSelectedItem();
                        String sell = StockCategoryData.getSellPrice(condition);
                        String shelf = StockCategoryData.getShelf(condition);
                        String stockRemain = String.valueOf(StockCategoryData.getProductQuantity(condition));
                        StockCategoryData.getWholeSellPrice(condition);
                        priceTextField.setText(sell);
                        shelfTextField.setText(shelf);
                        remainInStockTextField.setText(stockRemain);
                        discountTextField.setText(Integer.toString(0));
                    }
                    quantityTextField.requestFocus();
                    break;
            }

        });

        productNameChooser.getEditor().setOnKeyReleased(event -> {
            //update list of combo box
            ObservableList<String> products = StockCategoryData.getProductNames();

            switch (event.getCode()) {
                case DOWN: {
                    event.consume();
                    break;
                }
                case UP: {
                    event.consume();
                    break;
                }
                case LEFT: {
                    event.consume();
                    break;
                }
                case RIGHT: {
                    event.consume();
                    break;
                }
                case SHIFT: {
                    event.consume();
                    break;
                }
                case CONTROL: {
                    event.consume();
                    break;
                }
                case ALT: {
                    event.consume();
                    break;
                }
                case SPACE: {

                    break;
                }
                case BACK_SPACE: {

                    ObservableList<String> filter = FXCollections.observableArrayList();
                    //filter the products with the word typed
                    productNameChooser.getItems().clear();
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = productNameChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            productNameChooser.getItems().add(product);
                        }
                    }

                    if (productNameChooser.getItems().isEmpty()) filter.add("No such item in stock");

                    if (productNameChooser.getEditor().getText().isEmpty()) {
                        productNameChooser.setItems(products);
                        productNameChooser.show();
                    }

                    break;
                }
                default: {
                    ObservableList<String> filter1 = FXCollections.observableArrayList();
                    productNameChooser.getItems().clear();
                    for (String product : products) {
                        //implement not case sensitive
                        String inputString = productNameChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            productNameChooser.getItems().add(product);
                        }
                    }

                    if (productNameChooser.getItems().isEmpty()) filter1.add("No such item in stock");

                    if (productNameChooser.getEditor().getText().isEmpty()) {
                        productNameChooser.setItems(products);
                        productNameChooser.show();
                    }

                    break;
                }
            }

            productNameChooser.show();

        });

        productNameChooser.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (!productNameChooser.getSelectionModel().isEmpty()) {
                        String condition = productNameChooser.getSelectionModel().getSelectedItem();
                        String sell = StockCategoryData.getSellPrice(condition);
                        String shelf = StockCategoryData.getShelf(condition);
                        String stockRemain = String.valueOf(StockCategoryData.getProductQuantity(condition));
                        StockCategoryData.getWholeSellPrice(condition);
                        priceTextField.setText(sell);
                        shelfTextField.setText(shelf);
                        discountTextField.setText(Integer.toString(0));
                        remainInStockTextField.setText(stockRemain);
                    }
                    quantityTextField.requestFocus();
                    break;
            }
        });

        productNameChooser.setOnMouseClicked(event -> {
            ObservableList<String> products = StockCategoryData.getProductNames();
            productNameChooser.setItems(products);
            removeFromCart.setVisible(false);
            productNameChooser.getSelectionModel().clearSelection();
            productNameChooser.show();
            quantityTextField.clear();
            changesTextField.clear();
            shelfTextField.clear();
            remainInStockTextField.clear();
            priceTextField.clear();
            amountTextField.clear();

        });

        //check if a product is selected and quantity is set then shift focus to add to cart if its tru
        quantityTextField.setOnKeyPressed(event -> {

            if (productNameChooser.getSelectionModel().isEmpty()) {
                productNameChooser.requestFocus();
                productNameChooser.show();

            } else {
                switch (event.getCode()) {
                    case ENTER:

                        //implementation to add to view list will be here
                        if (quantityTextField.getText().isEmpty()) {
                            quantityTextField.requestFocus();
                        } else {
                            discountTextField.requestFocus();
                        }
                }
            }

        });

        //implement discount text field
        discountTextField.setOnKeyPressed(event -> {
            if (quantityTextField.getText().isEmpty()) {

                quantityTextField.requestFocus();

            } else {
                switch (event.getCode()) {
                    case ENTER:
                        //implementation to add to view list will be here
                        if (discountTextField.getText().isEmpty()) {

                            discountTextField.requestFocus();

                        } else {

                            addToCartButton.requestFocus();

                        }
                }
            }
        });

        //dynamic calculate the amount of product if is selected and key pressed=digit
        quantityTextField.setOnKeyReleased(event -> {
            //if is a digit
            if (event.getCode().isDigitKey()) {
                if (wholeSaleCheckBox.isSelected()) {
                    int quant = Integer.parseInt(quantityTextField.getText());
                    float wPrice = StockCategoryData.wSellPrice;
                    try {
                        float total = (wPrice * quant) - Integer.parseInt(discountTextField.getText());
                        amountTextField.setText(NumberFormat.getInstance().format(total));

                    } catch (Throwable q) {
                        q.printStackTrace();
                    }

                } else {

                    float price = StockCategoryData.sellPrice;
                    try {
                        float product = (price * (Integer.parseInt(quantityTextField.getText())))
                                - Integer.parseInt(discountTextField.getText());
                        //format number for accountant
                        String value = NumberFormat.getInstance().format(product);
                        amountTextField.setText(value);
                    } catch (Throwable ignore) { }
                }
            }
            //is its a backslash
            switch (event.getCode()) {
                case BACK_SPACE:
                    float price = StockCategoryData.sellPrice;
                    if (wholeSaleCheckBox.isSelected()) {
                        if (quantityTextField.getText().isEmpty()) {
                            amountTextField.setText(String.valueOf(0));
                        } else {
                            int quant = Integer.parseInt(quantityTextField.getText());
                            float wPrice = StockCategoryData.wSellPrice;
                            try {
                                float total = (wPrice * quant) - Integer.parseInt(discountTextField.getText());
                                amountTextField.setText(NumberFormat.getInstance().format(total));

                            } catch (Throwable q) {
                                amountTextField.setText(String.valueOf(0));
                            }
                        }

                    } else {
                        if (quantityTextField.getText().isEmpty()) {
                            amountTextField.setText(String.valueOf(0));
                        } else {
                            try {
                                float product = (price * (Integer.parseInt(quantityTextField.getText())))
                                        - Integer.parseInt(discountTextField.getText());
                                //format number for accountant
                                String value = NumberFormat.getInstance().format(product);
                                amountTextField.setText(value);
                            } catch (Throwable throwable) {
                                amountTextField.setText(String.valueOf(0));
                            }
                        }
                    }
                    break;
            }
        });

        //implement discount
        discountTextField.setOnKeyReleased(event -> {
            if (quantityTextField.getText().isEmpty()) quantityTextField.requestFocus();
            else {
                if (event.getCode().isDigitKey()) {
                    float price = StockCategoryData.sellPrice;
                    try {
                        float discountAmount = (price * (Integer.parseInt(quantityTextField.getText())))
                                - Integer.parseInt(discountTextField.getText());
                        //format number for accountant
                        String value = NumberFormat.getInstance().format(discountAmount);
                        amountTextField.setText(value);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                //is its a backslash
                switch (event.getCode()) {
                    case BACK_SPACE:
                        float price = StockCategoryData.sellPrice;
                        try {
                            int amount = ((int) (price * (Integer.parseInt(quantityTextField.getText()))))
                                    - Integer.parseInt(discountTextField.getText());
                            String value = NumberFormat.getInstance().format(amount);
                            amountTextField.setText(value);
                        } catch (Throwable t) {
                            amountTextField.setText(String.valueOf("0"));
                        }
                        break;
                }
            }
        });

        //when clicked set unit and price and category of product if selected
        quantityTextField.setOnMouseClicked(event -> {
            if (productNameChooser.getSelectionModel().isEmpty()) {
                productNameChooser.requestFocus();
                productNameChooser.show();
            } else {
                if (!productNameChooser.getSelectionModel().isEmpty()) {
                    String condition = productNameChooser.getSelectionModel().getSelectedItem();
                    String sellPrice = StockCategoryData.getSellPrice(condition);
                    String shelf = StockCategoryData.getShelf(condition);
                    String stockRemain = String.valueOf(StockCategoryData.getProductQuantity(condition));
                    StockCategoryData.getWholeSellPrice(condition);
                    priceTextField.setText(sellPrice);
                    shelfTextField.setText(shelf);
                    remainInStockTextField.setText(stockRemain);
                    discountTextField.setText("0");
                }
            }
        });

        //delete the input data
        cancelCashSaleButton.setOnMouseClicked(event -> {
            productNameChooser.getSelectionModel().clearSelection();
            receivedTextField.clear();
            changesTextField.clear();
            quantityTextField.clear();
            amountTextField.clear();
            priceTextField.clear();
            shelfTextField.clear();
            remainInStockTextField.clear();
        });

        //save the current product to the slip before submit and shift product to product chooser if
        //every input filled
        addToCartButton.setOnMouseClicked(event -> {

            if (amountTextField.getText().isEmpty()) {

                quantityTextField.requestFocus();

            } else if (discountTextField.getText().isEmpty()) {

                discountTextField.requestFocus();

            } else {

                if (wholeSaleCheckBox.isSelected()) {
                    //check if product stock quantity minus sell quantity is negative
                    String product = productNameChooser.getSelectionModel().getSelectedItem();
                    int quantity;
                    float discount;

                    //check if discount is number
                    try {
                        discount = Float.parseFloat(discountTextField.getText());
                    } catch (Throwable t) {
                        discountTextField.requestFocus();
                        throw new IllegalArgumentException("Illegal input in discount");
                    }

                    //test if quantity is number
                    try {
                        quantity = Integer.parseInt(quantityTextField.getText())
                                * StockCategoryData.getWholeProductQuantity(product);
                    } catch (NumberFormatException e) {
                        quantityTextField.clear();
                        quantityTextField.requestFocus();
                        throw new NumberFormatException();
                    }

                    //check if quantity is greater than available
                    int stockQuantity = StockCategoryData.getProductQuantity(product);
                    stockQuantity = stockQuantity - quantity;
                    if (stockQuantity < 0) {
                        quantityTextField.requestFocus();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("You sell more than available");
                        alert.setTitle("Zero Stock Error");
                        alert.show();
                        //if all condition are okay then we add to cart
                    } else {

                        //it update the static sell price field in stock category class

                        //get amount of a product with discount
                        float amount1 = (Integer.parseInt(quantityTextField.getText()) * StockCategoryData.wSellPrice)
                                - Integer.parseInt(discountTextField.getText());

                        //add data to the columns of bill table
                        billData.add(
                                new CashSaleTableBill(product, quantity, discount, amount1)
                        );
                        listBillTable.setItems(billData);

                        //calculate the sum after add all items to the cart
                        int totalSum = (int) (((int) (Integer.valueOf(quantityTextField.getText())
                                * StockCategoryData.wSellPrice)) - discount);
                        int sum;
                        sum = (Integer.parseInt(invisibleTotalAmountTextField.getText()) + totalSum);

                        invisibleTotalAmountTextField.setText(String.valueOf(sum));
                        totalAmountTextField.setText(NumberFormat.getInstance().format(sum));
                        productNameChooser.getSelectionModel().clearSelection();
                        receivedTextField.clear();
                        priceTextField.clear();
                        changesTextField.clear();
                        quantityTextField.clear();
                        amountTextField.clear();
                        shelfTextField.clear();
                        discountTextField.clear();
                        remainInStockTextField.clear();
                        productNameChooser.requestFocus();
                        wholeSaleCheckBox.setSelected(false);
                    }

                } else {
                    //check if product stock quantity minus sell quantity is negative
                    String product = productNameChooser.getSelectionModel().getSelectedItem();
                    int quantity;
                    float discount;

                    //check if discount is number
                    try {
                        discount = Float.parseFloat(discountTextField.getText());
                    } catch (Throwable t) {
                        discountTextField.requestFocus();
                        throw new IllegalArgumentException("Illegal input in discount");
                    }

                    //test if quantity is number
                    try {
                        quantity = Integer.parseInt(quantityTextField.getText());
                    } catch (NumberFormatException e) {
                        quantityTextField.clear();
                        quantityTextField.requestFocus();
                        throw new NumberFormatException();
                    }

                    //check if quantity is greater than available
                    int stockQuantity = StockCategoryData.getProductQuantity(product);
                    stockQuantity = stockQuantity - quantity;
                    if (stockQuantity < 0) {
                        quantityTextField.requestFocus();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("You sell more than available");
                        alert.setTitle("Zero Stock Error");
                        alert.show();
                        //if all condition are okay then we add to cart
                    } else {

                        //it update the static sell price field in stock category class
                        StockCategoryData.getSellPrice(productNameChooser.getSelectionModel().getSelectedItem());

                        //get amount of a product with discount
                        float amount1 = (Integer.parseInt(quantityTextField.getText()) * StockCategoryData.sellPrice)
                                - Integer.parseInt(discountTextField.getText());

                        //add data to the columns of bill table
                        billData.add(
                                new CashSaleTableBill(product, quantity, discount, amount1)
                        );
                        listBillTable.setItems(billData);

                        //calculate the sum after add all items to the cart
                        int totalSum = (int) (((int) (Integer.valueOf(quantityTextField.getText())
                                * StockCategoryData.sellPrice)) - discount);
                        int sum;
                        sum = (Integer.parseInt(invisibleTotalAmountTextField.getText()) + totalSum);
                        invisibleTotalAmountTextField.setText(String.valueOf(totalSum));

                        invisibleTotalAmountTextField.setText(String.valueOf(sum));
                        totalAmountTextField.setText(NumberFormat.getInstance().format(sum));
                        productNameChooser.getSelectionModel().clearSelection();
                        receivedTextField.clear();
                        priceTextField.clear();
                        changesTextField.clear();
                        quantityTextField.clear();
                        amountTextField.clear();
                        shelfTextField.clear();
                        discountTextField.clear();
                        remainInStockTextField.clear();
                        productNameChooser.requestFocus();
                        wholeSaleCheckBox.setSelected(false);
                    }
                }
            }
        });

        addToCartButton.setOnKeyPressed(event -> {
            switch (event.getCode()) {

                case ENTER: {

                    if (amountTextField.getText().isEmpty()) {

                        quantityTextField.requestFocus();

                    } else if (discountTextField.getText().isEmpty()) {

                        discountTextField.requestFocus();

                    } else {

                        if (wholeSaleCheckBox.isSelected()) {
                            //check if product stock quantity minus sell quantity is negative
                            String product = productNameChooser.getSelectionModel().getSelectedItem();
                            int quantity;
                            float discount;

                            //check if discount is number
                            try {
                                discount = Float.parseFloat(discountTextField.getText());
                            } catch (Throwable t) {
                                discountTextField.requestFocus();
                                throw new IllegalArgumentException("Illegal input in discount");
                            }

                            //test if quantity is number
                            try {
                                quantity = Integer.parseInt(quantityTextField.getText())
                                        * StockCategoryData.getWholeProductQuantity(product);
                            } catch (NumberFormatException e) {
                                quantityTextField.clear();
                                quantityTextField.requestFocus();
                                throw new NumberFormatException();
                            }

                            //check if quantity is greater than available
                            int stockQuantity = StockCategoryData.getProductQuantity(product);
                            stockQuantity = stockQuantity - quantity;
                            if (stockQuantity < 0) {
                                quantityTextField.requestFocus();
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("You sell more than available");
                                alert.setTitle("Zero Stock Error");
                                alert.show();
                                //if all condition are okay then we add to cart
                            } else {

                                //it update the static sell price field in stock category class

                                //get amount of a product with discount
                                float amount1 = (Integer.parseInt(quantityTextField.getText()) * StockCategoryData.wSellPrice)
                                        - Integer.parseInt(discountTextField.getText());

                                //add data to the columns of bill table
                                billData.add(
                                        new CashSaleTableBill(product, quantity, discount, amount1)
                                );
                                listBillTable.setItems(billData);

                                //calculate the sum after add all items to the cart
                                int totalSum = (int) (((int) (Integer.valueOf(quantityTextField.getText())
                                        * StockCategoryData.wSellPrice)) - discount);
                                int sum;
                                sum = (Integer.parseInt(invisibleTotalAmountTextField.getText()) + totalSum);

                                invisibleTotalAmountTextField.setText(String.valueOf(sum));
                                totalAmountTextField.setText(NumberFormat.getInstance().format(sum));
                                productNameChooser.getSelectionModel().clearSelection();
                                receivedTextField.clear();
                                priceTextField.clear();
                                changesTextField.clear();
                                quantityTextField.clear();
                                amountTextField.clear();
                                shelfTextField.clear();
                                discountTextField.clear();
                                remainInStockTextField.clear();
                                productNameChooser.requestFocus();
                                wholeSaleCheckBox.setSelected(false);
                            }

                        } else {
                            //check if product stock quantity minus sell quantity is negative
                            String product = productNameChooser.getSelectionModel().getSelectedItem();
                            int quantity;
                            float discount;

                            //check if discount is number
                            try {
                                discount = Float.parseFloat(discountTextField.getText());
                            } catch (Throwable t) {
                                discountTextField.requestFocus();
                                throw new IllegalArgumentException("Illegal input in discount");
                            }

                            //test if quantity is number
                            try {
                                quantity = Integer.parseInt(quantityTextField.getText());
                            } catch (NumberFormatException e) {
                                quantityTextField.clear();
                                quantityTextField.requestFocus();
                                throw new NumberFormatException();
                            }

                            //check if quantity is greater than available
                            int stockQuantity = StockCategoryData.getProductQuantity(product);
                            stockQuantity = stockQuantity - quantity;
                            if (stockQuantity < 0) {
                                quantityTextField.requestFocus();
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("You sell more than available");
                                alert.setTitle("Zero Stock Error");
                                alert.show();
                                //if all condition are okay then we add to cart
                            } else {

                                //it update the static sell price field in stock category class
                                StockCategoryData.getSellPrice(productNameChooser
                                        .getSelectionModel().getSelectedItem());

                                //get amount of a product with discount
                                float amount1 = (Integer.parseInt(quantityTextField.getText())
                                        * StockCategoryData.sellPrice)
                                        - Integer.parseInt(discountTextField.getText());

                                //add data to the columns of bill table
                                billData.add(
                                        new CashSaleTableBill(product, quantity, discount, amount1)
                                );
                                listBillTable.setItems(billData);

                                //calculate the sum after add all items to the cart
                                int totalSum = (int) (((int) (Integer.valueOf(quantityTextField.getText())
                                        * StockCategoryData.sellPrice)) - discount);
                                int sum;
                                sum = (Integer.parseInt(invisibleTotalAmountTextField.getText()) + totalSum);
                                invisibleTotalAmountTextField.setText(String.valueOf(totalSum));

                                invisibleTotalAmountTextField.setText(String.valueOf(sum));
                                totalAmountTextField.setText(NumberFormat.getInstance().format(sum));
                                productNameChooser.getSelectionModel().clearSelection();
                                receivedTextField.clear();
                                priceTextField.clear();
                                changesTextField.clear();
                                quantityTextField.clear();
                                amountTextField.clear();
                                shelfTextField.clear();
                                discountTextField.clear();
                                remainInStockTextField.clear();
                                productNameChooser.requestFocus();
                                wholeSaleCheckBox.setSelected(false);
                            }
                        }
                    }
                }
            }

        });

        removeFromCart.setOnMouseClicked(event -> {
            if (listBillTable.getSelectionModel().isEmpty()) listBillTable.getSelectionModel().select(0);
            else {
                int selectedIndex = listBillTable.getSelectionModel().getSelectedIndex();
                int amount = (int) listBillTable.getSelectionModel().getSelectedItem().getAmount();
                int amountNew = Integer.parseInt(invisibleTotalAmountTextField.getText()) - amount;
                invisibleTotalAmountTextField.setText(String.valueOf(amountNew));
                totalAmountTextField.setText(NumberFormat.getInstance().format(amountNew));
                listBillTable.getItems().remove(selectedIndex);
            }
            removeFromCart.setVisible(false);
        });

        //show remove button when clicked
        listBillTable.setOnMouseClicked(event -> removeFromCart.setVisible(true));

        //when submit bill button pressed
        submitCashBill.setOnMouseClicked(event -> {
            setSubmitCashBill();
        });

        //when check button pressed for
        traCheckButton.setOnMouseClicked(event -> {
            if (traCheckButton.isSelected()) listBillTable.setStyle("-fx-base: #00ff00");
            else listBillTable.setStyle("-fx-base: #efeded");
        });

        cashSaleUI.add(inputPaneAndBillTablePane, firstColumn, firstRow);
    }

    void setSubmitCashBill() {
        //set alert dialog if error happen on submit slip
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Zero Stock Error");
        alert.setContentText("You sell more than available...\n\n");
        boolean showAlert = false;

        //insert the list of product in the cash sale database when paid
        String id;
        String product;
        String category;
        String unit;
        int quantity;
        int stockQuantity;
        float discount;
        float amount;
        String user;
        //check if tra button is pressed
        //n-means a normal sell
        //n/n
        if (traCheckButton.isSelected()) id = "n/n";
        else id = "n";

        //check first if list is not empty
        if (!listBillTable.getItems().isEmpty()) {

            //add content of the bill to the database
            for (CashSaleTableBill cashSaleTableBill : listBillTable.getItems()) {

                product = cashSaleTableBill.getProduct();
                amount = cashSaleTableBill.getAmount();
                quantity = cashSaleTableBill.getQuantity();
                discount = cashSaleTableBill.getDiscount();

                //update the stock quantity
                stockQuantity = StockCategoryData.getProductQuantity(product);
                stockQuantity = stockQuantity - quantity;

                //check each product quantity for zero stock error
                if (stockQuantity < 0) {
                    String string = alert.getContentText();
                    alert.setContentText(string.concat(product + "\n"));
                    showAlert = true;
                } else {

                    //update product quantity after sell it
                    StockCategoryData.updateProductQuantity(product, stockQuantity);

                    category = StockCategoryData.getCategory(product);
                    unit = StockCategoryData.getUnit(product);
                    long timeInMillis = Calendar.getInstance().getTimeInMillis();
                    Date date1 = new Date(timeInMillis);

                    //get the user input the data
                    user = LogInStage.currentUserName;

                    //add data to all sale of the day table
                    tableViewSalesOfDay.getItems().add(
                            new CashSaleOfDay(
                                    date1.toString(),
                                    product,
                                    category,
                                    quantity,
                                    amount
                            )
                    );

                    //add data to tra table sale of the day
                    if (id.equals("n/n")) {
                        tableViewSaleTraOfDay.getItems().add(
                                new CashTraSaleOfDay(
                                        date1.toString(),
                                        product,
                                        category,
                                        quantity,
                                        amount
                                )
                        );
                    }

                    //add data to database
                    SaleCategoryData.insertData(
                            id,
                            product,
                            category,
                            unit,
                            quantity,
                            amount,
                            discount,
                            user
                    );

                    //update total tra sales
                    float traSaleTotal1 = SaleCategoryData.getTotalTraSaleOfDay();
                    float traCashierSales1 = SaleCategoryData
                            .getTotalTraSaleOfDayOfCashier(LogInStage.currentUserName);
                    totalTraSale.setText(NumberFormat.getInstance().format(traSaleTotal1));
                    totalUserTraSales.setText(NumberFormat.getInstance().format(traCashierSales1));

                    //clear simple calculator
                    receivedTextField.clear();
                    changesTextField.clear();

                }
            }
            //show product with results int zero stock
            if (showAlert) {
                String concat = alert.getContentText().concat("\n remove those from cart");
                alert.setContentText(concat);
                alert.show();
            } else {

                //if successful bill is submitted
                invisibleTotalAmountTextField.setText(String.valueOf(0));
                totalAmountTextField.clear();
                listBillTable.getItems().clear();
                traCheckButton.setSelected(false);
                listBillTable.setStyle("-fx-base: #efeded");
                productNameChooser.requestFocus();
                productNameChooser.show();
            }

        } else if (productNameChooser.getSelectionModel().isEmpty()) {
            productNameChooser.requestFocus();
            productNameChooser.show();
        } else {
            addToCartButton.requestFocus();
        }
    }

    private void cashSaleReports(boolean isAdmin) {

        if (isAdmin) {

            Label totalSale = new Label("Total :");
            Label chooseDateLabel = new Label("Choose Date :");
            chooseDateLabel.setFont(new Font(14));
            totalSale.setFont(new Font(14));

            Button refreshButton = new Button("Refresh");
            refreshButton.setDefaultButton(true);

            TextField totalAmountTextField = new TextField();
            totalAmountTextField.setEditable(false);
            totalAmountTextField.setStyle("-fx-background-color: #13a715");

            NumberAxis floatAxis = new NumberAxis();
            CategoryAxis categoryAxis = new CategoryAxis();

            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll("N/N", "N", "A");

            floatAxis.setLabel("amount");
            categoryAxis.setCategories(observableList);

            BarChart<Number, String> barChart = new BarChart<>(floatAxis, categoryAxis);
            barChart.setTitle("Sales Report");

            XYChart.Series<Number, String> seriesTra;
            XYChart.Series<Number, String> seriesNormal;
            XYChart.Series<Number, String> seriesTotal;

            seriesTra = new XYChart.Series<>();
            seriesNormal = new XYChart.Series<>();
            seriesTotal = new XYChart.Series<>();
            seriesTotal.getData().add(new XYChart.Data<>(10000, "A"));
            seriesNormal.getData().add(new XYChart.Data<>(2300, "N"));
            seriesTra.getData().add(new XYChart.Data<>(4879, "N/N"));

            barChart.getData().add(0, seriesTotal);
            barChart.getData().add(1, seriesNormal);
            barChart.getData().add(2, seriesTra);

            TableView<CashierSale> cashierSaleTableView = new TableView<>();
            cashierSaleTableView.setStyle("-fx-base: #13a715");
            cashierSaleTableView.autosize();

            TableColumn<CashierSale, String> cashierColumn = new TableColumn<>("Name");
            TableColumn<CashierSale, Float> amountColumn = new TableColumn<>("Amount(TZS)");
            TableColumn<CashierSale, Float> discountColumn = new TableColumn<>("Discount(TZS)");

            cashierColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
            discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

            cashierSaleTableView.getColumns().addAll(cashierColumn, amountColumn, discountColumn);
            java.util.Date dat = new java.util.Date();

            String date = new Date(dat.getTime()).toString();

            ObservableList<String> data = SaleCategoryData.cashierList(date);
            String totalCashSale = NumberFormat.getInstance().format(SaleCategoryData.getTotalSaleOfDay(date));
            totalAmountTextField.setText(totalCashSale);
            for (String s : data) {
                cashierSaleTableView.getItems().add(
                        new CashierSale(
                                s,
                                SaleCategoryData.getTotalSaleOfDay(s, date),
                                SaleCategoryData.getTotalDiscount(s, date)
                        )
                );

            }

            //for total amount and refresh table
            HBox totalSaleHBox = new HBox();
            totalSaleHBox.setAlignment(Pos.TOP_LEFT);
            totalSaleHBox.setSpacing(4);
            totalSaleHBox.getChildren().addAll(refreshButton, totalSale, totalAmountTextField);

            //for pick date
            HBox dateHBox = new HBox();
            dateHBox.setAlignment(Pos.TOP_LEFT);
            dateHBox.setSpacing(20);
            DatePicker saleOfDayDatePicker = new DatePicker();
            dateHBox.getChildren().addAll(chooseDateLabel, saleOfDayDatePicker);

            VBox totalSaleVBox = new VBox();
            totalSaleVBox.setSpacing(4);
            totalSaleVBox.getChildren().addAll(
                    dateHBox,
                    totalSaleHBox,
                    cashierSaleTableView
            );


            splitPane = new SplitPane();
            SplitPane splitPaneSaleTotalReports = new SplitPane();
            splitPaneSaleTotalReports.setOrientation(Orientation.VERTICAL);
            splitPaneSaleTotalReports.setDividerPositions(0.37f);

            splitPane.setOrientation(Orientation.HORIZONTAL);
            splitPane.setDividerPositions(0.4f);

            splitPaneSaleTotalReports.getItems().addAll(barChart, totalSaleVBox);
            splitPane.getItems().addAll(splitPaneSaleTotalReports, cashierAlerts());

            refreshButton.setOnAction(event -> {
                if (saleOfDayDatePicker.getValue() == null) {

                    saleOfDayDatePicker.requestFocus();
                    saleOfDayDatePicker.show();

                } else {
                    int year = saleOfDayDatePicker.getValue().getYear();
                    int month = saleOfDayDatePicker.getValue().getMonthValue();
                    int day = saleOfDayDatePicker.getValue().getDayOfMonth();
                    String date1 = year + "-" + month + "-" + day;

                    cashierSaleTableView.getItems().clear();
                    ObservableList<String> data1 = SaleCategoryData.cashierList(date1);
                    for (String s : data1) {
                        cashierSaleTableView.getItems().add(
                                new CashierSale(
                                        s,
                                        SaleCategoryData.getTotalSaleOfDay(s, date1),
                                        SaleCategoryData.getTotalDiscount(s, date1)
                                )
                        );
                    }
                    String totalCashSale1 = NumberFormat.getInstance().format(SaleCategoryData.getTotalSaleOfDay(date1));
                    totalAmountTextField.setText(totalCashSale1);
                }
            });
        } else {
            splitPane = new SplitPane();
            splitPane.setOrientation(Orientation.VERTICAL);
            splitPane.setDividerPositions(0.8f);
            splitPane.getItems().add(cashierAlerts());
        }

    }

    private GridPane cashierAlerts() {

        Label orderListLabel = new Label("Order List");
        Label expireListLabel = new Label("Expired Product");
        Label nearToExpireLabel = new Label("Near To Expire");

        Button reloadButton = new Button("Reload");

        ListView<String> expiredListView = new ListView<>();
        ListView<String> nearToExpireListView = new ListView<>();
        ListView<String> orderListView = new ListView<>();

        //set initial alerts reports
        expiredListView.setItems(StockCategoryData.getExpiredProduct());
        nearToExpireListView.setItems(StockCategoryData.getNearExpiredProduct());
        orderListView.setItems(StockCategoryData.getOrderList());

        GridPane orderListVBox = new GridPane();
        RowConstraints r00 = new RowConstraints(20);
        RowConstraints r11 = new RowConstraints();
        r11.setVgrow(Priority.ALWAYS);
        ColumnConstraints c00 = new ColumnConstraints();
        c00.setHgrow(Priority.ALWAYS);
        orderListVBox.getColumnConstraints().add(c00);
        orderListVBox.getRowConstraints().addAll(r00, r11);

        VBox expireListVBox = new VBox();
        VBox nearToExpireListVBox = new VBox();

        orderListVBox.setAlignment(Pos.TOP_CENTER);
        expireListVBox.setAlignment(Pos.TOP_CENTER);
        nearToExpireListVBox.setAlignment(Pos.TOP_CENTER);

        orderListVBox.add(orderListLabel, 0, 0);
        orderListVBox.add(orderListView, 0, 1);

        expireListVBox.getChildren().addAll(expireListLabel, expiredListView);
        nearToExpireListVBox.getChildren().addAll(nearToExpireLabel, nearToExpireListView);

        SplitPane splitPaneHorizontal = new SplitPane();
        SplitPane splitPaneVertical = new SplitPane();

        splitPaneHorizontal.setOrientation(Orientation.HORIZONTAL);
        splitPaneVertical.setOrientation(Orientation.VERTICAL);

        splitPaneVertical.getItems().addAll(nearToExpireListVBox, expireListVBox);
        splitPaneHorizontal.getItems().addAll(orderListVBox, splitPaneVertical);

        splitPaneVertical.setDividerPositions(0.5);
        splitPaneHorizontal.setDividerPositions(0.4);

        GridPane mainAlertGridPane = new GridPane();
        mainAlertGridPane.setPadding(new Insets(4, 4, 4, 4));
        RowConstraints r0 = new RowConstraints(30);
        RowConstraints r1 = new RowConstraints();
        ColumnConstraints c0 = new ColumnConstraints();
        mainAlertGridPane.getRowConstraints().addAll(r0, r1);
        mainAlertGridPane.getColumnConstraints().add(c0);
        c0.setHgrow(Priority.ALWAYS);
        r1.setVgrow(Priority.ALWAYS);

        mainAlertGridPane.add(reloadButton, 0, 0);
        mainAlertGridPane.add(splitPaneHorizontal, 0, 1);

        orderListView.setOnMouseClicked(event -> {

            Tab tab = cashierAlertsDetailOrder(StockCategoryData.getOrderListDetail());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            //load contents in the tables

        });

        nearToExpireListView.setOnMouseClicked(event -> {
            Tab tab = cashierAlertsdetailNearExpire(StockCategoryData.getNearExpiredProductDetail());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        });

        expiredListView.setOnMouseClicked(event -> {
            Tab tab = cashierAlertsDetailExpire(StockCategoryData.getExpiredProductDetail());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        });

        reloadButton.setOnAction(event -> {
            expiredListView.getItems().clear();
            nearToExpireListView.getItems().clear();
            orderListView.getItems().clear();
            //set initial alerts reports
            expiredListView.setItems(StockCategoryData.getExpiredProduct());
            nearToExpireListView.setItems(StockCategoryData.getNearExpiredProduct());
            orderListView.setItems(StockCategoryData.getOrderList());
        });

        return mainAlertGridPane;

    }

    private Tab cashierAlertsDetailOrder(ObservableList<OrderList> orderLists) {

        Label filterLabel = new Label("Filter Product: ");
        Label orderListLabel = new Label("ORDER LIST    ");

        TextField orderFilterTextField = new TextField();
        orderFilterTextField.setPromptText("Search...");

        //order list table view
        TableView<OrderList> orderListTableView = new TableView<>(orderLists);
        orderListTableView.autosize();
        TableColumn<OrderList, String> orderProductColumn = new TableColumn<>("Product");
        TableColumn<OrderList, Integer> orderQuantityColumn = new TableColumn<>("Quantity Remain");
        TableColumn<OrderList, String> orderSupplierColumn = new TableColumn<>("Last Supplier");
        TableColumn<OrderList, Float> orderPurchaseColumn = new TableColumn<>("Last Purchase Price(TZS)");

        HBox hBoxOrderList = new HBox();
        hBoxOrderList.setPadding(new Insets(4, 4, 4, 4));
        hBoxOrderList.setSpacing(10);
        hBoxOrderList.getChildren().addAll(orderListLabel, filterLabel, orderFilterTextField);

        orderProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        orderPurchaseColumn.setCellValueFactory(new PropertyValueFactory<>("purchase"));
        orderListTableView.getColumns().addAll(
                orderProductColumn,
                orderQuantityColumn,
                orderSupplierColumn,
                orderPurchaseColumn);

        GridPane gridPaneOrderList = new GridPane();
        gridPaneOrderList.setPadding(new Insets(6, 6, 6, 6));
        RowConstraints rw0 = new RowConstraints(30);
        RowConstraints rw1 = new RowConstraints();
        ColumnConstraints c0 = new ColumnConstraints();
        rw1.setVgrow(Priority.ALWAYS);
        c0.setHgrow(Priority.ALWAYS);
        gridPaneOrderList.getRowConstraints().addAll(rw0, rw1);
        gridPaneOrderList.getColumnConstraints().addAll(c0);
        gridPaneOrderList.add(hBoxOrderList, 0, 0);
        gridPaneOrderList.add(orderListTableView, 0, 1);

        Tab tabDetailAlerts = new Tab("Order Detail");
        tabDetailAlerts.setContent(gridPaneOrderList);

        orderFilterTextField.setOnKeyReleased(event -> {
            ObservableList<OrderList> orderLists1 = StockCategoryData.getOrderListDetail();

            switch (event.getCode()) {
                case DOWN:
                    event.consume();
                    break;
                case UP:
                    event.consume();
                    break;
                case LEFT:
                    event.consume();
                    break;
                case RIGHT:
                    event.consume();
                    break;
                case SHIFT:
                    event.consume();
                    break;
                case CONTROL:
                    event.consume();
                    break;
                case ALT:
                    event.consume();
                    break;
                case ENTER:
                    event.consume();
                    break;
                case BACK_SPACE: {

                    orderListTableView.getItems().clear();

                    for (OrderList stockList2 : orderLists1) {

                        if (!orderFilterTextField.getText().isEmpty()) {
                            String product = orderFilterTextField.getText().toLowerCase();
                            String productStock = stockList2.getProduct().toLowerCase();

                            if (productStock.contains(product)) {

                                orderListTableView.getItems().add(stockList2);
                            }
                        }

                    }

                    break;
                }
                default: {

                    orderListTableView.getItems().clear();

                    for (OrderList stockList2 : orderLists1) {

                        if (!orderFilterTextField.getText().isEmpty()) {
                            String product = orderFilterTextField.getText().toLowerCase();
                            String productStock = stockList2.getProduct().toLowerCase();

                            if (productStock.contains(product)) {

                                orderListTableView.getItems().add(stockList2);
                            }
                        }

                    }

                    break;
                }
            }

            if (orderFilterTextField.getText().isEmpty()) {
                orderListTableView.setItems(orderLists1);
            }

        });

        return tabDetailAlerts;
    }

    private Tab cashierAlertsDetailExpire(ObservableList<ExpiredProduct> expiredProducts) {

        Label expiredLabel = new Label("Expired Products");

        //expired product
        TableView<ExpiredProduct> expiredProductTableView = new TableView<>(expiredProducts);
        expiredProductTableView.autosize();

        TableColumn<ExpiredProduct, String> expiredProductColumn = new TableColumn<>("Product");
        TableColumn<ExpiredProduct, Integer> expiredQuantityColumn = new TableColumn<>("Quantity");
        TableColumn<ExpiredProduct, String> expiredDateColumn = new TableColumn<>("Expire Date");
        expiredProductTableView.getColumns().addAll(expiredProductColumn, expiredQuantityColumn, expiredDateColumn);

        expiredProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        expiredQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        expiredDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        HBox hBoxExpire = new HBox();
        hBoxExpire.setPadding(new Insets(4, 4, 4, 4));
        hBoxExpire.setSpacing(10);
        hBoxExpire.getChildren().addAll(expiredLabel);

        GridPane gridPaneExpire = new GridPane();
        gridPaneExpire.setPadding(new Insets(6, 6, 6, 6));
        RowConstraints r_0 = new RowConstraints(30);
        RowConstraints r_1 = new RowConstraints();
        ColumnConstraints c__0 = new ColumnConstraints();
        r_1.setVgrow(Priority.ALWAYS);
        c__0.setHgrow(Priority.ALWAYS);
        gridPaneExpire.getRowConstraints().addAll(r_0, r_1);
        gridPaneExpire.getColumnConstraints().addAll(c__0);
        gridPaneExpire.add(hBoxExpire, 0, 0);
        gridPaneExpire.add(expiredProductTableView, 0, 1);

        Tab tab = new Tab("Expred Detail");
        tab.setContent(gridPaneExpire);

        return tab;
    }

    private Tab cashierAlertsdetailNearExpire(ObservableList<NearExpire> nearExpires) {

        Label nearExpiredProduct = new Label("Near Expired Product");

        //near expire table
        TableView<NearExpire> nearExpireTableView = new TableView<>(nearExpires);
        nearExpireTableView.autosize();
        TableColumn<NearExpire, String> nearExpireProductColumn = new TableColumn<>("Product");
        TableColumn<NearExpire, Integer> nearExpireQuantityColumn = new TableColumn<>("Quantity");
        TableColumn<NearExpire, Integer> nearExpiredDateColumn = new TableColumn<>("Date");

        nearExpireProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        nearExpireQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        nearExpiredDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nearExpireTableView.getColumns().addAll(nearExpireProductColumn, nearExpireQuantityColumn, nearExpiredDateColumn);

        HBox hBoxNearExpire = new HBox();
        hBoxNearExpire.setPadding(new Insets(4, 4, 4, 4));
        hBoxNearExpire.setSpacing(10);
        hBoxNearExpire.getChildren().addAll(nearExpiredProduct);

        GridPane gridPaneNearExpire = new GridPane();
        gridPaneNearExpire.setPadding(new Insets(6, 6, 6, 6));
        RowConstraints r0 = new RowConstraints(30);
        RowConstraints r1 = new RowConstraints();
        ColumnConstraints c_0 = new ColumnConstraints();
        r1.setVgrow(Priority.ALWAYS);
        c_0.setHgrow(Priority.ALWAYS);
        gridPaneNearExpire.getRowConstraints().addAll(r0, r1);
        gridPaneNearExpire.getColumnConstraints().addAll(c_0);
        gridPaneNearExpire.add(hBoxNearExpire, 0, 0);
        gridPaneNearExpire.add(nearExpireTableView, 0, 1);

        Tab tab = new Tab("Near Expired Detail");

        tab.setContent(gridPaneNearExpire);

        return tab;
    }

    public static class CashSaleTableBill {
        private final SimpleStringProperty product;
        private final SimpleIntegerProperty quantity;
        private final SimpleFloatProperty amount;
        private final SimpleFloatProperty discount;

        CashSaleTableBill(String product, int quantity, float discount, float amount) {
            this.product = new SimpleStringProperty(product);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.discount = new SimpleFloatProperty(discount);
            this.amount = new SimpleFloatProperty(amount);
        }

        public String getProduct() {
            return product.get();
        }

        public int getQuantity() {
            return quantity.get();
        }

        public float getDiscount() {
            return discount.get();
        }

        public float getAmount() {
            return amount.get();
        }

    }

    public static class CashSaleOfDay {
        private SimpleStringProperty date;
        private SimpleStringProperty product;
        private SimpleStringProperty category;
        private SimpleIntegerProperty quantity;
        private SimpleFloatProperty amount;

        public CashSaleOfDay(String date, String product, String category, int quantity, float amount) {
            this.date = new SimpleStringProperty(date);
            this.product = new SimpleStringProperty(product);
            this.category = new SimpleStringProperty(category);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.amount = new SimpleFloatProperty(amount);
        }


        public String getDate() {
            return date.get();
        }

        public String getProduct() {
            return product.get();
        }

        public String getCategory() {
            return category.get();
        }

        public int getQuantity() {
            return quantity.get();
        }

        public float getAmount() {
            return amount.get();
        }
    }

    public static class CashTraSaleOfDay {
        private SimpleStringProperty date;
        private SimpleStringProperty product;
        private SimpleStringProperty category;
        private SimpleIntegerProperty quantity;
        private SimpleFloatProperty amount;

        public CashTraSaleOfDay(String date, String product, String category, int quantity, float amount) {
            this.date = new SimpleStringProperty(date);
            this.product = new SimpleStringProperty(product);
            this.category = new SimpleStringProperty(category);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.amount = new SimpleFloatProperty(amount);
        }

        public String getDate() {
            return date.get();
        }

        public String getProduct() {
            return product.get();
        }

        public String getCategory() {
            return category.get();
        }

        public int getQuantity() {
            return quantity.get();
        }

        public float getAmount() {
            return amount.get();
        }
    }

    public static class CashierSale {
        private final SimpleStringProperty name;
        private final SimpleFloatProperty amount;
        private final SimpleFloatProperty discount;

        public CashierSale(String name, float amount, float discount) {
            this.name = new SimpleStringProperty(name);
            this.amount = new SimpleFloatProperty(amount);
            this.discount = new SimpleFloatProperty(discount);
        }

        public float getAmount() {
            return amount.get();
        }

        public String getName() {
            return name.get();
        }

        public float getDiscount() {
            return discount.get();
        }
    }

    public static class OrderList {
        private final SimpleStringProperty product;
        private final SimpleIntegerProperty quantity;
        private final SimpleStringProperty supplier;
        private final SimpleFloatProperty purchase;

        public OrderList(String product, int quantity, String supplier, float purchase) {
            this.product = new SimpleStringProperty(product);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.supplier = new SimpleStringProperty(supplier);
            this.purchase = new SimpleFloatProperty(purchase);
        }

        public String getProduct() {
            return product.get();
        }

        public String getSupplier() {
            return supplier.get();
        }

        public int getQuantity() {
            return quantity.get();
        }

        public float getPurchase() {
            return purchase.get();
        }
    }

    public static class NearExpire {
        private final SimpleStringProperty product;
        private final SimpleIntegerProperty quantity;
        private final SimpleStringProperty date;

        public NearExpire(String product, int quantity, String date) {
            this.product = new SimpleStringProperty(product);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.date = new SimpleStringProperty(date);
        }

        public String getProduct() {
            return product.get();
        }

        public int getQuantity() {
            return quantity.get();
        }

        public String getDate() {
            return date.get();
        }
    }

    public static class ExpiredProduct {
        private final SimpleStringProperty product;
        private final SimpleIntegerProperty quantity;
        private final SimpleStringProperty date;

        public ExpiredProduct(String product, int quantity, String date) {
            this.product = new SimpleStringProperty(product);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.date = new SimpleStringProperty(date);
        }

        public String getProduct() {
            return product.get();
        }

        public int getQuantity() {
            return quantity.get();
        }

        public String getDate() {
            return date.get();
        }
    }

}