package fahamu.bin.Ui;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.StackPane;

public class CashierScene {

    @FXML
    public JFXButton addToCartJFXButton;
    public JFXToggleButton wholesaleJFXToggleButton;
    public JFXTextField quantityOfUnitJFXBadge;
    public JFXTextField discountJFXTextField;
    public JFXBadge amountOfItemJFXBadge;
    public JFXButton cancelOrderJFXButton;
    public JFXButton submitBillJFXButton;
    public JFXButton cancelBillJFXButton;
    public JFXToggleButton tJFXToggleButton;
    public Tab allSalesTab;
    public Tab cashierReportTab;
    public Tab salesOfDayTab;
    public JFXTreeTableView billJFXTreeTable;
    public JFXBadge totalBillJFXBadge;
    public JFXTextField receivedCashJFXTextField;
    public MenuItem logOutMenuItem;
    public MenuItem profileMenuItem;
    public MenuItem fullScreenMenuItem;
    public MenuItem vasMenuItem;
    public MenuItem aboutMenuItem;
    public JFXTreeTableView salesOfDayJFXTreeTableView;
    public JFXToggleButton salesTrendJFXToggleButton;
    public Label cpdCounter;
    public Label yourSaleLabel;
    public Label allSaleLabel;
    public AreaChart salesTrendAreaChart;
    public JFXSpinner salesTrendRefreshJFXSpinner;
    public JFXTreeTableView allSaleJFXTreeTableView;

    @FXML
    public void initialize(){
        addToCartJFXButton.setGraphic(new ImageView(getClass().getResource("resources/cart.png").toExternalForm()));
    }

    public void enterButtonPressed(KeyEvent keyEvent) {
    }

    public void changeToWholeSale(ActionEvent actionEvent) {
    }

    public void displayProductsResults(KeyEvent keyEvent) {
    }

    public void addItemToCart(ActionEvent actionEvent) {
    }

    public void cancelOrder(ActionEvent actionEvent) {
    }

    public void submitBill(ActionEvent actionEvent) {
    }

    public void cancelBill(ActionEvent actionEvent) {
    }

    public void changeBillMode(ActionEvent actionEvent) {
    }

    public void setFullScreen(ActionEvent actionEvent) {
    }

    public void logOut(ActionEvent actionEvent) {
    }

    public void showProfile(ActionEvent actionEvent) {
    }

    public void viewAllSale(ActionEvent actionEvent) {
    }

    public void showAbout(ActionEvent actionEvent) {
    }

    public void refreshSalesTrend(Event mouseEvent) {
    }

    public void changeSaleTrendMode(ActionEvent actionEvent) {
    }
}
