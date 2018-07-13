package com.fahamutech.smartstock.Ui;


import com.fahamutech.smartstock.dataFactory.PurchaseCategoryData;
import com.fahamutech.smartstock.dataFactory.StockCategoryData;
import com.fahamutech.smartstock.provider.BaseDataClass;
import com.mysql.cj.jdbc.MysqlDataSource;
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
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.File;
import java.sql.*;


public class StockCategoryUI extends BaseDataClass {

    //this is the list of all the current stock updated

    public static ObservableList<String> unitList = FXCollections.observableArrayList();
    public static ObservableList<String> categoryList = FXCollections.observableArrayList();

    static Popup popupAddCategory;
    static Popup popupRemoveCategory;
    static Popup popupRemoveUnit;
    static Popup popupAddUnit;

    private static String localhost = "localhost";
    private String username=serverDetail.get("username");
    private String password=serverDetail.get("password");
    private String serverAddress=serverDetail.get("serverAddress");

    StockCategoryUI() {
        addProductCategory();
        addProductUnit();
        removeProductCategory();
        removeProductUnit();
    }

    GridPane setNewStockUI() {

        //resources input controls components
        Label zeroStockLabel = new Label("Zero Stock :");
        Label reorderStockLabel = new Label("Reorder Stock :");
        Label expiredStockLabel = new Label("Expired Stock :");
        Label totalStockLabel = new Label("Total Stock");
        Label productLabel = new Label("Product Name :");
        Label uniLabel = new Label("Unit Of Product :");
        Label categoryLabel = new Label("Category Of Product :");
        Label quantityLabel = new Label("Quantity :");
        Label wQuantityLabel = new Label("WholeSale Quantity");
        Label reorderLevelLabel = new Label("Reorder Level :");
        Label supplierLabel = new Label("Supplier :");
        Label purchaseLabel = new Label("Purchase Price(TZS): ");
        Label sellLabel = new Label("Sell Price(TZS)");
        Label wSellLabel = new Label("WholeSale Price(TZS)");
        Label profitLabel = new Label("Profit(TZS) :");
        Label timesLabel = new Label("Times :");
        Label expireLabel = new Label("Expire Date :");
        Label shelfLabel = new Label("Shelf :");
        Label filterProductLabel = new Label("Filter product:");


        zeroStockLabel.setFont(new Font(14));
        wQuantityLabel.setFont(new Font(14));
        filterProductLabel.setFont(new Font(14));
        reorderStockLabel.setFont(new Font(14));
        expiredStockLabel.setFont(new Font(14));
        totalStockLabel.setFont(new Font(14));
        productLabel.setFont(new Font(14));
        uniLabel.setFont(new Font(14));
        categoryLabel.setFont(new Font(14));
        quantityLabel.setFont(new Font(14));
        reorderLevelLabel.setFont(new Font(14));
        supplierLabel.setFont(new Font(14));
        purchaseLabel.setFont(new Font(14));
        sellLabel.setFont(new Font(14));
        wSellLabel.setFont(new Font(14));
        profitLabel.setFont(new Font(14));
        timesLabel.setFont(new Font(14));
        expireLabel.setFont(new Font(14));
        shelfLabel.setFont(new Font(14));


        TextField productTextField = new TextField();
        TextField filterStockList = new TextField();
        TextField quantityTextField = new TextField();
        TextField wQuantityTextField = new TextField();
        TextField reorderLeverTextField = new TextField();
        TextField purchaseTextField = new TextField();
        TextField sellTextField = new TextField();
        TextField wSellTextField = new TextField();
        TextField profitTextField = new TextField();
        TextField timesTextField = new TextField();
        TextField shelfTextField = new TextField();
        TextField zeroStockTextField = new TextField();
        TextField reorderStockTextField = new TextField();
        TextField expireStockTextField = new TextField();
        TextField lowPotentialStockTextField = new TextField();
        TextField highPotentialStockTextField = new TextField();
        TextField totalStockTextField = new TextField();
        filterStockList.setPromptText("search...");

        //combo box to choose category and unit
        ComboBox<String> categoryChooser = new ComboBox<>();
        ComboBox<String> unitChooser = new ComboBox<>();
        ComboBox<String> supplierChooser = new ComboBox<>();
        supplierChooser.setMinWidth(146);
        supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
        supplierChooser.setEditable(true);
        categoryChooser.setEditable(true);
        unitChooser.setEditable(true);
        categoryChooser.setItems(categoryList);
        unitChooser.setItems(unitList);
        categoryChooser.setMinWidth(146);
        unitChooser.setMinWidth(146);
        StockCategoryData.getAllCategories();
        StockCategoryData.getAllUnit();

        DatePicker expireDatePicker = new DatePicker();

        timesTextField.setEditable(false);
        zeroStockTextField.setEditable(false);
        reorderStockTextField.setEditable(false);
        expireStockTextField.setEditable(false);
        lowPotentialStockTextField.setEditable(false);
        highPotentialStockTextField.setEditable(false);
        totalStockTextField.setEditable(false);
        profitTextField.setEditable(false);
        totalStockTextField.setStyle("-fx-background-color: #b54eb5");
        zeroStockTextField.setStyle("-fx-background-color: #b54eb5");
        expireStockTextField.setStyle("-fx-background-color: #b54eb5");
        reorderStockTextField.setStyle("-fx-background-color: #b54eb5");
        lowPotentialStockTextField.setStyle("-fx-background-color: #b54eb5");
        highPotentialStockTextField.setStyle("-fx-background-color: #b54eb5");
        profitTextField.setStyle("-fx-background-color: #b54eb5");
        timesTextField.setStyle("-fx-background-color: #b54eb5");

        zeroStockTextField.clear();
        expireStockTextField.clear();
        reorderStockTextField.clear();
        totalStockTextField.clear();

        zeroStockTextField.setText(StockCategoryData.getQuickStockReport()[0]);
        reorderStockTextField.setText(StockCategoryData.getQuickStockReport()[1]);
        expireStockTextField.setText(StockCategoryData.getQuickStockReport()[2]);
        totalStockTextField.setText(StockCategoryData.getQuickStockReport()[3]);

        Button addToStockButton = new Button("Add Stock");
        Button cancelStock = new Button("Cancel");

        addToStockButton.setDefaultButton(true);

        //initialize pane
        GridPane dataInputGridPane = new GridPane();
        dataInputGridPane.setPadding(new Insets(4, 4, 4, 4));
        ColumnConstraints inputCol0 = new ColumnConstraints(150);
        ColumnConstraints inputCol1 = new ColumnConstraints();
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
                .addAll(rw1, rw2, rw3, rw4, rw5, rw6, rw7, rw8, rw9, rw10, rw11, rw12, rw13,
                        rw14, rw15);
        //add content to the
        dataInputGridPane.add(productLabel, 0, 1);
        dataInputGridPane.add(productTextField, 1, 1);
        dataInputGridPane.add(uniLabel, 0, 2);
        dataInputGridPane.add(unitChooser, 1, 2);
        dataInputGridPane.add(categoryLabel, 0, 3);
        dataInputGridPane.add(categoryChooser, 1, 3);
        dataInputGridPane.add(quantityLabel, 0, 4);
        dataInputGridPane.add(quantityTextField, 1, 4);
        dataInputGridPane.add(wQuantityLabel, 0, 5);
        dataInputGridPane.add(wQuantityTextField, 1, 5);
        dataInputGridPane.add(reorderLevelLabel, 0, 6);
        dataInputGridPane.add(reorderLeverTextField, 1, 6);
        dataInputGridPane.add(supplierLabel, 0, 7);
        dataInputGridPane.add(supplierChooser, 1, 7);
        dataInputGridPane.add(purchaseLabel, 0, 8);
        dataInputGridPane.add(purchaseTextField, 1, 8);
        dataInputGridPane.add(sellLabel, 0, 9);
        dataInputGridPane.add(sellTextField, 1, 9);
        dataInputGridPane.add(wSellLabel, 0, 10);
        dataInputGridPane.add(wSellTextField, 1, 10);
        dataInputGridPane.add(profitLabel, 0, 11);
        dataInputGridPane.add(profitTextField, 1, 11);
        dataInputGridPane.add(timesLabel, 0, 12);
        dataInputGridPane.add(timesTextField, 1, 12);
        dataInputGridPane.add(expireLabel, 0, 13);
        dataInputGridPane.add(expireDatePicker, 1, 13);
        dataInputGridPane.add(shelfLabel, 0, 14);
        dataInputGridPane.add(shelfTextField, 1, 14);
        dataInputGridPane.add(addToStockButton, 0, 15);
        dataInputGridPane.add(cancelStock, 1, 15);

        TableView<StockList> stockListTableView = new TableView<>();
        stockListTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        stockListTableView.setItems(StockCategoryData.getStockList());
        stockListTableView.setStyle(
                        "-fx-border-color: #330033;" +
                        " -fx-border-width:2 ;" +
                        "-fx-border-radius:5 ;" +
                        "-fx-padding:2.5");
        TableColumn<StockList, String> productColumn = new TableColumn<>("Product");
        TableColumn<StockList, String> unitColumn = new TableColumn<>("Unit");
        TableColumn<StockList, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<StockList, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<StockList, Integer> wquantity = new TableColumn<>("W.Quantity");
        TableColumn<StockList, String> q_updateColumn = new TableColumn<>("Q_update");
        TableColumn<StockList, Integer> reorderColumn = new TableColumn<>("Reorder");
        TableColumn<StockList, String> supplierColumn = new TableColumn<>("Supplier");
        TableColumn<StockList, Float> purchaseColumn = new TableColumn<>("Purchase(TZS)");
        TableColumn<StockList, Float> sellColumn = new TableColumn<>("Selling(TSZ)");
        TableColumn<StockList, Float> wSellColumn = new TableColumn<>("W.Selling(TZS)");
        TableColumn<StockList, Float> profitColumn = new TableColumn<>("Profit(TZS)");
        TableColumn<StockList, Float> timesColumn = new TableColumn<>("Times");
        TableColumn<StockList, Date> expireDateColumn = new TableColumn<>("Expire");
        TableColumn<StockList, String> shelfColumn = new TableColumn<>("Shelf");
        stockListTableView.getColumns().addAll(
                productColumn,
                unitColumn,
                categoryColumn,
                quantityColumn,
                q_updateColumn,
                wquantity,
                reorderColumn,
                supplierColumn,
                purchaseColumn,
                sellColumn,
                wSellColumn,
                profitColumn,
                timesColumn,
                expireDateColumn,
                shelfColumn
        );
        //set cell value factory
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        q_updateColumn.setCellValueFactory(new PropertyValueFactory<>("q_update"));
        wquantity.setCellValueFactory(new PropertyValueFactory<>("wquantity"));
        reorderColumn.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("purchase"));
        sellColumn.setCellValueFactory(new PropertyValueFactory<>("sell"));
        wSellColumn.setCellValueFactory(new PropertyValueFactory<>("wsell"));
        profitColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));
        timesColumn.setCellValueFactory(new PropertyValueFactory<>("times"));
        expireDateColumn.setCellValueFactory(new PropertyValueFactory<>("expire"));
        shelfColumn.setCellValueFactory(new PropertyValueFactory<>("shelf"));
        stockListTableView.autosize();

        //tab pane for table view and some quick reports
        GridPane stockListGridPane = new GridPane();
        stockListGridPane.setPadding(new Insets(4, 4, 0, 10));
        RowConstraints r1 = new RowConstraints(30);
        RowConstraints r2 = new RowConstraints();
        ColumnConstraints c1 = new ColumnConstraints();
        r2.setVgrow(Priority.ALWAYS);
        c1.setHgrow(Priority.ALWAYS);
        stockListGridPane.getColumnConstraints().add(c1);
        stockListGridPane.getRowConstraints().addAll(r1, r2);
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.getChildren().addAll(filterProductLabel, filterStockList);
        stockListGridPane.add(hBox, 0, 0);
        stockListGridPane.add(stockListTableView, 0, 1);


        //main panel to add all contents and this pane will be added to current task area
        GridPane newStockUI = new GridPane();
        newStockUI.setPadding(new Insets(10, 10, 10, 10));
        newStockUI.setId("new_stock");
        RowConstraints row0 = new RowConstraints();
        ColumnConstraints col0 = new ColumnConstraints(300);
        ColumnConstraints col1 = new ColumnConstraints();
        newStockUI.getRowConstraints().add(row0);
        newStockUI.getColumnConstraints().addAll(col0, col1);
        row0.setVgrow(Priority.ALWAYS);
        newStockUI.add(dataInputGridPane, 0, 0);
        newStockUI.add(stockListGridPane, 1, 0);

        stockListTableView.setRowFactory((TableView<StockList> param) -> {
            TableRow<StockList> tableRow = new TableRow<>();
            tableRow.setOnMouseEntered(event -> {
                if (tableRow.isSelected()) {
                    event.consume();
                } else if (tableRow.isEmpty()) {
                    event.consume();
                } else {
                    stockListTableView.requestFocus();
                    stockListTableView.getFocusModel().focus(tableRow.getIndex());
                    tableRow.setStyle("-fx-background-color: rgba(0,0,255,0.15)");
                }
                tableRow.setOnMouseExited(event1 -> {
                    tableRow.setStyle("-fx-base: white");
                });
            });
            return tableRow;
        });
        //implement enter button
        productTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (productTextField.getText().isEmpty()) productTextField.requestFocus();
                    else {
                        unitList.clear();
                        StockCategoryData.getAllUnit();
                        unitChooser.requestFocus();
                        unitChooser.show();
                    }
            }
        });
        unitChooser.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (unitChooser.getSelectionModel().isEmpty()) {
                        unitChooser.requestFocus();
                        unitChooser.show();
                    } else {
                        categoryList.clear();
                        StockCategoryData.getAllCategories();
                        categoryChooser.requestFocus();
                        categoryChooser.show();
                    }
            }
        });
        categoryChooser.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (categoryChooser.getSelectionModel().isEmpty()) {
                        categoryChooser.requestFocus();
                        categoryChooser.show();
                    } else {
                        quantityTextField.requestFocus();
                    }
            }
        });
        quantityTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (quantityTextField.getText().isEmpty()) quantityTextField.requestFocus();
                    else wQuantityTextField.requestFocus();
            }
        });
        wQuantityTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (wQuantityTextField.getText().isEmpty()) wQuantityTextField.requestFocus();
                    else reorderLeverTextField.requestFocus();
            }
        });
        reorderLeverTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (reorderLeverTextField.getText().isEmpty()) reorderLeverTextField.requestFocus();
                    else {
                        supplierChooser.requestFocus();
                        supplierChooser.show();
                    }
            }
        });
        supplierChooser.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (supplierChooser.getSelectionModel().isEmpty()) {
                        supplierChooser.requestFocus();
                        supplierChooser.show();
                    } else purchaseTextField.requestFocus();
            }
        });
        purchaseTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (purchaseTextField.getText().isEmpty()) purchaseTextField.requestFocus();
                    else sellTextField.requestFocus();
            }
        });
        sellTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (sellTextField.getText().isEmpty()) sellTextField.requestFocus();
                    else wSellTextField.requestFocus();
            }
        });
        wSellTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (wSellTextField.getText().isEmpty()) wSellTextField.requestFocus();
                    else {
                        expireDatePicker.requestFocus();
                        expireDatePicker.show();
                    }
            }
        });
        expireDatePicker.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (expireDatePicker.getEditor().getText().isEmpty()) {
                        expireDatePicker.requestFocus();
                        expireDatePicker.show();
                    } else shelfTextField.requestFocus();
            }
        });
        shelfTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (shelfTextField.getText().isEmpty()) {
                        shelfTextField.requestFocus();
                    } else addToStockButton.requestFocus();
            }
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

        filterStockList.setOnKeyReleased(event -> {
            //update list of combo box
            ObservableList<StockList> stocks = StockCategoryData.getStockList();

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

                    stockListTableView.getItems().clear();
                    for (StockList stockList : stocks) {

                        if (!filterStockList.getText().isEmpty()) {
                            String product = filterStockList.getText().toLowerCase();
                            String productStock = stockList.getProduct().toLowerCase();

                            if (productStock.contains(product)) {

                                stockListTableView.getItems().add(stockList);
                            }
                        }

                    }

                    //if the filter text field is empty
                    if (filterStockList.getText().isEmpty()) {
                        stockListTableView.setItems(StockCategoryData.getStockList());
                    }
                    break;
                }

                default: {

                    stockListTableView.getItems().clear();
                    for (StockList stockList : stocks) {

                        if (!filterStockList.getText().isEmpty()) {
                            String product = filterStockList.getText().toLowerCase();
                            String productStock = stockList.getProduct().toLowerCase();

                            if (productStock.contains(product)) {

                                stockListTableView.getItems().add(stockList);
                            }
                        }

                    }
                    break;
                }
            }

        });
        //implement unit chooser and category chooser
        categoryChooser.setOnMouseClicked(event -> {
            categoryList.clear();
            StockCategoryData.getAllCategories();

        });
        unitChooser.setOnMouseClicked(event -> {
            unitList.clear();
            StockCategoryData.getAllUnit();
        });

        //implement stock list table view on option
        stockListTableView.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case SECONDARY:
                    double x = event.getSceneX() + 58;
                    double y = event.getSceneY() + 60;
                    Popup popup = new Popup();
                    popup.setX(x);
                    popup.setY(y);

                    ListView<String> list = new ListView<>();
                    ObservableList<String> data = FXCollections.observableArrayList();
                    data.addAll("Refresh", "Import CSV", "Export CSV");
                    list.setItems(data);
                    list.setStyle("-fx-base: white");
                    list.setPrefSize(150, 120);

                    popup.getContent().addAll(list);
                    popup.setAutoHide(true);
                    popup.show(MainStage.stageAdmin);

                    list.setOnMouseClicked(event1 -> {
                        int selectedIndex = list.getSelectionModel().getSelectedIndex();

                        //refresh clicked implementation
                        if (selectedIndex == 0) {

                            stockListTableView.getItems().clear();
                            stockListTableView.setItems(StockCategoryData.getStockList());

                            //refresh the quick report
                            zeroStockTextField.clear();
                            expireStockTextField.clear();
                            reorderStockTextField.clear();
                            totalStockTextField.clear();
                            zeroStockTextField.setText(StockCategoryData.getQuickStockReport()[0]);
                            reorderStockTextField.setText(StockCategoryData.getQuickStockReport()[1]);
                            expireStockTextField.setText(StockCategoryData.getQuickStockReport()[2]);
                            totalStockTextField.setText(StockCategoryData.getQuickStockReport()[3]);

                            list.setVisible(false);

                        } else if (selectedIndex == 1) {

                            //import a csv to be implemented upo selection
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Open Resource File");
                            fileChooser.getExtensionFilters().addAll(
                                    new FileChooser.ExtensionFilter(
                                            "Text Files", "*.txt"),
                                    new FileChooser.ExtensionFilter(
                                            "CSV files", "*.csv")
                            );

                            File selectedFile = fileChooser.showOpenDialog(MainStage.stageAdmin);

                            if (selectedFile != null) {
                                //implementation
                                System.out.println("File is not equal to null");
                            }

                        } else if (selectedIndex == 2) {
                            //implementation
                            System.out.println("To be implemented");
                            list.setVisible(false);

                        }
                    });

            }
        });
        //implement date picker
        expireDatePicker.getEditor().setOnMouseClicked(event -> expireDatePicker.show());
        expireDatePicker.getEditor().setOnKeyPressed(event -> expireDatePicker.show());

        //implement auto fill of sell price text field
        sellTextField.setOnKeyReleased(event -> {

            if (event.getCode().isDigitKey()) {
                if (purchaseTextField.getText().isEmpty()) {
                    purchaseTextField.requestFocus();
                    purchaseTextField.clear();
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
                    // for times text field
                    float times = Float.parseFloat(sellTextField.getText())
                            / Float.parseFloat(purchaseTextField.getText());
                    //for profit text field
                    float profits = Float.parseFloat(sellTextField.getText());
                    profits = profits - Float.parseFloat(purchaseTextField.getText());

                    profitTextField.setText(String.valueOf(profits));
                    timesTextField.setText(String.valueOf(times));

                } catch (NumberFormatException e) {
                    profitTextField.setText("-" + purchaseTextField.getText());
                    timesTextField.clear();

                }
            }
        });
        //implement add to stock button
        addToStockButton.setOnMouseClicked(event -> {

            if (productTextField.getText().isEmpty()) {
                productTextField.requestFocus();
            } else if (unitChooser.getSelectionModel().getSelectedItem().isEmpty()) {
                unitChooser.requestFocus();
                unitChooser.show();
            } else if (categoryChooser.getSelectionModel().getSelectedItem().isEmpty()) {
                categoryChooser.requestFocus();
                categoryChooser.show();
            } else if (quantityTextField.getText().isEmpty()) {
                quantityTextField.requestFocus();
            } else if (reorderLeverTextField.getText().isEmpty()) {
                reorderLeverTextField.requestFocus();
            } else if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.show();
            } else if (purchaseTextField.getText().isEmpty()) {
                purchaseTextField.requestFocus();
            } else if (sellTextField.getText().isEmpty()) {
                sellTextField.requestFocus();
            } else if (profitTextField.getText().isEmpty()) {
                purchaseTextField.requestFocus();
            } else if (timesTextField.getText().isEmpty()) {
                purchaseTextField.requestFocus();
            } else if (expireDatePicker.getEditor().getText().isEmpty()) {
                expireDatePicker.requestFocus();
                expireDatePicker.show();
            } else if (shelfTextField.getText().isEmpty()) {
                shelfTextField.requestFocus();
            } else {

                //get date in database format
                int year, month, day;
                year = expireDatePicker.getValue().getYear();
                month = expireDatePicker.getValue().getMonth().getValue();
                day = expireDatePicker.getValue().getDayOfMonth();

                //prepare value
                String product = productTextField.getText().trim().replace("'", "");
                String unit = unitChooser.getSelectionModel().getSelectedItem();
                String category = categoryChooser.getSelectionModel().getSelectedItem();
                String shelf = shelfTextField.getText();
                String q_status = "no";
                int quantity = Integer.parseInt(quantityTextField.getText());
                int wQuantity = Integer.parseInt(wQuantityTextField.getText());
                int reorder = Integer.parseInt(reorderLeverTextField.getText());
                String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                float purchase = Float.parseFloat(purchaseTextField.getText());
                float sell = Float.parseFloat(sellTextField.getText());
                float wsell = Float.parseFloat(wSellTextField.getText());
                float profit = Float.parseFloat(profitTextField.getText());
                float times = Float.parseFloat(timesTextField.getText());

                String expireDate;
                if (day < 0) {

                    expireDate = year + "-" + month + "-0" + day;
                } else {
                    expireDate = year + "-" + month + "-" + day;
                }
                //insert into database
                StockCategoryData.insertDataIntoStock(
                        product,
                        unit,
                        category,
                        shelf,
                        quantity,
                        wQuantity,
                        q_status,
                        reorder,
                        supplier,
                        purchase,
                        sell,
                        wsell,
                        profit,
                        times,
                        expireDate
                );
                //update the stock list table
                stockListTableView.getItems().clear();
                stockListTableView.setItems(StockCategoryData.getStockList());

                //update quick reports
                productTextField.clear();
                unitChooser.getSelectionModel().clearSelection();
                categoryChooser.getSelectionModel().clearSelection();
                quantityTextField.clear();
                reorderLeverTextField.clear();
                supplierChooser.getSelectionModel().clearSelection();
                purchaseTextField.clear();
                sellTextField.clear();
                profitTextField.clear();
                timesTextField.clear();
                expireDatePicker.getEditor().clear();
                shelfTextField.clear();
                zeroStockTextField.clear();
                expireStockTextField.clear();
                reorderStockTextField.clear();
                totalStockTextField.clear();
                zeroStockTextField.setText(StockCategoryData.getQuickStockReport()[0]);
                reorderStockTextField.setText(StockCategoryData.getQuickStockReport()[1]);
                expireStockTextField.setText(StockCategoryData.getQuickStockReport()[2]);
                totalStockTextField.setText(StockCategoryData.getQuickStockReport()[3]);
            }
        });
        //implement cancel button
        cancelStock.setOnMouseClicked(event -> {
            productTextField.clear();
            unitChooser.getSelectionModel().clearSelection();
            categoryChooser.getSelectionModel().clearSelection();
            quantityTextField.clear();
            wQuantityTextField.clear();
            reorderLeverTextField.clear();
            supplierChooser.getSelectionModel().clearSelection();
            purchaseTextField.clear();
            sellTextField.clear();
            wSellTextField.clear();
            profitTextField.clear();
            timesTextField.clear();
            expireDatePicker.getEditor().clear();
            shelfTextField.clear();
        });

        return newStockUI;

    }

    GridPane setUpdateStockUI() {
        //array contain the quick reports
        //resources input controls components
        Label zeroStockLabel = new Label("Zero Stock :");
        Label reorderStockLabel = new Label("Reorder Stock :");
        Label expiredStockLabel = new Label("Expired Stock :");
        Label totalStockLabel = new Label("Total Stock");

        Label productLabel = new Label("Choose Product :");
        Label newProductNameLabel = new Label("New Product Name :");
        Label uniLabel = new Label("Unit Of Product :");
        Label categoryLabel = new Label("Category Of Product :");
        Label quantityLabel = new Label("Quantity :");
        Label wQuantityLabel = new Label("WholeSale Quantity :");
        Label reorderLevelLabel = new Label("Reorder Level :");
        Label supplierLabel = new Label("Supplier :");
        Label purchaseLabel = new Label("Purchase Price(TZS) : ");
        Label sellLabel = new Label("Sell Price(TZS) :");
        Label wSellLabel = new Label("WholeSale Price(TZS) :");
        Label profitLabel = new Label("Profit(TSZ) :");
        Label timesLabel = new Label("Times :");
        Label expireLabel = new Label("Expire Date :");
        Label shelfLabel = new Label("Shelf :");
        Label filterProductLabel = new Label("Filter product:");

        zeroStockLabel.setFont(new Font(14));
        wQuantityLabel.setFont(new Font(14));
        filterProductLabel.setFont(new Font(14));
        reorderStockLabel.setFont(new Font(14));
        expiredStockLabel.setFont(new Font(14));
        totalStockLabel.setFont(new Font(14));
        productLabel.setFont(new Font(14));
        uniLabel.setFont(new Font(14));
        categoryLabel.setFont(new Font(14));
        quantityLabel.setFont(new Font(14));
        reorderLevelLabel.setFont(new Font(14));
        supplierLabel.setFont(new Font(14));
        purchaseLabel.setFont(new Font(14));
        sellLabel.setFont(new Font(14));
        wSellLabel.setFont(new Font(14));
        profitLabel.setFont(new Font(14));
        timesLabel.setFont(new Font(14));
        expireLabel.setFont(new Font(14));
        shelfLabel.setFont(new Font(14));

        //Observable list for combo box
        //combo box to choose category and unit
        ComboBox<String> categoryChooser = new ComboBox<>();
        ComboBox<String> unitChooser = new ComboBox<>();
        ComboBox<String> supplierChooser = new ComboBox<>();

        supplierChooser.setMinWidth(146);
        supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
        //its contents already added to the new Stock Ui
        categoryChooser.setItems(categoryList);
        unitChooser.setItems(unitList);
        categoryChooser.setEditable(true);
        unitChooser.setEditable(true);
        supplierChooser.setEditable(true);
        categoryChooser.setMinWidth(146);
        unitChooser.setMinWidth(146);

        ComboBox<String> productChooser = new ComboBox<>();
        productChooser.setEditable(true);
        productChooser.setItems(StockCategoryData.getProductNames());

        //the text fields
        TextField quantityTextField = new TextField();
        TextField wQuantityTextField = new TextField();
        TextField reorderLeverTextField = new TextField();
        TextField purchaseTextField = new TextField();
        TextField sellTextField = new TextField();
        TextField wSellTextField = new TextField();
        TextField profitTextField = new TextField();
        TextField newProductNameTextField = new TextField();
        TextField timesTextField = new TextField();
        TextField shelfTextField = new TextField();
        TextField zeroStockTextField = new TextField();
        TextField reorderStockTextField = new TextField();
        TextField expireStockTextField = new TextField();
        TextField totalStockTextField = new TextField();
        TextField filterStockList = new TextField();
        filterStockList.setPromptText("search...");

        DatePicker expireDatePicker = new DatePicker();

        timesTextField.setEditable(false);
        zeroStockTextField.setEditable(false);
        reorderStockTextField.setEditable(false);
        expireStockTextField.setEditable(false);
        totalStockTextField.setEditable(false);
        profitTextField.setEditable(false);

        totalStockTextField.setStyle("-fx-background-color: #b54eb5");
        zeroStockTextField.setStyle("-fx-background-color: #b54eb5");
        expireStockTextField.setStyle("-fx-background-color: #b54eb5");
        reorderStockTextField.setStyle("-fx-background-color: #b54eb5");
        timesTextField.setStyle("-fx-background-color: #b54eb5");
        profitTextField.setStyle("-fx-background-color: #b54eb5");
        newProductNameTextField.setPromptText("click me...");
        zeroStockTextField.clear();
        expireStockTextField.clear();
        reorderStockTextField.clear();
        totalStockTextField.clear();

        zeroStockTextField.setText(StockCategoryData.getQuickStockReport()[0]);
        reorderStockTextField.setText(StockCategoryData.getQuickStockReport()[1]);
        expireStockTextField.setText(StockCategoryData.getQuickStockReport()[2]);
        totalStockTextField.setText(StockCategoryData.getQuickStockReport()[3]);


        Button updateStockButton = new Button("Update");
        Button cancelStock = new Button("Cancel");
        Button loadButton = new Button("Load Data");
        loadButton.setDefaultButton(true);


        updateStockButton.setDefaultButton(true);


        //initialize pane
        GridPane dataInputGridPane = new GridPane();
        dataInputGridPane.setPadding(new Insets(4, 4, 4, 4));
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
        //RowConstraints rw20 = new RowConstraints(30);
        dataInputGridPane.getColumnConstraints().addAll(inputCol0, inputCol1);
        dataInputGridPane.getRowConstraints()
                .addAll(rw0, rw1, rw2, rw3, rw4, rw5, rw6, rw7, rw8, rw9, rw10, rw11, rw12, rw13,
                        rw14, rw15);
        //add content to the pane
        dataInputGridPane.add(loadButton, 0, 0);
        dataInputGridPane.add(productLabel, 0, 1);
        dataInputGridPane.add(productChooser, 1, 1);
        dataInputGridPane.add(newProductNameLabel, 0, 2);
        dataInputGridPane.add(newProductNameTextField, 1, 2);
        dataInputGridPane.add(uniLabel, 0, 3);
        dataInputGridPane.add(unitChooser, 1, 3);
        dataInputGridPane.add(categoryLabel, 0, 4);
        dataInputGridPane.add(categoryChooser, 1, 4);
        dataInputGridPane.add(quantityLabel, 0, 5);
        dataInputGridPane.add(quantityTextField, 1, 5);
        dataInputGridPane.add(wQuantityLabel, 0, 6);
        dataInputGridPane.add(wQuantityTextField, 1, 6);
        dataInputGridPane.add(reorderLevelLabel, 0, 7);
        dataInputGridPane.add(reorderLeverTextField, 1, 7);
        dataInputGridPane.add(supplierLabel, 0, 8);
        dataInputGridPane.add(supplierChooser, 1, 8);
        dataInputGridPane.add(purchaseLabel, 0, 9);
        dataInputGridPane.add(purchaseTextField, 1, 9);
        dataInputGridPane.add(sellLabel, 0, 10);
        dataInputGridPane.add(sellTextField, 1, 10);
        dataInputGridPane.add(wSellLabel, 0, 11);
        dataInputGridPane.add(wSellTextField, 1, 11);
        dataInputGridPane.add(profitLabel, 0, 12);
        dataInputGridPane.add(profitTextField, 1, 12);
        dataInputGridPane.add(timesLabel, 0, 13);
        dataInputGridPane.add(timesTextField, 1, 13);
        dataInputGridPane.add(expireLabel, 0, 14);
        dataInputGridPane.add(expireDatePicker, 1, 14);
        dataInputGridPane.add(shelfLabel, 0, 15);
        dataInputGridPane.add(shelfTextField, 1, 15);
        dataInputGridPane.add(updateStockButton, 0, 16);
        dataInputGridPane.add(cancelStock, 1, 16);

        TableView<StockList> stockListTableView = new TableView<>();
        stockListTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        stockListTableView.setItems(StockCategoryData.getStockList());
        stockListTableView.setStyle("-fx-border-color: #330033; -fx-border-width: 2 ; -fx-border-radius: 5 ;-fx-padding:2.5");
        TableColumn<StockList, String> productColumn = new TableColumn<>("Product");
        TableColumn<StockList, String> unitColumn = new TableColumn<>("Unit");
        TableColumn<StockList, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<StockList, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<StockList, Integer> wquantity = new TableColumn<>("W.Quantity");
        TableColumn<StockList, String> q_updateColumn = new TableColumn<>("Q_update");
        TableColumn<StockList, Integer> reorderColumn = new TableColumn<>("Reorder");
        TableColumn<StockList, String> supplierColumn = new TableColumn<>("Supplier");
        TableColumn<StockList, Float> purchaseColumn = new TableColumn<>("Purchase(TZS)");
        TableColumn<StockList, Float> sellColumn = new TableColumn<>("Selling(TSZ)");
        TableColumn<StockList, Float> wSellColumn = new TableColumn<>("W.Selling(TZS)");
        TableColumn<StockList, Float> profitColumn = new TableColumn<>("Profit(TZS)");
        TableColumn<StockList, Float> timesColumn = new TableColumn<>("Times");
        TableColumn<StockList, Date> expireDateColumn = new TableColumn<>("Expire");
        TableColumn<StockList, String> shelfColumn = new TableColumn<>("Shelf");
        stockListTableView.getColumns().addAll(
                productColumn,
                unitColumn,
                categoryColumn,
                quantityColumn,
                q_updateColumn,
                wquantity,
                reorderColumn,
                supplierColumn,
                purchaseColumn,
                sellColumn,
                wSellColumn,
                profitColumn,
                timesColumn,
                expireDateColumn,
                shelfColumn
        );
        //set cell value factory
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        q_updateColumn.setCellValueFactory(new PropertyValueFactory<>("q_update"));
        wquantity.setCellValueFactory(new PropertyValueFactory<>("wquantity"));
        reorderColumn.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("purchase"));
        sellColumn.setCellValueFactory(new PropertyValueFactory<>("sell"));
        wSellColumn.setCellValueFactory(new PropertyValueFactory<>("wsell"));
        profitColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));
        timesColumn.setCellValueFactory(new PropertyValueFactory<>("times"));
        expireDateColumn.setCellValueFactory(new PropertyValueFactory<>("expire"));
        shelfColumn.setCellValueFactory(new PropertyValueFactory<>("shelf"));
        stockListTableView.autosize();

        //tab pane for table view and some quick reports
        GridPane stockListGridPane = new GridPane();
        stockListGridPane.setPadding(new Insets(4, 4, 0, 10));
        RowConstraints r1 = new RowConstraints(30);
        RowConstraints r2 = new RowConstraints();
        ColumnConstraints c1 = new ColumnConstraints();
        r2.setVgrow(Priority.ALWAYS);
        c1.setHgrow(Priority.ALWAYS);
        stockListGridPane.getColumnConstraints().add(c1);
        stockListGridPane.getRowConstraints().addAll(r1, r2);
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.getChildren().addAll(filterProductLabel, filterStockList);
        stockListGridPane.add(hBox, 0, 0);
        stockListGridPane.add(stockListTableView, 0, 1);

        //main panel to add all contents and this pane will be added to current task area
        GridPane updateStockUI = new GridPane();
        updateStockUI.setPadding(new Insets(10, 10, 10, 10));
        updateStockUI.setId("update_stock");
        RowConstraints row0 = new RowConstraints();
        ColumnConstraints col0 = new ColumnConstraints(300);
        ColumnConstraints col1 = new ColumnConstraints();
        updateStockUI.getRowConstraints().add(row0);
        updateStockUI.getColumnConstraints().addAll(col0, col1);
        row0.setVgrow(Priority.ALWAYS);
        updateStockUI.add(dataInputGridPane, 0, 0);
        updateStockUI.add(stockListGridPane, 1, 0);


        stockListTableView.setRowFactory((TableView<StockList> param) -> {
            TableRow<StockList> tableRow = new TableRow<>();
            tableRow.setOnMouseEntered(event -> {
                if (tableRow.isSelected()) {
                    event.consume();
                } else if (tableRow.isEmpty()) {
                    event.consume();
                } else {
                    stockListTableView.requestFocus();
                    stockListTableView.getFocusModel().focus(tableRow.getIndex());
                    tableRow.setStyle("-fx-background-color: rgba(0,0,255,0.15)");
                }
                tableRow.setOnMouseExited(event1 -> {
                    tableRow.setStyle("-fx-base: white");
                });
            });
            return tableRow;
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
        supplierChooser.setOnMouseClicked(event -> {
            supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
            supplierChooser.show();
        });
        supplierChooser.getEditor().setOnMouseClicked(event -> {
            supplierChooser.setItems(PurchaseCategoryData.getSuppliers());
            supplierChooser.show();
        });

        //implement the enter button on text fields
        stockListTableView.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case SECONDARY: {
                    if (stockListTableView.getSelectionModel().isEmpty()) {
                        event.consume();
                    } else {
                        double x = event.getSceneX() + 50;
                        double y = event.getSceneY() + 70;
                        Popup popup = new Popup();
                        popup.setX(x);
                        popup.setY(y);

                        ListView<String> list = new ListView<>();
                        ObservableList<String> data = FXCollections.observableArrayList();
                        data.addAll("Delete ", "Update ", "Refresh", "Print");
                        list.setItems(data);
                        list.setStyle("-fx-background-color: white");
                        list.setPrefSize(100, 130);
                        list.setOnMouseClicked(event1 -> {
                            int selectedIndex = list.getSelectionModel().getSelectedIndex();

                            //when delete option clicked
                            if (selectedIndex == 0) {

                                ObservableList<StockList> stockL
                                        = stockListTableView.getSelectionModel().getSelectedItems();
                                for (StockList list1 : stockL) {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setContentText("Confirm Removal Of\n" + list1.getProduct());
                                    alert.showAndWait()
                                            .filter(buttonType -> buttonType == ButtonType.OK)
                                            .ifPresent(buttonType -> {
                                                String product = list1.getProduct();
                                                StockCategoryData.deleteProduct(product);
                                            });

                                }

                                //update stock list
                                stockListTableView.getItems().clear();
                                stockListTableView.setItems(StockCategoryData.getStockList());

                                list.setVisible(false);
                                //update the quick reports
                                zeroStockTextField.clear();
                                expireStockTextField.clear();
                                reorderStockTextField.clear();
                                totalStockTextField.clear();
                                zeroStockTextField.setText(StockCategoryData.getQuickStockReport()[0]);
                                reorderStockTextField.setText(StockCategoryData.getQuickStockReport()[1]);
                                expireStockTextField.setText(StockCategoryData.getQuickStockReport()[2]);
                                totalStockTextField.setText(StockCategoryData.getQuickStockReport()[3]);


                            } else if (selectedIndex == 1) {     //this update the selected product

                                if (stockListTableView.getSelectionModel().isEmpty()) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    //alert.setContentText("Sele");
                                }
                                //connect to the database
                                MysqlDataSource mysqlDataSource = new MysqlDataSource();
                                mysqlDataSource.setUser(username);
                                mysqlDataSource.setPassword(password);
                                mysqlDataSource.setServerName(serverAddress);

                                Connection connection = null;

                                try {
                                    String selectQuery = "SELECT * FROM retailStock WHERE product=\'"
                                            + stockListTableView.getSelectionModel()
                                            .getSelectedItem().getProduct() + "\'";
                                    String selectDatabase = "use stockdata";

                                    try {
                                        connection = mysqlDataSource.getConnection();
                                    } catch (SQLException e) {
                                        mysqlDataSource.setServerName(localhost);
                                        connection = mysqlDataSource.getConnection();
                                    }
                                    //select database
                                    Statement statement = connection.createStatement();
                                    statement.execute(selectDatabase);
                                    //get the results
                                    ResultSet resultSet = statement.executeQuery(selectQuery);
                                    while (resultSet.next()) {
                                        productChooser.getSelectionModel().select(
                                                resultSet.getString("product")
                                        );
                                        newProductNameTextField
                                                .setText(resultSet.getString("product"));
                                        unitChooser.getEditor()
                                                .setText(resultSet.getString("unit"));
                                        categoryChooser.getEditor()
                                                .setText(resultSet.getString("category"));
                                        quantityTextField
                                                .setText(String.valueOf(resultSet.getInt("quantity")));
                                        wQuantityTextField
                                                .setText(String.valueOf(resultSet.getInt("wquantity")));
                                        reorderLeverTextField
                                                .setText(String.valueOf(resultSet.getInt("reorder")));
                                        supplierChooser.getSelectionModel()
                                                .select(resultSet.getString("supplier"));
                                        purchaseTextField
                                                .setText(String.valueOf(resultSet.getFloat("purchase")));
                                        sellTextField
                                                .setText(String.valueOf(resultSet.getFloat("sell")));
                                        wSellTextField.setText(String.valueOf(resultSet.getFloat("wsell")));
                                        profitTextField
                                                .setText(String.valueOf(resultSet.getFloat("profit")));
                                        timesTextField
                                                .setText(String.valueOf(resultSet.getFloat("times")));
                                        expireDatePicker.getEditor()
                                                .setText(resultSet.getString("expire"));
                                        shelfTextField
                                                .setText(resultSet.getString("shelf"));

                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (connection != null) try {
                                        connection.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }

                                updateStockButton.requestFocus();
                                list.setVisible(false);

                            } else if (selectedIndex == 2) {   //this when refresh option clicked

                                stockListTableView.getItems().clear();
                                stockListTableView.setItems(StockCategoryData.getStockList());

                                //refresh the quick reports
                                zeroStockTextField.clear();
                                expireStockTextField.clear();
                                reorderStockTextField.clear();
                                totalStockTextField.clear();
                                zeroStockTextField.setText(StockCategoryData.getQuickStockReport()[0]);
                                reorderStockTextField.setText(StockCategoryData.getQuickStockReport()[1]);
                                expireStockTextField.setText(StockCategoryData.getQuickStockReport()[2]);
                                totalStockTextField.setText(StockCategoryData.getQuickStockReport()[3]);
                                list.setVisible(false);
                            } else if (selectedIndex == 3) {
                                //printJob

                            }

                        });

                        VBox vBox = new VBox();
                        vBox.setSpacing(3);

                        vBox.setStyle("-fx-background-color: #330033");
                        vBox.getChildren().addAll(list);

                        popup.getContent().addAll(list);
                        popup.setAutoHide(true);
                        popup.show(MainStage.stageAdmin);
                    }
                }
            }

        });

        productChooser.setOnKeyPressed(event -> {
            if (!productChooser.getSelectionModel().isEmpty()) {
                switch (event.getCode()) {
                    case ENTER:
                        //connect to the database
                        MysqlDataSource mysqlDataSource = new MysqlDataSource();
                        mysqlDataSource.setUser(username);
                        mysqlDataSource.setPassword(password);
                        mysqlDataSource.setServerName(serverAddress);

                        Connection connection = null;
                        try {
                            try {

                                connection = mysqlDataSource.getConnection();
                            } catch (SQLException e) {
                                mysqlDataSource.setServerName("127.0.0.1");
                                connection = mysqlDataSource.getConnection();
                            }
                            String selectQuery = "SELECT * FROM retailStock WHERE product=\'"
                                    + productChooser.getSelectionModel().getSelectedItem() + "\'";
                            String selectDatabase = "use stockdata";
                            //select database
                            Statement statement = connection.createStatement();
                            statement.execute(selectDatabase);
                            //get the results
                            ResultSet resultSet = statement.executeQuery(selectQuery);
                            while (resultSet.next()) {
                                unitChooser.getEditor().setText(resultSet.getString("unit"));
                                categoryChooser.getEditor().setText(resultSet.getString("category"));
                                quantityTextField.setText(String.valueOf(resultSet.getInt("quantity")));
                                wQuantityTextField.setText(String.valueOf(resultSet.getInt("wquantity")));
                                newProductNameTextField.setText(resultSet.getString("product"));
                                reorderLeverTextField.setText(String.valueOf(resultSet.getInt("reorder")));
                                supplierChooser.getSelectionModel().select(resultSet.getString("supplier"));
                                purchaseTextField.setText(String.valueOf(resultSet.getFloat("purchase")));
                                sellTextField.setText(String.valueOf(resultSet.getFloat("sell")));
                                wSellTextField.setText(String.valueOf(resultSet.getFloat("wsell")));
                                profitTextField.setText(String.valueOf(resultSet.getFloat("profit")));
                                timesTextField.setText(String.valueOf(resultSet.getFloat("times")));
                                expireDatePicker.getEditor().setText(resultSet.getString("expire"));
                                shelfTextField.setText(resultSet.getString("shelf"));

                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            if (connection != null) try {
                                connection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        newProductNameTextField.requestFocus();
                        break;
                    case UP:
                        event.consume();
                        break;
                    case DOWN:
                        event.consume();
                        break;
                    case LEFT:
                        event.consume();
                        break;
                    case RIGHT:
                        event.consume();
                        break;
                    case ALT:
                        event.consume();
                        break;
                    case CONTROL:
                        event.consume();
                        break;
                    case SHIFT:
                        event.consume();
                        break;
                }
            }
        });
        newProductNameTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (newProductNameTextField.getText().isEmpty()) newProductNameTextField.requestFocus();
                    else quantityTextField.requestFocus();
                }
            }
        });
        unitChooser.getEditor().setOnKeyPressed(event -> {
            if (!unitChooser.getEditor().getText().isEmpty()) {
                if (event.getCode() == KeyCode.ENTER) {
                    categoryChooser.requestFocus();
                    categoryChooser.show();
                }
            }
        });
        newProductNameTextField.setOnMouseClicked(event -> {
            if (!productChooser.getSelectionModel().isEmpty()) {
                //connect to the database
                MysqlDataSource mysqlDataSource = new MysqlDataSource();
                mysqlDataSource.setUser(username);
                mysqlDataSource.setPassword(password);
                mysqlDataSource.setServerName(serverAddress);

                Connection connection = null;

                try {
                    String selectQuery = "SELECT * FROM retailStock WHERE product=\'"
                            + productChooser.getSelectionModel().getSelectedItem() + "\'";
                    String selectDatabase = "use stockdata";

                    try {
                        connection = mysqlDataSource.getConnection();
                    } catch (SQLException e) {
                        mysqlDataSource.setServerName(localhost);
                        connection = mysqlDataSource.getConnection();
                    }
                    //select database
                    Statement statement = connection.createStatement();
                    statement.execute(selectDatabase);
                    //get the results
                    ResultSet resultSet = statement.executeQuery(selectQuery);
                    while (resultSet.next()) {
                        newProductNameTextField.setText(resultSet.getString("product"));
                        unitChooser.getEditor().setText(resultSet.getString("unit"));
                        categoryChooser.getEditor().setText(resultSet.getString("category"));
                        quantityTextField.setText(String.valueOf(resultSet.getInt("quantity")));
                        wQuantityTextField.setText(String.valueOf(resultSet.getInt("wquantity")));
                        reorderLeverTextField.setText(String.valueOf(resultSet.getInt("reorder")));
                        supplierChooser.getSelectionModel().select(resultSet.getString("supplier"));
                        purchaseTextField.setText(String.valueOf(resultSet.getFloat("purchase")));
                        sellTextField.setText(String.valueOf(resultSet.getFloat("sell")));
                        wSellTextField.setText(String.valueOf(resultSet.getFloat("wsell")));
                        profitTextField.setText(String.valueOf(resultSet.getFloat("profit")));
                        timesTextField.setText(String.valueOf(resultSet.getFloat("times")));
                        expireDatePicker.getEditor().setText(resultSet.getString("expire"));
                        shelfTextField.setText(resultSet.getString("shelf"));

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        loadButton.setOnAction(event -> {
            if (productChooser.getSelectionModel().isEmpty()) {
                productChooser.requestFocus();
                productChooser.show();
            } else {
                //connect to the database
                MysqlDataSource mysqlDataSource = new MysqlDataSource();
                mysqlDataSource.setUser(username);
                mysqlDataSource.setPassword(password);
                mysqlDataSource.setServerName(serverAddress);

                Connection connection = null;

                try {
                    String selectQuery = "SELECT * FROM retailStock WHERE product=\'"
                            + productChooser.getSelectionModel().getSelectedItem() + "\'";
                    String selectDatabase = "use stockdata";

                    try {
                        connection = mysqlDataSource.getConnection();
                    } catch (SQLException e) {
                        mysqlDataSource.setServerName(localhost);
                        connection = mysqlDataSource.getConnection();
                    }
                    //select database
                    Statement statement = connection.createStatement();
                    statement.execute(selectDatabase);
                    //get the results
                    ResultSet resultSet = statement.executeQuery(selectQuery);
                    while (resultSet.next()) {
                        newProductNameTextField.setText(resultSet.getString("product"));
                        unitChooser.getEditor().setText(resultSet.getString("unit"));
                        categoryChooser.getEditor().setText(resultSet.getString("category"));
                        quantityTextField.setText(String.valueOf(resultSet.getInt("quantity")));
                        wQuantityTextField.setText(String.valueOf(resultSet.getInt("wquantity")));
                        reorderLeverTextField.setText(String.valueOf(resultSet.getInt("reorder")));
                        supplierChooser.getEditor().setText(resultSet.getString("supplier"));
                        purchaseTextField.setText(String.valueOf(resultSet.getFloat("purchase")));
                        sellTextField.setText(String.valueOf(resultSet.getFloat("sell")));
                        wSellTextField.setText(String.valueOf(resultSet.getFloat("wsell")));
                        profitTextField.setText(String.valueOf(resultSet.getFloat("profit")));
                        timesTextField.setText(String.valueOf(resultSet.getFloat("times")));
                        expireDatePicker.getEditor().setText(resultSet.getString("expire"));
                        shelfTextField.setText(resultSet.getString("shelf"));

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        categoryChooser.getEditor().setOnKeyPressed(event -> {
            if (!categoryChooser.getEditor().getText().isEmpty()) {
                if (event.getCode() == KeyCode.ENTER) quantityTextField.requestFocus();
            }
        });
        wQuantityTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (wQuantityTextField.getText().isEmpty()) wQuantityTextField.requestFocus();
                    else reorderLeverTextField.requestFocus();
                }
            }
        });

        quantityTextField.setOnKeyPressed(event -> {
            if (!quantityTextField.getText().isEmpty()) {
                if (event.getCode() == KeyCode.ENTER) wQuantityTextField.requestFocus();
            }
        });
        reorderLeverTextField.setOnKeyPressed(event -> {
            if (!reorderLeverTextField.getText().isEmpty()) {
                if (event.getCode() == KeyCode.ENTER) {
                    purchaseTextField.requestFocus();
                }
            }
        });
        purchaseTextField.setOnKeyPressed(event -> {
            if (!purchaseTextField.getText().isEmpty()) {
                if (event.getCode() == KeyCode.ENTER) sellTextField.requestFocus();
            }
        });
        sellTextField.setOnKeyPressed(event -> {
            if (!sellTextField.getText().isEmpty()) {
                if (event.getCode() == KeyCode.ENTER) wSellTextField.requestFocus();
            }
        });
        wSellTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (wSellTextField.getText().isEmpty()) {
                        wSellTextField.requestFocus();
                    } else {
                        updateStockButton.requestFocus();
                    }
                }
            }
        });
        expireDatePicker.setOnKeyPressed(event -> {
            if (!expireStockTextField.getText().isEmpty()) {
                expireDatePicker.show();
                if (event.getCode() == KeyCode.ENTER) shelfTextField.requestFocus();
            }
        });
        shelfTextField.setOnKeyPressed(event -> {
            if (!expireDatePicker.getEditor().getText().isEmpty()) {
                if (event.getCode() == KeyCode.ENTER) updateStockButton.requestFocus();
            }
        });
        //implement combo box which is product chooser
        productChooser.getEditor().setOnMouseClicked(event -> {
            productChooser.getSelectionModel().clearSelection();
            unitChooser.getEditor().clear();
            newProductNameTextField.clear();
            unitChooser.getSelectionModel().clearSelection();
            categoryChooser.getEditor().clear();
            categoryChooser.getSelectionModel().clearSelection();
            quantityTextField.clear();
            wQuantityTextField.clear();
            reorderLeverTextField.clear();
            supplierChooser.getEditor().clear();
            supplierChooser.getSelectionModel().clearSelection();
            purchaseTextField.clear();
            sellTextField.clear();
            wSellTextField.clear();
            profitTextField.clear();
            timesTextField.clear();
            expireDatePicker.getEditor().clear();
            shelfTextField.clear();

            productChooser.setItems(StockCategoryData.getProductNames());
            productChooser.show();
        });
        //auto complete profit and times button
        sellTextField.setOnKeyReleased(event -> {
            if (event.getCode().isDigitKey()) {
                if (purchaseTextField.getText().isEmpty()) {
                    purchaseTextField.requestFocus();
                    purchaseTextField.clear();
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
                    // for times text field
                    float times = Float.parseFloat(sellTextField.getText())
                            / Float.parseFloat(purchaseTextField.getText());
                    //for profit text field
                    float profits = Float.parseFloat(sellTextField.getText());
                    profits = profits - Float.parseFloat(purchaseTextField.getText());

                    profitTextField.setText(String.valueOf(profits));
                    timesTextField.setText(String.valueOf(times));

                } catch (NumberFormatException e) {
                    profitTextField.setText("-" + purchaseTextField.getText());
                    timesTextField.clear();

                }
            }
        });
        //purchase text field on digit entered
        purchaseTextField.setOnKeyReleased(event -> {
            if (event.getCode().isDigitKey()) {
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
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                try {
                    // for times text field
                    float times = Float.parseFloat(sellTextField.getText())
                            / Float.parseFloat(purchaseTextField.getText());
                    //for profit text field
                    float profits = Float.parseFloat(sellTextField.getText());
                    profits = profits - Float.parseFloat(purchaseTextField.getText());

                    profitTextField.setText(String.valueOf(profits));
                    timesTextField.setText(String.valueOf(times));

                } catch (NumberFormatException e) {
                    profitTextField.setText("-" + purchaseTextField.getText());
                    timesTextField.clear();

                }
            }
        });
        //prompt for product from the stock
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
                case ENTER:
                    event.consume();
                    break;
                case BACK_SPACE:
                    //clear selection and update variable depend on selection
                    unitChooser.getEditor().clear();
                    newProductNameTextField.clear();
                    unitChooser.getSelectionModel().clearSelection();
                    categoryChooser.getEditor().clear();
                    categoryChooser.getSelectionModel().clearSelection();
                    quantityTextField.clear();
                    wQuantityTextField.clear();
                    reorderLeverTextField.clear();
                    supplierChooser.getEditor().clear();
                    supplierChooser.getSelectionModel().clearSelection();
                    purchaseTextField.clear();
                    sellTextField.clear();
                    wSellTextField.clear();
                    profitTextField.clear();
                    timesTextField.clear();
                    expireDatePicker.getEditor().clear();
                    shelfTextField.clear();

                    //show the updated list
                    productChooser.show();
                    productChooser.getItems().clear();
                    //filter the products with the word typed
                    for (String product : products) {
                        String inputString = productChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {

                            productChooser.getItems().add(product);
                        }
                    }
                    if (productChooser.getItems().isEmpty()) productChooser.getItems().add("No such item in stock");
                    if (productChooser.getEditor().getText().isEmpty()) productChooser.setItems(products);
                    //update the list to match typed word

                    break;
                default:
                    //clear content if product is not empty
                    productChooser.show();
                    productChooser.getItems().clear();
                    for (String product : products) {

                        String inputString = productChooser.getEditor().getText().toLowerCase();
                        String productStock = product.toLowerCase();

                        if (productStock.contains(inputString)) {

                            productChooser.getItems().add(product);
                        }
                    }
                    if (productChooser.getItems().isEmpty()) productChooser.getItems().add("No such item in stock");
                    break;
            }


        });

        //filter contents of the table
        filterStockList.setOnKeyReleased(event -> {
            //update list of combo box
            ObservableList<StockList> stocks = StockCategoryData.getStockList();

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

                    stockListTableView.getItems().clear();

                    for (StockList stockList : stocks) {

                        if (!filterStockList.getText().isEmpty()) {
                            String product = filterStockList.getText().toLowerCase();
                            String productStock = stockList.getProduct().toLowerCase();

                            if (productStock.contains(product)) {

                                stockListTableView.getItems().add(stockList);
                            }
                        }

                    }
                    if (filterStockList.getText().isEmpty()) stockListTableView.setItems(stocks);
                    break;
                }

                default: {

                    stockListTableView.getItems().clear();

                    for (StockList stockList : stocks) {

                        if (!filterStockList.getText().isEmpty()) {
                            String product = filterStockList.getText().toLowerCase();
                            String productStock = stockList.getProduct().toLowerCase();

                            if (productStock.contains(product)) {

                                stockListTableView.getItems().add(stockList);
                            }
                        }

                    }

                    break;
                }
            }

            if (filterStockList.getText().isEmpty()) {
                stockListTableView.setItems(StockCategoryData.getStockList());
            }

        });

        //implement date picker
        expireDatePicker.getEditor().setOnKeyPressed(event -> expireDatePicker.show());
        //implement update button
        updateStockButton.setOnMouseClicked(event -> {
            //look all the input for null value
            if (productChooser.getSelectionModel().isEmpty() && newProductNameTextField.getText().isEmpty()) {
                productChooser.requestFocus();
            } else if (unitChooser.getEditor().getText().isEmpty()) {
                unitChooser.requestFocus();
            } else if (categoryChooser.getEditor().getText().isEmpty()) {
                categoryChooser.getEditor().requestFocus();
            } else if (quantityTextField.getText().isEmpty()) {
                quantityTextField.requestFocus();
            } else if (reorderLeverTextField.getText().isEmpty()) {
                reorderLeverTextField.requestFocus();
            } else if (supplierChooser.getSelectionModel().isEmpty()) {
                supplierChooser.requestFocus();
                supplierChooser.show();
            } else if (purchaseTextField.getText().isEmpty()) {
                purchaseTextField.requestFocus();
            } else if (sellTextField.getText().isEmpty()) {
                sellTextField.requestFocus();
            } else if (profitTextField.getText().isEmpty()) {
                purchaseTextField.requestFocus();
            } else if (timesTextField.getText().isEmpty()) {
                purchaseTextField.requestFocus();
            } else if (expireDatePicker.getEditor().getText().isEmpty()) {
                expireDatePicker.requestFocus();
                expireDatePicker.show();
            } else if (shelfTextField.getText().isEmpty()) {
                shelfTextField.requestFocus();
            } else {

                //get date in database format
                int year, month, day;
                String expireDate;

                try {
                    year = expireDatePicker.getValue().getYear();
                    month = expireDatePicker.getValue().getMonth().getValue();
                    day = expireDatePicker.getValue().getDayOfMonth();
                    if (day < 0) {
                        expireDate = year + "-" + month + "-0" + day;
                    } else {
                        expireDate = year + "-" + month + "-" + day;
                    }


                } catch (NullPointerException nullP) {
                    if (expireDatePicker.getEditor().getText().isEmpty()) {
                        expireDatePicker.requestFocus();
                        expireDatePicker.show();
                        throw new NullPointerException("Null Point Catch in date picker");
                    } else {
                        expireDate = expireDatePicker.getEditor().getText();
                    }
                }

                //prepare value to update
                String product, condition;
                if (newProductNameTextField.getText().isEmpty()) {
                    product = productChooser.getSelectionModel().getSelectedItem();
                } else {
                    product = newProductNameTextField.getText().trim().replace("'", "");
                }

                if (productChooser.getSelectionModel().isEmpty()) {
                    condition = stockListTableView.getSelectionModel().getSelectedItem().getProduct();

                } else {
                    condition = productChooser.getSelectionModel().getSelectedItem();

                }

                String unit = unitChooser.getEditor().getText();
                String category = categoryChooser.getEditor().getText();
                String shelf = shelfTextField.getText();
                int quantity = Integer.parseInt(quantityTextField.getText());
                int wQuantity = Integer.parseInt(wQuantityTextField.getText());
                int reorder = Integer.parseInt(reorderLeverTextField.getText());
                String supplier = supplierChooser.getSelectionModel().getSelectedItem();
                float purchase = Float.parseFloat(purchaseTextField.getText());
                float sell = Float.parseFloat(sellTextField.getText());
                float wSell = Float.parseFloat(wSellTextField.getText());

                //check if quantity changed
                String q_status;
                int stockQuantity = StockCategoryData.getProductQuantity(condition);
                if (quantity != stockQuantity) {
                    if (StockCategoryData.getProductQuantityCheckStatus(condition).equals("no")) {
                        q_status = "yes";
                    } else q_status = "yes";
                } else {
                    if (StockCategoryData.getProductQuantityCheckStatus(condition).equals("no")) {
                        q_status = "no";
                    } else q_status = "yes";
                }

                //insert into database
                StockCategoryData.updateProductParameters(
                        condition,
                        product,
                        unit,
                        category,
                        shelf,
                        quantity,
                        wQuantity,
                        q_status,
                        reorder,
                        supplier,
                        purchase,
                        sell,
                        wSell,
                        expireDate
                );
                //clear current content
                stockListTableView.setItems(StockCategoryData.getStockList());

                //update quick reports
                productChooser.getSelectionModel().clearSelection();
                newProductNameTextField.clear();
                unitChooser.getEditor().clear();
                unitChooser.getSelectionModel().clearSelection();
                categoryChooser.getEditor().clear();
                categoryChooser.getSelectionModel().clearSelection();
                quantityTextField.clear();
                wQuantityTextField.clear();
                reorderLeverTextField.clear();
                supplierChooser.getSelectionModel().clearSelection();
                purchaseTextField.clear();
                sellTextField.clear();
                wSellTextField.clear();
                profitTextField.clear();
                timesTextField.clear();
                expireDatePicker.getEditor().clear();
                shelfTextField.clear();
                zeroStockTextField.clear();
                expireStockTextField.clear();
                reorderStockTextField.clear();
                totalStockTextField.clear();
                zeroStockTextField.setText(StockCategoryData.getQuickStockReport()[0]);
                reorderStockTextField.setText(StockCategoryData.getQuickStockReport()[1]);
                expireStockTextField.setText(StockCategoryData.getQuickStockReport()[2]);
                totalStockTextField.setText(StockCategoryData.getQuickStockReport()[3]);

                productChooser.requestFocus();
            }
        });

        //implement cancel button
        cancelStock.setOnMouseClicked(event -> {
            productChooser.getSelectionModel().clearSelection();
            newProductNameTextField.clear();
            unitChooser.getEditor().clear();
            supplierChooser.getSelectionModel().clearSelection();
            unitChooser.getSelectionModel().clearSelection();
            categoryChooser.getEditor().clear();
            categoryChooser.getSelectionModel().clearSelection();
            quantityTextField.clear();
            wQuantityTextField.clear();
            supplierChooser.getSelectionModel().clearSelection();
            reorderLeverTextField.clear();
            supplierChooser.getSelectionModel().clearSelection();
            purchaseTextField.clear();
            sellTextField.clear();
            wSellTextField.clear();
            profitTextField.clear();
            timesTextField.clear();
            expireDatePicker.getEditor().clear();
            shelfTextField.clear();
        });


        return updateStockUI;

    }

    private void addProductCategory() {
        //popup to set a new supplier
        popupAddCategory = new Popup();
        //control fields
        Label productNameLabel = new Label("PRODUCT CATEGORY");
        productNameLabel.setFont(new Font(14));

        TextField productCategory = new TextField();


        Button addProductButton = new Button("Add Category");
        Button cancelButton = new Button("Cancel");

        addProductButton.setStyle("-fx-base: #330033");
        cancelButton.setStyle("-fx-base: #330033");

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
        gridPane.add(productNameLabel, 0, 0);
        gridPane.add(productCategory, 0, 1);
        gridPane.add(hBox, 0, 2);

        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: #000000");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popupAddCategory.centerOnScreen();
        popupAddCategory.setAutoHide(true);
        popupAddCategory.setAutoFix(true);
        popupAddCategory.getContent().add(vBox);

        cancelButton.setOnAction(event -> {
            popupAddCategory.hide();
            productCategory.clear();
        });
        addProductButton.setOnAction(event -> {
            if (productCategory.getText().isEmpty()) productCategory.requestFocus();
            else {
                String category = productCategory.getText().trim();
                //hide the popup
                popupAddCategory.hide();
                //insert resources into table and create a new supplier table
                StockCategoryData.insertProductCategory(category);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successful");
                alert.setTitle("Category Added");
                alert.show();
                productCategory.clear();

                //update the product category table
                StockCategoryUI.categoryList.clear();
                StockCategoryData.getAllCategories();
            }
        });


    }

    private void removeProductCategory() {
        //popup to set a new supplier
        popupRemoveCategory = new Popup();
        //control fields
        Label productNameLabel = new Label("Categories");
        productNameLabel.setFont(new Font(14));

        ListView<String> categoriesListView = new ListView<>(categoryList);

        Button removeProductButton = new Button("Remove");
        Button cancelButton = new Button("Cancel");

        removeProductButton.setStyle("-fx-base: #330033");
        cancelButton.setStyle("-fx-base: #330033");

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
        gridPane.add(categoriesListView, 0, 1);
        gridPane.add(hBox, 0, 2);

        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: #000000");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popupRemoveCategory.centerOnScreen();
        popupRemoveCategory.setAutoHide(true);
        popupRemoveCategory.setAutoFix(true);
        popupRemoveCategory.getContent().add(vBox);

        cancelButton.setOnAction(event -> {
            categoryList.clear();
            popupRemoveCategory.hide();
        });

        removeProductButton.setOnAction(event1 -> {
            if (categoriesListView.getSelectionModel().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Select first to remove");
                alert.showAndWait();

            } else {
                String category = categoriesListView.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Confirm Delete Of :" + category);
                alert.showAndWait().filter(buttonType -> buttonType == ButtonType.OK).ifPresent(buttonType -> {
                    //delete the category
                    StockCategoryData.removeCategory(category);
                    //hide the popup
                    popupRemoveCategory.hide();

                    //update the product category table
                    StockCategoryUI.categoryList.clear();
                    StockCategoryData.getAllCategories();
                });

            }
        });
    }

    private void addProductUnit() {
        //popup to set a new supplier
        popupAddUnit = new Popup();
        //control fields
        Label productNameLabel = new Label("PRODUCT UNIT");
        productNameLabel.setFont(new Font(14));

        TextField productUnit = new TextField();


        Button addProductButton = new Button("Add Unit");
        Button cancelButton = new Button("Cancel");

        addProductButton.setStyle("-fx-base: #330033");
        cancelButton.setStyle("-fx-base: #330033");

        HBox hBox = new HBox();
        hBox.setSpacing(110);
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
        gridPane.add(productNameLabel, 0, 0);
        gridPane.add(productUnit, 0, 1);
        gridPane.add(hBox, 0, 2);

        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: #000000");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popupAddUnit.centerOnScreen();
        popupAddUnit.setAutoHide(true);
        popupAddUnit.setAutoFix(true);
        popupAddUnit.getContent().add(vBox);

        cancelButton.setOnAction(event -> {
            popupAddUnit.hide();
            productUnit.clear();
        });
        addProductButton.setOnAction(event -> {
            if (productUnit.getText().isEmpty()) productUnit.requestFocus();
            else {
                String unit = productUnit.getText().trim();
                //hide the popup
                popupAddUnit.hide();
                //insert resources into table and create a new supplier table
                StockCategoryData.insertProductUnit(unit);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successful");
                alert.setTitle("Unit Added");
                alert.show();
                productUnit.clear();

                //update the product category table
                StockCategoryUI.unitList.clear();
                StockCategoryData.getAllUnit();
            }
        });

    }

    private void removeProductUnit() {
        //popup to set a new supplier
        popupRemoveUnit = new Popup();
        //control fields
        Label productNameLabel = new Label("All Unit");
        productNameLabel.setFont(new Font(14));

        ListView<String> categoriesListView = new ListView<>(unitList);

        Button removeProductButton = new Button("Remove");
        Button cancelButton = new Button("Cancel");

        removeProductButton.setStyle("-fx-base: #330033");
        cancelButton.setStyle("-fx-base: #330033");

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
        gridPane.add(categoriesListView, 0, 1);
        gridPane.add(hBox, 0, 2);

        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: #000000");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popupRemoveUnit.centerOnScreen();
        popupRemoveUnit.setAutoHide(true);
        popupRemoveUnit.setAutoFix(true);
        popupRemoveUnit.getContent().add(vBox);

        cancelButton.setOnAction(event -> {
            unitList.clear();
            popupRemoveUnit.hide();
        });

        removeProductButton.setOnAction(event1 -> {
            if (categoriesListView.getSelectionModel().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Select first to remove");
                alert.showAndWait();

            } else {
                String category = categoriesListView.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Confirm Delete Of :" + category);
                alert.showAndWait().filter(buttonType -> buttonType == ButtonType.OK).ifPresent(buttonType -> {
                    //delete the unit
                    StockCategoryData.removeUnit(category);
                    //hide the popup
                    popupRemoveUnit.hide();

                    //update the product category table
                    StockCategoryUI.categoryList.clear();
                    StockCategoryData.getAllCategories();
                });

            }
        });
    }

    public static class StockList {
        private final SimpleStringProperty product;
        private final SimpleStringProperty unit;
        private final SimpleStringProperty category;
        private final SimpleIntegerProperty quantity;
        private final SimpleStringProperty q_update;
        private final SimpleIntegerProperty wquantity;
        private final SimpleIntegerProperty reorderLevel;
        private final SimpleStringProperty supplier;
        private final SimpleFloatProperty purchase;
        private final SimpleFloatProperty sell;
        private final SimpleFloatProperty wsell;
        private final SimpleFloatProperty profit;
        private final SimpleFloatProperty times;
        private final SimpleStringProperty expire;
        private final SimpleStringProperty shelf;

        public StockList(String product,
                         String unit,
                         String category,
                         int quantity,
                         String q_update,
                         int wquantity,
                         int reorder,
                         String supplier,
                         float purchase,
                         float sell,
                         float wsell,
                         float profit,
                         float times,
                         String expire,
                         String shelf) {
            this.product = new SimpleStringProperty(product);
            this.unit = new SimpleStringProperty(unit);
            this.category = new SimpleStringProperty(category);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.q_update = new SimpleStringProperty(q_update);
            this.wquantity = new SimpleIntegerProperty(wquantity);
            this.reorderLevel = new SimpleIntegerProperty(reorder);
            this.supplier = new SimpleStringProperty(supplier);
            this.purchase = new SimpleFloatProperty(purchase);
            this.sell = new SimpleFloatProperty(sell);
            this.wsell = new SimpleFloatProperty(wsell);
            this.profit = new SimpleFloatProperty(profit);
            this.times = new SimpleFloatProperty(times);
            this.expire = new SimpleStringProperty(expire);
            this.shelf = new SimpleStringProperty(shelf);
        }

        public String getProduct() {
            return product.get();
        }

        public void setProduct(String product) {
            this.product.set(product);
        }

        public String getUnit() {
            return unit.get();
        }

        public void setUnit(String unit) {
            this.unit.set(unit);
        }

        public String getCategory() {
            return category.get();
        }

        public void setCategory(String category) {
            this.category.set(category);
        }

        public int getQuantity() {
            return quantity.get();
        }

        public String getQ_update() {
            return q_update.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public int getReorderLevel() {
            return reorderLevel.get();
        }

        public void setReorderLevel(int reorderLevel) {
            this.reorderLevel.set(reorderLevel);
        }

        public String getSupplier() {
            return supplier.get();
        }

        public void setSupplier(String supplier) {
            this.supplier.set(supplier);
        }

        public float getPurchase() {
            return purchase.get();
        }

        public void setPurchase(float purchase) {
            this.purchase.set(purchase);
        }

        public float getSell() {
            return sell.get();
        }

        public void setSell(float sell) {
            this.sell.set(sell);
        }

        public float getProfit() {
            return profit.get();
        }

        public void setProfit(float profit) {
            this.profit.set(profit);
        }

        public float getTimes() {
            return times.get();
        }

        public void setTimes(float times) {
            this.times.set(times);
        }

        public String getExpire() {
            return expire.get();
        }

        public void setExpire(String expire) {
            this.expire.set(expire);
        }

        public String getShelf() {
            return shelf.get();
        }

        public void setShelf(String shelf) {
            this.shelf.set(shelf);
        }

        public float getWsell() {
            return wsell.get();
        }

        public int getWquantity() {
            return wquantity.get();
        }
    }

}
