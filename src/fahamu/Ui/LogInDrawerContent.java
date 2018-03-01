package fahamu.Ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import fahamu.provider.Resources;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class LogInDrawerContent extends Resources{

    public JFXButton signUpBannerJFXButton;
    public JFXButton logInBannerJFXButton;
    public JFXButton settingBannerJFXButton;
    public FlowPane flowPane;

    private JFXDrawersStack jfxDrawersStack;
    private JFXDrawer jfxDrawerLogIn;
    private JFXDrawer jfxDrawerSignUp;
    //private JFXDrawer jfxDrawerSetting;
    private boolean notSet=true;

    @FXML
    public void initialize() {

        assert LOG_IN_BANNER_IMAGE != null;
        logInBannerJFXButton.setGraphic(new ImageView(LOG_IN_BANNER_IMAGE.toExternalForm()));
        signUpBannerJFXButton.setGraphic(new ImageView(SIGN_UP_BANNER_IMAGE.toExternalForm()));
        settingBannerJFXButton.setGraphic(new ImageView(SETTING_BANNER_IMAGE.toExternalForm()));
    }

    public void signUpBanner(ActionEvent actionEvent) {
        toggleDrawer(actionEvent,1);

    }

    public void logInBanner(ActionEvent actionEvent) {
        toggleDrawer(actionEvent,0);
    }

    public void settingDrawer(ActionEvent actionEvent) {

    }

    private void toggleDrawer(ActionEvent actionEvent, int id){
        JFXButton jfxButton = (JFXButton) actionEvent.getSource();
        FlowPane flowPane= (FlowPane) jfxButton.getParent().getParent();
        if (notSet) {
            try {
                jfxDrawersStack = (JFXDrawersStack) flowPane.getParent();
                for (Node node :
                        jfxDrawersStack.getChildren()) {
                    if (node.getId().equals("leftDrawer")) {
                        jfxDrawerLogIn = (JFXDrawer) node;
                        System.out.println(jfxDrawerLogIn.toString());
                    } else if (node.getId().equals("rightDrawer")) {
                        jfxDrawerSignUp = (JFXDrawer) node;
                        System.out.println(jfxDrawerSignUp.toString());
                    }
                }
            } catch (Throwable ignore) { }
            notSet=false;
        }
        if (id==0)jfxDrawersStack.toggle(jfxDrawerLogIn);
        else if (id==1)jfxDrawersStack.toggle(jfxDrawerSignUp);
    }
}
