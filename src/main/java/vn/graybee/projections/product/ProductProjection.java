package vn.graybee.projections.product;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface ProductProjection {

    long getId();

    int getCategoryId();

    int getManufacturerId();
    
    String getName();

    int getWarranty();

    float getWeight();

    String getDimension();

    float getPrice();

    String getColor();

    String getDescription();

    String getThumbnail();

    String getConditions();

    String getStatus();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getUpdatedAt();

}
