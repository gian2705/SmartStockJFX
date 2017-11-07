package fahamu.UserInteface;

import fahamu.dataFactory.LogInStageData;
import fahamu.dataFactory.StockCategoryData;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.text.NumberFormat;


class MainStage {

    private SalesCategoryUI salesCategoryUI;
    private PurchaseCategoryUI purchaseCategoryUI;
    private StockCategoryUI stockCategoryUI;
    private ReportsCategoryUI reportsCategoryUI;

    private GridPane cashSaleUI;
    private GridPane rootAdmin;
    private GridPane rootUser;
    private GridPane newStockUI;
    private GridPane updateStockUI;
    private GridPane cashPurchaseUI;
    private GridPane creditPurchaseUI;
    private GridPane duePurchaseUI;
    private TabPane homeUIDashboard;

    static Stage stageAdmin;
    static Stage stageUser;

    private int firstRow = 0;


    static int ADMIN_UI = 1;
    static int CASHIER_UI = 2;

    MainStage(int service, SalesCategoryUI salesCategoryUI) {
        this.salesCategoryUI = salesCategoryUI;
        purchaseCategoryUI = new PurchaseCategoryUI();
        stockCategoryUI = new StockCategoryUI();
        reportsCategoryUI = new ReportsCategoryUI();
        this.newStockUI = stockCategoryUI.setNewStockUI();
        this.updateStockUI = stockCategoryUI.setUpdateStockUI();
        this.cashPurchaseUI = purchaseCategoryUI.setCashPurchaseUI();
        this.creditPurchaseUI = purchaseCategoryUI.setCreditPurchaseUI();
        this.duePurchaseUI = purchaseCategoryUI.setDueInvoiceUI();
        this.cashSaleUI = salesCategoryUI.setCashSaleUI();


        switch (service) {
            case 1: {
                //admin user interface
                stageAdmin = new Stage();
                //set parent root
                setRootNode();
                //set main navigation pane
                setNavigationNode();
                //set task ui, dashboard
                setHomeUIDashboard();
                //set menu bar of admin
                setMenuNode(ADMIN_UI);
                //cashier scene
                Scene myScene = new Scene(rootAdmin, 1250, 660);
                stageAdmin.setScene(myScene);

                stageAdmin.setOnCloseRequest(event -> {
                    event.consume();
                    if (salesCategoryUI.listBillTable.getItems().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Do you want to exit?");
                        alert.showAndWait()
                                .filter(buttonType -> buttonType == ButtonType.OK)
                                .ifPresent(buttonType -> {
                                    System.exit(0);
                                });
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Submit Product From Cart Before Exit\nOtherwise Remove Them ");
                        alert.show();
                    }

                });
                break;
            }
            case 2: {
                stageUser = new Stage();
                //set main task UI
                setCashierTaskUI();
                //set menu bar
                setMenuNode(CASHIER_UI);
                //cashier scene
                Scene userScene = new Scene(rootUser, 1050, 660);
                stageUser.setScene(userScene);

                stageUser.setOnCloseRequest(event -> {
                    event.consume();
                    if (salesCategoryUI.listBillTable.getItems().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Do you want to exit?");
                        alert.showAndWait()
                                .filter(buttonType -> buttonType == ButtonType.OK)
                                .ifPresent(buttonType -> {
                                    System.exit(0);
                                });
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Submit Product From Cart Before Exit\nOtherwise Remove Them ");
                        alert.show();
                    }

                });
                break;
            }
        }
    }

    private void setRootNode() {

        rootAdmin = new GridPane();
        ColumnConstraints column0 = new ColumnConstraints(220);
        ColumnConstraints column1 = new ColumnConstraints();
        RowConstraints row0 = new RowConstraints(20);
        RowConstraints row1 = new RowConstraints();
        rootAdmin.getColumnConstraints().addAll(column0, column1);
        rootAdmin.getRowConstraints().addAll(row0, row1);
        //root.setPadding(new Insets(1, 0, 0, 0));
        row1.setVgrow(Priority.ALWAYS);
        row1.setMaxHeight(Double.MAX_VALUE);
        column1.setHgrow(Priority.ALWAYS);

        //add menu bar to fill gap
        MenuBar currentTask = new MenuBar();
        currentTask.getMenus().add(new Menu(""));
        currentTask.setId("menu_blank");
        currentTask.setStyle("-fx-base: #000000");
        currentTask.setMinWidth(224);

        int firstColumn = 0;
        rootAdmin.add(currentTask, firstColumn, firstRow);

    }

    private void setNavigationNode() {
        //buttons
        Button nhifSales;
        Button wholeSale;
        Button cashSalesButton;
        Button creditSales;
        Button cashPurchase;
        Button creditPurchase;
        Button dueInvoice;
        Button purchaseReport;
        Button addSupplier;
        Button officeExpenditure;
        Button addExpensesCategories;
        Button removeCategoryButton;
        Button removeUnitButton;
        Button newStock;
        Button updateStock;
        Button stockReports;
        Button balanceSheet;
        Button profitAndLoss;
        Button balanceStock;
        Button salesReports;
        Button customers;
        Button dashboardButton;
        Button addCategoryButton;
        Button addUnitbutton;
        Button receipts;
        Button paidInvoice;
        Button addReceipt;
        Button addInvoice;
        Button removeReceipt;
        Button removeInvoice;
        Button removeSupplier;
        Button productReportButton;

        //content of titled pane dashboard
        dashboardButton = new Button("Discount Reports");
        dashboardButton.setStyle("-fx-base: #001a80");
        dashboardButton.setMinSize(200, 10);
        productReportButton = new Button("Product Reports");
        productReportButton.setStyle("-fx-base: #001a80");
        productReportButton.setMinSize(200, 10);
        salesReports = new Button("Sales Reports");
        salesReports.setStyle("-fx-base: #001a80");
        salesReports.setMinSize(200, 10);
        purchaseReport = new Button("Purchase Reports");
        purchaseReport.setStyle("-fx-base: #001a80");
        purchaseReport.setMinSize(200, 10);
        stockReports = new Button("Stock Reports");
        stockReports.setStyle("-fx-base: #001a80");
        stockReports.setMinSize(200, 10);
        balanceSheet = new Button("Balance Sheet");
        balanceSheet.setStyle("-fx-base: #001a80");
        balanceSheet.setMinSize(200, 10);
        profitAndLoss = new Button("Profit and Loss");
        profitAndLoss.setStyle("-fx-base: #001a80");
        profitAndLoss.setMinSize(200, 10);
        customers = new Button("WholeSales Customers");
        customers.setStyle("-fx-base: #001a80");
        customers.setMinSize(200, 10);
        dashboardButton.setMinSize(200, 10);
        TilePane dashboardTilePane = new TilePane();
        dashboardTilePane.setVgap(3);
        dashboardTilePane.setPrefTileWidth(200);
        dashboardTilePane.setPrefColumns(1);
        dashboardTilePane.getChildren().add(dashboardButton);
        dashboardTilePane.getChildren().add(productReportButton);
        dashboardTilePane.getChildren().add(salesReports);
        dashboardTilePane.getChildren().add(purchaseReport);
        dashboardTilePane.getChildren().add(stockReports);
        dashboardTilePane.getChildren().add(balanceSheet);
        dashboardTilePane.getChildren().add(profitAndLoss);
        dashboardTilePane.getChildren().add(customers);

        //content of titled pane sales
        cashSalesButton = new Button("Cash Sales");
        cashSalesButton.setStyle("-fx-base: #13a715");
        cashSalesButton.setMinSize(200, 10);
        creditSales = new Button("Night Sales");
        creditSales.setStyle("-fx-base: #13a715");
        creditSales.setMinSize(200, 10);
        nhifSales = new Button("NHIF  Sales");
        nhifSales.setMinSize(200, 10);
        nhifSales.setStyle("-fx-base: #13a715");
        wholeSale = new Button("WholeSales ");
        wholeSale.setStyle("-fx-base: #13a715");
        wholeSale.setMinSize(200, 10);

        TilePane saleOptionsTile = new TilePane();
        saleOptionsTile.setVgap(3);
        saleOptionsTile.setPrefTileWidth(200);
        saleOptionsTile.setPrefColumns(1);
        saleOptionsTile.getChildren().add(cashSalesButton);
        saleOptionsTile.getChildren().add(creditSales);
        saleOptionsTile.getChildren().add(nhifSales);
        saleOptionsTile.getChildren().add(wholeSale);

        //content of titled pane purchase
        cashPurchase = new Button("Cash Purchases");
        cashPurchase.setStyle("-fx-base: #ea2121");
        cashPurchase.setMinSize(200, 10);
        creditPurchase = new Button("Credit Purchases");
        creditPurchase.setStyle("-fx-base: #ea2121");
        creditPurchase.setMinSize(200, 10);
        dueInvoice = new Button("Due Invoice");
        dueInvoice.setStyle("-fx-base: #ea2121");
        dueInvoice.setMinSize(200, 10);
        addSupplier = new Button("Add Supplier");
        addSupplier.setStyle("-fx-base: #ea2121");
        addSupplier.setMinSize(200, 10);
        removeSupplier = new Button("Remove Supplier");
        removeSupplier.setStyle("-fx-base: #ea2121");
        removeSupplier.setMinSize(200, 10);
        receipts = new Button("All Receipts");
        receipts.setStyle("-fx-base: #ea2121");
        receipts.setMinSize(200, 10);
        paidInvoice = new Button("Paid Invoice");
        paidInvoice.setStyle("-fx-base: #ea2121");
        paidInvoice.setMinSize(200, 10);
        addInvoice = new Button("Add Invoice");
        addInvoice.setStyle("-fx-base: #ea2121");
        addInvoice.setMinSize(200, 10);
        addReceipt = new Button("Add Receipt");
        addReceipt.setStyle("-fx-base: #ea2121");
        addReceipt.setMinSize(200, 10);
        removeReceipt = new Button("Remove Receipt");
        removeReceipt.setStyle("-fx-base: #ea2121");
        removeReceipt.setMinSize(200, 10);
        removeInvoice = new Button("Remove Invoice");
        removeInvoice.setStyle("-fx-base: #ea2121");
        removeInvoice.setMinSize(200, 10);
        TilePane purchaseOptionsTile = new TilePane();
        purchaseOptionsTile.setVgap(3);
        purchaseOptionsTile.setPrefTileWidth(200);
        purchaseOptionsTile.setPrefColumns(1);
        purchaseOptionsTile.getChildren().add(cashPurchase);
        purchaseOptionsTile.getChildren().add(creditPurchase);
        purchaseOptionsTile.getChildren().add(addSupplier);
        purchaseOptionsTile.getChildren().add(removeSupplier);
        purchaseOptionsTile.getChildren().add(receipts);
        purchaseOptionsTile.getChildren().add(addReceipt);
        purchaseOptionsTile.getChildren().add(removeReceipt);
        purchaseOptionsTile.getChildren().add(paidInvoice);
        purchaseOptionsTile.getChildren().add(dueInvoice);
        purchaseOptionsTile.getChildren().add(addInvoice);
        purchaseOptionsTile.getChildren().add(removeInvoice);


        //content of titled pane stock
        newStock = new Button("New Stock");
        newStock.setStyle("-fx-base: #330033");
        newStock.setMinSize(200, 10);
        updateStock = new Button("Update Stock");
        updateStock.setStyle("-fx-base: #330033");
        updateStock.setMinSize(200, 10);
        balanceStock = new Button("Balance Stock");
        balanceStock.setStyle("-fx-base: #330033");
        addCategoryButton = new Button("Add Category");
        addCategoryButton.setMinSize(200, 10);
        addCategoryButton.setStyle("-fx-base: #330033");
        addUnitbutton = new Button("Add Unit");
        addUnitbutton.setMinSize(200, 10);
        addUnitbutton.setStyle("-fx-base: #330033");
        balanceStock.setMinSize(200, 10);
        removeCategoryButton = new Button("Remove Category");
        removeCategoryButton.setMinSize(200, 10);
        removeCategoryButton.setStyle("-fx-base: #330033");
        removeUnitButton = new Button("Remove Unit");
        removeUnitButton.setMinSize(200, 10);
        removeUnitButton.setStyle("-fx-base: #330033");
        TilePane stockOptionsTile = new TilePane();
        stockOptionsTile.setVgap(3);
        stockOptionsTile.setPrefTileWidth(200);
        stockOptionsTile.setPrefColumns(1);
        stockOptionsTile.getChildren().add(newStock);
        stockOptionsTile.getChildren().add(updateStock);
        stockOptionsTile.getChildren().add(addCategoryButton);
        stockOptionsTile.getChildren().add(removeCategoryButton);
        stockOptionsTile.getChildren().add(addUnitbutton);
        stockOptionsTile.getChildren().add(removeUnitButton);

        //content of titled pane expenses
        officeExpenditure = new Button("New Expenditure");
        officeExpenditure.setStyle("-fx-base: #f3622d");
        officeExpenditure.setMinSize(200, 10);
        addExpensesCategories = new Button("Add Expenditure Categories");
        addExpensesCategories.setStyle("-fx-base: #f3622d");
        addExpensesCategories.setMinSize(200, 10);
        TilePane expensesOptionsTile = new TilePane();
        expensesOptionsTile.setVgap(3);
        expensesOptionsTile.setPrefTileWidth(200);
        expensesOptionsTile.setPrefColumns(1);
        expensesOptionsTile.getChildren().add(officeExpenditure);
        expensesOptionsTile.getChildren().add(addExpensesCategories);


        //content of titled pane setting

        TilePane settingOptionsTile = new TilePane();
        settingOptionsTile.setVgap(3);
        settingOptionsTile.setPrefTileWidth(200);
        settingOptionsTile.setPrefColumns(1);


        //set Accordion
        TitledPane dashboardNav = new TitledPane("DASHBOARD", dashboardTilePane);
        TitledPane salesNav = new TitledPane("SALES    CATEGORY", saleOptionsTile);
        TitledPane purchaseNav = new TitledPane("PURCHASE  CATEGORY", purchaseOptionsTile);
        TitledPane stockNav = new TitledPane("STOCK   CATEGORY", stockOptionsTile);
        TitledPane expensesNav = new TitledPane("EXPENDITURE CATEGORY", expensesOptionsTile);
        TitledPane settingsNav = new TitledPane("SETTINGS CATEGORY", settingOptionsTile);

        Accordion accordion = new Accordion();
        accordion.setId("navigation");
        accordion.setPrefHeight(430);
        accordion.setStyle("-fx-base: #000000");
        accordion.setExpandedPane(dashboardNav);
        accordion.getPanes().addAll(
                dashboardNav,
                salesNav,
                purchaseNav,
                stockNav,
                expensesNav,
                settingsNav);

        dashboardNav.setOnMouseClicked(event -> {

            int index = rootAdmin.getChildren().size();
            for (int i = 0; i < index; i++) {
                Node node = rootAdmin.getChildren().get(i);
                if (node.toString().startsWith("Grid")) {
                    rootAdmin.getChildren().remove(i);
                    rootAdmin.add(homeUIDashboard, 1, 1);
                }
            }

        });
        salesNav.setOnMouseClicked(event -> {
            openCashSaleCategory();
        });
        stockNav.setOnMouseClicked(event -> {
            //open new stock section
            //change only once the task UI
            int index = rootAdmin.getChildren().size();

            for (int i = 0; i < index; i++) {
                Node node = rootAdmin.getChildren().get(i);
                //avoid repetition of removing the same task UI
                if (node.getId().equals("new_stock")) {

                    break;

                } else {
                    //check if its home page
                    if (node.toString().startsWith("TabPan")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(newStockUI, 1, 1);
                        break;
                        //check if its other pane
                    } else if (node.toString().startsWith("Grid")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(newStockUI, 1, 1);
                        break;

                    }
                }
            }
        });
        purchaseNav.setOnMouseClicked(event -> {
            //change only once the task UI
            int index = rootAdmin.getChildren().size();
            for (int i = 0; i < index; i++) {
                Node node = rootAdmin.getChildren().get(i);
                //avoid repetition of removing the same task UI
                if (node.getId().equals("cash_purchase")) {
                    //break the loop
                    break;
                } else {
                    //check if its home page
                    if (node.toString().startsWith("TabPan")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(cashPurchaseUI, 1, 1);
                        break;

                        //check if its other pane
                    } else if (node.toString().startsWith("Grid")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(cashPurchaseUI, 1, 1);
                        break;

                    }
                }
            }
        });
        expensesNav.setOnMouseClicked(event -> {
            //change only once the task UI
            int index = rootAdmin.getChildren().size();
            for (int i = 0; i < index; i++) {
                Node node = rootAdmin.getChildren().get(i);
                //avoid repetition of removing the same task UI
                if (node.getId().equals("new_expenditure")) {
                    //break the loop
                    break;
                } else {
                    //check if its home page
                    if (node.toString().startsWith("TabPan")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(ExpenditureCategoryUI.newExpenditureUI, 1, 1);
                        break;

                        //check if its other pane
                    } else if (node.toString().startsWith("Grid")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(ExpenditureCategoryUI.newExpenditureUI, 1, 1);
                        break;

                    }
                }
            }
        });

        dashboardButton.setOnAction(event -> {
            homeUIDashboard.getSelectionModel().select(0);
        });
        productReportButton.setOnAction(event -> {
            //this boolean its true if the searched tab with given id is not available
            boolean notAvailable = true;
            ObservableList<Tab> tabs = homeUIDashboard.getTabs();
            for (Tab tab : tabs) {
                try {
                    if (tab.getId().equals("product_report")) {
                        homeUIDashboard.getSelectionModel().select(tab);
                        notAvailable = false;
                        break;
                    }
                } catch (NullPointerException ignore) {
                }
            }

            if (notAvailable) {
                Tab tab1 = new Tab("Product Report");
                tab1.setId("product_report");

                GridPane gridPane = reportsCategoryUI.setDashboardSalesReportUI();
                gridPane.add(reportsCategoryUI.navigationLeftPaneProductReport(), 0, 0);
                gridPane.add(reportsCategoryUI
                                .mainTaskUI(
                                        reportsCategoryUI.purchaseCreditHistoryTable,
                                        reportsCategoryUI.purchaseCashHistoryTable,
                                        reportsCategoryUI.sellHistoryTable,
                                        reportsCategoryUI.sellPurchaseChart, 3),
                        1, 0);

                tab1.setContent(gridPane);
                homeUIDashboard.getTabs().add(tab1);
                homeUIDashboard.getSelectionModel().select(tab1);

                //set maximum number of spinner
                reportsCategoryUI.spinner.getValueFactory().setValue(reportsCategoryUI.multiples + 1);
            }

        });
        salesReports.setOnAction(event -> {

            //this boolean its true if the searched tab with given id is not available
            boolean notAvailable = true;
            ObservableList<Tab> tabs = homeUIDashboard.getTabs();
            for (Tab tab : tabs) {
                try {
                    if (tab.getId().equals("sales_report")) {
                        homeUIDashboard.getSelectionModel().select(tab);
                        notAvailable = false;
                        break;
                    }
                } catch (NullPointerException ignore) {
                }
            }

            if (notAvailable) {
                Tab tab1 = new Tab("Sales Report");
                tab1.setId("sales_report");

                GridPane gridPane = reportsCategoryUI.setDashboardSalesReportUI();

                gridPane.add(reportsCategoryUI.navigationLeftPaneSalesReports(), 0, 0);

                gridPane.add(reportsCategoryUI.setSalesReportGraph(), 1, 0);

                tab1.setContent(gridPane);
                homeUIDashboard.getTabs().add(tab1);
                homeUIDashboard.getSelectionModel().select(tab1);

            }
        });

        //call cash sale activity
        cashSalesButton.setOnAction(event -> {
            openCashSaleCategory();
        });
        //call new stock activity
        newStock.setOnAction(event -> {
            //change only once the task UI
            int index = rootAdmin.getChildren().size();

            for (int i = 0; i < index; i++) {
                Node node = rootAdmin.getChildren().get(i);
                //avoid repetition of removing the same task UI
                if (node.getId().equals("new_stock")) {

                    break;

                } else {
                    //check if its home page
                    if (node.toString().startsWith("TabPan")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(newStockUI, 1, 1);
                        break;
                        //check if its other pane
                    } else if (node.toString().startsWith("Grid")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(newStockUI, 1, 1);
                        break;

                    }
                }
            }
        });
        //call update stock activity
        updateStock.setOnAction(event -> {
            //change only once the task UI
            int index = rootAdmin.getChildren().size();
            for (int i = 0; i < index; i++) {
                Node node = rootAdmin.getChildren().get(i);
                //avoid repetition of removing the same task UI
                if (node.getId().equals("update_stock")) {

                    break;

                } else {
                    //check if its home page
                    if (node.toString().startsWith("TabPan")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(updateStockUI, 1, 1);
                        break;
                        //check if its other pane
                    } else if (node.toString().startsWith("Grid")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(updateStockUI, 1, 1);
                        break;

                    }
                }
            }
        });
        //implementing purchase category
        cashPurchase.setOnAction(event -> {
            //change only once the task UI
            int index = rootAdmin.getChildren().size();
            for (int i = 0; i < index; i++) {
                Node node = rootAdmin.getChildren().get(i);
                //avoid repetition of removing the same task UI
                if (node.getId().equals("cash_purchase")) {
                    //break the loop
                    break;
                } else {
                    //check if its home page
                    if (node.toString().startsWith("TabPan")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(cashPurchaseUI, 1, 1);
                        break;

                        //check if its other pane
                    } else if (node.toString().startsWith("Grid")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(cashPurchaseUI, 1, 1);
                        break;

                    }
                }
            }
        });
        creditPurchase.setOnAction(event -> {
            //change only once the task UI
            int index = rootAdmin.getChildren().size();
            for (int i = 0; i < index; i++) {
                Node node = rootAdmin.getChildren().get(i);
                //avoid repetition of removing the same task UI
                if (node.getId().equals("credit_purchase")) {
                    //break the loop
                    break;
                } else {
                    //check if its home page
                    if (node.toString().startsWith("TabPan")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(creditPurchaseUI, 1, 1);
                        break;

                        //check if its other pane
                    } else if (node.toString().startsWith("Grid")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(creditPurchaseUI, 1, 1);
                        break;

                    }
                }
            }
        });
        dueInvoice.setOnAction(event -> {
            //change only once the task UI
            int index = rootAdmin.getChildren().size();
            for (int i = 0; i < index; i++) {
                Node node = rootAdmin.getChildren().get(i);
                //avoid repetition of removing the same task UI
                if (node.getId().equals("due_invoice")) {
                    //break the loop
                    break;
                } else {
                    //check if its home page
                    if (node.toString().startsWith("TabPan")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(duePurchaseUI, 1, 1);
                        break;

                        //check if its other pane
                    } else if (node.toString().startsWith("Grid")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(duePurchaseUI, 1, 1);
                        break;

                    }
                }
            }
        });

        addSupplier.setOnAction(event -> PurchaseCategoryUI.popup.show(stageAdmin));
        removeSupplier.setOnAction(event -> PurchaseCategoryUI.popupRemoveSupplier.show(stageAdmin));
        //implement expenditure category buttons
        addExpensesCategories.setOnAction(event -> ExpenditureCategoryUI.popup.show(stageAdmin));
        officeExpenditure.setOnAction(event -> {
            //change only once the task UI
            int index = rootAdmin.getChildren().size();
            for (int i = 0; i < index; i++) {
                Node node = rootAdmin.getChildren().get(i);
                //avoid repetition of removing the same task UI
                if (node.getId().equals("new_expenditure")) {
                    //break the loop
                    break;
                } else {
                    //check if its home page
                    if (node.toString().startsWith("TabPan")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(ExpenditureCategoryUI.newExpenditureUI, 1, 1);
                        break;

                        //check if its other pane
                    } else if (node.toString().startsWith("Grid")) {
                        rootAdmin.getChildren().remove(i);
                        rootAdmin.add(ExpenditureCategoryUI.newExpenditureUI, 1, 1);
                        break;

                    }
                }
            }
        });

        addCategoryButton.setOnAction(event -> StockCategoryUI.popupAddCategory.show(stageAdmin));
        removeCategoryButton.setOnAction(event -> {
            StockCategoryUI.categoryList.clear();
            StockCategoryData.getAllCategories();
            StockCategoryUI.popupRemoveCategory.show(stageAdmin);
        });

        addUnitbutton.setOnAction(event -> StockCategoryUI.popupAddUnit.show(stageAdmin));
        removeUnitButton.setOnAction(event -> {
            StockCategoryUI.unitList.clear();
            StockCategoryData.getAllUnit();
            StockCategoryUI.popupRemoveUnit.show(stageAdmin);
        });

        addReceipt.setOnAction(event -> PurchaseCategoryUI.popupAddReceipt.show(stageAdmin));
        removeReceipt.setOnAction(event -> PurchaseCategoryUI.popupRemoveReceipt.show(stageAdmin));

        addInvoice.setOnAction(event -> PurchaseCategoryUI.popupAddInvoice.show(stageAdmin));
        removeInvoice.setOnAction(event -> PurchaseCategoryUI.popupRemoveInvoice.show(stageAdmin));

        rootAdmin.add(accordion, 0, 1);
    }


    private void setHomeUIDashboard() {
        //set the home task UI
        homeUIDashboard = new TabPane();
        homeUIDashboard.setId("dashboard");
        Tab tab = new Tab("Q.Sale Reports");

        tab.setClosable(false);

        //to be implemented
        tab.setContent(reportsCategoryUI.dashboard);
        homeUIDashboard.setPadding(new Insets(8, 8, 8, 8));
        homeUIDashboard.getTabs().addAll(tab);

        rootAdmin.add(homeUIDashboard, 1, 1);
    }

    private void setMenuNode(int type) {
        switch (type) {
            case 1: {
                MenuBar menuBar = new MenuBar();
                menuBar.setId("menu_bar");
                Menu fileMenu = new Menu("File");
                Menu viewMenu = new Menu("View");
                Menu accountMenu = new Menu("Account");
                Menu backUpMenu = new Menu("Data");

                accountMenu.setMnemonicParsing(true);

                MenuItem fullScreenViewMenuItem = new MenuItem("Enter Full Screen");
                MenuItem allSellViewMenuItem = new MenuItem("V-A-S");
                MenuItem createUserMenuItem = new MenuItem("Create user");
                MenuItem removeUserMenuItem = new MenuItem("Remove User");
                MenuItem changePasswordMenuItem = new MenuItem("Change Password");
                MenuItem logoutMenuItem = new MenuItem("Logout");
                MenuItem backUpMenuItem = new MenuItem("Back Up");
                MenuItem restoreMenuItem = new MenuItem("Restore");

                viewMenu.getItems().addAll(fullScreenViewMenuItem, allSellViewMenuItem);
                backUpMenu.getItems().addAll(backUpMenuItem, restoreMenuItem);
                accountMenu.getItems().addAll(
                        createUserMenuItem,
                        removeUserMenuItem,
                        changePasswordMenuItem,
                        logoutMenuItem);
                menuBar.setStyle("-fx-base: #000000");
                menuBar.getMenus().addAll(fileMenu, viewMenu, accountMenu, backUpMenu);

                //refresh stock list table
                logoutMenuItem.setOnAction(event -> {
                    //change stage to login stage
                    if (salesCategoryUI.listBillTable.getItems().isEmpty()) {

                        salesCategoryUI.tableViewSalesOfDay.getItems().clear();
                        salesCategoryUI.tableViewSaleTraOfDay.getItems().clear();

                        stageAdmin.hide();

                        LogInStage.stageLogIn.show();

                    } else {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("You have unconfirmed bill\nSubmit it or delete contents");
                        alert.show();

                    }
                });

                fullScreenViewMenuItem.setOnAction(event -> {
                    if (stageAdmin.isFullScreen()) {
                        stageAdmin.setFullScreen(false);
                        fullScreenViewMenuItem.setText("Enter FullScreen");
                    } else {
                        stageAdmin.setFullScreen(true);
                        fullScreenViewMenuItem.setText("Exit Full Screen");
                    }
                });

                //create user menu implementation
                createUserMenuItem.setOnAction(event -> {

                    Label usernameLabel = new Label("Username");
                    usernameLabel.setFont(new Font(14));

                    TextField usernameTextField = new TextField();

                    Button createUserButton = new Button("CREATE USER");
                    RadioButton radioButton = new RadioButton("Is Admin");
                    createUserButton.setStyle("-fx-base: green");

                    Circle circle = new Circle(55);
                    circle.setStyle("-fx-background-color: #000000");
                    Arc arc = new Arc();
                    arc.setStartAngle(0f);
                    arc.setLength(180f);
                    arc.setType(ArcType.ROUND);
                    arc.setRadiusX(15);
                    arc.setRadiusY(15);

                    VBox profileIconPane = new VBox();
                    profileIconPane.setAlignment(Pos.TOP_CENTER);
                    profileIconPane.setPadding(new Insets(10, 10, 10, 10));
                    profileIconPane.getChildren().addAll(circle, arc);

                    VBox inputControl = new VBox();
                    inputControl.setSpacing(10);
                    inputControl.setPadding(new Insets(10, 10, 10, 10));
                    inputControl.setAlignment(Pos.CENTER);
                    inputControl.setStyle("-fx-background-color: white");

                    VBox ribbonVBox = new VBox();
                    ribbonVBox.setPadding(new Insets(4, 4, 4, 4));
                    ribbonVBox.setStyle("-fx-background-color: black");

                    inputControl.getChildren().addAll(
                            profileIconPane,
                            usernameLabel,
                            usernameTextField,
                            radioButton,
                            createUserButton);

                    ribbonVBox.getChildren().add(inputControl);

                    Popup popup = new Popup();
                    popup.getContent().addAll(ribbonVBox);
                    popup.setAutoHide(true);
                    popup.show(stageAdmin);

                    createUserButton.setOnAction(event1 -> {
                        if (usernameTextField.getText().isEmpty()) {
                            usernameTextField.requestFocus();
                        } else {
                            String username = usernameTextField.getText();
                            String password;
                            String userType;
                            if (radioButton.isSelected()) {
                                userType = "admin";
                                password = "admin";
                            } else {
                                userType = "cashier";
                                password = username.toLowerCase().concat("1234");
                            }
                            //add user to the database
                            LogInStageData.addUser(username, password, userType);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("User Successful Added\nPassword Is :" + password);
                            alert.show();
                            //disable radio button
                            radioButton.setSelected(false);
                        }
                    });

                });

                //change the password
                changePasswordMenuItem.setOnAction(event -> {
                    setChangePasswordUI().show(stageAdmin);

                });
                //implement remove user
                removeUserMenuItem.setOnAction(event -> {

                    Button removeUserButton = new Button("REMOVE USER");
                    removeUserButton.setVisible(false);
                    removeUserButton.setStyle("-fx-base: green");

                    ListView<String> listView = new ListView<>();
                    listView.setStyle("-fx-base: gray");
                    //get all users
                    listView.setItems(LogInStageData.getAllUsers(LogInStage.currentUserName));

                    VBox listUserListView = new VBox();
                    listUserListView.setAlignment(Pos.TOP_CENTER);
                    listUserListView.setPadding(new Insets(10, 10, 10, 10));
                    listUserListView.getChildren().add(listView);

                    VBox inputControl = new VBox();
                    inputControl.setSpacing(10);
                    inputControl.setPadding(new Insets(10, 10, 10, 10));
                    inputControl.setAlignment(Pos.CENTER);
                    inputControl.setStyle("-fx-background-color: white");

                    VBox ribbonVBox = new VBox();
                    ribbonVBox.setPadding(new Insets(4, 4, 4, 4));
                    ribbonVBox.setStyle("-fx-background-color: black");

                    inputControl.getChildren().addAll(listView, removeUserButton);
                    ribbonVBox.getChildren().add(inputControl);

                    Popup popup = new Popup();
                    popup.getContent().addAll(ribbonVBox);
                    popup.setAutoHide(true);
                    popup.show(stageAdmin);

                    listView.setOnMouseClicked(event1 -> {
                        removeUserButton.setVisible(true);

                    });

                    //remove user
                    removeUserButton.setOnAction(event1 -> {
                        if (listView.getSelectionModel().isEmpty()) listView.requestFocus();
                        else {
                            String user = listView.getSelectionModel().getSelectedItem();
                            LogInStageData.removeUser(user);
                            //update list view
                            listView.getItems().clear();
                            listView.setItems(LogInStageData.getAllUsers(LogInStage.currentUserName));
                        }
                    });

                });

                allSellViewMenuItem.setOnAction(event ->
                        salesCategoryUI.tabPane.getTabs().add(salesCategoryUI.salesTableViewTab));


                int secondColumn = 1;
                rootAdmin.add(menuBar, secondColumn, firstRow);
                break;
            }
            case 2: {
                MenuBar menuBar = new MenuBar();
                menuBar.setId("menu_bar");
                Menu fileMenu = new Menu("File");
                Menu viewMenu = new Menu("View");
                Menu stockMenu = new Menu("Account");
                MenuItem fullScreenViewMenuItem = new MenuItem("Enter Full Screen");
                MenuItem allSellViewMenuItem = new MenuItem("V-A-S");
                MenuItem changePasswordMenuItem = new MenuItem("Change Password");
                MenuItem logoutMenuItem = new MenuItem("Logout");

                viewMenu.getItems().addAll(fullScreenViewMenuItem, allSellViewMenuItem);
                stockMenu.getItems().addAll(changePasswordMenuItem, logoutMenuItem);
                menuBar.setStyle("-fx-base: #000000");
                menuBar.getMenus().addAll(fileMenu, viewMenu, stockMenu);

                //refresh stock list table
                logoutMenuItem.setOnAction(event -> {
                    //change stage to login stage
                    if (salesCategoryUI.listBillTable.getItems().isEmpty()) {
                        stageUser.hide();

                        salesCategoryUI.tableViewSalesOfDay.getItems().clear();
                        salesCategoryUI.tableViewSaleTraOfDay.getItems().clear();

                        LogInStage.stageLogIn.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("You have unconfirmed bill\nSubmit it or delete contents");
                        alert.show();
                    }
                });

                changePasswordMenuItem.setOnAction(event -> {
                    setChangePasswordUI().show(stageUser);
                });

                fullScreenViewMenuItem.setOnAction(event -> {
                    if (stageUser.isFullScreen()) {
                        stageUser.setFullScreen(false);
                        fullScreenViewMenuItem.setText("Enter FullScreen");
                    } else {
                        stageUser.setFullScreen(true);
                        fullScreenViewMenuItem.setText("Exit Full Screen");
                    }
                });

                allSellViewMenuItem.setOnAction(event ->
                        salesCategoryUI.tabPane.getTabs().add(salesCategoryUI.salesTableViewTab));

                rootUser.add(menuBar, 0, firstRow);
                break;
            }
        }
    }

    private Popup setChangePasswordUI() {
        Label titleLabel = new Label("CHANGE PASSWORD");
        Label newPasswordLabel = new Label("New Password");
        Label confirmPasswordLabel = new Label("Confirm Password");

        titleLabel.setFont(new Font(20));
        newPasswordLabel.setPrefWidth(120);
        confirmPasswordLabel.setPrefWidth(120);

        PasswordField newPasswordTextField = new PasswordField();
        PasswordField confirmPasswordTextField = new PasswordField();

        Button submitButton = new Button("change");

        HBox newPassword = new HBox();
        HBox confirmPassword = new HBox();

        newPassword.getChildren().addAll(newPasswordLabel, newPasswordTextField);
        confirmPassword.getChildren().addAll(confirmPasswordLabel, confirmPasswordTextField);

        VBox changePasswordMainPane = new VBox();
        changePasswordMainPane.setPadding(new Insets(6, 6, 6, 6));
        changePasswordMainPane.setStyle("-fx-background-color: white");
        changePasswordMainPane.setSpacing(10);
        changePasswordMainPane.setAlignment(Pos.TOP_CENTER);
        changePasswordMainPane.getChildren().addAll(
                titleLabel,
                newPassword,
                confirmPassword,
                submitButton
        );

        VBox ribbonVBox = new VBox();
        ribbonVBox.setStyle("-fx-background-color: black");
        ribbonVBox.setPadding(new Insets(4, 4, 4, 4));
        ribbonVBox.getChildren().add(changePasswordMainPane);

        Popup popupChangePassword = new Popup();
        popupChangePassword.setAutoFix(true);
        popupChangePassword.setAutoHide(true);
        popupChangePassword.getContent().add(ribbonVBox);

        newPasswordTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (newPasswordTextField.getText().isEmpty()) newPasswordTextField.requestFocus();
                    else confirmPasswordTextField.requestFocus();
                }
            }
        });
        confirmPasswordTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    if (confirmPasswordTextField.getText().isEmpty()) confirmPasswordTextField.requestFocus();
                    else {
                        //implementation of change the password
                        if (newPasswordTextField.getText().equals(confirmPasswordTextField.getText())) {
                            LogInStageData
                                    .updateUserInfo(LogInStage.currentUserName, confirmPasswordTextField.getText());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Password Changed");
                            alert.show();
                            newPasswordTextField.clear();
                            confirmPasswordTextField.clear();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Password don't match");
                            alert.show();
                        }
                    }
                }
            }
        });

        confirmPassword.setOnKeyReleased(event -> {
            String newP = newPasswordTextField.getText();
            String confirmP = confirmPasswordTextField.getText();
            if (newP.equals(confirmP)) {
                newPasswordTextField.setStyle("-fx-base: green");
                confirmPasswordTextField.setStyle("-fx-base: green");
            } else {
                newPasswordTextField.setStyle("-fx-base: red");
                confirmPasswordTextField.setStyle("-fx-base: red");
            }
        });

        submitButton.setOnAction(event -> {
            if (newPasswordTextField.getText().equals(confirmPasswordTextField.getText())) {
                LogInStageData
                        .updateUserInfo(LogInStage.currentUserName, confirmPasswordTextField.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Password Changed");
                alert.show();
                newPasswordTextField.clear();
                confirmPasswordTextField.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Password don't match");
                alert.show();
            }
        });


        return popupChangePassword;

    }

    private void setCashierTaskUI() {

        rootUser = new GridPane();
        ColumnConstraints column0 = new ColumnConstraints();
        RowConstraints row0 = new RowConstraints(20);
        RowConstraints row1 = new RowConstraints();
        rootUser.getColumnConstraints().addAll(column0);
        rootUser.getRowConstraints().addAll(row0, row1);

        row1.setVgrow(Priority.ALWAYS);
        row1.setMaxHeight(Double.MAX_VALUE);
        column0.setHgrow(Priority.ALWAYS);
        rootUser.add(cashSaleUI, 0, 1);

    }

    private void openCashSaleCategory() {
        //change only once the task UI
        int index = rootAdmin.getChildren().size();
        for (int i = 0; i < index; i++) {
            Node node = rootAdmin.getChildren().get(i);
            //avoid repetition of removing the same task UI
            if (node.getId().equals("cash_sale")) {

                //break the loop to avoid repetition
                //some keyboard shortcut
                salesCategoryUI.submitCashBill.getScene().getAccelerators().put(
                        new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN),
                        () -> {
                            salesCategoryUI.setSubmitCashBill();
                        }
                );
                salesCategoryUI.traCheckButton.getScene().getAccelerators().put(
                        new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN),
                        () -> {
                            if (salesCategoryUI.traCheckButton.isSelected()) {
                                salesCategoryUI.traCheckButton.setSelected(false);
                                salesCategoryUI.listBillTable.setStyle("-fx-base: #efeded");
                            } else {
                                salesCategoryUI.traCheckButton.setSelected(true);
                                salesCategoryUI.listBillTable.setStyle("-fx-base: #00ff00");
                            }
                        }
                );
                salesCategoryUI.wholeSaleCheckBox.getScene().getAccelerators().put(
                        new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN),
                        () -> {
                            if (salesCategoryUI.wholeSaleCheckBox.isSelected()) {
                                salesCategoryUI.wholeSaleCheckBox.setSelected(false);
                                float price = StockCategoryData.sellPrice;
                                try {
                                    float product = (price * (Integer.parseInt(salesCategoryUI.
                                            quantityTextField.getText())))
                                            - Integer.parseInt(salesCategoryUI.discountTextField.getText());
                                    //format number for accountant
                                    String value = NumberFormat.getInstance().format(product);
                                    salesCategoryUI.amountTextField.setText(value);
                                } catch (Throwable throwable) {
                                    salesCategoryUI.amountTextField.setText(String.valueOf(0));
                                }
                            } else {
                                salesCategoryUI.wholeSaleCheckBox.setSelected(true);
                                if (salesCategoryUI.quantityTextField.getText().isEmpty()) {
                                    salesCategoryUI.amountTextField.setText(String.valueOf(0));
                                } else {
                                    int quant = Integer.parseInt(salesCategoryUI.quantityTextField.getText());
                                    float wPrice = StockCategoryData.wSellPrice;
                                    try {
                                        float total = (wPrice * quant)
                                                - Integer.parseInt(salesCategoryUI.discountTextField.getText());
                                        salesCategoryUI.amountTextField
                                                .setText(NumberFormat.getInstance().format(total));

                                    } catch (Throwable q) {
                                        salesCategoryUI.amountTextField.setText(String.valueOf(0));
                                    }
                                }
                            }
                        }
                );
                break;
            } else {
                //check if its home page
                if (node.toString().startsWith("TabPan")) {
                    rootAdmin.getChildren().remove(i);
                    rootAdmin.add(cashSaleUI, 1, 1);

                    //some keyboard shortcut
                    salesCategoryUI.submitCashBill.getScene().getAccelerators().put(
                            new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN),
                            () -> {
                                salesCategoryUI.setSubmitCashBill();
                            }
                    );
                    salesCategoryUI.traCheckButton.getScene().getAccelerators().put(
                            new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN),
                            () -> {
                                if (salesCategoryUI.traCheckButton.isSelected()) {
                                    salesCategoryUI.traCheckButton.setSelected(false);
                                    salesCategoryUI.listBillTable.setStyle("-fx-base: #efeded");
                                } else {
                                    salesCategoryUI.traCheckButton.setSelected(true);
                                    salesCategoryUI.listBillTable.setStyle("-fx-base: #00ff00");
                                }
                            }
                    );
                    salesCategoryUI.wholeSaleCheckBox.getScene().getAccelerators().put(
                            new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN),
                            () -> {
                                if (salesCategoryUI.wholeSaleCheckBox.isSelected()) {
                                    salesCategoryUI.wholeSaleCheckBox.setSelected(false);
                                    float price = StockCategoryData.sellPrice;
                                    try {
                                        float product = (price * (Integer.parseInt(salesCategoryUI.
                                                quantityTextField.getText())))
                                                - Integer.parseInt(salesCategoryUI.discountTextField.getText());
                                        //format number for accountant
                                        String value = NumberFormat.getInstance().format(product);
                                        salesCategoryUI.amountTextField.setText(value);
                                    } catch (Throwable throwable) {
                                        salesCategoryUI.amountTextField.setText(String.valueOf(0));
                                    }
                                } else {
                                    salesCategoryUI.wholeSaleCheckBox.setSelected(true);
                                    if (salesCategoryUI.quantityTextField.getText().isEmpty()) {
                                        salesCategoryUI.amountTextField.setText(String.valueOf(0));
                                    } else {
                                        int quant = Integer.parseInt(salesCategoryUI.quantityTextField.getText());
                                        float wPrice = StockCategoryData.wSellPrice;
                                        try {
                                            float total = (wPrice * quant)
                                                    - Integer.parseInt(salesCategoryUI.discountTextField.getText());
                                            salesCategoryUI.amountTextField
                                                    .setText(NumberFormat.getInstance().format(total));

                                        } catch (Throwable q) {
                                            salesCategoryUI.amountTextField.setText(String.valueOf(0));
                                        }
                                    }
                                }
                            }
                    );
                    break;
                    //check if its other pane
                } else if (node.toString().startsWith("Grid")) {
                    rootAdmin.getChildren().remove(i);
                    rootAdmin.add(cashSaleUI, 1, 1);

                    //some keyboard shortcut
                    salesCategoryUI.submitCashBill.getScene().getAccelerators().put(
                            new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN),
                            () -> {
                                salesCategoryUI.setSubmitCashBill();
                            }
                    );
                    salesCategoryUI.traCheckButton.getScene().getAccelerators().put(
                            new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN),
                            () -> {
                                if (salesCategoryUI.traCheckButton.isSelected()) {
                                    salesCategoryUI.traCheckButton.setSelected(false);
                                    salesCategoryUI.listBillTable.setStyle("-fx-base: #efeded");
                                } else {
                                    salesCategoryUI.traCheckButton.setSelected(true);
                                    salesCategoryUI.listBillTable.setStyle("-fx-base: #00ff00");
                                }
                            }
                    );
                    salesCategoryUI.wholeSaleCheckBox.getScene().getAccelerators().put(
                            new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN),
                            () -> {
                                if (salesCategoryUI.wholeSaleCheckBox.isSelected()) {
                                    salesCategoryUI.wholeSaleCheckBox.setSelected(false);
                                } else {
                                    salesCategoryUI.wholeSaleCheckBox.setSelected(true);
                                }
                            }
                    );
                    break;

                }
            }
        }
    }

}