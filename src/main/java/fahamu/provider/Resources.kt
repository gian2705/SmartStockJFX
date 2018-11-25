//package fahamu.provider
//
//import java.net.URL
//
//open class Resources() {
//
//    var ROOT_PATH_FOR_RESOURCE = "" //empty string solve a nul bug
//    //public final Path SERVER_CREDENTIAL_FILE = getResourceAsPath("../sqlite3/serverCredential.db.encrypted");
//    val BUSINESS_LOGO = getResourceAsUrl("image/lbLogo.jpg")
//    val CART_ICON = getResourceAsUrl("image/cart.png")
//    val DATABASE_ERROR_ICON = getResourceAsUrl("image/databaseIcon.png")
//    val ICON = getResourceAsUrl("image/ssmlogo.png")
//    val BACKGROUND_IMAGE_PATH = getResourceAsUrl("image/calculate.jpg")
//    val wrongPasswordIcon = getResourceAsUrl("image/manicon.png")
//    val LOG_IN_UI_FXML = getResourceAsUrl("fxmls/loginUi.fxml")
//    val LEFT_DRAWER_LAYOUT_FXML = getResourceAsUrl("fxmls/logInLeftDrawer.fxml")
//    val SALE_UI_LAYOUT_FXML = getResourceAsUrl("fxmls/sellerUi.fxml")
//    val LOG_IN_BANNER_IMAGE = getResourceAsUrl("image/googleSign.png")
//    val LOG_IN_DRAWER_STACK_PANE_CONTENT = getResourceAsUrl("fxmls/logInDrawerContent.fxml")
//    val SIGN_UP_BANNER_IMAGE = getResourceAsUrl("image/googleSignUp.png")
//    val SETTING_BANNER_IMAGE = getResourceAsUrl("image/setting.png")
//
//    val ADMIN = "admin"
//
//    companion object {
//        val BUSINESS_NAME = "SMART STOCK MANAGER"
//        var SERVER_IPA4_ADDRESS = byteArrayOf(192.toByte(), 168.toByte(), 0, 2)
//
//        fun  getResourceAsUrl(name: String): URL {
//            return Thread.currentThread().contextClassLoader.getResource(name)
//        }
//    }
//
//}
