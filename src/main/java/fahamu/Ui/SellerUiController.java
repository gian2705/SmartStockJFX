package fahamu.Ui;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class SellerUiController extends BaseUIComponents {

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
    public JFXTreeTableView allSaleJFXTreeTableView;
    public StackPane parentPane;

    @FXML
    public void initialize()  {
        addToCartJFXButton.setGraphic(new ImageView(getCART_ICON().toExternalForm()));
    }

    public void enterButtonPressed(KeyEvent keyEvent) {

    }

    public void changeToWholeSale(ActionEvent actionEvent) {
        tJFXToggleButton.setAlignment(Pos.TOP_LEFT);
        if (wholesaleJFXToggleButton.isSelected())wholesaleJFXToggleButton.setText("Whole Sale");
        else wholesaleJFXToggleButton.setText("          ");
        actionEvent.consume();
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
        if (tJFXToggleButton.isSelected()){
            tJFXToggleButton.setText("n/n");
        }
        else tJFXToggleButton.setText("   ");
        actionEvent.consume();
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

//    private void onCloseStage(){
//        parentPane.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                event.consume();
//                System.out.println("Close stoped");
//            }
//        });
//    }
}
