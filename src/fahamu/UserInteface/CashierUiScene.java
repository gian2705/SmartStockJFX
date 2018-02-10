package fahamu.UserInteface;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class CashierUiScene {

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
    public StackPane parentStackPane;

    @FXML
    public void initialize(){
        addToCartJFXButton.setGraphic(new ImageView(getClass().getResource("data/cart.png").toExternalForm()));
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

    public void submittBill(ActionEvent actionEvent) {
    }

    public void cancelBill(ActionEvent actionEvent) {
    }

    public void changeBillMode(ActionEvent actionEvent) {
    }
}
