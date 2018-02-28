package fahamu.service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Resources {

    public String ROOT_PATH_FOR_RESOURCE=""; //empty string solve a nul bug
    public URL BACKGROUND_IMAGE_PATH;
    public URL SIGNUP_IMAGE_PATH;
    public String SERVER_CREDENTIAL_FILE;
    public String BUSINESS_NAME;
    public URL ICON;
    public URL WRONG_PASSWORD_ICON;
    public URL DATABASE_ERROR_ICON;
    public URL CART_ICON;
    public URL BUSINESS_LOGO;
    public URL LOG_IN_UI_FXML;
    public URL LEFT_DRAWER_LAYOUT_FXML;
    public URL SALE_UI_LAYOUT_FXML;

    public final String ADMIN = "admin";


    public byte[] SERVER_IPA4_ADDRESS = new byte[]{(byte) 192, (byte) 168, 0, 2};

    public Resources() {
        BUSINESS_NAME = "SMART STOCK MANAGER";
        BUSINESS_LOGO =getResourceAsUrl("res/image/lbLogo.jpg");
        CART_ICON=getResourceAsUrl("res/image/cart.png");
        DATABASE_ERROR_ICON=getResourceAsUrl("res/image/databaseIcon.png");
        ICON = getResourceAsUrl("/res/image/ssmlogo.png");
        BACKGROUND_IMAGE_PATH = getResourceAsUrl("res/image/calculate.jpg");
        SIGNUP_IMAGE_PATH = getResourceAsUrl("res/image/googleSign.png");
        WRONG_PASSWORD_ICON=getResourceAsUrl("res/image/manicon.png");
        SERVER_CREDENTIAL_FILE = getResourceAsPath("res/sqlite3/serverCredential.db.encrypted").toString();
        LOG_IN_UI_FXML = getResourceAsUrl("res/fxmls/loginUi.fxml");
        LEFT_DRAWER_LAYOUT_FXML = getResourceAsUrl("res/fxmls/logInLeftDrawerContents.fxml");
        SALE_UI_LAYOUT_FXML = getResourceAsUrl("res/fxmls/sellerUi.fxml");
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
