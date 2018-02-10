package fahamu.UserInteface;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class CashierUiScene {

    @FXML
    public JFXButton addToCartJFXButton;

    @FXML
    public void initialize(){
        addToCartJFXButton.setGraphic(new ImageView(getClass().getResource("data/cart.png").toExternalForm()));
    }

}
