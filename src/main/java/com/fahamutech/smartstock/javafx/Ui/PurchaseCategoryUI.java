package com.fahamutech.smartstock.javafx.Ui;

import com.fahamutech.smartstock.javafx.dataFactory.PurchaseCategoryData;
import com.fahamutech.smartstock.javafx.dataFactory.StockCategoryData;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Popup;

import java.text.NumberFormat;
import java.time.LocalDate;

public class PurchaseCategoryUI {

    static Popup popup;
    static Popup popupAddReceipt;
    static Popup popupAddInvoice;
    static Popup popupRemoveSupplier;
    static Popup popupRemoveReceipt;
    static Popup popupRemoveInvoice;

    public static ObservableList<DueInvoiceListTable> creditPurchaseData;
    private static ObservableList<InvoiceDetail> invoiceDetailsData;


    PurchaseCategoryUI() {
        addSupplier();
        removeSupplier();
        addReceipt();
        removeReceipt();
        addInvoice();
        removeInvoice();

    }

    GridPane setDueInvoiceUI() {

        //controls
        Label selectSupplierLabel = new Label("Choose Supplier :");
        Label selectInvoiceLabel = new Label("Choose Invoice :");
        Label invoiceDetailTitleLabel = new Label();
        Label nearDueLabel = new Label("Remain <= 5days :");
        Label expiredInvoice = new Label("Expired Invoice :");
        Label totalUnpaidLabel = new Label("Total Unpaid :");
        Label totalCredit = new Label("Total Credit :");

        ComboBox<String> supplierChooser = new ComboBox<>();
        ComboBox<String> invoiceChooser = new ComboBox<>();

        supplierChooser.setEditable(true);
        invoiceChooser.setEditable(true);

        TextField nearDueTextField = new TextField();
        TextField unpaidTextField = new TextField();
        TextField expireInvoiceTextField = new TextField();
        TextField totalAmountTextField = new TextField();
        TextField filterProductTextField = new TextField();

        HBox supplierHBox = new HBox();
        HBox invoiceHBox = new HBox();
        HBox nearDueHBox = new HBox();
        HBox expiredHBox = new HBox();
        HBox notPaidHBox = new HBox();
        HBox totalAmountHBox = new HBox();
        HBox loadDataHBox = new HBox();
        HBox payCreditHBox = new HBox();

        Button loadDataButton = new Button("Load Invoice Data");
        Button payButton = new Button("Pay Invoice");
        Button cancelButton = new Button("Cancel");

        loadDataButton.setStyle("-fx-base: #ea2121");
        payButton.setStyle("-fx-base: #ea2121");
        cancelButton.setStyle("-fx-base: #ea2121");


        supplierHBox.setSpacing(50);
        invoiceHBox.setSpacing(50);
        nearDueHBox.setSpacing(50);
        expiredHBox.setSpacing(50);
        notPaidHBox.setSpacing(50);
        totalAmountHBox.setSpacing(50);
        loadDataHBox.setSpacing(50);
        payCreditHBox.setSpacing(10);
        payCreditHBox.setPadding(new Insets(10, 0, 0, 0));

        nearDueTextField.setMinWidth(184);
        unpaidTextField.setMinWidth(184);
        expireInvoiceTextField.setMinWidth(184);
        totalAmountTextField.setMinWidth(184);

        supplierChooser.setMinWidth(184);
        invoiceChooser.setMinWidth(184);

        nearDueTextField.setEditable(false);
        unpaidTextField.setEditable(false);
        expireInvoiceTextField.setEditable(false);
        totalAmountTextField.setEditable(false);
        filterProductTextField.setPromptText("Search...");
        nearDueTextField.setStyle("-fx-background-color: #ea2121");
        unpaidTextField.setStyle("-fx-background-color: #ea2121");
        expireInvoiceTextField.setStyle("-fx-background-color: #ea2121");
        totalAmountTextField.setStyle("-fx-background-color: #ea2121");

        selectInvoiceLabel.setFont(new Font(14));
        selectSupplierLabel.setFont(new Font(14));
        invoiceDetailTitleLabel.setFont(new Font(20));
        nearDueLabel.setFont(new Font(14));
        expiredInvoice.setFont(new Font(14));
        totalUnpaidLabel.setFont(new Font(14));
        totalCredit.setFont(new Font(14));
        selectSupplierLabel.setMinWidth(150);
        selectInvoiceLabel.setMinWidth(150);
        nearDueLabel.setMinWidth(150);
        expiredInvoice.setMinWidth(150);
        totalUnpaidLabel.setMinWidth(150);
        totalCredit.setMinWidth(150);

        nearDueTextField.setText(PurchaseCategoryData.getNearExpireInvoice());
        expireInvoiceTextField.setText(PurchaseCategoryData.getExpiredInvoice());
        unpaidTextField.setText(PurchaseCategoryData.getUnPaidProductsCount());
        totalAmountTextField.setText(PurchaseCategoryData.getTotalAmountOfNotPaidCreditPurchase());


        //TableView to show detail of invoice
        TableView<InvoiceDetail> invoiceDetailTable = new TableView<>();
        invoiceDetailsData = FXCollections.observableArrayList();
        invoiceDetailTable.setItems(invoiceDetailsData);
        invoiceDetailTable.setStyle(
                "-fx-border-color: #ea2121;" +
                        " -fx-border-width:2 ;" +
                        "-fx-border-radius:5 ;" +
                        "-fx-padding:2.5");
        TableColumn<InvoiceDetail, String> productDetailColumn = new TableColumn<>("Product");
        TableColumn<InvoiceDetail, String> unitDetailColumn = new TableColumn<>("Unit");
        TableColumn<InvoiceDetail, Integer> quantityDetailColumn = new TableColumn<>("Quantity");
        TableColumn<InvoiceDetail, Float> purchaseDetailColumn = new TableColumn<>("purchase(TZS)");
        TableColumn<InvoiceDetail, Float> amountDetailColumn = new TableColumn<>("Amount(TZS)");
        invoiceDetailTable.getColumns().addAll(
                productDetailColumn,
                unitDetailColumn,
                quantityDetailColumn,
                purchaseDetailColumn,
                amountDetailColumn
        );
        productDetailColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        unitDetailColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        quantityDetailColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchaseDetailColumn.setCellValueFactory(new PropertyValueFactory<>("purchase"));
        amountDetailColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        productDetailColumn.setMinWidth(250);


        //table contain all unpaid Invoice
        TableView<DueInvoiceListTable> dueInvoiceListTableView = new TableView<>();
        creditPurchaseData = FXCollections.observableArrayList();
        PurchaseCategoryData.getCreditPurchasedProduct();
        dueInvoiceListTableView.setItems(creditPurchaseData);
        dueInvoiceListTableView.setStyle(
                "-fx-border-color: #ea2121;" +
                        " -fx-border-width:2 ;" +
                        "-fx-border-radius:5 ;" +
                        "-fx-padding:2.5");
        TableColumn<DueInvoiceListTable, String> invoiceNumberColumn = new TableColumn<>("Invoice#");
        TableColumn<DueInvoiceListTable, Integer> daysRemainColumn = new TableColumn<>("Remains Day");
        TableColumn<DueInvoiceListTable, String> dueDateColumn = new TableColumn<>("Date");
        TableColumn<DueInvoiceListTable, String> statusColumn = new TableColumn<>("Status");
        TableColumn<DueInvoiceListTable, String> supplierColumn = new TableColumn<>("Supplier");
        supplierColumn.setPrefWidth(200);
        dueInvoiceListTableView.getColumns().addAll(
                invoiceNumberColumn,
                supplierColumn,
                daysRemainColumn,
                dueDateColumn,
                statusColumn
        );

        daysRemainColumn.setPrefWidth(50);
        invoiceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        daysRemainColumn.setCellValueFactory(new PropertyValueFactory<>("remain"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("due"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        //pane to hold control input and detail of the invoice
        supplierHBox.getChildren().addAll(selectSupplierLabel, supplierChooser);
        invoiceHBox.getChildren().addAll(selectInvoiceLabel, invoiceChooser);
        nearDueHBox.getChildren().addAll(nearDueLabel, nearDueTextField);
        notPaidHBox.getChildren().addAll(totalUnpaidLabel, unpaidTextField);
        expiredHBox.getChildren().addAll(expiredInvoice, expireInvoiceTextField);
        loadDataHBox.getChildren().addAll(loadDataButton, invoiceDetailTitleLabel, filterProductTextField);
        totalAmountHBox.getChildren().addAll(totalCredit, totalAmountTextField);
        payCreditHBox.getChildren().addAll(payButton, cancelButton);

        VBox dataControlVBox = new VBox();
        dataControlVBox.setSpacing(8);
        dataControlVBox.setPadding(new Insets(0, 10, 0, 0));
        dataControlVBox.getChildren().addAll(
                supplierHBox,
                invoiceHBox,
                dueInvoiceListTableView,
                nearDueHBox,
                expiredHBox,
                notPaidHBox,
                totalAmountHBox
        );

        GridPane invoiceDetailTableView = new GridPane();
        RowConstraints r1 = new RowConstraints(30);
        RowConstraints r2 = new RowConstraints();
        RowConstraints r3 = new RowConstraints(30);
        ColumnConstraints c1 = new ColumnConstraints();
        invoiceDetailTableView.getColumnConstraints().add(c1);
        invoiceDetailTableView.getRowConstraints().addAll(r1, r2, r3);
        r2.setVgrow(Priority.ALWAYS);
        c1.setHgrow(Priority.ALWAYS);
        invoiceDetailTableView.add(loadDataHBox, 0, 0);
        invoiceDetailTableView.add(invoiceDetailTable, 0, 1);
        invoiceDetailTableView.add(payCreditHBox, 0, 2);


        GridPane dueInvoiceUI = new GridPane();
        dueInvoiceUI.setPadding(new Insets(10, 10, 10, 10));
        dueInvoiceUI.setId("due_invoice");
        RowConstraints row0 = new RowConstraints();
        ColumnConstraints col0 = new ColumnConstraints(400);
        ColumnConstraints col1 = new ColumnConstraints();
        dueInvoiceUI.getRowConstraints().add(row0);
        dueInvoiceUI.getColumnConstraints().addAll(col0, col1);
        row0.setVgrow(Priority.ALWAYS);
        col1.setHgrow(Priority.ALWAYS);
        dueInvoiceUI.add(dataControlVBox, 0, 0);
        dueInvoiceUI.add(invoiceDetailTableView, 1, 0);

        invoiceDetailTable.setRowFactory((TableView<InvoiceDetail> param) -> {
            TableRow<InvoiceDetail> tableRow = new TableRow<>();
            tableRow.setOnMouseEntered(event -> {
                if (tableRow.isSelected()) {
                    event.consume();
                } else if (tableRow.isEmpty()) {
                    event.consume();
                } else {
                    invoiceDetailTable.requestFocus();
                    invoiceDetailTable.getFocusModel().focus(tableRow.getIndex());
                    tableRow.setStyle("-fx-background-color: rgba(0,0,255,0.15)");
                }
                tableRow.setOnMouseExited(event1 -> {
                    tableRow.setStyle("-fx-base: white");
                });
            });
            return tableRow;
        });

        dueInvoiceListTableView.setRowFactory((TableView<DueInvoiceListTable> param) -> {
            TableRow<DueInvoiceListTable> tableRow = new TableRow<>();
            tableRow.setOnMouseEntered(event -> {
                if (tableRow.isSelected()) {
                    event.consume();
                } else if (tableRow.isEmpty()) {
                    event.consume();
                } else {
                    dueInvoiceListTableView.requestFocus();
                    dueInvoiceListTableView.getFocusModel().focus(tableRow.getIndex());
                    tableRow.setStyle("-fx-background-color: rgba(0,0,255,0.15)");
                }
                tableRow.setOnMouseExited(event1 -> tableRow.setStyle("-fx-base: white"));
            });
            return tableRow;
        });

        filterProductTextField.setOnKeyReleased(event -> {
            String invoice = invoiceChooser.getSelectionModel().getSelectedItem();
            String supplier = supplierChooser.getSelectionModel().getSelectedItem();

            ObservableList<InvoiceDetail> invoiceDetails = PurchaseCategoryData.getCreditInvoiceDetail(invoice, supplier);

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

                    invoiceDetailTable.getItems().clear();

                    for (InvoiceDetail invoiceDetail : invoiceDetails) {

                        if (!filterProductTextField.getText().isEmpty()) {
                            String product = filterProductTextField.getText().toLowerCase();
                            String productInvoice = invoiceDetail.getProduct().toLowerCase();

                            if (productInvoice.contains(product)) {

                                invoiceDetailTable.getItems().add(invoiceDetail);
                            }
                        }

                    }

                    //if the filter text field is empty
                    if (filterProductTextField.getText().isEmpty()) {
                        invoiceDetailTable.setItems(invoiceDetails);
                    }
                    break;
                }

                default: {

                    invoiceDetailTable.getItems().clear();
                    for (InvoiceDetail invoiceDetail : invoiceDetails) {

                        if (!filterProductTextField.getText().isEmpty()) {
                            String product = filterProductTextField.getText().toLowerCase();
                            String productInvoice = invoiceDetail.getProduct().toLowerCase();

                            if (productInvoice.contains(product)) {

                                invoiceDetailTable.getItems().add(invoiceDetail);
                            }
                        }

                    }
                    break;
                }
            }

        });

        dueInvoiceListTableView.setOnMouseClicked(event -> {
            if (dueInvoiceListTableView.getItems().isEmpty()) {
                event.consume();
            } else if (dueInvoiceListTableView.getSelectionModel().isEmpty()) {
                event.consume();
            } else {
                String invoice = dueInvoiceListTableView.getSelectionModel().getSelectedItem().getInvoice();
                String supplier = dueInvoiceListTableView.getSelectionModel().getSelectedItem().getSupplier();

                supplierChooser.getSelectionModel().select(supplier);
                invoiceChooser.getSelectionModel().select(invoice);

                //load resources into the detail invoice table view
                invoiceDetailTable.setItems(PurchaseCategoryData.getCreditInvoiceDetail(invoice, supplier));
                String total = NumberFormat.getInstance().format(PurchaseCategoryData.amountOfCreditInvoice);
                invoiceDetailTitleLabel.setText(total + " TZS");

            }
        });

        //implement supplier chooser and invoice chooser
        supplierChooser.setOnMouseClicked(event -> supplierChooser.setItems(PurchaseCategoryData.getSuppliers()));
        invoiceChooser.setOnMouseClicked(event -> {
            if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
                supplierChooser.show();
            } else {
                String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                if (PurchaseCategoryData.getDueInvoices(supplier).isEmpty()) {
                    invoiceChooser.setItems(FXCollections.observableArrayList("All Credit Paid"));
                } else invoiceChooser.setItems(PurchaseCategoryData.getDueInvoices(supplier));
            }
        });
        //implement load resources button
        loadDataButton.setOnAction(event -> {
            if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
                supplierChooser.show();
            } else if (invoiceChooser.getSelectionModel().isEmpty()) {
                invoiceChooser.requestFocus();
            } else {
                invoiceDetailTable.getItems().clear();
                String invoice = invoiceChooser.getSelectionModel().getSelectedItem();
                String supplier = supplierChooser.getSelectionModel().getSelectedItem();

                //get detail of the invoice number and add to the table by using static field reference
                invoiceDetailTable.setItems(PurchaseCategoryData.getCreditInvoiceDetail(invoice, supplier));

                //find total amount of the invoice number
                String formatAmount = NumberFormat.getInstance().format(PurchaseCategoryData.amountOfCreditInvoice);
                invoiceDetailTitleLabel.setText(formatAmount + " TZS");
            }
        });
        //implements pay button
        payButton.setOnAction(event -> {
            if (invoiceDetailTable.getItems().isEmpty()) {
                loadDataButton.requestFocus();
            } else {

                String invoice = invoiceChooser.getSelectionModel().getSelectedItem();
                PurchaseCategoryData.payCreditInvoice(invoice);
                //clear all the inputs
                supplierChooser.getSelectionModel().clearSelection();
                invoiceChooser.getSelectionModel().clearSelection();
                invoiceDetailTable.getItems().clear();
                invoiceDetailTitleLabel.setText("0 TZS");
                //updates some info
                nearDueTextField.setText(PurchaseCategoryData.getNearExpireInvoice());
                expireInvoiceTextField.setText(PurchaseCategoryData.getExpiredInvoice());
                unpaidTextField.setText(PurchaseCategoryData.getUnPaidProductsCount());
                totalAmountTextField.setText(PurchaseCategoryData.getTotalAmountOfNotPaidCreditPurchase());
                dueInvoiceListTableView.getItems().clear();

                PurchaseCategoryData.getCreditPurchasedProduct();
            }
        });
        //implement cancel button
        cancelButton.setOnAction(event -> {
            //clear all the inputs
            supplierChooser.getSelectionModel().clearSelection();
            supplierChooser.getEditor().clear();
            invoiceChooser.getSelectionModel().clearSelection();
            invoiceChooser.getEditor().clear();
            invoiceDetailTable.getItems().clear();
            invoiceDetailTitleLabel.setText("0 TZS");
        });


        return dueInvoiceUI;
    }

    //set credit purchase Ui
    GridPane setCreditPurchaseUI() {

        Label purchaseTitle = new Label("CREDIT PURCHASE");
        Label dateOfPurchaseLabel = new Label("Purchase Date :");
        Label dueDateLabel = new Label("Due Date :");
        Label invoiceNumberLabel = new Label("Invoice Number :");
        Label productLabel = new Label("Product Name :");
        Label supplierLabel = new Label("Choose Supplier :");
        Label quantityLabel = new Label("Quantity :");
        Label wQuantityLabel = new Label("WholeSale Quantity");
        Label expireLabel = new Label("Expire Date :");
        Label totalAmountLabel = new Label("Total Amount :");
        Label filterPurchaseProduct = new Label("Filter Product :");

        Label unitLabel = new Label("Unit Of Product :");
        Label categoryLabel = new Label("Category Of Product :");
        Label reorderLevelLabel = new Label("Reorder Level :");
        Label purchaseLabel = new Label("Purchase Price(TZS) : ");
        Label sellLabel = new Label("RetailSale Price(TZS) :");
        Label wSellLabel = new Label("WholeSale Price(TZS) :");
        Label profitLabel = new Label("Profit(TSZ) :");
        Label timesLabel = new Label("Times :");
        Label shelfLabel = new Label("Shelf :");

        purchaseTitle.setFont(new Font(16));
        invoiceNumberLabel.setFont(new Font(14));
        dateOfPurchaseLabel.setFont(new Font(14));

        productLabel.setFont(new Font(14));
        unitLabel.setFont(new Font(14));
        wQuantityLabel.setFont(new Font(14));
        wSellLabel.setFont(new Font(14));
        totalAmountLabel.setFont(new Font(14));
        filterPurchaseProduct.setFont(new Font(14));
        categoryLabel.setFont(new Font(14));
        quantityLabel.setFont(new Font(14));
        reorderLevelLabel.setFont(new Font(14));
        supplierLabel.setFont(new Font(14));
        purchaseLabel.setFont(new Font(14));
        sellLabel.setFont(new Font(14));
        profitLabel.setFont(new Font(14));
        timesLabel.setFont(new Font(14));
        expireLabel.setFont(new Font(14));
        shelfLabel.setFont(new Font(14));

        //Observable list for combo box
        ComboBox<String> invoiceNumberChooser = new ComboBox<>(PurchaseCategoryData.getAllInvoice());
        ComboBox<String> productChooser = new ComboBox<>(StockCategoryData.getProductNames());
        ComboBox<String> supplierChooser = new ComboBox<>(PurchaseCategoryData.getSuppliers());

        supplierChooser.setMinWidth(138);
        productChooser.setEditable(true);
        supplierChooser.setEditable(true);
        invoiceNumberChooser.setEditable(true);

        DatePicker dateOfPurchase = new DatePicker();
        DatePicker dateOfExpire = new DatePicker();
        DatePicker dueDatePicker = new DatePicker();

        TextField unitTextField = new TextField();
        TextField categoryTextField = new TextField();
        TextField quantityTextField = new TextField();
        TextField wQuantityTextField = new TextField();
        TextField reorderLeverTextField = new TextField();
        TextField purchaseTextField = new TextField();
        TextField sellTextField = new TextField();
        TextField profitTextField = new TextField();
        TextField timesTextField = new TextField();
        TextField shelfTextField = new TextField();
        TextField totalAmountTextField = new TextField();
        TextField filterPurchaseTextField = new TextField();
        TextField wSellTextField = new TextField();

        filterPurchaseTextField.setPromptText("Search...");
        timesTextField.setEditable(false);
        profitTextField.setEditable(false);
        unitTextField.setEditable(false);
        categoryTextField.setEditable(false);
        reorderLeverTextField.setEditable(false);
        shelfTextField.setEditable(false);
        quantityTextField.setPromptText("click me...");
        unitTextField.setStyle("-fx-background-color: #ea2121");
        categoryTextField.setStyle("-fx-background-color: #ea2121");
        reorderLeverTextField.setStyle("-fx-background-color: #ea2121");
        totalAmountTextField.setStyle("-fx-background-color: #ea2121");

        profitTextField.setStyle("-fx-background-color: #ea2121");
        timesTextField.setStyle("-fx-background-color: #ea2121");
        shelfTextField.setStyle("-fx-background-color: #ea2121");

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        Button refreshButton = new Button("Refresh List");

        submitButton.setDefaultButton(true);
        refreshButton.setDefaultButton(true);

        TableView<CreditPurchaseList> purchaseListTableView = new TableView<>();
        ObservableList<CreditPurchaseList> purchaseListsData = FXCollections.observableArrayList();
        purchaseListTableView.setItems(purchaseListsData);
        purchaseListTableView.autosize();
        purchaseListTableView.setStyle(
                "-fx-border-color: #ea2121;" +
                        " -fx-border-width:2 ;" +
                        "-fx-border-radius:5 ;" +
                        "-fx-padding:2.5");

        TableColumn<CreditPurchaseList, String> purchaseDateColumn = new TableColumn<>("Purchase Date");
        TableColumn<CreditPurchaseList, String> dueDateColumn = new TableColumn<>("Due Date");
        TableColumn<CreditPurchaseList, String> invoiceNumberColumn = new TableColumn<>("Invoice #");
        TableColumn<CreditPurchaseList, String> productNameColumn = new TableColumn<>("Product");
        TableColumn<CreditPurchaseList, String> unitColumn = new TableColumn<>("Unit");
        TableColumn<CreditPurchaseList, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<CreditPurchaseList, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<CreditPurchaseList, String> supplierColumn = new TableColumn<>("Supplier");
        TableColumn<CreditPurchaseList, Float> purchaseColumn = new TableColumn<>("Purchase(TZS)/Unit");
        TableColumn<CreditPurchaseList, Float> totalAmount = new TableColumn<>("Total Amount");
        TableColumn<CreditPurchaseList, String> statusColumn = new TableColumn<>("Status");

        purchaseListTableView.getColumns().addAll(
                purchaseDateColumn,
                dueDateColumn,
                invoiceNumberColumn,
                productNameColumn,
                unitColumn,
                categoryColumn,
                quantityColumn,
                supplierColumn,
                purchaseColumn,
                totalAmount,
                statusColumn
        );
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("due"));
        invoiceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("purchase"));
        totalAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        //resources input panel
        GridPane dataInputGridPane = new GridPane();
        dataInputGridPane.setVgap(4);
        ColumnConstraints inputCol0 = new ColumnConstraints(150);
        ColumnConstraints inputCol1 = new ColumnConstraints();
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
        RowConstraints rw10 = new RowConstraints(30);
        RowConstraints rw11 = new RowConstraints(30);
        RowConstraints rw12 = new RowConstraints(30);
        RowConstraints rw13 = new RowConstraints(30);
        RowConstraints rw14 = new RowConstraints(30);
        RowConstraints rw15 = new RowConstraints(30);
        dataInputGridPane.getColumnConstraints().addAll(inputCol0, inputCol1);
        dataInputGridPane.getRowConstraints()
                .addAll(rw0, rw1, rw2, rw3, rw4, rw5, rw6, rw7, rw8, rw9, rw10, rw11, rw12, rw13, rw14, rw15);

        //add content to the pane
        dataInputGridPane.add(dateOfPurchaseLabel, 0, 0);
        dataInputGridPane.add(dateOfPurchase, 1, 0);
        dataInputGridPane.add(dueDateLabel, 0, 1);
        dataInputGridPane.add(dueDatePicker, 1, 1);
        dataInputGridPane.add(invoiceNumberLabel, 0, 2);
        dataInputGridPane.add(invoiceNumberChooser, 1, 2);
        dataInputGridPane.add(supplierLabel, 0, 3);
        dataInputGridPane.add(supplierChooser, 1, 3);
        dataInputGridPane.add(productLabel, 0, 4);
        dataInputGridPane.add(productChooser, 1, 4);
        dataInputGridPane.add(quantityLabel, 0, 5);
        dataInputGridPane.add(quantityTextField, 1, 5);
        dataInputGridPane.add(wQuantityLabel, 0, 6);
        dataInputGridPane.add(wQuantityTextField, 1, 6);
        dataInputGridPane.add(purchaseLabel, 0, 7);
        dataInputGridPane.add(purchaseTextField, 1, 7);
        dataInputGridPane.add(sellLabel, 0, 8);
        dataInputGridPane.add(sellTextField, 1, 8);
        dataInputGridPane.add(wSellLabel, 0, 9);
        dataInputGridPane.add(wSellTextField, 1, 9);
        dataInputGridPane.add(unitLabel, 0, 10);
        dataInputGridPane.add(unitTextField, 1, 10);
        dataInputGridPane.add(categoryLabel, 0, 11);
        dataInputGridPane.add(categoryTextField, 1, 11);
        dataInputGridPane.add(reorderLevelLabel, 0, 12);
        dataInputGridPane.add(reorderLeverTextField, 1, 12);
        dataInputGridPane.add(profitLabel, 0, 13);
        dataInputGridPane.add(profitTextField, 1, 13);
        dataInputGridPane.add(timesLabel, 0, 14);
        dataInputGridPane.add(timesTextField, 1, 14);
        dataInputGridPane.add(shelfLabel, 0, 15);
        dataInputGridPane.add(shelfTextField, 1, 15);
        dataInputGridPane.add(expireLabel, 0, 16);
        dataInputGridPane.add(dateOfExpire, 1, 16);

        //put resources input pane into vbox pane
        VBox dataInputPaneVBox = new VBox();
        HBox savePaneHBox = new HBox();
        savePaneHBox.setSpacing(90);
        savePaneHBox.getChildren().addAll(submitButton, cancelButton);
        dataInputPaneVBox.setAlignment(Pos.TOP_CENTER);
        dataInputPaneVBox.getChildren().addAll(purchaseTitle, dataInputGridPane, savePaneHBox);


        //preview table view
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(
                totalAmountLabel,
                totalAmountTextField,
                refreshButton,
                filterPurchaseProduct,
                filterPurchaseTextField);

        GridPane previewTableGridPane = new GridPane();
        previewTableGridPane.setPadding(new Insets(6, 0, 0, 4));
        previewTableGridPane.setVgap(6);
        RowConstraints r = new RowConstraints(25);
        RowConstraints r1 = new RowConstraints();
        ColumnConstraints c = new ColumnConstraints();
        r1.setVgrow(Priority.ALWAYS);
        c.setHgrow(Priority.ALWAYS);
        previewTableGridPane.getColumnConstraints().addAll(c);
        previewTableGridPane.getRowConstraints().addAll(r, r1);

        previewTableGridPane.add(hBox, 0, 0);
        previewTableGridPane.add(purchaseListTableView, 0, 1);

        GridPane creditPurchaseUI = new GridPane();
        creditPurchaseUI.setPadding(new Insets(10, 10, 10, 10));
        creditPurchaseUI.setId("credit_purchase");
        RowConstraints row0 = new RowConstraints();
        ColumnConstraints col0 = new ColumnConstraints(300);
        ColumnConstraints col1 = new ColumnConstraints();
        creditPurchaseUI.getRowConstraints().add(row0);
        creditPurchaseUI.getColumnConstraints().addAll(col0, col1);
        row0.setVgrow(Priority.ALWAYS);
        col1.setHgrow(Priority.ALWAYS);
        creditPurchaseUI.add(dataInputPaneVBox, 0, 0);
        creditPurchaseUI.add(previewTableGridPane, 1, 0);

        purchaseListTableView.setRowFactory((TableView<CreditPurchaseList> param) -> {
            TableRow<CreditPurchaseList> tableRow = new TableRow<>();
            tableRow.setOnMouseEntered(event -> {
                if (tableRow.isSelected()) {
                    event.consume();
                } else if (tableRow.isEmpty()) {
                    event.consume();
                } else {
                    purchaseListTableView.requestFocus();
                    purchaseListTableView.getFocusModel().focus(tableRow.getIndex());
                    tableRow.setStyle("-fx-background-color: rgba(0,0,255,0.15)");
                }
                tableRow.setOnMouseExited(event1 -> tableRow.setStyle("-fx-base: white"));
            });
            return tableRow;
        });
        dateOfPurchase.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    dueDatePicker.requestFocus();
                    dueDatePicker.show();
                }
            }
        });
        dueDatePicker.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    invoiceNumberChooser.requestFocus();
                    invoiceNumberChooser.show();
                }
            }
        });
        invoiceNumberChooser.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (invoiceNumberChooser.getSelectionModel().isEmpty()) {
                        invoiceNumberChooser.requestFocus();
                        invoiceNumberChooser.show();
                    } else {
                        supplierChooser.requestFocus();
                        supplierChooser.show();
                    }
                }
            }
        });
        supplierChooser.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (supplierChooser.getSelectionModel().isEmpty()) {
                        supplierChooser.requestFocus();
                        supplierChooser.show();
                    } else {
                        productChooser.requestFocus();
                        productChooser.show();
                    }
                }
            }
        });
        productChooser.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (productChooser.getSelectionModel().isEmpty()) {
                        productChooser.requestFocus();
                        productChooser.show();
                    } else {
                        String product = productChooser.getSelectionModel().getSelectedItem();

                        wQuantityTextField.setText(String.valueOf(StockCategoryData.getWholeProductQuantity(product)));
                        purchaseTextField.setText(String.valueOf(StockCategoryData.getPurchase(product)));
                        categoryTextField.setText(StockCategoryData.getCategory(product));
                        unitTextField.setText(StockCategoryData.getUnit(product));
                        reorderLeverTextField.setText(String.valueOf(StockCategoryData.getReorderLevel(product)));
                        StockCategoryData.getSellPrice(product);
                        sellTextField.setText(String.valueOf(StockCategoryData.sellPrice));
                        StockCategoryData.getWholeSellPrice(product);
                        wSellTextField.setText(String.valueOf(StockCategoryData.wSellPrice));
                        profitTextField.setText(String.valueOf(StockCategoryData.getProfit(product)));
                        timesTextField.setText(String.valueOf(StockCategoryData.sellPrice
                                / StockCategoryData.getPurchase(product)));
                        shelfTextField.setText(StockCategoryData.getShelf(product));

                        quantityTextField.requestFocus();
                    }
                }
            }
        });
        quantityTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (quantityTextField.getText().isEmpty()) quantityTextField.requestFocus();
                    else wQuantityTextField.requestFocus();
                }
            }

        });
        wQuantityTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (wQuantityTextField.getText().isEmpty()) wQuantityTextField.requestFocus();
                    else purchaseTextField.requestFocus();
                }
            }
        });
        purchaseTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (purchaseTextField.getText().isEmpty()) purchaseTextField.requestFocus();
                    else sellTextField.requestFocus();
                }
            }
        });
        sellTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (sellTextField.getText().isEmpty()) sellTextField.requestFocus();
                    else wSellTextField.requestFocus();
                }
            }
        });
        wSellTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (wSellTextField.getText().isEmpty()) wSellTextField.requestFocus();
                    else {
                        dateOfExpire.requestFocus();
                        dateOfExpire.show();
                    }
                }
            }
        });
        dateOfExpire.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    submitButton.requestFocus();
                }
            }
        });

        refreshButton.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Fill\nDate of purchase\nInvoice number\nSupplier name\nThen, Try to refresh");
            if (dateOfPurchase.getEditor().getText().isEmpty()) {
                dateOfPurchase.requestFocus();
                dateOfPurchase.show();
                alert.show();
            } else if (invoiceNumberChooser.getSelectionModel().isEmpty()) {
                invoiceNumberChooser.requestFocus();
                invoiceNumberChooser.show();
                alert.show();
            } else if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.show();
                alert.show();
            } else {
                LocalDate value = dateOfPurchase.getValue();

                String date;
                if (value.getDayOfMonth() < 0) {
                    int day = value.getDayOfMonth();
                    date = value.getYear() + "-" + value.getMonthValue() + "-0" + day;
                } else {
                    date = value.getYear() + "-" + value.getMonthValue() + "-" + value.getDayOfMonth();
                }
                String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                //supplier
                String receipt = invoiceNumberChooser.getSelectionModel().getSelectedItem();

                purchaseListTableView.getItems().clear();
                purchaseListTableView.setItems(PurchaseCategoryData.getCurrentCreditPurchase(date, receipt, supplier));
                float sum = PurchaseCategoryData.getAmountOfCurrentCreditPurchase(date, receipt, supplier);
                totalAmountTextField.setText(NumberFormat.getInstance().format(sum) + " TZS");

            }
        });

        filterPurchaseTextField.setOnKeyReleased(event -> {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill\nDate of purchase\nReceipt number\nSupplier name\nThen Try Again");
            if (dateOfPurchase.getEditor().getText().isEmpty()) {
                dateOfPurchase.requestFocus();
                dateOfPurchase.show();
                alert.show();
            } else if (invoiceNumberChooser.getSelectionModel().isEmpty()) {
                invoiceNumberChooser.requestFocus();
                invoiceNumberChooser.show();
                alert.show();
            } else if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.show();
                alert.show();
            } else {
                LocalDate value = dateOfPurchase.getValue();

                String date;
                if (value.getDayOfMonth() < 0) {
                    int day = value.getDayOfMonth();
                    date = value.getYear() + "-" + value.getMonthValue() + "-0" + day;
                } else {
                    date = value.getYear() + "-" + value.getMonthValue() + "-" + value.getDayOfMonth();
                }

                String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                String receipt = invoiceNumberChooser.getSelectionModel().getSelectedItem();

                purchaseListTableView.getItems().clear();

                ObservableList<CreditPurchaseList> purchases
                        = PurchaseCategoryData.getCurrentCreditPurchase(date, receipt, supplier);

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

                        purchaseListTableView.getItems().clear();
                        for (CreditPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    purchaseListTableView.getItems().add(purchaseList);
                                }
                            }

                        }


                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }
                        break;
                    }

                    default: {

                        purchaseListTableView.getItems().clear();
                        for (CreditPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    purchaseListTableView.getItems().add(purchaseList);
                                }
                            }

                        }


                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }

                    }
                }
            }
        });

        purchaseListTableView.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case SECONDARY: {
                    if (purchaseListTableView.getItems().isEmpty()) {
                        event.consume();
                    } else {
                        double x = event.getSceneX();
                        double y = event.getSceneY();
                        Popup popUp = new Popup();
                        popUp.setX(x);
                        popUp.setY(y);

                        ListView<String> list = new ListView<>();
                        ObservableList<String> data = FXCollections.observableArrayList();
                        data.addAll("Refresh", "Update");
                        list.setPadding(new Insets(6, 6, 6, 6));
                        list.setPrefSize(100, 100);
                        list.setItems(data);
                        list.setStyle("-fx-base: white");

                        popUp.getContent().addAll(list);
                        popUp.setAutoHide(true);
                        popUp.show(MainStage.stageAdmin);

                        list.setOnMouseClicked(event1 -> {
                            if (list.getSelectionModel().getSelectedIndex() == 1) {
                                //popup to set a new supplier
                                Popup popupUpdatePurchase = new Popup();
                                //control fields
                                Label dateLabel = new Label("Date Of Purchase :");
                                Label quantityUpdateLabel = new Label("Quantity :");
                                Label purchaseUpdateLabel = new Label("Purchase (TZS) :");
                                Label sellingUpdateLabel = new Label("Selling (TZS) :");

                                dateLabel.setFont(new Font(14));
                                quantityUpdateLabel.setFont(new Font(14));
                                purchaseUpdateLabel.setFont(new Font(14));
                                sellingUpdateLabel.setFont(new Font(14));

                                TextField dateofUpdatePurchaseTextField = new TextField();
                                TextField quantityPurchasedTextField = new TextField();
                                TextField purchasePriceTextField = new TextField();
                                TextField sellingPriceTextField = new TextField();

                                String dateOld = purchaseListTableView.getSelectionModel().getSelectedItem().getDate();
                                int quant = purchaseListTableView.getSelectionModel().getSelectedItem().getQuantity();
                                float purchase1 = purchaseListTableView.getSelectionModel().getSelectedItem().getPurchase();
                                StockCategoryData
                                        .getSellPrice(purchaseListTableView.getSelectionModel().getSelectedItem()
                                                .getProduct());
                                float sel = StockCategoryData.sellPrice;

                                dateofUpdatePurchaseTextField.setText(dateOld);
                                quantityPurchasedTextField.setText(String.valueOf(quant));
                                purchasePriceTextField.setText(String.valueOf(purchase1));
                                sellingPriceTextField.setText(String.valueOf(sel));


                                Button updateButton = new Button("Update");
                                Button cancelEditButton = new Button("Cancel");
                                updateButton.setPrefWidth(164);
                                updateButton.setStyle("-fx-base: #ea2121");
                                cancelEditButton.setStyle("-fx-base: #ea2121");

                                //container of pop up
                                GridPane gridPane = new GridPane();
                                gridPane.setPadding(new Insets(16, 16, 16, 16));
                                gridPane.setStyle("-fx-background-color: white");
                                gridPane.setVgap(6);
                                gridPane.setHgap(4);
                                ColumnConstraints column0 = new ColumnConstraints(150);
                                ColumnConstraints column1 = new ColumnConstraints();
                                RowConstraints rw_0 = new RowConstraints(40);
                                RowConstraints rw_1 = new RowConstraints(40);
                                RowConstraints rw_2 = new RowConstraints(40);
                                RowConstraints rw_3 = new RowConstraints(40);
                                RowConstraints rw_4 = new RowConstraints(40);

                                gridPane.getColumnConstraints().addAll(column0, column1);
                                gridPane.getRowConstraints().addAll(rw_0, rw_1, rw_2, rw_3, rw_4);
                                gridPane.add(dateLabel, 0, 0);
                                gridPane.add(dateofUpdatePurchaseTextField, 1, 0);
                                gridPane.add(quantityUpdateLabel, 0, 1);
                                gridPane.add(quantityPurchasedTextField, 1, 1);
                                gridPane.add(purchaseUpdateLabel, 0, 2);
                                gridPane.add(purchasePriceTextField, 1, 2);
                                gridPane.add(sellingUpdateLabel, 0, 3);
                                gridPane.add(sellingPriceTextField, 1, 3);
                                gridPane.add(updateButton, 0, 4);
                                gridPane.add(cancelEditButton, 1, 4);

                                //make a black ribbon around the add supplier popup
                                VBox vBox = new VBox();
                                vBox.setPadding(new Insets(4, 4, 4, 4));
                                vBox.setStyle("-fx-background-color: #000000");
                                vBox.getChildren().add(gridPane);

                                //popup.setAutoHide(true);
                                popupUpdatePurchase.centerOnScreen();
                                popupUpdatePurchase.setAutoHide(true);
                                popupUpdatePurchase.getContent().add(vBox);

                                cancelEditButton.setOnAction(event11 -> {
                                    dateofUpdatePurchaseTextField.clear();
                                    quantityPurchasedTextField.clear();
                                    purchasePriceTextField.clear();
                                    sellingPriceTextField.clear();
                                    popupUpdatePurchase.hide();
                                });

                                updateButton.setOnAction(event2 -> {
                                    int quantity;
                                    float purchase;
                                    float sell;
                                    String productCondiotion = purchaseListTableView
                                            .getSelectionModel()
                                            .getSelectedItem().getProduct();
                                    String dateCondition = purchaseListTableView
                                            .getSelectionModel()
                                            .getSelectedItem().getDate();
                                    String supplierCopndition = purchaseListTableView
                                            .getSelectionModel()
                                            .getSelectedItem().getSupplier();
                                    String invoiceCondition = purchaseListTableView
                                            .getSelectionModel()
                                            .getSelectedItem().getInvoice();
                                    String dateNew = dateofUpdatePurchaseTextField.getText().trim();
                                    int pQuantity = purchaseListTableView
                                            .getSelectionModel()
                                            .getSelectedItem().getQuantity();

                                    try {
                                        quantity = Integer.parseInt(quantityPurchasedTextField.getText());
                                        purchase = Float.parseFloat(purchasePriceTextField.getText());
                                        sell = Float.parseFloat(sellingPriceTextField.getText());

                                        //update product quantity and sell in stock
                                        int stockQuantity = StockCategoryData.getProductQuantity(productCondiotion);
                                        int minusQuantity = Integer.parseInt(quantityPurchasedTextField.getText())
                                                - pQuantity;
                                        int nStockQuantity = stockQuantity + minusQuantity;

                                        //update product quantity in stock
                                        StockCategoryData.updateProductQuantity(productCondiotion, nStockQuantity);

                                        //update product in cash purchase table in databases
                                        PurchaseCategoryData.updateCreditPurchaseProductParameter(
                                                dateCondition,
                                                invoiceCondition,
                                                supplierCopndition,
                                                productCondiotion,
                                                dateNew,
                                                quantity,
                                                purchase

                                        );

                                        //update sell price in stock
                                        StockCategoryData.updateSellPrice(productCondiotion, sell);

                                        //refresh the table
                                        LocalDate value = dateOfPurchase.getValue();

                                        String date;
                                        if (value.getDayOfMonth() < 0) {
                                            int day = value.getDayOfMonth();
                                            date = value.getYear() + "-" + value.getMonthValue() + "-0" + day;
                                        } else {
                                            date = value.getYear() + "-" + value.getMonthValue() + "-" + value.getDayOfMonth();
                                        }

                                        String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                                        String receipt = invoiceNumberChooser.getSelectionModel().getSelectedItem();

                                        purchaseListTableView.setItems(PurchaseCategoryData
                                                .getCurrentCreditPurchase(date, receipt, supplier));
                                        float sum = PurchaseCategoryData
                                                .getAmountOfCurrentCreditPurchase(date, receipt, supplier);
                                        totalAmountTextField.setText(NumberFormat.getInstance().format(sum) + " TZS");

                                        //hide the popup
                                        popupUpdatePurchase.hide();

                                    } catch (Throwable t) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setContentText("There is an error\n" +
                                                "Make sure quantity, sell and purchase are numerical");
                                        alert.show();
                                    }

                                });

                                popUp.hide();
                                popupUpdatePurchase.show(MainStage.stageAdmin);

                            } else if (list.getSelectionModel().getSelectedIndex() == 0) {
                                //refresh the table
                                LocalDate value = dateOfPurchase.getValue();

                                String date;
                                if (value.getDayOfMonth() < 0) {
                                    int day = value.getDayOfMonth();
                                    date = value.getYear() + "-" + value.getMonthValue() + "-0" + day;
                                } else {
                                    date = value.getYear() + "-" + value.getMonthValue() + "-" + value.getDayOfMonth();
                                }

                                String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                                String receipt = invoiceNumberChooser.getSelectionModel().getSelectedItem();

                                purchaseListTableView.setItems(PurchaseCategoryData
                                        .getCurrentCreditPurchase(date, receipt, supplier));
                                float sum = PurchaseCategoryData
                                        .getAmountOfCurrentCreditPurchase(date, receipt, supplier);
                                totalAmountTextField.setText(NumberFormat.getInstance().format(sum) + " TZS");
                                popUp.hide();

                            }
                        });
                    }
                }
            }
        });

        supplierChooser.setOnMouseClicked(event -> supplierChooser.show());
        supplierChooser.getEditor().setOnMouseClicked(event -> {
            supplierChooser.show();
        });
        supplierChooser.getEditor().setOnKeyReleased(event -> {
            //update list of combo box
            ObservableList<String> products = PurchaseCategoryData.getSuppliers();

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
                case BACK_SPACE:
                    //show the updated list
                    if (!supplierChooser.getSelectionModel().isEmpty()) {
                        supplierChooser.getSelectionModel().clearSelection();
                    }
                    supplierChooser.show();
                    ObservableList<String> filter = FXCollections.observableArrayList();
                    //filter the products with the word typed
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = supplierChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            filter.add(product);
                        }

                    }
                    if (supplierChooser.getEditor().getText().isEmpty()) {
                        supplierChooser.setItems(products);
                    }
                    if (filter.isEmpty()) filter.add("No such supplier");
                    //update the list to match typed word
                    supplierChooser.setItems(filter);
                    break;
                default:
                    supplierChooser.show();
                    ObservableList<String> filter1 = FXCollections.observableArrayList();
                    //filter the products with the word typed
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = supplierChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            filter1.add(product);
                        }

                    }
                    if (supplierChooser.getEditor().getText().isEmpty()) {
                        supplierChooser.setItems(products);
                    }
                    if (filter1.isEmpty()) filter1.add("No such supplier");
                    //update the list to match typed word
                    supplierChooser.setItems(filter1);
                    break;
            }

            supplierChooser.show();
        });

        invoiceNumberChooser.setOnMouseClicked(event ->
                invoiceNumberChooser.setItems(PurchaseCategoryData.getAllInvoice()));
        invoiceNumberChooser.getEditor().setOnMouseClicked(event -> {
            invoiceNumberChooser.setItems(PurchaseCategoryData.getAllInvoice());
            invoiceNumberChooser.show();
        });
        invoiceNumberChooser.getEditor().setOnKeyReleased(event -> {
            //update list of combo box
            ObservableList<String> products = PurchaseCategoryData.getAllInvoice();

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
                case BACK_SPACE:
                    //show the updated list
                    invoiceNumberChooser.getItems().clear();
                    //filter the products with the word typed
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = invoiceNumberChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            invoiceNumberChooser.getItems().add(product);
                        }

                    }
                    if (invoiceNumberChooser.getEditor().getText().isEmpty()) {
                        invoiceNumberChooser.setItems(products);
                    }
                    if (invoiceNumberChooser.getItems().isEmpty())
                        invoiceNumberChooser.getItems().add("No such invoice");

                    break;
                default:
                    invoiceNumberChooser.getItems().clear();
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = invoiceNumberChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            invoiceNumberChooser.getItems().add(product);
                        }

                    }
                    if (invoiceNumberChooser.getEditor().getText().isEmpty()) {
                        invoiceNumberChooser.setItems(products);
                    }
                    if (invoiceNumberChooser.getItems().isEmpty())
                        invoiceNumberChooser.getItems().add("No such invoice");

                    break;
            }

            invoiceNumberChooser.show();
        });

        //oen date of on click
        dateOfPurchase.getEditor().setOnMouseClicked(event -> dateOfPurchase.show());
        dateOfExpire.getEditor().setOnMouseClicked(event -> dateOfExpire.show());
        dueDatePicker.getEditor().setOnMouseClicked(event -> dueDatePicker.show());
        //implement product chooser combo box
        productChooser.getEditor().setOnMouseClicked(event -> {
            productChooser.setItems(StockCategoryData.getProductNames());
            productChooser.show();
        });
        productChooser.setOnMouseClicked(event -> productChooser
                .setItems(StockCategoryData.getProductNames()));
        productChooser.getEditor().setOnKeyReleased(event -> {
            //update list of combo box
            ObservableList<String> products = StockCategoryData.getProductNames();
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
                case BACK_SPACE:
                    //clear selection and update variable depend on selection
                    if (productChooser.getEditor().getText().isEmpty()) {
                        unitTextField.clear();
                        sellTextField.clear();
                        categoryTextField.clear();
                        shelfTextField.clear();
                        profitTextField.clear();
                        reorderLeverTextField.clear();
                        purchaseTextField.clear();
                        timesTextField.clear();
                    }
                    //filter the products with the word typed
                    productChooser.getItems().clear();
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = productChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            productChooser.getItems().add(product);
                        }
                    }
                    if (productChooser.getItems().isEmpty()) productChooser.getItems().add("No such item in stock");
                    //update the list to match typed word
                    break;
                default:
                    productChooser.getItems().clear();
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = productChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            productChooser.getItems().add(product);
                        }
                    }
                    if (productChooser.getItems().isEmpty()) productChooser.getItems().add("No such item in stock");
                    break;
            }

            productChooser.show();
        });

        //implement quantity text field
        quantityTextField.setOnMouseClicked(event -> {
            if (productChooser.getSelectionModel().isEmpty()) {
                productChooser.requestFocus();
                productChooser.setItems(StockCategoryData.getProductNames());
                productChooser.show();
            } else {
                String product = productChooser.getSelectionModel().getSelectedItem();

                wQuantityTextField.setText(String.valueOf(StockCategoryData.getWholeProductQuantity(product)));
                purchaseTextField.setText(String.valueOf(StockCategoryData.getPurchase(product)));
                categoryTextField.setText(StockCategoryData.getCategory(product));
                unitTextField.setText(StockCategoryData.getUnit(product));
                reorderLeverTextField.setText(String.valueOf(StockCategoryData.getReorderLevel(product)));
                StockCategoryData.getSellPrice(product);
                sellTextField.setText(String.valueOf(StockCategoryData.sellPrice));
                StockCategoryData.getWholeSellPrice(product);
                wSellTextField.setText(String.valueOf(StockCategoryData.wSellPrice));
                profitTextField.setText(String.valueOf(StockCategoryData.getProfit(product)));
                timesTextField.setText(String.valueOf(StockCategoryData.sellPrice
                        / StockCategoryData.getPurchase(product)));
                shelfTextField.setText(StockCategoryData.getShelf(product));
            }
        });

        purchaseTextField.setOnKeyReleased(event -> {

            if (event.getCode().isDigitKey()) {
                if (quantityTextField.getText().isEmpty()) {
                    quantityTextField.requestFocus();
                    quantityTextField.clear();
                } else if (sellTextField.getText().isEmpty()) {
                    event.consume();
                } else {
                    try {
                        //for times text field
                        float times = Float.parseFloat(sellTextField.getText())
                                / Float.parseFloat(purchaseTextField.getText());
                        //for profit text field
                        float profits = Float.parseFloat(sellTextField.getText()) -
                                Float.parseFloat(purchaseTextField.getText());

                        profitTextField.setText(String.valueOf(profits));
                        timesTextField.setText(String.valueOf(times));

                    } catch (NumberFormatException ignore) {
                        profitTextField.setText(String.valueOf(0));
                        timesTextField.setText(String.valueOf(0));
                    }
                }

            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                try {
                    //for times text field
                    float times = Float.parseFloat(sellTextField.getText())
                            / Float.parseFloat(purchaseTextField.getText());
                    //for profit text field
                    float profits = Float.parseFloat(sellTextField.getText()) -
                            Float.parseFloat(purchaseTextField.getText());

                    profitTextField.setText(String.valueOf(profits));
                    timesTextField.setText(String.valueOf(times));

                } catch (NumberFormatException e) {
                    profitTextField.setText(String.valueOf(0));
                    timesTextField.setText(String.valueOf(0));
                }
            }
        });
        sellTextField.setOnKeyReleased(event -> {

            if (event.getCode().isDigitKey()) {
                if (quantityTextField.getText().isEmpty()) {
                    quantityTextField.requestFocus();
                    quantityTextField.clear();
                } else if (purchaseTextField.getText().isEmpty()) {
                    event.consume();
                } else {
                    try {
                        //for times text field
                        float times = Float.parseFloat(sellTextField.getText())
                                / Float.parseFloat(purchaseTextField.getText());
                        //for profit text field
                        float profits = Float.parseFloat(sellTextField.getText()) -
                                Float.parseFloat(purchaseTextField.getText());

                        profitTextField.setText(String.valueOf(profits));
                        timesTextField.setText(String.valueOf(times));

                    } catch (NumberFormatException ignore) {
                        profitTextField.setText(String.valueOf(0));
                        timesTextField.setText(String.valueOf(0));
                    }
                }

            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                try {
                    //for times text field
                    float times = Float.parseFloat(sellTextField.getText())
                            / Float.parseFloat(purchaseTextField.getText());
                    //for profit text field
                    float profits = Float.parseFloat(sellTextField.getText()) -
                            Float.parseFloat(purchaseTextField.getText());

                    profitTextField.setText(String.valueOf(profits));
                    timesTextField.setText(String.valueOf(times));

                } catch (NumberFormatException e) {
                    profitTextField.setText(String.valueOf(0));
                    timesTextField.setText(String.valueOf(0));
                }
            }
        });
        //implements buttons
        cancelButton.setOnAction(event -> {

            productChooser.getSelectionModel().clearSelection();
            quantityTextField.clear();
            wQuantityTextField.clear();
            purchaseTextField.clear();
            dateOfExpire.getEditor().clear();
            categoryTextField.clear();
            reorderLeverTextField.clear();
            unitTextField.clear();
            sellTextField.clear();
            wSellTextField.clear();
            profitTextField.clear();
            timesTextField.clear();
            shelfTextField.clear();

        });
        submitButton.setOnAction(event -> {
            if (dateOfPurchase.getEditor().getText().isEmpty()) {
                dateOfPurchase.requestFocus();
                dateOfPurchase.show();
            } else if (dueDatePicker.getEditor().getText().isEmpty()) {
                dueDatePicker.requestFocus();
                dueDatePicker.show();
            } else if (invoiceNumberChooser.getSelectionModel().isEmpty()) {
                invoiceNumberChooser.requestFocus();
                invoiceNumberChooser.show();
            } else if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
                supplierChooser.show();
            } else if (productChooser.getSelectionModel().isEmpty()) {
                productChooser.requestFocus();
                productChooser.setItems(StockCategoryData.getProductNames());
                productChooser.show();
            } else if (quantityTextField.getText().isEmpty()) quantityTextField.requestFocus();
            else if (wQuantityTextField.getText().isEmpty()) wQuantityTextField.requestFocus();
            else if (purchaseTextField.getText().isEmpty()) purchaseTextField.requestFocus();
            else if (sellTextField.getText().isEmpty()) sellTextField.requestFocus();
            else if (wSellTextField.getText().isEmpty()) wSellTextField.requestFocus();
            else if (dateOfExpire.getEditor().getText().isEmpty()) {
                dateOfExpire.requestFocus();
                dateOfExpire.show();
            }
            //if everything is good add to the purchase list and update the stock quantity
            else {

                String product = productChooser.getSelectionModel().getSelectedItem();

                int quantity;
                int wQuantity;
                float purchase;
                float sell;
                float wSell;
                try {
                    quantity = Integer.parseInt(quantityTextField.getText());
                    wQuantity = Integer.parseInt(wQuantityTextField.getText());
                    purchase = Float.parseFloat(purchaseTextField.getText());
                    sell = Float.parseFloat(sellTextField.getText());
                    wSell = Float.parseFloat(wSellTextField.getText());


                    //update information of a stock
                    int oldStockQuantity = StockCategoryData.getProductQuantity(product);
                    quantity = oldStockQuantity + quantity;
                    StockCategoryData.updateProductQuantity(product, quantity);
                    StockCategoryData.updatePurchasePrice(product, purchase);
                    StockCategoryData.updateCurrentSupplier(product, supplierChooser.getSelectionModel().getSelectedItem());
                    StockCategoryData.updateSellPrice(product, sell);
                    StockCategoryData.updateWholeQuantityOfProduct(product, wQuantity);
                    StockCategoryData.updateWholeSalePrice(product, wSell);

                    //insert resources into credit purchase
                    String date;
                    if (dateOfPurchase.getValue().getDayOfMonth() < 0) {
                        int day = dateOfPurchase.getValue().getDayOfMonth();
                        date = dateOfPurchase.getValue().getYear() + "-"
                                + dateOfPurchase.getValue().getMonthValue() + "-0" + day;
                    } else {
                        date = dateOfPurchase.getValue().getYear() + "-"
                                + dateOfPurchase.getValue().getMonthValue() + "-"
                                + dateOfPurchase.getValue().getDayOfMonth();
                    }

                    date = dateOfPurchase.getValue().getYear() + "-"
                            + dateOfPurchase.getValue().getMonthValue() + "-" + dateOfPurchase.getValue().getDayOfMonth();
                    String dueDate = dueDatePicker.getValue().getYear() + "-" +
                            dueDatePicker.getValue().getMonthValue() + "-" + dueDatePicker.getValue().getDayOfMonth();
                    String invoice = invoiceNumberChooser.getSelectionModel().getSelectedItem();
                    String category = categoryTextField.getText();
                    String unit = unitTextField.getText();
                    String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                    int quantity1 = Integer.parseInt(quantityTextField.getText());
                    float purchase1 = Float.parseFloat(purchaseTextField.getText());
                    float amount = quantity1 * purchase1;
                    //insert resources into databases
                    PurchaseCategoryData.insertDataIntoCreditPurchase(
                            date,
                            dueDate,
                            invoice,
                            product,
                            category,
                            unit,
                            supplier,
                            oldStockQuantity,
                            quantity1,
                            purchase1,
                            amount
                    );

                    //update total amount of the current purchase
                    float sum = PurchaseCategoryData.getAmountOfCurrentCreditPurchase(date, invoice, supplier);
                    totalAmountTextField.setText(NumberFormat.getInstance().format(sum) + " TZS");

                    //insert resources into current purchase preview table
                    purchaseListTableView.getItems().add(new CreditPurchaseList(
                            date,
                            dueDate,
                            invoice,
                            product,
                            category,
                            unit,
                            supplier,
                            quantity1,
                            purchase,
                            amount,
                            "Not Paid"
                    ));

                    //clear the input panel
                    productChooser.getSelectionModel().clearSelection();
                    quantityTextField.clear();
                    wQuantityTextField.clear();
                    purchaseTextField.clear();
                    dateOfExpire.getEditor().clear();
                    categoryTextField.clear();
                    reorderLeverTextField.clear();
                    unitTextField.clear();
                    sellTextField.clear();
                    wSellTextField.clear();
                    profitTextField.clear();
                    timesTextField.clear();
                    shelfTextField.clear();

                    productChooser.requestFocus();

                } catch (Throwable t) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Unsuccessful\nCheck Your Numeric Values");
                }
            }
        });
        submitButton.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (dateOfPurchase.getEditor().getText().isEmpty()) {
                        dateOfPurchase.requestFocus();
                        dateOfPurchase.show();
                    } else if (dueDatePicker.getEditor().getText().isEmpty()) {
                        dueDatePicker.requestFocus();
                        dueDatePicker.show();
                    } else if (invoiceNumberChooser.getSelectionModel().isEmpty()) {
                        invoiceNumberChooser.requestFocus();
                        invoiceNumberChooser.show();
                    } else if (supplierChooser.getSelectionModel().isEmpty()) {
                        supplierChooser.requestFocus();
                        supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
                        supplierChooser.show();
                    } else if (productChooser.getSelectionModel().isEmpty()) {
                        productChooser.requestFocus();
                        productChooser.setItems(StockCategoryData.getProductNames());
                        productChooser.show();
                    } else if (quantityTextField.getText().isEmpty()) quantityTextField.requestFocus();
                    else if (wQuantityTextField.getText().isEmpty()) wQuantityTextField.requestFocus();
                    else if (purchaseTextField.getText().isEmpty()) purchaseTextField.requestFocus();
                    else if (sellTextField.getText().isEmpty()) sellTextField.requestFocus();
                    else if (wSellTextField.getText().isEmpty()) wSellTextField.requestFocus();
                    else if (dateOfExpire.getEditor().getText().isEmpty()) {
                        dateOfExpire.requestFocus();
                        dateOfExpire.show();
                    }
                    //if everything is good add to the purchase list and update the stock quantity
                    else {

                        String product = productChooser.getSelectionModel().getSelectedItem();

                        int quantity;
                        int wQuantity;
                        float purchase;
                        float sell;
                        float wSell;
                        try {
                            quantity = Integer.parseInt(quantityTextField.getText());
                            wQuantity = Integer.parseInt(wQuantityTextField.getText());
                            purchase = Float.parseFloat(purchaseTextField.getText());
                            sell = Float.parseFloat(sellTextField.getText());
                            wSell = Float.parseFloat(wSellTextField.getText());


                            //update information of a stock
                            int oldStockQuantity=StockCategoryData.getProductQuantity(product);
                            quantity = oldStockQuantity + quantity;
                            StockCategoryData.updateProductQuantity(product, quantity);
                            StockCategoryData.updatePurchasePrice(product, purchase);
                            StockCategoryData.updateCurrentSupplier(product, supplierChooser.getSelectionModel().getSelectedItem());
                            StockCategoryData.updateSellPrice(product, sell);
                            StockCategoryData.updateWholeQuantityOfProduct(product, wQuantity);
                            StockCategoryData.updateWholeSalePrice(product, wSell);

                            //insert resources into credit purchase
                            String date;
                            if (dateOfPurchase.getValue().getDayOfMonth() < 0) {
                                int day = dateOfPurchase.getValue().getDayOfMonth();
                                date = dateOfPurchase.getValue().getYear() + "-"
                                        + dateOfPurchase.getValue().getMonthValue() + "-0" + day;
                            } else {
                                date = dateOfPurchase.getValue().getYear() + "-"
                                        + dateOfPurchase.getValue().getMonthValue() + "-"
                                        + dateOfPurchase.getValue().getDayOfMonth();
                            }
                            date = dateOfPurchase.getValue().getYear() + "-"
                                    + dateOfPurchase.getValue().getMonthValue() + "-" + dateOfPurchase.getValue().getDayOfMonth();
                            String dueDate = dueDatePicker.getValue().getYear() + "-" +
                                    dueDatePicker.getValue().getMonthValue() + "-" + dueDatePicker.getValue().getDayOfMonth();
                            String invoice = invoiceNumberChooser.getSelectionModel().getSelectedItem();
                            String category = categoryTextField.getText();
                            String unit = unitTextField.getText();
                            String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                            int quantity1 = Integer.parseInt(quantityTextField.getText());
                            float purchase1 = Float.parseFloat(purchaseTextField.getText());
                            float amount = quantity1 * purchase1;
                            PurchaseCategoryData.insertDataIntoCreditPurchase(
                                    date,
                                    dueDate,
                                    invoice,
                                    product,
                                    category,
                                    unit,
                                    supplier,
                                    oldStockQuantity,
                                    quantity1,
                                    purchase1,
                                    amount
                            );

                            //update total amount of the current purchase
                            float sum = PurchaseCategoryData.getAmountOfCurrentCreditPurchase(date, invoice, supplier);
                            totalAmountTextField.setText(NumberFormat.getInstance().format(sum) + " TZS");

                            //insert resources into current purchase preview table
                            purchaseListTableView.getItems().add(new CreditPurchaseList(
                                    date,
                                    dueDate,
                                    invoice,
                                    product,
                                    category,
                                    unit,
                                    supplier,
                                    quantity1,
                                    purchase,
                                    amount,
                                    "Not Paid"
                            ));

                            //clear the input panel
                            productChooser.getSelectionModel().clearSelection();
                            quantityTextField.clear();
                            wQuantityTextField.clear();
                            purchaseTextField.clear();
                            dateOfExpire.getEditor().clear();
                            categoryTextField.clear();
                            reorderLeverTextField.clear();
                            unitTextField.clear();
                            sellTextField.clear();
                            wSellTextField.clear();
                            profitTextField.clear();
                            timesTextField.clear();
                            shelfTextField.clear();

                            productChooser.requestFocus();

                        } catch (Throwable t) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Unsuccessful\nCheck Your Numeric Values");
                        }
                    }
                }
            }
        });

        return creditPurchaseUI;
    }

    //cash purchase Ui
    GridPane setCashPurchaseUI() {

        Label purchaseTitle = new Label("CASH PURCHASE");
        Label dateOfPurchaseLabel = new Label("Purchase Date");
        Label receiptNumberLabel = new Label("Receipt Number");
        Label productLabel = new Label("Product Name :");
        Label supplierLabel = new Label("Choose Supplier :");
        Label quantityLabel = new Label("Quantity :");
        Label wQuantityLabel = new Label("WholeSale Quantity");
        Label expireLabel = new Label("Expire Date :");
        Label filterPurchaseProduct = new Label("Filter Product :");
        Label totalAmountLabel = new Label("Total Amount :");

        Label unitLabel = new Label("Unit Of Product :");
        Label categoryLabel = new Label("Category Of Product :");
        Label reorderLevelLabel = new Label("Reorder Level :");
        Label purchaseLabel = new Label("Purchase Price(TZS) : ");
        Label sellLabel = new Label("RetailSale Price(TZS) :");
        Label wSellLabel = new Label("WholeSale Price(TZS) :");
        Label profitLabel = new Label("Profit(TSZ) :");
        Label timesLabel = new Label("Times :");
        Label shelfLabel = new Label("Shelf :");

        purchaseTitle.setFont(new Font(16));
        receiptNumberLabel.setFont(new Font(14));
        dateOfPurchaseLabel.setFont(new Font(14));
        totalAmountLabel.setFont(new Font(14));
        wQuantityLabel.setFont(new Font(14));
        wSellLabel.setFont(new Font(14));
        filterPurchaseProduct.setFont(new Font(14));
        productLabel.setFont(new Font(14));
        unitLabel.setFont(new Font(14));
        categoryLabel.setFont(new Font(14));
        quantityLabel.setFont(new Font(14));
        reorderLevelLabel.setFont(new Font(14));
        supplierLabel.setFont(new Font(14));
        purchaseLabel.setFont(new Font(14));
        sellLabel.setFont(new Font(14));
        profitLabel.setFont(new Font(14));
        timesLabel.setFont(new Font(14));
        expireLabel.setFont(new Font(14));
        shelfLabel.setFont(new Font(14));

        //Observable list for combo box
        ComboBox<String> productChooser = new ComboBox<>();
        ComboBox<String> supplierChooser = new ComboBox<>();
        ComboBox<String> receiptChooser = new ComboBox<>(FXCollections.observableArrayList());

        supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
        receiptChooser.setItems(PurchaseCategoryData.getAllCashReceipt());
        productChooser.setItems(StockCategoryData.getProductNames());

        receiptChooser.setEditable(true);
        supplierChooser.setEditable(true);
        productChooser.setEditable(true);
        receiptChooser.setVisibleRowCount(10);
        supplierChooser.setVisibleRowCount(10);

        DatePicker dateOfPurchase = new DatePicker();
        DatePicker dateOfExpire = new DatePicker();

        TextField unitTextField = new TextField();
        TextField categoryTextField = new TextField();
        TextField quantityTextField = new TextField();
        TextField reorderLeverTextField = new TextField();
        TextField supplierTextField = new TextField();
        TextField purchaseTextField = new TextField();
        TextField sellTextField = new TextField();
        TextField profitTextField = new TextField();
        TextField timesTextField = new TextField();
        TextField shelfTextField = new TextField();
        TextField totalAmountTextField = new TextField();
        TextField filterPurchaseTextField = new TextField();
        TextField wQuantityTextField = new TextField();
        TextField wSellTextField = new TextField();

        timesTextField.setEditable(false);
        profitTextField.setEditable(false);
        unitTextField.setEditable(false);
        categoryTextField.setEditable(false);
        reorderLeverTextField.setEditable(false);
        supplierTextField.setEditable(false);
        shelfTextField.setEditable(false);
        totalAmountTextField.setEditable(false);
        quantityTextField.setPromptText("click me...");
        filterPurchaseTextField.setPromptText("Search...");
        unitTextField.setStyle("-fx-background-color: #ea2121");
        categoryTextField.setStyle("-fx-background-color: #ea2121");
        reorderLeverTextField.setStyle("-fx-background-color: #ea2121");
        profitTextField.setStyle("-fx-background-color: #ea2121");
        timesTextField.setStyle("-fx-background-color: #ea2121");
        shelfTextField.setStyle("-fx-background-color: #ea2121");
        totalAmountTextField.setStyle("-fx-background-color: #ea2121");

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        Button refreshButton = new Button("Refresh List");

        submitButton.setDefaultButton(true);
        refreshButton.setDefaultButton(true);

        TableView<CashPurchaseList> purchaseListTableView = new TableView<>();
        ObservableList<CashPurchaseList> purchaseListsData = FXCollections.observableArrayList();
        purchaseListTableView.setItems(purchaseListsData);
        purchaseListTableView.autosize();
        purchaseListTableView.setStyle(
                "-fx-border-color: #ea2121;" +
                        " -fx-border-width:2 ;" +
                        "-fx-border-radius:5 ;" +
                        "-fx-padding:2.5");

        TableColumn<CashPurchaseList, String> purchaseDateColumn = new TableColumn<>("Date");
        TableColumn<CashPurchaseList, String> invoiceNumberColumn = new TableColumn<>("Receipt #");
        TableColumn<CashPurchaseList, String> productNameColumn = new TableColumn<>("Product");
        TableColumn<CashPurchaseList, String> unitColumn = new TableColumn<>("Unit");
        TableColumn<CashPurchaseList, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<CashPurchaseList, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<CashPurchaseList, String> supplierColumn = new TableColumn<>("Supplier");
        TableColumn<CashPurchaseList, Float> purchaseColumn = new TableColumn<>("Purchase(TZS)/Unit");
        TableColumn<CashPurchaseList, Float> totalAmount = new TableColumn<>("Total Amount");

        purchaseListTableView.getColumns().addAll(
                purchaseDateColumn,
                invoiceNumberColumn,
                productNameColumn,
                unitColumn,
                categoryColumn,
                quantityColumn,
                supplierColumn,
                purchaseColumn,
                totalAmount
        );
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        invoiceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("purchase"));
        totalAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));


        //resources input panel
        GridPane dataInputGridPane = new GridPane();
        dataInputGridPane.setVgap(4);
        ColumnConstraints inputCol0 = new ColumnConstraints(150);
        ColumnConstraints inputCol1 = new ColumnConstraints();
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
        RowConstraints rw10 = new RowConstraints(30);
        RowConstraints rw11 = new RowConstraints(30);
        RowConstraints rw12 = new RowConstraints(30);
        RowConstraints rw13 = new RowConstraints(30);
        RowConstraints rw14 = new RowConstraints(30);
        RowConstraints rw15 = new RowConstraints(30);
        dataInputGridPane.getColumnConstraints().addAll(inputCol0, inputCol1);
        dataInputGridPane.getRowConstraints()
                .addAll(rw0, rw1, rw2, rw3, rw4, rw5, rw6, rw7, rw8, rw9, rw10, rw11, rw12, rw13, rw14, rw15);

        //add content to the pane
        dataInputGridPane.add(dateOfPurchaseLabel, 0, 0);
        dataInputGridPane.add(dateOfPurchase, 1, 0);
        dataInputGridPane.add(receiptNumberLabel, 0, 1);
        dataInputGridPane.add(receiptChooser, 1, 1);
        dataInputGridPane.add(supplierLabel, 0, 2);
        dataInputGridPane.add(supplierChooser, 1, 2);
        dataInputGridPane.add(productLabel, 0, 3);
        dataInputGridPane.add(productChooser, 1, 3);
        dataInputGridPane.add(quantityLabel, 0, 4);
        dataInputGridPane.add(quantityTextField, 1, 4);
        dataInputGridPane.add(wQuantityLabel, 0, 5);
        dataInputGridPane.add(wQuantityTextField, 1, 5);
        dataInputGridPane.add(purchaseLabel, 0, 6);
        dataInputGridPane.add(purchaseTextField, 1, 6);
        dataInputGridPane.add(sellLabel, 0, 7);
        dataInputGridPane.add(sellTextField, 1, 7);
        dataInputGridPane.add(wSellLabel, 0, 8);
        dataInputGridPane.add(wSellTextField, 1, 8);
        dataInputGridPane.add(categoryLabel, 0, 9);
        dataInputGridPane.add(categoryTextField, 1, 9);
        dataInputGridPane.add(reorderLevelLabel, 0, 10);
        dataInputGridPane.add(reorderLeverTextField, 1, 10);
        dataInputGridPane.add(unitLabel, 0, 11);
        dataInputGridPane.add(unitTextField, 1, 11);
        dataInputGridPane.add(profitLabel, 0, 12);
        dataInputGridPane.add(profitTextField, 1, 12);
        dataInputGridPane.add(timesLabel, 0, 13);
        dataInputGridPane.add(timesTextField, 1, 13);
        dataInputGridPane.add(shelfLabel, 0, 14);
        dataInputGridPane.add(shelfTextField, 1, 14);
        dataInputGridPane.add(expireLabel, 0, 15);
        dataInputGridPane.add(dateOfExpire, 1, 15);

        //put resources input pane into v box pane
        VBox dataInputPaneVBox = new VBox();
        HBox savePaneHBox = new HBox();
        savePaneHBox.setSpacing(90);
        savePaneHBox.getChildren().addAll(submitButton, cancelButton);
        dataInputPaneVBox.setAlignment(Pos.TOP_CENTER);
        dataInputPaneVBox.getChildren().addAll(purchaseTitle, dataInputGridPane, savePaneHBox);


        //preview table view
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(
                totalAmountLabel,
                totalAmountTextField,
                refreshButton,
                filterPurchaseProduct,
                filterPurchaseTextField);

        GridPane previewTableGridPane = new GridPane();
        previewTableGridPane.setPadding(new Insets(6, 0, 0, 4));
        previewTableGridPane.setVgap(6);
        RowConstraints r = new RowConstraints(25);
        RowConstraints r1 = new RowConstraints();
        ColumnConstraints c = new ColumnConstraints();
        r1.setVgrow(Priority.ALWAYS);
        c.setHgrow(Priority.ALWAYS);
        previewTableGridPane.getColumnConstraints().addAll(c);
        previewTableGridPane.getRowConstraints().addAll(r, r1);

        previewTableGridPane.add(hBox, 0, 0);
        previewTableGridPane.add(purchaseListTableView, 0, 1);


        GridPane cashPurchaseUI = new GridPane();
        cashPurchaseUI.setPadding(new Insets(10, 10, 10, 10));
        cashPurchaseUI.setId("cash_purchase");
        RowConstraints row0 = new RowConstraints();
        ColumnConstraints col0 = new ColumnConstraints(300);
        ColumnConstraints col1 = new ColumnConstraints();
        cashPurchaseUI.getRowConstraints().add(row0);
        cashPurchaseUI.getColumnConstraints().addAll(col0, col1);
        row0.setVgrow(Priority.ALWAYS);
        col1.setHgrow(Priority.ALWAYS);
        cashPurchaseUI.add(dataInputPaneVBox, 0, 0);
        cashPurchaseUI.add(previewTableGridPane, 1, 0);

        purchaseListTableView.setRowFactory((TableView<CashPurchaseList> param) -> {
            TableRow<CashPurchaseList> tableRow = new TableRow<>();
            tableRow.setOnMouseEntered(event -> {
                if (tableRow.isSelected()) {
                    event.consume();
                } else if (tableRow.isEmpty()) {
                    event.consume();
                } else {
                    purchaseListTableView.requestFocus();
                    purchaseListTableView.getFocusModel().focus(tableRow.getIndex());
                    tableRow.setStyle("-fx-background-color: rgba(0,0,255,0.15)");
                }
                tableRow.setOnMouseExited(event1 -> tableRow.setStyle("-fx-base: white"));
            });
            return tableRow;
        });

        refreshButton.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill date of purchase\nReceipt number\nSupplier name\nThen Try to refresh");
            if (dateOfPurchase.getEditor().getText().isEmpty()) {
                dateOfPurchase.requestFocus();
                dateOfPurchase.show();
                alert.show();
            } else if (receiptChooser.getSelectionModel().isEmpty()) {
                receiptChooser.requestFocus();
                receiptChooser.show();
                alert.show();
            } else if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.show();
                alert.show();
            } else {
                LocalDate value = dateOfPurchase.getValue();

                String date;
                if (value.getDayOfMonth() < 0) {
                    int day = value.getDayOfMonth();
                    date = value.getYear() + "-" + value.getMonthValue() + "-0" + day;
                } else {
                    date = value.getYear() + "-" + value.getMonthValue() + "-" + value.getDayOfMonth();
                }

                String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                String receipt = receiptChooser.getSelectionModel().getSelectedItem();

                purchaseListTableView.getItems().clear();
                purchaseListTableView.setItems(PurchaseCategoryData.getCurrentPurchase(date, receipt, supplier));
                float sum = PurchaseCategoryData.getAmountOfCurrentCashPurchase(date, receipt, supplier);
                totalAmountTextField.setText(NumberFormat.getInstance().format(sum) + " TZS");

            }
        });

        filterPurchaseTextField.setOnKeyReleased(event -> {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill\nDate of purchase\nReceipt number\nSupplier name\nThen Try Again");
            if (dateOfPurchase.getEditor().getText().isEmpty()) {
                dateOfPurchase.requestFocus();
                dateOfPurchase.show();
                alert.show();
            } else if (receiptChooser.getSelectionModel().isEmpty()) {
                receiptChooser.requestFocus();
                receiptChooser.show();
                alert.show();
            } else if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.show();
                alert.show();
            } else {
                LocalDate value = dateOfPurchase.getValue();

                String date;
                if (value.getDayOfMonth() < 0) {
                    int day = value.getDayOfMonth();
                    date = value.getYear() + "-" + value.getMonthValue() + "-0" + day;
                } else {
                    date = value.getYear() + "-" + value.getMonthValue() + "-" + value.getDayOfMonth();
                }

                String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                String receipt = receiptChooser.getSelectionModel().getSelectedItem();

                purchaseListTableView.getItems().clear();

                ObservableList<CashPurchaseList> purchases
                        = PurchaseCategoryData.getCurrentPurchase(date, receipt, supplier);

                switch (event.getCode()) {
                    case DOWN:
                        ObservableList<CashPurchaseList> filterDown = FXCollections.observableArrayList();

                        for (CashPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    filterDown.add(purchaseList);
                                }
                            }

                        }

                        //if the filter text field is empty
                        if (!filterDown.isEmpty()) purchaseListTableView.setItems(filterDown);

                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }
                        break;
                    case UP:
                        ObservableList<CashPurchaseList> filterUp = FXCollections.observableArrayList();

                        for (CashPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    filterUp.add(purchaseList);
                                }
                            }

                        }

                        //if the filter text field is empty
                        if (!filterUp.isEmpty()) purchaseListTableView.setItems(filterUp);

                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }
                        break;
                    case LEFT:
                        ObservableList<CashPurchaseList> filterLeft = FXCollections.observableArrayList();

                        for (CashPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    filterLeft.add(purchaseList);
                                }
                            }

                        }

                        //if the filter text field is empty
                        if (!filterLeft.isEmpty()) purchaseListTableView.setItems(filterLeft);

                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }
                        break;
                    case RIGHT:
                        ObservableList<CashPurchaseList> filterRight = FXCollections.observableArrayList();

                        for (CashPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    filterRight.add(purchaseList);
                                }
                            }

                        }

                        //if the filter text field is empty
                        if (!filterRight.isEmpty()) purchaseListTableView.setItems(filterRight);

                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }
                        break;
                    case SHIFT:
                        ObservableList<CashPurchaseList> filterShift = FXCollections.observableArrayList();

                        for (CashPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    filterShift.add(purchaseList);
                                }
                            }

                        }

                        //if the filter text field is empty
                        if (!filterShift.isEmpty()) purchaseListTableView.setItems(filterShift);

                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }
                        break;
                    case CONTROL:
                        ObservableList<CashPurchaseList> filterControl = FXCollections.observableArrayList();

                        for (CashPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    filterControl.add(purchaseList);
                                }
                            }

                        }

                        //if the filter text field is empty
                        if (!filterControl.isEmpty()) purchaseListTableView.setItems(filterControl);

                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }
                        break;
                    case ALT:
                        ObservableList<CashPurchaseList> filterAlt = FXCollections.observableArrayList();

                        for (CashPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    filterAlt.add(purchaseList);
                                }
                            }

                        }

                        //if the filter text field is empty
                        if (!filterAlt.isEmpty()) purchaseListTableView.setItems(filterAlt);

                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }
                        break;
                    case ENTER:
                        ObservableList<CashPurchaseList> filterEnter = FXCollections.observableArrayList();

                        for (CashPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    filterEnter.add(purchaseList);
                                }
                            }

                        }

                        //if the filter text field is empty
                        if (!filterEnter.isEmpty()) purchaseListTableView.setItems(filterEnter);

                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }
                        break;
                    case BACK_SPACE: {

                        ObservableList<CashPurchaseList> filterBack = FXCollections.observableArrayList();

                        for (CashPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    filterBack.add(purchaseList);
                                }
                            }

                        }

                        //if the filter text field is empty
                        if (!filterBack.isEmpty()) purchaseListTableView.setItems(filterBack);

                        if (filterPurchaseTextField.getText().isEmpty()) {
                            purchaseListTableView.setItems(purchases);
                        }
                        break;
                    }

                    default: {

                        ObservableList<CashPurchaseList> filter = FXCollections.observableArrayList();

                        for (CashPurchaseList purchaseList : purchases) {

                            if (!filterPurchaseTextField.getText().isEmpty()) {
                                String product = filterPurchaseTextField.getText().toLowerCase();
                                String purchaseProduct = purchaseList.getProduct().toLowerCase();

                                if (purchaseProduct.contains(product)) {

                                    filter.add(purchaseList);
                                }
                            }

                        }

                        //if the filter text field is empty
                        if (!filter.isEmpty()) purchaseListTableView.setItems(filter);
                    }

                    if (filterPurchaseTextField.getText().isEmpty()) {
                        purchaseListTableView.setItems(purchases);
                    }

                }
            }
        });

        dateOfPurchase.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (dateOfPurchase.getEditor().getText().isEmpty()) {
                        dateOfPurchase.requestFocus();
                        dateOfPurchase.show();
                    } else {
                        receiptChooser.requestFocus();
                        receiptChooser.show();
                    }
                }
            }
        });

        purchaseListTableView.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case SECONDARY: {
                    if (purchaseListTableView.getItems().isEmpty()) {
                        event.consume();
                    } else {
                        double x = event.getSceneX();
                        double y = event.getSceneY();
                        Popup popUp = new Popup();
                        popUp.setX(x);
                        popUp.setY(y);

                        ListView<String> list = new ListView<>();
                        ObservableList<String> data = FXCollections.observableArrayList();
                        data.addAll("Refresh", "Update");
                        list.setPadding(new Insets(6, 6, 6, 6));
                        list.setPrefSize(100, 100);
                        list.setItems(data);
                        list.setStyle("-fx-base: white");

                        popUp.getContent().addAll(list);
                        popUp.setAutoHide(true);
                        popUp.show(MainStage.stageAdmin);

                        list.setOnMouseClicked(event1 -> {
                            if (list.getSelectionModel().getSelectedIndex() == 1) {
                                //popup to set a new supplier
                                Popup popupUpdatePurchase = new Popup();
                                //control fields
                                Label dateLabel = new Label("Date Of Purchase :");
                                Label quantityUpdateLabel = new Label("Quantity :");
                                Label purchaseUpdateLabel = new Label("Purchase (TZS) :");
                                Label sellingUpdateLabel = new Label("Selling (TZS) :");

                                dateLabel.setFont(new Font(14));
                                quantityUpdateLabel.setFont(new Font(14));
                                purchaseUpdateLabel.setFont(new Font(14));
                                sellingUpdateLabel.setFont(new Font(14));

                                TextField dateofUpdatePurchaseTextField = new TextField();
                                TextField quantityPurchasedTextField = new TextField();
                                TextField purchasePriceTextField = new TextField();
                                TextField sellingPriceTextField = new TextField();

                                String dateOld = purchaseListTableView.getSelectionModel().getSelectedItem().getDate();
                                int quant = purchaseListTableView.getSelectionModel().getSelectedItem().getQuantity();
                                float purchase1 = purchaseListTableView.getSelectionModel().getSelectedItem().getPurchase();
                                StockCategoryData
                                        .getSellPrice(purchaseListTableView.getSelectionModel().getSelectedItem().getProduct());
                                float sel = StockCategoryData.sellPrice;

                                dateofUpdatePurchaseTextField.setText(dateOld);
                                quantityPurchasedTextField.setText(String.valueOf(quant));
                                purchasePriceTextField.setText(String.valueOf(purchase1));
                                sellingPriceTextField.setText(String.valueOf(sel));


                                Button updateButton = new Button("Update");
                                Button cancelEditButton = new Button("Cancel");
                                updateButton.setPrefWidth(164);
                                updateButton.setStyle("-fx-base: #ea2121");
                                cancelEditButton.setStyle("-fx-base: #ea2121");

                                //container of pop up
                                GridPane gridPane = new GridPane();
                                gridPane.setPadding(new Insets(16, 16, 16, 16));
                                gridPane.setStyle("-fx-background-color: white");
                                gridPane.setVgap(6);
                                gridPane.setHgap(4);
                                ColumnConstraints column0 = new ColumnConstraints(150);
                                ColumnConstraints column1 = new ColumnConstraints();
                                RowConstraints rw_0 = new RowConstraints(40);
                                RowConstraints rw_1 = new RowConstraints(40);
                                RowConstraints rw_2 = new RowConstraints(40);
                                RowConstraints rw_3 = new RowConstraints(40);
                                RowConstraints rw_4 = new RowConstraints(40);

                                gridPane.getColumnConstraints().addAll(column0, column1);
                                gridPane.getRowConstraints().addAll(rw_0, rw_1, rw_2, rw_3, rw_4);
                                gridPane.add(dateLabel, 0, 0);
                                gridPane.add(dateofUpdatePurchaseTextField, 1, 0);
                                gridPane.add(quantityUpdateLabel, 0, 1);
                                gridPane.add(quantityPurchasedTextField, 1, 1);
                                gridPane.add(purchaseUpdateLabel, 0, 2);
                                gridPane.add(purchasePriceTextField, 1, 2);
                                gridPane.add(sellingUpdateLabel, 0, 3);
                                gridPane.add(sellingPriceTextField, 1, 3);
                                gridPane.add(updateButton, 0, 4);
                                gridPane.add(cancelEditButton, 1, 4);

                                //make a black ribbon around the add supplier popup
                                VBox vBox = new VBox();
                                vBox.setPadding(new Insets(4, 4, 4, 4));
                                vBox.setStyle("-fx-background-color: #000000");
                                vBox.getChildren().add(gridPane);

                                //popup.setAutoHide(true);
                                popupUpdatePurchase.centerOnScreen();
                                popupUpdatePurchase.setAutoHide(true);
                                popupUpdatePurchase.getContent().add(vBox);

                                cancelEditButton.setOnAction(event11 -> {
                                    dateofUpdatePurchaseTextField.clear();
                                    quantityPurchasedTextField.clear();
                                    purchasePriceTextField.clear();
                                    sellingPriceTextField.clear();
                                    popupUpdatePurchase.hide();
                                });

                                updateButton.setOnAction(event2 -> {
                                    int quantity;
                                    float purchase;
                                    float sell;
                                    String productCondiotion = purchaseListTableView
                                            .getSelectionModel()
                                            .getSelectedItem().getProduct();
                                    String dateCondition = purchaseListTableView
                                            .getSelectionModel()
                                            .getSelectedItem().getDate();
                                    String supplierCopndition = purchaseListTableView
                                            .getSelectionModel()
                                            .getSelectedItem().getSupplier();
                                    String receiptCondition = purchaseListTableView
                                            .getSelectionModel()
                                            .getSelectedItem().getInvoice();
                                    String dateNew = dateofUpdatePurchaseTextField.getText().trim();

                                    try {
                                        quantity = Integer.parseInt(quantityPurchasedTextField.getText());
                                        purchase = Float.parseFloat(purchasePriceTextField.getText());
                                        sell = Float.parseFloat(sellingPriceTextField.getText());
                                    } catch (Throwable t) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setContentText("There is an error\n" +
                                                "Make sure quantity, sell and purchase are numerical");
                                        alert.show();
                                        throw new NumberFormatException("In update cash purchase");
                                    }

                                    //update product quantity and sell in stock
                                    int stockQuantity = StockCategoryData.getProductQuantity(productCondiotion);
                                    int minusQuantity = Integer.parseInt(quantityPurchasedTextField.getText())
                                            - purchaseListTableView
                                            .getSelectionModel()
                                            .getSelectedItem().getQuantity();
                                    stockQuantity = stockQuantity + minusQuantity;
                                    StockCategoryData.updateProductQuantity(productCondiotion, stockQuantity);


                                    //update product in cash purchase table

                                    PurchaseCategoryData.updateCashPurchaseProductParameter(
                                            dateCondition,
                                            receiptCondition,
                                            supplierCopndition,
                                            productCondiotion,
                                            dateNew,
                                            quantity,
                                            purchase

                                    );

                                    //update sell price in stock
                                    StockCategoryData.updateSellPrice(productCondiotion, sell);

                                    //refresh the table
                                    LocalDate value = dateOfPurchase.getValue();

                                    String date;
                                    if (value.getDayOfMonth() < 0) {
                                        int day = value.getDayOfMonth();
                                        date = value.getYear() + "-" + value.getMonthValue() + "-0" + day;
                                    } else {
                                        date = value.getYear() + "-" + value.getMonthValue() + "-" + value.getDayOfMonth();
                                    }

                                    String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                                    String receipt = receiptChooser.getSelectionModel().getSelectedItem();

                                    purchaseListTableView.getItems().clear();
                                    purchaseListTableView
                                            .setItems(PurchaseCategoryData.getCurrentPurchase(date, receipt, supplier));
                                    float sum = PurchaseCategoryData
                                            .getAmountOfCurrentCashPurchase(date, receipt, supplier);
                                    totalAmountTextField.setText(NumberFormat.getInstance().format(sum) + " TZS");

                                    //hide the popup
                                    popupUpdatePurchase.hide();


                                });

                                popUp.hide();
                                popupUpdatePurchase.show(MainStage.stageAdmin);
                            } else if (list.getSelectionModel().getSelectedIndex() == 0) {
                                //refresh the table
                                LocalDate value = dateOfPurchase.getValue();

                                String date;
                                if (value.getDayOfMonth() < 0) {
                                    int day = value.getDayOfMonth();
                                    date = value.getYear() + "-" + value.getMonthValue() + "-0" + day;
                                } else {
                                    date = value.getYear() + "-" + value.getMonthValue() + "-" + value.getDayOfMonth();
                                }

                                String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                                String receipt = receiptChooser.getSelectionModel().getSelectedItem();

                                purchaseListTableView.setItems(PurchaseCategoryData
                                        .getCurrentPurchase(date, receipt, supplier));
                                float sum = PurchaseCategoryData
                                        .getAmountOfCurrentCashPurchase(date, receipt, supplier);
                                totalAmountTextField.setText(NumberFormat.getInstance().format(sum) + " TZS");
                                popUp.hide();
                            }

                        });
                    }
                }
            }
        });

        receiptChooser.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    try {

                        if (receiptChooser.getSelectionModel().getSelectedItem().isEmpty()) {
                            receiptChooser.requestFocus();
                            receiptChooser.show();
                        } else {
                            supplierChooser.requestFocus();
                            supplierChooser.show();
                        }
                    } catch (NullPointerException ignore) {
                    }
                }
            }
        });
        receiptChooser.getEditor().setOnMouseClicked(event -> receiptChooser.show());
        receiptChooser.getEditor().setOnKeyReleased(event -> {
            //update list of combo box
            ObservableList<String> products = PurchaseCategoryData.getAllCashReceipt();

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
                case BACK_SPACE:
                    //show the updated list
                    if (!receiptChooser.getSelectionModel().isEmpty()) {
                        receiptChooser.getSelectionModel().clearSelection();
                    }
                    receiptChooser.show();
                    ObservableList<String> filter = FXCollections.observableArrayList();
                    //filter the products with the word typed
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = receiptChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            filter.add(product);
                        }

                    }
                    if (receiptChooser.getEditor().getText().isEmpty()) {
                        receiptChooser.setItems(products);
                    }
                    if (filter.isEmpty()) filter.add("No such supplier");
                    //update the list to match typed word
                    receiptChooser.setItems(filter);
                    break;
                default:
                    receiptChooser.show();
                    ObservableList<String> filter1 = FXCollections.observableArrayList();
                    //filter the products with the word typed
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = receiptChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            filter1.add(product);
                        }

                    }
                    if (receiptChooser.getEditor().getText().isEmpty()) {
                        receiptChooser.setItems(products);
                    }
                    if (filter1.isEmpty()) filter1.add("No such supplier");
                    //update the list to match typed word
                    receiptChooser.setItems(filter1);
                    break;
            }

            receiptChooser.show();
        });

        supplierChooser.getEditor().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (supplierChooser.getSelectionModel().isEmpty()) {
                    supplierChooser.requestFocus();
                    supplierChooser.show();
                } else {
                    productChooser.requestFocus();
                    productChooser.show();
                }
            }
        });
        supplierChooser.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (supplierChooser.getSelectionModel().isEmpty()) {
                    supplierChooser.requestFocus();
                    supplierChooser.show();
                } else {
                    productChooser.requestFocus();
                    productChooser.show();
                }
            }
        });

        productChooser.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (productChooser.getSelectionModel().isEmpty()) {
                    productChooser.getItems().clear();
                    productChooser.setItems(StockCategoryData.getProductNames());
                    productChooser.requestFocus();
                    productChooser.show();
                } else {
                    if (dateOfPurchase.getEditor().getText().isEmpty()) {
                        dateOfPurchase.requestFocus();
                        dateOfPurchase.show();
                    } else if (receiptChooser.getSelectionModel().isEmpty()) {
                        receiptChooser.requestFocus();
                        receiptChooser.show();
                    } else if (supplierChooser.getSelectionModel().isEmpty()) {
                        supplierChooser.requestFocus();
                        supplierChooser.show();
                    } else if (productChooser.getSelectionModel().isEmpty()) {
                        productChooser.requestFocus();
                        productChooser.getItems().clear();
                        productChooser.setItems(StockCategoryData.getProductNames());
                        productChooser.show();
                    } else {
                        String product = productChooser.getSelectionModel().getSelectedItem();

                        purchaseTextField.setText(String.valueOf(StockCategoryData.getPurchase(product)));
                        categoryTextField.setText(StockCategoryData.getCategory(product));
                        unitTextField.setText(StockCategoryData.getUnit(product));
                        reorderLeverTextField.setText(String.valueOf(StockCategoryData.getReorderLevel(product)));
                        StockCategoryData.getSellPrice(product);
                        sellTextField.setText(String.valueOf(StockCategoryData.sellPrice));
                        profitTextField.setText(String.valueOf(StockCategoryData.getProfit(product)));
                        StockCategoryData.getSellPrice(product);
                        timesTextField.setText(String.valueOf(StockCategoryData.sellPrice
                                / StockCategoryData.getPurchase(product)));
                        shelfTextField.setText(StockCategoryData.getShelf(product));
                        wQuantityTextField.setText(String.valueOf(StockCategoryData
                                .getWholeProductQuantity(product)));
                        StockCategoryData.getWholeSellPrice(product);
                        wSellTextField.setText(String.valueOf(StockCategoryData.wSellPrice));

                    }
                    quantityTextField.requestFocus();
                }
            }
        });
        productChooser.getEditor().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (productChooser.getSelectionModel().isEmpty()) {
                    productChooser.getItems().clear();
                    productChooser.setItems(StockCategoryData.getProductNames());
                    productChooser.requestFocus();
                    productChooser.show();
                } else {
                    quantityTextField.requestFocus();
                }
            }
        });


        supplierChooser.setOnMouseClicked(event -> supplierChooser.show());
        supplierChooser.getEditor().setOnMouseClicked(event -> supplierChooser.show());
        supplierChooser.getEditor().setOnKeyReleased(event -> {
            //update list of combo box
            ObservableList<String> products = PurchaseCategoryData.getSuppliers();

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
                case BACK_SPACE:
                    //show the updated list
                    if (!supplierChooser.getSelectionModel().isEmpty()) {
                        supplierChooser.getSelectionModel().clearSelection();
                    }
                    supplierChooser.show();
                    ObservableList<String> filter = FXCollections.observableArrayList();
                    //filter the products with the word typed
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = supplierChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            filter.add(product);
                        }

                    }
                    if (supplierChooser.getEditor().getText().isEmpty()) {
                        supplierChooser.setItems(products);
                    }
                    if (filter.isEmpty()) filter.add("No such supplier");
                    //update the list to match typed word
                    supplierChooser.setItems(filter);
                    break;
                default:
                    supplierChooser.show();
                    ObservableList<String> filter1 = FXCollections.observableArrayList();
                    //filter the products with the word typed
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = supplierChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            filter1.add(product);
                        }

                    }
                    if (supplierChooser.getEditor().getText().isEmpty()) {
                        supplierChooser.setItems(products);
                    }
                    if (filter1.isEmpty()) filter1.add("No such supplier");
                    //update the list to match typed word
                    supplierChooser.setItems(filter1);
                    break;
            }

            supplierChooser.show();
        });

        //oen date of on click
        dateOfPurchase.getEditor().setOnMouseClicked(event -> dateOfPurchase.show());
        dateOfExpire.getEditor().setOnMouseClicked(event -> dateOfExpire.show());

        //implement product chooser combo box
        productChooser.getEditor().setOnMouseClicked(event -> {
            productChooser.setItems(StockCategoryData.getProductNames());
            unitTextField.clear();
            sellTextField.clear();
            categoryTextField.clear();
            shelfTextField.clear();
            profitTextField.clear();
            reorderLeverTextField.clear();
            purchaseTextField.clear();
            timesTextField.clear();
            productChooser.show();
        });

        productChooser.getEditor().setOnKeyReleased(event -> {
            //update list of combo box
            ObservableList<String> products = StockCategoryData.getProductNames();
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
                case BACK_SPACE:
                    //clear selection and update variable depend on selection
                    if (productChooser.getEditor().getText().isEmpty()) {
                        unitTextField.clear();
                        sellTextField.clear();
                        categoryTextField.clear();
                        shelfTextField.clear();
                        profitTextField.clear();
                        reorderLeverTextField.clear();
                        purchaseTextField.clear();
                        timesTextField.clear();
                    }
                    productChooser.getItems().clear();
                    //filter the products with the word typed
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = productChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            productChooser.getItems().add(product);
                        }
                    }
                    if (productChooser.getItems().isEmpty()) productChooser.getItems().add("No such item in stock");

                    break;
                default: {
                    productChooser.getItems().clear();
                    for (String product : products) {

                        //implement not case sensitive
                        String inputString = productChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {
                            productChooser.getItems().add(product);
                        }
                    }
                    if (productChooser.getItems().isEmpty()) productChooser.getItems().add("No such item in stock");

                    break;
                }
            }

            productChooser.show();
        });

        //implement quantity text field
        quantityTextField.setOnMouseClicked(event -> {
            if (dateOfPurchase.getEditor().getText().isEmpty()) {
                dateOfPurchase.requestFocus();
                dateOfPurchase.show();
            } else if (receiptChooser.getSelectionModel().isEmpty()) {
                receiptChooser.requestFocus();
                receiptChooser.show();
            } else if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.show();
            } else if (productChooser.getSelectionModel().isEmpty()) {
                productChooser.requestFocus();
                productChooser.getItems().clear();
                productChooser.setItems(StockCategoryData.getProductNames());
                productChooser.show();
            } else {
                String product = productChooser.getSelectionModel().getSelectedItem();

                purchaseTextField.setText(String.valueOf(StockCategoryData.getPurchase(product)));
                categoryTextField.setText(StockCategoryData.getCategory(product));
                unitTextField.setText(StockCategoryData.getUnit(product));
                reorderLeverTextField.setText(String.valueOf(StockCategoryData.getReorderLevel(product)));
                sellTextField.setText(StockCategoryData.getSellPrice(product));
                profitTextField.setText(String.valueOf(StockCategoryData.getProfit(product)));
                StockCategoryData.getSellPrice(product);
                timesTextField.setText(String.valueOf(StockCategoryData.sellPrice
                        / StockCategoryData.getPurchase(product)));
                StockCategoryData.getShelf(product);
                shelfTextField.setText(String.valueOf(StockCategoryData.sellPrice));
                wQuantityTextField.setText(String.valueOf(StockCategoryData
                        .getWholeProductQuantity(product)));
                StockCategoryData.getWholeSellPrice(product);
                wSellTextField.setText(String.valueOf(StockCategoryData.wSellPrice));

            }
        });

        quantityTextField.setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case ENTER: {
                    if (quantityTextField.getText().isEmpty()) {
                        quantityTextField.requestFocus();
                    } else if (dateOfPurchase.getEditor().getText().isEmpty()) {
                        dateOfPurchase.requestFocus();
                        dateOfPurchase.show();
                    } else if (receiptChooser.getSelectionModel().isEmpty()) {
                        receiptChooser.requestFocus();
                        receiptChooser.show();
                    } else if (supplierChooser.getSelectionModel().isEmpty()) {
                        supplierChooser.requestFocus();
                        supplierChooser.show();
                    } else if (productChooser.getSelectionModel().isEmpty()) {
                        productChooser.requestFocus();
                        productChooser.getItems().clear();
                        productChooser.setItems(StockCategoryData.getProductNames());
                        productChooser.show();
                    } else {
                        String product = productChooser.getSelectionModel().getSelectedItem();
                        StockCategoryData.getSellPrice(product);

                        purchaseTextField.setText(String.valueOf(StockCategoryData.getPurchase(product)));
                        categoryTextField.setText(StockCategoryData.getCategory(product));
                        unitTextField.setText(StockCategoryData.getUnit(product));
                        reorderLeverTextField.setText(String.valueOf(StockCategoryData.getReorderLevel(product)));
                        sellTextField.setText(String.valueOf(StockCategoryData.sellPrice));
                        profitTextField.setText(String.valueOf(StockCategoryData.getProfit(product)));
                        StockCategoryData.getSellPrice(product);
                        timesTextField.setText(String.valueOf(StockCategoryData.sellPrice
                                / StockCategoryData.getPurchase(product)));
                        shelfTextField.setText(StockCategoryData.getShelf(product));

                        wQuantityTextField.requestFocus();
                    }
                }
                break;
            }
        });

        wQuantityTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (quantityTextField.getText().isEmpty()) {
                        quantityTextField.requestFocus();
                    } else purchaseTextField.requestFocus();
                }
            }
        });
        purchaseTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (purchaseTextField.getText().isEmpty()) {
                        purchaseTextField.requestFocus();
                    } else sellTextField.requestFocus();
                }
            }
        });
        sellTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (sellTextField.getText().isEmpty()) {
                        sellTextField.requestFocus();
                    } else wSellTextField.requestFocus();
                }
            }
        });
        wSellTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (wSellTextField.getText().isEmpty()) {
                        wSellTextField.requestFocus();
                    } else {
                        dateOfExpire.requestFocus();
                        dateOfExpire.show();
                    }
                }
            }
        });
        dateOfExpire.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (dateOfExpire.getEditor().getText().isEmpty()) {
                        dateOfExpire.requestFocus();
                        dateOfExpire.show();
                    } else submitButton.requestFocus();
                }
            }
        });

        //implement purchase text field on changes
        purchaseTextField.setOnKeyReleased(event -> {
            if (!sellTextField.getText().isEmpty()) {

                if (event.getCode().isDigitKey()) {
                    if (quantityTextField.getText().isEmpty()) {
                        quantityTextField.requestFocus();
                    } else {
                        try {
                            //for times text field
                            float times = Float.parseFloat(sellTextField.getText())
                                    / Float.parseFloat(purchaseTextField.getText());

                            //for profit text field
                            float profits = Float.parseFloat(sellTextField.getText()) -
                                    Float.parseFloat(purchaseTextField.getText());

                            profitTextField.setText(String.valueOf(profits));
                            timesTextField.setText(String.valueOf(times));

                        } catch (NumberFormatException ignore) {
                            profitTextField.setText("-" + purchaseTextField.getText());
                            timesTextField.clear();
                        }
                    }

                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    try {
                        //for times text field
                        float times = Float.parseFloat(sellTextField.getText())
                                / Float.parseFloat(purchaseTextField.getText());

                        //for profit text field
                        float profits = Float.parseFloat(sellTextField.getText()) -
                                Float.parseFloat(purchaseTextField.getText());

                        profitTextField.setText(String.valueOf(profits));
                        timesTextField.setText(String.valueOf(times));

                    } catch (NumberFormatException ignore) {
                        profitTextField.setText("-" + purchaseTextField.getText());
                        timesTextField.clear();
                    }
                }
            }
        });

        sellTextField.setOnKeyReleased(event -> {
            if (!purchaseTextField.getText().isEmpty()) {

                if (event.getCode().isDigitKey()) {
                    if (quantityTextField.getText().isEmpty()) {
                        quantityTextField.requestFocus();
                    } else {
                        try {
                            //for times text field
                            float times = Float.parseFloat(sellTextField.getText())
                                    / Float.parseFloat(purchaseTextField.getText());

                            //for profit text field
                            float profits = Float.parseFloat(sellTextField.getText()) -
                                    Float.parseFloat(purchaseTextField.getText());

                            profitTextField.setText(String.valueOf(profits));
                            timesTextField.setText(String.valueOf(times));

                        } catch (NumberFormatException ignore) {
                            profitTextField.setText("-" + purchaseTextField.getText());
                            timesTextField.clear();
                        }
                    }

                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    try {
                        //for times text field
                        float times = Float.parseFloat(sellTextField.getText())
                                / Float.parseFloat(purchaseTextField.getText());

                        //for profit text field
                        float profits = Float.parseFloat(sellTextField.getText()) -
                                Float.parseFloat(purchaseTextField.getText());

                        profitTextField.setText(String.valueOf(profits));
                        timesTextField.setText(String.valueOf(times));

                    } catch (NumberFormatException ignore) {
                        profitTextField.setText("-" + purchaseTextField.getText());
                        timesTextField.clear();
                    }
                }
            }
        });

        //implements buttons
        cancelButton.setOnAction(event -> {
            productChooser.getSelectionModel().clearSelection();
            quantityTextField.clear();
            purchaseTextField.clear();
            dateOfExpire.getEditor().clear();
            categoryTextField.clear();
            reorderLeverTextField.clear();
            unitTextField.clear();
            sellTextField.clear();
            profitTextField.clear();
            timesTextField.clear();
            shelfTextField.clear();
            wQuantityTextField.clear();
            wSellTextField.clear();

        });
        submitButton.setOnMouseClicked(event -> {
            if (dateOfPurchase.getEditor().getText().isEmpty()) {
                dateOfPurchase.requestFocus();
                dateOfPurchase.show();
            } else if (receiptChooser.getSelectionModel().isEmpty()) receiptChooser.requestFocus();
            else if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.getItems().clear();
                supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
                supplierChooser.show();
            } else if (productChooser.getSelectionModel().isEmpty()) {
                productChooser.requestFocus();
                productChooser.getItems().clear();
                productChooser.setItems(StockCategoryData.getProductNames());
                productChooser.show();
            } else if (quantityTextField.getText().isEmpty()) quantityTextField.requestFocus();
            else if (purchaseTextField.getText().isEmpty()) purchaseTextField.requestFocus();
            else if (dateOfExpire.getEditor().getText().isEmpty()) {
                dateOfExpire.requestFocus();
                dateOfExpire.show();
            }
            //if everything is good add to the purchase list and update the stock quantity
            else {
                String product = productChooser.getSelectionModel().getSelectedItem();
                int quantity;
                int wQuantity;
                float purchase;
                float sell;
                float wSell;
                try {
                    quantity = Integer.parseInt(quantityTextField.getText());
                    wQuantity = Integer.parseInt(wQuantityTextField.getText());
                    wSell = Float.parseFloat(wSellTextField.getText());
                    purchase = Float.parseFloat(purchaseTextField.getText());
                    sell = Float.parseFloat(sellTextField.getText());

                    //update information of a stock
                    int oldStockQuantity=StockCategoryData.getProductQuantity(product);
                    quantity = oldStockQuantity + quantity;
                    StockCategoryData.updateProductQuantity(product, quantity);
                    StockCategoryData.updatePurchasePrice(product, purchase);
                    StockCategoryData.updateCurrentSupplier(product, supplierChooser.getSelectionModel().getSelectedItem());
                    StockCategoryData.updateSellPrice(product, sell);
                    StockCategoryData.updateWholeQuantityOfProduct(product, wQuantity);
                    StockCategoryData.updateWholeSalePrice(product, wSell);


                    //insert resources into cash purchase
                    String date;
                    if (dateOfPurchase.getValue().getDayOfMonth() < 0) {
                        int day = dateOfPurchase.getValue().getDayOfMonth();
                        date = dateOfPurchase.getValue().getYear() + "-"
                                + dateOfPurchase.getValue().getMonthValue() + "-0" + day;
                    } else {
                        date = dateOfPurchase.getValue().getYear() + "-"
                                + dateOfPurchase.getValue().getMonthValue() + "-"
                                + dateOfPurchase.getValue().getDayOfMonth();
                    }


                    String receipt = receiptChooser.getSelectionModel().getSelectedItem();
                    String category = categoryTextField.getText();
                    String unit = unitTextField.getText();
                    String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                    int quantity1 = Integer.parseInt(quantityTextField.getText());
                    float purchase1 = Float.parseFloat(purchaseTextField.getText());
                    float amount = quantity1 * purchase1;

                    //put records to the database
                    PurchaseCategoryData.insertDataIntoCashPurchase(
                            date,
                            receipt,
                            product,
                            category,
                            unit,
                            supplier,
                            oldStockQuantity,
                            quantity1,
                            purchase1,
                            amount
                    );

                    //update total amount of the current purchase
                    float sum = PurchaseCategoryData.getAmountOfCurrentCashPurchase(date, receipt, supplier);
                    totalAmountTextField.setText(NumberFormat.getInstance().format(sum) + " TZS");

                    //insert resources into current purchase preview table
                    purchaseListsData.add(new CashPurchaseList(
                            date,
                            receipt,
                            product,
                            category,
                            unit,
                            supplier,
                            quantity1,
                            purchase,
                            amount
                    ));

                    //clear the input panel
                    productChooser.getSelectionModel().clearSelection();
                    quantityTextField.clear();
                    purchaseTextField.clear();
                    dateOfExpire.getEditor().clear();
                    categoryTextField.clear();
                    reorderLeverTextField.clear();
                    unitTextField.clear();
                    sellTextField.clear();
                    profitTextField.clear();
                    timesTextField.clear();
                    shelfTextField.clear();
                    wQuantityTextField.clear();
                    wSellTextField.clear();

                    productChooser.setItems(StockCategoryData.getProductNames());
                    productChooser.requestFocus();
                    productChooser.show();

                } catch (Throwable t) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Unsuccessful\nCheck Your Numeric Values");
                    alert.show();
                }

            }
        });

        submitButton.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (dateOfPurchase.getEditor().getText().isEmpty()) {
                        dateOfPurchase.requestFocus();
                        dateOfPurchase.show();
                    } else if (receiptChooser.getSelectionModel().isEmpty()) receiptChooser.requestFocus();
                    else if (supplierChooser.getSelectionModel().isEmpty()) {
                        supplierChooser.requestFocus();
                        supplierChooser.getItems().clear();
                        supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
                        supplierChooser.show();
                    } else if (productChooser.getSelectionModel().isEmpty()) {
                        productChooser.requestFocus();
                        productChooser.getItems().clear();
                        productChooser.setItems(StockCategoryData.getProductNames());
                        productChooser.show();
                    } else if (quantityTextField.getText().isEmpty()) quantityTextField.requestFocus();
                    else if (purchaseTextField.getText().isEmpty()) purchaseTextField.requestFocus();
                    else if (dateOfExpire.getEditor().getText().isEmpty()) {
                        dateOfExpire.requestFocus();
                        dateOfExpire.show();
                    }
                    //if everything is good add to the purchase list and update the stock quantity
                    else {
                        String product = productChooser.getSelectionModel().getSelectedItem();
                        int quantity;
                        int wQuantity;
                        float purchase;
                        float sell;
                        float wSell;
                        try {
                            quantity = Integer.parseInt(quantityTextField.getText());
                            wQuantity = Integer.parseInt(wQuantityTextField.getText());
                            wSell = Float.parseFloat(wSellTextField.getText());
                            purchase = Float.parseFloat(purchaseTextField.getText());
                            sell = Float.parseFloat(sellTextField.getText());

                            //update information of a stock
                            int oldStockQuantity=StockCategoryData.getProductQuantity(product);
                            quantity = oldStockQuantity + quantity;
                            StockCategoryData.updateProductQuantity(product, quantity);
                            StockCategoryData.updatePurchasePrice(product, purchase);
                            StockCategoryData.updateCurrentSupplier(product, supplierChooser.getSelectionModel().getSelectedItem());
                            StockCategoryData.updateSellPrice(product, sell);
                            StockCategoryData.updateWholeQuantityOfProduct(product, wQuantity);
                            StockCategoryData.updateWholeSalePrice(product, wSell);


                            //insert resources into cash purchase
                            String date;
                            if (dateOfPurchase.getValue().getDayOfMonth() < 0) {
                                int day = dateOfPurchase.getValue().getDayOfMonth();
                                date = dateOfPurchase.getValue().getYear() + "-"
                                        + dateOfPurchase.getValue().getMonthValue() + "-0" + day;
                            } else {
                                date = dateOfPurchase.getValue().getYear() + "-"
                                        + dateOfPurchase.getValue().getMonthValue() + "-"
                                        + dateOfPurchase.getValue().getDayOfMonth();
                            }


                            String receipt = receiptChooser.getSelectionModel().getSelectedItem();
                            String category = categoryTextField.getText();
                            String unit = unitTextField.getText();
                            String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                            int quantity1 = Integer.parseInt(quantityTextField.getText());
                            float purchase1 = Float.parseFloat(purchaseTextField.getText());
                            float amount = quantity1 * purchase1;

                            //put records to the database
                            PurchaseCategoryData.insertDataIntoCashPurchase(
                                    date,
                                    receipt,
                                    product,
                                    category,
                                    unit,
                                    supplier,
                                    oldStockQuantity,
                                    quantity1,
                                    purchase1,
                                    amount
                            );

                            //update total amount of the current purchase
                            float sum = PurchaseCategoryData.getAmountOfCurrentCashPurchase(date, receipt, supplier);
                            totalAmountTextField.setText(NumberFormat.getInstance().format(sum) + " TZS");

                            //insert resources into current purchase preview table
                            purchaseListsData.add(new CashPurchaseList(
                                    date,
                                    receipt,
                                    product,
                                    category,
                                    unit,
                                    supplier,
                                    quantity1,
                                    purchase,
                                    amount
                            ));

                            //clear the input panel
                            productChooser.getSelectionModel().clearSelection();
                            quantityTextField.clear();
                            purchaseTextField.clear();
                            dateOfExpire.getEditor().clear();
                            categoryTextField.clear();
                            reorderLeverTextField.clear();
                            unitTextField.clear();
                            sellTextField.clear();
                            profitTextField.clear();
                            timesTextField.clear();
                            shelfTextField.clear();
                            wQuantityTextField.clear();
                            wSellTextField.clear();

                            productChooser.setItems(StockCategoryData.getProductNames());
                            productChooser.requestFocus();
                            productChooser.show();

                        } catch (Throwable t) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Unsuccessful\nCheck Your Numeric Values");
                            alert.show();
                        }

                    }
                }
            }
        });

        return cashPurchaseUI;
    }

    //add supplier popup
    private void addSupplier() {
        //popup to set a new supplier
        popup = new Popup();
        //control fields
        Label supplierNameLabel = new Label("Supplier Name");
        Label supplierAddressLabel = new Label("Post Address");
        Label shopLocationLabel = new Label("Shop Location");
        Label contactsLabel = new Label("Supplier Contacts");

        supplierNameLabel.setFont(new Font(14));
        supplierAddressLabel.setFont(new Font(14));
        shopLocationLabel.setFont(new Font(14));
        contactsLabel.setFont(new Font(14));

        TextField supplierNametextField = new TextField();
        TextField addressTextField = new TextField();
        TextArea shopLocationTextArea = new TextArea();
        TextArea contactsTextArea = new TextArea();

        shopLocationTextArea.setWrapText(true);
        shopLocationTextArea.setMaxHeight(80);
        contactsTextArea.setWrapText(true);
        contactsTextArea.setMaxHeight(80);

        Button saveSupplierButton = new Button("Add Supplier");
        Button cancelButton = new Button("Cancel");
        saveSupplierButton.setPrefWidth(164);
        cancelButton.setStyle("-fx-base: #ea2121");
        saveSupplierButton.setStyle("-fx-base: #ea2121");

        //container of pop up
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(16, 16, 16, 16));
        gridPane.setPrefSize(300, 330);
        gridPane.setStyle("-fx-background-color: white");
        gridPane.setVgap(1);
        ColumnConstraints column0 = new ColumnConstraints(120);
        ColumnConstraints column1 = new ColumnConstraints();
        RowConstraints rw0 = new RowConstraints(30);
        RowConstraints rw1 = new RowConstraints(60);
        RowConstraints rw2 = new RowConstraints(100);
        RowConstraints rw3 = new RowConstraints(100);
        RowConstraints rw4 = new RowConstraints();

        gridPane.getColumnConstraints().addAll(column0, column1);
        gridPane.getRowConstraints().addAll(rw0, rw1, rw2, rw3, rw4);
        gridPane.add(supplierNameLabel, 0, 0);
        gridPane.add(supplierNametextField, 1, 0);
        gridPane.add(supplierAddressLabel, 0, 1);
        gridPane.add(addressTextField, 1, 1);
        gridPane.add(shopLocationLabel, 0, 2);
        gridPane.add(shopLocationTextArea, 1, 2);
        gridPane.add(contactsLabel, 0, 3);
        gridPane.add(contactsTextArea, 1, 3);
        gridPane.add(saveSupplierButton, 1, 4);
        gridPane.add(cancelButton, 0, 4);
        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: #000000");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popup.centerOnScreen();
        popup.setAutoHide(true);
        popup.setAutoFix(true);
        popup.getContent().add(vBox);

        cancelButton.setOnAction(event -> popup.hide());
        saveSupplierButton.setOnAction(event -> {
            String supplierName = supplierNametextField.getText().trim();
            String supplierAddress = addressTextField.getText();
            String shopLocation = shopLocationTextArea.getText();
            String contacts = contactsTextArea.getText();

            //hide the popup
            popup.hide();

            //insert resources into table and create a new supplier table
            PurchaseCategoryData.addSupplierInfo(supplierName, supplierAddress, shopLocation, contacts);

        });
    }

    private void removeSupplier() {
        //popup to set a new supplier
        popupRemoveSupplier = new Popup();
        //control fields
        Label productNameLabel = new Label("All Supplier");
        productNameLabel.setFont(new Font(14));

        ListView<String> supplierList = new ListView<>(PurchaseCategoryData.getSuppliers());

        Button removeProductButton = new Button("Remove");
        Button cancelButton = new Button("Cancel");

        removeProductButton.setStyle("-fx-base: #ea2121");
        cancelButton.setStyle("-fx-base: #ea2121");

        HBox hBox = new HBox();
        hBox.setSpacing(112);
        hBox.getChildren().addAll(removeProductButton, cancelButton);


        //container of pop up
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(8, 8, 8, 8));
        //gridPane.setPrefSize(300, 330);
        gridPane.setStyle("-fx-background-color: white");
        gridPane.setVgap(4);
        ColumnConstraints column0 = new ColumnConstraints(250);
        RowConstraints rw0 = new RowConstraints(30);
        RowConstraints rw1 = new RowConstraints();
        rw1.setVgrow(Priority.ALWAYS);
        RowConstraints rw2 = new RowConstraints(30);

        gridPane.getColumnConstraints().addAll(column0);
        gridPane.getRowConstraints().addAll(rw0, rw1, rw2);
        gridPane.add(productNameLabel, 0, 0);
        gridPane.add(supplierList, 0, 1);
        gridPane.add(hBox, 0, 2);

        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: black");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popupRemoveSupplier.centerOnScreen();
        popupRemoveSupplier.setAutoHide(true);
        popupRemoveSupplier.setAutoFix(true);
        popupRemoveSupplier.getContent().add(vBox);

        cancelButton.setOnAction(event -> popupRemoveSupplier.hide());

        removeProductButton.setOnAction(event1 -> {
            if (supplierList.getSelectionModel().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Select first to remove");
                alert.showAndWait();

            } else {
                String supplier = supplierList.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Confirm Delete Of :" + supplier);
                alert.showAndWait().filter(buttonType -> buttonType == ButtonType.OK).ifPresent(buttonType -> {
                    //delete supplier
                    PurchaseCategoryData.removeSupplier(supplier);
                    //refresh supplier list
                    supplierList.setItems(PurchaseCategoryData.getSuppliers());
                    //hide the popup
                    popupRemoveSupplier.hide();
                });

            }
        });
    }

    private void addReceipt() {
        //popup to set a new supplier
        popupAddReceipt = new Popup();
        //control fields
        Label receiptLabel = new Label("NEW RECEIPT");
        receiptLabel.setFont(new Font(14));

        TextField receipTextField = new TextField();


        Button addProductButton = new Button("Add Receipt");
        Button cancelButton = new Button("Cancel");

        addProductButton.setStyle("-fx-base: #ea2121");
        cancelButton.setStyle("-fx-base: #ea2121");

        HBox hBox = new HBox();
        hBox.setSpacing(80);
        hBox.getChildren().addAll(addProductButton, cancelButton);


        //container of pop up
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(8, 8, 8, 8));
        //gridPane.setPrefSize(300, 330);
        gridPane.setStyle("-fx-background-color: white");
        gridPane.setVgap(4);
        ColumnConstraints column0 = new ColumnConstraints(250);
        RowConstraints rw0 = new RowConstraints(30);
        RowConstraints rw1 = new RowConstraints(30);
        RowConstraints rw2 = new RowConstraints(30);

        gridPane.getColumnConstraints().addAll(column0);
        gridPane.getRowConstraints().addAll(rw0, rw1, rw2);
        gridPane.add(receiptLabel, 0, 0);
        gridPane.add(receipTextField, 0, 1);
        gridPane.add(hBox, 0, 2);

        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: #000000");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popupAddReceipt.centerOnScreen();
        popupAddReceipt.setAutoHide(true);
        popupAddReceipt.setAutoFix(true);
        popupAddReceipt.getContent().add(vBox);

        cancelButton.setOnAction(event -> {
            popupAddReceipt.hide();
            receipTextField.clear();
        });
        addProductButton.setOnAction(event -> {
            if (receipTextField.getText().isEmpty()) receipTextField.requestFocus();
            else {
                String receipt = receipTextField.getText().trim();
                //hide the popup
                popupAddReceipt.hide();
                receipTextField.clear();
                //insert resources into table and create a new supplier table
                PurchaseCategoryData.insertReceipt(receipt);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successful");
                alert.setTitle("Receipt Added");
                alert.show();
            }
        });


    }

    private void removeReceipt() {
        //popup to set a new supplier
        popupRemoveReceipt = new Popup();
        //control fields
        Label productNameLabel = new Label("All Receipt");
        productNameLabel.setFont(new Font(14));

        ListView<String> supplierList = new ListView<>(PurchaseCategoryData.getAllCashReceipt());

        Button removeProductButton = new Button("Remove");
        Button cancelButton = new Button("Cancel");

        removeProductButton.setStyle("-fx-base: #ea2121");
        cancelButton.setStyle("-fx-base: #ea2121");

        HBox hBox = new HBox();
        hBox.setSpacing(112);
        hBox.getChildren().addAll(removeProductButton, cancelButton);

        //container of pop up
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(8, 8, 8, 8));
        //gridPane.setPrefSize(300, 330);
        gridPane.setStyle("-fx-background-color: white");
        gridPane.setVgap(4);
        ColumnConstraints column0 = new ColumnConstraints(250);
        RowConstraints rw0 = new RowConstraints(30);
        RowConstraints rw1 = new RowConstraints();
        rw1.setVgrow(Priority.ALWAYS);
        RowConstraints rw2 = new RowConstraints(30);

        gridPane.getColumnConstraints().addAll(column0);
        gridPane.getRowConstraints().addAll(rw0, rw1, rw2);
        gridPane.add(productNameLabel, 0, 0);
        gridPane.add(supplierList, 0, 1);
        gridPane.add(hBox, 0, 2);

        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: black");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popupRemoveReceipt.centerOnScreen();
        popupRemoveReceipt.setAutoHide(true);
        popupRemoveReceipt.setAutoFix(true);
        popupRemoveReceipt.getContent().add(vBox);

        cancelButton.setOnAction(event -> popupRemoveReceipt.hide());

        removeProductButton.setOnAction(event1 -> {
            if (supplierList.getSelectionModel().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Select first to remove");
                alert.showAndWait();

            } else {
                String receipt = supplierList.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Confirm Delete Of :" + receipt);
                alert.showAndWait().filter(buttonType -> buttonType == ButtonType.OK).ifPresent(buttonType -> {
                    //delete supplier
                    PurchaseCategoryData.removeReceipt(receipt);

                    //hide the popup
                    popupRemoveReceipt.hide();
                    //refresh supplier list
                    supplierList.setItems(PurchaseCategoryData.getAllCashReceipt());
                });

            }
        });
    }

    private void addInvoice() {
        //popup to set a new supplier
        popupAddInvoice = new Popup();
        //control fields
        Label receiptLabel = new Label("NEW INVOICE");
        receiptLabel.setFont(new Font(14));

        TextField invoiceTextField = new TextField();


        Button addProductButton = new Button("Add Invoice");
        Button cancelButton = new Button("Cancel");

        addProductButton.setStyle("-fx-base: #ea2121");
        cancelButton.setStyle("-fx-base: #ea2121");

        HBox hBox = new HBox();
        hBox.setSpacing(80);
        hBox.getChildren().addAll(addProductButton, cancelButton);


        //container of pop up
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(8, 8, 8, 8));
        //gridPane.setPrefSize(300, 330);
        gridPane.setStyle("-fx-background-color: white");
        gridPane.setVgap(4);
        ColumnConstraints column0 = new ColumnConstraints(250);
        RowConstraints rw0 = new RowConstraints(30);
        RowConstraints rw1 = new RowConstraints(30);
        RowConstraints rw2 = new RowConstraints(30);

        gridPane.getColumnConstraints().addAll(column0);
        gridPane.getRowConstraints().addAll(rw0, rw1, rw2);
        gridPane.add(receiptLabel, 0, 0);
        gridPane.add(invoiceTextField, 0, 1);
        gridPane.add(hBox, 0, 2);

        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: #000000");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popupAddInvoice.centerOnScreen();
        popupAddInvoice.setAutoHide(true);
        popupAddInvoice.setAutoFix(true);
        popupAddInvoice.getContent().add(vBox);

        cancelButton.setOnAction(event -> {
            popupAddInvoice.hide();
            invoiceTextField.clear();
        });

        addProductButton.setOnAction(event -> {
            if (invoiceTextField.getText().isEmpty()) invoiceTextField.requestFocus();
            else {

                String invoice = invoiceTextField.getText().trim();

                //hide the popup
                invoiceTextField.clear();
                popupAddInvoice.hide();
                //insert resources into table and create a new supplier table
                System.out.println(invoice);
                PurchaseCategoryData.insertInvoice(invoice);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successful");
                alert.setTitle("Invoice Added");
                alert.show();

            }
        });


    }

    private void removeInvoice() {
        //popup to set a new supplier
        popupRemoveInvoice = new Popup();
        //control fields
        Label productNameLabel = new Label("All Invoice");
        productNameLabel.setFont(new Font(14));

        ListView<String> supplierList = new ListView<>(PurchaseCategoryData.getAllInvoice());

        Button removeProductButton = new Button("Remove");
        Button cancelButton = new Button("Cancel");

        removeProductButton.setStyle("-fx-base: #ea2121");
        cancelButton.setStyle("-fx-base: #ea2121");

        HBox hBox = new HBox();
        hBox.setSpacing(112);
        hBox.getChildren().addAll(removeProductButton, cancelButton);

        //container of pop up
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(8, 8, 8, 8));
        //gridPane.setPrefSize(300, 330);
        gridPane.setStyle("-fx-background-color: white");
        gridPane.setVgap(4);
        ColumnConstraints column0 = new ColumnConstraints(250);
        RowConstraints rw0 = new RowConstraints(30);
        RowConstraints rw1 = new RowConstraints();
        rw1.setVgrow(Priority.ALWAYS);
        RowConstraints rw2 = new RowConstraints(30);

        gridPane.getColumnConstraints().addAll(column0);
        gridPane.getRowConstraints().addAll(rw0, rw1, rw2);
        gridPane.add(productNameLabel, 0, 0);
        gridPane.add(supplierList, 0, 1);
        gridPane.add(hBox, 0, 2);

        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: black");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popupRemoveInvoice.centerOnScreen();
        popupRemoveInvoice.setAutoHide(true);
        popupRemoveInvoice.setAutoFix(true);
        popupRemoveInvoice.getContent().add(vBox);

        cancelButton.setOnAction(event -> popupRemoveInvoice.hide());

        removeProductButton.setOnAction(event1 -> {
            if (supplierList.getSelectionModel().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Select first to remove");
                alert.showAndWait();

            } else {
                String invoice = supplierList.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Confirm Delete Of :" + invoice);
                alert.showAndWait().filter(buttonType -> buttonType == ButtonType.OK).ifPresent(buttonType -> {
                    //delete supplier
                    PurchaseCategoryData.removeInvoice(invoice);

                    //hide the popup
                    popupRemoveInvoice.hide();
                    //refresh supplier list
                    supplierList.setItems(PurchaseCategoryData.getAllInvoice());
                });

            }
        });
    }

    //cash purchase resources model
    public static class CashPurchaseList {
        private final SimpleStringProperty date;
        private final SimpleStringProperty invoice;
        private final SimpleStringProperty product;
        private final SimpleStringProperty category;
        private final SimpleStringProperty unit;
        private final SimpleStringProperty supplier;
        private final SimpleIntegerProperty quantity;
        private final SimpleFloatProperty purchase;
        private final SimpleFloatProperty amount;

        public CashPurchaseList(String date,
                                String invoice,
                                String product,
                                String category,
                                String unit,
                                String supplier,
                                int quantity,
                                float purchase,
                                float amount) {
            this.date = new SimpleStringProperty(date);
            this.invoice = new SimpleStringProperty(invoice);
            this.product = new SimpleStringProperty(product);
            this.category = new SimpleStringProperty(category);
            this.unit = new SimpleStringProperty(unit);
            this.supplier = new SimpleStringProperty(supplier);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.purchase = new SimpleFloatProperty(purchase);
            this.amount = new SimpleFloatProperty(amount);
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getInvoice() {
            return invoice.get();
        }

        public void setInvoice(String invoice) {
            this.invoice.set(invoice);
        }

        public String getProduct() {
            return product.get();
        }

        public void setProduct(String product) {
            this.product.set(product);
        }

        public String getCategory() {
            return category.get();
        }

        public void setCategory(String category) {
            this.category.set(category);
        }

        public String getUnit() {
            return unit.get();
        }

        public void setUnit(String unit) {
            this.unit.set(unit);
        }

        public String getSupplier() {
            return supplier.get();
        }

        public void setSupplier(String supplier) {
            this.supplier.set(supplier);
        }

        public int getQuantity() {
            return quantity.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public float getPurchase() {
            return purchase.get();
        }

        public void setPurchase(float purchase) {
            this.purchase.set(purchase);
        }

        public float getAmount() {
            return amount.get();
        }

        public void setAmount(float amount) {
            this.amount.set(amount);
        }
    }

    //credit purchase resources model
    public static class CreditPurchaseList {
        private final SimpleStringProperty date;
        private final SimpleStringProperty due;
        private final SimpleStringProperty invoice;
        private final SimpleStringProperty product;
        private final SimpleStringProperty category;
        private final SimpleStringProperty unit;
        private final SimpleStringProperty supplier;
        private final SimpleIntegerProperty quantity;
        private final SimpleFloatProperty purchase;
        private final SimpleFloatProperty amount;
        private final SimpleStringProperty status;

        public CreditPurchaseList(String date,
                                  String due,
                                  String invoice,
                                  String product,
                                  String category,
                                  String unit,
                                  String supplier,
                                  int quantity,
                                  float purchase,
                                  float amount,
                                  String status) {
            this.date = new SimpleStringProperty(date);
            this.due = new SimpleStringProperty(due);
            this.invoice = new SimpleStringProperty(invoice);
            this.product = new SimpleStringProperty(product);
            this.category = new SimpleStringProperty(category);
            this.unit = new SimpleStringProperty(unit);
            this.supplier = new SimpleStringProperty(supplier);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.purchase = new SimpleFloatProperty(purchase);
            this.amount = new SimpleFloatProperty(amount);
            this.status = new SimpleStringProperty(status);
        }

        public String getDate() {
            return date.get();
        }

        public String getDue() {
            return due.get();
        }

        public void setDue(String due) {
            this.due.set(due);
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getInvoice() {
            return invoice.get();
        }

        public void setInvoice(String invoice) {
            this.invoice.set(invoice);
        }

        public String getProduct() {
            return product.get();
        }

        public void setProduct(String product) {
            this.product.set(product);
        }

        public String getCategory() {
            return category.get();
        }

        public void setCategory(String category) {
            this.category.set(category);
        }

        public String getUnit() {
            return unit.get();
        }

        public void setUnit(String unit) {
            this.unit.set(unit);
        }

        public String getSupplier() {
            return supplier.get();
        }

        public void setSupplier(String supplier) {
            this.supplier.set(supplier);
        }

        public int getQuantity() {
            return quantity.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public float getPurchase() {
            return purchase.get();
        }

        public void setPurchase(float purchase) {
            this.purchase.set(purchase);
        }

        public float getAmount() {
            return amount.get();
        }

        public void setAmount(float amount) {
            this.amount.set(amount);
        }

        public String getStatus() {
            return status.get();
        }

        public void setStatus(String status) {
            this.status.set(status);
        }
    }

    //unpaid invoice list
    public static class DueInvoiceListTable {

        private final SimpleStringProperty invoice;
        private final SimpleIntegerProperty remain;
        private final SimpleStringProperty due;
        private final SimpleStringProperty status;
        private final SimpleStringProperty supplier;

        public DueInvoiceListTable(
                String invoice,
                int remain,
                String due,
                String status,
                String supplier) {
            this.invoice = new SimpleStringProperty(invoice);
            this.remain = new SimpleIntegerProperty(remain);
            this.due = new SimpleStringProperty(due);
            this.status = new SimpleStringProperty(status);
            this.supplier = new SimpleStringProperty(supplier);
        }

        public String getInvoice() {
            return invoice.get();
        }

        public int getRemain() {
            return remain.get();
        }

        public String getDue() {
            return due.get();
        }

        public String getStatus() {
            return status.get();
        }

        public String getSupplier() {
            return supplier.get();
        }
    }

    //detail of invoice resources model
    public static class InvoiceDetail {

        private final SimpleStringProperty product;
        private final SimpleStringProperty unit;
        private final SimpleIntegerProperty quantity;
        private final SimpleFloatProperty purchase;
        private final SimpleFloatProperty amount;

        public InvoiceDetail(
                String product,
                String unit,
                int quantity,
                float purchase,
                float amount) {
            this.product = new SimpleStringProperty(product);
            this.unit = new SimpleStringProperty(unit);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.purchase = new SimpleFloatProperty(purchase);
            this.amount = new SimpleFloatProperty(amount);
        }

        public String getProduct() {
            return product.get();
        }

        public String getUnit() {
            return unit.get();
        }

        public int getQuantity() {
            return quantity.get();
        }

        public float getPurchase() {
            return purchase.get();
        }

        public float getAmount() {
            return amount.get();
        }
    }

}
