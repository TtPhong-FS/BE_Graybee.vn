package vn.graybee.response.admin.products;

public class ProductStatisticResponse {

    private int id;

    private String productCode;

    private int viewCount;

    private int purchaseCount;

    private boolean hasPromotion;

    public ProductStatisticResponse() {
    }

    public ProductStatisticResponse(int id, String productCode, int viewCount, int purchaseCount, boolean hasPromotion) {
        this.id = id;
        this.productCode = productCode;
        this.viewCount = viewCount;
        this.purchaseCount = purchaseCount;
        this.hasPromotion = hasPromotion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public boolean isHasPromotion() {
        return hasPromotion;
    }

    public void setHasPromotion(boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

}
