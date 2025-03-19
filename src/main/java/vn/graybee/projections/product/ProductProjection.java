package vn.graybee.projections.product;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface ProductProjection {

    String getProductName();

    String getProductCode();

    String getCategoryName();

    String getManufacturerName();

    int getWarranty();

    float getWeight();

    String getDimension();

    float getPrice();

    int getDiscountPercent();

    float getFinalPrice();

    String getColor();

    String getThumbnail();

    String getConditions();

    String getStatus();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getUpdatedAt();

}
