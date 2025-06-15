package vn.graybee.modules.account.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class FavoriteProductResponse {

    private Long productId;

    private double price;

    private double finalPrice;

    private String productName;

    private String thumbnailUrl;

    public FavoriteProductResponse(Long productId, BigDecimal price, BigDecimal finalPrice, String productName, String thumbnailUrl) {
        this.productId = productId;
        this.price = price != null ? price.doubleValue() : 0.0;
        this.finalPrice = finalPrice != null ? finalPrice.doubleValue() : 0.0;
        this.productName = productName;
        this.thumbnailUrl = thumbnailUrl;
    }

}
