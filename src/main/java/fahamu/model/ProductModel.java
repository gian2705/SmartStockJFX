package fahamu.model;

public class ProductModel {
    private String productName;
    private float productWSell;
    private int productId;



    public ProductModel(String productName, float productWSell, int productId) {
        this.productName = productName;
        this.productWSell = productWSell;
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductWSell() {
        return productWSell;
    }

    public void setProductWSell(float productWSell) {
        this.productWSell = productWSell;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
