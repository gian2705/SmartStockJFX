package fahamu.provider;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Resources {

    public String ROOT_PATH_FOR_RESOURCE=""; //empty string solve a nul bug
    public String BUSINESS_NAME = "SMART STOCK MANAGER";
    public final Path SERVER_CREDENTIAL_FILE = getResourceAsPath("res/sqlite3/serverCredential.db.encrypted");
    public final URL BUSINESS_LOGO =getResourceAsUrl("res/image/lbLogo.jpg");
    public final URL CART_ICON=getResourceAsUrl("res/image/cart.png");
    public final URL DATABASE_ERROR_ICON=getResourceAsUrl("res/image/databaseIcon.png");
    public final URL ICON = getResourceAsUrl("res/image/ssmlogo.png");
    public final URL BACKGROUND_IMAGE_PATH = getResourceAsUrl("res/image/calculate.jpg");
    public final URL WRONG_PASSWORD_ICON=getResourceAsUrl("res/image/manicon.png");
    public final URL LOG_IN_UI_FXML = getResourceAsUrl("res/fxmls/loginUi.fxml");
    public final URL LEFT_DRAWER_LAYOUT_FXML = getResourceAsUrl("res/fxmls/logInLeftDrawer.fxml");
    public final URL SALE_UI_LAYOUT_FXML = getResourceAsUrl("res/fxmls/sellerUi.fxml");
    public final URL LOG_IN_BANNER_IMAGE = getResourceAsUrl("res/image/googleSign.png");
    public final URL LOG_IN_DRAWER_STACK_PANE_CONTENT =getResourceAsUrl("res/fxmls/logInDrawerContent.fxml");
    public final URL SIGN_UP_BANNER_IMAGE=getResourceAsUrl("res/image/googleSignUp.png");
    public final URL SETTING_BANNER_IMAGE=getResourceAsUrl("res/image/setting.png");

    public final String ADMIN = "admin";
    public byte[] SERVER_IPA4_ADDRESS = new byte[]{(byte) 192, (byte) 168, 0, 2};

    public Resources() {

    }

    private URL getResourceAsUrl(String name) {
        Path path = Paths.get(ROOT_PATH_FOR_RESOURCE, name);
        File file = path.toFile();
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Path getResourceAsPath(String name) {
        //System.out.println(Paths.get(rootResourcePath,name));
        return Paths.get(ROOT_PATH_FOR_RESOURCE, name);
    }
}
