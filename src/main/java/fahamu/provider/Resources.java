package fahamu.provider;

import java.net.URL;

public class Resources {

    public static final String BUSINESS_NAME = "SMART STOCK MANAGER";
    public final URL BUSINESS_LOGO =getResourceAsUrl("image/lbLogo.jpg");
    public final URL CART_ICON=getResourceAsUrl("image/cart.png");
    public final URL DATABASE_ERROR_ICON=getResourceAsUrl("image/databaseIcon.png");
    public final URL ICON = getResourceAsUrl("image/ssmlogo.png");
    public final URL BACKGROUND_IMAGE_PATH = getResourceAsUrl("image/calculate.jpg");
    public final URL WRONG_PASSWORD_ICON=getResourceAsUrl("image/manicon.png");
    public final URL LOG_IN_UI_FXML = getResourceAsUrl("../fxmls/loginUi.fxml");
    public final URL LEFT_DRAWER_LAYOUT_FXML = getResourceAsUrl("logInLeftDrawer.fxml");
    public final URL SALE_UI_LAYOUT_FXML = getResourceAsUrl("../fxmls/sellerUi.fxml");
    public final URL LOG_IN_BANNER_IMAGE = getResourceAsUrl("image/googleSign.png");
    public final URL LOG_IN_DRAWER_STACK_PANE_CONTENT =getResourceAsUrl("logInDrawerContent.fxml");
    public final URL SIGN_UP_BANNER_IMAGE=getResourceAsUrl("image/googleSignUp.png");
    public final URL SETTING_BANNER_IMAGE=getResourceAsUrl("image/setting.png");

    public final String ADMIN = "admin";

    //45.56.64.243
    public static byte[] SERVER_IPA4_ADDRESS = new byte[]{(byte) 45, (byte) 56, (byte)64, (byte)243};

    public Resources() {

    }

    private URL getResourceAsUrl(String name) {
//        Path path = Paths.get(ROOT_PATH_FOR_RESOURCE, name);
//        File file = path.toFile();
//        try {
//            return file.toURI().toURL();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        return Resources.class.getResource(name);
    }

//    private Path getResourceAsPath(String name) {
//        //System.out.println(Paths.get(rootResourcePath,name));
//        return Paths.get(getClass().getResource(name).getPath());
//    }
}
