package fahamu.UserInteface;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class BaseUIComponents {

    BaseUIComponents() {

    }

    public void alertCreator(String title, String header, String content, StackPane parent,ImageView icon) {
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        Label head=new Label(header == null ? title : "  "+title + "\n  " + header);
        if (icon!=null)head.setGraphic(icon);
        dialogContent.setHeading(head);
        dialogContent.setBody(new Text(content));
        JFXButton close = new JFXButton("Close");
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: red; -fx-text-fill:black");
        close.getStyleClass().add("JFXButton");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(parent, dialogContent, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);
        close.setOnAction(event -> dialog.close());
        dialog.show();
    }
}
