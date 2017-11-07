package fahamu.UserInteface;

import fahamu.dataFactory.PurchaseCategoryData;
import fahamu.dataFactory.SaleCategoryData;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;


public class ReportsCategoryUI {

    private GridPane navPane;
    GridPane dashboard;
    SplitPane splitPane;

    private String dateClicked;

    TableView<SalesTableDataClass> salesTable;
    TableView<SalesCategoryUI.CashierSale> cashierSaleTable, purchaseCreditHistoryTable;
    TableView<DiscountDetailTableDataClass> discountDetail, purchaseCashHistoryTable;
    TableView<ProductSellHistoryDataClass> sellHistoryTable;
    AreaChart<String, Number> salesGraph, sellPurchaseChart;

    XYChart.Series<String, Number> productCashSaleFrequency;

    static ObservableList<String> productsHistory;
    Spinner<Number> spinner;
    int multiples;
    int modulus;


    ReportsCategoryUI() {

        dashboard = setDashboardSalesReportUI();
        salesTable = setSalesTableViewUI("Date", "Sale(TZS)", "Discount(TZS)");
        cashierSaleTable = cashierSaleTableView("Name", "Amount(TZS)", "Discount(TZS)");
        discountDetail = setDiscountDetailTableView("Product", "Sale(TZS)", "Discount(TZS)");
        sellHistoryTable = setProductSellHistoryTableView();
        purchaseCashHistoryTable = setDiscountDetailTableView("Date", "Cash Purchase", "Stock");
        purchaseCreditHistoryTable = cashierSaleTableView("Date", "Credit Purchase", "Stock");
        salesGraph = setSalesGraphUI();
        sellPurchaseChart = setPotentialProductGraphUI();

        navPane = navigationLeftPane();
        splitPane = mainTaskUI(cashierSaleTable, discountDetail, null, salesGraph, 2);

        //add contents
        dashboard.add(navPane, 0, 0);
        dashboard.add(splitPane, 1, 0);

    }

    public GridPane setDashboardSalesReportUI() {
        GridPane mainGridPaneUI = new GridPane();
        mainGridPaneUI.setId("dashboard");
        mainGridPaneUI.setPadding(new Insets(10, 10, 10, 10));
        RowConstraints r1 = new RowConstraints();
        ColumnConstraints c1 = new ColumnConstraints(280);
        ColumnConstraints c2 = new ColumnConstraints();
        r1.setPercentHeight(100);
        c2.setHgrow(Priority.ALWAYS);
        mainGridPaneUI.getRowConstraints().addAll(r1);
        mainGridPaneUI.getColumnConstraints().addAll(c1, c2);
        return mainGridPaneUI;
    }

    public TableView<SalesTableDataClass> setSalesTableViewUI(String c1, String c2, String c3) {
        Date date = new Date(new java.util.Date().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, -7);
        java.util.Date dateBefore30Days = cal.getTime();

        String today = date.toString();
        String lastWeek = new Date(dateBefore30Days.getTime()).toString();

        TableView<SalesTableDataClass> salesTableView = new TableView<>();
        salesTableView.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5");
        salesTableView.autosize();
        salesTableView.setItems(SaleCategoryData.getSales(lastWeek, today));

        TableColumn<SalesTableDataClass, String> dateColumn = new TableColumn<>(c1);
        TableColumn<SalesTableDataClass, Float> saleColumn = new TableColumn<>(c2);
        TableColumn<SalesTableDataClass, Float> discountColumn = new TableColumn<>(c3);

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        saleColumn.setCellValueFactory(new PropertyValueFactory<>("sale"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));


        salesTableView.getColumns().addAll(dateColumn, saleColumn, discountColumn);

        salesTableView.setOnMouseClicked(event -> {
            if (salesTableView.getItems().isEmpty()) {
                event.consume();
            } else {
                if (salesTableView.getSelectionModel().isEmpty()) {
                    event.consume();
                } else {
                    String date1 = salesTableView.getSelectionModel().getSelectedItem().getDate();
                    dateClicked = date1;
                    ObservableList<String> data = SaleCategoryData.cashierList(date1);
                    //String totalCashSale = NumberFormat.getInstance().format(SaleCategoryData.getTotalSaleOfDay(date));
                    //totalAmountTextField.setText(totalCashSale);
                    cashierSaleTable.getItems().clear();
                    discountDetail.getItems().clear();
                    for (String s : data) {
                        cashierSaleTable.getItems().add(
                                new SalesCategoryUI.CashierSale(
                                        s,
                                        SaleCategoryData.getTotalSaleOfDay(s, date1),
                                        SaleCategoryData.getTotalDiscount(s, date1)
                                )
                        );

                    }
                }
            }
        });

        salesTableView.setRowFactory(param -> {
            TableRow<SalesTableDataClass> tableRow=new TableRow<>();
            tableRow.setOnMouseEntered(event -> {
                if (tableRow.isSelected()){
                    event.consume();
                }else if (tableRow.isEmpty()){
                    event.consume();
                }else {
                    salesTableView.requestFocus();
                    salesTableView.getFocusModel().focus(tableRow.getIndex());
                    tableRow.setStyle("-fx-background-color: rgba(0,0,255,0.15)");
                }
                tableRow.setOnMouseExited(event1 -> {
                    tableRow.setStyle("-fx-base: white");
                });
            });
            return tableRow;
        });

        return salesTableView;
    }

    public TableView<SalesCategoryUI.CashierSale> cashierSaleTableView(String c1, String c2, String c3) {

        TableView<SalesCategoryUI.CashierSale> cashierSaleTableView = new TableView<>();
        cashierSaleTableView.setPadding(new Insets(2.5));
        cashierSaleTableView.setStyle("-fx-border-color: blue; -fx-border-width: 2; -fx-border-radius: 10");
        cashierSaleTableView.autosize();

        TableColumn<SalesCategoryUI.CashierSale, String> cashierColumn = new TableColumn<>(c1);
        TableColumn<SalesCategoryUI.CashierSale, Float> amountColumn = new TableColumn<>(c2);
        TableColumn<SalesCategoryUI.CashierSale, Float> discountColumn = new TableColumn<>(c3);

        cashierColumn.setMinWidth(100);
        cashierColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        cashierSaleTableView.getColumns().addAll(cashierColumn, amountColumn, discountColumn);

        cashierSaleTableView.setOnMouseClicked(event -> {
            if (cashierSaleTable.getItems().isEmpty()) {
                event.consume();
            } else {
                String user = cashierSaleTable.getSelectionModel().getSelectedItem().getName();
                discountDetail.setItems(SaleCategoryData.getDiscountProduct(user, dateClicked));
            }
        });

        cashierSaleTableView.setRowFactory(param -> {
            TableRow<SalesCategoryUI.CashierSale> tableRow = new TableRow<>();
            tableRow.setOnMouseEntered(event -> {
                if (tableRow.isSelected()){
                    event.consume();
                }else if (tableRow.isEmpty()){
                    event.consume();
                }else {
                    cashierSaleTableView.requestFocus();
                    cashierSaleTableView.getFocusModel().focus(tableRow.getIndex());
                    tableRow.setStyle("-fx-background-color: rgba(0,0,255,0.15)");
                }
                tableRow.setOnMouseExited(event1 -> {
                    tableRow.setStyle("-fx-base: white");
                });
            });
            return tableRow;
        });

        return cashierSaleTableView;
    }

    public TableView<DiscountDetailTableDataClass> setDiscountDetailTableView(String c1, String c2, String c3) {

        TableView<DiscountDetailTableDataClass> discountDetailTable = new TableView<>();
        discountDetailTable.setPadding(new Insets(2.5));
        discountDetailTable.setStyle("-fx-border-color: blue; -fx-border-width: 2; -fx-border-radius: 10");
        discountDetailTable.autosize();

        TableColumn<DiscountDetailTableDataClass, String> productColumn = new TableColumn<>(c1);
        TableColumn<DiscountDetailTableDataClass, Float> saleColumn = new TableColumn<>(c2);
        TableColumn<DiscountDetailTableDataClass, Float> discountColumn = new TableColumn<>(c3);

        productColumn.setMinWidth(100);
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        saleColumn.setCellValueFactory(new PropertyValueFactory<>("sale"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        discountDetailTable.getColumns().addAll(productColumn, saleColumn, discountColumn);

        return discountDetailTable;

    }

    public TableView<ProductSellHistoryDataClass> setProductSellHistoryTableView() {
        TableView<ProductSellHistoryDataClass> tableView = new TableView<>();
        tableView.setPadding(new Insets(2.5));
        tableView.setStyle("-fx-border-color: blue; -fx-border-width: 2; -fx-border-radius: 10");
        tableView.autosize();

        TableColumn<ProductSellHistoryDataClass, String> dateColumn = new TableColumn<>("Date");
        TableColumn<ProductSellHistoryDataClass, Integer> quantityColumn = new TableColumn<>("Quantity");
        dateColumn.setMinWidth(100);

        tableView.getColumns().addAll(dateColumn, quantityColumn);

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        return tableView;
    }

    public GridPane navigationLeftPane() {
        Label fromLabel = new Label("From Date");
        Label toLabel = new Label("To Date");

        fromLabel.setFont(new Font(14));
        toLabel.setFont(new Font(14));

        fromLabel.setMinWidth(100);
        toLabel.setMinWidth(100);

        DatePicker fromDatePicker = new DatePicker();
        DatePicker toDatePicker = new DatePicker();

        Button refreshButton = new Button("Refresh");
        refreshButton.setDefaultButton(true);

        HBox fromDateHBox = new HBox();
        HBox toDateHBox = new HBox();
        fromDateHBox.getChildren().addAll(fromLabel, fromDatePicker);
        toDateHBox.getChildren().addAll(toLabel, toDatePicker);

        GridPane navigationPane = new GridPane();
        RowConstraints r1 = new RowConstraints(30);
        RowConstraints r2 = new RowConstraints(30);
        RowConstraints r3 = new RowConstraints(30);
        RowConstraints r4 = new RowConstraints();
        r4.setVgrow(Priority.ALWAYS);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(100);
        navigationPane.getColumnConstraints().addAll(c1);
        navigationPane.getRowConstraints().addAll(r1, r2, r3, r4);

        navigationPane.add(refreshButton, 0, 2);
        navigationPane.add(fromDateHBox, 0, 0);
        navigationPane.add(toDateHBox, 0, 1);
        navigationPane.add(salesTable, 0, 3);

        refreshButton.setOnAction(event -> {
            if (fromDatePicker.getEditor().getText().isEmpty()) {
                fromDatePicker.requestFocus();
                fromDatePicker.show();
            } else if (toDatePicker.getEditor().getText().isEmpty()) {
                toDatePicker.requestFocus();
                toDatePicker.show();
            } else {
                LocalDate fromDatePickerValue = fromDatePicker.getValue();
                LocalDate toDatePickerValue = toDatePicker.getValue();
                String from = fromDatePickerValue.getYear() + "-" +
                        fromDatePickerValue.getMonthValue() + "-" +
                        fromDatePickerValue.getDayOfMonth();
                String to = toDatePickerValue.getYear() + "-" +
                        toDatePickerValue.getMonthValue() + "-" +
                        toDatePickerValue.getDayOfMonth();

                salesTable.setItems(SaleCategoryData.getSales(from, to));
                cashierSaleTable.getItems().clear();
                discountDetail.getItems().clear();

                //update the graph
                salesGraph.getData().clear();
                for (XYChart.Series<String, Number> series : SaleCategoryData.getSalesByCategory(from, to)) {
                    salesGraph.getData().add(series);
                }
            }
        });

        return navigationPane;
    }

    public GridPane navigationLeftPaneProductReport() {
        Label fromLabel = new Label("From Date");
        Label toLabel = new Label("To Date");
        fromLabel.setFont(new Font(14));
        toLabel.setFont(new Font(14));
        fromLabel.setMinWidth(100);
        toLabel.setMinWidth(100);

        TextField filterProductTextField = new TextField();
        filterProductTextField.setPromptText("Search...");

        DatePicker fromDatePicker = new DatePicker();
        DatePicker toDatePicker = new DatePicker();

        Button refreshButton = new Button("Refresh");
        refreshButton.setDefaultButton(true);

        Date date = new Date(new java.util.Date().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, -7);
        java.util.Date dateBefore30Days = cal.getTime();
        String today = date.toString();
        String lastWeek = new Date(dateBefore30Days.getTime()).toString();

        fromDatePicker.getEditor().setText(lastWeek);
        toDatePicker.getEditor().setText(today);
        ObservableList<String> creditPurchaseProducts = PurchaseCategoryData
                .getCreditPurchaseProductHistory(lastWeek, today);
        ObservableList<String> cashPurchaseProducts = PurchaseCategoryData
                .getCashPurchaseProductHistory(lastWeek, today);
        ObservableList<String> cashSaleProducts = SaleCategoryData.getCashSaleProductHistory(lastWeek, today);

        for (String product1 : creditPurchaseProducts) {
            if (!cashPurchaseProducts.contains(product1)) {
                cashPurchaseProducts.add(product1);
            }
        }

        for (String string : cashSaleProducts) {
            if (!cashPurchaseProducts.contains(string)) {
                cashPurchaseProducts.add(string);
            }
        }

        productsHistory = cashPurchaseProducts;

        ListView<String> productListView = new ListView<>();
        productListView.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5");
        productListView.setItems(productsHistory);

        productListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> cell = new ListCellsCustom();
                cell.setOnMouseEntered(event -> {
                    if (cell.isSelected()) {
                        event.consume();
                    } else if (cell.getText() == null) {
                        event.consume();
                    } else {
                        cell.setStyle("-fx-background-color: rgba(0, 0, 255,0.15)");
                        productListView.requestFocus();
                        productListView.getFocusModel().focus(cell.getIndex());
                    }
                });
                cell.setOnMouseExited(event -> {
                    cell.setStyle("-fx-base: white");
                });

                return cell;
            }
        });

        HBox fromDateHBox = new HBox();
        HBox toDateHBox = new HBox();
        HBox refreshHBox = new HBox();
        refreshHBox.setSpacing(40);
        filterProductTextField.setAlignment(Pos.CENTER_LEFT);
        refreshHBox.getChildren().addAll(refreshButton, filterProductTextField);
        fromDateHBox.getChildren().addAll(fromLabel, fromDatePicker);
        toDateHBox.getChildren().addAll(toLabel, toDatePicker);

        GridPane navigationPane = new GridPane();
        RowConstraints r1 = new RowConstraints(30);
        RowConstraints r2 = new RowConstraints(30);
        RowConstraints r3 = new RowConstraints(30);
        RowConstraints r4 = new RowConstraints();
        r4.setVgrow(Priority.ALWAYS);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(100);
        navigationPane.getColumnConstraints().addAll(c1);
        navigationPane.getRowConstraints().addAll(r1, r2, r3, r4);

        navigationPane.add(refreshHBox, 0, 2);
        navigationPane.add(fromDateHBox, 0, 0);
        navigationPane.add(toDateHBox, 0, 1);
        navigationPane.add(productListView, 0, 3);

        refreshButton.setOnAction(event -> {
            if (fromDatePicker.getEditor().getText().isEmpty()) {
                fromDatePicker.requestFocus();
                fromDatePicker.show();
            } else if (toDatePicker.getEditor().getText().isEmpty()) {
                toDatePicker.requestFocus();
                toDatePicker.show();
            } else {
                try {
                    LocalDate fromDatePickerValue = fromDatePicker.getValue();
                    LocalDate toDatePickerValue = toDatePicker.getValue();
                    String from = fromDatePickerValue.getYear() + "-" +
                            fromDatePickerValue.getMonthValue() + "-" +
                            fromDatePickerValue.getDayOfMonth();
                    String to = toDatePickerValue.getYear() + "-" +
                            toDatePickerValue.getMonthValue() + "-" +
                            toDatePickerValue.getDayOfMonth();

                    //list view contents iterations

                    ObservableList<String> creditPurchaseProducts1 = PurchaseCategoryData
                            .getCreditPurchaseProductHistory(from, to);
                    ObservableList<String> cashPurchaseProducts1 = PurchaseCategoryData
                            .getCashPurchaseProductHistory(from, to);
                    ObservableList<String> cashSaleProducts1 = SaleCategoryData.getCashSaleProductHistory(from, to);

                    for (String product1 : creditPurchaseProducts1) {
                        if (!cashPurchaseProducts1.contains(product1)) {
                            cashPurchaseProducts1.add(product1);
                        }
                    }

                    for (String string : cashSaleProducts1) {
                        if (!cashPurchaseProducts1.contains(string)) {
                            cashPurchaseProducts1.add(string);
                        }
                    }

                    productsHistory = cashPurchaseProducts1;
                    //update product list view
                    productListView.setItems(productsHistory);

                    purchaseCashHistoryTable.getItems().clear();
                    sellHistoryTable.getItems().clear();
                    purchaseCreditHistoryTable.getItems().clear();

                    //update the graph
                    sellPurchaseChart.getData().clear();
                    productCashSaleFrequency = SaleCategoryData.getProductCashSaleFrequency(from, to);
                    int size = productCashSaleFrequency.getData().size();
                    modulus = size % 50;
                    multiples = size / 50;
                    spinner.getValueFactory().setValue(multiples + 1);
                    if (productCashSaleFrequency.getData().size() > 50) {
                        if (modulus == 0) {
                            List<XYChart.Data<String, Number>> data = productCashSaleFrequency.getData()
                                    .subList((multiples - 1) * 50, size);
                            ObservableList<XYChart.Data<String, Number>> seriesData = FXCollections.observableList(data);
                            sellPurchaseChart.getData().add(new XYChart.Series<>(seriesData));
                        } else {
                            List<XYChart.Data<String, Number>> data = productCashSaleFrequency.getData()
                                    .subList((multiples) * 50, size);
                            ObservableList<XYChart.Data<String, Number>> seriesData = FXCollections.observableList(data);
                            sellPurchaseChart.getData().add(new XYChart.Series<>(seriesData));
                        }

                    } else sellPurchaseChart.getData().add(productCashSaleFrequency);

                } catch (NullPointerException e) {
                    fromDatePicker.getEditor().clear();
                    toDatePicker.getEditor().clear();
                    fromDatePicker.requestFocus();
                    fromDatePicker.show();
                }
            }
        });
        filterProductTextField.setOnKeyReleased(event -> {
            String from;
            String to;
            try {
                LocalDate fromValue = fromDatePicker.getValue();
                LocalDate toValue = toDatePicker.getValue();
                from = fromValue.getYear() + "-" + fromValue.getMonthValue() + "-" + fromValue.getDayOfMonth();
                to = toValue.getYear() + "-" + toValue.getMonthValue() + "-" + toValue.getDayOfMonth();
            } catch (NullPointerException e) {
                from = fromDatePicker.getEditor().getText();
                to = toDatePicker.getEditor().getText();
            }

            ObservableList<String> creditPurchaseProducts1 = PurchaseCategoryData
                    .getCreditPurchaseProductHistory(from, to);
            ObservableList<String> cashPurchaseProducts1 = PurchaseCategoryData
                    .getCashPurchaseProductHistory(from, to);
            ObservableList<String> cashSaleProducts1 = SaleCategoryData.getCashSaleProductHistory(from, to);

            for (String product1 : creditPurchaseProducts1) {
                if (!cashPurchaseProducts1.contains(product1)) {
                    cashPurchaseProducts1.add(product1);
                }
            }

            for (String string : cashSaleProducts1) {
                if (!cashPurchaseProducts1.contains(string)) {
                    cashPurchaseProducts1.add(string);
                }
            }

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

                    productListView.getItems().clear();

                    for (String product : cashSaleProducts1) {

                        if (!filterProductTextField.getText().isEmpty()) {
                            String productEntered = filterProductTextField.getText().toLowerCase();
                            String productStock = product.toLowerCase();

                            if (productStock.contains(productEntered)) {

                                productListView.getItems().add(product);
                            }
                        }

                    }
                    if (filterProductTextField.getText().isEmpty()) productListView.setItems(cashSaleProducts1);
                    break;
                }

                default: {

                    productListView.getItems().clear();

                    for (String product : cashSaleProducts1) {

                        if (!filterProductTextField.getText().isEmpty()) {
                            String productEntered = filterProductTextField.getText().toLowerCase();
                            String productStock = product.toLowerCase();

                            if (productStock.contains(productEntered)) {

                                productListView.getItems().add(product);
                            }
                        }

                    }

                    break;
                }
            }

            if (filterProductTextField.getText().isEmpty()) {
                productListView.setItems(cashSaleProducts1);
            }
        });
        productListView.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY: {
                    if (fromDatePicker.getEditor().getText().isEmpty()) {
                        fromDatePicker.requestFocus();
                        fromDatePicker.show();
                    } else if (toDatePicker.getEditor().getText().isEmpty()) {
                        toDatePicker.requestFocus();
                        toDatePicker.show();
                    } else if (productListView.getItems().isEmpty()) {
                        event.consume();
                    } else {
                        String from;
                        String to;
                        try {
                            LocalDate fromValue = fromDatePicker.getValue();
                            LocalDate toValue = toDatePicker.getValue();
                            from = fromValue.getYear() + "-" + fromValue.getMonthValue() + "-" + fromValue.getDayOfMonth();
                            to = toValue.getYear() + "-" + toValue.getMonthValue() + "-" + toValue.getDayOfMonth();
                        } catch (NullPointerException e) {
                            from = fromDatePicker.getEditor().getText();
                            to = toDatePicker.getEditor().getText();
                        }
                        String product = productListView.getSelectionModel().getSelectedItem();
                        purchaseCreditHistoryTable.setItems(PurchaseCategoryData.getCreditPurchasedHistory(
                                from,
                                to,
                                product
                        ));
                        purchaseCashHistoryTable.setItems(PurchaseCategoryData.getCashPurchasedHistory(
                                from,
                                to,
                                product
                        ));
                        sellHistoryTable.setItems(SaleCategoryData.getCashSaleHistory(from, to, product));
                    }
                }
            }
        });

        return navigationPane;
    }

    public AreaChart<String, Number> setSalesGraphUI() {

        Date date = new Date(new java.util.Date().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, -7);
        java.util.Date dateBefore30Days = cal.getTime();
        String today = date.toString();
        String lastWeek = new Date(dateBefore30Days.getTime()).toString();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Amount");
        yAxis.autosize();
        xAxis.autosize();

        AreaChart<String, Number> salesGraph = new AreaChart<>(xAxis, yAxis);
        salesGraph.setTitle("Sales Per Day");
        salesGraph.setStyle("-fx-border-color: blue; -fx-border-width: 2; -fx-border-radius: 5");

        for (XYChart.Series<String, Number> series : SaleCategoryData.getSalesByCategory(lastWeek, today)) {
            salesGraph.getData().add(series);
        }
        return salesGraph;

    }

    public AreaChart<String, Number> setPotentialProductGraphUI() {

        Date date = new Date(new java.util.Date().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, -7);
        java.util.Date dateBefore30Days = cal.getTime();
        String today = date.toString();
        String lastWeek = new Date(dateBefore30Days.getTime()).toString();


        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Product");
        yAxis.setLabel("Sell Frequency");
        yAxis.autosize();
        xAxis.autosize();

        AreaChart<String, Number> salesGraph = new AreaChart<>(xAxis, yAxis);
        salesGraph.setAnimated(true);
        salesGraph.setTitle("Potential Product Measure");
        salesGraph.setStyle("-fx-border-color: blue; -fx-border-width: 2; -fx-border-radius: 5");

        productCashSaleFrequency = SaleCategoryData.getProductCashSaleFrequency(lastWeek, today);
        int size = productCashSaleFrequency.getData().size();
        modulus = size % 50;
        multiples = size / 50;
        if (productCashSaleFrequency.getData().size() > 50) {
            if (modulus == 0) {
                List<XYChart.Data<String, Number>> data = productCashSaleFrequency.getData()
                        .subList((multiples - 1) * 50, size);
                ObservableList<XYChart.Data<String, Number>> seriesData = FXCollections.observableList(data);
                salesGraph.getData().add(new XYChart.Series<>(seriesData));
            } else {
                List<XYChart.Data<String, Number>> data = productCashSaleFrequency.getData()
                        .subList((multiples) * 50, size);
                ObservableList<XYChart.Data<String, Number>> seriesData = FXCollections.observableList(data);
                salesGraph.getData().add(new XYChart.Series<>(seriesData));
            }

        } else salesGraph.getData().add(productCashSaleFrequency);


        return salesGraph;

    }

    public SplitPane mainTaskUI(
            TableView table1,
            TableView table2,
            TableView table3,
            AreaChart areaChart,
            int rooms) {

        SplitPane splitPaneVertical = new SplitPane();
        splitPaneVertical.setPadding(new Insets(0, 0, 0, 6));
        splitPaneVertical.setOrientation(Orientation.VERTICAL);
        splitPaneVertical.setDividerPositions(0.3);

        SplitPane splitPaneHorizontal = new SplitPane();
        splitPaneHorizontal.setOrientation(Orientation.HORIZONTAL);
        if (rooms == 2) {

            splitPaneHorizontal.setDividerPositions(0.5);
            splitPaneHorizontal.getItems().addAll(table1, table2);
            splitPaneVertical.getItems().addAll(splitPaneHorizontal, areaChart);

        } else if (rooms == 3) {

            splitPaneHorizontal.setDividerPositions(0.35, 0.7);
            splitPaneHorizontal.getItems().addAll(table1, table2, table3);
            GridPane gridPane = new GridPane();
            ColumnConstraints c1 = new ColumnConstraints();
            RowConstraints r1 = new RowConstraints(30);
            RowConstraints r2 = new RowConstraints();
            c1.setHgrow(Priority.ALWAYS);
            r2.setVgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(c1);
            gridPane.getRowConstraints().addAll(r1, r2);

            spinner = new Spinner<>();
            spinner.setValueFactory(new SpinnerValueFactory<Number>() {
                @Override
                public void decrement(int steps) {
                    int i = spinner.getValue().intValue() - steps;
                    if (i > 0) {
                        spinner.getValueFactory().setValue(i);
                        //change the graph
                        if (productCashSaleFrequency.getData().size() > 50) {

                            List<XYChart.Data<String, Number>> data = productCashSaleFrequency.getData()
                                    .subList((i - 1) * 50, i * 50);
                            ObservableList<XYChart.Data<String, Number>> seriesData = FXCollections.observableList(data);
                            sellPurchaseChart.getData().clear();
                            sellPurchaseChart.getData().add(new XYChart.Series<>(seriesData));

                        } else sellPurchaseChart.getData().add(productCashSaleFrequency);
                    }
                }

                @Override
                public void increment(int steps) {
                    int i = spinner.getValue().intValue() + steps;
                    if (i <= multiples + 1) {
                        spinner.getValueFactory().setValue(i);
                        //change the graph
                        if (i == (multiples + 1)) {
                            if (productCashSaleFrequency.getData().size() > 50) {

                                List<XYChart.Data<String, Number>> data = productCashSaleFrequency.getData()
                                        .subList(multiples * 50, productCashSaleFrequency.getData().size());
                                ObservableList<XYChart.Data<String, Number>> seriesData = FXCollections
                                        .observableList(data);
                                sellPurchaseChart.getData().clear();
                                sellPurchaseChart.getData().add(new XYChart.Series<>(seriesData));

                            } else sellPurchaseChart.getData().add(productCashSaleFrequency);

                        } else {
                            if (productCashSaleFrequency.getData().size() > 50) {

                                List<XYChart.Data<String, Number>> data = productCashSaleFrequency.getData()
                                        .subList((i - 1) * 50, i * 50);
                                ObservableList<XYChart.Data<String, Number>> seriesData = FXCollections
                                        .observableList(data);
                                sellPurchaseChart.getData().clear();
                                sellPurchaseChart.getData().add(new XYChart.Series<>(seriesData));

                            } else sellPurchaseChart.getData().add(productCashSaleFrequency);
                        }
                    }
                }
            });
            gridPane.add(spinner, 0, 0);
            gridPane.add(areaChart, 0, 1);

            splitPaneVertical.getItems().addAll(splitPaneHorizontal, gridPane);

        }

        return splitPaneVertical;
    }

    public static class SalesTableDataClass {
        public final SimpleStringProperty date;
        public final SimpleFloatProperty sale;
        public final SimpleFloatProperty discount;

        public SalesTableDataClass(String date, float sale, float discount) {
            this.date = new SimpleStringProperty(date);
            this.sale = new SimpleFloatProperty(sale);
            this.discount = new SimpleFloatProperty(discount);
        }

        public String getDate() {
            return date.get();
        }

        public float getDiscount() {
            return discount.get();
        }

        public float getSale() {
            return sale.get();
        }
    }

    public static class DiscountDetailTableDataClass {
        public final SimpleStringProperty product;
        public final SimpleFloatProperty sale;
        public final SimpleFloatProperty discount;

        public DiscountDetailTableDataClass(String product, float sale, float discount) {
            this.product = new SimpleStringProperty(product);
            this.sale = new SimpleFloatProperty(sale);
            this.discount = new SimpleFloatProperty(discount);
        }

        public float getSale() {
            return sale.get();
        }

        public String getProduct() {
            return product.get();
        }

        public float getDiscount() {
            return discount.get();
        }
    }

    public static class ProductSellHistoryDataClass {
        public final SimpleStringProperty date;
        public final SimpleIntegerProperty quantity;

        public ProductSellHistoryDataClass(String date, int quantity) {
            this.date = new SimpleStringProperty(date);
            this.quantity = new SimpleIntegerProperty(quantity);
        }

        public String getDate() {
            return date.get();
        }

        public int getQuantity() {
            return quantity.get();
        }
    }
}
