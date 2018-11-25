package fahamu.provider;

import java.net.URL;

public class Resources {

    //String ROOT_PATH_FOR_RESOURCE = "" //empty string solve a nul bug
    //public final Path SERVER_CREDENTIAL_FILE = getResourceAsPath("../sqlite3/serverCredential.db.encrypted");
    public static URL BUSINESS_LOGO = getResourceAsUrl("image/lbLogo.jpg");
    public static URL CART_ICON = getResourceAsUrl("image/cart.png");
    public static URL DATABASE_ERROR_ICON = getResourceAsUrl("image/databaseIcon.png");
    public static URL ICON = getResourceAsUrl("image/ssmlogo.png");
    public static URL BACKGROUND_IMAGE_PATH = getResourceAsUrl("image/calculate.jpg");
    public static URL wrongPasswordIcon = getResourceAsUrl("image/manicon.png");
    public static URL LOG_IN_UI_FXML = getResourceAsUrl("fxmls/loginUi.fxml");
    public static URL LEFT_DRAWER_LAYOUT_FXML = getResourceAsUrl("fxmls/logInLeftDrawer.fxml");
    public static URL SALE_UI_LAYOUT_FXML = getResourceAsUrl("fxmls/sellerUi.fxml");
    public static URL LOG_IN_BANNER_IMAGE = getResourceAsUrl("image/googleSign.png");
    public static URL LOG_IN_DRAWER_STACK_PANE_CONTENT = getResourceAsUrl("fxmls/logInDrawerContent.fxml");
    public static URL SIGN_UP_BANNER_IMAGE = getResourceAsUrl("image/googleSignUp.png");
    public static URL SETTING_BANNER_IMAGE = getResourceAsUrl("image/setting.png");

    public static String ADMIN = "admin";

    public static String BUSINESS_NAME = "SMART STOCK MANAGER";
    public static String SERVER_IPA4_ADDRESS = "192.168.0.2";

    public static URL getResourceAsUrl(String name) {
        return Thread.currentThread().getContextClassLoader().getResource(name);
    }


}
